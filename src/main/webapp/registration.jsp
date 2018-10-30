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
    <form action="/registration" method="post" name="registrationForm">
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
                <div class="form-group">
                    <label for="university">University: </label>
                    <select id="university" name="university"></select>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="faculties">Faculty: </label>
                    <select id="faculties" name="faculties"></select>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="departments">Department: </label>
                    <select id="departments" name="departments"></select>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="groups">Group: </label>
                    <select id="groups" name="groups"></select>
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
                <a href="/login"><fmt:message key="registration.login"/> </a>
            </div>
        </div>
    </form>
</div>

<script>
    var universities;
    $.ajax({
        type: 'GET',
        url: '/universities',
        success: function (data) {
            // the next thing you want to do
            var university_select = $('#university');
            university_select.empty();
            $('#faculties').empty();
            $('#departments').empty();
            $('#groups').empty();
            university_select.append('<option value=0></option>');
            for (var i = 0; i < data.length; i++) {
                university_select.append('<option value=' + data[i].id + '>' + data[i].name + '</option>');
            }
            universities = data;
        }
    });

    $('#university').change(function () {
        var id = $(this).find(':selected')[0].value;
        var fac_select = $('#faculties');
        fac_select.empty();
        $('#departments').empty();
        $('#groups').empty();
        for (var i = 0; i < universities.length; i++) {
            if (id == universities[i].id) {
                fac_select.append('<option value=0></option>');
                for (var j = 0; j < universities[i].faculties.length; j++) {
                    fac_select.append('<option value=' + universities[i].faculties[j].id + '>' + universities[i].faculties[j].name + '</option>');
                }
            }
        }
    });
</script>
</body>
</html>
