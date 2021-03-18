<%--
 * list.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div id="contenidos" class="mostrar">
		<div id="estadisticas">
		<fieldset>
		<spring:message code="admin.statistics" var="statisticsHeader" />
			<legend><b><jstl:out value="${statisticsHeader}" /></b></legend>
		<table>
		<tr><td><table>
				
				
				<spring:message code="admin.min" var="ocupacionHeader" />
				<tr>
					<th><jstl:out value="${ocupacionHeader}: " /></th>
					<td><jstl:out value="${min}" /></td>
				</tr>
				
				<spring:message code="admin.avg" var="commentHeader" />
				<tr>
					<th><jstl:out value="${commentHeader}: " /></th>
					<td><jstl:out value="${avg}" /></td>
				</tr>
				
				
				<spring:message code="admin.max" var="billHeader" />
				<tr>
					<th><jstl:out value="${billHeader}: " /></th>
					<td><jstl:out value="${max}" /></td>
				</tr>
				
				<spring:message code="admin.minWorker" var="reservaHeader" />
				<tr>
					<th><jstl:out value="${reservaHeader}: " /></th>
					<td><jstl:out value="${minWorker}" /></td>
				</tr>
				
				<spring:message code="admin.avgWorker" var="trpHeader" />
				<tr>
					<th><jstl:out value="${trpHeader}: " /></th>
					<td><jstl:out value="${avgWorker}" /></td>
				</tr>
				<spring:message code="admin.maxWorker" var="trpHeader" />
				<tr>
					<th><jstl:out value="${trpHeader}: " /></th>
					<td><jstl:out value="${maxWorker}" /></td>
				</tr>
				<tr>
				<spring:message code="admin.hotel" var="chorbifeeHeader" />
				  <tr><td><h4><a href="statistics/hotel.do"><jstl:out value="${chorbifeeHeader}" /></a></h4>
				  </td>
				  
				  <tr>
				<spring:message code="admin.manager" var="chorbifeeHeader" />
				  <tr><td><h4><a href="statistics/manajer.do"><jstl:out value="${chorbifeeHeader}" /></a></h4>
				  </td>
				  
				</table>
			</table>
			</fieldset>
			</div>
		</div>
