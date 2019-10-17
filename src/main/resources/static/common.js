window.onload = function () {
    var boardNavigation = document.querySelector("[name=board-navigation]");
    console.log("boardNavigation : " + boardNavigation);
    if (boardNavigation != null) {
        highlightCurrentPageNavigationItem();
    }
}

function highlightCurrentPageNavigationItem() {
    var url = new URL(window.location.href);
    var currentPage = url.searchParams.get("page")
    var paginationItems = document.querySelectorAll("[name=pagination-item]");
    for (var index = 0; index < paginationItems.length; index++) {
        if (paginationItems[index].text == currentPage) {
            paginationItems[index].classList.add("is-current");
        }
    }
}

function loginGuest() {
    event.preventDefault();

    $.ajax({
        type: 'POST',
        url: '/login/guest'
    }).done(function () {
        window.location.href = "/main?page=1";
    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert(jqXHR.responseText);
    });
}

function logout() {
    $.ajax({
        type: 'GET',
        url: '/logout'
    }).done(function () {
        window.location.href = "/";
    });
}

function login() {
    event.preventDefault();

    $.ajax({
        type: 'POST',
        data: $('#loginForm').serialize(),
        contentType: 'application/x-www-form-urlencoded',
        url: '/login'
    }).done(function () {
        window.location.href = "/main?page=1";
    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert(jqXHR.responseText);
    });
}

function join() {
    var form = document.querySelector("#join-form");
    var formData = {
        name: form.querySelector("[name=name]").value,
        email: form.querySelector("[name=email]").value,
        password: form.querySelector("[name=password-original]").value,
        passwordConfirmed: form.querySelector("[name=password-confirmed]").value
    };

    console.log(formData);

    $.ajax({
        type: 'POST',
        data: JSON.stringify(formData),
        contentType: 'application/json',
        url: '/members/join'
    }).done(function (data) {
        alert("회원가입 완료되었습니다");
        window.location.href = "/";
    });
}

function checkValidPassword(event) {
    var parent = event.target.parentElement.parentElement;
    var typed = parent.querySelector("[name=password-original]");
    var reTyped = parent.querySelector("[name=password-confirmed]");

    if (typed.value != reTyped.value) {
        parent.querySelector("[name=isValid]").style.cssText = "display: none";
        parent.querySelector("[name=isNotValid]").style.cssText = "display:";
    } else {
        parent.querySelector("[name=isValid]").style.cssText = "display:";
        parent.querySelector("[name=isNotValid]").style.cssText = "display: none";
    }
}

var timer = null;

function checkValidEmail(event) {
    var email = event.target.value;
    clearTimeout(timer);
    timer = setTimeout(function () {
        $.ajax({
            type: 'GET',
            data: {
                email: email
            },
            url: '/members/valid/email',
        }).done(function (data) {
            if (data && email != "") {
                event.target.parentElement.querySelector("[name=isValid]").style.cssText = "display:";
                event.target.parentElement.querySelector("[name=isNotValid]").style.cssText = "display: none";
            } else {
                event.target.parentElement.querySelector("[name=isValid]").style.cssText = "display: none";
                event.target.parentElement.querySelector("[name=isNotValid]").style.cssText = "display:";
            }
        });
    }, 1000)
}

function deleteArticle() {
    var checkBoxElements = document.querySelectorAll("[name=checkbox]");
    var checkedElements = [];
    checkBoxElements.forEach(function (element) {
        if(element.checked) {
            var articleIdElement = element.parentElement.nextElementSibling;
            checkedElements.push(parseInt(articleIdElement.innerHTML));
        }
    })
    if (checkedElements.length === 0) {
        return alert("삭제할 아티클을 선택해주세요");
    }

    $.ajax({
        type: 'DELETE',
        data: JSON.stringify(checkedElements),
        contentType: 'application/json',
        url: '/articles'
    }).done(function (data, textStatus, xhr) {
        alert("삭제되었습니다");
        window.location.href = xhr.getResponseHeader("Location");
    });
}