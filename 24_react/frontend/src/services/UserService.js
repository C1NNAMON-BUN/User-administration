import axios from "axios";
import jwtDecode from "jwt-decode";

const REST_API_URL = "http://localhost:8080/";

class UserService {
    login(jsonCredentials) {
        return axios
            .post(REST_API_URL + "login", jsonCredentials, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
    }

    logout() {
        localStorage.removeItem("user");
        localStorage.removeItem("token");
    }

    getCurrentUserRole() {
        return jwtDecode(localStorage.getItem('token')).roles;
    }

    getUsers() {
        console.dir(localStorage.getItem("token"))
        return axios
            .get(REST_API_URL + "users", {
                headers: {
                    'Token': "Bearer_" + localStorage.getItem("token"),
                }
            })
            .then(response => {
                return response.data;
            });
    }

    getRoles() {
        console.dir(localStorage.getItem("token"))
        return axios
            .get(REST_API_URL + "roles", {
                headers: {
                    'Token': "Bearer_" + localStorage.getItem("token"),
                }
            })
            .then(response => {
                console.log(response.data);
                return response.data;
            });
    }

    postAddNewUser(jsonObject) {
        return axios
            .post(REST_API_URL + "registration", jsonObject, {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                return response.data;
            });
    }

    putUpdateUser(jsonObject) {
        return axios
            .put(REST_API_URL + "user", jsonObject, {
                headers: {
                    'Token': "Bearer_" + localStorage.getItem("token"),
                    'Content-Type': 'application/json',
                }
            })
            .then(response => {
                return response.data;
            });
    }

    deleteUser(id) {
        return axios
            .delete(REST_API_URL + "user", {
                headers: {
                    'Token': "Bearer_" + localStorage.getItem("token"),
                },
                params: {
                    id: id,
                }
            })
            .then(response => {
                return response.data;
            });
    }

    findByIdUser(id) {
        return axios
            .get(REST_API_URL + "user", {
                headers: {
                    'Token': "Bearer_" + localStorage.getItem("token"),
                },
                params: {
                    id: id,
                }
            })
            .then(response => {
                return response.data;
            });
    }

    findByNameRole(name) {
        return axios
            .get(REST_API_URL + "role", {
                headers: {
                    'Token': "Bearer_" + localStorage.getItem("token"),
                },
                params: {
                    name: name,
                }
            });
    }
}

export default new UserService();