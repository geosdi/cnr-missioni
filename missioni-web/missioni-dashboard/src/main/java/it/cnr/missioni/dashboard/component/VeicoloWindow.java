package it.cnr.missioni.dashboard.component;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.action.UpdateUserAction;
import it.cnr.missioni.dashboard.action.VeicoloAction;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;

@SuppressWarnings("serial")
public class VeicoloWindow extends Window {

	public static final String ID = "veicolowindow";

	private final BeanFieldGroup<Veicolo> fieldGroup;

	private TextField tipoField;
	private TextField targaField;
	private TextField cartaCircolazioneField;
	private TextField polizzaAssicurativaField;
	
	private boolean modifica;
	
	private Veicolo veicolo;
	private final User user =  (User)VaadinSession.getCurrent().getAttribute(User.class.getName());

	private VeicoloWindow(final Veicolo veicolo,boolean modifica) {

		this.modifica = modifica;
		
		addStyleName("profile-window");
		setId(ID);
		Responsive.makeResponsive(this);

		setModal(true);
		setCloseShortcut(KeyCode.ESCAPE, null);
		setResizable(false);
		setClosable(true);
		setHeight(90.0f, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(new MarginInfo(true, false, false, false));
		setContent(content);

		TabSheet detailsWrapper = new TabSheet();
		detailsWrapper.setSizeFull();
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		content.addComponent(detailsWrapper);
		content.setExpandRatio(detailsWrapper, 1f);

		fieldGroup = new BeanFieldGroup<Veicolo>(Veicolo.class);
		fieldGroup.setItemDataSource(veicolo);
		fieldGroup.setBuffered(true);

		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);
		detailsWrapper.addComponent(buildAnagraficaTab());

		content.addComponent(buildFooter());

	}

	private Component buildAnagraficaTab() {
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
		
		if(modifica)
			this.veicolo = user.getMappaVeicolo().get(targaField.getValue());
		targaField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				String targa = (String) value;


				if(user.getMappaVeicolo().containsKey(targa) && !modifica){
					throw new InvalidValueException(Utility.getMessage("targa_present"));
				}
				
				if(modifica){
					if(user.getMappaVeicolo().containsKey(targa) && !veicolo.getTarga().equals(targa))
						throw new InvalidValueException(Utility.getMessage("targa_present"));
				}

			}

		});
		

		return root;
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

					targaField.validate();
					fieldGroup.commit();

					BeanItem<Veicolo> beanItem = (BeanItem<Veicolo>) fieldGroup.getItemDataSource();
					Veicolo veicolo = beanItem.getBean();
					
					DashboardEventBus.post(new VeicoloAction(veicolo,modifica));
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

	public static void open(final Veicolo veicolo,boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new VeicoloWindow(veicolo,modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
