package generator.conf;

import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据库配置实体
 * 
 * @author zh
 *
 */
public class DbConf {
	private static final Log log = LogFactory.getLog(DbConf.class);
	private String dbDriver;
	private String url;
	private String user;
	private String password;

	/**
	 * 从文件加载配置
	 * 
	 * @param absPath
	 * @return
	 */
	public Boolean parseConf(String simpleFileName) {
		try {
			ResourceBundle cFile = ResourceBundle.getBundle(simpleFileName);
			if (cFile.containsKey("dbDriver")) {
				this.setDbDriver(cFile.getString("dbDriver"));
				log.debug(this.getDbDriver());
			}
			if (cFile.containsKey("url")) {
				this.setUrl(cFile.getString("url"));
				log.debug(this.getUrl());
			}
			if (cFile.containsKey("user")) {
				this.setUser(cFile.getString("user"));
				log.debug(this.getUser());
			}
			if (cFile.containsKey("password")) {
				this.setPassword(cFile.getString("password"));
				log.debug(this.getPassword());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	public static boolean avilible(DbConf db) {
		if (db == null || StringUtils.isBlank(db.dbDriver) || StringUtils.isBlank(db.url) || StringUtils.isBlank(db.user) || StringUtils.isBlank(db.password)) {
			return false;
		}
		return true;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = StringUtils.trimToNull(dbDriver);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = StringUtils.trimToNull(url);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = StringUtils.trimToNull(user);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = StringUtils.trimToNull(password);
	}

}
