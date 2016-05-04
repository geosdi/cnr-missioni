package it.cnr.missioni.dashboard.utility;

import java.io.Serializable;
import java.util.Comparator;

public class CaseInsensitivePropertyComparator implements 
Comparator<Object>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8845080404843918802L;

	@Override
	public int compare(Object o1, Object o2) {
		return ((String)o1).compareToIgnoreCase((String)o2);
	}

}
