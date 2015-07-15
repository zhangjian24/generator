package ${servicePkg};

import java.util.List;

import ${modelPkg}.${className?if_exists};
import ${dtoPkg}.${className?if_exists}Dto;

/**
 * @author wanfeizhang
 * @date 2015-07-13 12:06:59
 */
public interface ${className?if_exists}Service {

	/**
	 * 添加记录
	 *
	 * @param ${className?uncap_first} ${className?uncap_first}
	 * @return PrimaryKey
	 */
	public Integer add${className?if_exists}(${className?if_exists} ${className?uncap_first});

	/**
	 * 根据ID获取记录
	 *
	 * @param id 主键ID
	 * @return ${className?if_exists}
	 */
	public ${className?if_exists} find${className?if_exists}(Integer id);

	/**
	 * 根据ID删除记录
	 *
	 * @param id 主键ID
	 * @return 删除记录的条数
	 */
	public Integer delete${className?if_exists}(Integer id);

	/**
	 * 更新所有字段
	 *
	 * @param ${className?uncap_first} ${className?uncap_first}
	 * @return 更新记录的条数
	 */
	public Integer update${className?if_exists}(${className?if_exists} ${className?uncap_first});

	/**
	 * 更新不为NULL字段信息
	 *
	 * @param ${className?uncap_first} ${className?uncap_first}
	 * @return 更新记录的条数
	 */
	public Integer update${className?if_exists}Selective(${className?if_exists} ${className?if_exists});

	/**
	 * 获取满足条件的记录，不带分页功能
	 *
	 * @param dto 查询条件
	 * @return 满足条件的记录
	 */
	public List<${className?if_exists}> find${className?if_exists}List(${className?if_exists}Dto dto);

	/**
	 * 获取满足条件的记录，带分页功能
	 *
	 * @param dto 查询条件
	 * @param startNo 开始行
	 * @param pageSize 每页记录数
	 * @return 满足条件的记录
	 */
	public List<${className?if_exists}> find${className?if_exists}WithPg(${className?if_exists}Dto dto, Long startNo, Integer pageSize);

	/**
	 * 获取满足条件的记录数
	 *
	 * @param dto 查询条件
	 * @return delete recode count
	 */
	public Integer countFind${className?if_exists}WithPg(${className?if_exists}Dto dto);

}