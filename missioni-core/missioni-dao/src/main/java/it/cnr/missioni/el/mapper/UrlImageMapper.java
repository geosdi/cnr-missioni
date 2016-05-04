package it.cnr.missioni.el.mapper;

import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.stereotype.Component;

import it.cnr.missioni.model.configuration.UrlImage;

/**
 * @author Salvia Vito
 */
@Component(value = "urlImageMapper")
public class UrlImageMapper extends GPBaseMapper<UrlImage> {

    public UrlImageMapper() {
        super(UrlImage.class, new GPJacksonSupport()
        );
    }

    @Override
    public String getMapperName() {
        return "Url Image Mapper";
    }

    @Override
    public String toString() {
        return getMapperName();
    }
}