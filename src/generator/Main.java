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
		String modelUrl=File.separator+StringUtils.replace(modelPkg, ".", File.separator);
		String modelPath = targetUrl+modelUrl+File.separatorChar+modelMeta.get("className")+".java";
		modelMeta.put("modelPkg", modelPkg);
		rd.render(modelTpl, modelPath, modelMeta);

		//dto类生成
		String dtoTpl="dto_java.ftl";
		String dtoPkg = generatorConf.getString("target.dto.pkg");
		String dtoUrl=File.separator+StringUtils.replace(dtoPkg, ".", File.separator);
		String dtoPath = targetUrl+dtoUrl+File.separatorChar+modelMeta.get("className")+"Dto.java";
		modelMeta.put("dtoPkg", dtoPkg);
		rd.render(dtoTpl, dtoPath, modelMeta);
		
		//sqlmap生成
		String sqlmapTpl="sqlmap_xml.ftl";
		String sqlmapPath=targetUrl+modelUrl+File.separatorChar+modelMeta.get("className")+"-sqlmap.xml";
		rd.render(sqlmapTpl, sqlmapPath, modelMeta);
		
		//dao类生成
		String daoTpl="dao_java.ftl";
		String daoPkg=generatorConf.getString("target.dao.pkg");
		String daoUrl=File.separator+StringUtils.replace(daoPkg, ".", File.separator);
		String daoPath=targetUrl+daoUrl+File.separatorChar+modelMeta.get("className")+"Dao.java";
		modelMeta.put("daoPkg", daoPkg);
		rd.render(daoTpl, daoPath, modelMeta);
		
		//daoImpl类生成
		String daoImplTpl="dao_impl_java.ftl";
		String daoImplPkg=generatorConf.getString("target.dao.impl.pkg");
		String daoImplUrl=File.separator+StringUtils.replace(daoImplPkg, ".", File.separator);
		String daoImplPath=targetUrl+daoImplUrl+File.separatorChar+modelMeta.get("className")+"DaoImpl.java";
		modelMeta.put("daoImplPkg", daoImplPkg);
		rd.render(daoImplTpl, daoImplPath, modelMeta);
		
		//service类生成
		String serviceTpl="service_java.ftl";
		String servicePkg=generatorConf.getString("target.service.pkg");
		String serviceUrl=File.separator+StringUtils.replace(servicePkg, ".", File.separator);
		String servicePath=targetUrl+serviceUrl+File.separatorChar+modelMeta.get("className")+"Service.java";
		modelMeta.put("servicePkg", servicePkg);
		rd.render(serviceTpl, servicePath, modelMeta);
		
		//serviceImpl类生成
		String serviceImplTpl="service_impl_java.ftl";
		String serviceImplPkg=generatorConf.getString("target.service.impl.pkg");
		String serviceImplUrl=File.separator+StringUtils.replace(serviceImplPkg, ".", File.separator);
		String serviceImplPath=targetUrl+serviceImplUrl+File.separatorChar+modelMeta.get("className")+"ServiceImpl.java";
		modelMeta.put("serviceImplPkg", serviceImplPkg);
		rd.render(serviceImplTpl, serviceImplPath, modelMeta);
	}
}
