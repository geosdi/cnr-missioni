package it.cnr.missioni.dashboard.checkmassimale;

/**
 * @author Salvia Vito
 */
public class CheckOneFattura extends IControlCheckMassimale.AbstractControlCheckMassimale {

	protected CheckOneFattura() {
	}

	public static IControlCheckMassimale newCheckOneFattura() {
		return new CheckOneFattura();
	}

	/**
	 * 
	 */
	@Override
	public void check() throws Exception {

		if (!checkMassimale.getMissione().isMissioneEstera() && checkMassimale.getOtherFattura() == null)
			checkItalia();
		else if (checkMassimale.getMissione().isMissioneEstera() && checkMassimale.getOtherFattura()  == null)
			checkEstera();
		else
			this.nextControl.check();
	}

	private void checkEstera() {
		if (checkMassimale.getTotaleFattureGiornaliera() > checkMassimale.getMassimale().getValue())
			checkMassimale.getFattura().setImportoSpettante(checkMassimale.getFattura().getImporto() - (checkMassimale.getMassimale().getValue()));
	}

	private void checkItalia() {
		if (checkMassimale.getTotaleFattureGiornaliera()  > checkMassimale.getMassimale().getValue() / 2.0)
			checkMassimale.getFattura().setImportoSpettante(checkMassimale.getMassimale().getValue() / 2.0);
	}

}
