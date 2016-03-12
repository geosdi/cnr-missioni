package it.cnr.missioni.model.validator;

import org.joda.time.DateTime;
import org.joda.time.Hours;

/**
 * @author Salvia Vito
 */
public class ValidatorFatturaPastoEstera implements IValidatorManager{

	private DateTime dataFattura;
	private DateTime dataInizioMissione;
	private DateTime dataFineMissione;
	private DateTime dataFrontieraAndata;
	private DateTime dataFrontieraRitorno;
	private int maxOccorrenze;

	public ValidatorFatturaPastoEstera(DateTime dataFattura, DateTime dataInizioMissione, DateTime dataFineMissione,
			DateTime dataFrontieraAndata, DateTime dataFrontieraRitorno) {
		this.dataFattura = dataFattura;
		this.dataInizioMissione = dataInizioMissione;
		this.dataFineMissione = dataFineMissione;
		this.dataFrontieraAndata = dataFrontieraAndata;
		this.dataFrontieraRitorno = dataFrontieraRitorno;
	}

	public void initialize() throws Exception{
		Step1 s1 = new Step1();
		Step2 s2 = new Step2();
		Step3 s3 = new Step3();
		Step4 s4 = new Step4();
		Step5 s5 = new Step5();
		Step6 s6 = new Step6();

		s1.setNextValidator(s2);
		s2.setNextValidator(s3);
		s3.setNextValidator(s4);
		s4.setNextValidator(s5);
		s5.setNextValidator(s6);

		s1.check();

	}
	
	private boolean checkInThesameDay(DateTime data1,DateTime data2){
		if(data1.getYear() == data2.getYear() && data1.getDayOfYear() == data2.getDayOfYear())
			return true;
		return false;
	}

	/**
	 * 
	 */
	public ValidatorFatturaPastoEstera() {
		// TODO Auto-generated constructor stub
	}

	// Se la fattura è nel periodo estero
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
		public void check() throws Exception {
			if (dataFattura.isAfter(dataFrontieraAndata) && dataFattura.isBefore(dataFrontieraRitorno)) {
				maxOccorrenze = 2;
			} else
				this.nextValidator.check();
		}

	}

	// Se data della fattura compresa tra data inizio e data frontiera andata e date inizio missione e data andata frontiera sono in giorni differenti
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
		public void check() throws Exception {
			if (dataFattura.isAfter(dataInizioMissione) && dataFattura.isBefore(dataFrontieraAndata) && !checkInThesameDay(dataInizioMissione,dataFrontieraAndata)) {			
				
				DateTime d = dataInizioMissione.plusDays(1);
				int hours = Hours.hoursBetween(dataInizioMissione, new DateTime(d.getYear(),d.getMonthOfYear(),d.getDayOfMonth(),0,0)).getHours();
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
	
	// Se data della fattura compresa tra data inizio e data frontiera andata e date inizio missione e data andata frontiera sono nello stesso giorno
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
			if (dataFattura.isAfter(dataInizioMissione) && dataFattura.isBefore(dataFrontieraAndata) && checkInThesameDay(dataInizioMissione,dataFrontieraAndata)) {
				int hours = Hours.hoursBetween(dataInizioMissione, dataFrontieraAndata).getHours();
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

	// Se data della fattura compresa tra data Fine e data frontiera ritorno e data ritorno e data ritorno frontiera sono in gg differenti
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
		public void check() throws Exception {
			if (dataFattura.isAfter(dataFrontieraRitorno) && dataFattura.isBefore(dataFineMissione) && !checkInThesameDay(dataFineMissione,dataFrontieraRitorno)) {
				DateTime d = new DateTime(dataFineMissione.getYear(),dataFineMissione.getMonthOfYear(),dataFineMissione.getDayOfMonth(),0,0);

				int hours = Hours.hoursBetween(d, dataFineMissione)
						.getHours();
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
	
	// Se data della fattura compresa tra data Fine e data frontiera ritorno e data ritorno e data ritorno frontiera sono nello stesso giorno
	class Step5 extends IValidator.AbstractValidator {

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
			if (dataFattura.isAfter(dataFrontieraRitorno) && dataFattura.isBefore(dataFineMissione) && checkInThesameDay(dataFineMissione,dataFrontieraRitorno)) {

				int hours = Hours.hoursBetween(dataFrontieraRitorno, dataFineMissione)
						.getHours();
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

	// Se data della fattura in un giorno differente della data inizio e data
	// fine
	class Step6 extends IValidator.AbstractValidator {

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
	 * @return the dataFrontieraAndata
	 */
	public DateTime getDataFrontieraAndata() {
		return dataFrontieraAndata;
	}

	/**
	 * @param dataFrontieraAndata
	 */
	public void setDataFrontieraAndata(DateTime dataFrontieraAndata) {
		this.dataFrontieraAndata = dataFrontieraAndata;
	}

	/**
	 * @return the dataFrontieraRitorno
	 */
	public DateTime getDataFrontieraRitorno() {
		return dataFrontieraRitorno;
	}

	/**
	 * @param dataFrontieraRitorno
	 */
	public void setDataFrontieraRitorno(DateTime dataFrontieraRitorno) {
		this.dataFrontieraRitorno = dataFrontieraRitorno;
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
