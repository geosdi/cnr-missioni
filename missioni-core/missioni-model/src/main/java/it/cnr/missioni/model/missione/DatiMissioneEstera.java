package it.cnr.missioni.model.missione;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * @author Salvia Vito
 */
public class DatiMissioneEstera implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 734443544980437567L;


    private TrattamentoMissioneEsteraEnum trattamentoMissioneEsteraEnum;
    private DateTime attraversamentoFrontieraAndata;
    private DateTime attraversamentoFrontieraRitorno;

    /**
     * @return the trattamentoMissioneEsteraEnum
     */
    public TrattamentoMissioneEsteraEnum getTrattamentoMissioneEsteraEnum() {
        return trattamentoMissioneEsteraEnum;
    }

    /**
     * @param trattamentoMissioneEsteraEnum
     */
    public void setTrattamentoMissioneEsteraEnum(TrattamentoMissioneEsteraEnum trattamentoMissioneEsteraEnum) {
        this.trattamentoMissioneEsteraEnum = trattamentoMissioneEsteraEnum;
    }

    /**
     * @return the attraversamentoFrontieraAndata
     */
    public DateTime getAttraversamentoFrontieraAndata() {
        return attraversamentoFrontieraAndata;
    }

    /**
     * @param attraversamentoFrontieraAndata
     */
    public void setAttraversamentoFrontieraAndata(DateTime attraversamentoFrontieraAndata) {
        this.attraversamentoFrontieraAndata = attraversamentoFrontieraAndata;
    }

    /**
     * @return the attraversamentoFrontieraRitorno
     */
    public DateTime getAttraversamentoFrontieraRitorno() {
        return attraversamentoFrontieraRitorno;
    }

    /**
     * @param attraversamentoFrontieraRitorno
     */
    public void setAttraversamentoFrontieraRitorno(DateTime attraversamentoFrontieraRitorno) {
        this.attraversamentoFrontieraRitorno = attraversamentoFrontieraRitorno;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "DatiMissioneEstera [trattamentoMissioneEsteraEnum=" + trattamentoMissioneEsteraEnum
                + ", attraversamentoFrontieraAndata=" + attraversamentoFrontieraAndata
                + ", attraversamentoFrontieraRitorno=" + attraversamentoFrontieraRitorno + "]";
    }

}
