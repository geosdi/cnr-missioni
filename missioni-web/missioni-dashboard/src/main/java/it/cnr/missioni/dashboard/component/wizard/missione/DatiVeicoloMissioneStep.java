package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
public class DatiVeicoloMissioneStep implements WizardStep {

	boolean veicoloPrincipaleSetted = false;
	private TextArea motivazioneMezzoProprio;
	private BeanFieldGroup<Missione> fieldGroup;
	private HorizontalLayout mainLayout;
	private OptionGroup optionGroupMezzo;
	private Label labelVeicoloProprio;

	private static final String VEICOLO_CNR = "Veicolo CNR";
	private static final String VEICOLO_PROPRIO = "Veicolo Proprio";
	private static final String ALTRO = "ALTRO";

	private Missione missione;

	public String getCaption() {
		return "step 4";
	}

	public DatiVeicoloMissioneStep(Missione missione) {
		this.missione = missione;

	}

	public Component getContent() {
		return buildGeneraleTab();
	}

	public void bindFieldGroup() {

		optionGroupMezzo = new OptionGroup("Veicolo");
		optionGroupMezzo.addItems(VEICOLO_CNR,VEICOLO_PROPRIO,ALTRO);

		optionGroupMezzo.select(VEICOLO_CNR);

		fieldGroup = new BeanFieldGroup<Missione>(Missione.class);
		fieldGroup.setItemDataSource(missione);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);
		motivazioneMezzoProprio = (TextArea) fieldGroup.buildAndBind("Motivazione mezzo proprio",
				"motivazioniMezzoProprio", TextArea.class);

		motivazioneMezzoProprio.setReadOnly(true);

		//recupera il veicolo principale dell'user per visualizzarlo
		Veicolo v = DashboardUI.getCurrentUser().getVeicoloPrincipale();
		if (v != null)
			labelVeicoloProprio = new Label("Veicolo: "+v.getTipo()+" Targa: "+v.getTarga());
		labelVeicoloProprio.setVisible(false);

		addvalidator();

	}

	private void addvalidator() {
		optionGroupMezzo.addValidator(new Validator() {

			// Verifica se è presenta alemno un veicolo proprio per l'utente
			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value.equals(VEICOLO_PROPRIO)) {
					User user = (User) VaadinSession.getCurrent().getAttribute(User.class);
					user.getMappaVeicolo().values().forEach(veicolo -> {
						if (veicolo.isVeicoloPrincipale())
							veicoloPrincipaleSetted = true;
					});

					// se non è presnte un veicolo settato come principale
					if (!veicoloPrincipaleSetted)
						throw new InvalidValueException(Utility.getMessage("no_veicolo"));

				}

			}
		});

		// Se veicolo è proprio motivazione è obbligatoria
		motivazioneMezzoProprio.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (motivazioneMezzoProprio.getValue() == null && optionGroupMezzo.getValue().equals(VEICOLO_PROPRIO))
					throw new InvalidValueException(Utility.getMessage("altre_disposizioni_error"));

			}
		});

		optionGroupMezzo.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (optionGroupMezzo.getValue().equals(VEICOLO_CNR) || optionGroupMezzo.getValue().equals(ALTRO)) {
					motivazioneMezzoProprio.setValue(null);
					motivazioneMezzoProprio.setReadOnly(true);
					motivazioneMezzoProprio.setValidationVisible(false);
					labelVeicoloProprio.setVisible(false);
				}
				if (optionGroupMezzo.getValue().equals(VEICOLO_PROPRIO)) {
					motivazioneMezzoProprio.setReadOnly(false);
					labelVeicoloProprio.setVisible(true);

				}
			}
		});

	}

	private Component buildGeneraleTab() {

		mainLayout = new HorizontalLayout();
		mainLayout.setCaption("Dati Generali");
		mainLayout.setIcon(FontAwesome.SUITCASE);
		mainLayout.setWidth(100.0f, Unit.PERCENTAGE);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		mainLayout.addComponent(details);
		mainLayout.setExpandRatio(details, 1);
		details.addComponent(optionGroupMezzo);
		details.addComponent(labelVeicoloProprio);
		details.addComponent(motivazioneMezzoProprio);

		// details.addComponent(fieldDataInserimentoField);
		// details.addComponent(fieldDataLastModifiedField);

		return mainLayout;
	}

	public boolean onAdvance() {
		try {
			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			fieldGroup.commit();

			BeanItem<Missione> beanItem = (BeanItem<Missione>) fieldGroup.getItemDataSource();
			missione = beanItem.getBean();
			missione.setMezzoProprio(optionGroupMezzo.getValue().equals(VEICOLO_PROPRIO) ? true : false);

			return true;
		} catch (InvalidValueException | CommitException e) {
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
