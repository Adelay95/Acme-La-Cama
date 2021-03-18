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

<!-- LESSOR -->
<div id="contenidos" class="mostrar">
		<div id="search">
		<ul>
		<jstl:if test="${finder.hotelName!=null && finder.hotelName!=''}">
		<spring:message code="finder.hotelName" var="hotelNameHeader" />
		<li><b><jstl:out value="${hotelNameHeader}: " /></b><jstl:out value="${finder.hotelName}" /></li>
		</jstl:if>
		
		<jstl:if test="${finder.terrain!=null && finder.terrain!=''}">
		<spring:message code="finder.terrain" var="terrainHeader" />
		<li><b><jstl:out value="${terrainHeader}: " /></b><jstl:out value="${finder.terrain}" /></li>
		</jstl:if>
		
		<jstl:if test="${finder.kindOfRoom!=null && finder.kindOfRoom!=''}">
		<spring:message code="finder.kindOfRoom" var="kindOfRoomHeader" />
		<li><b><jstl:out value="${kindOfRoomHeader}: " /></b><jstl:out value="${finder.kindOfRoom}" /></li>
		</jstl:if>
		
		<jstl:if test="${finder.kindOfOffert!=null && finder.kindOfOffert!=''}">
		<spring:message code="finder.kindOfOffert" var="kindOfOffertHeader" />
		<li><b><jstl:out value="${kindOfOffertHeader}: " /></b><jstl:out value="${finder.kindOfOffert}" /></li>
		</jstl:if>
		
		<jstl:if test="${finder.checkIn!=null}">
		<spring:message code="finder.checkIn" var="checkInHeader" />
		<li><b><jstl:out value="${checkInHeader}: " /></b><jstl:out value="${finder.checkIn}" /></li>
		</jstl:if>
		
		<jstl:if test="${finder.checkOut!=null}">
		<spring:message code="finder.checkOut" var="checkOutHeader" />
		<li><b><jstl:out value="${checkOutHeader}: " /></b><jstl:out value="${finder.checkOut}" /></li>
		</jstl:if>
		
		<jstl:if test="${finder.capacity!=null}">
		<spring:message code="finder.capacity" var="capacityPriceHeader" />
		<li><b><jstl:out value="${capacityPriceHeader}: " /></b><jstl:out value="${finder.capacity}" /></li>
		</jstl:if>
	
		<jstl:if test="${finder.minimumPrice!=null}">
		<spring:message code="finder.minimumPrice" var="minimumPriceHeader" />
		<li><b><jstl:out value="${minimumPriceHeader}: " /></b><jstl:out value="${finder.minimumPrice}" /></li>
		</jstl:if>
		
		<jstl:if test="${finder.maximumPrice!=null}">
		<spring:message code="finder.maximumPrice" var="maximumPriceHeader" />
		<li><b><jstl:out value="${maximumPriceHeader}: " /></b><jstl:out value="${finder.maximumPrice}" /></li>
		</jstl:if>
		
		<jstl:if test="${finder.province!=null && finder.province!=''}">
		<spring:message code="finder.location.province" var="provinceHeader" />
		<li><b><jstl:out value="${provinceHeader}: " /></b><jstl:out value="${finder.province}" /></li>
		</jstl:if>
		
		<jstl:if test="${finder.population!=null && finder.population!=''}">
		<spring:message code="finder.location.population" var="populationHeader" />
		<li><b><jstl:out value="${populationHeader}: " /></b><jstl:out value="${finder.population}" /></li>
		</jstl:if>
		
		</ul>
		
		
		<spring:message code="finder.results.room" var="resultsRoomHeader" />
		<div><a href="finder/client/room/result.do"><jstl:out value="${resultsRoomHeader} " /></a></div>	
		<spring:message code="finder.results.offert" var="resultsOffertHeader" />
		<div><a href="finder/client/offert/result.do"><jstl:out value="${resultsOffertHeader} " /></a></div>	
		<div>
			<a href="finder/client/edit.do">
						<spring:message	code="finder.edit" />
			</a>
		</div>	
	
	
	</div>
		
</div>
