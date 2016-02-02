package it.cnr.missioni.dashboard.view.admin;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.admin.ElencoQualificaUserTable;
import it.cnr.missioni.dashboard.component.table.admin.ElencoVeicoliCNRTable;
import it.cnr.missioni.dashboard.component.window.admin.QualificaUserWindow;
import it.cnr.missioni.dashboard.component.window.admin.VeicoloCNRWindow;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.QualificaUserSearchBuilder;
import it.cnr.missioni.el.model.search.builder.VeicoloCNRSearchBuilder;
import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.rest.api.response.qualificaUser.QualificaUserStore;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;

/**
 * @author Salvia Vito
 */
public class GestioneQualificaUserView extends GestioneTemplateView implements View {

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
	private Button buttonModifica;
	private QualificaUser selectedQualificaUser;
	private QualificaUserStore qualificaUserStore;


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

		try {
			QualificaUserSearchBuilder qualificaUserSearchBuilder = QualificaUserSearchBuilder.getQualificaUserSearchBuilder();
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
		final Button buttonNew = new Button("Aggiungi Qualifica");
		buttonNew.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonNew.setIcon(FontAwesome.PLUS);
		buttonNew.setDescription("Inserisce una nuova qualifica");
		buttonNew.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		buttonNew.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				QualificaUserWindow.open(new QualificaUser(), false);
			}

		});
		return buttonNew;
	}

	protected GridLayout buildButtons() {
		GridLayout layout = new GridLayout(4, 1);
		layout.setSpacing(true);
		buttonModifica = new Button("Modifica");
		buttonModifica.setDescription("Modifica");
		buttonModifica.setIcon(FontAwesome.PENCIL);
		buttonModifica.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonModifica.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

		buttonModifica.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				QualificaUserWindow.open(selectedQualificaUser, false);

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
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	@Override
	protected void buildComboPage() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return
	 */
	@Override
	protected Button createButtonSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	protected  Component buildFilter(){
		return null;
	}

}