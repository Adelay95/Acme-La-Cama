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

<form:form action="${requestURI}" modelAttribute="formActor">
<!-- Formularios -->
	<form:hidden path="id" />
	<form:hidden path="version" />
	<security:authorize access="isAnonymous()">
	<acme:textbox code="actor.username" path="username" />
	<br />
	<acme:password code="actor.password" path="password" />
	<br />

	<acme:password code="actor.password2" path="password2" />
	<br />
	</security:authorize>
	<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${requestURI.contains('worker') }">
	<acme:textbox code="actor.username" path="username" />
	<br />
	<acme:password code="actor.password" path="password" />
	<br />

	<acme:password code="actor.password2" path="password2" />
	</jstl:if>
	<br />
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
	<jstl:if test="${requestURI.contains('client')}">
	<acme:textbox code="actor.dniNif" path="dniNif" />
	<br />
	</jstl:if>
	<jstl:if test="${requestURI.contains('worker')}">
	<acme:textbox code="actor.salary" path="salary" />
	<br />
	<form:hidden path="hotel" />
	</jstl:if>
	<jstl:if test="${requestURI.contains('client') || requestURI.contains('worker') || requestURI.contains('manajer')}">
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
	</jstl:if>
	<div>
	<jstl:if test="${formActor.id==0 }">
	<form:checkbox path="confirmed" value="true" />  
	<spring:message code="actor.accept" />
	<a href="politic/termsAndConditions.do" ><spring:message code="actor.terms" /></a>
	</jstl:if>
	</div>
	<br/>
	
	
<!-- Acciones -->
	<acme:submit code="actor.save" name="save" />
	<acme:cancel code="actor.cancel" url="" />
	
</form:form>