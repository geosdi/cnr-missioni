package it.cnr.missioni.dashboard.component.wizard.missione;

import org.joda.time.DateTime;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiPeriodoMissione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class DatiPeriodoMissioneStep implements WizardStep {

	private DateField inizioMissioneField;
	private DateField fineMissioneField;
	private BeanFieldGroup<DatiPeriodoMissione> fieldGroup;
	private HorizontalLayout mainLayout;;

	private DatiPeriodoMissione datiPeriodoMissione;
	private Missione missione;

	public String getCaption() {
		return "Step 5";
	}

	public DatiPeriodoMissioneStep(DatiPeriodoMissione datiPeriodoMissione,Missione missione) {
		this.datiPeriodoMissione = datiPeriodoMissione;
		this.missione = missione;

	}

	public Component getContent() {
		return buildPanel();
	}

	public void bindFieldGroup() {

		fieldGroup = new BeanFieldGroup<DatiPeriodoMissione>(DatiPeriodoMissione.class);
		fieldGroup.setItemDataSource(datiPeriodoMissione);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);

		inizioMissioneField = (DateField) fieldGroup.buildAndBind("Inizio Missione", "inizioMissione");
//		inizioMissioneField.setResolution(Resolution.MINUTE);
		
		fineMissioneField = (DateField) fieldGroup.buildAndBind("Fine Missione", "fineMissione");

		fineMissioneField.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				DateTime data = (DateTime) value;
				if (inizioMissioneField.getValue() != null && inizioMissioneField.getValue() != null
						&& data.isBefore(inizioMissioneField.getValue().getTime()))
					throw new InvalidValueException(Utility.getMessage("data_error"));
			}
		});

	}

	private Component buildPanel() {
		mainLayout = new HorizontalLayout();
		mainLayout.setCaption("Inizio\\Fine");
		mainLayout.setIcon(FontAwesome.CALENDAR);
		mainLayout.setWidth(100.0f, Unit.PERCENTAGE);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		mainLayout.addComponent(details);
		mainLayout.setExpandRatio(details, 1);

		details.addComponent(inizioMissioneField);
		details.addComponent(fineMissioneField);

		return mainLayout;
	}

	public boolean onAdvance() {
		try {
			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			fieldGroup.commit();
			
			BeanItem<DatiPeriodoMissione> beanItem = (BeanItem<DatiPeriodoMissione>)fieldGroup.getItemDataSource();
			DatiPeriodoMissione new_datiPeriodoMissione = beanItem.getBean();		
			missione.setDatiPeriodoMissione(new_datiPeriodoMissione);
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
