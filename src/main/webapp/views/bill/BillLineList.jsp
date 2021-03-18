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
	name="billLines" requestURI="${requestURI}" id="row">
	
	<!-- Atributos -->
	<spring:message code="bill.amount" var="amountHeader" />
	<display:column property="amount" title="${amountHeader}" sortable="true" />
	
	<spring:message code="bill.reason" var="reasonHeader" />
	<display:column property="reason" title="${reasonHeader}" sortable="true" />
	
	<security:authorize access="hasRole('WORKER')">
		<display:column>
		<jstl:if test="${!row.bill.paid}">

	<a href="billLine/edit.do?billLineId=${row.id}">
				<spring:message	code="bill.edit" />
	</a>
	
</jstl:if>
</display:column>
<display:column>
		<jstl:if test="${!row.bill.paid}">

	<a href="billLine/delete.do?billLineId=${row.id}"  onclick="return confirm('<spring:message code="bill.confirm.delete" />')">
				<spring:message	code="bill.delete" />
	</a>
	
</jstl:if>
</display:column>
</security:authorize>
</display:table>
<security:authorize access="hasRole('WORKER')">
<jstl:if test="${!row.bill.paid}">
<div>
    <a href="billLine/create.do?billId=${billId}">
				<spring:message	code="bill.create" />
	</a>
</div>
</jstl:if>
</security:authorize>
