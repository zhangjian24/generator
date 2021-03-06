package ${modelPkg?if_exists};
<#list imports as import>
import ${import};
</#list>
/**
*
*${comment?if_exists}
*/
public class ${className?if_exists} { 
	<#list feilds as feild>
	/**${feild.columnComment?if_exists}*/
	private ${feild.simpleFeildType?if_exists} ${feild.feildName?if_exists};

	</#list>
	<#list feilds as feild>
	public ${feild.simpleFeildType?if_exists} get${feild.feildName?cap_first}(){
		return this.${feild.feildName?if_exists};
	}

	</#list>

public static enum FeildInfo {
	<#list feilds as feild><#if feild_index!=0>,</#if>${feild.columnName?capitalize}("${feild.feildName?if_exists}","${feild.feildType?if_exists}","${feild.columnName?if_exists}",${feild.feildIndex?if_exists},"${feild.dataType?if_exists}","${feild.columnComment?if_exists}")</#list>;
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