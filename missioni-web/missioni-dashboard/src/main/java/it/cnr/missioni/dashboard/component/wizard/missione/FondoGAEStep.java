package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public class FondoGAEStep implements WizardStep {

	private TextField fondoField;
	private TextField GAEField;
	private ComboBox listaResponsabiliGruppoField;

	private BeanFieldGroup<Missione> fieldGroup;
	private HorizontalLayout mainLayout;;

	private Missione missione;

	public String getCaption() {
		return "Step 3";
	}

	public FondoGAEStep(Missione missione) {
		this.missione = missione;

	}

	public Component getContent() {
		return buildGeneraleTab();
	}

	public void bindFieldGroup() {
		
		listaResponsabiliGruppoField = new ComboBox("Responsabile Gruppo");

		try{
			UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withAll(true).withResponsabileGruppo(true);
			
			UserStore userStore = ClientConnector.getUser(userSearchBuilder);
			
			if(userStore != null){
			
			userStore.getUsers().forEach(u -> {
				listaResponsabiliGruppoField.addItem(u.getId());
				listaResponsabiliGruppoField.setItemCaption(u.getId(),u.getAnagrafica().getCognome()+" " + u.getAnagrafica().getNome());
			});
			}
			
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		
		listaResponsabiliGruppoField.setValidationVisible(false);
		listaResponsabiliGruppoField.addBlurListener(new BlurListener() {

			@Override
			public void blur(BlurEvent event) {
				try {
					listaResponsabiliGruppoField.validate();
				} catch (Exception e) {
					listaResponsabiliGruppoField.setValidationVisible(true);
				}
			}

		});

		
		fieldGroup = new BeanFieldGroup<Missione>(Missione.class);
		fieldGroup.setItemDataSource(missione);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);
		fondoField = (TextField) fieldGroup.buildAndBind("Fondo", "fondo");
		GAEField = (TextField) fieldGroup.buildAndBind("GAE", "GAE");
		fieldGroup.bind(listaResponsabiliGruppoField, "responsabileGruppo");

	}

	private Component buildGeneraleTab() {

		mainLayout = new HorizontalLayout();
		mainLayout.setCaption("Fondo\\GAE");
		mainLayout.setIcon(FontAwesome.SUITCASE);
		mainLayout.setWidth(100.0f, Unit.PERCENTAGE);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		mainLayout.addComponent(details);
		mainLayout.setExpandRatio(details, 1);

		details.addComponent(fondoField);
		details.addComponent(GAEField);
		details.addComponent(listaResponsabiliGruppoField);
		return mainLayout;
	}

	public boolean onAdvance() {
		try {
			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			fondoField.validate();
			GAEField.validate();
			listaResponsabiliGruppoField.validate();
			
			BeanItem<Missione> beanItem = (BeanItem<Missione>)fieldGroup.getItemDataSource();
			missione = beanItem.getBean();
			missione.setGAE(GAEField.getValue());
			missione.setFondo(fondoField.getValue());
			missione.setResponsabileGruppo(listaResponsabiliGruppoField.getValue().toString());
			missione.setShortResponsabileGruppo(listaResponsabiliGruppoField.getItemCaption(listaResponsabiliGruppoField.getValue()));
			
			return true;
		} catch (InvalidValueException e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
					Type.ERROR_MESSAGE);
			return false;
		}
	}

	public boolean onBack() {
		return true;
	}

	public HorizontalLayout getMainLayout() {
		return mainLayout;
	}

}
