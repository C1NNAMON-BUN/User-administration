import React, {Component} from "react";
import {Redirect} from "react-router-dom";
import UserService from "../../services/UserService";
import "../../styles/genericStyles.css";
import "../../styles/adminPageStyles .css";

class adminPage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            redirect: false,
            redirectlogout: false,
            redirectAdd: false,
            users: [],
        }

        this.handleOnClickLogout = this.handleOnClickLogout.bind(this);
        this.handleOnClickAddNewUser = this.handleOnClickAddNewUser.bind(this);
    }

    componentDidMount() {
        UserService.getUsers().then((response) => {
            this.setState({users: response});
        })

        this.handleOnClickEdit = this.handleOnClickEdit.bind(this);
    }


    handleOnClickEdit(button) {
        UserService.findByIdUser(button.target.value).then(resp => {
            localStorage.setItem("updateuser", JSON.stringify(resp));
            this.setState({redirect: true});
        });
    }

    handleOnClickDelete(button) {
        if (window.confirm("Are you sure, you want to delete this user?")) {
            UserService.deleteUser(button.target.value);
            window.location.reload();
        }
    }

    handleOnClickAddNewUser(event) {
        event.preventDefault();
        this.setState({redirectAdd: true})
    }

    handleOnClickLogout(event) {
        event.preventDefault();
        UserService.logout();
        this.setState({redirectLogout: true})
    }

    _calculateAge(birthday) {
        var ageDifMs;
        var ageDifMs = Date.now() - birthday.getTime();
        var ageDate = new Date(ageDifMs);
        return Math.abs(ageDate.getUTCFullYear() - 1970);
    }

    checkIsAdmin() {
        return UserService.getCurrentUserRole() === "Admin";
    }

    deleteUser(user) {
        if (user.id !==1) {
            return <button className='ghost-round full-width'  onClick={this.handleOnClickDelete} value={user.id}>Delete</button>
        } else {

        }
    }

    render() {
        if (this.state.redirectLogout) {
            return <Redirect to={"/"}/>
        }
        if (!this.checkIsAdmin()) {
            return <Redirect to={"/user-page"}/>
        } else if (this.state.redirect) {
            return <Redirect to={"/user-update"}/>
        } else if (this.state.redirectAdd) {
            return <Redirect to={{
                pathname: "/registration",
                nameheader: "Add new user",
                redirect: "admin",
            }}/>
        }

        return (
            <div id="admin-page">

                <h3 align="right">Hello, {JSON.parse(localStorage.getItem('user')).firstName}
                <a  className='ghost-round full-width' onClick={this.handleOnClickLogout}>    Logout </a>
                </h3>
                <a  className='ghost-round full-width position' onClick={this.handleOnClickAddNewUser}>Add new user </a>

                <div>
                    <table id="cells">
                        <thead>
                        <tr>
                            <td id="outer">Login</td>
                            <td>First Name</td>
                            <td>Last Name</td>
                            <td>Age</td>
                            <td>Role</td>
                            <td>Actions</td>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.users.map(user => <tr key={user.id}>
                                <td>{user.login}</td>
                                <td>{user.firstName}</td>
                                <td>{user.lastName}</td>
                                <td>{this._calculateAge(new Date(user.birthday)) + " years"}</td>
                                <td>{user.roleEntity.name}</td>
                                <td>
                                    <button className='ghost-round full-width'  onClick={this.handleOnClickEdit} value={user.id}>Edit</button>
                                    {this.deleteUser(user)}
                                </td>
                            </tr>)
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default adminPage;