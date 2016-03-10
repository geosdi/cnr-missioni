package it.cnr.missioni.dashboard.checkmassimale;

/**
 * @author Salvia Vito
 */
public class CheckTwoFattura extends IControlCheckMassimale.AbstractControlCheckMassimale{

    protected CheckTwoFattura() {
    }

    public static IControlCheckMassimale newCheckTwoFattura() {
        return new CheckTwoFattura();
    }
	
	/**
	 * 
	 */
	@Override
	public void check()  throws Exception{
		if (checkMassimale.getTotaleFattureGiornaliera() > checkMassimale.getMassimale().getValue()) {
			
			if(checkMassimale.getFattura().getImporto() >= checkMassimale.getOtherFattura().getImporto()){
				checkMassimale.getFattura().setImportoSpettante(checkMassimale.getMassimale().getValue());
				checkMassimale.getOtherFattura().setImportoSpettante(0.0);
			}else{
				checkMassimale.getOtherFattura().setImportoSpettante(checkMassimale.getMassimale().getValue());
				checkMassimale.getFattura().setImportoSpettante(0.0);
			}
		}
	}

}
