package it.cnr.missioni.dashboard.component.window.admin;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.admin.VeicoloCNRAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.VeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;

public class VeicoloCNRWindow extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2904991157664896304L;

	public static final String ID = "veicoloCNRwindow";

	private final BeanFieldGroup<VeicoloCNR> fieldGroup;

	private TextField tipoField;
	private TextField targaField;
	private TextField cartaCircolazioneField;
	private TextField polizzaAssicurativaField;
	private ComboBox statoField;

	private boolean modifica;

	private final VeicoloCNR veicoloCNR;

	private VeicoloCNRWindow(final VeicoloCNR veicoloCNR, boolean modifica) {
		super();
		this.veicoloCNR = veicoloCNR;
		this.modifica = modifica;
		setId(ID);
		fieldGroup = new BeanFieldGroup<VeicoloCNR>(VeicoloCNR.class);
		build();
		buildFieldGroup();
		detailsWrapper.addComponent(buildVeicoloCNRTab());
		content.addComponent(buildFooter());

	}

	private void buildFieldGroup() {
		fieldGroup.setItemDataSource(this.veicoloCNR);
		fieldGroup.setBuffered(true);

		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);
	}

	private Component buildVeicoloCNRTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Veicolo");
		root.setIcon(FontAwesome.CAR);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		tipoField = (TextField) fieldGroup.buildAndBind("Tipo", "tipo");
		details.addComponent(tipoField);
		targaField = (TextField) fieldGroup.buildAndBind("Targa", "targa");
		details.addComponent(targaField);

		cartaCircolazioneField = (TextField) fieldGroup.buildAndBind("Carta Circolazione", "cartaCircolazione");
		details.addComponent(cartaCircolazioneField);
		polizzaAssicurativaField = (TextField) fieldGroup.buildAndBind("Polizza Assicurativa", "polizzaAssicurativa");
		details.addComponent(polizzaAssicurativaField);

		statoField = (ComboBox) fieldGroup.buildAndBind("Stato", "stato", ComboBox.class);
		details.addComponent(statoField);

		addValidator();
		return root;
	}

	private void addValidator() {

		targaField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null) {
					VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder
							.getVeicoloCNRSearchBuilder().withTarga(targaField.getValue());
					if (modifica)
						veicoloCNRSearchBuilder.withId(veicoloCNR.getId());
					VeicoloCNRStore veicoloCNRStore = null;
					try {
						veicoloCNRStore = ClientConnector.getVeicoloCNR(veicoloCNRSearchBuilder);
					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
					if (veicoloCNRStore != null)
						throw new InvalidValueException(Utility.getMessage("targa_present"));
				}

			}

		});

		polizzaAssicurativaField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null) {
					VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder
							.getVeicoloCNRSearchBuilder().withPolizzaAssicurativa(polizzaAssicurativaField.getValue());
					if (modifica)
						veicoloCNRSearchBuilder.withId(veicoloCNR.getId());
					VeicoloCNRStore veicoloCNRStore = null;
					try {
						veicoloCNRStore = ClientConnector.getVeicoloCNR(veicoloCNRSearchBuilder);
					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
					if (veicoloCNRStore != null)
						throw new InvalidValueException(Utility.getMessage("polizza_present"));
				}

			}

		});

		cartaCircolazioneField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null) {
					VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder
							.getVeicoloCNRSearchBuilder().withCartaCircolazione(cartaCircolazioneField.getValue());
					if (modifica)
						veicoloCNRSearchBuilder.withId(veicoloCNR.getId());
					VeicoloCNRStore veicoloCNRStore = null;
					try {
						veicoloCNRStore = ClientConnector.getVeicoloCNR(veicoloCNRSearchBuilder);
					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
					if (veicoloCNRStore != null)
						throw new InvalidValueException(Utility.getMessage("carta_circolazione_present"));
				}
			}

		});

	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				try {

					for (Field<?> f : fieldGroup.getFields()) {
						((AbstractField<?>) f).setValidationVisible(true);
					}

					// targaField.validate();
					fieldGroup.commit();

					BeanItem<VeicoloCNR> beanItem = (BeanItem<VeicoloCNR>) fieldGroup.getItemDataSource();
					VeicoloCNR new_veicoloCNR = beanItem.getBean();

					DashboardEventBus.post(new VeicoloCNRAction(new_veicoloCNR, modifica));
					close();

				} catch (InvalidValueException | CommitException e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();
		footer.addComponent(ok);
		footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
		return footer;
	}

	public static void open(final VeicoloCNR veicoloCNR, boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new VeicoloCNRWindow(veicoloCNR, modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
