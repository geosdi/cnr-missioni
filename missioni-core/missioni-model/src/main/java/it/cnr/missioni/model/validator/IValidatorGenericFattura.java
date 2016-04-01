package it.cnr.missioni.model.validator;

/**
 * @author Salvia Vito
 */

public interface IValidatorGenericFattura extends IValidatorFattura<IValidatorGenericFattura> {

	public class GenericValidatorFattura extends AbstractValidatorFattura<IValidatorGenericFattura> implements IValidatorGenericFattura {

		protected GenericValidatorFattura(){}
		
		public static GenericValidatorFattura getGenericValidatorFattura(){
			return new GenericValidatorFattura();
		}
		
		/**
		 * @throws Exception
		 */
		@Override
		protected void initialize() throws Exception {
			InitialStep initialStep = new InitialStep();
			initialStep.check();
		}

		/**
		 * @return
		 */
		@Override
		protected IValidatorGenericFattura self() {
			return this;
		}
	}
}
