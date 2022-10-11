import React from "react";
import UserService from "../../services/UserService";
import {Redirect} from "react-router-dom";
import "../../styles/genericStyles.css";
import "../../styles/user-pageStyles.css";

class UserPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            redirect: false,
        }
        this.handleOnClickLogout = this.handleOnClickLogout.bind(this);
    }

    handleOnClickLogout() {
        UserService.logout();
        this.setState({redirect: true})
    }

    render() {
        if (this.state.redirect) {
            return <Redirect to={'/'}/>
        }
        return (
            <body>
            <div className='container'>
                <div className='windowUserPage'>
                    <div className='center'>

                    <h1 >Hello, {JSON.parse(localStorage.getItem("user")).firstName}</h1>
                    <p  >Click <button onClick={this.handleOnClickLogout}>here</button> to logout</p>
                    </div>
                </div>
            </div>
            </body>
        );
    }
}

export default UserPage;