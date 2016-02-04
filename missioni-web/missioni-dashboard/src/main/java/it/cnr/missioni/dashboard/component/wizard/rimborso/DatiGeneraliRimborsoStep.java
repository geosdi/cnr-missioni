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
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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

	private TextField avvisoPagamentoField;
	private TextField anticipazionePagamentoField;
	private TextField totaleTAM;

	private BeanFieldGroup<Rimborso> fieldGroup;
	private HorizontalLayout mainLayout;;

	private Rimborso rimborso;
	private int days;

	public String getCaption() {
		return "Step 1";
	}

	public DatiGeneraliRimborsoStep(Rimborso rimborso,int days) {
		this.rimborso = rimborso;
		this.days = days;

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

		avvisoPagamentoField = (TextField) fieldGroup.buildAndBind("Avviso Pagamento", "avvisoPagamento");
		anticipazionePagamentoField = (TextField) fieldGroup.buildAndBind("Anticipazione Pagamento", "anticipazionePagamento");
		totaleTAM = (TextField) fieldGroup.buildAndBind("TAM", "totaleTAM");
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

		details.addComponent(avvisoPagamentoField);
		details.addComponent(anticipazionePagamentoField);
		details.addComponent(new Label("GG all'estero: "+this.days+"\tTot. lordo TAM: "+(rimborso.getTotaleTAM() != null ? rimborso.getTotaleTAM() : 0)));
		totaleTAM.setReadOnly(true);
//		details.addComponent(fieldDataInserimentoField);
//		details.addComponent(fieldDataLastModifiedField);

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
