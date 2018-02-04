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



<form:form action="${RequestURI }" modelAttribute="applicationFor">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="explorer"/>
	
	<form:hidden path="moment"/>
	<form:hidden path="status"/>
	<form:hidden path="reasonWhy"/>
	<form:hidden path="trip"/>
	<form:hidden path="creditCard"/>
	<form:hidden path="manager"/>
	


<security:authorize access="hasRole('EXPLORER')">

<p><spring:message code="trip.ticker" />: <jstl:out value="${trip.ticker}"></jstl:out></p>
	<form:label path="comments">
		<spring:message code="applicationfor.comment" />:
	</form:label>

	
	<form:input path="comments" />
	<form:errors cssClass="error" path="comments" /> 
	<br />
	<br />
	<br />
	


	<!--  <h2><spring:message code="trip.aplicationfor.name.table" /></h2>	-->

	
	

	
	
	<br />

</security:authorize>




	<input type="submit" name="save" value="<spring:message code="applicationfor.save" />" />&nbsp;
	 
	<jstl:if test="${applicationFor.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="applicationfor.delete" />"
			onclick="javascript: return confirm('<spring:message code="applicationfor.confirm.delete" />')" />&nbsp;
			
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="applicationfor.cancel" />"
		onclick="javascript:  window.location.replace('trip/explorer/list-not-apply.do');" />
	<br />
</form:form>
