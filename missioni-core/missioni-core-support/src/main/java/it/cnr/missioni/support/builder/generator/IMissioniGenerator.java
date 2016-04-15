package it.cnr.missioni.support.builder.generator;

/**
 * @author Salvia Vito
 */
public interface IMissioniGenerator<Generator extends IMissioniGenerator> {

	/**
	 * 
	 * @return {@link String}
	 */
	String build();

	default String getGeneratorName() {
		return getClass().getSimpleName();
	}

	abstract class AbstracMissioniGenerator<Generator extends IMissioniGenerator>
			implements IMissioniGenerator<Generator> {

		abstract Generator self();

		public abstract String build();

		public String toString() {
			return getGeneratorName();
		}

	}

}
