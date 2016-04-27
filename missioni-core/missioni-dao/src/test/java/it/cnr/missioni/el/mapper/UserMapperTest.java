package it.cnr.missioni.el.mapper;

import org.joda.time.DateTime;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import it.cnr.missioni.model.user.User;


/**
 * @author Salvia Vito
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(UserMapperTest.class);
    //
    private final UserMapper userMapper = new UserMapper();

    @Test
    public void writeEsempio1AsStringTest() throws Exception {
        logger.info("\n\n@@@@@@@@@@@@@@@@@@Mapper : {}, write as String : "
                + "{}", userMapper, userMapper
                .writeAsString(userMapper
                        .read(new ClassPathResource("esempioUser.json").getFile())));
    }

    @Test
    public void readEsempio1FromFileTest() throws Exception {
        logger.info("\n\n@@@@@@@@@@@@@@@@Mapper : {}, read from File : {}\n\n",
                userMapper, userMapper
                        .read(new ClassPathResource("esempioUser.json").getFile()));
    }
    
    @Test
    public void writeMountainNowUserAsStringTest() throws Exception {
        logger.info("##########################USER_AS_STRING : \n{}\n",
                this.userMapper.writeAsString(createUser()));
    }
    
    private User createUser(){
    	return new User(){
    		{
    			super.setDataRegistrazione(new DateTime());
    		}
    	};
    }

}
