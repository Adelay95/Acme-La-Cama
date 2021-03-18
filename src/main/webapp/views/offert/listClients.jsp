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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<!-- Listing grid -->

<form:form action="${requestURI}" modelAttribute="offert">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="rooms" />
	<form:hidden path="client" />
	<form:hidden path="bill" />
	
	<form:label path="kindOfOffert">
		<spring:message code="offert.kindOfOffert" />:
	</form:label>
	<form:select id="kindOfOffert" path="kindOfOffert">
     <jstl:forEach var="k" items="${kindOfOffert}">
     <form:option value="${k}" label="${k}" />
     </jstl:forEach>  
     </form:select>
			<form:errors cssClass="error" path="kindOfOffert" />
			
	<br />		
    <br />
	<acme:textbox code="offert.cost" path="totalPrice" />
	<br />
	<acme:textbox code="offert.checkIn" path="checkIn" />
	<br />
	
	<acme:textbox code="offert.checkOut" path="checkOut" />
	<br />
	
	
<!-- Acciones -->
	<acme:submit code="offert.save" name="save" />
	<acme:cancel code="offert.cancel" url="offert/list.do" />
	
</form:form>
