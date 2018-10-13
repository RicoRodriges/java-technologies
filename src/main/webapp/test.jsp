<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/head.jsp" %>
    <title><fmt:message key="test.testpage"/></title>
</head>
<body>

    <c:set value="${test.getName()}" var="headerTitle" />
    <c:set value="The test consists of questions with with choice of answer. There can be one or more right answers. Pay attention to wordings. After the test don't close browser or redirect into another web-site." var="headerDesc" />
    <%@ include file="parts/header.jsp" %>

    <form action="result" method="POST" name="testpageForm">
        <c:set var="qNumber" value="0"/>
        <c:forEach items="${test.getQuest()}" var="q">
            <c:set var="qNumber" value="${qNumber+1}"/>
            <c:set var="correctAnswers" value="0"/>
            <c:forEach items="${q.getAnswers()}" var="a">
                <c:if test="${a.getIsRight()}">
                    <c:set var="correctAnswers" value="${correctAnswers+1}"/>
                </c:if>
            </c:forEach>

            <div class="row justify-content-md-center">
                <div class="col-6.col-md-1 order-first">
                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                        <span class="badge badge-secondary badge-pill">${qNumber}</span>
                    </h4>
                </div>
                <div class="col-md-8 order-md-1">
                    <h5 class="d-flex justify-content-between align-items-center mb-3">
                            ${q.getText()}
                    </h5>
                    <c:forEach items="${q.getAnswers()}" var="a">
                        <c:if test="${correctAnswers == 1}">
                            <div>
                                <input name="q${q.getId()}" type="radio" value="${a.getId()}">
                                    ${a.getText()}
                            </div>
                        </c:if>
                        <c:if test="${correctAnswers > 1 or correctAnswers == 0}">
                            <div>
                                <input name="q${q.getId()}" type="checkbox" value="${a.getId()}">
                                    ${a.getText()}
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <hr class="mb-4">
        </c:forEach>

        <div class="row justify-content-md-center">
            <div class="col-md-4">
                <input type="hidden" name="testId" value="${test.getId()}">
                <button class="btn btn-primary" type="submit"><fmt:message key="test.send"/></button>
                <a href="/catalog"><fmt:message key="test.close"/></a>
            </div>
        </div>
    </form>

    <%@ include file="parts/footer.jsp" %>

</body>
</html>