package it.cnr.missioni.dashboard.utility;

import com.vaadin.server.*;
import com.vaadin.ui.AbstractComponent;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Salvia Vito
 */
public class AdvancedFileDownloader extends FileDownloader {

    /**
     *
     */
    private static final long serialVersionUID = -1377598357403435904L;

    /**
     *
     */
    private static final boolean DEBUG_MODE = true;

    private static final Logger logger = java.util.logging.Logger
            .getLogger(AdvancedFileDownloader.class.getName());

    private AbstractComponent extendedComponet;

    private AdvancedDownloaderListener dynamicDownloaderListener;
    private DownloaderEvent downloadEvent;
    private StreamResource resource;

    private AdvancedFileDownloader(StreamResource resource) {
        super(resource == null ? (resource = new StreamResource(null, "")) : resource);
        AdvancedFileDownloader.this.resource = resource;
    }

    public AdvancedFileDownloader() {
        this(null);
    }

    public void fireEvent() {
        if (DEBUG_MODE) {
            logger.info("inside fireEvent");
        }
        if (this.dynamicDownloaderListener != null
                && this.downloadEvent != null) {
            if (DEBUG_MODE) {
                logger.info("beforeDownload is going to be invoked");
            }
            this.dynamicDownloaderListener.beforeDownload(this.downloadEvent);
        }
    }

    public void addAdvancedDownloaderListener(
            AdvancedDownloaderListener listener) {
        if (listener != null) {
            DownloaderEvent downloadEvent = new DownloaderEvent() {
                private AbstractComponent extendedComponet;

                @Override
                public AbstractComponent getExtendedComponet() {
                    // TODO Auto-generated method stub
                    return this.extendedComponet;
                }

                @Override
                public void setExtendedComponet(
                        AbstractComponent extendedComponet) {
                    this.extendedComponet = extendedComponet;
                }
            };
            downloadEvent
                    .setExtendedComponet(AdvancedFileDownloader.this.extendedComponet);
            this.dynamicDownloaderListener = listener;
            this.downloadEvent = downloadEvent;

        }
    }

    @Override
    public boolean handleConnectorRequest(VaadinRequest request,
            VaadinResponse response, String path) throws IOException {

        if (!path.matches("dl(/.*)?")) {
            // Ignore if it isn't for us
            return false;
        }
        VaadinSession session = getSession();
        session.lock();
        AdvancedFileDownloader.this.fireEvent();
        DownloadStream stream;
        try {
            Resource resource = getFileDownloadResource();
            if (!(resource instanceof ConnectorResource)) {
                return false;
            }
            stream = ((ConnectorResource) resource).getStream();
            if (stream.getParameter("Content-Disposition") == null) {
                // Content-Disposition: attachment generally forces download
                stream.setParameter("Content-Disposition",
                        "attachment; filename=\"" + stream.getFileName() + "\"");
            }
            // Content-Type to block eager browser plug-ins from hijacking
            // the file
            if (isOverrideContentType()) {
                stream.setContentType("application/octet-stream;charset=UTF-8");
            }
        } finally {
            session.unlock();
        }
        stream.writeResponse(request, response);
        return true;
    }

    public interface AdvancedDownloaderListener {
        /**
         * Invocato prima che parta il download
         *
         * @param downloadEvent
         */
        public void beforeDownload(DownloaderEvent downloadEvent);
    }

    public abstract class DownloaderEvent {

        /**
         * @return
         */
        public abstract AbstractComponent getExtendedComponet();

        public abstract void setExtendedComponet(
                AbstractComponent extendedComponet);
    }
}

