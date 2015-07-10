package generator.render;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Render {

	private Configuration conf;

	public Render(Configuration conf) {
		this.conf = conf;
	}

	public void render(String tplFileName, String outAbsPath, Map<String,Object> modelMeta) throws IOException, TemplateException, SQLException {
		if (StringUtils.isBlank(tplFileName) || StringUtils.isBlank(outAbsPath) || MapUtils.isEmpty(modelMeta)) {
			return;
		}
		Template tpl = conf.getTemplate(tplFileName);
		Writer out = new FileWriter(new File(outAbsPath));
		tpl.process(modelMeta, out);
		out.flush();
		out.close();
	}
}