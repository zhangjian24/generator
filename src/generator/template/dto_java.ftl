package ${dtoPkg?if_exists};
<#list imports as import>
import ${import};
</#list>
/**
*
*${comment?if_exists}
*/
public class ${className?if_exists}Dto extends BaseDto{ 
	<#list feilds as feild>
	/**${feild.columnComment?if_exists}*/
	private ${feild.simpleFeildType?if_exists} ${feild.feildName?if_exists};

	</#list>
	<#list feilds as feild>
	public ${feild.simpleFeildType?if_exists} get${feild.feildName?cap_first}(){
		return this.${feild.feildName?if_exists};
	}

	</#list>
}