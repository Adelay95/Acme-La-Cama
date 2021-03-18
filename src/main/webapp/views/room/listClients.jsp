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

<form:form action="${requestURI}" modelAttribute="formRoom">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="hotel" />
	
	<acme:textbox code="room.kindOfRoom" path="kindOfRoom" readonly="true"/>
	<br />
	<acme:textbox code="room.numberOfRooms" path="numberOfRooms" />
	<br />
	<acme:textbox code="room.originalPriceDays" path="originalPriceDays" readonly="true" />
	<br />
	<spring:message code="room.acceptAutomaticPrice" /><form:checkbox path="checkAutomaticPrice" value="true" />  
	
	<acme:textbox code="room.personalPriceDays" path="personalPriceDays" />
	<br />
	<spring:message code="room.kids" /><form:checkbox path="kids" value="true" />
	<br />  
	<acme:textbox code="room.capacity" path="capacity" />
	<br />
	<acme:textarea code="room.description" path="description" />
	<br />
	<acme:textbox code="room.number" path="number" readonly="true"/>
	<br />
	<acme:textbox code="room.picture" path="picture" />
	<br />
	<form:label path="services">
		<spring:message code="room.services" />:
	</form:label>
	<form:select id="services" path="services" multiple="multiple" >
	<jstl:forEach var="sn" items="${servicesNames}">
     <form:option value="${sn}" label="${sn}" />
     </jstl:forEach>
     </form:select>
	
	
<!-- Acciones -->
	<acme:submit code="room.save" name="save" />
	<acme:cancel code="room.cancel" url="hotel/manajer/list.do" />
	
</form:form>
