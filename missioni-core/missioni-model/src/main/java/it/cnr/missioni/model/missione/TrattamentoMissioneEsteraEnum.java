package it.cnr.missioni.model.missione;

public enum TrattamentoMissioneEsteraEnum {
    RIMBORSO_DOCUMENTATO("Rimborso Documentato"), TRATTAMENTO_ALTERNATIVO("Trattamento Alternativo");

    private final String stato;

    TrattamentoMissioneEsteraEnum(String stato) {
        this.stato = stato;
    }

    public static TrattamentoMissioneEsteraEnum getStatoEnum(String stato) {
        for (TrattamentoMissioneEsteraEnum statoEnum : TrattamentoMissioneEsteraEnum.values()) {
            if (statoEnum.name().equals(stato))
                return statoEnum;
        }
        return null;
    }

    public static TrattamentoMissioneEsteraEnum getTrattamentoMissioneEstera(String stato) {
        for (TrattamentoMissioneEsteraEnum t : TrattamentoMissioneEsteraEnum.values()) {
            if (t.getStato().equals(stato))
                return t;
        }
        return null;
    }

    public String getStato() {
        return stato;
    }

}
