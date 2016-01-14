package it.cnr.missioni.notification.spring.configuration;

import com.google.common.base.Preconditions;
import net.jcip.annotations.Immutable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Immutable
@Component(value = "cnrMissioniItaliaEmail")
public class CNRMissioniItaliaEmail implements CNRMissioniEmail {

    @Value(value = "gpMailConfigurator{gp.mail.cnr.missioni_italia_email:@null}")
    private String email;

    /**
     * @return {@link String}
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkArgument(((this.email != null)
                && (!this.email.isEmpty())), "The EMAIL Parameter must not be "
                + "null or an Empty Value.");
    }

    @Override
    public String toString() {
        return this.emailInfo();
    }
}
