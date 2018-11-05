<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/head.jsp" %>
    <title>Register Tutor</title>

    <script src="/js/onePageEditor.js"></script>
    <script src="/js/jquery.redirect.js"></script>

</head>
<body>

    <fmt:message key="catalog.tts" var="headerTitle" />
    <fmt:message key="catalog.description" var="headerDesc" />
    <%@ include file="parts/header.jsp" %>

    <main role="main" class="container">
        <form method="post">
            <input name="user" placeholder="Enter tutor email"><br>
            <input name="password" placeholder="Enter tutor password"><br>
            <label>
                <select name="univer">
                    <option value=""></option>
                    <c:forEach items="${requestScope.universities}" var="q">
                        <option value="${q.id}">${q.name}</option>
                    </c:forEach>
                </select>
            </label><br>
            <input type="submit" value="Submit">
        </form>
    </main>

    <%@ include file="parts/footer.jsp" %>

</body>
</html>
