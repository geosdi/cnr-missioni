package it.cnr.missioni.dashboard.component.form.missione;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public class VeicoloMissioneForm extends IForm.FormAbstract<Missione> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6978603374633816039L;
	boolean veicoloPrincipaleSetted = false;
	private TextArea motivazioneMezzoProprio;
	private OptionGroup optionGroupMezzo;
	private Label labelVeicoloProprio;
	private Veicolo v = null;
	private User user;

	private static final String VEICOLO_CNR = "Veicolo CNR";
	private static final String VEICOLO_PROPRIO = "Veicolo Proprio";
	private static final String NESSUNO = "Nessuno";

	public VeicoloMissioneForm(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
		super(missione, isAdmin, enabled, modifica);
		setFieldGroup(new BeanFieldGroup<Missione>(Missione.class));
		addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		buildFieldGroup();
		buildTab();

	}

	public void buildTab() {

		//se non è Admin prendo come user quello loggato al sistema
		if (!isAdmin) {
			user = DashboardUI.getCurrentUser();
			v = user.getVeicoloPrincipale();
		} else {
			try {
				UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withId(bean.getIdUser());
				UserStore userStore = ClientConnector.getUser(userSearchBuilder);
				user = userStore.getUsers().get(0);
				v = user.getVeicoloPrincipale();
			} catch (Exception e) {
				Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
						Type.ERROR_MESSAGE);
			}
		}

		optionGroupMezzo = new OptionGroup("Veicolo");
		optionGroupMezzo.addItems(VEICOLO_CNR, VEICOLO_PROPRIO, NESSUNO);
		optionGroupMezzo.select(bean.getTipoVeicolo());
		optionGroupMezzo.setReadOnly(!enabled);
		
		motivazioneMezzoProprio = (TextArea) getFieldGroup().buildAndBind("Motivazione mezzo proprio",
				"motivazioniMezzoProprio", TextArea.class);
		if (bean.getTipoVeicolo() == VEICOLO_PROPRIO)
			motivazioneMezzoProprio.setReadOnly(false);
		else
			motivazioneMezzoProprio.setReadOnly(true);

		labelVeicoloProprio = new Label();

		// Visualizza il veicolo principale dell'user
		if (bean.isMezzoProprio()) {
			labelVeicoloProprio.setValue("Veicolo: " + v.getTipo() + " Targa: " + v.getTarga());
			labelVeicoloProprio.setVisible(true);

		} else {
			labelVeicoloProprio.setVisible(false);
		}
		
		addComponent(optionGroupMezzo);
		addComponent(labelVeicoloProprio);
		addComponent(motivazioneMezzoProprio);
		addListener();
		addValidator();

	}

	public Missione validate() throws CommitException, InvalidValueException {

		for (Field<?> f : getFieldGroup().getFields()) {
			((AbstractField<?>) f).setValidationVisible(true);
		}
		getFieldGroup().commit();
		BeanItem<Missione> beanItem = (BeanItem<Missione>) getFieldGroup().getItemDataSource();
		bean = beanItem.getBean();
		bean.setTipoVeicolo(optionGroupMezzo.getValue().toString());
		bean.setMezzoProprio(optionGroupMezzo.getValue().equals(VEICOLO_PROPRIO) ? true : false);

		return bean;
	}

	/**
	 * 
	 */
	@Override
	public void addValidator() {
		optionGroupMezzo.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4519173795761278326L;

			// Verifica se è presenta almeno un veicolo proprio per l'utente
			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value.equals(VEICOLO_PROPRIO)) {
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

			/**
			 * 
			 */
			private static final long serialVersionUID = -1375538750658054368L;

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (motivazioneMezzoProprio.getValue() == null && optionGroupMezzo.getValue().equals(VEICOLO_PROPRIO))
					throw new InvalidValueException(Utility.getMessage("altre_disposizioni_error"));

			}
		});

	}

	/**
	 * 
	 */
	@Override
	public void addListener() {
		optionGroupMezzo.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1526024942878895542L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (optionGroupMezzo.getValue().equals(VEICOLO_CNR) || optionGroupMezzo.getValue().equals(NESSUNO)) {
					motivazioneMezzoProprio.setReadOnly(false);
					motivazioneMezzoProprio.setValue(null);
					motivazioneMezzoProprio.setReadOnly(true);
					motivazioneMezzoProprio.setValidationVisible(false);
					labelVeicoloProprio.setVisible(false);
				}
				if (optionGroupMezzo.getValue().equals(VEICOLO_PROPRIO)) {
					motivazioneMezzoProprio.setReadOnly(false);
					labelVeicoloProprio.setVisible(true);
					labelVeicoloProprio.setValue("Veicolo: " + v.getTipo() + " Targa: " + v.getTarga());
				}
			}
		});
	}

}
