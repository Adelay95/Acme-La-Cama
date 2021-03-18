<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a title="Home" href=""><img src="images/cabecera.png" alt="Acme La Cama SA" /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="permitAll">
			<li><a class="fNiv"><spring:message	code="master.page.catalogue" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="hotel/list.do"><spring:message code="master.page.catalogue.hotel" /></a></li>	
							
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="services/list.do"><spring:message code="master.page.services.list" /></a></li>	
					<li><a href="actor/manajer/list.do"><spring:message code="master.page.manajer.list" /></a></li>		
					<li><a href="actor/administrator/edit.do"><spring:message code="master.page.administrator.edit" /></a></li>
						<li><a href="banner/list.do"><spring:message code="master.page.banner.edit" /></a></li>	
						<li><a href="request/list.do"><spring:message code="master.page.request.list" /></a></li>			
					<li><a href="global/edit.do"><spring:message code="master.page.global.edit" /></a></li>		
					<li><a href="statistics/dashboard_admin.do"><spring:message code="master.page.administrator.dashboard" /></a></li>						
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CLIENT')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/client/edit.do"><spring:message code="master.page.client.edit" /></a></li>
					<li><a href="finder/client/display.do"><spring:message code="master.page.client.finder" /></a></li>	
					<li><a href="reservation/client/list.do"><spring:message code="master.page.client.reservationList" /></a></li>
					<li><a href="comment/list.do"><spring:message code="master.page.client.comments" /></a></li>
					<li><a href="offert/client/list.do"><spring:message code="master.page.client.offerts" /></a></li>		
				    <li><a href="bill/list.do"><spring:message code="master.page.client.bills" /></a></li>			
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv"><spring:message	code="master.page.manager" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/manajer/edit.do"><spring:message code="master.page.manager.edit" /></a></li>	
					<li><a href="actor/worker/listWorkers.do"><spring:message code="master.page.worker.list" /></a></li>
					<li><a href="offert/manajer/list.do"><spring:message code="master.page.manager.offerts" /></a></li>		
					<li><a href="services/petition.do"><spring:message code="master.page.petition" /></a></li>	
					<li><a href="hotel/manajer/list.do"><spring:message code="master.page.manager.myhotels" /></a></li>	
					<li><a href="trip/list.do"><spring:message code="master.page.manager.trip" /></a></li>	
					<li><a href="actor/worker/edit.do"><spring:message code="master.page.worker.edit" /></a></li>		
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('WORKER')">
			<li><a class="fNiv"><spring:message	code="master.page.worker" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/worker/edit.do"><spring:message code="master.page.worker.edit" /></a></li>	
					<li><a href="room/myhotel/worker/list.do"><spring:message code="master.page.worker.rooms" /></a></li>	
					<li><a href="billLine/worker/list.do"><spring:message code="master.page.worker.list.rooms" /></a></li>		
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="actor/client/create.do"><spring:message code="master.page.client.create" /></a></li>
			<li><a class="fNiv" href="actor/manajer/create.do"><spring:message code="master.page.manager.create" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="hotel/list.do"><spring:message code="master.page.hotel.list" /></a></li>				
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
					<li><a href="folder/list.do"><spring:message code="master.page.profile.folderList" /></a></li>
					<li><a href="actor/client/list.do"><spring:message code="master.page.client.list" /></a></li>	
				</ul>
			</li>
		</security:authorize>
			<li><a class="fNiv"><spring:message	code="master.page.idioms"/>   </a>
	<ul>
	<li class="arrow"></li>
	<li><a href="?language=en"><img src="images/in.png" >   <spring:message code="master.page.idiom.english" /></a></li>
	<li><a href="?language=es"><img src="images/es.png" >   <spring:message code="master.page.idiom.spanish" /></a></li>
	</ul>
			</li>
	</ul>
</div>



