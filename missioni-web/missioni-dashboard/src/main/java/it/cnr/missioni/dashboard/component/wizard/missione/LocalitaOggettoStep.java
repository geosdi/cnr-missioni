package it.cnr.missioni.dashboard.component.wizard.missione;

import org.elasticsearch.common.geo.GeoPoint;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
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
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.response.geocoder.GeocoderStore;
import it.cnr.missioni.rest.api.response.missione.distance.DistanceResponse;

/**
 * @author Salvia Vito
 */
public class LocalitaOggettoStep implements WizardStep {

	private TextField localitaField;
	private TextField oggettoField;
	private ComboBox listaLocalitaField;
	private GeocoderStore geocoderStore;
	private TextField distanzaField;

	private BeanFieldGroup<Missione> fieldGroup;
	private HorizontalLayout mainLayout;;

	private Missione missione;

	public String getCaption() {
		return "Step 2";
	}

	public LocalitaOggettoStep(Missione missione) {
		this.missione = missione;

	}

	public Component getContent() {
		return buildGeneraleTab();
	}

	public void bindFieldGroup() {
		fieldGroup = new BeanFieldGroup<Missione>(Missione.class);
		fieldGroup.setItemDataSource(missione);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);
		localitaField = new TextField("Localita");
		listaLocalitaField = new ComboBox("Seleziona località");
		listaLocalitaField.setValidationVisible(false);

		fieldGroup.bind(listaLocalitaField, "localita");
		oggettoField = (TextField) fieldGroup.buildAndBind("Oggetto", "oggetto");
		distanzaField = (TextField) fieldGroup.buildAndBind("Distanza", "distanza");
		distanzaField.setEnabled(false);
	}

	private Component buildGeneraleTab() {

		mainLayout = new HorizontalLayout();
		mainLayout.setCaption("Località\\Oggetto");
		mainLayout.setIcon(FontAwesome.SUITCASE);
		mainLayout.setWidth(100.0f, Unit.PERCENTAGE);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		mainLayout.addComponent(details);
		mainLayout.setExpandRatio(details, 1);

		HorizontalLayout layoutLocalita = new HorizontalLayout();
		layoutLocalita.addComponent(localitaField);

		details.addComponent(localitaField);
		details.addComponent(listaLocalitaField);
		details.addComponent(oggettoField);
		details.addComponent(distanzaField);

		addListener();
		
		return mainLayout;
	}

	private void addListener() {

		listaLocalitaField.addBlurListener(new BlurListener() {

			@Override
			public void blur(BlurEvent event) {
				try {
					listaLocalitaField.validate();
				} catch (Exception e) {
					listaLocalitaField.setValidationVisible(true);
				}
			}
		});

		listaLocalitaField.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				try {
					DistanceResponse.MissioneDistanceResponse distance = ClientConnector
							.getDistanceForMissione("Tito Scalo", localitaField.getValue());
					distanzaField.setValue(distance.getDistance());
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}

			}
		});

		localitaField.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				listaLocalitaField.removeAllItems();

				if (!localitaField.getValue().trim().equals("")) {

					try {
						geocoderStore = ClientConnector.getGeocoderStoreForMissioneLocation(localitaField.getValue());

						if (geocoderStore.getGeocoderResponses() != null) {

							geocoderStore.getGeocoderResponses().forEach(c -> {
								listaLocalitaField.addItem(c.getLat()
										 + "-" + c.getLon());
								 listaLocalitaField.setItemCaption(c.getLat()
								 + "-" + c.getLon(),
								 c.getFormattedAddress());
							});
						}
						else{
							Utility.getNotification(Utility.getMessage("error_message"),
									Utility.getMessage("localita_error"), Type.ERROR_MESSAGE);
						}

					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
				}

			}
		});
	}

	public boolean onAdvance() {
		try {
			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			localitaField.validate();
			oggettoField.validate();

			BeanItem<Missione> beanItem = (BeanItem<Missione>) fieldGroup.getItemDataSource();
			missione = beanItem.getBean();
			missione.setOggetto(oggettoField.getValue());
			missione.setLocalita(localitaField.getValue());
			missione.setDistanza(distanzaField.getValue());
			
			String[] latLng = ((String)listaLocalitaField.getValue()).split("-");
			GeoPoint geoPoint  = new GeoPoint(Double.parseDouble(latLng[0]),Double.parseDouble(latLng[1]));
			missione.setGeoPoint(geoPoint);
			
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
