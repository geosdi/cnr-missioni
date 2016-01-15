package it.cnr.missioni.dashboard.component.wizard.rimborso;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
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
import it.cnr.missioni.model.rimborso.Rimborso;

/**
 * @author Salvia Vito
 */
public class DatiGeneraliRimborsoStep implements WizardStep {

	private TextField numeroOrdineField;
	private TextField avvisoPagamentoField;
	private TextField anticipazionePagamentoField;
	private TextField totaleField;
	private DateField fieldDataInserimentoField;
	private DateField fieldDataLastModifiedField;

	private BeanFieldGroup<Rimborso> fieldGroup;
	private HorizontalLayout mainLayout;;

	private Rimborso rimborso;

	public String getCaption() {
		return "Generale";
	}

	public DatiGeneraliRimborsoStep(Rimborso rimborso) {
		this.rimborso = rimborso;

	}

	public Component getContent() {
		return buildGeneraleTab();
	}

	public void bindFieldGroup() {
		fieldGroup = new BeanFieldGroup<Rimborso>(Rimborso.class);
		fieldGroup.setItemDataSource(rimborso);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);

		numeroOrdineField = (TextField) fieldGroup.buildAndBind("Numero Ordine", "numeroOrdine");
		avvisoPagamentoField = (TextField) fieldGroup.buildAndBind("Avviso Pagamento", "avvisoPagamento");
		anticipazionePagamentoField = (TextField) fieldGroup.buildAndBind("Anticipazione Pagamento", "anticipazionePagamento");
		totaleField = (TextField) fieldGroup.buildAndBind("Totale", "totale");

		fieldDataInserimentoField = (DateField) fieldGroup.buildAndBind("Data Inserimento", "dataRimborso");
		fieldDataInserimentoField.setReadOnly(true);
		fieldDataLastModifiedField = (DateField) fieldGroup.buildAndBind("Data ultima modifica", "dateLastModified");
		fieldDataLastModifiedField.setReadOnly(true);



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

		details.addComponent(numeroOrdineField);
		details.addComponent(avvisoPagamentoField);
		details.addComponent(anticipazionePagamentoField);
		details.addComponent(totaleField);
		totaleField.setReadOnly(true);
		details.addComponent(fieldDataInserimentoField);
		details.addComponent(fieldDataLastModifiedField);

		return mainLayout;
	}

	public boolean onAdvance() {
		try {
			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			fieldGroup.commit();
			
			BeanItem<Rimborso> beanItem = (BeanItem<Rimborso>)fieldGroup.getItemDataSource();
			rimborso = beanItem.getBean();

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
