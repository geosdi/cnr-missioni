package it.cnr.missioni.dashboard.component.wizard.rimborso;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.component.tab.rimborso.DatiGeneraliTabLayout;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.rimborso.Rimborso;

/**
 * @author Salvia Vito
 */
public class DatiGeneraliRimborsoStep implements WizardStep {


	private  Rimborso rimborso;
	private final int days;
	private final boolean mezzoProprio;
	private DatiGeneraliTabLayout datiGenerali;
	private final boolean enabled;

	public String getCaption() {
		return "Step 1";
	}

	public DatiGeneraliRimborsoStep(Rimborso rimborso, int days, boolean mezzoProprio,boolean enabled) {
		this.rimborso = rimborso;
		this.days = days;
		this.mezzoProprio = mezzoProprio;
		this.enabled = enabled;

	}

	public Component getContent() {
		 datiGenerali = new DatiGeneraliTabLayout(rimborso, days, mezzoProprio,enabled);
		return datiGenerali;
	}



	public boolean onAdvance() {
		try {
			for (Field<?> f : datiGenerali.getFieldGroup().getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			datiGenerali.getFieldGroup().commit();

			BeanItem<Rimborso> beanItem = (BeanItem<Rimborso>) datiGenerali.getFieldGroup().getItemDataSource();
			rimborso = beanItem.getBean();
			rimborso.setRimborsoKm(datiGenerali.getTotRimborsoKm());
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

}
