package it.cnr.missioni.el.model.search;

import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class MissioneModelSearch extends AbstractModelSearch {

	private static final String DATA_INSERIMENTO = "missione.dataInserimento";
	private static final String ID_UTENTE = "missione.idUtente";
	private static final String STATO = "missione.stato";

	private DateTime from;
	private DateTime to;
	private String idUtente;
	private String stato;

	public MissioneModelSearch(DateTime from, DateTime to, String idUtente, String stato) {
		super();
		this.from = from;
		this.to = to;
		this.idUtente = idUtente;
		this.stato = stato;
		buildQuery();
	}

	public void buildQuery() {

		buildRangeDateQuery(DATA_INSERIMENTO, from, to);
		if (idUtente != null)
			buildExactValue(ID_UTENTE, idUtente);
		if (stato != null)
			buildExactValue(STATO, stato);
	}

}
