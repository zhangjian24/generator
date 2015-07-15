package ${daoPkg?if_exists};

import java.util.List;
import ${modelPkg?if_exists}.${className};
import ${dtoPkg?if_exists}.${className}Dto;

public interface ${className?if_exists}Dao {

	/**
	 * 添加记录
	 *
	 * @param ${className?uncap_first} ${className?uncap_first}
	 * @return PrimaryKey
	 */
	public Integer add${className}(${className} ${className?uncap_first});

	/**
	 * 根据ID获取记录
	 *
	 * @param id 主键ID
	 * @return ${className}
	 */
	public ${className} find${className}(Integer id);

	/**
	 * 根据ID删除记录
	 *
	 * @param id 主键ID
	 * @return 删除记录的条数
	 */
	public Integer delete${className}(Integer id);

	/**
	 * 更新所有字段
	 *
	 * @param ${className?uncap_first} ${className?uncap_first}
	 * @return 更新记录的条数
	 */
	public Integer update${className}(${className} ${className?uncap_first});

	/**
	 * 更新不为NULL字段信息
	 *
	 * @param ${className?uncap_first} ${className?uncap_first}
	 * @return 更新记录的条数
	 */
	public Integer update${className}Selective(${className} ${className?uncap_first});

	/**
	 * 获取满足条件的记录，不带分页功能
	 *
	 * @param dto 查询条件
	 * @return 满足条件的记录
	 */
	public List<${className}> find${className}List(${className}Dto dto);

	/**
	 * 获取满足条件的记录，带分页功能
	 *
	 * @param dto 查询条件
	 * @param startNo 开始行
	 * @param pageSize 每页记录数
	 * @return 满足条件的记录
	 */
	public List<${className}> find${className}WithPg(${className}Dto dto, Long startNo, Integer pageSize);

	/**
	 * 获取满足条件的记录数
	 *
	 * @param dto 查询条件
	 * @return delete recode count
	 */
	public Integer countFind${className}WithPg(${className}Dto dto);

}