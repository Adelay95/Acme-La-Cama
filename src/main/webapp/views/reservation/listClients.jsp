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
	<form:hidden path="rooms" />
	<form:hidden path="client" />
	<form:hidden path="bill" />
	<form:hidden path="numDays" />
	
	<acme:textbox code="reservation.kindOfOffert" path="kindOfOffert" readonly="true"/>
	<br />
	<acme:textbox code="reservation.priceDay" path="priceDay" readonly="true" />
	<br />
	<acme:textbox code="reservation.checkIn" path="checkIn" />
	<br />
	
	<acme:textbox code="reservation.checkOut" path="checkOut" />
	<br />
	
	
<!-- Acciones -->
	<acme:submit code="reservation.save" name="save" />
	<acme:cancel code="reservation.cancel" url="reservation/client/list.do" />
	
</form:form>
