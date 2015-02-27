<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style/studentStyle1.css" />">
    <title>Add/Edit Record</title>
</head>
<body>
<form:form commandName="courses" action="${pageContext.request.contextPath}/saveCourse" method="post">
    <fieldset>
    <legend>General information</legend>
    <ol>
        <li>
            <label><s:message code="table.course_name"/></label>
            <form:hidden path="id"/> <form:input path="course_name"/>
        </li>
        <li>
            <label><s:message code="table.course_description"/></label>
            <form:input path="course_description"/>
        </li>
        <li><input type="submit" value="Save"/></li>
    </ol>
    </fieldset>
</form:form>
</body>
</html>
