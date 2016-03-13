package it.cnr.missioni.dashboard.view.admin;

import java.util.Collection;

import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

import com.google.common.eventbus.Subscribe;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.admin.ElencoQualificaUserTable;
import it.cnr.missioni.dashboard.component.window.admin.QualificaUserWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableQualificaUserUpdatedEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.QualificaUserSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.rest.api.response.qualificaUser.QualificaUserStore;

/**
 * @author Salvia Vito
 */
public class GestioneQualificaUserView extends GestioneTemplateView<QualificaUser> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6356780101217798393L;
	/**
	 * 
	 */
	/**
	 * 
	 */
	private ElencoQualificaUserTable elencoQualificaUserTable;
	private QualificaUser selectedQualificaUser;
	private QualificaUserStore qualificaUserStore;
	private QualificaUserSearchBuilder qualificaUserSearchBuilder;

	public GestioneQualificaUserView() {
		super();
	}
	
	protected void inizialize() {
		this.qualificaUserSearchBuilder = QualificaUserSearchBuilder.getQualificaUserSearchBuilder();
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoQualificaUserTable = new ElencoQualificaUserTable();
		this.qualificaUserSearchBuilder = QualificaUserSearchBuilder.getQualificaUserSearchBuilder();
		try {
			qualificaUserStore = ClientConnector.getQualificaUser(qualificaUserSearchBuilder);
			this.elencoQualificaUserTable.aggiornaTable(qualificaUserStore);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		this.elencoQualificaUserTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6096596473626952913L;

			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedQualificaUser = (QualificaUser) itemClickEvent.getItemId();
				enableButtons();
			}
		});

		v.addComponent(this.elencoQualificaUserTable);
		v.setComponentAlignment(elencoQualificaUserTable,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));

		return v;
	}

	@Override
	public void enter(final ViewChangeEvent event) {

	}



	protected HorizontalLayout addActionButtons() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		
		buttonNew = buildButton("Aggiungi Qualifica", "Inserisce una nuova qualifica", FontAwesome.PLUS);
		buttonNew.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 182973545327593771L;

			@Override
			public void buttonClick(ClickEvent event) {
				selectedQualificaUser = null;
				elencoQualificaUserTable.select(null);
				QualificaUserWindow.open(new QualificaUser(), true,true,false);
			}

		});
		
		buttonModifica = buildButton("Modifica", "Modifica", FontAwesome.PENCIL);

		buttonModifica.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7581719058706756833L;

			@Override
			public void buttonClick(ClickEvent event) {
				QualificaUserWindow.open(selectedQualificaUser,true,true, true);

			}

		});

		layout.addComponents(buttonNew,buttonModifica);

		disableButtons();

		return layout;

	}


	/**
	 * 
	 */
	@Override
	protected void initPagination() {
		buildPagination(qualificaUserStore != null ? qualificaUserStore.getTotale() : 0);
		addListenerPagination();

	}

	/**
	 * 
	 * Aggiunge il listener alla paginazione
	 * 
	 */
	protected void addListenerPagination() {

		pagingComponent.addListener(new LazyPagingComponentListener<QualificaUser>(itemsArea) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1524083863677631755L;

			@Override
			protected Collection<QualificaUser> getItemsList(int startIndex, int endIndex) {

				try {
					qualificaUserStore = ClientConnector
							.getQualificaUser(qualificaUserSearchBuilder.withFrom(startIndex));
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				elencoQualificaUserTable.aggiornaTable(qualificaUserStore);
				return qualificaUserStore != null ? qualificaUserStore.getQualificaUser() : null;

			}

			@Override
			protected Component displayItem(int index, QualificaUser item) {
				return new Label(item.toString());
			}
		});
	}

	/**
	 * @return
	 */
	@Override
	protected Button createButtonSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	protected Component buildFilter() {
		return null;
	}

	/**
	 * 
	 * Aggiorna la table e la paginazione a seguito di un inserimento o una
	 * modifica
	 * 
	 */
	@Subscribe
	public void aggiornaTableQualificaUser(final TableQualificaUserUpdatedEvent event) {

		try {
			this.qualificaUserStore = ClientConnector.getQualificaUser(this.qualificaUserSearchBuilder);
			elencoQualificaUserTable.aggiornaTable(this.qualificaUserStore);
			buildPagination(qualificaUserStore.getTotale());
			addListenerPagination();
			disableButtons();
			} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

	}

	/**
	 * 
	 */
	@Override
	protected void enableButtons() {
		this.buttonModifica.setEnabled(true);
		
	}

	/**
	 * 
	 */
	@Override
	protected void disableButtons() {
		this.buttonModifica.setEnabled(false);
		
	}

}