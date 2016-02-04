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

import it.cnr.missioni.dashboard.action.admin.MassimaleAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

public class MassimaleWindow extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5414281952989114531L;

	public static final String ID = "massimalewindow";

	private final BeanFieldGroup<Massimale> fieldGroup;

	private TextField valueField;
	private ComboBox areagGeograficaField;
	private ComboBox livelloField;
	private TextField descrizioneField;
	private boolean modifica;

	private final Massimale massimale;

	private MassimaleWindow(final Massimale massimale, boolean modifica) {

		super();
		this.massimale = massimale;
		this.modifica = modifica;
		setId(ID);
		fieldGroup = new BeanFieldGroup<Massimale>(Massimale.class);
		build();
		buildFieldGroup();
		detailsWrapper.addComponent(buildAnagraficaTab());

		content.addComponent(buildFooter());

	}

	private void buildFieldGroup() {
		fieldGroup.setItemDataSource(this.massimale);
		fieldGroup.setBuffered(true);

		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);
	}

	private Component buildAnagraficaTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Massimale");
		root.setIcon(FontAwesome.EURO);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		valueField = (TextField) fieldGroup.buildAndBind("Importo", "value");
		details.addComponent(valueField);

		areagGeograficaField = (ComboBox) fieldGroup.buildAndBind("Area Geografica", "areaGeografica", ComboBox.class);
		details.addComponent(areagGeograficaField);

		livelloField = (ComboBox) fieldGroup.buildAndBind("Livello", "livello", ComboBox.class);
		details.addComponent(livelloField);

		descrizioneField = (TextField) fieldGroup.buildAndBind("Descrizione", "descrizione");
		details.addComponent(descrizioneField);

		addValidator();

		return root;
	}

	private void addValidator() {

		livelloField.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value != null && areagGeograficaField.getValue() != null) {

					MassimaleSearchBuilder massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder()
							.withAreaGeografica(areagGeograficaField.getValue().toString()).withLivello(((LivelloUserEnum)value).name());
					if (modifica)
						massimaleSearchBuilder.withNotId(massimale.getId());

					MassimaleStore massimaleStore = null;

					try {
						massimaleStore = ClientConnector.getMassimale(massimaleSearchBuilder);

					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}

					if (massimaleStore != null)
						throw new InvalidValueException(Utility.getMessage("livello_area_error"));

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

					BeanItem<Massimale> beanItem = (BeanItem<Massimale>) fieldGroup.getItemDataSource();
					Massimale massimale = beanItem.getBean();

					DashboardEventBus.post(new MassimaleAction(massimale, modifica));
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

	public static void open(final Massimale massimale, boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new MassimaleWindow(massimale, modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
