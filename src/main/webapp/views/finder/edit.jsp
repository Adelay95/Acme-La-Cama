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

<form:form action="${requestURI}" modelAttribute="finder">
<!-- Formularios -->
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="client" />
	
	<acme:textbox code="finder.hotelName" path="hotelName"/>
	<br />
	
	<form:label path="terrain">
		<spring:message code="finder.terrain" />:
	</form:label>
	<form:select id="terrain" path="terrain">
     <form:option value="" label="----" /> 
     <jstl:forEach var="t" items="${terrains}">
     <form:option value="${t}" label="${t}" />
     </jstl:forEach>  
     </form:select>
			<form:errors cssClass="error" path="terrain" />
	<br />
	<br />
	<form:label path="kindOfRoom">
		<spring:message code="finder.kindOfRoom" />:
	</form:label>
	<form:select id="kindOfRoom" path="kindOfRoom">
     <form:option value="" label="----" /> 
     <jstl:forEach var="k" items="${kindOfRooms}">
     <form:option value="${k}" label="${k}" />
     </jstl:forEach>  
     </form:select>
			<form:errors cssClass="error" path="kindOfRoom" />
	<br />
	<br />
	<form:label path="kindOfOffert">
		<spring:message code="finder.kindOfOffert" />:
	</form:label>
	<form:select id="kindOfOffert" path="kindOfOffert">
     <form:option value="" label="----" /> 
     <jstl:forEach var="k2" items="${kindOfOfferts}">
     <form:option value="${k2}" label="${k2}" />
     </jstl:forEach>  
     </form:select>
			<form:errors cssClass="error" path="kindOfOffert" />
	<br />
	<br />
	<acme:textbox code="finder.checkIn" path="checkIn"/>
	<br />
	
	<acme:textbox code="finder.checkOut" path="checkOut"/>
	<br />
	<acme:textbox code="finder.capacity" path="capacity"/>
	<br />
	<acme:textbox code="finder.minimumPrice" path="minimumPrice"/>
	<br />
	
	<acme:textbox code="finder.maximumPrice" path="maximumPrice"/>
	<br />
	
	<acme:textbox code="finder.location.province" path="province"/>
	<br />
	
	<acme:textbox code="finder.location.population" path="population"/>
	<br />
	

	
<!-- Acciones -->
	<acme:submit code="finder.save" name="save" />&nbsp; 
	
	<acme:cancel code="finder.cancel" url="finder/client/display.do" />
	
	
</form:form>