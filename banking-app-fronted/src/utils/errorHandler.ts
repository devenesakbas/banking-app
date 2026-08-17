import type { ApiResponse } from "../types/ApiResponse";

export const getLocalizedErrorMessage = (errorResponse?: ApiResponse<any>): string => {
    if (errorResponse?.errorCode === "VALIDATION_ERROR" && errorResponse?.message) {
        return errorResponse.message;
    }

    const errorMessages: Record<string, string> = {
        "EMAIL_ALREADY_EXIST": "Bu email adresi daha önceden alınmış.",
        "INVALID_CREDENTIALS": "E-posta veya şifre hatalı.",
        "TOKEN_EXPIRED": "Oturum süreniz doldu, lütfen tekrar giriş yapın.",
        "TOKEN_INVALID": "Geçersiz oturum.",
        "UNAUTHORIZED": "Bu işlem için giriş yapmanız gerekiyor.",
        "FORBIDDEN": "Bu alana erişim yetkiniz yok.",
        "NOT_FOUND": "Aradığınız kayıt bulunamadı.",
        "INTERNAL_SERVER_ERROR": "Sunucu hatası oluştu, lütfen daha sonra tekrar deneyin.",
        "BAD_REQUEST": "Geçersiz istek.",
        "CONFLICT": "Çakışma.",
        "PRECONDITION_FAILED": "Önkoşul başarısız.",
        "SERVICE_UNAVAILABLE": "Hizmet kullanılamıyor.",
        "GATEWAY_TIMEOUT": "Geçit zaman aşımı.",
        "UNKNOWN_ERROR": "Bilinmeyen hata.",
        "PASSWORD_MISMATCH": "Şifreler uyuşmuyor.",
        "USER_NOT_FOUND": "Sistemimizde böyle bir kullanıcıyı bulamadık.",
        "INVALID_RESET_CODE": "Geçersiz sıfırlama kodu."
    };

    if (errorResponse?.errorCode && errorMessages[errorResponse.errorCode]) {
        return errorMessages[errorResponse.errorCode];
    }

    return errorResponse?.message || "Beklenmeyen bir hata oluştu.";
};