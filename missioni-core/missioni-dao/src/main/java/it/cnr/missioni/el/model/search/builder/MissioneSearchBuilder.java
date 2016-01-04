package it.cnr.missioni.el.model.search.builder;

import java.io.Serializable;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.joda.time.DateTime;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.DateRangeSearch;
import it.cnr.missioni.el.model.search.ExactSearch;

/**
 * @author Salvia Vito
 */
public class MissioneSearchBuilder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4387150079550066147L;

	private BooleanModelSearch booleanModelSearch;
	private DateTime fromDataInserimento = null;
	private DateTime toDataInserimento = null;
	private String idUser = null;
	private String stato = null;
	private Long numeroOrdineRimborso = null;
	private DateTime fromDataRimbroso = null;
	private DateTime toDataRimbroso = null;
	private String idMissione = null;
	
	public MissioneSearchBuilder(){}
	
	public MissioneSearchBuilder(String idMissione,String idUser,String stato, Long numeroOrdineRimborso){
		this.idUser = idUser;
		this.stato = stato;
		this.numeroOrdineRimborso = numeroOrdineRimborso;
		this.idMissione = idMissione;
	}

	public BoolQueryBuilder buildQuery() {
		booleanModelSearch = new BooleanModelSearch();
		if (fromDataInserimento != null || toDataInserimento != null)
			booleanModelSearch.getListaSearch().add(new DateRangeSearch(SearchConstants.MISSIONE_FIELD_DATA_INSERIMENTO,
					fromDataInserimento, toDataInserimento));
		if (idUser != null)
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MISSIONE_FIELD_ID_USER, idUser));
		if (stato != null)
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MISSIONE_FIELD_STATO, stato));
		if (numeroOrdineRimborso != null)
			booleanModelSearch.getListaSearch()
					.add(new ExactSearch(SearchConstants.MISSIONE_FIELD_RIMBORSO_NUMERO_ORDINE, numeroOrdineRimborso));
		if (fromDataRimbroso != null || toDataRimbroso != null)
			booleanModelSearch.getListaSearch().add(new DateRangeSearch(
					SearchConstants.MISSIONE_FIELD_RIMBORSO_DATA_RIMBORSO, fromDataRimbroso, toDataRimbroso));
		
		if (idMissione != null)
			booleanModelSearch.getListaSearch().add(new ExactSearch(SearchConstants.MISSIONE_FIELD_ID, idMissione));
		
		return booleanModelSearch.buildQuery();
	}

	/**
	 * @return the booleanModelSearch
	 */
	public BooleanModelSearch getBooleanModelSearch() {
		return booleanModelSearch;
	}

	/**
	 * @param booleanModelSearch
	 */
	public void setBooleanModelSearch(BooleanModelSearch booleanModelSearch) {
		this.booleanModelSearch = booleanModelSearch;
	}

	/**
	 * @return the fromDataInserimento
	 */
	public DateTime getFromDataInserimento() {
		return fromDataInserimento;
	}

	/**
	 * @param fromDataInserimento
	 */
	public void setFromDataInserimento(DateTime fromDataInserimento) {
		this.fromDataInserimento = fromDataInserimento;
	}

	/**
	 * @return the toDataInserimento
	 */
	public DateTime getToDataInserimento() {
		return toDataInserimento;
	}

	/**
	 * @param toDataInserimento
	 */
	public void setToDataInserimento(DateTime toDataInserimento) {
		this.toDataInserimento = toDataInserimento;
	}

	/**
	 * @return the idUser
	 */
	public String getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser
	 */
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the stato
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * @param stato
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}

	/**
	 * @return the numeroOrdineRimborso
	 */
	public Long getNumeroOrdineRimborso() {
		return numeroOrdineRimborso;
	}

	/**
	 * @param numeroOrdineRimborso
	 */
	public void setNumeroOrdineRimborso(Long numeroOrdineRimborso) {
		this.numeroOrdineRimborso = numeroOrdineRimborso;
	}

	/**
	 * @return the fromDataRimbroso
	 */
	public DateTime getFromDataRimbroso() {
		return fromDataRimbroso;
	}

	/**
	 * @param fromDataRimbroso
	 */
	public void setFromDataRimbroso(DateTime fromDataRimbroso) {
		this.fromDataRimbroso = fromDataRimbroso;
	}

	/**
	 * @return the toDataRimbroso
	 */
	public DateTime getToDataRimbroso() {
		return toDataRimbroso;
	}

	/**
	 * @param toDataRimbroso
	 */
	public void setToDataRimbroso(DateTime toDataRimbroso) {
		this.toDataRimbroso = toDataRimbroso;
	}

	/**
	 * @return the idMissione
	 */
	public String getIdMissione() {
		return idMissione;
	}

	/**
	 * @param idMissione 
	 */
	public void setIdMissione(String idMissione) {
		this.idMissione = idMissione;
	}

}
