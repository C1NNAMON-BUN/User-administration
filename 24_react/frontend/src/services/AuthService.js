import jwtDecode from "jwt-decode";

class authService {
    checkValidToken() {
        try {
            const token = localStorage.getItem('token');
            let decodedToken = jwtDecode(token);
            let currentDate = new Date();

            return decodedToken.exp * 1000 >= currentDate.getTime();
        } catch (e) {
            return false;
        }
    };
}

export default new authService();