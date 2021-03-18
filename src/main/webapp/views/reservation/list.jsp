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
	name="reservations" requestURI="${requestURI}" id="row">
	
	<!-- Atributos -->
	<spring:message code="reservation.kindOfOffert" var="kindOfOffertHeader" />
	<display:column property="kindOfOffert" title="${kindOfOffertHeader}" sortable="true" />
	
	<spring:message code="reservation.checkIn" var="checkInHeader" />
	<display:column property="checkIn" title="${checkInHeader}" sortable="true" />
	
	<spring:message code="reservation.checkOut" var="checkOutHeader" />
	<display:column property="checkOut" title="${checkOutHeader}" sortable="true" />
	
	<spring:message code="reservation.cost" var="cost" />
	<jstl:out value="${row.numDays*row.priceDay }" />
	
	<spring:message code="reservation.hotel" var="hotelHeader" />
	<display:column property="rooms.hotel.name" title="${hotelHeader}" sortable="true" />
	
	<spring:message code="reservation.roomNumber" var="roomNumberHeader" />
	<display:column property="rooms.number" title="${roomNumberHeader}" sortable="true" />
   
   
	<!-- Enlaces -->
	<security:authorize access="hasRole('CLIENT')">
	<display:column>
	<a href="trip/list2.do?reservaId=${row.id}">
				<spring:message	code="reservation.trips" />
	</a>
</display:column>
	<display:column>
<jstl:if test="${now<row.checkIn}">

	<a href="reservation/edit.do?reservationId=${row.id}">
				<spring:message	code="reservation.edit" />
	</a>

</jstl:if>
</display:column>
</security:authorize>
</display:table>

