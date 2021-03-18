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
	name="managers" requestURI="${requestURI}" id="row">
	
	<!-- Atributos -->
	<spring:message code="actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<jstl:if test="${requestURI.contains('stat')}">
	<spring:message code="actor.num" var="numHeader" />
	 <display:column title="${numHeader}" sortable="true">
 		${row.requests.size()} 
	</display:column>
	</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<display:column>
				<jstl:if test="${!row.banned}">
				<a href="actor/manajer/ban.do?actorId=${row.id}">
						<spring:message	code="manager.ban" />
				</a>
				</jstl:if>
				<jstl:if test="${row.banned}">
				<a href="actor/manajer/unban.do?actorId=${row.id}">
						<spring:message	code="manager.unban" />
				</a>
				</jstl:if>
					</display:column>
				</security:authorize>
	
	<display:column>
	<!-- Enlaces -->
    <div>
	<a href="message/edit.do?actorId=${row.id}">
				<spring:message	code="actor.message.create" />
	</a>
    </div>
	</display:column>
	
	
	
	
</display:table>
