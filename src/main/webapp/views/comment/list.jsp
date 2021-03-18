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
	name="comments" requestURI="${requestURI}" id="row">
	
	<!-- Atributos -->
	<spring:message code="comment.title" var="titleHeader" />
	<display:column property="tittle" title="${titleHeader}" sortable="true" />
	
	<spring:message code="comment.momentPosted" var="momentPostedHeader" />
	<display:column property="creationDate" title="${momentPostedHeader}" sortable="false" />
	
	<spring:message code="comment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />
	
	<spring:message code="comment.stars" var="starsHeader" />
	<display:column property="stars" title="${starsHeader}" sortable="false" />
	
	<spring:message code="comment.hotel" var="hotelHeader" />
	<display:column property="hotel.name" title="${hotelHeader}" sortable="true" />
	
	<security:authorize access="hasRole('CLIENT')">
		<display:column>

	<a href="comment/edit.do?commentId=${row.id}">
				<spring:message	code="comment.edit" />
	</a>
</display:column>
</security:authorize>
</display:table>

