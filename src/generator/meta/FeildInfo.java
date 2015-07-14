package generator.meta;

public enum FeildInfo {
	UNKNOWN();
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
