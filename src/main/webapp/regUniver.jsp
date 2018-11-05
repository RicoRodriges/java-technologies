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
    <form method="post">
        <input name="name" placeholder="Enter university name"><br>
        <input type="submit" value="Create">
    </form>
    Universities:
    <ul>
        <c:forEach items="${requestScope.universities}" var="q">
            <li>${q.name}</li>
        </c:forEach>
    </ul>
</main>

<%@ include file="parts/footer.jsp" %>

</body>
</html>
