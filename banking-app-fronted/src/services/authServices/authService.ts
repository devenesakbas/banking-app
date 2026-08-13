import api from "../api";
import type {
  LoginRequest,
  LoginResponse,
  RegisterRequest,
  RegisterResponse,
  RefreshTokenRequest,
  RefreshTokenResponse,
  MeResponse,
} from "../../types/auth";
import type { ApiResponse } from "../../types/ApiResponse";

export const authService = {
  register: async (data: RegisterRequest): Promise<ApiResponse<RegisterResponse>> => {
    const response: ApiResponse<RegisterResponse> = await api.post(
      "/auth/register",
      data
    );

    return response;
  },

  login: async (data: LoginRequest): Promise<ApiResponse<LoginResponse>> => {
    const response: ApiResponse<LoginResponse> = await api.post(
      "/auth/login",
      data
    );

    return response;
  },

  refresh: async (data: RefreshTokenRequest): Promise<ApiResponse<RefreshTokenResponse>> => {
    const response: ApiResponse<RefreshTokenResponse> = await api.post(
      "/auth/refresh",
      data
    );

    return response;
  },

  me: async (): Promise<ApiResponse<MeResponse>> => {
    const response: ApiResponse<MeResponse> = await api.get(
      "/auth/me"
    );

    return response;
  },
};