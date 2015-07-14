package generator.meta;

import generator.conf.DbConf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MetaProvider {
	private static final Log log = LogFactory.getLog(MetaProvider.class);
	private DbConf db;

	public MetaProvider(DbConf db) {
		this.db = db;
	}

	/**
	 * 
	 * @param tableName
	 * @return tableName-对应表的名字; className-对应model的类; comment-表注释;
	 *         feilds-model字段列表 dataType-表字段类型; feildType-model类字段类型;
	 *         columnName-表中字段名;
	 *         simpleFeildType-model类字段的简写;feildIndex-model中字段的索引;
	 *         feildName-model中字段名; columnComment-表字段注释;
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map<String, Object> getMeta(String tableName) throws ClassNotFoundException, SQLException {
		if (StringUtils.isBlank(tableName) || !DbConf.avilible(this.db)) {
			return null;
		}
		Map<String, Object> modelMeta = new HashMap<String, Object>();
		// 加载驱动
		Class.forName(db.getDbDriver());
		Connection conn = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPassword());
		log.debug(conn);
		if (conn == null) {
			return null;
		}
		// 生成Statement
		Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		log.debug(stat);
		if (stat == null) {
			return null;
		}

		// 获取表信息
		String tableSql = "select * from information_schema.tables where table_name='" + tableName + "'";
		ResultSet tableResult = stat.executeQuery(tableSql);
		String comment = "";
		if (tableResult.next()) {
			comment = tableResult.getString("TABLE_COMMENT");
		}
		tableResult.close();
		String className = this.getClassName(tableName);
		modelMeta.put("tableName", tableName);
		modelMeta.put("className", className);
		modelMeta.put("comment", comment);
		log.debug("{tableName:" + tableName + ",className:" + className + ",comment:" + comment + "}");

		// 查询数据表字段信息
		String columnSql = "select * from information_schema.COLUMNS where table_name='" + tableName + "'";
		log.debug(columnSql);
		ResultSet columnResult = stat.executeQuery(columnSql);
		log.debug(columnResult);
		if (columnResult == null) {
			return null;
		}
		List<String> imports=new ArrayList<String>();
		List<Map<String, Object>> feilds = new ArrayList<Map<String, Object>>();
		while (columnResult.next()) {
			Map<String, Object> feild = new HashMap<String, Object>();
			// table 中的datatype
			String dataType = columnResult.getString("DATA_TYPE");
			feild.put("dataType", dataType);
			// model中的字段类型
			String feildType = this.getColumnType(dataType);
			feild.put("feildType", feildType);
			if(!feildType.startsWith("java.lang.") && !imports.contains(feildType)){
				imports.add(feildType);
			}
			
			String simpleFeildType = feildType;
			int index = StringUtils.lastIndexOf(feildType, ".");
			if (index > 0) {
				simpleFeildType = StringUtils.substring(feildType, index + 1);
			}
			feild.put("simpleFeildType", simpleFeildType);
			// table 中字段名称
			String columnName = columnResult.getString("COLUMN_NAME");
			feild.put("columnName", columnName);
			String feildIndex = columnResult.getString("ORDINAL_POSITION");
			feild.put("feildIndex", feildIndex);
			// model 中字段的名称
			String feildName = this.getFeild(columnName);
			feild.put("feildName", feildName);
			String columnComment = columnResult.getString("COLUMN_COMMENT");
			// table中字段的注释
			feild.put("columnComment", columnComment);
			log.debug("{dataType:" + dataType + ",feildType:" + feildType + ",simpleFeildType:" + simpleFeildType + ",columnName:" + columnName
					+ ",feildIndex:" + feildIndex + ",feildName:" + feildName + ",columnComment:" + columnComment + "}");
			feilds.add(feild);
		}
		columnResult.close();
		stat.close();
		conn.close();
		modelMeta.put("feilds", feilds);
		modelMeta.put("imports", imports);
		return modelMeta;
	}

	/**
	 * 类型映射
	 * 
	 * @param DataType
	 * @return
	 */
	private String getColumnType(String DataType) {
		String typeStr = "";

		if (StringUtils.equalsIgnoreCase(DataType, "VARCHAR")) {
			typeStr = "java.lang.String";
		} else if (StringUtils.equalsIgnoreCase(DataType, "CHAR")) {
			typeStr = "java.lang.String";
		} else if (StringUtils.equalsIgnoreCase(DataType, "BLOB")) {
			typeStr = "java.lang.byte[]";
		} else if (StringUtils.equalsIgnoreCase(DataType, "TEXT")) {
			typeStr = "java.lang.String";
		} else if (StringUtils.equalsIgnoreCase(DataType, "MEDIUMTEXT")) {
			typeStr = "java.lang.String";
		} else if (StringUtils.equalsIgnoreCase(DataType, "INTEGER")) {
			typeStr = "java.lang.Integer";
		} else if (StringUtils.equalsIgnoreCase(DataType, "INT")) {
			typeStr = "java.lang.Integer";
		} else if (StringUtils.equalsIgnoreCase(DataType, "TINYINT")) {
			typeStr = "java.lang.Integer";
		} else if (StringUtils.equalsIgnoreCase(DataType, "SMALLINT")) {
			typeStr = "java.lang.Integer";
		} else if (StringUtils.equalsIgnoreCase(DataType, "MEDIUMINT")) {
			typeStr = "java.lang.Integer";
		} else if (StringUtils.equalsIgnoreCase(DataType, "BIT")) {
			typeStr = "java.lang.Boolean";
		} else if (StringUtils.equalsIgnoreCase(DataType, "BIGINT")) {
			typeStr = "java.math.BigInteger";
		} else if (StringUtils.equalsIgnoreCase(DataType, "FLOAT")) {
			typeStr = "java.lang.Float";
		} else if (StringUtils.equalsIgnoreCase(DataType, "DOUBLE")) {
			typeStr = "java.lang.Double";
		} else if (StringUtils.equalsIgnoreCase(DataType, "DECIMAL")) {
			typeStr = "java.math.BigDecimal";
		} else if (StringUtils.equalsIgnoreCase(DataType, "DATE")) {
			typeStr = "java.util.Date";
		} else if (StringUtils.equalsIgnoreCase(DataType, "TIME")) {
			typeStr = "java.util.Date";
		} else if (StringUtils.equalsIgnoreCase(DataType, "DATETIME")) {
			typeStr = "java.util.Date";
		} else if (StringUtils.equalsIgnoreCase(DataType, "TIMESTAMP")) {
			typeStr = "java.util.Date";
		} else if (StringUtils.equalsIgnoreCase(DataType, "YEAR")) {
			typeStr = "java.util.Date";
		}
		return typeStr;
	}

	/**
	 * 获取java类的字段
	 * 
	 * @param columnName
	 * @return
	 */
	private String getFeild(String columnName) {
		StringBuffer feild = new StringBuffer();
		if (StringUtils.isNotBlank(columnName)) {
			String[] subStrs = StringUtils.split(columnName, "_");
			feild.append(StringUtils.lowerCase(subStrs[0]));
			for (int i = 1; i < subStrs.length; i++) {
				feild.append(StringUtils.upperCase(StringUtils.substring(subStrs[i], 0, 1))).append(StringUtils.substring(subStrs[i], 1, subStrs[i].length()));
			}
		}
		return feild.toString();
	}

	/**
	 * 同表名获取类名
	 * 
	 * @param tableName
	 * @return
	 */
	private String getClassName(String tableName) {
		StringBuffer className = new StringBuffer();
		if (StringUtils.isNotBlank(tableName)) {
			String[] subStrs = StringUtils.split(tableName, "_");
			for (String sub : subStrs) {
				className.append(StringUtils.upperCase(StringUtils.substring(sub, 0, 1))).append(StringUtils.lowerCase(StringUtils.substring(sub, 1)));
			}
		}
		return className.toString();
	}
}
