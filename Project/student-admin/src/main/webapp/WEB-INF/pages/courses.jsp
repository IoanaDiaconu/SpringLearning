<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style/tableStyle.css" />">
    <title>Available Courses</title>
</head>
<body>
<sec:authorize ifAnyGranted="ROLE_ADMIN">
    <a class="addRecord" href="${pageContext.request.contextPath}/addCourse">Add new course</a>
</sec:authorize>

<div>
    <c:url value="/j_spring_security_logout" var="logoutUrl"/>
    <p>
        <label>Hello <sec:authentication property="principal.username"/> <a class="addRecord"
                                                                            href="${logoutUrl}">Logout</a></label>
    </p>
</div>
<h1>All Records</h1>

<div>
    <c:choose>
        <c:when test="${fn:length(courses)>0}">
            <table id="records">
                <thead>
                <tr>
                    <th><s:message code="table.course_name"/></th>
                    <th><s:message code="table.course_description"/></th>
                    <th><s:message code="table.students"/></th>
                    <c:choose>
                        <c:when test="${username == 'administrator'}">
                            <th><s:message code="table.edit"/></th>
                            <th><s:message code="table.delete"/></th>
                        </c:when>
                    </c:choose>
                </tr>
                <c:forEach items="${courses}" var="p">
                    <tr>
                        <td><c:out value="${p.course_name}"></c:out></td>
                        <td><c:out value="${p.course_description}"></c:out></td>
                        <td><c:forEach items="${p.students}" var="s">
                            <c:out value="${s.first_name} ${s.last_name},"></c:out>
                        </c:forEach>
                        </td>
                        <sec:authorize ifAnyGranted="ROLE_ADMIN">
                            <td><a href="<c:url value="/editCourse/${p.id}"/>"><img src="/app/img/edit.png" border="0"
                                                                                    title="Edit"></a></td>
                            <td><a href="<c:url value="/deleteCourse/${p.id}"/>"
                                   onclick="return confirm('Are you sure you want to delete this course?')"><img
                                    src="/app/img/delete.png" border="0" title="Delete this document"></a></td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
                </thead>
            </table>
        </c:when>
        <c:otherwise>
            <label>Sorry! No Record To Display</label>@
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>