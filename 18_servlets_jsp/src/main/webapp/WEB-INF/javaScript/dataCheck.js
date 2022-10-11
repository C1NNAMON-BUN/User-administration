var logCheck = function () {
    if (!(document.getElementById("login").value.match(/^[a-z0-9_-]{4,20}$/))) {
        document.getElementById('messageLogin').style.color = 'red';
        document.getElementById('messageLogin').innerHTML = 'login invalid';
    } else {
        document.getElementById('messageLogin').innerHTML = '';
    }
};

var eCheck = function () {
    if (!(document.getElementById("email").value.match(/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/))) {
        document.getElementById('messageEmail').style.color = 'red';
        document.getElementById('messageEmail').innerHTML = 'email invalid';
    } else {
        document.getElementById('messageEmail').innerHTML = '';
    }
};

var fNameCheck = function () {
    if (!(document.getElementById("firstName").value.match(/[a-zA-Z]{2,20}/))) {
        document.getElementById('messageFname').style.color = 'red';
        document.getElementById('messageFname').innerHTML = 'first name invalid';
    } else {
        document.getElementById('messageFname').innerHTML = '';
    }
};

var lNameCheck = function () {
    if (!(document.getElementById("lastName").value.match(/[a-zA-Z]{2,20}/))) {
        document.getElementById('messageLname').style.color = 'red';
        document.getElementById('messageLname').innerHTML = 'last name invalid';
    } else {
        document.getElementById('messageLname').innerHTML = '';
    }
};

var check = function () {
    if (document.getElementById('password').value.match(/[a-zA-Z]{4,20}/)) {
        if (document.getElementById('password').value ===
            document.getElementById('confirmPassword').value) {
            document.getElementById('message').style.color = 'green';
            document.getElementById('message').innerHTML = 'password confirmed';
        } else {
            document.getElementById('message').style.color = 'red';
            document.getElementById('message').innerHTML = 'password mismatch';
        }
    } else {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerHTML = 'password invalid';
    }
};