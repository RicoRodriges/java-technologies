var questionId = 0;
var answerId = 0;

var testObject = {
    name: "",
    type: "",
    quest: [
        {
            text: "",
            answer: [
                {
                    text: "",
                    isRight: true
                }
            ]
        }

    ]
};


function addQuestionField() {
    console.log("addQuestion");

    var containerId = generateNextQuestionId();

    var questionDiv = document.createElement("div");
    questionDiv.className = "question";
    questionDiv.id = "question".concat(containerId);
    questionDiv.classList.add("form-group");

    var questionDeleteButton = document.createElement("button");
    questionDeleteButton.type = "button";
    questionDeleteButton.className = "close";
    var deleteSpan = document.createElement("span");
    var deleteText = document.createTextNode(String.fromCharCode(215));
    deleteSpan.appendChild(deleteText);
    questionDeleteButton.appendChild(deleteSpan);
    var questionLabel = document.createElement("label");
    var labelText = document.createTextNode("Question text:");
    var questionInput = document.createElement("input");
    questionDeleteButton.onclick = function () {
        var currentQuestion = document.getElementById("question".concat(containerId));
        console.log(document.getElementById("question".concat(containerId)));

        currentQuestion.parentNode.removeChild(currentQuestion);
    };

    questionInput.type = "question";
    questionInput.placeholder = "Enter question";
    questionDiv.classList.add("form-control");
    questionInput.classList.add("form-control");
    questionInput.classList.add("question-text");

    questionDiv.appendChild(questionDeleteButton);
    questionDiv.appendChild(questionLabel);
    questionDiv.appendChild(questionInput);


    var answerContainer = document.createElement("div");
    answerContainer.className = "answer-container col-md-8";
    console.log("generatedId=", containerId);
    answerContainer.id = "answer-container".concat(containerId);
    questionDiv.appendChild(answerContainer);
    var addAnswerButton = document.createElement("button");
    var addAnswerButtonText = document.createTextNode("Add answer");
    addAnswerButton.appendChild(addAnswerButtonText);
    addAnswerButton.value = "Add button";
    addAnswerButton.className = "btn btn-primary create-answer-button";
    addAnswerButton.onclick = function () {
        console.log("addAnswer");

        var answerId = generateNextAnswerId();

        var answerDiv = document.createElement("div");
        answerDiv.className = "answer form-group";
        var answerInput = document.createElement("input");
        answerInput.type = "question";
        answerInput.placeholder = "Enter answer";
        answerInput.classList.add("form-control");
        answerInput.classList.add("answer-text");

        var answerDeleteButton = document.createElement("button");
        answerDeleteButton.type = "button";
        answerDeleteButton.className = "close";
        answerDeleteButton.onclick = function () {
            var currentAnswer = document.getElementById("answ".concat(answerId));
            console.log(document.getElementById("answ".concat(answerId)));
            currentAnswer.parentNode.removeChild(currentAnswer);
        };
        var deleteSpan = document.createElement("span");
        var deleteText = document.createTextNode(String.fromCharCode(215));
        deleteSpan.appendChild(deleteText);
        answerDeleteButton.appendChild(deleteSpan);

        answerDiv.appendChild(answerInput);


        var answerCheckBoxDiv = document.createElement("div");
        answerCheckBoxDiv.className = "form-check";
        var checkboxLabel = document.createElement("label");
        checkboxLabel.className = "form-check-label";
        var checkBoxInput = document.createElement("input");
        checkBoxInput.className = "form-check-input";
        checkBoxInput.type = "checkbox";
        var checkBoxText = document.createTextNode("It's right answer?");
        checkboxLabel.appendChild(checkBoxInput);
        checkboxLabel.appendChild(checkBoxText);
        answerCheckBoxDiv.appendChild(checkboxLabel);

        var hr = document.createElement("hr");

        var answerGroup = document.createElement("div");
        answerGroup.id = "answ".concat(answerId);
        answerGroup.className = "answer-group";
        answerGroup.appendChild(answerDeleteButton);
        answerGroup.appendChild(answerDiv);
        answerGroup.appendChild(answerCheckBoxDiv);
        answerGroup.appendChild(hr);


        var answerContainer = document.getElementById("answer-container".concat(containerId));
        answerContainer.appendChild(answerGroup);

    };
    answerContainer.appendChild(addAnswerButton);

    var parrent = document.getElementById("question-parent");
    parrent.appendChild(questionDiv);

}


function addAnswerField(contId) {
    console.log("addAnswer");
    var answerDiv = document.createElement("div");
    answerDiv.className = "answer form-group";
    var answerInput = document.createElement("input");
    answerInput.type = "question";
    answerInput.placeholder = "Enter answer";
    answerInput.classList.add("form-control");
    answerInput.classList.add("answer-text");


    var answerDeleteButton = document.createElement("button");
    answerDeleteButton.type = "button";
    answerDeleteButton.className = "close";
    answerDeleteButton.onclick = function () {
        var currentAnswer = document.getElementById("answ".concat(contId));
        console.log(document.getElementById("answ".concat(contId)));
        currentAnswer.parentNode.removeChild(currentAnswer);
    };
    var deleteSpan = document.createElement("span");
    deleteSpan.setAttribute("aria-hidden", true);
    var deleteText = document.createTextNode(String.fromCharCode(215));
    deleteSpan.appendChild(deleteText);
    answerDeleteButton.appendChild(deleteSpan);

    answerDiv.appendChild(answerInput);


    var answerCheckBoxDiv = document.createElement("div");
    answerCheckBoxDiv.className = "form-check";
    var checkboxLabel = document.createElement("label");
    checkboxLabel.className = "form-check-label";
    var checkBoxInput = document.createElement("input");
    checkBoxInput.className = "form-check-input";
    checkBoxInput.type = "checkbox";
    var checkBoxText = document.createTextNode("It's right answer?");
    checkboxLabel.appendChild(checkBoxInput);
    checkboxLabel.appendChild(checkBoxText);
    answerCheckBoxDiv.appendChild(checkboxLabel);

    var hr = document.createElement("hr");

    var answerGroup = document.createElement("div");
    answerGroup.id = "answ".concat(contId);
    answerGroup.className = "answer-group";
    answerGroup.appendChild(answerDeleteButton);
    answerGroup.appendChild(answerDiv);
    answerGroup.appendChild(answerCheckBoxDiv);
    answerGroup.appendChild(hr);

    var answerContainer = document.getElementById("answer-container".concat(contId));
    answerContainer.appendChild(answerGroup);
}


function generateNextQuestionId() {
    console.log("next question id =", questionId);
    return questionId++;
}

function generateNextAnswerId() {
    console.log("next answer id =", answerId);
    return answerId++;
}

function getNextAnswerId() {
    return "answer-container".concat(generateNextAnswerId())
}

function getCurrentAnswerId() {
    return answerId;
}


function deleteQuestion(id) {
    var currentQuestion = document.getElementById("question".concat(id));
    currentQuestion.parentNode.removeChild(currentQuestion);
}

function deleteAnswer(id) {
    var currentAnswer = document.getElementById("answ".concat(id));
    currentAnswer.parentNode.removeChild(currentAnswer);
}



function biuldTest() {
    var test = {};
    test.name = $("#testNameInput").val();
    test.type = $("#testTheme").val();
    test.quest = [];
    var questions = $(".question");

    console.log(questions);

    Array.from(questions).forEach(function (question) {
        var newQuestion = {};

        console.log(question.getElementsByClassName("question-text")[0].value);

        newQuestion.Qtext = question.getElementsByClassName("question-text")[0].value;
        newQuestion.answers = [];
        var answerGroups = question.getElementsByClassName("answer-container")[0].getElementsByClassName("answer-group");
        Array.from(answerGroups).forEach(function (answer) {
            var newAnswer = {};
            newAnswer.Atext = answer.getElementsByClassName("answer-text")[0].value;
            newAnswer.isRight = answer.getElementsByClassName("form-check-input")[0].checked;
            newQuestion.answers.push(newAnswer);
        });
        test.quest.push(newQuestion);
    });

    console.log(test);

    if ($("#edit").val() != 1) {
        $.redirect("/addTest", {test: JSON.stringify(test)});
    }
    else {
        $.redirect("/editor", {test: JSON.stringify(test)});
    }
}

