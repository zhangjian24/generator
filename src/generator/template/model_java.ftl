/**
*
*${comment?if_exists}
*/
public class ${className?if_exists} { 
	<#list columns as column>
	/**${column.comment?if_exists}*/
	private ${column.typeName?if_exists} ${column.name?if_exists};

	</#list>
	<#list columns as column>
	public ${column.typeName?if_exists} get${column.upName?if_exists}(){
		return this.${column.name?if_exists};
	}

	</#list>
}