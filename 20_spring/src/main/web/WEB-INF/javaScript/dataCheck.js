var logCheck = function () {
    if (document.getElementById("login").value === "") {
        document.getElementById('messageLogin').style.color = 'red';
        document.getElementById('messageLogin').innerHTML = 'Fill in the empty field!';
    } else if (!(document.getElementById("login").value.match(/^[a-z0-9_-]{4,20}$/))) {
        document.getElementById('messageLogin').style.color = 'red';
        document.getElementById('messageLogin').innerHTML = 'login invalid';
    } else {
        document.getElementById('messageLogin').innerHTML = '';
    }
};

var eCheck = function () {
    if (document.getElementById("email").value === "") {
        document.getElementById('messageEmail').style.color = 'red';
        document.getElementById('messageEmail').innerHTML = 'Fill in the empty field!';
    } else if (!(document.getElementById("email").value.match(/^[\w\.=-]+@[\w\.-]+\.[\w]{2,3}$/))) {
        document.getElementById('messageEmail').style.color = 'red';
        document.getElementById('messageEmail').innerHTML = 'email invalid';
    } else {
        document.getElementById('messageEmail').innerHTML = '';
    }
};

var fNameCheck = function () {
    if (document.getElementById("firstName").value === "") {
        document.getElementById('messageFname').style.color = 'red';
        document.getElementById('messageFname').innerHTML = 'Fill in the empty field!';
    } else if (!(document.getElementById("firstName").value.match(/[a-zA-Z]{2,20}/))) {
        document.getElementById('messageFname').style.color = 'red';
        document.getElementById('messageFname').innerHTML = 'first name invalid';
    } else {
        document.getElementById('messageFname').innerHTML = '';
    }
};

var lNameCheck = function () {
    if (document.getElementById("lastName").value === "") {
        document.getElementById('messageLname').style.color = 'red';
        document.getElementById('messageLname').innerHTML = 'Fill in the empty field!';
    } else if (!(document.getElementById("lastName").value.match(/[a-zA-Z]{2,20}/))) {
        document.getElementById('messageLname').style.color = 'red';
        document.getElementById('messageLname').innerHTML = 'last name invalid';
    } else {
        document.getElementById('messageLname').innerHTML = '';
    }
};

var check = function () {
    if (document.getElementById("password").value === ""
        && document.getElementById("password2").value === "") {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerHTML = 'Fill in the empty field!';
    } else if (document.getElementById('password').value.match(/[a-zA-Z]{4,20}/)) {
        if (document.getElementById('password').value ===
            document.getElementById('password2').value) {
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