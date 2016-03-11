package it.cnr.missioni.dashboard.checkmassimalepasto;

/**
 * 
 * Se nello stesso giorno abbiamo una sola fattura di vitto
 * 
 * @author Salvia Vito
 */
public class CheckOneFatturaPasto extends IControlCheckMassimale.AbstractControlCheckMassimale {

	protected CheckOneFatturaPasto() {
	}

	public static IControlCheckMassimale newCheckOneFattura() {
		return new CheckOneFatturaPasto();
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
