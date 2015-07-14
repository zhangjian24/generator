package generator.des.model;
import java.util.Date;
/**
*
*
*/
public class Domain { 
	/***/
	private Integer id;

	/**域名所属用户id*/
	private Integer userId;

	/**域名类型: 0-测试, 1-sendcloud 2-小白*/
	private Integer type;

	/**域名名称*/
	private String name;

	/**域名验证情况:VERIFY_KEY,CNAME,MX,SPF,DKIM(16|8|4|2|1)*/
	private Integer verify;

	/**track域名的前缀(cname)*/
	private String prefix;

	/**域名验证的key*/
	private String verifyKey;

	/**是否作为网站归宿认证 0 否 1是*/
	private Integer isAuth;

	/***/
	private Date gmtCreate;

	/***/
	private Date gmtModify;

	public Integer getId(){
		return this.id;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public Integer getType(){
		return this.type;
	}

	public String getName(){
		return this.name;
	}

	public Integer getVerify(){
		return this.verify;
	}

	public String getPrefix(){
		return this.prefix;
	}

	public String getVerifyKey(){
		return this.verifyKey;
	}

	public Integer getIsAuth(){
		return this.isAuth;
	}

	public Date getGmtCreate(){
		return this.gmtCreate;
	}

	public Date getGmtModify(){
		return this.gmtModify;
	}


public static enum FeildInfo {
	Id("id","java.lang.Integer","id",1,"int",""),User_id("userId","java.lang.Integer","user_id",2,"int","域名所属用户id"),Type("type","java.lang.Integer","type",3,"tinyint","域名类型: 0-测试, 1-sendcloud 2-小白"),Name("name","java.lang.String","name",4,"varchar","域名名称"),Verify("verify","java.lang.Integer","verify",5,"tinyint","域名验证情况:VERIFY_KEY,CNAME,MX,SPF,DKIM(16|8|4|2|1)"),Prefix("prefix","java.lang.String","prefix",6,"varchar","track域名的前缀(cname)"),Verify_key("verifyKey","java.lang.String","verify_key",7,"varchar","域名验证的key"),Is_auth("isAuth","java.lang.Integer","is_auth",8,"int","是否作为网站归宿认证 0 否 1是"),Gmt_create("gmtCreate","java.util.Date","gmt_create",9,"datetime",""),Gmt_modify("gmtModify","java.util.Date","gmt_modify",10,"datetime","");
	private String feildName;
	private String feildType;
	private String columnName;
	private Integer feildIndex;
	private String dataType;
	private String columnComment;
	
	private FeildInfo(){
		
	}
	private FeildInfo(String feildName, String feildType, String columnName, Integer feildIndex, String dataType, String columnComment) {
		this.feildName = feildName;
		this.feildType = feildType;
		this.columnName = columnName;
		this.feildIndex = feildIndex;
		this.dataType = dataType;
		this.columnComment = columnComment;
	}
	public String getFeildName() {
		return feildName;
	}
	public void setFeildName(String feildName) {
		this.feildName = feildName;
	}
	public String getFeildType() {
		return feildType;
	}
	public void setFeildType(String feildType) {
		this.feildType = feildType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Integer getFeildIndex() {
		return feildIndex;
	}
	public void setFeildIndex(Integer feildIndex) {
		this.feildIndex = feildIndex;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
}

}