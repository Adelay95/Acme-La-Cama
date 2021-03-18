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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<!-- Listing grid -->

<form:form action="${requestURI}" modelAttribute="reservation">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="client" />
	<form:hidden path="rooms" />
	<form:hidden path="bill" />
	<form:hidden path="numDays" />
	
	<spring:message code="reservation.setAttributes"/>
	<br />
	<form:label path="kindOfOffert">
		<spring:message code="reservation.kindOfOffert" />:
	</form:label>
	<form:select id="kindOfOffert" path="kindOfOffert">
     <jstl:forEach var="k" items="${kindOfOffert}">
     <form:option value="${k}" label="${k}" />
     </jstl:forEach>  
     </form:select>
			<form:errors cssClass="error" path="kindOfOffert" />
	
	
	
<!-- Acciones -->
	<acme:submit code="reservation.save" name="save2" />
	<acme:cancel code="reservation.cancel" url="reservation/list.do" />
	
</form:form>
