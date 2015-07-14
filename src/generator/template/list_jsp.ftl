<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jstl/fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/my.tld"  %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>SendCloud后台系统</title>
     <link rel="stylesheet" href="/css/main.css" />
</head>
<body>
    <div class="nav">
       ${tableName}管理:${tableName}列表
       <span class="menuButton"><a href='/${lClassName}/edit${className}.do' class="create">添加${tableName}</a></span>
    </div>
    
    <div class="body">
    	<h1>${tableName}列表</h1>
        <c:if test="${'${'}error ne null}">
        	<div class="message">${'${'}error}</div>
        </c:if>
        
        <div class="search">
	       <form id="searchForm" action="/${lClassName}/list${className}.do" method="post">
		       	<table>
		       		<tr>
		       		<td>用户名或Id:<input type="text" value='${"$" + "{dto.userNameOrId}"}' id="userNameOrId" name="userNameOrId"/></td>	
		       		<#list columns as column>
						<td>${column}:<input type="text" value='${"$" + "{dto." + column + "}"}' id="${column}" name="${column}"/></td>
					</#list>
		       			<td><input type="submit" id="searchButton" value="搜索" /></td>
		       		</tr>
		       	</table>
			</form>
	    </div>
	    <br>
	    
        <div class="list">
            <table>
                <thead>
                    <tr>
                    	<th>用户名</th>
                    <#list columns as column>
						<th>
							<a 
								${column}
							</a>
						</th>
					</#list>
						<th>详情</th>
						<th>编辑</th>
                   	    <th>删除</th>
                    </tr>
                </thead>
                <tbody>
                	<c:if test="${ '$' + '{list ne null}'}">
					<c:forEach var="${lClassName}" items="${ '$' + '{list}' }" varStatus="status">
						<tr class="${'${'}(status.index % 2) == 0 ? 'odd' : 'even'}">
						<#list columns as column>
							<td>${"$" + "{" + lClassName + "." + column + "}"}</td>
						</#list>
							<td><a href='/${lClassName}/show${className}.do?id=${"$" + "{" + lClassName + ".id}" }' class="show">详情</a> </td>
							<td><a href='/${lClassName}/edit${className}.do?id=${"$" + "{" + lClassName + ".id}" }' class="edit">编辑</a> </td>
	          				<td><a href='/${lClassName}/delete${className}.do?id=${"$" + "{" + lClassName + ".id}" }' onClick="return confirm('请确认是否删除该信息?')">删除</a></td>
						</tr>
					</c:forEach>
					</c:if>
                </tbody>
            </table>
        </div>
        <div class="paginateButtons">
            <span><a href='/${lClassName}/list${className}.do?pageNo=${"$" + "{paging_vo.prePageNum}"}&sort=${"$" + "{dto.sort}"}&dir=${"$" + "{dto.dir}"}&userNameOrId=${"${" + "dto.userNameOrId}"}<#list columns as column>&${column}=${"$" + "{dto." + column + "}"}</#list>'>&laquo;上一页</a> | 
				<a href='/${lClassName}/list${className}.do?pageNo=${"$" + "{paging_vo.nextPageNum}"}&sort=${"$" + "{dto.sort}"}&dir=${"$" + "{dto.dir}"}&userNameOrId=${"${" + "dto.userNameOrId}"}<#list columns as column>&${column}=${"$" + "{dto." + column + "}"}</#list>'>下一页&raquo;</a></span>
			<span>${"$" + "{paging_vo.currentPageNum}"} /${"$" + "{paging_vo.maxPageNum}"}</span>页(共${"$" + "{paging_vo.recordTotal}"}条记录)
	       <form id="gotoForm" action="/${lClassName}/list${className}.do" method="post" style="display: inline;">
		       	<label>跳转到</label>
				<input name="pageNo" value='${"$" + "{paging_vo.currentPageNum}"}' style="width: 4em;"><label>页</label>
	       		<input type="hidden" value='${"$" + "{dto.userNameOrId}"}' id="userNameOrId" name="userNameOrId"/>	
	       		<#list columns as column>
					<input type="hidden" value='${"$" + "{dto." + column + "}"}' id="${column}" name="${column}"/>
				</#list>
				<input type="submit" value="跳转" />
			</form>
        </div>
    </div>
</body>
</html>