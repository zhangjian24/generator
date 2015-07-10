package generator;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import junit.framework.Assert;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import generator.conf.DbConf;
import generator.meta.MetaProvider;
import generator.render.Render;

public class Main {

	public static void main(String[] args) {
		try {
			Main.generate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	public static void generate() throws ClassNotFoundException, SQLException, IOException, TemplateException {
		DbConf db = new DbConf();
		Assert.assertTrue(db.parseConf("db"));
		MetaProvider mpro = new MetaProvider(db);
		ResourceBundle generatorConf=ResourceBundle.getBundle("generator");
		
		Map<String, Object> modelMeta = mpro.getMeta(generatorConf.getString("tableName"));
		Configuration conf = new Configuration();
		File dirf = new File(Main.class.getResource("").getPath()+File.separatorChar+"template");
		conf.setDirectoryForTemplateLoading(dirf);
		conf.setDefaultEncoding("UTF-8");
		Render rd = new Render(conf);
		String modelTpl = "model_java.ftl";
		String dtoTpl="dto_java.ftl";
		String targetUrl=generatorConf.getString("target.url");
		String modelUrl=generatorConf.getString("target.model.url");
		String dtoUrl=generatorConf.getString("target.dto.url");
		String modelPath = targetUrl+modelUrl+File.separatorChar+modelMeta.get("className")+".java";
		String dtoPath = targetUrl+dtoUrl+File.separatorChar+modelMeta.get("className")+"Dto.java";
		rd.render(modelTpl, modelPath, modelMeta);
		rd.render(dtoTpl, dtoPath, modelMeta);
	}
}
