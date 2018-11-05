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

        <span id="vue-section">
            <choose-university v-bind:universities="universities"></choose-university>
        </span>

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
    Vue.component('choose-group', {
        props: ['groups'],
        template: '<div class="form-group">\n' +
            '           <label for="groups">Group: </label>\n' +
            '           <select id="groups" name="groups"><option value=""/>' +
            '               <option v-for="group in groups"' +
            '                       v-bind:value="group.id">{{ group.name }}</option>' +
            '           </select>\n' +
            '      </div>'
    });

    Vue.component('choose-department', {
        props: ['departments'],
        template: '<div><div class="form-group">\n' +
            '           <label for="departments">Department: </label>\n' +
            '           <select id="departments" name="departments" v-model="selectedDepartment"><option value="[]"/>' +
            '               <option v-for="dep in departments"' +
            '                       v-bind:value="JSON.stringify(dep.groups)">{{ dep.name }}</option>' +
            '           </select>\n' +
            '      </div>' +
            '      <choose-group v-if="selectedDepartment != \'[]\'" v-bind:groups="JSON.parse(selectedDepartment)"></choose-group></div>',
        data: function () {
            return {
                selectedDepartment: "[]"
            };
        }
    });

    Vue.component('choose-faculty', {
        props: ['faculties'],
        template: '<div><div class="form-group">\n' +
            '           <label for="faculty">Faculty: </label>\n' +
            '           <select id="faculty" name="faculty" v-model="selectedFaculty"><option value="[]"/>' +
            '               <option v-for="fac in faculties"' +
            '                       v-bind:value="JSON.stringify(fac.departments)">{{ fac.name }}</option>' +
            '           </select>\n' +
            '      </div>' +
            '      <choose-department v-if="selectedFaculty != \'[]\'" v-bind:departments="JSON.parse(selectedFaculty)"></choose-department></div>',
        data: function () {
            return {
                selectedFaculty: "[]"
            };
        }
    });

    Vue.component('choose-university', {
        props: ['universities'],
        template: '<div class="row">\n' +
            '            <div class="col-md-3"></div>\n' +
            '            <div class="col-md-6">\n' +
            '                <div class="form-group">\n' +
            '                    <label for="university">University: </label>\n' +
            '                    <select id="university" name="university" v-model="selectedUni">' +
            '                        <option value="[]"/>' +
            '                        <option v-for="uni in universities"' +
            '                                v-bind:value="JSON.stringify(uni.faculties)">{{ uni.name }}</option>' +
            '                    </select>\n' +
            '                </div>\n' +
            '                <choose-faculty v-if="selectedUni != \'[]\'" v-bind:faculties="JSON.parse(selectedUni)"></choose-faculty>' +
            '            </div>\n' +
            '            <div class="col-md-3"></div>\n' +
            '        </div>',
        data: function () {
            return {
                selectedUni: "[]"
            };
        }
    });

    $.ajax({
        type: 'GET',
        url: '/universities',
        success: function (data) {
            new Vue({
                el: '#vue-section',
                data: {
                    universities: data
                }
            });
        }
    });
</script>
</body>
</html>
