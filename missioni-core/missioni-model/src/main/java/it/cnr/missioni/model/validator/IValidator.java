package it.cnr.missioni.model.validator;

/**
 * @author Salvia Vito
 */
public interface IValidator {

	void setNextValidator(IValidator validator);
	
	void check() throws Exception;
	
	abstract class AbstractValidator implements IValidator{
		
		protected IValidator nextValidator;

		/**
		 * @return the nextValidator
		 */
		public IValidator getNextValidator() {
			return nextValidator;
		}

		/**
		 * @param nextValidator 
		 */
		public void setNextValidator(IValidator nextValidator) {
			this.nextValidator = nextValidator;
		}
		
	}
	
}
