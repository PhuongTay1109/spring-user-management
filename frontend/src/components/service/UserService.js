import axios from "axios";
import AxiosInstance from "./AxiosInstance";

class UserService {
    static BASE_URL = "http://localhost:8080"

    static async login(email, password) {
        try {
            const response = await axios.post(`${UserService.BASE_URL}/auth/login`, { email, password })
            return response.data;
        } catch (err) {
            throw err;
        }
    }

    static async register(userData, token) {
        try {
            const response = await axios.post(`${UserService.BASE_URL}/auth/register`, userData,
                {
                    headers: { Authorization: `Bearer ${token}` }
                })
            return response.data;
        } catch (err) {
            throw err;
        }
    }

    static async getAllUsers(token, refreshToken) {
        try {
            const response = await AxiosInstance.get(`${UserService.BASE_URL}/admin/get-all-users`,
                {
                    headers: { Authorization: `Bearer ${token}`,  'Authorization-Refresh': `Bearer ${refreshToken}`}
                })
            return response.data;
        } catch (err) {
            throw err;
        }
    }


    static async getYourProfile(token, refreshToken) {
        try {
            const response = await AxiosInstance.get(`${UserService.BASE_URL}/adminuser/get-profile`,
                {
                    headers: { Authorization: `Bearer ${token}`, 'Authorization-Refresh': `Bearer ${refreshToken}`}
                })
            console.log(response.data);
            return response.data;
        } catch (err) {
            throw err;
        }
    }

    static async getUserById(userId, token, refreshToken) {
        try {
            const response = await AxiosInstance.get(`${UserService.BASE_URL}/admin/get-user/${userId}`,
                {
                    headers: { Authorization: `Bearer ${token}`, 'Authorization-Refresh': `Bearer ${refreshToken}` }
                })
            return response.data;
        } catch (err) {
            throw err;
        }
    }

    static async deleteUser(userId, token, refreshToken) {
        try {
            const response = await AxiosInstance.delete(`${UserService.BASE_URL}/admin/delete/${userId}`,
                {
                    headers: { Authorization: `Bearer ${token}`, 'Authorization-Refresh': `Bearer ${refreshToken}` }
                })
            return response.data;
        } catch (err) {
            throw err;
        }
    }


    static async updateUser(userId, userData, token, refreshToken) {
        try {
            const response = await AxiosInstance.put(`${UserService.BASE_URL}/admin/update/${userId}`, userData,
                {
                    headers: { Authorization: `Bearer ${token}`, 'Authorization-Refresh': `Bearer ${refreshToken}` }
                })
            return response.data;
        } catch (err) {
            throw err;
        }
    }

    /**AUTHENTICATION CHECKER */
    static logout() {
        localStorage.removeItem('token')
        localStorage.removeItem('role')
    }

    static isAuthenticated() {
        const token = localStorage.getItem('token')
        return !!token
    }

    static isAdmin() {
        const role = localStorage.getItem('role')
        return role === 'ADMIN'
    }

    static isUser() {
        const role = localStorage.getItem('role')
        return role === 'USER'
    }

    static adminOnly() {
        return this.isAuthenticated() && this.isAdmin();
    }

}

export default UserService;