<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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


<form:form action="${resquestURI}" modelAttribute="trip">
	
	<p><spring:message code="trip.ticker" />: <jstl:out value="${trip.ticker}"></jstl:out></p>
	<p><spring:message code="trip.title" />:  <jstl:out value="${trip.title}"></jstl:out></p>
	<p><spring:message code="trip.description" />:  <jstl:out value="${trip.description}"></jstl:out></p>
	<p><spring:message code="trip.startDate" />:  <jstl:out value="${trip.startDate}"></jstl:out></p>
	<p><spring:message code="trip.finishDate" />:  <jstl:out value="${trip.finishDate}"></jstl:out></p>	
	<p><spring:message code="trip.manager.name" />:  <jstl:out value="${trip.manager.name}"></jstl:out></p>
		
	<!-- AÑADIR BOTON DE NOTIFICATION -->
	<spring:message code="flout.list" var="flouts" />
		<spring:url value="flout/list.do" var="floutURL">
			<spring:param name="tripId" value="${trip.id }" />
		</spring:url>
			<a href="${floutURL}"><spring:message code="flout.list" /></a>

	<h2><spring:message code="trip.ranger" /></h2>	
	<display:table name="ranger" id="row" class="displaytag">
		<spring:message code="trip.ranger" var="titleHeader" />
		<display:column property="name" title="${titleHeader}" sortable="false" >
			<jstl:out value="${row.name}"></jstl:out>
		</display:column>
	</display:table>
	
	<h2><spring:message code="trip.tags.name.table" /></h2>	
	<display:table name="tags" id="row" class="displaytag">
		<spring:message code="trip.tags.title" var="titleHeader" />
		<display:column property="name" title="${titleHeader}" sortable="false" >
			<jstl:out value="${row.name}"></jstl:out>
		</display:column>
	</display:table>
	
	<h2><spring:message code="trip.requirementsExplorers.name.table" /></h2>	
	<display:table name="trip.requirementsExplorers" id="row" class="displaytag">
		<spring:message code="trip.requirementsExplorers.title" var="titleHeader" />
		<display:column title="${titleHeader}" sortable="false" >
			<jstl:out value="${row}"></jstl:out>
		</display:column>
	</display:table>
	
	<h2><spring:message code="trip.stages.name.table" /></h2>	
	<display:table name="stages" id="row" class="displaytag">
		<spring:message code="trip.stages.title" />
		<display:column property="title" title="${titleHeader}" sortable="false" >
			<jstl:out value="${row.title}"></jstl:out>
		</display:column>
		<spring:message code="trip.stages.description" var="titleHeader2" />
		<display:column property="description" title="${titleHeader2}" sortable="false" >
			<jstl:out value="${row.description}"></jstl:out>
		</display:column>
	</display:table>
	
	
	<security:authorize access="hasRole('MANAGER')">
	<h2><spring:message code="trip.applications" /></h2>	
	<display:table name="aplicationFor" id="row" class="displaytag">
	
		<spring:message code="trip.applicationFor.status" var="titleHeader" />
		<display:column property="status" title="${titleHeader}" sortable="false" />
		
		<spring:message code="trip.format.date1" var="pattern"></spring:message>
		<spring:message code="trip.applicationFor.moment" var="titleHeader" />
		<display:column property="moment" title="${titleHeader}" sortable="false" format="${pattern}">
		</display:column>
	</display:table>
	</security:authorize>
	
	
<security:authorize access="hasRole('MANAGER')">
	<input type="button" name="back" value="<spring:message code="trip.back" />"
		onclick="javascript:  window.location.replace('trip/manager_/list.do');" />
	<br />
</security:authorize>

<security:authorize access="hasRole('EXPLORER')">
	<input type="button" name="back" value="<spring:message code="trip.back" />"
		onclick="javascript:  window.location.replace('applicationFor/explorer/list.do');" />
	<br />
</security:authorize>


</form:form>
