import React from "react";
import UserService from "../services/UserService";
import {Redirect} from "react-router-dom";
import ReCAPTCHA from "react-google-recaptcha";
import "../styles/genericStyles.css";
import "../styles/add-usersStyles.css";

class RegistrationPage extends React.Component {
    constructor(props) {
        super(props);

        if (this.props.location.redirect === "admin") {
            UserService.getRoles().then((response) => {
                this.setState({roles: response});
            });
        }

        this.state = {
            roles: [],
            login: "",
            password: "",
            secondpassword: "",
            email: "",
            firstname: "",
            lastname: "",
            birthday: "",
            roleEntity: {
                idRole: '2',
                name: 'User',
            },
            adminpage: false,
            loginpage: false,
            error: "",
            isVerified: false,
        }

        this.handleOnChangeForm = this.handleOnChangeForm.bind(this);
        this.handleOnSubmitForm = this.handleOnSubmitForm.bind(this);
        this.handleOnLogIn = this.handleOnLogIn.bind(this);
        this.onChangeReCaptchaVerified = this.onChangeReCaptchaVerified.bind(this);
        this.onChangeExpired = this.onChangeExpired.bind(this);
    }

    handleOnChangeForm(event) {
        const {name, value} = event.target;
        this.setState({[name]: value});
    }

    onChangeReCaptchaVerified() {
        this.setState({isVerified: true});
    }

    onChangeExpired() {
        this.setState({isVerified: false});
    }

    handleOnSubmitForm(event) {
        event.preventDefault();

        if (this.props.location.redirect === "admin") {
            UserService.findByNameRole(this.getSetRoleValue()).then(response => {
                this.setState({roleEntity: response.data});
            });
        }

        const json = JSON.stringify({
            "login": this.state.login,
            "password": this.state.password,
            "email": this.state.email,
            "firstName": this.state.firstname,
            "lastName": this.state.lastname,
            "birthday": this.state.birthday,
            "roleEntity": this.state.roleEntity
        });

        if (this.state.password === this.state.secondpassword && this.state.password !== "") {
            this.setState({error: ""});
            UserService.postAddNewUser(json).then(resp => {
                if (resp.error != null) {
                    let obj = resp.error;
                    for (let key in obj) {
                        if (obj.hasOwnProperty(key)) {
                            this.setState({error: `${key} : ${obj[key]}`});
                        }
                    }
                } else {
                    this.handleOnLogIn();
                }
                return resp;
            });
        } else {
            this.setState({error: "2 passwords must be equals, and not null"});
        }
    }

    handleOnLogIn() {
        if (this.props.location.redirect === "admin") {
            this.setState({adminpage: true});
        } else {
            this.setState({loginpage: true})
        }
    }

    setRole() {
        if (this.props.location.redirect === "admin") {
            return <label className='input-line full-width'> Role:
                <select className="select-type5" id={"droplist"}
                        onChange={this.handleOnChangeDropList}>{
                    this.state.roles.map(role => <option key={role.idRole}>{role.name}</option>
                    )}
                </select>
            </label>
        }
    }

    setCaptcha() {
        if (this.props.location.redirect !== "admin") {
            return <ReCAPTCHA id="captcha"
                              sitekey="6Lf9MSgfAAAAAANIcPodCjSnBKUi8E2ZGerDhS7p"
                              onChange={this.onChangeReCaptchaVerified}
                              onExpired={this.onChangeExpired}
            />
        }
    }

    okButton() {
        if (this.props.location.redirect === "admin") {
            return <input type="submit"
                          className="ghost-round full-width"
                          value="Add"/>
        } else {
            return <input type="submit"
                          className="ghost-round full-width"
                          value="Register" disabled={!this.state.isVerified}/>
        }
    }

    getSetRoleValue() {
        return document.getElementById("droplist").value;
    }


    messageError() {
        if (this.state.error !== "") {
            return <p className="messageError">{this.state.error}</p>
        }
    }


    render() {
        if (this.state.adminpage) {
            return <Redirect to={"/admin-page"}/>
        }
        if (this.state.loginpage) {
            return <Redirect to={'/'}/>
        }
        return (
            <div className='container'>
                <div className='windowAddNewUser'>
                    <div className='content'>
                        <h2 className='welcome'>{this.props.location.nameheader}</h2>
                        <form onSubmit={this.handleOnSubmitForm}>
                            {this.messageError()}
                            <div>
                                <div>
                                    <label><h4>Login</h4>
                                        <input
                                            type="text"
                                            className="input-line full-width"
                                            name="login"
                                            value={this.state.login}
                                            onChange={this.handleOnChangeForm}
                                        />
                                    </label>
                                </div>
                                <div>
                                    <label htmlFor={"password"}>
                                        <h4>Password</h4>
                                        <input
                                            type="password"
                                            className="input-line full-width"
                                            name="password"
                                            value={this.state.password}
                                            onChange={this.handleOnChangeForm}
                                        />
                                    </label>
                                </div>
                                <div>
                                    <label htmlFor={"password"}>
                                        <h4>Password again</h4>
                                        <input
                                            type="password"
                                            className="input-line full-width"
                                            name="secondpassword"
                                            value={this.state.secondpassword}
                                            onChange={this.handleOnChangeForm}
                                        />
                                    </label>
                                </div>
                                <div>
                                    <label htmlFor={"password"}>
                                        <h4>Email</h4>
                                        <input
                                            type="email"
                                            className="input-line full-width"
                                            name="email"
                                            value={this.state.email}
                                            onChange={this.handleOnChangeForm}
                                        />
                                    </label>
                                </div>
                                <div>
                                    <label htmlFor={"password"}>
                                        <h4>First Name</h4>
                                        <input
                                            type="text"
                                            className="input-line full-width"
                                            name="firstname"
                                            value={this.state.firstname}
                                            onChange={this.handleOnChangeForm}
                                        />
                                    </label>
                                </div>
                                <div>
                                    <label htmlFor={"password"}>
                                        <h4>Last Name</h4>
                                        <input
                                            type="text"
                                            className="input-line full-width"
                                            name="lastname"
                                            value={this.state.lastname}
                                            onChange={this.handleOnChangeForm}
                                        />
                                    </label>
                                </div>
                                <div>
                                    <label htmlFor={"password"}>
                                        <h4>Birthday</h4>
                                        <input
                                            type="date"
                                            className="input-line full-width"
                                            name="birthday"
                                            value={this.state.birthday}
                                            onChange={this.handleOnChangeForm}
                                        />
                                    </label>
                                </div>
                                {this.setRole()}
                                {this.setCaptcha()}
                                {this.okButton()}
                                <button id="button"
                                        className="ghost-round full-width"
                                        onClick={this.handleOnLogIn} type="button">Back
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default RegistrationPage;