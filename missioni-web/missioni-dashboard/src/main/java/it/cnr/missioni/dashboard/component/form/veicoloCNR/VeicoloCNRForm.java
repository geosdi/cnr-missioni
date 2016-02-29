package it.cnr.missioni.dashboard.component.form.veicoloCNR;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.VeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;

/**
 * @author Salvia Vito
 */
public class VeicoloCNRForm extends IForm.FormAbstract<VeicoloCNR> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -871042723921901426L;
	private TextField tipoField;
	private TextField targaField;
	private TextField cartaCircolazioneField;
	private TextField polizzaAssicurativaField;
	private ComboBox statoField;


	public VeicoloCNRForm(VeicoloCNR veicoloCNR,boolean isAdmin,boolean enabled,boolean modifica) {
		super(veicoloCNR,isAdmin,enabled,modifica);
		setFieldGroup(new BeanFieldGroup<VeicoloCNR>(VeicoloCNR.class));

		buildFieldGroup();
		buildTab();

	}


	public void  buildTab() {


		addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

		tipoField = (TextField) getFieldGroup().buildAndBind("Tipo", "tipo");
		addComponent(tipoField);
		targaField = (TextField) getFieldGroup().buildAndBind("Targa", "targa");
		addComponent(targaField);

		cartaCircolazioneField = (TextField) getFieldGroup().buildAndBind("Carta Circolazione", "cartaCircolazione");
		addComponent(cartaCircolazioneField);
		polizzaAssicurativaField = (TextField) getFieldGroup().buildAndBind("Polizza Assicurativa", "polizzaAssicurativa");
		addComponent(polizzaAssicurativaField);

		statoField = (ComboBox) getFieldGroup().buildAndBind("Stato", "stato", ComboBox.class);
		addComponent(statoField);

		addValidator();
	}
	
	public void addValidator() {
		targaField.addValidator(new Validator() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1907043044766175552L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null) {
					VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder
							.getVeicoloCNRSearchBuilder().withTarga(targaField.getValue());
					if (modifica)
						veicoloCNRSearchBuilder.withNotId(bean.getId());
					VeicoloCNRStore veicoloCNRStore = null;
					try {
						veicoloCNRStore = ClientConnector.getVeicoloCNR(veicoloCNRSearchBuilder);
					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
					if (veicoloCNRStore.getTotale() > 0)
						throw new InvalidValueException(Utility.getMessage("targa_present"));
				}

			}

		});

		polizzaAssicurativaField.addValidator(new Validator() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1244682045921823625L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null) {
					VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder
							.getVeicoloCNRSearchBuilder().withPolizzaAssicurativa(polizzaAssicurativaField.getValue());
					if (modifica)
						veicoloCNRSearchBuilder.withNotId(bean.getId());
					VeicoloCNRStore veicoloCNRStore = null;
					try {
						veicoloCNRStore = ClientConnector.getVeicoloCNR(veicoloCNRSearchBuilder);
					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
					if (veicoloCNRStore.getTotale() > 0)
						throw new InvalidValueException(Utility.getMessage("polizza_present"));
				}

			}

		});

		cartaCircolazioneField.addValidator(new Validator() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7014811531417111554L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null) {
					VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder
							.getVeicoloCNRSearchBuilder().withCartaCircolazione(cartaCircolazioneField.getValue());
					if (modifica)
						veicoloCNRSearchBuilder.withNotId(bean.getId());
					VeicoloCNRStore veicoloCNRStore = null;
					try {
						veicoloCNRStore = ClientConnector.getVeicoloCNR(veicoloCNRSearchBuilder);
					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
					if (veicoloCNRStore.getTotale() > 0)
						throw new InvalidValueException(Utility.getMessage("carta_circolazione_present"));
				}
			}

		});


	}

	public VeicoloCNR validate() throws CommitException,InvalidValueException {
		for (Field<?> f : getFieldGroup().getFields()) {
			((AbstractField<?>) f).setValidationVisible(true);
		}
		getFieldGroup().commit();

		BeanItem<VeicoloCNR> beanItem = (BeanItem<VeicoloCNR>) getFieldGroup().getItemDataSource();
		VeicoloCNR veicoloCNR = beanItem.getBean();
		return veicoloCNR;
	}


	/**
	 * 
	 */
	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		
	}



}
