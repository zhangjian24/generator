package generator.test;

import junit.framework.Assert;
import generator.conf.DbConf;

import org.junit.Test;

public class DbConfTest {

	@Test
	public void testParseConf() {
		DbConf conf=new DbConf();
		Assert.assertTrue(conf.parseConf("db"));
	}

}
