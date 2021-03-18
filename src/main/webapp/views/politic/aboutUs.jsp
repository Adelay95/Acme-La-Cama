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

<li><b><spring:message code="master.page.Labor" /></b></li>
<spring:message code="master.page.escrito" />
<br><br>

<li><b><spring:message code="master.page.Manager" /></b></li>
<spring:message code="master.page.Oskita" />
<br><br>

<li><b><spring:message code="master.page.Programmers" /></b></li>
<spring:message code="master.page.sixto" /><br>
<spring:message code="master.page.fran" /><br>
<spring:message code="master.page.sergio" />
<br><br>

<li><b><spring:message code="master.page.City" /></b></li>
<spring:message code="master.page.sevilla" />
<br><br>

<li><b><spring:message code="master.page.place" /></b></li>
<spring:message code="master.page.universidad" /><br>
<a href="https://www.google.es/maps/place/Escuela+T%C3%A9cnica+superior+de+Ingenier%C3%ADa+Inform%C3%A1tica,+ETSII/@37.3588331,-5.9785305,15z/data=!4m5!3m4!1s0x0:0x29c3f634f8a021b8!8m2!3d37.3582106!4d-5.9870385"> <img src="images/universidad.png" ></a>
<br><br>




