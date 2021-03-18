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

<form:form action="${requestURI}" modelAttribute="formTrip">
<!-- Formularios -->
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="hotel" />
	
	
	
	<acme:textbox code="trip.title" path="title" />
	<br/>
	<acme:textbox code="trip.img" path="picture" />
	<br/>
	<acme:textbox code="trip.openingTime" path="openingTime" />
	<br/>
	<acme:textbox code="trip.closingTime" path="closingTime" />
	<br/>
	<acme:textbox code="trip.price" path="price" />
	<br/>
	<acme:textbox code="trip.location.population" path="location.population" />
	<br/>
	<acme:textbox code="trip.location.province" path="location.province" />
	<br/>
	<acme:textbox code="trip.location.gpsCoords" path="location.gpsCoords" />
	<br/>
	<acme:textbox code="trip.checkIn" path="checkIn" />
	<br/>
	<acme:textbox code="trip.checkOut" path="checkOut" />
	<br/>
	
	
	
	
    
<!-- Acciones -->
    <acme:submit code="trip.save" name="save" />
	<jstl:if test="${formTrip.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="trip.delete" />"
			onclick="return confirm('<spring:message code="trip.confirm.delete" />')" />&nbsp;
			</jstl:if>
	
		<acme:cancel code="trip.cancel" url="trip/list.do" />
	

	
</form:form>