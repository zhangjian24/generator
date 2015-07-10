package generator.test;

import generator.conf.DbConf;
import generator.meta.MetaProvider;

import java.sql.SQLException;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.collections.MapUtils;
import org.junit.Test;

public class MetaProviderTest {

	@Test
	public void testGetMeta() {
		DbConf conf=new DbConf();
		Assert.assertTrue(conf.parseConf("db"));
		MetaProvider metaProvider=new MetaProvider(conf);
		try {
			Map<String, Object> modelMeta = metaProvider.getMeta("domain");
			Assert.assertTrue(MapUtils.isNotEmpty(modelMeta));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
