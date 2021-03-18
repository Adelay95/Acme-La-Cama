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
	name="bills" requestURI="${requestURI}" id="row">
	
	<!-- Atributos -->
	<spring:message code="bill.totalAmount" var="totalAmountHeader" />
	<display:column property="totalAmount" title="${totalAmountHeader}" sortable="true" />
	
	<spring:message code="bill.paid" var="paidHeader" />
	<display:column property="paid" title="${paidHeader}" sortable="false" />
	
	<security:authorize access="hasRole('CLIENT')">
		<display:column>
<jstl:if test="${row.reservation==null}">
  <jstl:if test="${now>=row.offert.checkOut  && !row.paid}">
	<a href="bill/pay.do?billId=${row.id}">
				<spring:message	code="bill.pay" />
	</a>
</jstl:if>
</jstl:if>
<jstl:if test="${row.offert==null}">
  <jstl:if test="${now>=row.reservation.checkOut && !row.paid}">
	<a href="bill/pay.do?billId=${row.id}">
				<spring:message	code="bill.pay" />
	</a>
</jstl:if>
</jstl:if>
</display:column>
<display:column>
    <a href="billLine/list.do?billId=${row.id}">
				<spring:message	code="bill.billLines" />
	</a>
</display:column>
</security:authorize>
</display:table>

