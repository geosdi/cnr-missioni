package it.cnr.missioni.el.utility;

import java.util.ArrayList;
import java.util.List;

import it.cnr.missioni.model.configuration.QualificaUser;

/**
 * @author Salvia Vito
 */
public class QualificaFunction{


	public static List<QualificaUser> creaMassiveQualifica(){
		
		List<QualificaUser> listaQualificaUser = new ArrayList<QualificaUser>();
		
		QualificaUser qualificaUser = new QualificaUser();
		qualificaUser.setId("01");
		qualificaUser.setValue("Assegnista");
		listaQualificaUser.add(qualificaUser);

		qualificaUser = new QualificaUser();
		qualificaUser.setId("02");
		qualificaUser.setValue("Ricercatore");
		listaQualificaUser.add(qualificaUser);
		
		return listaQualificaUser;
	}


}
