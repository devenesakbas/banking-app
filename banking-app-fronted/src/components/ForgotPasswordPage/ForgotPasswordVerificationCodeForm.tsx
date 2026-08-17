import { useRef, useState, useEffect } from "react";
import { RiTimerLine } from "react-icons/ri";
import { authService } from "../../services/authServices/authService";
import type { verifyResetCodeResponse } from "../../types/auth";
import { getLocalizedErrorMessage } from "../../utils/errorHandler";
import { useNavigate } from "react-router-dom";
import { MdOutlineCheckCircle, MdOutlineErrorOutline } from "react-icons/md";

interface ForgotPasswordVerificationCodeFormProps {
  setShowVerificationCodeForm: (show: boolean) => void;
  email: string;
}

export default function ForgotPasswordVerificationCodeForm({
  setShowVerificationCodeForm,
  email,
}: ForgotPasswordVerificationCodeFormProps) {
  const [code, setCode] = useState<string[]>(["", "", "", "", "", ""]);
  const inputsRef = useRef<(HTMLInputElement | null)[]>([]);
  const [verificationCodeExpiresAt, setVerificationCodeExpiresAt] =
    useState<number>(300);
  const [response, setResponse] = useState<{
    type: "success" | "error";
    message: string;
  } | null>(null);

  const navigate = useNavigate();

  const handleChange = (value: string, index: number) => {
    // Sadece rakam kabul et
    if (!/^\d*$/.test(value)) return;

    const newCode = [...code];
    newCode[index] = value.slice(-1);

    setCode(newCode);

    // Rakam girildiyse sonraki inputa geç
    if (value && index < 5) {
      inputsRef.current[index + 1]?.focus();
    }
  };

  const handleKeyDown = (
    e: React.KeyboardEvent<HTMLInputElement>,
    index: number,
  ) => {
    // Backspace ile önceki inputa geç
    if (e.key === "Backspace" && !code[index] && index > 0) {
      inputsRef.current[index - 1]?.focus();
    }
  };

  const handlePaste = (e: React.ClipboardEvent<HTMLInputElement>) => {
    e.preventDefault();

    const pastedCode = e.clipboardData
      .getData("text")
      .replace(/\D/g, "")
      .slice(0, 6);

    if (!pastedCode) return;

    const newCode = pastedCode
      .split("")
      .concat(["", "", "", "", "", ""])
      .slice(0, 6);

    setCode(newCode);

    const nextIndex = Math.min(pastedCode.length, 5);
    inputsRef.current[nextIndex]?.focus();
  };

  const maskEmail = (email: string): string => {
    const [username, domain] = email.split("@");

    if (!username || !domain) {
      return email;
    }

    const visiblePart = username.slice(0, 3);
    const maskedPart = "*".repeat(Math.max(username.length - 3, 0));

    return `${visiblePart}${maskedPart}@${domain}`;
  };

  const handleSubmit = async () => {
    const verifyCode = code.join("");

    try {
      const response = await authService.verifyResetCode({
        email,
        code: verifyCode,
      });

      if (response.data.success) {
        navigate("/reset-password", { state: { email } });
      }
    } catch (error) {
      console.log(error.response.data.errorCode);
      const errorCode = error.response.data;
      const localizedMessage = getLocalizedErrorMessage(errorCode);
      setResponse({
        type: "error",
        message: localizedMessage,
      });
    }
  };

  useEffect(() => {
    const interval = setInterval(() => {
      setVerificationCodeExpiresAt((prev) => {
        if (prev <= 1) {
          clearInterval(interval);
          return 0;
        }

        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(interval);
  }, []);

  return (
    <div className="flex items-center justify-center px-4">
      <div className="w-full max-w-md rounded-2xl bg-white p-4">
        <div className="text-center mb-3">
          <h1 className="text-2xl font-bold text-gray-900">Doğrulama Kodu</h1>

          <p className="mt-2 text-sm text-gray-500">
            {maskEmail(email)} e-posta adresinize gönderdiğimiz 6 haneli kodu
            girin.
          </p>
        </div>
        {response && (
          <div className={`w-full transition-all duration-300`}>
            <div
              className={`p-3 rounded-xl text-sm font-medium border flex items-center gap-2 ${
                response.type === "success"
                  ? "bg-emerald-50 border-emerald-200 text-emerald-700"
                  : "bg-rose-50 border-rose-200 text-rose-700"
              }`}
            >
              {response.type === "success" ? (
                <MdOutlineCheckCircle />
              ) : (
                <MdOutlineErrorOutline />
              )}
              {response.message}
            </div>
          </div>
        )}
        
        <div className="mt-8 flex justify-center gap-2">
          {code.map((digit, index) => (
            <input
              key={index}
              ref={(el) => {
                inputsRef.current[index] = el;
              }}
              type="text"
              inputMode="numeric"
              maxLength={1}
              value={digit}
              onChange={(e) => handleChange(e.target.value, index)}
              onKeyDown={(e) => handleKeyDown(e, index)}
              onPaste={handlePaste}
              className="h-14 w-12 rounded-xl border border-gray-300 bg-gray-50 text-center text-xl font-semibold text-gray-900 outline-none transition focus:border-blue-500 focus:bg-white focus:ring-2 focus:ring-blue-100"
            />
          ))}
        </div>
        <div className="flex flex-row gap-2">
          <button
            onClick={handleSubmit}
            disabled={
              code.join("").length !== 6 || verificationCodeExpiresAt <= 0
            }
            className="mt-8 w-full rounded-xl bg-blue-600 py-3 font-medium text-white transition hover:bg-blue-700 disabled:cursor-not-allowed disabled:bg-gray-300 cursor-pointer"
          >
            Doğrula
          </button>

          <button
            onClick={() => {
              setShowVerificationCodeForm(false);
            }}
            className="mt-8 w-full rounded-xl bg-white py-3 font-medium text-blue-600 border border-blue-600 transition hover:bg-blue-600 hover:text-white disabled:cursor-not-allowed disabled:bg-gray-300 cursor-pointer"
          >
            Vazgeç
          </button>
        </div>
        <hr className="my-4 border-gray-300" />
        <div className="text-center text-sm text-gray-500 flex items-center justify-center gap-2">
          <RiTimerLine className="text-3xl text-slate-400" />{" "}
          {" Kodun geçerlilik süresi: "}
          <div
            className={`font-bold ${verificationCodeExpiresAt <= 0 ? "text-red-500" : ""}`}
          >
            {verificationCodeExpiresAt <= 0
              ? "Süresi doldu"
              : `${verificationCodeExpiresAt} saniye`}
          </div>
        </div>
      </div>
    </div>
  );
}
