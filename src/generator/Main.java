package generator;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;

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
		//系统配置
		ResourceBundle generatorConf=ResourceBundle.getBundle("generator");
		//数据库配置
		DbConf db = new DbConf();
		Assert.assertTrue(db.parseConf("db"));
		MetaProvider mpro = new MetaProvider(db);
		Map<String, Object> modelMeta = mpro.getMeta(generatorConf.getString("tableName"));

		//模板配置
		Configuration conf = new Configuration();
		File dirf = new File(Main.class.getResource("").getPath()+File.separatorChar+"template");
		conf.setDirectoryForTemplateLoading(dirf);
		conf.setDefaultEncoding("UTF-8");
		
		//模板渲染类
		Render rd = new Render(conf);
		String targetUrl=generatorConf.getString("target.url");
		
		//model类生成
		String modelTpl = "model_java.ftl";
		String modelPkg = generatorConf.getString("target.model.pkg");
		String modelUrl="/"+StringUtils.replace(modelPkg, ".", "/");
		String modelPath = targetUrl+modelUrl+File.separatorChar+modelMeta.get("className")+".java";
		modelMeta.put("modelPkg", modelPkg);
		rd.render(modelTpl, modelPath, modelMeta);

//		//dto类生成
//		String dtoTpl="dto_java.ftl";
//		String dtoPkg = generatorConf.getString("target.dto.pkg");
//		String dtoUrl="/"+StringUtils.replace(dtoPkg, ".", "/");
//		String dtoPath = targetUrl+dtoUrl+File.separatorChar+modelMeta.get("className")+"Dto.java";
//		modelMeta.put("dtoPkg", dtoPkg);
//		rd.render(dtoTpl, dtoPath, modelMeta);
	}
}
