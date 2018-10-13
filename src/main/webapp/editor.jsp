<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/head.jsp" %>
    <title></title>

    <script src="js/onePageEditor.js"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="js/jquery.redirect.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#testTheme option[value=${test.getType().getName()}]").prop ('selected', true);
        });
    </script>
</head>
<body>

    <fmt:message key="catalog.tts" var="headerTitle" />
    <fmt:message key="catalog.description" var="headerDesc" />
    <%@ include file="parts/header.jsp" %>

    <main role="main" class="container">
        <div class="jumbotron">
            <h2><fmt:message key="testeditor.edittestform"/></h2>
            <div class="form-group">
                <label for="testNameInput"><fmt:message key="testeditor.testtitle"/></label>
                <input type="text" class="form-control" id="testNameInput" value="${test.getName()}">
            </div>
            <label for="testTheme"><fmt:message key="testeditor.choosetheme"/></label>
            <select class="form-control" id="testTheme">
                <option value="Math"><fmt:message key="catalog.math"/></option>
                <option value="Physics"><fmt:message key="catalog.physics"/></option>
                <option value="Russian"><fmt:message key="catalog.russian"/></option>
                <option value="English"><fmt:message key="catalog.english"/></option>
            </select>

            <button type="submit" class="btn btn-primary create-question-button" onclick="addQuestionField()">
            <fmt:message key="testeditor.addquestion"/>
            </button>
            <c:if test="${not empty problem}">
                <c:choose>
                    <c:when test="${problem eq 'TEST_EXISTS'}">
                        <small id="editorWarn" class="form-text text-danger">
                        <fmt:message key="testeditor.testexists"/>
                        </small>
                    </c:when>
                    <c:when test="${problem eq 'TEST_NO_NAME'}">
                        <small id="editorWarn" class="form-text text-danger">
                        <fmt:message key="testeditor.forgotname"/>
                        </small>
                    </c:when>
                    <c:when test="${problem eq 'EMPTY_QUESTIONS'}">
                        <small id="editorWarn" class="form-text text-danger">
                        <fmt:message key="testeditor.noquestions"/>
                        </small>
                    </c:when>
                    <c:when test="${problem eq 'QUESTION_EXISTS'}">
                        <small id="editorWarn" class="form-text text-danger">
                        <fmt:message key="testeditor.duplquest"/>
                        </small>
                    </c:when>
                    <c:when test="${problem eq 'QUESTION_NO_TEXT'}">
                        <small id="editorWarn" class="form-text text-danger">
                        <fmt:message key="testeditor.emptyquesttext"/>
                        </small>
                    </c:when>
                    <c:when test="${problem eq 'QUESTION_NO_ANSWERS'}">
                        <small id="editorWarn" class="form-text text-danger">
                        <fmt:message key="testeditor.emptyquest"/>
                        </small>
                    </c:when>
                    <c:when test="${problem eq 'ANSWER_EXISTS'}">
                        <small id="editorWarn" class="form-text text-danger">
                        <fmt:message key="testeditor.duplans"/>
                        </small>
                    </c:when>
                    <c:when test="${problem eq 'ANSWER_NO_TEXT'}">
                        <small id="editorWarn" class="form-text text-danger">
                        <fmt:message key="testeditor.emptyans"/>
                        </small>
                    </c:when>
                </c:choose>
            </c:if>
            <div class="question-container" id="question-parent">
                <c:if test="${not empty test}">
                    <c:set var="contId" value="1000"/>
                    <c:forEach items="${test.getQuest()}" var="q">
                        <div class="question form-group form-control" id="question${contId}">
                            <button type="button" class="close" aria-label="Close" onclick="deleteQuestion(${contId})">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <input type="question" placeholder="Enter question" class="form-control question-text"
                                   value="${q.getText()}">
                            <div class="answer-container col-md-8" id="answer-container${contId}">
                                <button type="submit" class="btn btn-primary create-answer-button"
                                        onclick="addAnswerField('${contId}')">
                                        <fmt:message key="testeditor.addanswer"/>
                                </button>
                                <c:set var="contId" value="${contId+1}"/>
                                <c:forEach items="${q.getAnswers()}" var="a">
                                    <c:set var="ansId" value="${contId*100}"/>
                                    <div class="answer-group" id="answ${ansId}">
                                        <div class="answer form-group">
                                            <button type="button" class="close" aria-label="Close"
                                                    onclick="deleteAnswer(${ansId})">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                            <label></label>
                                            <input type="question" placeholder="Enter answer"
                                                   class="form-control answer-text" value="${a.getText()}">
                                        </div>
                                        <div class="form-check">
                                            <label class="form-check-label">
                                                <c:if test="${a.getRight()}">
                                                    <input class="form-check-input" type="checkbox" checked>
                                                </c:if>
                                                <c:if test="${not a.getRight()}">
                                                    <input class="form-check-input" type="checkbox">
                                                </c:if>
                                                <fmt:message key="testeditor.isrightanswer"/>
                                            </label>
                                        </div>
                                        <hr>
                                    </div>
                                    <c:set var="ansId" value="${ansId+1}"/>
                                </c:forEach>
                            </div>
                        </div>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${test.getId() == -1}">
                            <input type="hidden" id="edit" value="0">
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" id="edit" value="1">
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
            <button type="submit" class="btn btn-primary" onclick="biuldTest()">
            <fmt:message key="testeditor.save"/>
            </button>
            <a href="<c:url value="/catalog"/>" class="btn btn-danger">
            <fmt:message key="testeditor.cancel"/>
            </a>
        </div>
    </main>

    <%@ include file="parts/footer.jsp" %>

</body>
</html>
