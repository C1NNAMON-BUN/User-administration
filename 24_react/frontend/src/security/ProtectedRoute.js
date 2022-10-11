import React from "react";
import authService from "../services/AuthService";
import {Route} from "react-router-dom";
import {Redirect} from "react-router";

export const ProtectedRoute = ({
                                   path,
                                   component: Component,
                                   render,
                                   ...rest
                               }) => {
    return (
        <Route
            path={path}
            {...rest}
            render={props => {
                if (authService.checkValidToken()) {
                    return Component ? <Component {...props} /> : render(props);
                } else {
                    return <Redirect to="/"/>;
                }
            }}
        />
    );
};

export default ProtectedRoute;