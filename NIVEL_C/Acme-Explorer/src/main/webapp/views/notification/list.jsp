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
	name="notifications" requestURI="${requestURI}" id="row">
	
	<%!String estilo="red";%>
	<jstl:choose>
	
		<jstl:when test="${row.gauge=='1'}">
			<%=estilo = "red"%>
		</jstl:when>

		<jstl:when test="${row.gauge=='2'}">
			<%=estilo = "cyan"%>
		</jstl:when>
		
		<jstl:when test="${row.gauge=='3'}">
			<%=estilo = "yellow"%>
		</jstl:when>

	</jstl:choose>
	
	<spring:message code="notification.code" var="codeHeader" />
	<display:column property="code" title="${codeHeader}" sortable="true" />
	
	<spring:message code="notification.format.date" var="pattern"></spring:message>
	<spring:message code="notification.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" format="${pattern}" />

	<spring:message code="notification.gauge" var="gaugeHeader" />
	<display:column property="gauge" title="${gaugeHeader}" sortable="true" class="<%= estilo %>" />
	
	<!-- MOSTRAR EL LINK DE LA TRIP ASOCIADA -->
	<spring:message code="notification.trip" var="trip" />
	<display:column title="${trip}">
		<spring:url value="trip/display.do" var="tripURL">
			<spring:param name="tripId" value="${row.trip.id }" />
		</spring:url>
			<a href="${tripURL}"><spring:message code="notification.trip.display" /></a>
	</display:column>
	
	<!-- Mostrar el link para editar -->
	<jstl:if test="${showEditCreateLink}">
	<security:authorize access="hasRole('EXPLORER')">	
	<spring:message code="notification.edit" var="Edit" />
		<display:column title="${Edit}" sortable="true">
				<spring:url value="notification/explorer/edit.do" var="editURL">
					<spring:param name="notificationId" value="${row.id}" />
				</spring:url>
				<a href="${editURL}"><spring:message code="notification.edit" /></a>
		</display:column>		
	</security:authorize>
	</jstl:if>

</display:table>

<!-- Mostrar el link para crear -->
<jstl:if test="${showEditCreateLink}">
	<security:authorize access="hasRole('EXPLORER')">

		<div>
			<a href="notification/explorer/create.do"> 
				<spring:message	code="notification.create" />
			</a>
		</div>
	</security:authorize>
</jstl:if>