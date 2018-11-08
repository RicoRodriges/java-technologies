<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/head.jsp" %>
    <title>Register teacher</title>

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
        <form method="post" class="col-md-6" onsubmit="return onSubmitRegisterTeacher()">
            <c:if test="${requestScope.error != null}">
                <div style="color:red;">${requestScope.error}</div>
            </c:if>
            <div class="form-group">
                <label for="user">Teacher name</label>
                <input type="email" class="form-control" id="user" name="user"
                       placeholder="Enter teacher email">
                <small id="userMessage" class="form-text"></small>
            </div>
            <div class="form-group">
                <label for="password">Teacher name</label>
                <input type="password" class="form-control" id="password" name="password"
                       placeholder="Enter teacher password">
                <small id="passwordMessage" class="form-text"></small>
            </div>
            <div class="form-group">
                <label for="univer">University</label>
                <select name="univer" class="form-control" id="univer">
                    <option value=""></option>
                    <c:forEach items="${requestScope.universities}" var="q">
                        <option value="${q.id}">${q.name}</option>
                    </c:forEach>
                </select>
                <small id="univerMessage" class="form-text"></small>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="registration.register"/></button>
        </form>
        <div class="col-md-3"></div>
    </div>
</main>

<%@ include file="parts/footer.jsp" %>

</body>
</html>
