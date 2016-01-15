package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiMissioneEstera;
import it.cnr.missioni.model.missione.DatiPeriodoMissione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;

/**
 * @author Salvia Vito
 */
public class DatiMissioneEsteraStep implements WizardStep {

	private ComboBox trattamentoMissioneEsteraField;
	private DateField attraversamentoFrontieraAndataField;
	private DateField attraversamentoFrontieraRitornoField;

	private BeanFieldGroup<DatiMissioneEstera> fieldGroup;
	private HorizontalLayout mainLayout;;

	private DatiMissioneEstera datiMissioneEstera;
	private Missione missione;

	public String getCaption() {
		return "Missione Estera";
	}

	public DatiMissioneEsteraStep(DatiMissioneEstera datiMissioneEstera, Missione missione) {
		this.datiMissioneEstera = datiMissioneEstera;
		this.missione = missione;

	}

	public Component getContent() {
		return buildPanel();
	}

	public void bindFieldGroup() {

		fieldGroup = new BeanFieldGroup<DatiMissioneEstera>(DatiMissioneEstera.class);
		fieldGroup.setItemDataSource(datiMissioneEstera);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);

		trattamentoMissioneEsteraField = (ComboBox) fieldGroup.buildAndBind("Trattamento Rimborso",
				"trattamentoMissioneEsteraEnum", ComboBox.class);
		attraversamentoFrontieraAndataField = (DateField) fieldGroup.buildAndBind("Attraversamento Frontiera Andata",
				"attraversamentoFrontieraAndata");
		attraversamentoFrontieraRitornoField = (DateField) fieldGroup.buildAndBind("Attraversamento Frontiera Ritorno",
				"attraversamentoFrontieraRitorno");

		trattamentoMissioneEsteraField.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				TrattamentoMissioneEsteraEnum v = (TrattamentoMissioneEsteraEnum) value;
				if (missione.isMissioneEstera() && (v == null)) {
					throw new InvalidValueException(Utility.getMessage("checkbox_missione_error"));
				}

			}

		});

	}

	private Component buildPanel() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Missione Estera");
		root.setIcon(FontAwesome.PLANE);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		details.addComponent(trattamentoMissioneEsteraField);

		details.addComponent(attraversamentoFrontieraAndataField);
		details.addComponent(attraversamentoFrontieraRitornoField);

		return root;
	}

	public boolean onAdvance() {
		try {

			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			fieldGroup.commit();

			BeanItem<DatiMissioneEstera> beanItem = (BeanItem<DatiMissioneEstera>) fieldGroup.getItemDataSource();
			DatiMissioneEstera new_datiMissioneEstera = beanItem.getBean();
			missione.setDatiMissioneEstera(new_datiMissioneEstera);

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
