<%--
 * edit.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="${requestURI}" modelAttribute="services">
<!-- Formularios -->
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="rooms" />
	
	<acme:textbox code="services.name" path="name"  />
	<acme:textbox code="services.imageURL" path="imageURL"  />
	
	
<!-- Acciones -->
<jstl:if test="${!requestURI.contains('petition') }">
    <acme:submit code="services.save" name="save" />
    <acme:cancel  code="services.cancel" url="services/list.do" />
</jstl:if>

<jstl:if test="${requestURI.contains('petition') }">
    <acme:submit code="services.save2" name="save2" />
    <acme:cancel  code="services.cancel" url="" />
</jstl:if>
	
	
	
</form:form>

