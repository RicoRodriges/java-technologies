<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>

    <%@ include file="parts/head.jsp" %>

    <title><fmt:message key="login.login"/></title>
</head>
<body>

<%@ include file="parts/lang.jsp" %>

<div class="container login-form">
    <form action="/login" method="post" name="loginForm">
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <h2><fmt:message key="login.authorization"/></h2>
                <hr>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="emailInput"><fmt:message key="login.email"/></label>
                    <input type="email" class="form-control" name="user" id="emailInput"
                           placeholder="<fmt:message key="login.enteremail"/>">
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>

        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="passwordInput"><fmt:message key="login.password"/></label>
                    <input type="password" name="password" id="passwordInput" class="form-control"
                           placeholder="<fmt:message key="login.enterpassword"/>">
                    <small id="passwordWarn" class="form-text text-muted">
                        <fmt:message key="login.passwordAdvice"/>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
            <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
            <p class="text-danger"> <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/> </p>
            </c:if>
                <button type="submit" class="btn btn-primary" id="loginBtn"><fmt:message key="login.login"/></button>
                <a href="/registration"><fmt:message key="login.singup"/> </a>
            </div>
        </div>
    </form>
</div>

</body>
</html>