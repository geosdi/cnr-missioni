package it.cnr.missioni.support.builder.generator;

import com.google.common.base.Preconditions;

public interface IMd5PasswordGenerator extends IMissioniGenerator<IMd5PasswordGenerator> {

	/**
	 * 
	 * @param password
	 * @return {@link IMd5PasswordGenerator}
	 */
	IMd5PasswordGenerator withPassword(String password);

	class Md5PasswordGenerator extends AbstracMissioniGenerator<IMd5PasswordGenerator>
			implements IMd5PasswordGenerator {

		private String password;

		private Md5PasswordGenerator() {
		}

		public static Md5PasswordGenerator getMd5PasswordGenerator() {
			return new Md5PasswordGenerator();
		}

		public Md5PasswordGenerator withPassword(String password) {
			this.password = password;
			return self();
		}

		@Override
		Md5PasswordGenerator self() {
			return this;
		}

		private void checkArguments() {
			Preconditions.checkArgument(this.password != null && this.password.length() > 0,
					"Il Paramentro Password " + "non può essere una stringa vuota o nulla");
		}

		@Override
		public String build() {
			checkArguments();
			String hashString = null;
			try {
				java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
				byte[] hash = digest.digest(password.getBytes());
				hashString = "";
				for (int i = 0; i < hash.length; i++) {
					hashString += Integer.toHexString((hash[i] & 0xFF) | 0x100).toLowerCase().substring(1, 3);
				}
			} catch (java.security.NoSuchAlgorithmException e) {
			}
			return hashString;
		}

	}

}
