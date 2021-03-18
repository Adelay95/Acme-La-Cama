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
				
				
				<spring:message code="admin.ocupacion" var="ocupacionHeader" />
				<tr>
					<th><jstl:out value="${ocupacionHeader}: " /></th>
					<td><jstl:out value="${ocupacion}" /></td>
				</tr>
				
				<spring:message code="admin.avg.comment" var="commentHeader" />
				<tr>
					<th><jstl:out value="${commentHeader}: " /></th>
					<td><jstl:out value="${comment}" /></td>
				</tr>
				
				
				<spring:message code="admin.avg.bill" var="billHeader" />
				<tr>
					<th><jstl:out value="${billHeader}: " /></th>
					<td><jstl:out value="${bill}" /></td>
				</tr>
				
				<spring:message code="admin.total.reserva" var="reservaHeader" />
				<tr>
					<th><jstl:out value="${reservaHeader}: " /></th>
					<td><jstl:out value="${reserva}" /></td>
				</tr>
				
				<spring:message code="admin.avg.trip" var="trpHeader" />
				<tr>
					<th><jstl:out value="${trpHeader}: " /></th>
					<td><jstl:out value="${trip}" /></td>
				</tr>
				</table>
			</table>
			</fieldset>
			</div>
		</div>
