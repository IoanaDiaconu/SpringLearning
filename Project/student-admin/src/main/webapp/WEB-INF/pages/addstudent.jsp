<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style/studentStyle1.css" />">
    <title>Add/Edit Record</title>
</head>
<body>
<form:form commandName="student" action="${pageContext.request.contextPath}/save" method="post">
    <fieldset>
        <legend>General information</legend>
        <ol>
            <li>
                <label class="labelField"><s:message code="student.first_name"/></label>
                <form:hidden path="id"/> <form:input path="first_name"/>
                <form:errors path="first_name" cssClass="error"/>
            </li>
            <li>
                <label class="labelField"><s:message code="student.last_name"/></label>
                <form:input path="last_name"/>
                <form:errors path="last_name" cssClass="error"/>
            </li>
            <li>
                <label class="labelField"><s:message code="student.age"/></label>
                <form:input path="age"/>
                <form:errors path="age" cssClass="error"/>
            </li>
            <li>
                <label class="labelField"><s:message code="student.gender"/></label>
                <form:radiobutton path="gender" value="F" label="F"/>
                <form:radiobutton path="gender" value="M" label="M"/>
            </li>
        </ol>
    </fieldset>
    <fieldset>
        <legend>Address Info</legend>
        <ol>
            <li>
                <label class="labelField"><s:message code="student.country"/></label>
                <form:select path="country">
                    <form:option value="0" label="Select"/>
                    <form:option value="Romania" label="Romania"/>
                    <form:option value="USA" label="USA"/>
                    <form:option value="UK" label="UK"/>
                </form:select>
            </li>
            <li>
                <label class="labelField"><s:message code="student.address"/></label>
                <form:textarea path="address"/>
            </li>
        </ol>
    </fieldset>
    <fieldset>
        <legend>Student Info</legend>
        <ol>
            <li>
                <label class="labelField"><s:message code="student.speciality"/></label>
                <form:checkbox cssClass="checkboxes" path="speciality" value="csac" label="csac"/>
                <form:checkbox path="speciality" value="acs" label="acs"/>
            </li>
            <li>
                <label class="labelField"><s:message code="courses.name"/></label>
                <form:select path="course">
                    <c:forEach items="${courses}" var="p">
                        <form:option value="${p}" label="${p.course_name}"/>
                    </c:forEach>
                </form:select>
            </li>
            <li>
                <label class="labelField"><s:message code="student.document"/></label>
                <input type="file" name="file" id="file">
            </li>
            <li><input type="submit" value="Save"/></li>
        </ol>
    </fieldset>
</form:form>
</body>
</html>
