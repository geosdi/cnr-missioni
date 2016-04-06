package it.cnr.missioni.model.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface ICNRMissionValidator<D extends Object, MESSAGE> {

    /**
     * <p>Return Null, if there are no errors.</p>
     *
     * @param pojo
     * @return {@link MESSAGE}
     * @throws Exception
     */
    MESSAGE validate(D pojo);

    /**
     * @return {@link String}
     */
    String getValidatorName();

    /**
     *
     */
    abstract class CNRMissionValidator<D extends Object> implements ICNRMissionValidator<D, String> {

        private static final Logger logger = LoggerFactory.getLogger(CNRMissionValidator.class);
        //
        private final Validator validator;

        public CNRMissionValidator() {
            this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        }

        /**
         * <p>Return Null, if there are no errors.</p>
         *
         * @param pojo
         * @return {@link String}
         * @throws Exception
         */
        @Override
        public String validate(D pojo) {
            logger.trace("################################ {} - Validating........ {}\n",
                    getValidatorName(), pojo);
            Set<ConstraintViolation<D>> constraintViolations = this.validator.validate(pojo);
            return constraintViolations.isEmpty() ? null : buildMessage(constraintViolations);
        }

        /**
         * @param constraintViolations
         * @return {@link StringBuilder}
         */
        protected String buildMessage(Set<ConstraintViolation<D>> constraintViolations) {
            StringBuilder stringBuilder = new StringBuilder();
            constraintViolations.forEach(C -> {
                stringBuilder.append(C.getMessage()).append("\n");
            });
            return stringBuilder.toString();
        }
    }
}
