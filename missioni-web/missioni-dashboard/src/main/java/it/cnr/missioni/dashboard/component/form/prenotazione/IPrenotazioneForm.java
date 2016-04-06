package it.cnr.missioni.dashboard.component.form.prenotazione;

import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IVeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.StatoVeicoloEnum;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Salvia Vito
 */
public interface IPrenotazioneForm extends IForm<PrenotazioneEvent, IPrenotazioneForm> {

    /**
     * @param calendarComponent
     * @return {@link IPrenotazioneForm }
     */
    IPrenotazioneForm withCalendarComponents(Calendar calendarComponent);

    class PrenotazioneForm extends IForm.FormAbstract<PrenotazioneEvent, IPrenotazioneForm>
            implements IPrenotazioneForm {

        /**
         *
         */
        private static final long serialVersionUID = -5193133236078976093L;
        private DateField dataFrom;
        private DateField dataTo;
        private ComboBox veicoliCNRField;
        private boolean isPresent = false;
        private Calendar calendarComponent;
        private CheckBox allDay;
        private TextField localita;

        private PrenotazioneForm() {
        }

        public static IPrenotazioneForm getPrenotazioneForm() {
            return new PrenotazioneForm();
        }

        public IPrenotazioneForm build() {
            setFieldGroup(new BeanFieldGroup<PrenotazioneEvent>(PrenotazioneEvent.class));
            buildFieldGroup();
            buildTab();
            return self();
        }

        public IPrenotazioneForm withCalendarComponents(Calendar calendarComponent) {
            this.calendarComponent = calendarComponent;
            return self();
        }

        public void buildTab() {

            localita = (TextField) getFieldGroup().buildAndBind("Localita", "localita");
            addComponent(localita);
            dataFrom = (DateField) getFieldGroup().buildAndBind("Inizio", "start");
            addComponent(dataFrom);
            dataTo = (DateField) getFieldGroup().buildAndBind("Fine", "end");
            addComponent(dataTo);
            buildFields();
            addComponent(veicoliCNRField);
            allDay = new CheckBox("Tutto il giorno");
            allDay.setImmediate(true);
            addComponent(allDay);
            getFieldGroup().bind(allDay, "allDay");
            setAllDayResolution(allDay.getValue());
            addListener();
        }

        private void buildFields() {
            List<VeicoloCNR> lista = new ArrayList<VeicoloCNR>();
            veicoliCNRField = new ComboBox("Veicolo");
            try {
                lista = ClientConnector.getVeicoloCNR(IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                        .getVeicoloCNRSearchBuilder().withStato(StatoVeicoloEnum.DISPONIBILE.name())).getVeicoliCNR();
            } catch (Exception e) {
                Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                        Type.ERROR_MESSAGE);
            }
            lista.forEach(v -> {
                veicoliCNRField.addItem(v.getId());
                veicoliCNRField.setItemCaption(v.getId(), v.getTipo());
            });
            veicoliCNRField.setValidationVisible(false);
            getFieldGroup().bind(veicoliCNRField, "veicolo");
        }

        public PrenotazioneEvent validate() throws CommitException, InvalidValueException {
            getFieldGroup().getFields().stream().forEach(f -> {
                ((AbstractField<?>) f).setValidationVisible(true);
            });
            getFieldGroup().commit();
            BeanItem<PrenotazioneEvent> beanItem = (BeanItem<PrenotazioneEvent>) getFieldGroup().getItemDataSource();
            PrenotazioneEvent prenotazione = beanItem.getBean();
            prenotazione.setVeicoloDescription(veicoliCNRField.getItemCaption(prenotazione.getVeicolo()));
            List<CalendarEvent> lista = calendarComponent.getEvents(prenotazione.getStart(), prenotazione.getEnd());
            lista.forEach(c -> {
                if (((PrenotazioneEvent) c).getVeicolo().equals(veicoliCNRField.getValue())
                        && !((PrenotazioneEvent) c).getId().equals(bean.getId())) {
                    Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("event_present"),
                            Type.ERROR_MESSAGE);
                    isPresent = true;
                }
            });
            if (!isPresent)
                return prenotazione;
            return null;
        }

        /**
         *
         */
        @Override
        public void addValidator() {
        }

        /**
         *
         */
        @Override
        public void addListener() {

            veicoliCNRField.addBlurListener(new BlurListener() {

                /**
                 *
                 */
                private static final long serialVersionUID = 2442322875270937559L;

                @Override
                public void blur(BlurEvent event) {
                    try {
                        veicoliCNRField.validate();
                    } catch (Exception e) {
                        veicoliCNRField.setValidationVisible(true);
                    }
                }
            });

            allDay.addListener(new Listener() {

                /**
                 *
                 */
                private static final long serialVersionUID = -2906867985601976653L;

                @Override
                public void componentEvent(Event event) {
                    boolean value = allDay.getValue();
                    setAllDayResolution(value);
                }
            });
        }

        private void setAllDayResolution(boolean allDay) {
            if (allDay) {
                dataFrom.setResolution(Resolution.DAY);
                dataTo.setResolution(Resolution.DAY);
                dataFrom.setDateFormat("dd/MM/yyyy");
                dataTo.setDateFormat("dd/MM/yyyy");
            } else {
                dataFrom.setResolution(Resolution.MINUTE);
                dataTo.setResolution(Resolution.MINUTE);
                dataFrom.setDateFormat("dd/MM/yyyy HH:mm");
                dataTo.setDateFormat("dd/MM/yyyy HH:mm");

            }
        }

        /**
         * @return
         */
        @Override
        protected IPrenotazioneForm self() {
            return this;
        }

    }

}
