import api from "../api";
import type {
  LoginRequest,
  LoginResponse,
  RegisterRequest,
  RegisterResponse,
  RefreshTokenRequest,
  RefreshTokenResponse,
  MeResponse,
  ForgotPasswordRequest,
  ForgotPasswordResponse,
  verifyResetCodeRequest,
  verifyResetCodeResponse,
  resetPasswordRequest,
  resetPasswordResponse,
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

  forgotPassword: async (data: ForgotPasswordRequest): Promise<ApiResponse<ForgotPasswordResponse>> => {
    const response: ApiResponse<ForgotPasswordResponse> = await api.post(
      "/auth/forgot-password",
      data
    );

    return response;
  },

  verifyResetCode: async (data: verifyResetCodeRequest): Promise<ApiResponse<verifyResetCodeResponse>> => {
    const response: ApiResponse<verifyResetCodeResponse> = await api.post(
      "/auth/verify-reset-code",
      data
    );

    return response;
  },

  resetPassword: async (data: resetPasswordRequest): Promise<ApiResponse<resetPasswordResponse>> => {
    const response: ApiResponse<resetPasswordResponse> = await api.post(
      "/auth/reset-password",
      data
    );

    return response;
  }
};