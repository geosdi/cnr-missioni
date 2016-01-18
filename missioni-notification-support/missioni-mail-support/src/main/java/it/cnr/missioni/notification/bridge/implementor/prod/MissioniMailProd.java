package it.cnr.missioni.notification.bridge.implementor.prod;

import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import it.cnr.missioni.notification.message.preparator.IMissioniMessagePreparator;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
abstract class MissioniMailProd implements MissioniMailImplementor<IMissioniMessagePreparator> {

    /**
     * @param mimeMessage
     * @param gpMailDetail
     * @return {@link MimeMessageHelper}
     * @throws Exception
     */
    protected MimeMessageHelper createMimeMessageHelper(MimeMessage mimeMessage,
            final GPMailDetail gpMailDetail, Boolean multipart) throws Exception {
        return new MimeMessageHelper(mimeMessage, multipart) {

            {
                super.setFrom(gpMailDetail.getFrom(),
                        gpMailDetail.getFromName());
                super.setReplyTo(gpMailDetail.getReplayToName());
                super.setSubject(getSubjectMessage());
            }

        };
    }

    /**
     * @return {@link IMissioniMessagePreparator}
     */
    protected IMissioniMessagePreparator createMissioniMessagePreparator() {
        return new IMissioniMessagePreparator.MissioniMessagePreparator();
    }

    /**
     * @return {@link String}
     */
    protected String getSubjectMessage() {
        return "".concat(DateTime.now(DateTimeZone.UTC).toString())
                .concat(" #CNR Sistema Missioni");
    }

    @Override
    public String toString() {
        return this.mailImplementorInfo();
    }
}
