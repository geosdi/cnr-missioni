package it.cnr.missioni.dashboard.utility;

import java.util.Comparator;
import java.util.List;

import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.model.user.User;

public interface IComparator<E> extends Comparator<E>{

	class QualificaComparator implements IComparator<QualificaUser>{

		@Override
		public int compare(QualificaUser o1, QualificaUser o2) {
			return o1.getValue().compareToIgnoreCase(o2.getValue());
		}
		
	}
	
	class UserComparator implements IComparator<User>{

		private List<Comparator<User>> listComparators;
		
		@Override
		public int compare(User o1, User o2) {
			Comparator<User> c = (p, o) ->
		    p.getAnagrafica().getCognome().compareToIgnoreCase(o.getAnagrafica().getCognome());
		    c.thenComparing((p,o)->p.getAnagrafica().getNome().compareToIgnoreCase(o.getAnagrafica().getNome()));
			return c.compare(o1, o2);
		}
		
	}
	
}
