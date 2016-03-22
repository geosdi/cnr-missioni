package it.cnr.missioni.model.validator;

import org.joda.time.DateTime;
import org.joda.time.Hours;

/**
 * @author Salvia Vito
 */
public class ValidatorFatturaPastoItalia implements IValidatorManager{

	private DateTime dataFattura;
	private DateTime dataInizioMissione;
	private DateTime dataFineMissione;
	private int maxOccorrenze;

	public ValidatorFatturaPastoItalia(DateTime dataFattura, DateTime dataInizioMissione, DateTime dataFineMissione) {
		this.dataFattura = dataFattura;
		this.dataInizioMissione = dataInizioMissione;
		this.dataFineMissione = dataFineMissione;
	}
	
	public void initialize() throws Exception{
		Step1 s1 = new Step1();
		Step2 s2 = new Step2();
		Step3 s3 = new Step3();
		Step4 s4 = new Step4();

		s1.setNextValidator(s2);
		s2.setNextValidator(s3);
		s3.setNextValidator(s4);

		s1.check();

	}

	/**
	 * 
	 */
	public ValidatorFatturaPastoItalia() {
		// TODO Auto-generated constructor stub
	}

	// Se data inizio e data fine nello stesso giorno
	class Step1 extends IValidator.AbstractValidator {

		/**
		 * @param validator
		 */
		@Override
		public void setNextValidator(IValidator validator) {
			this.nextValidator = validator;
		}

		/**
		 * @return
		 */
		@Override
		public void check() throws Exception{
			if (dataInizioMissione.getYear() == dataFineMissione.getYear()
					&& dataInizioMissione.getDayOfYear() == dataFineMissione.getDayOfYear()) {
				int hours = Hours.hoursBetween(dataInizioMissione, dataFineMissione).getHours();
				if (hours > 4 && hours <= 12)
					setMaxOccorrenze(1);
				else if (hours > 12)
					setMaxOccorrenze(2);
				else
					setMaxOccorrenze(0);
			} else
				this.nextValidator.check();
		}

	}
	
	// Se data della fattura nello stesso giorno della data inizio
	class Step2 extends IValidator.AbstractValidator {

		/**
		 * @param validator
		 */
		@Override
		public void setNextValidator(IValidator validator) {
			this.nextValidator = validator;
		}

		/**
		 * @return
		 */
		@Override
		public void check() throws Exception{
			if (dataInizioMissione.getYear() == dataFattura.getYear()
					&& dataInizioMissione.getDayOfYear() == dataFattura.getDayOfYear()) {				
				int hours = Hours.hoursBetween(dataInizioMissione, new DateTime(dataInizioMissione.getYear(),dataInizioMissione.getMonthOfYear(),dataInizioMissione.getDayOfMonth(),23,59)).getHours();
				if (hours > 4 && hours <= 12)
					setMaxOccorrenze(1);
				else if (hours > 12)
					setMaxOccorrenze(2);
				else
					setMaxOccorrenze(0);
			} else
				this.nextValidator.check();
		}

	}
	
	// Se data della fattura nello stesso giorno della data Fine
	class Step3 extends IValidator.AbstractValidator {

		/**
		 * @param validator
		 */
		@Override
		public void setNextValidator(IValidator validator) {
			this.nextValidator = validator;
		}

		/**
		 * @return
		 */
		@Override
		public void check() throws Exception {
			if (dataFineMissione.getYear() == dataFattura.getYear()
					&& dataFineMissione.getDayOfYear() == dataFattura.getDayOfYear()) {
				int hours = Hours.hoursBetween(new DateTime(dataFineMissione.getYear(),dataFineMissione.getMonthOfYear(),dataFineMissione.getDayOfMonth(),0,0), dataFineMissione).getHours();
				if (hours > 4 && hours <= 12)
					setMaxOccorrenze(1);
				else if (hours > 12)
					setMaxOccorrenze(2);
				else
					setMaxOccorrenze(0);
			} else
				this.nextValidator.check();
		}

	}
	
	// Se data della fattura in un giorno differente della data inizio e data fine
	class Step4 extends IValidator.AbstractValidator {

		/**
		 * @param validator
		 */
		@Override
		public void setNextValidator(IValidator validator) {
			this.nextValidator = validator;
		}

		/**
		 * @return
		 */
		@Override
		public void check() {
			setMaxOccorrenze(2);

		}

	}

	/**
	 * @return the dataFattura
	 */
	public DateTime getDataFattura() {
		return dataFattura;
	}

	/**
	 * @param dataFattura
	 */
	public void setDataFattura(DateTime dataFattura) {
		this.dataFattura = dataFattura;
	}

	/**
	 * @return the dataInizioMissione
	 */
	public DateTime getDataInizioMissione() {
		return dataInizioMissione;
	}

	/**
	 * @param dataInizioMissione
	 */
	public void setDataInizioMissione(DateTime dataInizioMissione) {
		this.dataInizioMissione = dataInizioMissione;
	}

	/**
	 * @return the dataFineMissione
	 */
	public DateTime getDataFineMissione() {
		return dataFineMissione;
	}

	/**
	 * @param dataFineMissione
	 */
	public void setDataFineMissione(DateTime dataFineMissione) {
		this.dataFineMissione = dataFineMissione;
	}

	/**
	 * @return the maxOccorrenze
	 */
	public int getMaxOccorrenze() {
		return maxOccorrenze;
	}

	/**
	 * @param maxOccorrenze 
	 */
	public void setMaxOccorrenze(int maxOccorrenze) {
		this.maxOccorrenze = maxOccorrenze;
	}

}
