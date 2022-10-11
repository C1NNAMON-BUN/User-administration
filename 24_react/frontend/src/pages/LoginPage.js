import React, {Component} from "react";
import UserService from "../services/UserService";
import {Redirect} from "react-router-dom";
import "../styles/genericStyles.css";
import "../styles/indexStyles.css";

class LoginPage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            login: "",
            password: "",
            user: "",
            roles: "",
            redirect: false,
            registration: false,
            error: "",
        }

        this.handleOnChangeForm = this.handleOnChangeForm.bind(this);
        this.handleOnSubmitForm = this.handleOnSubmitForm.bind(this);
        this.handleOnRegistrationClick = this.handleOnRegistrationClick.bind(this);
    }

    handleOnChangeForm(event) {
        const {name, value} = event.target;
        this.setState({[name]: value});
    }

    handleOnSubmitForm(event) {
        event.preventDefault();
        const json = JSON.stringify({
            "login": this.state.login,
            "password": this.state.password
        });

        UserService.login(json).then(response => {
            if (response.data.errors != null) {
                this.setState({error: response.data.errors.error});
            }
            if (response.data.token) {
                localStorage.setItem("user", JSON.stringify(response.data.user));
                localStorage.setItem("token", response.data.token);
                this.setState({roles: JSON.parse(localStorage.getItem("user")).roleEntity.name});
                this.setState({redirect: true});
            }
        });
    }


    messageError() {
        if (this.state.error !== "") {
            return  <p className="messageError">{this.state.error}</p>
        }
    }

    handleOnRegistrationClick() {
        this.setState({registration: true})
    }

    render() {
        if (this.state.registration) {
            return <Redirect to={{
                pathname: "/registration",
                nameheader: "Registration page",
                redirect: "login",
            }}/>
        }
        if (this.state.redirect && this.state.roles === "Admin") {
            return <Redirect to={'/admin-page'}/>
        } else if (this.state.redirect && this.state.roles === "User") {
            return <Redirect to={'/user-page'}/>
        }
        return (
            <div className='container'>
                <div className='windowLogin'>
                    <div className='content'>
                        <form onSubmit={this.handleOnSubmitForm}>
                            <h3 className='welcome'>Hello!</h3>
                            {this.messageError() }
                            <input type="text"
                                   name="login"
                                   value={this.state.login}
                                   onChange={this.handleOnChangeForm}
                                   className='input-line full-width'/>
                            <input type="password"
                                   name="password"
                                   value={this.state.password}
                                   onChange={this.handleOnChangeForm}
                                   className='input-line full-width'/>
                            <br/>
                            <button className='ghost-round full-width' id="button">Sign in</button>

                        </form>
                        <button id="button" className='ghost-round full-width btn'
                                onClick={this.handleOnRegistrationClick}>Register
                        </button>
                    </div>
                </div>
            </div>
        );
    }
}

export default LoginPage;
