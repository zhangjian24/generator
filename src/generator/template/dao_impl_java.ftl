package ${daoImplPkg?if_exists};

import org.springframework.stereotype.Repository;

import java.util.List;
import ${daoPkg}.${className}Dao;
import ${modelPkg}.${className};
import ${dtoPkg}.${className}Dto;


@Repository(value = "sc${className}Dao")
public class ${className}DaoImpl extends BaseDaoImpl implements ${className}Dao {

	@Override
	public Integer add${className}(${className} ${className?uncap_first}) {
		return (Integer) this.getSqlMapClientTemplate().insert("${className}SqlMap.add${className}", ${className?uncap_first});
	}

	@Override
	public ${className} find${className}(Integer id) {
		return (${className}) this.getSqlMapClientTemplate().queryForObject("${className}SqlMap.find${className}", id);
	}

	@Override
	public Integer delete${className}(Integer id) {
		return (Integer) this.getSqlMapClientTemplate().delete("${className}SqlMap.delete${className}", id);
	}

	@Override
	public Integer update${className}(${className} ${className?uncap_first}) {
		return (Integer) this.getSqlMapClientTemplate().update("${className}SqlMap.update${className}", ${className?uncap_first});
	}

	@Override
	public Integer update${className}Selective(${className} ${className?uncap_first}) {
		return (Integer) this.getSqlMapClientTemplate().update("${className}SqlMap.update${className}Selective", ${className?uncap_first});
	}

	@Override
	public List<${className}> find${className}List(${className}Dto dto) {
		return (List<${className}>) this.getSqlMapClientTemplate().queryForList("${className}SqlMap.find${className}List", dto);
	}

	@Override
	public List<${className}> find${className}WithPg(${className}Dto dto, Long startNo, Integer pageSize) {
		dto.setStartNo(startNo);
		dto.setPageSize(pageSize);
		return (List<${className}>) this.getSqlMapClientTemplate().queryForList("${className}SqlMap.find${className}WithPg", dto);
	}

	@Override
	public Integer countFind${className}WithPg(${className}Dto dto) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("${className}SqlMap.countFind${className}WithPg", dto);
	}

}