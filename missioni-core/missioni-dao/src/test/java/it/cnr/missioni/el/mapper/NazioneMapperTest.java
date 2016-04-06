package it.cnr.missioni.el.mapper;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;


/**
 * @author Salvia Vito
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NazioneMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(NazioneMapperTest.class);
    //
    private final NazioneMapper nazioneMapper = new NazioneMapper();

    @Test
    public void writeEsempio1AsStringTest() throws Exception {
        logger.info("\n\n@@@@@@@@@@@@@@@@@@Mapper : {}, write as String : "
                + "{}", nazioneMapper, nazioneMapper
                .writeAsString(nazioneMapper
                        .read(new ClassPathResource("esempioNazione.json").getFile())));
    }

    @Test
    public void readEsempio1FromFileTest() throws Exception {
        logger.info("\n\n@@@@@@@@@@@@@@@@Mapper : {}, read from File : {}\n\n",
                nazioneMapper, nazioneMapper
                        .read(new ClassPathResource("esempioNazione.json").getFile()));
    }

}
