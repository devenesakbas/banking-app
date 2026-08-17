import { useState } from "react";
import logo from "../../assets/logo.png";
import { useNavigate } from "react-router-dom";
import { MdOutlineErrorOutline, MdOutlineCheckCircle } from "react-icons/md";
import ForgotPasswordVerificationCodeForm from "./ForgotPasswordVerificationCodeForm";
import { authService } from "../../services/authServices/authService";
import { getLocalizedErrorMessage } from "../../utils/errorHandler";

function ForgotPasswordForm() {
  const [email, setEmail] = useState<string>("");
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [response, setResponse] = useState<{
    type: "success" | "error";
    message: string;
  } | null>(null);
  const [showVerificationCodeForm, setShowVerificationCodeForm] =
    useState<boolean>(false);

  const navigate = useNavigate();

  const handleForgotPassword = async () => {
    if (!email) {
      setResponse({
        type: "error",
        message: "Lütfen sisteme kayıtlı e-posta adresinizi girin.",
      });
      return;
    }

    setIsLoading(true);

    try {
      const response = await authService.forgotPassword({ email });
      if (response.data.success) {
        setResponse({
          type: "success",
          message:
            "Doğrulama kodu e-posta adresinize gönderildi.",
        });
        setShowVerificationCodeForm(true);
      }
    } catch (error: any) {
      const errorResponse = error.response?.data;
      const localizedMessage = getLocalizedErrorMessage(errorResponse);
      setResponse({
        type: "error",
        message: localizedMessage,
      });
    } 
    finally{
      setIsLoading(false);
    }

  };

  const handleRouteLogin = () => {
    navigate("/login");
  };


  return (
    <div className="w-full max-w-xl bg-white flex flex-col items-center justify-between border border-slate-200 rounded-2xl shadow-lg overflow-hidden p-4 gap-4">
      <img src={logo} alt="Logo" className="h-24 mx-auto" />

      <div className="px-3 w-full">
        <div className="flex flex-col items-center justify-between gap-4">
          {showVerificationCodeForm ? (
            <ForgotPasswordVerificationCodeForm
              setShowVerificationCodeForm={setShowVerificationCodeForm}
              email={email}
            />
          ) : (
            <>
              <div className="text-start w-full">
                <h1 className="text-2xl font-bold text-slate-500">
                  Şifrenizi mi unuttunuz?
                </h1>
                <p className="text-xs text-slate-400 mt-2">
                  Şifrenizi sıfırlamak için lütfen sisteme giriş yaptıgınız
                  e-posta adresinizi girin.
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

              <div className="w-full">
                <div className="text-sm text-slate-600 font-bold mb-1">
                  E-Posta
                </div>
                <div className="relative flex items-center rounded-xl">
                  <input
                    type="text"
                    placeholder="E-Posta Adresi"
                    className="w-full pl-4 pr-4 p-2 border border-slate-200 rounded-xl outline-none focus:border-blue-400"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
              </div>

              <div className="w-full">
                <button
                  className="w-full cursor-pointer py-2 bg-gradient-to-r from-blue-500 to-indigo-600 hover:from-blue-600 hover:to-indigo-700 text-white font-semibold rounded-2xl shadow-lg shadow-blue-500/30 hover:shadow-xl hover:shadow-blue-500/40 transition-all duration-300 transform"
                  onClick={handleForgotPassword}
                  disabled={isLoading}
                >
                  {isLoading ? "Kontrol ediliyor..." : "Şifre Sıfırla"}
                </button>
              </div>

              <div className="flex items-center gap-4 my-4 w-full">
                <hr className="w-full border-slate-200 dark:border-neutral-300" />
                <p className="text-sm text-slate-700 text-center dark:text-slate-500">
                  veya
                </p>
                <hr className="w-full border-slate-200 dark:border-neutral-300" />
              </div>

              <div className="w-full mb-3">
                <div className="text-sm text-slate-500 text-center">
                  Giriş yapmak mı istiyorsunuz?{" "}
                  <span
                    onClick={handleRouteLogin}
                    className="text-blue-500 cursor-pointer text-sm hover:text-blue-600"
                  >
                    Giriş Yap
                  </span>
                </div>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default ForgotPasswordForm;
