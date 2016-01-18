package it.cnr.missioni.notification.message.preparator;

import com.google.common.collect.Lists;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.io.File;
import java.util.List;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface IMissioniMessagePreparator {

    /**
     * @return {@link MimeMessagePreparator}
     */
    MimeMessagePreparator getMimeMessagePreparator();

    /**
     * @param theMimeMessagePreparator
     */
    void setMimeMessagePreparator(MimeMessagePreparator theMimeMessagePreparator);

    /**
     * @param attach
     */
    void addAttachment(File attach);

    void deleteAttachments();

    /**
     *
     */
    class MissioniMessagePreparator implements IMissioniMessagePreparator {

        private MimeMessagePreparator mimeMessagePreparator;
        private List<File> files = Lists.newArrayList();

        /**
         * @return {@link MimeMessagePreparator}
         */
        @Override
        public MimeMessagePreparator getMimeMessagePreparator() {
            return this.mimeMessagePreparator;
        }

        /**
         * @param theMimeMessagePreparator
         */
        @Override
        public void setMimeMessagePreparator(MimeMessagePreparator theMimeMessagePreparator) {
            this.mimeMessagePreparator = theMimeMessagePreparator;
        }

        /**
         * @param attach
         */
        @Override
        public void addAttachment(File attach) {
            this.files.add(attach);
        }

        @Override
        public void deleteAttachments() {
            if (!this.files.isEmpty()) {
                this.files.forEach(file -> {
                    file.delete();
                });
            }
        }
    }
}
