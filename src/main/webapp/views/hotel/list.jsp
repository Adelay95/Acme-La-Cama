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



<!-- Listing grid -->

<display:table pagesize="5" class="table table-striped table-borderer table-over" keepStatus="false"
	name="hotels" requestURI="${requestURI}" id="row">
	
	<!-- Atributos -->
	
	
	<spring:message code="hotel.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="hotel.stars" var="starsHeader" />
	<display:column property="stars" title="${starsHeader}" sortable="true" />
	
	<spring:message code="hotel.location.province" var="provinceHeader" />
	<display:column property="location.province" title="${provinceHeader}" sortable="true" />
	
	<spring:message code="hotel.location.population" var="populationHeader" />
	<display:column property="location.population" title="${populationHeader}" sortable="true" />
	
	
	<spring:message code="hotel.terrain" var="terrainHeader" />
	<display:column property="terrain" title="${terrainHeader}" sortable="true" />
    
    <spring:message code="hotel.totalRooms" var="totalRoomsHeader" />
	<display:column property="totalRooms" title="${totalRoomsHeader}" sortable="true" />
	<!-- Enlaces -->


<security:authorize access="hasRole('MANAGER')">
<jstl:if test="${(requestURI=='hotel/manajer/list.do')}">

<spring:message code="hotel.request.price" var="priceHeader" />
	<display:column property="request.price" title="${priceHeader}" sortable="true" />
	
	<spring:message code="hotel.request.timeIn" var="timeInHeader" />
	<display:column property="request.timeIn" title="${timeInHeader}" sortable="true" />
	
	<spring:message code="hotel.request.timeOut" var="timeOutHeader" />
	<display:column property="request.timeOut" title="${timeOutHeader}" sortable="true" />
	
	<spring:message code="hotel.request.state" var="stateHeader" />
	<display:column property="request.state" title="${stateHeader}" sortable="true" />

    <display:column>

	<a href="reservation/hotel/list.do?hotelId=${row.id}">
				<spring:message	code="hotel.reservations" />
	</a>

</display:column>
<display:column>
<jstl:if test="${(row.request.state=='ACCEPTED')&& (row.request.timeOut>=hoy)}">
	<a href="room/setAttributes.do?hotelId=${row.id}">
				<spring:message	code="hotel.room.create" />
	</a>
</jstl:if>
</display:column>

<display:column>

<jstl:if test="${(row.request.state!='ACCEPTED')||(row.request.timeOut<hoy)}">
	<a href="hotel/manajer/edit.do?hotelId=${row.id}">
				<spring:message	code="hotel.edit" />
	</a>
</jstl:if>
<jstl:if test="${(row.request.state)=='ACCEPTED'&&(row.request.timeOut>=hoy)}">
	<a href="actor/worker/hotel/manajer/list.do?hotelId=${row.id}">
				<spring:message	code="hotel.worker" />
	</a>
</jstl:if>
</display:column>
</jstl:if>
</security:authorize>

<security:authorize access="hasAnyRole('CLIENT','WORKER')">
<display:column>
	<a href="hotel/report.do?hotelId=${row.id}">
				<spring:message	code="hotel.report" />
	</a>
</display:column>
</security:authorize>

<display:column>
	<a href="hotel/display.do?hotelId=${row.id}">
				<spring:message	code="hotel.display" />
	</a>
</display:column>

<security:authorize access="!hasRole('ADMINISTRATOR')">
<display:column>
	<a href="offert/hotel/list.do?hotelId=${row.id}">
				<spring:message	code="hotel.offert" />
	</a>
</display:column>
</security:authorize>

<display:column>
<jstl:if test="${requestURI.contains('mana')}" >
	<a href="statistics/dashboard.do?hotelId=${row.id}">
				<spring:message	code="hotel.statistics" />
	</a>

</jstl:if>
</display:column>
</display:table>

<security:authorize access="hasRole('MANAGER')">
<div>
	<a href="hotel/manajer/create.do">
				<spring:message	code="hotel.create" />
	</a>
</div>	
</security:authorize>
