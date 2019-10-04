function loginGuest() {

}

function login() {
    window.location.href = "/login";
}

function join() {
    window.location.href = "/members/join";
}

function checkValidPassword(event) {
    var parent = event.target.parentElement.parentElement;
    var typed = parent.querySelector("[name=password-original]");
    var reTyped = parent.querySelector("[name=password-confirmed]");

    if (typed.value != reTyped.value) {

        console.log(parent.querySelector("[name=isValid]"));
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
            if (data) {
                event.target.parentElement.querySelector("[name=isValid]").style.cssText = "display:";
                event.target.parentElement.querySelector("[name=isNotValid]").style.cssText = "display: none";
            } else {
                event.target.parentElement.querySelector("[name=isValid]").style.cssText = "display: none";
                event.target.parentElement.querySelector("[name=isNotValid]").style.cssText = "display:";
            }
        });
    }, 1000)
}