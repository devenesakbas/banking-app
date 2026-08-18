import axios from "axios";
import { authService } from "./authServices/authService";
import type { RefreshTokenRequest, RefreshTokenResponse } from "../types/auth";
import type { ApiResponse } from "../types/ApiResponse";

const BASE_URI = import.meta.env.VITE_API_BASE_URI;

const API_TIMEOUT = import.meta.env.VITE_API_TIMEOUT;

const api = axios.create({
    baseURL: BASE_URI,
    timeout: API_TIMEOUT ? parseInt(API_TIMEOUT) : 10000,
    headers: {
        "Content-Type": "application/json",
    },
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem("accessToken");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});


api.interceptors.response.use((response) => {
    return response.data;
}, async (error) => {

    const orginalRequest = error.config;

    if (error.response?.status === 401 && !orginalRequest._retry) {
        orginalRequest._retry = true;

        try {

            const refreshToken = localStorage.getItem("refreshToken");

            const data: RefreshTokenRequest = {
                refreshToken: refreshToken!
            };

            const result: ApiResponse<RefreshTokenResponse> = await authService.refresh(data);

            const { accessToken } = result.data;

            localStorage.setItem("accessToken", accessToken);

            orginalRequest.headers.Authorization = `Bearer ${accessToken}`;
            return api(orginalRequest);

        }
        catch (refreshError) {
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            window.location.href = "/login";
            return Promise.reject(error);
        }

    }
    return Promise.reject(error);
});

export default api;