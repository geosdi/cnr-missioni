package it.cnr.missioni.dashboard.component.form.massimale;

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

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

/**
 * @author Salvia Vito
 */
public class MassimaleForm extends IForm.FormAbstract<Massimale> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7899131798012080507L;
	/**
	 * 
	 */
	private TextField valueField;
	private ComboBox areagGeograficaField;
	private ComboBox livelloField;
	private ComboBox tipoField;
	private TextField descrizioneField;
	private TextField valueMezzaGiornataField;


	public MassimaleForm(Massimale massimale,boolean isAdmin,boolean enabled,boolean modifica) {
		super(massimale,isAdmin,enabled,modifica);
		setFieldGroup(new BeanFieldGroup<Massimale>(Massimale.class));

		buildFieldGroup();
		buildTab();

	}

	public void buildTab() {

		valueField = (TextField) getFieldGroup().buildAndBind("Importo", "value");
		addComponent(valueField);
		valueMezzaGiornataField = (TextField) getFieldGroup().buildAndBind("Importo Mezza Giornata", "valueMezzaGiornata");
		addComponent(valueMezzaGiornataField);
		areagGeograficaField = (ComboBox) getFieldGroup().buildAndBind("Area Geografica", "areaGeografica", ComboBox.class);
		addComponent(areagGeograficaField);
		livelloField = (ComboBox) getFieldGroup().buildAndBind("Livello", "livello", ComboBox.class);
		addComponent(livelloField);
		tipoField = (ComboBox) getFieldGroup().buildAndBind("Tipo", "tipo", ComboBox.class);
		addComponent(tipoField);
		descrizioneField = (TextField) getFieldGroup().buildAndBind("Descrizione", "descrizione");
		addComponent(descrizioneField);
		addValidator();

	}

	public void addValidator() {
		

		tipoField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1077060872731708958L;

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value != null && areagGeograficaField.getValue() != null && livelloField.getValue() != null) {

					MassimaleSearchBuilder massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder()
							.withAreaGeografica(areagGeograficaField.getValue().toString())
							.withLivello(((LivelloUserEnum) livelloField.getValue()).name())
							.withTipo(((TrattamentoMissioneEsteraEnum) value).name());
					if (modifica)
						massimaleSearchBuilder.withNotId(bean.getId());

					MassimaleStore massimaleStore = null;

					try {
						massimaleStore = ClientConnector.getMassimale(massimaleSearchBuilder);

					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}

					if (massimaleStore.getTotale() > 0)
						throw new InvalidValueException(Utility.getMessage("livello_area_error"));

				}
			}

		});

	}

	public Massimale validate() throws CommitException,InvalidValueException {
		for (Field<?> f : getFieldGroup().getFields()) {
			((AbstractField<?>) f).setValidationVisible(true);
		}
		getFieldGroup().commit();

		BeanItem<Massimale> beanItem = (BeanItem<Massimale>) getFieldGroup().getItemDataSource();
		Massimale massimale = beanItem.getBean();
		return massimale;
	}

	/**
	 * 
	 */
	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		
	}

}
