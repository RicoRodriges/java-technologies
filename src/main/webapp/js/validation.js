function valid(valueId, messageId, validate) {
    var e = document.getElementById(valueId);
    var val = e != null ? e.value : null;
    var message = validate(val);
    var messageObject = document.getElementById(messageId);
    if (message == null && messageObject != null) {
        messageObject.innerHTML = "";
        return true;
    } else {
        messageObject.innerHTML = message;
        messageObject.style.color = "red";
        return false;
    }
}

function onSubmitLogin() {
    var isOk = true;
    isOk = valid("emailInput", "emailMessage", function (val) {
        var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
        var isCorrect = email_regex.test(val);
        if (!isCorrect) {
            return "Неверный email";
        }
        return null;
    }) && isOk;
    isOk = valid("passwordInput", "passwordMessage", function (val) {
        var isCorrect = val.length >= 5;
        if (!isCorrect) {
            return "Пароль должен быть более 5 символов";
        }
        return null;
    }) && isOk;
    return isOk;
}

function onSubmitRegisterUser() {
    var isOk = true;
    isOk = valid("emailInput", "emailMessage", function (val) {
        var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
        var isCorrect = email_regex.test(val);
        if (!isCorrect) {
            return "Неверный email";
        }
        return null;
    }) && isOk;
    isOk = valid("passwordFirst", "passwordMessage", function (val) {
        var isCorrect = val.length >= 5;
        if (!isCorrect) {
            return "Пароль должен быть более 5 символов";
        }
        return null;
    }) && isOk;
    isOk = valid("passwordSecond", "secondPasswordMessage", function (val) {
        var val1 = document.getElementById("passwordFirst").value;
        var isCorrect = val === val1;
        if (!isCorrect) {
            return "Пароли должены совпадать";
        }
        return null;
    }) && isOk;
    isOk = valid("groups", "groupsMessage", function (val) {
        var isCorrect = val > 0;
        if (!isCorrect) {
            return "Где группа???";
        }
        return null;
    }) && isOk;
    return isOk;
}

function onSubmitEditTest(test) {
    var errors = [];
    if (test.name.length < 1) {
        errors.push("Пустое имя теста");
    }
    if (test.quest.length < 1) {
        errors.push("В тесте нет вопросов");
    } else {
        Array.from(test.quest).forEach(function (question) {
            if (question.Qtext.length < 1) {
                errors.push("Вопрос с пустым именем");
            }
            if (question.answers.length < 1) {
                errors.push("Вопрос без вариантов ответа")
            } else {
                Array.from(question.answers).forEach(function (answer) {
                    if (answer.Atext.length < 1) {
                        errors.push("Пустой ответ");
                    }
                });
            }
        });
    }
    if (test.groups == null || test.groups.length < 1) {
        errors.push("Не выбраны группы");
    }
    return errors;
}

function onSubmitCreateGroup() {
    var isOk = true;
    isOk = valid("univer", "univerMessage", function (val) {
        var isCorrect = val != null && val.length > 0 || val == null;
        if (!isCorrect) {
            return "Выберите университет";
        }
        return null;
    }) && isOk;
    isOk = valid("fac", "facMessage", function (val) {
        var isCorrect = val.trim().length > 0;
        if (!isCorrect) {
            return "Введите название факультета";
        }
        return null;
    }) && isOk;
    isOk = valid("dep", "depMessage", function (val) {
        var isCorrect = val.trim().length > 0;
        if (!isCorrect) {
            return "Введите название кафедры";
        }
        return null;
    }) && isOk;
    isOk = valid("group", "groupMessage", function (val) {
        var isCorrect = val.trim().length > 0;
        if (!isCorrect) {
            return "Введите название группы";
        }
        return null;
    }) && isOk;
    return isOk;
}

function onSubmitRegisterTeacher() {
    var isOk = true;
    isOk = valid("user", "userMessage", function (val) {
        var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
        var isCorrect = email_regex.test(val);
        if (!isCorrect) {
            return "Неверный email";
        }
        return null;
    }) && isOk;
    isOk = valid("password", "passwordMessage", function (val) {
        var isCorrect = val.length >= 5;
        if (!isCorrect) {
            return "Пароль должен быть более 5 символов";
        }
        return null;
    }) && isOk;
    isOk = valid("univer", "univerMessage", function (val) {
        var isCorrect = val.length > 0;
        if (!isCorrect) {
            return "Выберите университет";
        }
        return null;
    }) && isOk;
    return isOk;
}

function onSubmitRegisterUniver() {
    var isOk = true;
    isOk = valid("name", "nameMessage", function (val) {
        var isCorrect = val.trim().length > 0;
        if (!isCorrect) {
            return "Введите название университета";
        }
        return null;
    }) && isOk;
    return isOk;
}