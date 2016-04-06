package it.cnr.missioni.dashboard.component.form.rimborso;

import java.util.ArrayList;
import java.util.UUID;

import org.joda.time.DateTime;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;

/**
 * @author Salvia Vito
 */
public class LayoutFatturaRimborso extends VerticalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 3473679597608380736L;

    private ElencoFattureTable elencoFattureTable;
    private IFormFattura formFattura;
    private Missione missione;
    private Button reset;
    private Button ok;
    private boolean isAdmin;
    private boolean enabled;
    private boolean modifica;
    private TableTypeEnum tableTypeEnum = TableTypeEnum.LIGHT;
    
    public enum TableTypeEnum{
    	FULL,LIGHT;
    }

    private LayoutFatturaRimborso(){}
    
    public static LayoutFatturaRimborso getFatturaRimborsoForm(){
    	return new LayoutFatturaRimborso();
    }
    
    public LayoutFatturaRimborso withMissione(Missione missione){
    	this.missione = missione;
    	return self();
    }
    
    public LayoutFatturaRimborso withIsAdmin(boolean isAdmin){
    	this.isAdmin = isAdmin;
    	return self();
    }
    
    public LayoutFatturaRimborso withEnabled(boolean enabled){
    	this.enabled = enabled;
    	return self();
    }
    
    public LayoutFatturaRimborso withModifica(boolean modifica){
    	this.modifica = modifica;
    	return self();
    }
    
    public LayoutFatturaRimborso withTableType(TableTypeEnum tableTypeEnum){
    	this.tableTypeEnum = tableTypeEnum;
    	return self();
    }
    
    public LayoutFatturaRimborso build(){
        this.formFattura = IFormFattura.FormFattura.getFormFattura().withModifica(modifica).withEnabled(enabled)
                .withIsAdmin(isAdmin).withMissione(missione).build();
        addComponent(formFattura);
        if (enabled) {
            HorizontalLayout l = buildFatturaButton();
            addComponent(l);
            setComponentAlignment(l, Alignment.MIDDLE_RIGHT);
        }
        elencoFattureTable = new ElencoFattureTable(formFattura.getFieldGroup(), formFattura.getDataField(), missione);
        buildTable(tableTypeEnum);
        elencoFattureTable.aggiornaTotale(missione.getRimborso().getTotale());
        addComponent(elencoFattureTable);
        return self();
    }
    
    private void buildTable(TableTypeEnum tableTypeEnum){
    	switch(tableTypeEnum){
    	case FULL:
            elencoFattureTable.aggiornaTableRiepilogo(
                    new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
            break;
    	case LIGHT:
            elencoFattureTable.aggiornaTable(
                    new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
            break;
            default :
            	break;
    	}
    }
    
    public LayoutFatturaRimborso self(){
    	return this;
    }

    // Ogni fattura deve essere compresa tra le date di inizio e fine
    // missione
    public void setRangeDate() {
        // formFattura.getDataField().setRangeStart(missione.getDatiPeriodoMissione().getInizioMissione().toDate());
        formFattura.getDataField().setRangeEnd(missione.getDatiPeriodoMissione().getFineMissione().toDate());
    }

    public void discardChange() {
        formFattura.getFieldGroup().discard();
    }

    private HorizontalLayout buildFatturaButton() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        reset = new Button("Reset");
        reset.addStyleName(ValoTheme.BUTTON_PRIMARY);
        reset.addClickListener(new ClickListener() {

            /**
             *
             */
            private static final long serialVersionUID = -6490162510371608133L;

            @Override
            public void buttonClick(ClickEvent event) {
                formFattura.getFieldGroup().discard();
                for (Field<?> f : formFattura.getFieldGroup().getFields()) {
                    ((AbstractField<?>) f).setValidationVisible(false);
                }
                formFattura.getDataField().setValidationVisible(false);
                formFattura.aggiornaFatturaTab(new Fattura());
            }
        });

        ok = new Button("OK");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -4170493805638852036L;

            @Override
            public void buttonClick(ClickEvent event) {
                boolean check = true;
                formFattura.getDataField().setValidationVisible(true);
                for (Field<?> f : formFattura.getFieldGroup().getFields()) {
                    ((AbstractField<?>) f).setValidationVisible(true);
                }
                try {

                    formFattura.getFieldGroup().commit();

                } catch (InvalidValueException | CommitException e) {
                    check = false;
                }
                try {
                    formFattura.getDataField().validate();
                } catch (InvalidValueException e) {
                    check = false;
                }
                if (check) {
                    BeanItem<Fattura> beanItem = (BeanItem<Fattura>) formFattura.getFieldGroup().getItemDataSource();
                    Fattura new_fattura = beanItem.getBean();
                    new_fattura.setImportoSpettante(new_fattura.getImporto());
                    // se la fattura è nuova creo un ID
                    if (new_fattura.getId() == null)
                        new_fattura.setId(UUID.randomUUID().toString());
                    new_fattura.setData(new DateTime(formFattura.getDataField().getValue()));
                    new_fattura.setShortDescriptionTipologiaSpesa(
                            formFattura.getTipologiaSpesaField().getItemCaption(new_fattura.getIdTipologiaSpesa()));
                    formFattura.getMissione().getRimborso().getMappaFattura().put(new_fattura.getId(), new_fattura);
                    Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
                    // ripulisco la form
                    for (Field<?> f : formFattura.getFieldGroup().getFields()) {
                        ((AbstractField<?>) f).setValidationVisible(false);
                    }
                    // aggiorno la tabella
                    formFattura.aggiornaFatturaTab(new Fattura());
                    elencoFattureTable.aggiornaTable(
                            new ArrayList<Fattura>(formFattura.getMissione().getRimborso().getMappaFattura().values()));
                    elencoFattureTable.aggiornaTotale(formFattura.getMissione().getRimborso().getTotale());
                    // Se estera ricancello la combobox
                    if (missione.isMissioneEstera())
                        formFattura.getTipologiaSpesaField().removeAllItems();
                } else {
                    Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
                            Type.ERROR_MESSAGE);
                }

            }
        });
        ok.focus();
        layout.addComponents(ok, reset);
        layout.setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
        layout.setComponentAlignment(reset, Alignment.BOTTOM_RIGHT);
        return layout;
    }
}
