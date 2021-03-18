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
	name="banners" requestURI="${requestURI}" id="row">
	
	<!-- Atributos -->
	<spring:message code="banner.bannerUrl" var="bannerUrlHeader" />
	<display:column property="bannerUrl" title="${bannerUrlHeader}" sortable="true" />
	
	<display:column>

	<a href="banner/edit.do?bannerId=${row.id}">
				<spring:message	code="banner.edit" />
	</a>
</display:column>

<display:column>

	<a href="banner/delete.do?bannerId=${row.id}">
				<spring:message	code="banner.delete" />
	</a>
</display:column>
   

</display:table>
<div><a href="banner/create.do"><spring:message code="banner.create" /></a></div>

