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
	name="notification" requestURI="${requestURI}" id="row">
	<%!String estilo="red";%>
	<%-- <jstl:choose>
			<jstl:when test="${row.status=='PENDING'}">
<jstl:if test="${util.moreThanThirtyDays(row.trip.startDate)==false}"> 
		<%=estilo = "red"%>
	
</jstl:if>
<jstl:if test="${util.moreThanThirtyDays(row.trip.startDate)==true}"> 
		<%=estilo = "white"%>
	
</jstl:if> 
			</jstl:when>

			<jstl:when test="${row.status=='REJECTED'}">
				<%=estilo = "grey"%>

			</jstl:when>

			<jstl:when test="${row.status=='DUE'}">

				<%=estilo = "yellow"%>
			</jstl:when>

			<jstl:when test="${row.status=='ACCEPTED'}">
				<%=estilo = "green"%>
			</jstl:when>

			<jstl:when test="${row.status=='CANCELLED'}">
				<%=estilo = "cyan"%>
			</jstl:when>

		</jstl:choose> --%>
	
	<spring:message code="notification.format.date" var="pattern"></spring:message>
	<spring:message code="notification.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" format="${pattern}" class="<%= estilo %>" />

	<spring:message code="notification.status" var="statusHeader" />
	<display:column property="status" title="${statusHeader}" sortable="true" class="<%= estilo %>" />

	<spring:message code="notification.reason" var="reasonWhyHeader" />
	<display:column property="reasonWhy" title="${reasonWhyHeader}" sortable="true" class="<%= estilo %>" />

	<spring:message code="notification.creditCard.holderName" var="creditCardHeader" />
	<display:column property="creditCard.holderName" title="${creditCardHeader}" sortable="true" class="<%= estilo %>" />
	
	<spring:message code="notification.trip1" var="trip1" />
	<display:column title="${trip1}" class="<%= estilo %>" />

</display:table>
