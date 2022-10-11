import React, {Component} from "react";
import {Redirect} from "react-router-dom";
import UserService from "../services/UserService";

class LogoutPage extends Component {
    render() {
        UserService.logout();
        return <Redirect to={'/'}/>
    }
}

export default LogoutPage;