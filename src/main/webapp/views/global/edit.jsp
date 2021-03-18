<%--
 * edit.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="global">
<!-- Formularios -->
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="season">
	<spring:message code="global.season" />:
	</form:label>
	<form:select id="season" path="season">
     <jstl:forEach var="s" items="${season}">
     <form:option value="${s}" label="${s}" />
     </jstl:forEach>  
     </form:select>
			<form:errors cssClass="error" path="season" />
	
	
	<acme:textbox code="global.requestPriceDay" path="requestPriceDay" />
	<br />
	
<!-- Acciones -->
        <acme:submit code="global.save" name="save" />
		<acme:cancel code="global.cancel" url="" />
</form:form>