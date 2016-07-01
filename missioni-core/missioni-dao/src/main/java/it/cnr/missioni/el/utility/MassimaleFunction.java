package it.cnr.missioni.el.utility;

import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Salvia Vito
 */
public class MassimaleFunction {


    public static List<Massimale> creaMassiveMassimale() {

        List<Massimale> listaMassimale = new ArrayList<Massimale>();

        Massimale m = new Massimale();
        m.setId("01");
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setDescrizione("Ricercatore");
        m.setLivello(LivelloUserEnum.I);
        m.setValue(new Double(120));
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        listaMassimale.add(m);

        m = new Massimale();
        m.setId("02");
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setDescrizione("Ricercatore");
        m.setLivello(LivelloUserEnum.I);
        m.setValue(new Double(120));
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        listaMassimale.add(m);

        m = new Massimale();
        m.setId("03");
        m.setAreaGeografica(AreaGeograficaEnum.C);
        m.setDescrizione("Ricercatore");
        m.setLivello(LivelloUserEnum.I);
        m.setValue(new Double(120));
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        listaMassimale.add(m);

        m = new Massimale();
        m.setId("04");
        m.setAreaGeografica(AreaGeograficaEnum.D);
        m.setDescrizione("Ricercatore");
        m.setLivello(LivelloUserEnum.I);
        m.setValue(new Double(120));
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        listaMassimale.add(m);

        m = new Massimale();
        m.setId("05");
        m.setAreaGeografica(AreaGeograficaEnum.E);
        m.setDescrizione("Ricercatore");
        m.setLivello(LivelloUserEnum.I);
        m.setValue(new Double(120));
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        listaMassimale.add(m);

        m = new Massimale();
        m.setId("06");
        m.setAreaGeografica(AreaGeograficaEnum.F);
        m.setDescrizione("Ricercatore");
        m.setLivello(LivelloUserEnum.I);
        m.setValue(new Double(120));
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        listaMassimale.add(m);

        m = new Massimale();
        m.setId("07");
        m.setAreaGeografica(AreaGeograficaEnum.G);
        m.setDescrizione("Ricercatore");
        m.setLivello(LivelloUserEnum.I);
        m.setValue(new Double(120));
        m.setTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO);
        listaMassimale.add(m);

        m = new Massimale();
        m.setId("08");
        m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
        m.setDescrizione("Assegnista");
        m.setLivello(LivelloUserEnum.IV);
        m.setValue(new Double(44.26));
        listaMassimale.add(m);

        m = new Massimale();
        m.setId("09");
        m.setAreaGeografica(AreaGeograficaEnum.ITALIA);
        m.setDescrizione("Ricercatore");
        m.setLivello(LivelloUserEnum.I);
        m.setValue(new Double(61.10));
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        listaMassimale.add(m);

        m = new Massimale();
        m.setId("10");
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setDescrizione("Assegnista");
        m.setLivello(LivelloUserEnum.IV);
        m.setValue(new Double(61.10));
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        listaMassimale.add(m);

        m = new Massimale();
        m.setId("11");
        m.setAreaGeografica(AreaGeograficaEnum.B);
        m.setDescrizione("Assegnista");
        m.setLivello(LivelloUserEnum.IV);
        m.setValue(new Double(61.10));
        m.setTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
        listaMassimale.add(m);

        return listaMassimale;
    }


}
