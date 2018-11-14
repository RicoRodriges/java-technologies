<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/head.jsp" %>
    <title>Create university</title>

    <script src="/js/onePageEditor.js"></script>
    <script src="/js/jquery.redirect.js"></script>

</head>
<body>

<fmt:message key="catalog.tts" var="headerTitle"/>
<fmt:message key="catalog.description" var="headerDesc"/>
<%@ include file="parts/header.jsp" %>

<main role="main" class="container">
    <div class="row">
        <div class="col-md-3"></div>
        <form method="post" class="col-md-6" onsubmit="return onSubmitRegisterUniver()">
            <c:if test="${requestScope.error != null}">
                <div style="color:red;">${requestScope.error}</div>
            </c:if>
            <div class="form-group">
                <label for="name"><fmt:message key="reg.universityname"/></label>
                <input type="text" class="form-control" id="name" name="name"
                       placeholder="<fmt:message key="reg.enteruniversityname"/>">
                <small id="nameMessage" class="form-text"></small>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="registration.register" /></button>
        </form>
        <div class="col-md-3"></div>
    </div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <fmt:message key="reg.universities"/>
            <ul class="list-group list-group-flush">
                <c:forEach items="${requestScope.universities}" var="q">
                    <li class="list-group-item">${q.name}</li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-3"></div>
    </div>
</main>

<%@ include file="parts/footer.jsp" %>

</body>
</html>
