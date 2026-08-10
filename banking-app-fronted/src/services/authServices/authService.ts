import axios from "axios";

const BASE_URL = "http://localhost:8080";

const BASE_TIMEOUT = 10000; // 10 seconds

const apiClient = axios.create({
    baseURL: BASE_URL,
    timeout: BASE_TIMEOUT,
    headers: {
        "Content-Type": "application/json",
    },
});

export const authService = {
    login: (username: string, password: string) => {
        return apiClient.post("auth/login", { username, password });
    },
    
    register: (username: string, password: string) => {
        return apiClient.post("auth/register", { username, password });
    },
    
    getProfile: () => {
        return apiClient.get("auth/profile");
    },
};