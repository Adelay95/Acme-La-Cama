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

<style>
fieldset {
 margin-left: 40px !important;
}</style>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<div id="contenidos" class="mostrar">
		<div>
		<fieldset>
		<spring:message code="hotel.name" var="nameHeader" />
			<legend><b><jstl:out value="${nameHeader}:" />&nbsp;<jstl:out value="${hotel.name}" />  </b></legend>
		<table>
		<tr>
			<td colspan="2">
			<img WIDTH=400 HEIGHT=240 src="<jstl:out value="${hotel.picture}" />" />
			</td>
			</tr>
		<tr>
		<spring:message code="hotel.hotelChain" var="hotelChainHeader" />
		<spring:message code="hotel.stars" var="starsHeader" />
			<td><b><jstl:out value="${hotelChainHeader}: " /></b><jstl:out value="${hotel.hotelChain}" /></td><td><b><jstl:out value="${starsHeader}: " /></b><jstl:out value="${hotel.stars}" /></td>
			</tr>
			
		<tr>
		<spring:message code="hotel.terrain" var="terrainHeader" />
		<spring:message code="hotel.location.province" var="provinceHeader" />
			<td><b><jstl:out value="${terrainHeader}: " /></b><jstl:out value="${hotel.terrain}" /></td><td><b><jstl:out value="${provinceHeader}:"  /></b><jstl:out value="${hotel.location.province}" /></td>
			</tr>
			<tr>
			
			<spring:message code="hotel.location.population" var="populationHeader" />
			<spring:message code="hotel.totalRooms" var="totalRoomsHeader" />
			<td><b><jstl:out value="${totalRoomsHeader}: " /></b><jstl:out value="${hotel.totalRooms} " />
			</td><td><b><jstl:out value="${populationHeader}: " /></b><jstl:out value="${hotel.location.population}" /></td>
			</tr>
			
			<security:authorize access="isAuthenticated()">
			<tr>
			<spring:message code="hotel.location.gpsCoords" var="gpsCoordsHeader" />
			<td colspan="2"><a href="room/hotel/list.do?hotelId=${hotel.id}">
						<spring:message	code="hotel.see.rooms" />
			</a></td>
			</tr></security:authorize>
			<jstl:if test="${(hotel.location.gpsCoords!=null)}">
			<tr>
			<spring:message code="hotel.location.gpsCoords" var="gpsCoordsHeader" />
			<td colspan="2"><b><jstl:out value="${gpsCoordsHeader}: " /></b><jstl:out value="${hotel.location.gpsCoords}" /></td>
			</tr>
			</jstl:if>
			<tr>
			<spring:message code="hotel.description" var="descriptionHeader" />
			<td colspan="2"><b><jstl:out value="${descriptionHeader}: " /></b><jstl:out value="${hotel.description}" /></td>
			</tr>
			
		</table>
	<display:table pagesize="5" class="table table-striped table-borderer table-over" keepStatus="false"
	name="comments" requestURI="${requestURI}" id="row">
	<spring:message code="comment" var="commentHeader" />
	<display:column title="${commentHeader}" sortable="false" >
	<table>
	<spring:message code="comment.tittle" var="tittleHeader" />
	<spring:message code="comment.stars" var="starsHeader" />
	<spring:message code="comment.creationDate" var="creationDateHeader" />
	<spring:message code="comment.client" var="clientHeader" />
	<tr>
	<td><b><jstl:out value="${clientHeader}: " /></b><jstl:out value="${row.client.name} ${row.client.surname}" />&nbsp;&nbsp;&nbsp;</td>
	<td><b><jstl:out value="${tittleHeader}: " /></b><jstl:out value="${row.tittle}" />&nbsp;&nbsp;&nbsp;</td>
	<td><b><jstl:out value="${starsHeader}: " /></b><jstl:out value="${row.stars}" />&nbsp;&nbsp;&nbsp;</td>
	<td><b><jstl:out value="${creationDateHeader}: " /></b><jstl:out value="${row.creationDate}" />&nbsp;&nbsp;&nbsp;</td>
	</tr>
	
	<tr>
			<td colspan="5" rowspan="3"><jstl:out value="${row.text}" /></td>
	</tr>
	</table>
	</display:column>
	</display:table>
	
	<security:authorize access="hasRole('CLIENT')">
		<div>
			<a href="comment/create.do?hotelId=${hotel.id}">
						<spring:message	code="hotel.create.comment" />
			</a>
		</div>	
	</security:authorize>
	</fieldset>
		</div>

	</div>
	<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${(hi.banned==false)}">
<div>
	<a href="trip/create.do?hotelId=${hotel.id}">
				<spring:message	code="trip.create.create" />
	</a>
</div>	
</jstl:if>
</security:authorize>
