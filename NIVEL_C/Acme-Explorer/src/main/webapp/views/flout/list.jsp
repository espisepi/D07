<%--
 * list.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<jsp:useBean id="util" class="utilities.Methodutilities" scope="page" />

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="flouts" requestURI="${requestURI}" id="row">
	
	<spring:message code="flout.number" var="numberHeader" />
	<display:column property="number" title="${numberHeader}" sortable="true" />
	
	<spring:message code="flout.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
	<spring:message code="flout.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true" />
	
	<spring:message code="flout.format.date" var="pattern"></spring:message>
	<spring:message code="flout.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" format="${pattern}" />

	<jstl:choose>
	
		<jstl:when test="${row.gauge=='1'}">
	<spring:message code="flout.gauge" var="gaugeHeader" />
	<display:column property="gauge" title="${gaugeHeader}" sortable="true" style="background-color:cyan;" />
		</jstl:when>
		
		<jstl:when test="${row.gauge=='2'}">
	<spring:message code="flout.gauge" var="gaugeHeader" />
	<display:column property="gauge" title="${gaugeHeader}" sortable="true" style="background-color:pink;" />
		</jstl:when>
		
		<jstl:when test="${row.gauge=='3'}">
	<spring:message code="flout.gauge" var="gaugeHeader" />
	<display:column property="gauge" title="${gaugeHeader}" sortable="true" style="background-color:gold;" />
		</jstl:when>
	</jstl:choose>
	
	<!-- MOSTRAR EL LINK DE LA TRIP ASOCIADA -->
	<spring:message code="flout.trip" var="trip" />
	<display:column title="${trip}">
		<spring:url value="trip/display.do" var="tripURL">
			<spring:param name="tripId" value="${row.trip.id }" />
		</spring:url>
			<a href="${tripURL}"><spring:message code="flout.trip.display" /></a>
	</display:column>
	
	<!-- Mostrar el link para editar -->
	<jstl:if test="${showEditCreateLink}">
	
	<security:authorize access="hasRole('MANAGER')">	
	<spring:message code="flout.edit" var="Edit" />
		<display:column title="${Edit}" sortable="true">
		<jstl:if test="${util.publicationDate(row.moment)==true}">
				<spring:url value="flout/manager_/edit.do" var="editURL">
					<spring:param name="floutId" value="${row.id}" />
				</spring:url>
				<a href="${editURL}"><spring:message code="flout.edit" /></a>
				</jstl:if>
		</display:column>		
	</security:authorize>
	</jstl:if>

</display:table>

<!-- Mostrar el link para crear -->
<jstl:if test="${showEditCreateLink}">
	<security:authorize access="hasRole('MANAGER')">

		<div>
			<a href="flout/manager_/create.do"> 
				<spring:message	code="flout.create" />
			</a>
		</div>
	</security:authorize>
</jstl:if>