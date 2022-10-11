import React from 'react'
import {Switch, Route} from 'react-router-dom'
import LoginPage from "./pages/LoginPage";
import ProtectedRoute from "./security/ProtectedRoute";
import userPage from "./pages/users/UserPage";
import AdminPage from "./pages/admin/AdminPage";
import LogoutPage from "./pages/LogoutPage";
import RegistrationPage from "./pages/RegistrationPage";
import {Redirect} from "react-router";
import UpdatePage from "./pages/admin/UpdatePage";

class App extends React.Component {
    render() {
        return (
            <Switch>
                <ProtectedRoute path='/user-page' component={userPage}/>
                <ProtectedRoute path='/logout' component={() => <LogoutPage/>}/>
                <Route exact path="/">
                    <Redirect to="/login" />
                </Route>
                <Route path='/login' component={() => <LoginPage/>}/>
                <ProtectedRoute path='/admin-page' component={() => <AdminPage/>}/>
                <ProtectedRoute path='/user-update' component={() => <UpdatePage/>}/>
                <Route path='/registration' component={RegistrationPage}/>
            </Switch>
        );
    }
}

export default App;
