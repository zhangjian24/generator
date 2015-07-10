package generator.test;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import generator.conf.DbConf;
import generator.meta.MetaProvider;
import generator.render.Render;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class RenderTest {

	@Test
	public void testRender() throws SQLException, ClassNotFoundException, IOException {
		DbConf db=new DbConf();
		Assert.assertTrue(db.parseConf("db"));
		MetaProvider mpro=new MetaProvider(db); 
		Map<String, Object> modelMeta= mpro.getMeta("domain");
		Configuration conf=new Configuration();
		File dirf = new File("/home/zh/workspace/generator/src/generator/template");
		Assert.assertTrue(dirf.exists());
		conf.setDirectoryForTemplateLoading(dirf);
		conf.setDefaultEncoding("UTF-8");
		Render rd=new Render(conf);
		String templName="model_java.ftl";
		String outPath="/home/zh/workspace/generator/src/generator/des/Domain.java";
		try {
			rd.render(templName, outPath, modelMeta);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

}
