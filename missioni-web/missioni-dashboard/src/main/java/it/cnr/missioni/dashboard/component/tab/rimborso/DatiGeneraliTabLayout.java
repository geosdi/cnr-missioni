package it.cnr.missioni.dashboard.component.tab.rimborso;

import java.text.NumberFormat;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
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
public class DatiGeneraliTabLayout extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4676249391052429281L;
	private TextField avvisoPagamentoField;
	private TextField anticipazionePagamentoField;
	private TextField totKmField;
	private Label labelTotRimborsoKm = new Label("Tot. Rimborso km: ");

	private BeanFieldGroup<Rimborso> fieldGroup;
	private HorizontalLayout mainLayout;;

	private Rimborso rimborso;
	private final int days;
	private final boolean mezzoProprio;
	private double totRimborsoKm = 0.0;
	private boolean enabled;

	public String getCaption() {
		return "Step 1";
	}

	public DatiGeneraliTabLayout(Rimborso rimborso, int days, boolean mezzoProprio,boolean enabled ) {
		this.rimborso = rimborso;
		this.days = days;
		this.enabled = enabled;
		this.mezzoProprio = mezzoProprio;
		bindFieldGroup();
		buildGeneraleTab();
	}


	public void bindFieldGroup() {
		setFieldGroup(new BeanFieldGroup<Rimborso>(Rimborso.class));
		getFieldGroup().setItemDataSource(rimborso);
		getFieldGroup().setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		getFieldGroup().setFieldFactory(fieldFactory);
		fieldGroup.setEnabled(enabled);
		avvisoPagamentoField = (TextField) getFieldGroup().buildAndBind("Avviso Pagamento", "avvisoPagamento");
		anticipazionePagamentoField = (TextField) getFieldGroup().buildAndBind("Anticipazione Pagamento",
				"anticipazionePagamento");
		totKmField = (TextField) getFieldGroup().buildAndBind("Km da rimborsare", "totKm");

	}

	private void buildGeneraleTab() {

		mainLayout = new HorizontalLayout();
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
				+ (rimborso.getTotaleTAM() != null ? rimborso.getTotaleTAM() : 0), ContentMode.HTML);
		// l.setStyleName(ValoTheme.LABEL_LIGHT);
		l.addStyleName(ValoTheme.LABEL_H3);
		l.addStyleName(ValoTheme.LABEL_LIGHT);

		details.addComponent(l);

		addListener();
		addComponent(mainLayout);
	}

	private void addListener() {

		totKmField.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4941530987925161416L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				try {
					RimborsoKmStore rimborsoKmStore = ClientConnector
							.getRimborsoKm(RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder());
					if (rimborsoKmStore.getTotale() > 0) {

						NumberFormat f = NumberFormat.getInstance(); // Gets a
																		// NumberFormat
																		// with
																		// the
																		// default
																		// locale,
																		// you
																		// can
																		// specify
																		// a
																		// Locale
																		// as
																		// first
																		// parameter
																		// (like
																		// Locale.FRENCH)
						double number = f.parse(totKmField.getValue()).doubleValue();

						setTotRimborsoKm(number * rimborsoKmStore.getRimborsoKm().get(0).getValue());

					}
					labelTotRimborsoKm.setValue("Tot. Rimborso km: " + getTotRimborsoKm());
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
			}

		});

	}

	/**
	 * @return the fieldGroup
	 */
	public BeanFieldGroup<Rimborso> getFieldGroup() {
		return fieldGroup;
	}

	/**
	 * @param fieldGroup
	 */
	public void setFieldGroup(BeanFieldGroup<Rimborso> fieldGroup) {
		this.fieldGroup = fieldGroup;
	}

	/**
	 * @return the totRimborsoKm
	 */
	public double getTotRimborsoKm() {
		return totRimborsoKm;
	}

	/**
	 * @param totRimborsoKm
	 */
	public void setTotRimborsoKm(double totRimborsoKm) {
		this.totRimborsoKm = totRimborsoKm;
	}

}
