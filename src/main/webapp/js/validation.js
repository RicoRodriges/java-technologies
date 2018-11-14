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
            return "Invalid email";
        }
        return null;
    }) && isOk;
    isOk = valid("passwordInput", "passwordMessage", function (val) {
        var isCorrect = val.length >= 5;
        if (!isCorrect) {
            return "Password has to consist of 5 or more chars";
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
            return "Invalid email";
        }
        return null;
    }) && isOk;
    isOk = valid("passwordFirst", "passwordMessage", function (val) {
        var isCorrect = val.length >= 5;
        if (!isCorrect) {
            return "Password has to consist of 5 or more chars";
        }
        return null;
    }) && isOk;
    isOk = valid("passwordSecond", "secondPasswordMessage", function (val) {
        var val1 = document.getElementById("passwordFirst").value;
        var isCorrect = val === val1;
        if (!isCorrect) {
            return "Passwords don't match";
        }
        return null;
    }) && isOk;
    isOk = valid("groups", "groupsMessage", function (val) {
        var isCorrect = val > 0;
        if (!isCorrect) {
            return "Where is your group???";
        }
        return null;
    }) && isOk;
    return isOk;
}

function onSubmitEditTest(test) {
    var errors = [];
    if (test.name.length < 1) {
        errors.push("Empty test name");
    }
    if (test.quest.length < 1) {
        errors.push("Test doesn't contain questions");
    } else {
        Array.from(test.quest).forEach(function (question) {
            if (question.Qtext.length < 1) {
                errors.push("Empty question name");
            }
            if (question.answers.length < 1) {
                errors.push("Question doesn't contain answers")
            } else {
                Array.from(question.answers).forEach(function (answer) {
                    if (answer.Atext.length < 1) {
                        errors.push("Empty answer");
                    }
                });
            }
        });
    }
    if (test.groups == null || test.groups.length < 1) {
        errors.push("Please, choose groups");
    }
    return errors;
}

function onSubmitCreateGroup() {
    var isOk = true;
    isOk = valid("univer", "univerMessage", function (val) {
        var isCorrect = val != null && val.length > 0 || val == null;
        if (!isCorrect) {
            return "Choose a university";
        }
        return null;
    }) && isOk;
    isOk = valid("fac", "facMessage", function (val) {
        var isCorrect = val.trim().length > 0;
        if (!isCorrect) {
            return "Choose a faculty";
        }
        return null;
    }) && isOk;
    isOk = valid("dep", "depMessage", function (val) {
        var isCorrect = val.trim().length > 0;
        if (!isCorrect) {
            return "Choose a department";
        }
        return null;
    }) && isOk;
    isOk = valid("group", "groupMessage", function (val) {
        var isCorrect = val.trim().length > 0;
        if (!isCorrect) {
            return "Choose a group";
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
            return "Invalid email";
        }
        return null;
    }) && isOk;
    isOk = valid("password", "passwordMessage", function (val) {
        var isCorrect = val.length >= 5;
        if (!isCorrect) {
            return "Password has to consist of 5 or more chars";
        }
        return null;
    }) && isOk;
    isOk = valid("univer", "univerMessage", function (val) {
        var isCorrect = val.length > 0;
        if (!isCorrect) {
            return "Choose a university";
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
            return "Input university name";
        }
        return null;
    }) && isOk;
    return isOk;
}