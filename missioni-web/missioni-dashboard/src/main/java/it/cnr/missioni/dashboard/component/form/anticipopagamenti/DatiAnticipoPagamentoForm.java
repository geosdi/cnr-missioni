package it.cnr.missioni.dashboard.component.form.anticipopagamenti;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiAnticipoPagamenti;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;

/**
 * @author Salvia Vito
 */
public class DatiAnticipoPagamentoForm extends IForm.FormAbstract<DatiAnticipoPagamenti> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5674917270638827694L;

	private CheckBox speseAlberghiereField;
	private CheckBox speseViaggioDocumentatoField;
	private CheckBox speseViaggioTamField;
	private CheckBox prospettoField;
	private TrattamentoMissioneEsteraEnum trattamentoMissioneEstera;
	private TextField speseMissioniAnticipateField;
	private TextField mandatoCNRField;

	public DatiAnticipoPagamentoForm(DatiAnticipoPagamenti datiAnticipoPagamenti,TrattamentoMissioneEsteraEnum trattamentoMissioneEstera,boolean isAdmin,boolean enabled,boolean modifica) {
		super(datiAnticipoPagamenti,isAdmin,enabled,modifica);
		this.trattamentoMissioneEstera = trattamentoMissioneEstera;
		setFieldGroup(new BeanFieldGroup<DatiAnticipoPagamenti>(DatiAnticipoPagamenti.class));
		buildFieldGroup();
		buildTab();

	}


	public void  buildTab() {
		speseAlberghiereField = (CheckBox) getFieldGroup().buildAndBind("Spese alberghiere/alloggio preventivate\n (Trattamento di missione con rimborso documentato);", "speseAlberghiere",CheckBox.class);
		speseViaggioDocumentatoField = (CheckBox) getFieldGroup().buildAndBind("Spese di viaggio(Trattamento di missione con rimborso documentato);", "speseViaggioDocumentato",CheckBox.class);
		speseViaggioTamField = (CheckBox) getFieldGroup().buildAndBind("Spese di viaggio(Trattamento alternativo di missione);", "speseViaggioTam",CheckBox.class);
		prospettoField = (CheckBox) getFieldGroup().buildAndBind("Prospetto calcolo anticipo(Trattamento alternativo di missione);", "prospetto",CheckBox.class);

		//Crea checkbox in base al tipo di trattamento
		if(trattamentoMissioneEstera == TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO){
			addComponent(speseAlberghiereField);
			addComponent(speseViaggioDocumentatoField);
		}else{
			addComponent(speseViaggioTamField);
			addComponent(prospettoField);
		}
		
		if(isAdmin)
			setIsAdmin();

		
	}
	
	/*
	 * Nel caso in cui si accede come Admin
	 * 
	 */
	private void setIsAdmin(){
		speseMissioniAnticipateField = (TextField) getFieldGroup().buildAndBind("Anticipo", "speseMissioniAnticipate");
		mandatoCNRField = (TextField) getFieldGroup().buildAndBind("Mandato CNR", "mandatoCNR");
		addComponent(speseMissioniAnticipateField);
		addComponent(mandatoCNRField);
	}

	public DatiAnticipoPagamenti validate() throws CommitException,InvalidValueException {
		for (Field<?> f : getFieldGroup().getFields()) {
			((AbstractField<?>) f).setValidationVisible(true);
		}
		
		if(!speseAlberghiereField.getValue() && !speseViaggioDocumentatoField.getValue() && !speseViaggioTamField.getValue() && !prospettoField.getValue()){
			speseAlberghiereField.setValidationVisible(true);
			throw new InvalidValueException(Utility.getMessage("no_anticipo_pagamenti"));
		}
		
		getFieldGroup().commit();
		BeanItem<DatiAnticipoPagamenti> beanItem = (BeanItem<DatiAnticipoPagamenti>) getFieldGroup().getItemDataSource();
		DatiAnticipoPagamenti datiAnticipoPagamenti = beanItem.getBean();
		return datiAnticipoPagamenti;
	}


	/**
	 * 
	 */
	@Override
	public void addValidator() {
		// TODO Auto-generated method stub
		
	}


	/**
	 * 
	 */
	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		
	}

}
