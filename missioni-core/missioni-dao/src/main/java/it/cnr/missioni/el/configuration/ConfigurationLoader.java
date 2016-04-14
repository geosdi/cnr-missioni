package it.cnr.missioni.el.configuration;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import it.cnr.missioni.el.dao.IDirettoreDAO;
import it.cnr.missioni.el.dao.IRimborsoKmDAO;
import it.cnr.missioni.model.configuration.Direttore;
import it.cnr.missioni.model.configuration.RimborsoKm;

@Configuration
public class ConfigurationLoader {
	
	@Bean(name="direttoreConfiguration", initMethod="configure")
	@DependsOn(value="missioniIndexConfigurator")
	public DirettoreConfiguration getDirettoreConfiguration(){
		return new DirettoreConfiguration();
	}
	
	@Bean(name="rimborsoKmConfiguration", initMethod="configure")
	@DependsOn(value="missioniIndexConfigurator")
	public RimborsoKmConfiguration getRimborsoKmConfiguration(){
		return new RimborsoKmConfiguration();
	}

	static class DirettoreConfiguration implements IConfiguration{
		
		@Resource(name = "direttoreDAO")
		private IDirettoreDAO direttoreDAO;
		
		public void configure() throws Exception{
			if(direttoreDAO.count() <= 0 ){
				Direttore d = new Direttore();
				d.setValue("Dott. Vincenzo Lapenna");
				direttoreDAO.persist(d);
			}
		}
	}
	
	static class RimborsoKmConfiguration implements IConfiguration{
		
	    @Resource(name = "rimborsoKmDAO")
	    private IRimborsoKmDAO rimborsoKmDAO;
		
		public void configure() throws Exception{
			if(rimborsoKmDAO.count() <= 0 ){
				RimborsoKm r = new RimborsoKm();
				r.setValue(0.36);
				rimborsoKmDAO.persist(r);
			}
		}
	}
}
