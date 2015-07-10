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

	public Map<String, Object> getMeta(String tableName) throws ClassNotFoundException, SQLException {
		if (StringUtils.isBlank(tableName) || !DbConf.avilible(this.db)) {
			return null;
		}
		Map<String, Object> modelMeta=new HashMap<String, Object>();
		//加载驱动
		Class.forName(db.getDbDriver());
		Connection conn = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPassword());
		log.debug(conn);
		if (conn == null) {
			return null;
		}
		
		//获取字段信息
		Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		log.debug(stat);
		if (stat == null) {
			return null;
		}
		//获取表信息
		String tableSql = "select * from information_schema.tables where table_name='"+tableName+"'";
		ResultSet tableResult = stat.executeQuery(tableSql);
		String comment="";
		if(tableResult.next()){
			comment=tableResult.getString("TABLE_COMMENT");
		}
		tableResult.close();
		modelMeta.put("className", this.getClassName(tableName));
		modelMeta.put("comment", comment);
		
		//查询数据表字段信息
		String columnSql = "select * from information_schema.COLUMNS where table_name='"+tableName+"'";
		log.debug(columnSql);
		ResultSet columnResult = stat.executeQuery(columnSql);
		log.debug(columnResult);
		if (columnResult == null) {
			return null;
		}
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		while(columnResult.next()){
			Map<String, Object> column = new HashMap<String, Object>();
			String typeName =this.getColumnType(columnResult.getString("DATA_TYPE"));
			column.put("typeName", typeName);
			log.debug("typeName:" + typeName);
			String name=this.getFeild(columnResult.getString("COLUMN_NAME"));
			column.put("name", name);
			log.debug("name:" + name);
			column.put("upName", StringUtils.upperCase(StringUtils.substring(name, 0, 1))+StringUtils.substring(name, 1));
			String colComment = columnResult.getString("COLUMN_COMMENT");
			column.put("comment", colComment);
			log.debug("comment:"+colComment);
			columns.add(column);
		}
		columnResult.close();
		stat.close();
		conn.close();
		modelMeta.put("columns", columns);
		return modelMeta;
	}

	/**
	 * 类型映射
	 * @param DataType
	 * @return
	 */
	private String getColumnType(String DataType) {
		String typeStr = "";
		
		if(StringUtils.equalsIgnoreCase(DataType, "VARCHAR")){
			typeStr="String";   
		}else if(StringUtils.equalsIgnoreCase(DataType, "CHAR")){
			typeStr="String";
		}else if(StringUtils.equalsIgnoreCase(DataType, "BLOB")){
			typeStr="byte[]";
		}else if(StringUtils.equalsIgnoreCase(DataType, "TEXT")){
			typeStr="String";
		}else if(StringUtils.equalsIgnoreCase(DataType, "MEDIUMTEXT")){
			typeStr="String";
		}else if(StringUtils.equalsIgnoreCase(DataType, "INTEGER")){
			typeStr="Integer";
		}else if(StringUtils.equalsIgnoreCase(DataType, "INT")){
			typeStr="Integer";
		}else if(StringUtils.equalsIgnoreCase(DataType, "TINYINT")){
			typeStr="Integer";
		}else if(StringUtils.equalsIgnoreCase(DataType, "SMALLINT")){
			typeStr="Integer";
		}else if(StringUtils.equalsIgnoreCase(DataType, "MEDIUMINT")){
			typeStr="Integer";
		}else if(StringUtils.equalsIgnoreCase(DataType, "BIT")){
			typeStr="Boolean";
		}else if(StringUtils.equalsIgnoreCase(DataType, "BIGINT")){
			typeStr="BigInteger";
		}else if(StringUtils.equalsIgnoreCase(DataType, "FLOAT")){
			typeStr="Float";
		}else if(StringUtils.equalsIgnoreCase(DataType, "DOUBLE")){
			typeStr="Double";
		}else if(StringUtils.equalsIgnoreCase(DataType, "DECIMAL")){
			typeStr="BigDecimal";
		}else if(StringUtils.equalsIgnoreCase(DataType, "DATE")){
			typeStr="Date";
		}else if(StringUtils.equalsIgnoreCase(DataType, "TIME")){
			typeStr="Date";
		}else if(StringUtils.equalsIgnoreCase(DataType, "DATETIME")){
			typeStr="Date";
		}else if(StringUtils.equalsIgnoreCase(DataType, "TIMESTAMP")){
			typeStr="Date";
		}else if(StringUtils.equalsIgnoreCase(DataType, "YEAR")){
			typeStr="Date";
		}
		return typeStr;
	}

	/**
	 * 获取java类的字段
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
	 * @param tableName
	 * @return
	 */
	private String getClassName(String tableName){
		StringBuffer className=new StringBuffer();
		if(StringUtils.isNotBlank(tableName)){
			String[] subStrs = StringUtils.split(tableName, "_");
			for(String sub:subStrs){
				className.append(StringUtils.upperCase(StringUtils.substring(sub, 0, 1))).append(StringUtils.lowerCase(StringUtils.substring(sub, 1)));
			}
		}
		return className.toString();
	}
}
