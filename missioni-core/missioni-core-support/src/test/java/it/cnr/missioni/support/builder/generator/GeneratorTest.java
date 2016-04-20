package it.cnr.missioni.support.builder.generator;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratorTest {
	
    private static final Logger logger = LoggerFactory.getLogger(GeneratorTest.class);

	@Test
	public void md5GeneratorTest(){
		String md5Password = IMd5PasswordGenerator.Md5PasswordGenerator.getMd5PasswordGenerator().withPassword("prova").build();
		Assert.assertTrue("MD5 PASSWORD GENERATOR", md5Password.equals("189bbbb00c5f1fb7fba9ad9285f193d1"));
	}
	
	@Test
	public void randomPasswordGeneratorTest(){
		String password = IRandomPasswordGenerator.RandomPasswordGenerator.getRandomPasswordGenerator().withLenght(8).withSeed(DateTime.now(DateTimeZone.UTC).toString().getBytes()).build();
		logger.info("###############RANDOM PASSWORD GENERATOR {}\n",password);
	}
	
}
