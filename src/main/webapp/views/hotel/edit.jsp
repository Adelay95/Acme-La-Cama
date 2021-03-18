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

<form:form action="${requestURI}" modelAttribute="formHotel">
<!-- Formularios -->
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<fieldset>
		<spring:message code="hotel.request" var="requestHeader" />
			<legend><b><jstl:out value="${requestHeader}" /> </b></legend>
	<acme:textbox code="hotel.request.timeIn" path="timeIn" />
	<br/>
	<acme:textbox code="hotel.request.timeOut" path="timeOut" />
	<br/>
	
			
	</fieldset>
	
	<br/>
	<acme:textbox code="hotel.name" path="name" />
	<br/>
	<acme:textbox code="hotel.hotelChain" path="hotelChain" />
	<br/>
	<acme:textbox code="hotel.stars" path="stars" />
	<br/>
	<acme:textbox code="hotel.roomPrice" path="roomPrice" />
	<br/>
	<acme:textbox code="hotel.totalRooms" path="totalRooms" />
	<br/>
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
	<br/>
	<br/>
    <acme:textbox code="hotel.location.province" path="location.province" />
	<br/>
	<acme:textbox code="hotel.location.population" path="location.population" />
	<br/>
	<acme:textbox code="hotel.location.gpsCoords" path="location.gpsCoords" />
	<br/>
	<acme:textbox code="hotel.description" path="description" />
	<br/>
	<acme:textbox code="hotel.picture" path="picture" />
	<br/>
	
	
    
<!-- Acciones -->
    <acme:submit code="hotel.save" name="save" />
	<jstl:if test="${formHotel.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="hotel.delete" />"
			onclick="return confirm('<spring:message code="hotel.confirm.delete" />')" />&nbsp;
			</jstl:if>
	
		<acme:cancel code="hotel.cancel" url="hotel/manajer/list.do" />
	
	
	
</form:form>