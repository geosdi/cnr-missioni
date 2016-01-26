package it.cnr.missioni.notification.support.itext.rimborso;

import it.cnr.missioni.notification.support.itext.PDFBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class RimborsoPDFBuilder extends PDFBuilder.AbstractPDFBuilder {

    private static final Logger logger = LoggerFactory.getLogger(RimborsoPDFBuilder.class);

    protected RimborsoPDFBuilder() {
    }

    public static PDFBuilder newPDFBuilder() {
        return new RimborsoPDFBuilder();
    }

    @Override
    public void build() throws Exception {
        logger.debug("############################{} ::::::::::::<<<<<<<<< PDF GENERATION BEGIN" +
                " >>>>>>>>>>>>\n", getType());
        super.checkArguments();
    }

    @Override
    public IPDFBuilderType getType() {
        return PDFBuilderType.RIMBORSO_PDF_BUIDER;
    }
}
