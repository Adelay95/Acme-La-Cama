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
	name="trips" requestURI="${requestURI}" id="row">
	
	<!-- Atributos -->
	<spring:message code="trip.title" var="nameHeader" />
	<display:column property="title" title="${nameHeader}" sortable="true" />
	
	
	
	
	<spring:message code="trip.img" var="pictureHeader" />
	<display:column title="${pictureHeader}" >
	<img WIDTH=200 HEIGHT=120 src="<jstl:out value="${row.picture}" />" />
	</display:column>
	
	<spring:message code="trip.openingTime" var="postalAdressHeader" />
	<display:column property="openingTime" title="${postalAdressHeader}" sortable="true" />
	
	<spring:message code="trip.closingTime" var="salaryHeader" />
	<display:column property="closingTime" title="${salaryHeader}" sortable="true" />
	
	<spring:message code="trip.price" var="accountNumberHeader" />
	<display:column property="price" title="${accountNumberHeader}" sortable="true" />
	
	<security:authorize access="hasRole('MANAGER')">
	<display:column>
<div>
	<a href="trip/edit.do?tripId=${row.id }">
				<spring:message	code="trip.create" />
	</a>
</div>	
</display:column>
</security:authorize>
	<display:column>
	<!-- Enlaces -->
	<jstl:if test="${!row.clients.contains(principal)}">
	<jstl:if test="${requestURI.contains('1')}">
<div>
	<a href="trip/attend_by_offert.do?oT=${row.id}&ofertaId=${ofertaId}" onclick="return confirm('<spring:message code="client.confirm.trip" />')">
				<spring:message	code="client.atend" />
	</a>
	
</div>
</jstl:if>
<jstl:if test="${requestURI.contains('2')}">
<div>
	
	<a href="trip/attend_by_reservation.do?oT=${row.id}&reservaId=${reservaId}" onclick="return confirm('<spring:message code="client.confirm.trip" />')">
				<spring:message	code="client.atend" />
	</a>
</div>
</jstl:if>
		</jstl:if>
</display:column>
</display:table>

