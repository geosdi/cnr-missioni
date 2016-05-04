package it.cnr.missioni.dashboard.utility;

import java.io.Serializable;
import java.util.Comparator;

import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;

public class CaseInsensitivePropertyEnumComparator implements 
Comparator<Object>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8845080404843918802L;

	@Override
	public int compare(Object o1, Object o2) {
		AreaGeograficaEnum a1 = (AreaGeograficaEnum)o1;
		AreaGeograficaEnum a2 = (AreaGeograficaEnum)o2;
		return a1.name().compareToIgnoreCase(a2.name());
	}

}
