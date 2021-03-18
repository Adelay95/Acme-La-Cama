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
	name="workers" requestURI="${requestURI}" id="row">
	
	<!-- Atributos -->
	<spring:message code="actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />
	
	<spring:message code="actor.class" var="rolHeader" />
	<display:column title="${rolHeader}" sortable="true" >
	<jstl:set var="n" value="0"/>
     <jstl:forEach var="x" items="${row.userAccount.authorities}">
        <jstl:if test="${n==0}"><jstl:out value="${x.authority}" /></jstl:if>
         <jstl:if test="${n!=0}"><jstl:out value=", ${x.authority}" /></jstl:if>
          <jstl:set var="n" value="1"/>
           </jstl:forEach>
	</display:column>
	
	<jstl:if test="${requestURI.contains('w')}" >
	<spring:message code="actor.postalAdress" var="postalAdressHeader" />
	<display:column property="postalAdress" title="${postalAdressHeader}" sortable="true" />
	
	<spring:message code="actor.salary" var="salaryHeader" />
	<display:column property="salary" title="${salaryHeader}" sortable="true" />
	
	<spring:message code="actor.accountNumber" var="accountNumberHeader" />
	<display:column property="creditCard.accountNumber" title="${accountNumberHeader}" sortable="true" />
	
	<spring:message code="actor.hotel" var="hotelHeader" />
	<display:column property="hotel.name" title="${hotelHeader}" sortable="true" />
	
	<display:column>
	<!-- Enlaces -->
<div>
	<a href="actor/worker/delete.do?workerId=${row.id}"  onclick="return confirm('<spring:message code="actor.deleter" />')">
				<spring:message	code="actor.delete" />
	</a>
</div>
</display:column>
</jstl:if>

<security:authorize access="!hasRole('MANAGER')">
	<display:column>
	<!-- Enlaces -->
    <div>
	<a href="message/edit.do?actorId=${row.id}">
				<spring:message	code="actor.message.create" />
	</a>
    </div>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
	<display:column>
	<jstl:if test="${manager.banned == false}">
	<!-- Enlaces -->
    <div>
	<a href="message/edit.do?actorId=${row.id}">
				<spring:message	code="actor.message.create" />
	</a>
    </div>
    </jstl:if>
	</display:column>
	</security:authorize>

</display:table>

<jstl:if test="${(requestURI=='actor/worker/hotel/manajer/list.do')}">
<div>
	<a href="actor/worker/create.do?hotelId=${hotelId}">
				<spring:message	code="actor.worker.create" />
	</a>
</div>	
</jstl:if>
