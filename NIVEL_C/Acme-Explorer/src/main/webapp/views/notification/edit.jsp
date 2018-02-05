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



<form:form action="${RequestURI }" modelAttribute="notification">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	
	<form:label path="gauge">
		<spring:message code="notification.gauge" />:
	</form:label>
	<form:select id="gauge" path="gauge" >
		<form:option value="0" label="----" />		
		<form:option value="1" label="1" />
		<form:option value="2" label="2" />
		<form:option value="3" label="3" />
	</form:select>
	<form:errors cssClass="error" path="gauge" />
	<br />
	
	<form:label path="moment">
		<spring:message code="notification.moment" />:
	</form:label>
	<form:input path="moment" readonly="true"/>
	<form:errors cssClass="error" path="moment" />
	<br />
	
	<form:label path="trip">
		<spring:message code="notification.trip" />:
	</form:label>
	<form:select id="trips" path="trip" >
		<form:option value="0" label="----" />		
		<form:options items="${trips}" itemValue="id"
			itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="trip" />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="notification.save" />" />&nbsp; 
		
	<jstl:if test="${notification.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="messageFolder.delete" />"
			onclick="javascript: return confirm('<spring:message code="notification.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="notification.cancel" />"
		onclick="javascript:  window.location.replace('notification/manager_/list.do');" />
</form:form>
