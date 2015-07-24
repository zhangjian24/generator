<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE sqlMap PUBLIC "-//ibatis.apache.org//DTD SQL Map 2.0//EN" "http://ibatis.apache.org/dtd/sql-map-2.dtd" >
<sqlMap namespace="${className?if_exists}SqlMap">

<!-- 为类申明一个别名-->
 <typeAlias alias="${className?if_exists}" type="${modelPkg?if_exists}.${className?if_exists}"/>
 <typeAlias alias="${className?if_exists}Dto" type="${dtoPkg?if_exists}.${className?if_exists}Dto"/>

<!--  默认查询结果映射 -->
<resultMap id="${className?if_exists}Result" class="${className?if_exists}">
	<#list feilds as feild>
	<result column="${feild.columnName}" property="${feild.feildName}"/>
	</#list>
</resultMap>

<!--  保存方法 -->
<insert id="add${className?if_exists}" parameterClass="${className?if_exists}">
  insert into ${tableName} (
<#list feilds as feild><#if feild_index!=0>,</#if>${feild.columnName}</#list>
) values (
<#list feilds as feild><#if feild_index!=0>,</#if>${feild.feildName}</#list>
)
  <selectKey keyProperty="id" resultClass="int">
      SELECT @@IDENTITY AS id
  </selectKey>
</insert>

<!--  根据ID查询记录 -->
<select id="find${className?if_exists}" resultMap="${className?if_exists}Result" parameterClass="Integer">
    select 
<#list feilds as feild><#if feild_index!=0>,</#if>t.${feild.columnName}</#list>
 	from ${tableName} t where t.id=#id#
</select>

<!--  根据ID删除记录 -->
<delete id="delete${className?if_exists}" parameterClass="Integer">
   delete from ${tableName}  where id=#id#
</delete>

<!--  更新所有字段信息 -->
<update id="update${className?if_exists}" parameterClass="${className?if_exists}">
    update ${tableName} t set 
<#list feilds as feild><#if feild_index!=0>,</#if>t.${feild.columnName}=#${feild.feildName}#</#list>
    where id=#id#
</update>

<!--  根据dto更新选中字段 -->
<update id="update${className?if_exists}Selective" parameterClass="${className?if_exists}">
    update ${tableName} t 
       <dynamic prepend="set">
		<#list feilds as feild>
	  	<isNotNull prepend="," property="${feild.feildName}">
		t.${feild.columnName}=#${feild.feildName}#
	  	</isNotNull>
		</#list>              
       </dynamic>
 where t.id=#id#
</update>

<!--  dto查询公共部分 -->
<sql id="find${className?if_exists}ByDto">
   select 
	<#list feilds as feild><#if feild_index!=0>,</#if>t.${feild.columnName}</#list>
</sql>

<!--  dto查询公共WHERE部分 -->
<sql id="find${className?if_exists}Where">
 from ${tableName} t where t.id is not null      
		<#list feilds as feild>
	  	<isNotEmpty prepend="," property="${feild.feildName}">
		t.${feild.columnName}=#${feild.feildName}#
	  	</isNotEmpty>
		</#list>              
</sql>

<!-- 根据dto查询记录 -->
<select id="find${className?if_exists}List" parameterClass="${className?if_exists}Dto" resultMap="${className?if_exists}Result">
   <include refid="find${className?if_exists}ByDto"/>
   <include refid="find${className?if_exists}Where"/>
   <isNotEmpty property="sort">order by $sort$ $dir$</isNotEmpty>
</select>

<!-- 根据dto查询总条数 -->
<select id="countFind${className?if_exists}WithPg" parameterClass="${className?if_exists}Dto"  resultClass="java.lang.Integer">
     SELECT count(*) PG_TOTALCOUNT 
        <include refid="find${className?if_exists}Where" />
</select>

<!-- 根据dto分页查询记录 -->
<select id="find${className?if_exists}WithPg" parameterClass="${className?if_exists}Dto" resultMap="${className?if_exists}Result">
   <include refid="find${className?if_exists}ByDto"/>
   <include refid="find${className?if_exists}Where"/>
   <isNotEmpty property="sort">order by $sort$ $dir$</isNotEmpty>
    LIMIT #startNo#,  #pageSize#
</select>

</sqlMap>