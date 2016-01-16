package it.cnr.missioni.el.model.search;

/**
 * @author Salvia Vito
 */
public enum EnumBooleanType {
	
	MUST("must"),SHOULD("should"),MUST_NOT("must not");
	
	private final String type;
	
	EnumBooleanType(String type){
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	
	public static EnumBooleanType getEnumBooleanType(String type) {
		for (EnumBooleanType e : EnumBooleanType.values()) {
			if (e.getType().equals(type))
				return e;
		}
		return null;
	}
	
	

}
