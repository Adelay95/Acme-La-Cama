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

<form:form action="${requestURI}" modelAttribute="actor">
<!-- Formularios -->
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />
	<form:hidden path="messageSent" />
	<form:hidden path="messageReceived" />
	<form:hidden path="folders" />
	<security:authorize access="hasRole('CLIENT')">
	<form:hidden path="comments" />
	<form:hidden path="offerts" />
	<form:hidden path="reservations" />
	</security:authorize>
	<security:authorize access="hasRole('MANAGER')">
	<form:hidden path="requests" />
	<form:hidden path="banned" />
	</security:authorize>
	<security:authorize access="hasRole('WORKER')">
	<form:hidden path="hotel" />
	</security:authorize>
	
	
	
	<acme:textbox code="actor.name" path="name" />
	<br />
	<acme:textbox code="actor.surname" path="surname" />
	<br />
	<acme:textbox code="actor.email" path="email" />
	<br />
	<acme:textbox code="actor.phoneNumber" path="phoneNumber" />
	<br />
	<acme:textbox code="actor.postalAdress" path="postalAdress" />
	<br />
	<security:authorize access="hasRole('CLIENT')">
	<acme:textbox code="actor.dniNif" path="dniNif" />
	<br />
	</security:authorize>
	<security:authorize access="hasRole('WORKER')">
	<acme:textbox code="actor.salary" path="salary" />
	<br />
	</security:authorize>
	<security:authorize access="hasAnyRole('WORKER','CLIENT','MANAGER')">
	<fieldset name="Credit Card" >
	<legend><b><spring:message code="actor.creditCard" /></b></legend>
	<acme:textbox code="actor.holderName" path="creditCard.holderName" />
	<br />
	<form:label path="creditCard.brandName">
		<spring:message code="actor.brandName" />:
	</form:label>
	<form:select id="brandName" path="creditCard.brandName">
     <form:option value="" label="----" /> 
     <jstl:forEach var="b" items="${brands}">
     <form:option value="${b}" label="${b}" />
     </jstl:forEach>  
     </form:select>
			<form:errors cssClass="error" path="creditCard.brandName" />
	<br />
	<br />
	<form:label path="creditCard.accountNumber">
		<spring:message code="actor.accountNumber" />:
	</form:label>
			<form:input path="creditCard.accountNumber" />
			<form:errors cssClass="error" path="creditCard.accountNumber" />
	<br />
	<br />
	
	<acme:textbox code="actor.expirationMonth" path="creditCard.expirationMonth" />
	<br />
	
	<acme:textbox code="actor.expirationYear" path="creditCard.expirationYear" />
	<br />
	
	<acme:textbox code="actor.cVV" path="creditCard.cVV" />
	</fieldset>
	<br/>
	</security:authorize>
<!-- Acciones -->
	<acme:submit code="actor.save" name="save2" />
		
	<acme:cancel code="actor.cancel" url="" />
	
</form:form>