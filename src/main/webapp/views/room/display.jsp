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


<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css" />

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<style>
.event a{
     background-color:#DC143C !important;
     color: White !important;
     font-weight:bold !important;
     font-size:12pt !important;
}

fieldset {
 margin-left: 40px !important;
}
</style>

	    
<div id="contenidos" class="mostrar">
<jstl:set var="iter" value="0" />
<jstl:forEach var="fechiras" items="${datesReserva}"> 
<input type="hidden" id="fechas${iter}" name="x" value="${datesReserva[iter]}" >
<jstl:set var="iter" value="${iter+1}"/>
</jstl:forEach>
<input type="hidden" id="iterDeph" name="x" value="${iter}" >
		<div>
		<fieldset>
		<spring:message code="room.hotel" var="hotelHeader" />
			<legend><b><jstl:out value="${hotelHeader}:" />&nbsp;<jstl:out value="${room.hotel.name}" />  </b></legend>
		<div>
			<img WIDTH=400 HEIGHT=240 src="<jstl:out value="${room.picture}" />" />
		</div>
	
		<ul>
		<spring:message code="room.number" var="numberHeader" />
		<li><b><jstl:out value="${numberHeader}: " /></b><jstl:out value="${room.number}" /></li>
		
		<spring:message code="room.kindOfRoom" var="kindOfRoomHeader" />
		<li><b><jstl:out value="${kindOfRoomHeader}: " /></b><jstl:out value="${room.kindOfRoom}" /></li>
		
		<spring:message code="room.originalPriceDay" var="originalPriceDaysHeader" />
		<li><b><jstl:out value="${originalPriceDaysHeader}: " /></b><jstl:out value="${room.originalPriceDays}" /></li>
		
		<spring:message code="room.capacity" var="capacityHeader" />
		<li><b><jstl:out value="${capacityHeader}: " /></b><jstl:out value="${room.capacity}" /></li>
		
		<spring:message code="room.kids" var="kidsHeader" />
		<li><b><jstl:out value="${kidsHeader}: " /></b><jstl:out value="${room.kids}" /></li>
		
		<spring:message code="room.description" var="descriptionHeader" />
		<li><b><jstl:out value="${descriptionHeader}: " /></b><jstl:out value="${room.description}" /></li>
		</ul>
		<security:authorize access="hasRole('CLIENT')">
	<div><a href="reservation/setAttributes.do?roomId=${room.id }"><spring:message code="room.reservation"/></a></div>
	    </security:authorize>
	 
	 <div id="datepicker" ></div>
	 <br />
	 <br />
	 <script type="text/javascript">

	 var disabledDays = [];
	 for (j = 0; j < document.getElementById("iterDeph").value; j++) {
		 if(document.getElementById("fechas"+j).value.substring(5,7)=="10" || document.getElementById("fechas"+j).value.substring(5,7)=="11" || 
				 document.getElementById("fechas"+j).value.substring(5,7)=="12"){
			 if(document.getElementById("fechas"+j).value.substring(8,10)=="01" || document.getElementById("fechas"+j).value.substring(8,10)=="02" || 
					 document.getElementById("fechas"+j).value.substring(8,10)=="03" ||document.getElementById("fechas"+j).value.substring(8,10)=="04" ||
					 document.getElementById("fechas"+j).value.substring(8,10)=="05" || document.getElementById("fechas"+j).value.substring(8,10)=="06" || 
					 document.getElementById("fechas"+j).value.substring(8,10)=="07" ||
					 document.getElementById("fechas"+j).value.substring(8,10)=="08" || document.getElementById("fechas"+j).value.substring(8,10)=="09"){
				 disabledDays.push(document.getElementById("fechas"+j).value.substring(0,4)+'-'+document.getElementById("fechas"+j).value.substring(5,7)+'-'+
						 document.getElementById("fechas"+j).value.substring(9,10));	 
			 }else{
		 disabledDays.push(document.getElementById("fechas"+j).value.substring(0,4)+'-'+document.getElementById("fechas"+j).value.substring(5,7)+'-'+
				 document.getElementById("fechas"+j).value.substring(8,10));}
		 }else{
			 if(document.getElementById("fechas"+j).value.substring(8,10)=="01" || document.getElementById("fechas"+j).value.substring(8,10)=="02" || 
					 document.getElementById("fechas"+j).value.substring(8,10)=="03" ||document.getElementById("fechas"+j).value.substring(8,10)=="04" ||
					 document.getElementById("fechas"+j).value.substring(8,10)=="05" || document.getElementById("fechas"+j).value.substring(8,10)=="06" || 
					 document.getElementById("fechas"+j).value.substring(8,10)=="07" ||
					 document.getElementById("fechas"+j).value.substring(8,10)=="08" || document.getElementById("fechas"+j).value.substring(8,10)=="09"){
				 disabledDays.push(document.getElementById("fechas"+j).value.substring(0,4)+'-'+document.getElementById("fechas"+j).value.substring(6,7)+'-'+
						 document.getElementById("fechas"+j).value.substring(9,10));	 
			 }else{
		 disabledDays.push(document.getElementById("fechas"+j).value.substring(0,4)+'-'+document.getElementById("fechas"+j).value.substring(6,7)+'-'+
				 document.getElementById("fechas"+j).value.substring(8,10));	} 
		 }
     }
	
	 
	
	 function inArrayDates(needle, haystack){
	     var found = 0;
	     for (var i=0, len=haystack.length;i<len;i++) {
	         if (haystack[i].getTime() == needle.getTime()) return i;
	             found++;
	         }
	     return -1;
	 }
	 var date = new Date();
	 jQuery(document).ready(function() { 
	     $( "#datepicker").datepicker({ 
	         dateFormat: 'yy-mm-dd',
	         beforeShowDay: function(date) {
	             var m = date.getMonth(), d = date.getDate(), y = date.getFullYear();
	             for (i = 0; i < disabledDays.length; i++) {
	                 if($.inArray(y + '-' + (m+1) + '-' + d,disabledDays) != -1) {
	                     //return [false];
	                     return [true, 'event', 'habitacionOcupada'];
	                 }
	             }
	             return [true];

	         }
	     });
	 });

	    </script>
	    
	<display:table pagesize="5" class="table table-striped table-borderer table-over" keepStatus="false"
	name="services" requestURI="${requestURI}" id="row">
	<spring:message code="room.services" var="pictureHeader" />
	<display:column title="${pictureHeader}" >
	<img WIDTH=200 HEIGHT=120 src="<jstl:out value="${row.imageURL}" />" />
	</display:column>
	
	<spring:message code="room.servicesName" var="servicesHeader" />
	<display:column property="name" title="${servicesHeader}" sortable="true" />
	
	
	<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${principal}">
	<display:column>
	<a href="room/removeservice.do?roomId=${room.id}&serviceId=${row.id}">
				<spring:message	code="room.removeservices" />
	</a>
	</display:column>
	</jstl:if>
	</security:authorize>
	
	</display:table>
	
	<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${principal}">
	
	<div>
	<a href="room/addservices.do?roomId=${room.id}">
				<spring:message	code="room.addservices" />
	</a>
	</div>	
	
	</jstl:if>
	</security:authorize>
	</fieldset>
		</div>

	</div>
