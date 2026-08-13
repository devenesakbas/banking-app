import logo from "../assets/logo.png";
import { useState } from "react";
import { IoEye, IoEyeOff, IoLockClosed, IoMail } from "react-icons/io5";
import { useNavigate } from "react-router-dom";
import type { LoginRequest, LoginResponse } from "../types/auth";
import type { ApiResponse } from "../types/ApiResponse";
import { authService } from "../services/authServices/authService";
import { MdOutlineCheckCircle, MdOutlineErrorOutline } from "react-icons/md";
import { getLocalizedErrorMessage } from "../utils/errorHandler";

function LogInForm() {
  const navigate = useNavigate();

  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [rememberMe, setRememberMe] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [passwordVisible, setPasswordVisible] = useState<boolean>(false);
  const [message, setMessage] = useState<{ type: string; text: string } | null>(
    null,
  );

  const handleRouteRegister = () => {
    navigate("/register");
  };

  const handleRouteForgotPassword = () => {
    navigate("/forgot-password");
  };

  const handleLogin = async () => {
    
    if(!email || !password) {
      setMessage({ type: "error", text: "Lütfen tüm alanları doldurunuz." });
      return;
    }
    
    setIsLoading(true);

    const data: LoginRequest = {
      email,
      password,
    };

    try {

      await authService
        .login(data)
        .then((response) => {
          if (response.data.success) {
            setIsLoading(false);
            setEmail("");
            setPassword("");
            setRememberMe(false);
            setMessage({ type: "success", text: "Giriş başarılı!" });

            navigate("/dashboard");
          } else {
            const errorMessage = getLocalizedErrorMessage(
              response.data?.errorCode,
            );
            setMessage({ type: "error", text: errorMessage });
          }
        })
        .catch((error) => {
          const errorResponse = error.response?.data;
          const localizedMessage = getLocalizedErrorMessage(errorResponse);
          setMessage({ text: localizedMessage, type: "error" });
        })
        .finally(() => {
          setIsLoading(false);
        });
    } catch (error) {
      setMessage({
        type: "error",
        text: "İşlem gerçekleştirilirken sorun ile karşılaşıldı. Lütfen daha sonra tekrar deneyiniz.",
      });
    }
  };

  return (
    <div className="flex flex-col gap-4 w-xl p-6">
      <div className="flex justify-end">
        <img src={logo} alt="Logo" className="h-24" />
      </div>

      <div>
        <h1 className="text-xl text-slate-600">Lütfen Giriş Yapın</h1>
      </div>

      {message && (
        <div className={`w-full transition-all duration-300`}>
          <div
            className={`p-3 rounded-xl text-xs font-medium border flex items-center gap-2 ${
              message.type === "success"
                ? "bg-emerald-50 border-emerald-200 text-emerald-700"
                : "bg-rose-50 border-rose-200 text-rose-700"
            }`}
          >
            {message.type === "success" ? (
              <MdOutlineCheckCircle className="text-xl" />
            ) : (
              <MdOutlineErrorOutline className="text-xl" />
            )}
            {message.text}
          </div>
        </div>
      )}

      <div>
        <div className="text-sm text-slate-600 font-bold mb-1">E-Posta</div>
        <div className="relative flex items-center rounded-xl">
          <span className="absolute left-3 text-slate-400">
            <IoMail size={20} />
          </span>

          <input
            type="text"
            placeholder="E-Posta"
            className="w-full pl-10 pr-4 p-2 border border-slate-200 rounded-xl outline-none focus:border-blue-400"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
      </div>

      <div>
        <div className="text-sm text-slate-600 font-bold mb-1">Şifre</div>
        <div className="relative flex items-center rounded-xl">
          <span className="absolute left-3 text-slate-400">
            <IoLockClosed size={20} />
          </span>

          <input
            type={passwordVisible ? "text" : "password"}
            placeholder="Şifre"
            className="w-full pl-10 pr-10 p-2 border border-slate-200 rounded-xl outline-none focus:border-blue-400"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <span
            onClick={() => setPasswordVisible(!passwordVisible)}
            className="absolute right-3 text-slate-400 cursor-pointer hover:text-slate-600"
          >
            {passwordVisible ? <IoEyeOff size={20} /> : <IoEye size={20} />}
          </span>
        </div>
      </div>

      <div className="flex items-center justify-between gap-2">
        <label className="flex items-center gap-2 text-sm hover:cursor-pointer">
          <input
            type="checkbox"
            checked={rememberMe}
            onChange={(e) => setRememberMe(e.target.checked)}
          />
          Beni Hatırla
        </label>
        <a
          onClick={handleRouteForgotPassword}
          className="text-blue-500 text-sm hover:text-blue-600 hover:cursor-pointer"
        >
          Şifremi Unuttum
        </a>
      </div>

      <div>
        <button
          className="w-full cursor-pointer py-3 bg-gradient-to-r from-blue-500 to-indigo-600 hover:from-blue-600 hover:to-indigo-700 text-white font-semibold rounded-2xl shadow-lg shadow-blue-500/30 hover:shadow-xl hover:shadow-blue-500/40 transition-all duration-300 transform"
          // className="w-full p-2 bg-blue-400 hover:bg-blue-500 hover:cursor-pointer rounded-xl text-white duration-300"
          disabled={isLoading}
          onClick={handleLogin}
        >
          {isLoading ? "Giriş yapılıyor..." : "Giriş Yap"}
        </button>
      </div>

      <div className="flex items-center gap-4 my-4">
        <hr className="w-full border-slate-200 dark:border-neutral-300" />
        <p className="text-sm text-slate-800 text-center dark:text-slate-500">
          veya
        </p>
        <hr className="w-full border-slate-200 dark:border-neutral-300" />
      </div>

      <div className="text-center text-sm text-slate-600">
        Hesabınız yok mu?{" "}
        <a
          onClick={handleRouteRegister}
          className="text-blue-500 text-sm hover:text-blue-600 hover:cursor-pointer"
        >
          Kayıt olun
        </a>
      </div>
    </div>
  );
}

export default LogInForm;
