import React from "react";
import UserService from "../../services/UserService";
import {Redirect} from "react-router-dom";
import "../../styles/genericStyles.css";
import "../../styles/edit-usersStyles.css";

class RegistrationPage extends React.Component {
    constructor(props) {
        super(props);
        let updateuser = JSON.parse(localStorage.getItem("updateuser"));
        let date = new Date(updateuser.birthday);

        UserService.getRoles().then((response) => {
            this.setState({roles: response});
        });

        this.state = {
            roles: [],
            id: updateuser.id,
            login: updateuser.login,
            password: updateuser.password,
            secondpassword: updateuser.password,
            email: updateuser.email,
            firstname: updateuser.firstName,
            lastname: updateuser.lastName,
            birthday: date.toLocaleDateString('en-CA'),
            roleEntity: {
                idRole: "1",
                name: "Admin",
            },
            redirectback: false,
            loginpage: false,
            error: "",
        }
        this.handleOnChangeForm = this.handleOnChangeForm.bind(this);
        this.handleOnSubmitForm = this.handleOnSubmitForm.bind(this);
        this.handleOnBackClick = this.handleOnBackClick.bind(this);
        this.handleOnChangeDropList = this.handleOnChangeDropList.bind(this);
    }

    handleOnChangeForm(event) {
        const {name, value} = event.target;
        this.setState({[name]: value});
    }

    handleOnSubmitForm(event) {
        event.preventDefault();
        const json = JSON.stringify({
            "id": this.state.id,
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
            UserService.putUpdateUser(json).then(response => {
                if (response.error != null) {
                    let obj = response.error;
                    for (let key in obj) {
                        if (obj.hasOwnProperty(key)) {
                            this.setState({error: `${key} : ${obj[key]}`});
                        }
                    }
                } else {
                    this.handleOnBackClick();
                    localStorage.removeItem("updateuser");
                }
                return response;
            });
        } else {
            this.setState({error: "2 passwords must be equals, and not null"});
        }
    }

    handleOnBackClick() {
        this.setState({redirectback: true})
    }

    handleOnChangeDropList() {
        UserService.findByNameRole(this.getSetRoleDropList()).then(response => {
            this.setState({roleEntity: response.data});
        });
    }

    getSetRoleDropList() {
        return document.getElementById("droplist").value;
    }

    messageError() {
        if (this.state.error !== "") {
            return  <p className="messageError">{this.state.error}</p>
        }
    }

    render() {
        if (this.state.redirectback) {
            return <Redirect to={'/admin-page'}/>
        }
        return (
            <div className='container'>
                <div className='windowUpdateUser'>
                    <div className='content'>
                        <h3 className='welcome'>Update User</h3>
                        {this.messageError() }
                        <form onSubmit={this.handleOnSubmitForm}>
                            <div>
                                <label><h4>Login</h4>
                                    <input
                                        type="text"
                                        name="login"
                                        className="input-line full-width"
                                        value={this.state.login}
                                        onChange={this.handleOnChangeForm}
                                        readOnly
                                    />
                                </label>
                            </div>
                            <div>
                                <label>
                                    <h4>Password</h4>
                                    <input
                                        type="password"
                                        name="password"
                                        className="input-line full-width"
                                        value={this.state.password}
                                        onChange={this.handleOnChangeForm}
                                    />
                                </label>
                            </div>
                            <div>
                                <label>
                                    <h4>Password again</h4>
                                    <input
                                        type="password"
                                        name="secondpassword"
                                        className="input-line full-width"
                                        value={this.state.secondpassword}
                                        onChange={this.handleOnChangeForm}
                                    />
                                </label>
                            </div>
                            <div>
                                <label>
                                    <h4>Email</h4>
                                    <input
                                        type="email"
                                        name="email"
                                        className="input-line full-width"
                                        value={this.state.email}
                                        onChange={this.handleOnChangeForm}
                                    />
                                </label>
                            </div>
                            <div>
                                <label>
                                    <h4>First Name</h4>
                                    <input
                                        type="text"
                                        name="firstname"
                                        className="input-line full-width"
                                        value={this.state.firstname}
                                        onChange={this.handleOnChangeForm}
                                    />
                                </label>
                            </div>
                            <div>
                                <label>
                                    <h4>Last Name</h4>
                                    <input
                                        type="text"
                                        name="lastname"
                                        className="input-line full-width"
                                        value={this.state.lastname}
                                        onChange={this.handleOnChangeForm}
                                    />
                                </label>
                            </div>
                            <div>
                                <label>
                                    <h4>Birthday</h4>
                                    <input
                                        type="date"
                                        name="birthday"
                                        className="input-line full-width"
                                        value={this.state.birthday}
                                        onChange={this.handleOnChangeForm}
                                    />
                                </label>
                            </div>
                            <br/>
                            <label className='input-line full-width'> Role:
                                <select className="select-type5" id={"droplist"}
                                        onChange={this.handleOnChangeDropList}>{
                                    this.state.roles.map(role => <option key={role.idRole}>{role.name}</option>
                                    )}
                                </select>
                            </label>

                            <br/>
                            <br/>
                            <br/>

                            <input type="submit"
                                   className="ghost-round full-width"
                                   value="OK"/>
                        </form>
                        <button id="cancel"
                                className="ghost-round full-width"
                                onClick={this.handleOnBackClick}>Cancel
                        </button>
                    </div>
                </div>
            </div>
        );
    }
}

export default RegistrationPage;