import axios from 'axios';

const AxiosInstance = axios.create({
    baseURL: 'http://localhost:3000'
});

// Add a response interceptor
AxiosInstance.interceptors.response.use(
    response => response,
    error => {
        if (error.response && error.response.status === 403) {
                window.location.href = '/login'; // Redirect to login page
        }
        return Promise.reject(error);
    }
);

export default AxiosInstance;
