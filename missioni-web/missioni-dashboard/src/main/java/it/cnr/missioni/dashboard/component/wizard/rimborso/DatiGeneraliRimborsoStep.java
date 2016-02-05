package it.cnr.missioni.dashboard.component.wizard.rimborso;

import java.text.NumberFormat;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.RimborsoKmSearchBuilder;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;

/**
 * @author Salvia Vito
 */
public class DatiGeneraliRimborsoStep implements WizardStep {

	private TextField avvisoPagamentoField;
	private TextField anticipazionePagamentoField;
	private TextField totKmField;
	private Label labelTotRimborsoKm = new Label("Tot. Rimborso km: ");

	private BeanFieldGroup<Rimborso> fieldGroup;
	private HorizontalLayout mainLayout;;

	private Rimborso rimborso;
	private final int days;
	private final boolean mezzoProprio;
	private double totRimborsoKm = 0.0;;

	public String getCaption() {
		return "Step 1";
	}

	public DatiGeneraliRimborsoStep(Rimborso rimborso, int days, boolean mezzoProprio) {
		this.rimborso = rimborso;
		this.days = days;
		this.mezzoProprio = mezzoProprio;

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
		anticipazionePagamentoField = (TextField) fieldGroup.buildAndBind("Anticipazione Pagamento",
				"anticipazionePagamento");
		totKmField = (TextField) fieldGroup.buildAndBind("Km da rimborsare", "totKm");

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
		if (mezzoProprio) {
			details.addComponent(totKmField);
			details.addComponent(labelTotRimborsoKm);

		}
		
		Label l = new Label("<b>GG all'estero:</b> " + this.days + "\t<b>Tot. lordo TAM:</b> "
				+ (rimborso.getTotaleTAM() != null ? rimborso.getTotaleTAM() : 0),ContentMode.HTML);
//		l.setStyleName(ValoTheme.LABEL_LIGHT);
		l.addStyleName(ValoTheme.LABEL_H3);
		l.addStyleName(ValoTheme.LABEL_LIGHT);


		details.addComponent(l);

		addListener();

		return mainLayout;
	}

	private void addListener() {

		totKmField.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				try {
					RimborsoKmStore rimborsoKmStore = ClientConnector
							.getRimborsoKm(RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder());
					if (rimborsoKmStore != null) {
						
						NumberFormat f = NumberFormat.getInstance(); // Gets a NumberFormat with the default locale, you can specify a Locale as first parameter (like Locale.FRENCH)
						double number = f.parse(totKmField.getValue()).doubleValue();
						
						totRimborsoKm = number
								* rimborsoKmStore.getRimborsoKm().get(0).getValue();

					}
					labelTotRimborsoKm.setValue("Tot. Rimborso km: " + totRimborsoKm);
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
			}

		});

	}

	public boolean onAdvance() {
		try {
			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			fieldGroup.commit();

			BeanItem<Rimborso> beanItem = (BeanItem<Rimborso>) fieldGroup.getItemDataSource();
			rimborso = beanItem.getBean();
			rimborso.setRimborsoKm(totRimborsoKm);
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
