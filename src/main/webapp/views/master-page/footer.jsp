<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date" />

<hr />
<li><a href="politic/aboutUs.do"><spring:message code="master.page.aboutUs" /></a></li>
<li><a href="politic/termsAndConditions.do"><spring:message code="master.page.TermsAndConditions" /></a></li>
<li><a href="politic/cookies.do"><spring:message code="master.page.Cookies" /></a></li>
<li><a href="politic/privacyPolicy.do"><spring:message code="master.page.PoliticOfPrivacy" /></a></li>
<br>
<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme-La-Cama SA.</b>