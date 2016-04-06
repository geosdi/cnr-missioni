package it.cnr.missioni.dashboard.binder;

import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.model.user.User;
import org.joda.time.DateTime;

import java.util.function.Function;

/**
 * @author Salvia Vito
 */
public interface IPrenotazioneBinder<TO extends Prenotazione, FROM extends PrenotazioneEvent>
        extends IBinder<TO, FROM, IPrenotazioneBinder<TO, FROM>> {

    IPrenotazioneBinder<TO, FROM> withModifica(boolean modifica);

    IPrenotazioneBinder<TO, FROM> withUser(User user);

    class PrenotazioneBinder extends
            IBinder.AbstractBinder<Prenotazione, PrenotazioneEvent, IPrenotazioneBinder<Prenotazione, PrenotazioneEvent>>
            implements IPrenotazioneBinder<Prenotazione, PrenotazioneEvent> {

        private boolean modifica;
        private User user;

        public static PrenotazioneBinder getPrenotazioneBinder() {
            return new PrenotazioneBinder();
        }

        /**
         * @return
         */
        @Override
        public Prenotazione bind() {
            return new PrenotazioneFunction().apply(from);
        }

        public PrenotazioneBinder withModifica(boolean modifica) {
            this.modifica = modifica;
            return self();
        }

        public PrenotazioneBinder withUser(User user) {
            this.user = user;
            return self();
        }

        /**
         * @return
         */
        @Override
        protected PrenotazioneBinder self() {
            return this;
        }

        class PrenotazioneFunction implements Function<PrenotazioneEvent, Prenotazione> {
            /**
             * @param prenotazioneEvent
             * @return
             */
            @Override
            public Prenotazione apply(PrenotazioneEvent prenotazioneEvent) {
                return new Prenotazione() {
                    {
                        super.setDataFrom(new DateTime(prenotazioneEvent.getStart().getTime()));
                        super.setDataTo(new DateTime(prenotazioneEvent.getEnd().getTime()));
                        super.setIdVeicoloCNR(prenotazioneEvent.getVeicolo());
                        super.setAllDay(prenotazioneEvent.isAllDay());
                        super.setLocalita(prenotazioneEvent.getLocalita());
                        super.setDescriptionVeicoloCNR(prenotazioneEvent.getVeicoloDescription());
                        if (modifica) {
                            super.setId(prenotazioneEvent.getId());
                            super.setIdUser(prenotazioneEvent.getIdUser());
                            super.setDescrizione(prenotazioneEvent.getDescrizione());
                        } else {
                            super.setIdUser(user.getId());
                            super.setDescrizione(
                                    user.getAnagrafica().getCognome() + " " + user.getAnagrafica().getNome());
                        }
                    }
                };
            }
        }
    }
}
