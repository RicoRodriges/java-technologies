function changeLanguage() {
    var language = document.getElementById("lang").className.substr(5, 2);
    language = language === "en" ? "ru" : "en";
    var url = document.URL;
    if (!url.includes("?"))
        document.location.href = document.URL + "?language=" + language;
    else if (url.match("language=")) {
        document.location.href = url.replace(/language=../, "language=" + language);
    } else
        document.location.href = url + "&language=" + language;
}