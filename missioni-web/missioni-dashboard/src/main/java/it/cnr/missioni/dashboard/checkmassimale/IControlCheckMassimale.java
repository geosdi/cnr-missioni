package it.cnr.missioni.dashboard.checkmassimale;

import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;

/**
 * @author Salvia Vito
 */
public interface IControlCheckMassimale {
	
	void check() throws Exception;
	
//	IControlCheckMassimale withMissione(Missione missione);
//	
//	IControlCheckMassimale withFattura(Fattura fattura);
//
//	IControlCheckMassimale withMassimale(Massimale massimale);
//
	void setNextControl(IControlCheckMassimale nextControl);
//
//	IControlCheckMassimale withOtherFattura(Fattura otherFattura);
//	
//	IControlCheckMassimale withTotaleFatturaGiornaliera(double totaleFattureGiornaliera);

	IControlCheckMassimale withCheckMassimale(CheckMassimale checkMassimale);

	
	abstract class AbstractControlCheckMassimale implements IControlCheckMassimale{
		
//		protected double totaleFattureGiornaliera = 0.0;
//		protected Fattura fattura;
//		protected Massimale massimale;
//		protected Fattura otherFattura;
		protected IControlCheckMassimale nextControl;
//		protected String areaGeografica;
//		protected Missione missione;
//		protected String livello;
		
		protected CheckMassimale checkMassimale;
		
		protected AbstractControlCheckMassimale() {
		}

		public IControlCheckMassimale withCheckMassimale(CheckMassimale checkMassimale){
			this.checkMassimale = checkMassimale;
			return _self();
		}
		
//		public IControlCheckMassimale withMissione(Missione missione){
//			this.missione = missione;
//			return _self();
//		}
//		
//		public IControlCheckMassimale withFattura(Fattura fattura){
//			this.fattura = fattura;
//			return _self();
//		}
//		
//		public IControlCheckMassimale withMassimale(Massimale massimale){
//			this.massimale = massimale;
//			return _self();
//		}
//		
		public void setNextControl(IControlCheckMassimale nextControl){
			this.nextControl = nextControl;
		}
//		
//		public IControlCheckMassimale withOtherFattura(Fattura otherFattura){
//			this.otherFattura = otherFattura;
//			return _self();
//		}
//		
//		public IControlCheckMassimale withTotaleFatturaGiornaliera(double totaleFattureGiornaliera){
//			this.totaleFattureGiornaliera = totaleFattureGiornaliera;
//			return _self();
//		}
		
		public IControlCheckMassimale _self(){
			return this;
		}

	}
	
}
