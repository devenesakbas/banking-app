export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
  expiresIn: number;
}

export interface RegisterRequest {
  name: string;
  surname: string;
  email: string;
  password: string;
}

export interface RegisterResponse {
  id: number;
  name: string;
  surname: string;
  email: string;
  createdAt: string;
}

export interface RefreshTokenResponse {
  accessToken: string;
  refreshToken: string;
}

export interface RefreshTokenRequest {
  refreshToken: string;
}

export interface MeResponse {
  name: string;
  surname: string;
  email: string;
  role: string;
}

export interface ForgotPasswordRequest {
  email: string;
}

export interface ForgotPasswordResponse {
  send: boolean;
}

export interface verifyResetCodeRequest {
  email: string;
  code: string;
}

export interface verifyResetCodeResponse {
  valid: boolean;
}

export interface resetPasswordRequest {
  email: string,
  password: string 
  passwordConfirm: string
}

export interface resetPasswordResponse {
  reset: boolean;
}