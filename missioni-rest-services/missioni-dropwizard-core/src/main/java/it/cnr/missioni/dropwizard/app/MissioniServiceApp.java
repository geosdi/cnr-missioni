package it.cnr.missioni.dropwizard.app;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import io.dropwizard.Application;
import io.dropwizard.jersey.jackson.JacksonMessageBodyProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import it.cnr.missioni.dropwizard.config.MissioniServiceConfig;
import it.cnr.missioni.dropwizard.config.spring.MissioniServiceLoader;
import it.cnr.missioni.dropwizard.health.MissioniServiceHealthCheck;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.ws.rs.Path;
import java.util.Map;

import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.WRITE_DATES_AS_TIMESTAMPS_DISABLE;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class MissioniServiceApp extends Application<MissioniServiceConfig> {

    public static void main(String[] args) throws Exception {
        new MissioniServiceApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<MissioniServiceConfig> bootstrap) {
    }

    @Override
    public void run(MissioniServiceConfig configuration, Environment environment) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MissioniServiceLoader.class);
        ctx.registerShutdownHook();
        ctx.start();

        environment.jersey().register(new JacksonMessageBodyProvider(
                new GPJacksonSupport()
                        .registerModule(new JodaModule())
                        .configure(WRITE_DATES_AS_TIMESTAMPS_DISABLE)
                        .getDefaultMapper(), environment.getValidator()));

        environment.healthChecks().register("service-health-check",
                new MissioniServiceHealthCheck());

        Map<String, Object> resources = ctx.getBeansWithAnnotation(Path.class);

        for (Map.Entry<String, Object> entry : resources.entrySet()) {
            environment.jersey().register(entry.getValue());
        }
    }

    @Override
    public String getName() {
        return "CNR Missioni Service";
    }
}
