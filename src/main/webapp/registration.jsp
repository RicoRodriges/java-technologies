<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>

    <%@ include file="parts/head.jsp" %>

    <title><fmt:message key="registration.registration"/></title>
</head>
<body>

<%@ include file="parts/lang.jsp" %>

<div class="container login-form">
    <form action="registrationServlet" method="post" name="registrationForm">
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <h2><fmt:message key="registration.registration"/></h2>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="emailInput"><fmt:message key="registration.email"/> </label>
                    <input type="email" class="form-control" id="emailInput" name="user"
                           placeholder="<fmt:message key="registration.enteremail"/>">
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="passwordFirst"><fmt:message key="registration.password"/> </label>
                    <input type="password" id="passwordFirst" class="form-control"
                           placeholder="<fmt:message key="registration.enterpassword"/>"
                           name="password">
                    <label for="passwordSecond"></label>
                    <input type="password" id="passwordSecond" class="form-control pass-repeat"
                           placeholder="<fmt:message key="registration.repeatpassword"/>"
                           name="passwordRepeat">
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
            <c:if test="${not empty requestScope.flag}">
             <p class="text-danger"> <c:out value="${requestScope.flag}"/> </p>
            </c:if>
                <button type="submit" class="btn btn-primary"><fmt:message key="registration.register"/></button>
                <a href="/login.jsp"><fmt:message key="registration.login"/> </a>
            </div>
        </div>
    </form>
</div>


</body>
</html>
