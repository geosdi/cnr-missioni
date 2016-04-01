package it.cnr.missioni.model.validator;

import org.joda.time.DateTime;
import org.joda.time.Hours;

/**
 * @author Salvia Vito
 */

public interface IValidatorPastoFattura extends IValidatorFattura<IValidatorPastoFattura> {

	int getMaxNumOccurrence();

	public class ValidatorPastoFattura extends AbstractValidatorFattura<IValidatorPastoFattura>
			implements IValidatorPastoFattura {

		protected int maxNumOccurrence = 0;
		private InitialStep initialStep;
		private Step1_Italia s1_italia;
		private Step1_Estera s1_estera;
		private FinalStep finalStep;

		/**
		 * 
		 */
		protected ValidatorPastoFattura() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public IValidatorPastoFattura self() {
			return this;
		}

		/**
		 * @return the maxNumOccurrence
		 */
		public int getMaxNumOccurrence() {
			return maxNumOccurrence;
		}

		public static ValidatorPastoFattura getValidatorPastoFattura() {
			return new ValidatorPastoFattura();
		}

		/**
		 * @throws Exception
		 */
		@Override
		protected void initialize() throws Exception {
			this.initialStep = new InitialStep();
			finalStep = new FinalStep();
			StepTipoMissione stepTipoMissione = new StepTipoMissione();

			// ITALIA
			this.s1_italia = new Step1_Italia();
			Step2_Italia s2_italia = new Step2_Italia();
			Step3_Italia s3_italia = new Step3_Italia();
			s1_italia.setNextValidator(s2_italia);
			s2_italia.setNextValidator(s3_italia);
			s3_italia.setNextValidator(finalStep);
			// ESTERA
			this.s1_estera = new Step1_Estera();
			Step2_Estera s2_estera = new Step2_Estera();
			Step3_Estera s3_estera = new Step3_Estera();
			Step4_Estera s4_estera = new Step4_Estera();
			Step5_Estera s5_estera = new Step5_Estera();
			this.s1_estera.setNextValidator(s2_estera);
			s2_estera.setNextValidator(s3_estera);
			s3_estera.setNextValidator(s4_estera);
			s4_estera.setNextValidator(s5_estera);
			s5_estera.setNextValidator(finalStep);
			stepTipoMissione.setNextValidator(initialStep);
			stepTipoMissione.check();
		}

		// Se data inizio e data fine nello stesso giorno
		class StepTipoMissione extends IStepValidator.AbstractStepValidator {

			/**
			 * @param validator
			 */
			@Override
			public void setNextValidator(IStepValidator validator) {
				this.nextValidator = validator;
			}

			/**
			 * @return
			 */
			@Override
			public void check() throws Exception {
				initialStep.setNextValidator(!missione.isMissioneEstera() ? s1_italia : s1_estera);
				initialStep.check();
			}

		}

		// Se data inizio e data fine nello stesso giorno
		class Step1_Italia extends IStepValidator.AbstractStepValidator {

			/**
			 * @param validator
			 */
			@Override
			public void setNextValidator(IStepValidator validator) {
				this.nextValidator = validator;
			}

			/**
			 * @return
			 */
			@Override
			public void check() throws Exception {
				if (missione.getDatiPeriodoMissione().getInizioMissione().getYear() == missione.getDatiPeriodoMissione()
						.getFineMissione().getYear()
						&& missione.getDatiPeriodoMissione().getInizioMissione().getDayOfYear() == missione
								.getDatiPeriodoMissione().getFineMissione().getDayOfYear()) {
					int hours = Hours.hoursBetween(missione.getDatiPeriodoMissione().getInizioMissione(),
							missione.getDatiPeriodoMissione().getFineMissione()).getHours();
					maxNumOccurrence = checkOccorrenze(hours);
					finalStep.check();
				} 
				else
					this.nextValidator.check();
			}

		}

		// Se data della fattura nello stesso giorno della data inizio
		class Step2_Italia extends IStepValidator.AbstractStepValidator {

			/**
			 * @param validator
			 */
			@Override
			public void setNextValidator(IStepValidator validator) {
				this.nextValidator = validator;
			}

			/**
			 * @return
			 */
			@Override
			public void check() throws Exception {
				if (missione.getDatiPeriodoMissione().getInizioMissione().getYear() == dataFattura.getYear() && missione
						.getDatiPeriodoMissione().getInizioMissione().getDayOfYear() == dataFattura.getDayOfYear()) {
					int hours = Hours.hoursBetween(missione.getDatiPeriodoMissione().getInizioMissione(),
							new DateTime(missione.getDatiPeriodoMissione().getInizioMissione().getYear(),
									missione.getDatiPeriodoMissione().getInizioMissione().getMonthOfYear(),
									missione.getDatiPeriodoMissione().getInizioMissione().getDayOfMonth(), 23, 59))
							.getHours();
					maxNumOccurrence = checkOccorrenze(hours);
					finalStep.check();
				} 
				else
					this.nextValidator.check();
			}

		}

		// Se data della fattura nello stesso giorno della data Fine
		class Step3_Italia extends IStepValidator.AbstractStepValidator {

			/**
			 * @param validator
			 */
			@Override
			public void setNextValidator(IStepValidator validator) {
				this.nextValidator = validator;
			}

			/**
			 * @return
			 */
			@Override
			public void check() throws Exception {
				if (missione.getDatiPeriodoMissione().getFineMissione().getYear() == dataFattura.getYear() && missione
						.getDatiPeriodoMissione().getFineMissione().getDayOfYear() == dataFattura.getDayOfYear()) {
					int hours = Hours.hoursBetween(
							new DateTime(missione.getDatiPeriodoMissione().getFineMissione().getYear(),
									missione.getDatiPeriodoMissione().getFineMissione().getMonthOfYear(),
									missione.getDatiPeriodoMissione().getFineMissione().getDayOfMonth(), 0, 0),
							missione.getDatiPeriodoMissione().getFineMissione()).getHours();
					maxNumOccurrence = checkOccorrenze(hours);
					finalStep.check();
				} else
					maxNumOccurrence = 2;
				nextValidator.check();
			}

		}

		// Se la fattura è nel periodo estero
		class Step1_Estera extends IStepValidator.AbstractStepValidator {

			/**
			 * @param validator
			 */
			@Override
			public void setNextValidator(IStepValidator validator) {
				this.nextValidator = validator;
			}

			/**
			 * @return
			 */
			@Override
			public void check() throws Exception {
				if (dataFattura.isAfter(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata())
						&& dataFattura
								.isBefore(missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno())) {
					maxNumOccurrence = 2;
					finalStep.check();
				} else
					this.nextValidator.check();
			}

		}

		// Se data della fattura compresa tra data inizio e data frontiera
		// andata e date inizio missione e data andata frontiera sono in giorni
		// differenti
		class Step2_Estera extends IStepValidator.AbstractStepValidator {

			/**
			 * @param validator
			 */
			@Override
			public void setNextValidator(IStepValidator validator) {
				this.nextValidator = validator;
			}

			/**
			 * @return
			 */
			@Override
			public void check() throws Exception {
				if (dataFattura.isAfter(missione.getDatiPeriodoMissione().getInizioMissione())
						&& dataFattura.isBefore(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata())
						&& !checkInThesameDay(missione.getDatiPeriodoMissione().getInizioMissione(),
								missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata())) {

					DateTime d = missione.getDatiPeriodoMissione().getInizioMissione().plusDays(1);
					int hours = Hours.hoursBetween(missione.getDatiPeriodoMissione().getInizioMissione(),
							new DateTime(d.getYear(), d.getMonthOfYear(), d.getDayOfMonth(), 0, 0)).getHours();
					maxNumOccurrence = checkOccorrenze(hours);
					finalStep.check();
				} else
					this.nextValidator.check();
			}

		}

		// Se data della fattura compresa tra data inizio e data frontiera
		// andata e date inizio missione e data andata frontiera sono nello
		// stesso giorno
		class Step3_Estera extends IStepValidator.AbstractStepValidator {

			/**
			 * @param validator
			 */
			@Override
			public void setNextValidator(IStepValidator validator) {
				this.nextValidator = validator;
			}

			/**
			 * @return
			 */
			@Override
			public void check() throws Exception {
				if (dataFattura.isAfter(missione.getDatiPeriodoMissione().getInizioMissione())
						&& dataFattura.isBefore(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata())
						&& checkInThesameDay(missione.getDatiPeriodoMissione().getInizioMissione(),
								missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata())) {
					int hours = Hours.hoursBetween(missione.getDatiPeriodoMissione().getInizioMissione(),
							missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata()).getHours();
					maxNumOccurrence = checkOccorrenze(hours);
					finalStep.check();
				} else
					this.nextValidator.check();
			}

		}

		// Se data della fattura compresa tra data Fine e data frontiera ritorno
		// e data ritorno e data ritorno frontiera sono in gg differenti
		class Step4_Estera extends IStepValidator.AbstractStepValidator {

			/**
			 * @param validator
			 */
			@Override
			public void setNextValidator(IStepValidator validator) {
				this.nextValidator = validator;
			}

			/**
			 * @return
			 */
			@Override
			public void check() throws Exception {
				if (dataFattura.isAfter(missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno())
						&& dataFattura.isBefore(missione.getDatiPeriodoMissione().getFineMissione())
						&& !checkInThesameDay(missione.getDatiPeriodoMissione().getFineMissione(),
								missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno())) {
					DateTime d = new DateTime(missione.getDatiPeriodoMissione().getFineMissione().getYear(),
							missione.getDatiPeriodoMissione().getFineMissione().getMonthOfYear(),
							missione.getDatiPeriodoMissione().getFineMissione().getDayOfMonth(), 0, 0);

					int hours = Hours.hoursBetween(d, missione.getDatiPeriodoMissione().getFineMissione()).getHours();
					maxNumOccurrence = checkOccorrenze(hours);
					finalStep.check();
				} else
					this.nextValidator.check();
			}

		}

		// Se data della fattura compresa tra data Fine e data frontiera ritorno
		// e data ritorno e data ritorno frontiera sono nello stesso giorno
		class Step5_Estera extends IStepValidator.AbstractStepValidator {

			/**
			 * @param validator
			 */
			@Override
			public void setNextValidator(IStepValidator validator) {
				this.nextValidator = validator;
			}

			/**
			 * @return
			 */
			@Override
			public void check() throws Exception {
				if (dataFattura.isAfter(missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno())
						&& dataFattura.isBefore(missione.getDatiPeriodoMissione().getFineMissione())
						&& checkInThesameDay(missione.getDatiPeriodoMissione().getFineMissione(),
								missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno())) {

					int hours = Hours
							.hoursBetween(missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno(),
									missione.getDatiPeriodoMissione().getFineMissione())
							.getHours();
					maxNumOccurrence = checkOccorrenze(hours);
					finalStep.check();
				} else
					maxNumOccurrence = 2;
				nextValidator.check();
			}

		}

		class FinalStep extends IStepValidator.AbstractStepValidator {

			/**
			 * @throws Exception
			 */
			@Override
			public void check() throws Exception {
				int fatturaOccurence = missione.getRimborso()
						.getNumberOfFatturaInDay(
								new DateTime(dataFattura.getYear(), dataFattura.getMonthOfYear(),
										dataFattura.getDayOfMonth(), 0, 0),
								new DateTime(dataFattura.getYear(), dataFattura.getMonthOfYear(),
										dataFattura.getDayOfMonth(), 23, 59),
								tipologiaSpesa.getId())
						.size();
				if (getMaxNumOccurrence() <= fatturaOccurence
						&& !missione.getRimborso().getMappaFattura().containsKey(fattura.getId())) {
					check = false;
					message = "error_occorrenze";
				} else
					check = true;
			}
		}

		/**
		 * Verifica che 2 date siano nello stesso giorno
		 * @param data1
		 * @param data2
		 * @return
		 */
		private boolean checkInThesameDay(DateTime data1, DateTime data2) {
			if (data1.getYear() == data2.getYear() && data1.getDayOfYear() == data2.getDayOfYear())
				return true;
			return false;
		}

		/**
		 * Ritorna il numero di occorrenze in base alle ore
		 * @param hours
		 * @return
		 */
		protected int checkOccorrenze(int hours) {
			if (hours > 4 && hours <= 12)
				return 1;
			else if (hours > 12)
				return 2;
			else
				return 0;
		}

	}
}
