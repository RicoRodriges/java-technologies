<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/head.jsp" %>
    <title>403</title>
</head>
<body>

    <fmt:message key="catalog.tts" var="headerTitle" />
    <fmt:message key="catalog.description" var="headerDesc" />
    <%@ include file="parts/header.jsp" %>

        <div class="error">
            <h1>403</h1>
            <h2>Sorry, request is denied</h2>
            <a href="/catalog">Go home</a>
        </div>

    <%@ include file="parts/footer.jsp" %>

</body>
</html>
