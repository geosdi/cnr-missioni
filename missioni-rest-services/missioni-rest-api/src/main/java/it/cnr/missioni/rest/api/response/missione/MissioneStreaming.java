package it.cnr.missioni.rest.api.response.missione;

import it.cnr.missioni.notification.support.itext.PDFBuilder;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class MissioneStreaming implements StreamingOutput {

    private final PDFBuilder pdfBuilder;

    public MissioneStreaming(PDFBuilder thePdfBuilder) {
        this.pdfBuilder = thePdfBuilder;
    }

    /**
     * Called to write the message body.
     *
     * @param output the OutputStream to write to.
     * @throws IOException             if an IO error is encountered
     * @throws WebApplicationException if a specific
     *                                 HTTP error response needs to be produced. Only effective if thrown prior
     *                                 to any bytes being written to output.
     */
    @Override
    public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
            this.pdfBuilder.withOutputStream(output);
            this.pdfBuilder.build();
        } catch (Exception e) {
            throw new WebApplicationException(e);
        } finally {
            if (output != null) output.close();
        }
    }
}
