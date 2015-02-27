<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style/loginStyle.css" />">
    <title>Login</title>
</head>
<body>
<div class="container">
    <div class="login">
        <h1>Login</h1>
        <form action="<c:url value="/j_spring_security_check"></c:url>" method="post" role="form">
                    <label>
                        <span colspan="2" style="color: red">${message}</span>
                    </label>
                    <p>
                        <label><s:message code="user.name"/></label>
                        <input type="text" name="username"/>
                    </p>
                    <p>
                        <label><s:message code="user.password"/></label>
                        <input type="password" name="password"/>
                    </p>
                    <p>
                        <label><s:message code="user.remember"/></label>
                        <input type="checkbox" name="remember-me"/>
                    </p>
                    <p>
                        <label>Language</label>
                        <a href="?locale=en">English</a>
                        <a href="?locale=fr">French</a>
                    </p>
                    <p class="submit">
                        <input type="submit" value="Login"/>
                    </p>
            </form>
    </div>
</div>
</body>
</html>