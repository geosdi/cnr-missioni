package it.cnr.missioni.dashboard.component.form;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.ui.FormLayout;

import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;

/**
 * @author Salvia Vito
 */
public interface IForm<T extends Object> {

	T validate()  throws CommitException,InvalidValueException ;

	void buildFieldGroup();

	void buildTab();

	void addValidator();
	
	void addListener();

	public abstract class FormAbstract<T> extends FormLayout implements IForm<T> {

		/**
		* 
		*/
		private static final long serialVersionUID = -7214832545147375718L;
		private BeanFieldGroup<T> fieldGroup;
		protected T bean;
		protected boolean enabled;
		protected boolean modifica;
		protected boolean isAdmin;

		protected FormAbstract(){}
		
		protected FormAbstract(T bean,boolean isAdmin,boolean enabled,boolean modifica){
			this.enabled = enabled;
			this.isAdmin = isAdmin;
			this.modifica = modifica;
			this.bean = bean;
			DashboardEventBus.register(this);

		}

		public void buildFieldGroup() {
					
			getFieldGroup().setItemDataSource(bean);
			getFieldGroup().setBuffered(true);
			getFieldGroup().setReadOnly(!enabled);
			FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
			getFieldGroup().setFieldFactory(fieldFactory);
		}
		
		
		public abstract void addValidator();
		
		public abstract void addListener();

		/**
		 * @return the fieldGroup
		 */
		public BeanFieldGroup<T> getFieldGroup() {
			return fieldGroup;
		}

		/**
		 * @param fieldGroup 
		 */
		public void setFieldGroup(BeanFieldGroup<T> fieldGroup) {
			this.fieldGroup = fieldGroup;
		}

	}


}
