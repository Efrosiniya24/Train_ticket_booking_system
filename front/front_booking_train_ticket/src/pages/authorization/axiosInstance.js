import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080', 
    withCredentials: true, 
});

axiosInstance.interceptors.request.use(config => {
    const token = document.querySelector('meta[name="csrf-token"]')?.getAttribute('content');
    if (token) {
        config.headers['X-CSRFToken'] = token;
    }
    return config;
});

export default axiosInstance;
