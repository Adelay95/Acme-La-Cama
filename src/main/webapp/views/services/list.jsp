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
	name="services" requestURI="${requestURI}" id="row">
	<spring:message code="services.services" var="pictureHeader" />
	<display:column title="${pictureHeader}" >
	<img WIDTH=200 HEIGHT=120 src="<jstl:out value="${row.imageURL}" />" />
	</display:column>
	
	<spring:message code="services.servicesName" var="servicesHeader" />
	<display:column property="name" title="${servicesHeader}" sortable="true" />
	
	
	
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<display:column>
	
   <div>
	<a href="services/delete.do?servicesId=${row.id}">
				<spring:message	code="services.delete" />
	</a>
    </div>
    
	</display:column>
	</security:authorize>
	
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<display:column>
	<div>
	<a href="services/edit.do?servicesId=${row.id}">
				<spring:message	code="folder.edit" />
	</a>
    </div>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
	<display:column>
	
   <div>
	<a href="room/addservice.do?roomId=${roomId}&serviceId=${row.id}">
				<spring:message	code="services.addservices" />
	</a>
    </div>
    
	</display:column>
	</security:authorize>

</display:table>
<security:authorize access="hasRole('ADMINISTRATOR')">
<div><a href="services/create.do"><spring:message code="services.create" /></a></div>
</security:authorize>

