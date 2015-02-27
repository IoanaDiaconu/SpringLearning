<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style/tableStyle.css" />">
    <title>Students</title>
</head>
<body>
    <a class="addRecord" href="${pageContext.request.contextPath}/add">Add new record</a>
    <div>
        <c:url value="/j_spring_security_logout" var="logoutUrl" />
        <p>
            <label>Hello ${username}. <a class="addRecord" href="${logoutUrl}">Logout</a></label>
        </p>
    </div>
<h1>All Records</h1>
<div>
    <c:choose>
        <c:when test="${fn:length(students)>0}">
            <table id="records">
                <thead>
                <tr>
                    <th><s:message code="table.edit"/></th>
                    <th><s:message code="table.delete"/></th>
                    <th><s:message code="table.download"/></th>
                    <th><s:message code="table.first_name"/></th>
                    <th><s:message code="table.last_name"/></th>
                    <th><s:message code="table.age"/></th>
                    <th><s:message code="table.gender"/></th>
                    <th><s:message code="table.country"/></th>
                    <th><s:message code="table.address"/></th>
                    <th><s:message code="table.speciality"/></th>
                    <th><s:message code="courses.name"/></th>
                </tr>


                <c:forEach items="${students}" var="p">
                    <tr>
                        <td><a href="<c:url value="/edit/${p.id}"/>"><img src="/app/img/edit.png" border="0" title="Edit"></a></td>
                        <td><a href="<c:url value="/delete/${p.id}"/>" onclick="return confirm('Are you sure you want to delete this document?')"><img src="/app/img/delete.png" border="0" title="Delete this document"></a></td>
                        <td><a href="<c:url value="/download/${p.id}"/>"><img src="/app/img/download.jpg" border="0" title="Save"></a></td>
                        <td><c:out value="${p.first_name}"></c:out></td>
                        <td><c:out value="${p.last_name}"></c:out></td>
                        <td><c:out value="${p.age}"></c:out></td>
                        <td><c:out value="${p.gender}"></c:out></td>
                        <td><c:out value="${p.country}"></c:out></td>
                        <td><c:out value="${p.address}"></c:out></td>
                        <td><c:out value="${p.speciality}"></c:out></td>
                        <td><c:out value="${p.course.course_name}"></c:out></td>
                    </tr>
                </c:forEach>
                </thead>
            </table>
        </c:when>
        <c:otherwise>
            <label>Sorry! No Record To Display</label>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>