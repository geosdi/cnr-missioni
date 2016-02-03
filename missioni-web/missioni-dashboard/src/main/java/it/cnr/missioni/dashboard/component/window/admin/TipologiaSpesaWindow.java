package it.cnr.missioni.dashboard.component.window.admin;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.admin.NazioneAction;
import it.cnr.missioni.dashboard.action.admin.TipologiaSpesaAction;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.TipologiaSpesa;

public class TipologiaSpesaWindow extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6599225757081293003L;

	public static final String ID = "tipologiaspesawindow";

	private final BeanFieldGroup<TipologiaSpesa> fieldGroup;

	private TextField valueField;
	private boolean modifica;

	private final TipologiaSpesa tipologiaSpesa;

	private TipologiaSpesaWindow(final TipologiaSpesa tipologiaSpesa, boolean modifica) {

		super();
		this.tipologiaSpesa = tipologiaSpesa;
		this.modifica = modifica;
		setId(ID);
		fieldGroup = new BeanFieldGroup<TipologiaSpesa>(TipologiaSpesa.class);
		build();
		buildFieldGroup();
		detailsWrapper.addComponent(buildTipologiaSpesaTab());

		content.addComponent(buildFooter());

	}

	private void buildFieldGroup() {
		fieldGroup.setItemDataSource(this.tipologiaSpesa);
		fieldGroup.setBuffered(true);

		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);
	}

	private Component buildTipologiaSpesaTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Tipologia Spesa");
		root.setIcon(FontAwesome.LIST);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		valueField = (TextField) fieldGroup.buildAndBind("Tipologia Spesa", "value");
		details.addComponent(valueField);

		return root;
	}

	private void addValidator() {

	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				try {

					for (Field<?> f : fieldGroup.getFields()) {
						((AbstractField<?>) f).setValidationVisible(true);
					}

					// targaField.validate();
					fieldGroup.commit();

					BeanItem<TipologiaSpesa> beanItem = (BeanItem<TipologiaSpesa>) fieldGroup.getItemDataSource();
					TipologiaSpesa tipologiaSpesa = beanItem.getBean();

					DashboardEventBus.post(new TipologiaSpesaAction(tipologiaSpesa, modifica));
					close();

				} catch (InvalidValueException | CommitException e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();
		footer.addComponent(ok);
		footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
		return footer;
	}

	public static void open(final TipologiaSpesa tipologiaSpesa, boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new TipologiaSpesaWindow(tipologiaSpesa, modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
