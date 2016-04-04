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
import it.cnr.missioni.dashboard.component.table.admin.ElencoUserTable;
import it.cnr.missioni.dashboard.component.window.UserWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableUserUpdatedEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public class GestioneUserAdminView extends GestioneTemplateView<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 93612577398232810L;

	private ElencoUserTable elencoUserTable;
	private User selectedUser;

	private IUserSearchBuilder userSearchBuilder;
	private UserStore userStore;

	public GestioneUserAdminView() {
		super();
	}
	
	protected void inizialize() {
		this.userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder();
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();


		this.elencoUserTable = new ElencoUserTable();
		this.elencoUserTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1826612705343869714L;

			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedUser = (User) itemClickEvent.getItemId();
				enableButtons();
			}
		});

		try {
			userStore = ClientConnector.getUser(userSearchBuilder);
			this.elencoUserTable.aggiornaTable(userStore);

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		HorizontalLayout layoutSelectPage = new HorizontalLayout();


		layoutSelectPage.setMargin(true);
		v.addComponent(this.elencoUserTable);
		v.addComponent(layoutSelectPage);
		v.setComponentAlignment(elencoUserTable,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
		v.setComponentAlignment(layoutSelectPage,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));

		return v;
	}


	@Override
	public void enter(final ViewChangeEvent event) {

	}

	protected Button createButtonSearch() {
		
		buttonCerca = buildButton("", "Ricerca full text",FontAwesome.SEARCH);
		buttonCerca.addClickListener(new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2401290057230222368L;

			@Override
			public void buttonClick(final ClickEvent event) {

				try {
					userSearchBuilder.withMultiMatch(multiMatchField.getValue());
					userStore = ClientConnector.getUser(userSearchBuilder);
					elencoUserTable.aggiornaTable(userStore);
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
			}

		});
		return buttonCerca;
	}

	protected HorizontalLayout addActionButtons() {
		HorizontalLayout layout = new HorizontalLayout();
		buttonDettagli = buildButton("Dettagli", "Visualizza i dettagli dell'user",FontAwesome.EDIT);

		buttonDettagli.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7939532417002125241L;

			@Override
			public void buttonClick(ClickEvent event) {
				UserWindow.open(selectedUser,true,true, true);

			}

		});

		layout.addComponents(buttonDettagli);

		disableButtons();

		return layout;

	}


	/**
	 * 
	 */
	protected void initPagination() {
		buildPagination(userStore != null ? userStore.getTotale() : 0);
		addListenerPagination();
	}

	/**
	 * 
	 * Aggiunge il listener alla paginazione
	 * 
	 */
	protected void addListenerPagination() {

		pagingComponent.addListener(new LazyPagingComponentListener<User>(itemsArea) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2537782371968033967L;

			@Override
			protected Collection<User> getItemsList(int startIndex, int endIndex) {

				try {
					userStore = ClientConnector.getUser(userSearchBuilder.withFrom(startIndex));
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				elencoUserTable.aggiornaTable(userStore);
				return userStore != null ? userStore.getUsers() : null;

			}

			@Override
			protected Component displayItem(int index, User item) {
				return new Label(item.toString());
			}
		});
	}


	
	/**
	 * 
	 * Aggiorna la table e la paginazione a seguito di un inserimento o una modifica
	 * 
	 */
	@Subscribe
	public void aggiornaTableUser(final TableUserUpdatedEvent event) {

		try {
			this.userStore = ClientConnector.getUser(this.userSearchBuilder);
			elencoUserTable.aggiornaTable(this.userStore);
			buildPagination(userStore.getTotale());
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
		this.buttonDettagli.setEnabled(true);
		
	}

	/**
	 * 
	 */
	@Override
	protected void disableButtons() {
		this.buttonDettagli.setEnabled(false);
		
	}

}