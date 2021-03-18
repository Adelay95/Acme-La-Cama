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
	name="request" requestURI="${requestURI}" id="row">
	
	<!-- Atributos -->
	<spring:message code="request.price" var="priceHeader" />
	<display:column property="price" title="${priceHeader}" sortable="true" />
	
	<spring:message code="request.timeIn" var="timeInHeader" />
	<display:column property="timeIn" title="${timeInHeader}" sortable="true" />
	
	<spring:message code="request.timeOut" var="timeOutHeader" />
	<display:column property="timeOut" title="${timeOutHeader}" sortable="true" />
	
	<spring:message code="request.hotel" var="hotelHeader" />
	<display:column property="hotel.name" title="${hotelHeader}" sortable="true" />
	
	<spring:message code="request.state" var="stateHeader" />
	<display:column property="state" title="${stateHeader}" sortable="true" />
	
   
	<!-- Enlaces -->
<display:column>
<jstl:if test="${row.state=='PENDING'}">

	<a href="request/accept.do?requestId=${row.id}">
				<spring:message	code="request.accept" />
	</a>

</jstl:if>
</display:column>
<display:column>
<jstl:if test="${row.state=='PENDING'}">


	<a href="request/deny.do?requestId=${row.id}">
				<spring:message	code="request.deny" />
	</a>

</jstl:if>
</display:column>

<display:column>

	<a href="hotel/display.do?hotelId=${row.hotel.id}">
				<spring:message	code="request.display" />
	</a>


</display:column>

</display:table>

