package it.cnr.missioni.dashboard.checkmassimalepasto;

/**
 * 
 * Se nello stesso giorno abbiamo 2 fatture di vitto
 * 
 * @author Salvia Vito
 */
public class CheckTwoFatturaPasto extends IControlCheckMassimale.AbstractControlCheckMassimale{

    protected CheckTwoFatturaPasto() {
    }

    public static IControlCheckMassimale newCheckTwoFattura() {
        return new CheckTwoFatturaPasto();
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
