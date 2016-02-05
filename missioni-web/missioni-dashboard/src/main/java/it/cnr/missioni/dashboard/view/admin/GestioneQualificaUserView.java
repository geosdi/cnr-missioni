package it.cnr.missioni.dashboard.view.admin;

import java.util.Collection;

import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.admin.ElencoQualificaUserTable;
import it.cnr.missioni.dashboard.component.window.admin.QualificaUserWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.QualificaUserSearchBuilder;
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
			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedQualificaUser = (QualificaUser) itemClickEvent.getItemId();
				enableDisableButtons(true);
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

	protected Button createButtonNew() {
		buttonNew = buildButton("Aggiungi Qualifica", "Inserisce una nuova qualifica",FontAwesome.PLUS);
		buttonNew.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				QualificaUserWindow.open(new QualificaUser(), false);
			}

		});
		return buttonNew;
	}

	protected GridLayout addActionButtons() {
		GridLayout layout = new GridLayout(4, 1);
		layout.setSpacing(true);
		buttonModifica = buildButton("Modifica", "Modifica",FontAwesome.PENCIL);

		buttonModifica.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				QualificaUserWindow.open(selectedQualificaUser, true);

			}

		});

		layout.addComponents(buttonModifica);

		enableDisableButtons(false);

		return layout;

	}

	protected void enableDisableButtons(boolean enabled) {
		this.buttonModifica.setEnabled(enabled);
	}

	/**
	 * 
	 */
	@Override
	protected void initialize() {
		if (qualificaUserStore != null)
			buildPagination(qualificaUserStore.getTotale());
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
				DashboardEventBus.post(new DashboardEvent.TableQualificaUserUpdatedEvent(qualificaUserStore));
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

}