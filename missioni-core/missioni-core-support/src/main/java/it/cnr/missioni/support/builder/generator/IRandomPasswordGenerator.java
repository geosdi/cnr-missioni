package it.cnr.missioni.support.builder.generator;

import java.security.SecureRandom;

import com.google.common.base.Preconditions;

public interface IRandomPasswordGenerator extends IMissioniGenerator<IRandomPasswordGenerator> {

	/**
	 * 
	 * @param password
	 * @return {@link Generator}
	 */
	IRandomPasswordGenerator withLenght(Integer lenght);

	/**
	 * 
	 * @param password
	 * @return {@link Generator}
	 */
	IRandomPasswordGenerator withSeed(byte[] seed);

	class RandomPasswordGenerator extends IMissioniGenerator.AbstracMissioniGenerator<IRandomPasswordGenerator>
			implements IRandomPasswordGenerator {

		protected static final char[] values = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9' };
		//
		protected Integer length;
		protected byte[] seed;
		protected SecureRandom random;

		private RandomPasswordGenerator() {
		}

		public static RandomPasswordGenerator getRandomPasswordGenerator() {
			return new RandomPasswordGenerator();
		}

		public IRandomPasswordGenerator withLenght(Integer length) {
			this.length = length;
			return self();
		}

		public IRandomPasswordGenerator withSeed(byte[] seed) {
			this.seed = seed;
			return self();
		}

		protected void checkArguments() {
			Preconditions.checkArgument((this.length != null && this.length > 5),
					"Il Paramentro Length non può essere " + "nullo o minore di 5 caratteri");
			Preconditions.checkArgument((this.seed != null && this.seed.length > 0),
					"Il Paramentro Seed non può essere " + "nullo o un'array vuoto");
		}

		@Override
		IRandomPasswordGenerator self() {
			return this;
		}

		@Override
		public String build() {
			checkArguments();
			this.random = new SecureRandom(this.seed);
			String out = "";
			for (int i = 0; i < length; i++) {
				int idx = random.nextInt(values.length);
				out += values[idx];
			}
			return out;
		}

	}

}
