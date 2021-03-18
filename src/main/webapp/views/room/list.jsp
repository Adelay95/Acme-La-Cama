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
<jstl:if test="${(requestURI=='room/myhotel/worker/list.do')||(requestURI=='room/myhotel/worker/search.do')}">
<form method="GET" action="room/myhotel/worker/search.do">
<label id="search">
		<spring:message code="room.search1" />:
	</label>
			<input type="number" min="0" max="999999999" name="search" value="${search}" /><input type="submit" name="searchs"
		value="<spring:message code="room.search2" />" />&nbsp; 
</form>
</jstl:if>


<display:table pagesize="5" class="table table-striped table-borderer table-over" keepStatus="false"
	name="rooms" requestURI="${requestURI}" id="row">
	
	<!-- Atributos -->
	<spring:message code="room.number" var="numberHeader" />
	<display:column property="number" title="${numberHeader}" sortable="true" />
	
	<spring:message code="room.kindOfRoom" var="kindOfRoomHeader" />
	<display:column property="kindOfRoom" title="${kindOfRoomHeader}" sortable="true" />
	
	<spring:message code="room.originalPriceDay" var="originalPriceDaysHeader" />
	<display:column property="originalPriceDays" title="${originalPriceDaysHeader}" sortable="true" />
	
	<spring:message code="room.capacity" var="capacityHeader" />
	<display:column property="capacity" title="${capacityHeader}" sortable="true" />
	
	<spring:message code="room.kids" var="kidsHeader" />
	<display:column property="kids" title="${kidsHeader}" sortable="true" />
	
	<spring:message code="room.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true" />
    

	<!-- Enlaces -->
 <jstl:if test="${(requestURI=='room/worker/list.do')}">
    <display:column>

 <a href="billLine/listWorker.do?roomId=${row.id}">
    <spring:message code="room.bill" />
 </a>

</display:column>
    </jstl:if>


<display:column>
	<a href="room/display.do?roomId=${row.id}">
				<spring:message	code="room.display" />
	</a>
</display:column>
<security:authorize access="hasRole('MANAGER')">
<jstl:if test="${principal}">
<display:column>
	<div><a href="offert/create.do?roomId=${row.id }"><spring:message code="room.offert"/></a></div>
</display:column>
</jstl:if>
</security:authorize>

</display:table>

