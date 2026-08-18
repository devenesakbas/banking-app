import { useState } from "react";
import logo from "../../assets/Logo.png";
import { useNavigate } from "react-router-dom";
import { authService } from "../../services/authServices/authService";
import { MdOutlineCheckCircle, MdOutlineErrorOutline } from "react-icons/md";
import { getLocalizedErrorMessage } from "../../utils/errorHandler";

interface ResetPasswordFormProps {
  email: string;
}

function ResetPasswordForm({ email }: ResetPasswordFormProps) {
  const [password, setPassword] = useState<string>("");

  const [passwordConfirm, setPasswordConfirm] = useState<string>("");

  const [response, setResponse] = useState<{
    type: "success" | "error";
    message: string;
  } | null>(null);

  const navigate = useNavigate();

  const handleResetPassword = async () => {
    if (!password || !passwordConfirm) {
      setResponse({
        type: "error",
        message: "Lütfen tüm alanları doldurunuz.",
      });
      return;
    }

    if (password !== passwordConfirm) {
      setResponse({
        type: "error",
        message: "Girilen şifreler uyuşmamaktadır.",
      });
      return;
    }

    try {
      const result = await authService.resetPassword({
        email,
        password,
        passwordConfirm,
      });

      if (result.success) {
        setResponse({
          type: "success",
          message: "Şifreniz başarıyla degişti. Yeni şifrenizle sisteme giriş yapabilirsiniz.",
        });

        setPassword("");
        setPasswordConfirm("");

      }
    } catch (error: any) {
      const errorCode = error.errorCode;
      const localizedMessage = getLocalizedErrorMessage(errorCode);
      setResponse({
        type: error.data.success,
        message: localizedMessage,
      });
    }
  };

  const handleRouteLogIn = () => {
    navigate("/login");
  };

  return (
    <div className="w-full max-w-xl bg-white flex flex-col items-center justify-between border border-slate-200 rounded-2xl shadow-lg overflow-hidden p-4 gap-4">
      <div className="px-3 w-full">
        <div className="flex flex-col items-center justify-between gap-4">
          <img src={logo} alt="Logo" className="h-24 mx-auto" />

          <div className="w-full">
            <div className="text-slate-500 font-bold text-2xl my-2">
              Şifre Sıfırlama
            </div>
            <p className="text-slate-400 text-xs">
              Lütfen sisteme giriş yapacağınız yeni şifrenizi belirleyiniz.
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
            <div className="text-sm text-slate-600 font-bold mb-1">Şifre</div>
            <div className="relative flex items-center rounded-xl">
              <input
                type="password"
                placeholder="Yeni şifre"
                className="w-full pl-4 pr-4 p-2 border border-slate-200 rounded-xl outline-none focus:border-blue-400"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
          </div>

          <div className="w-full">
            <div className="text-sm text-slate-600 font-bold mb-1">
              Şifre Tekrar
            </div>
            <div className="relative flex items-center rounded-xl">
              <input
                type="password"
                placeholder="Yeni şifre tekrar"
                className="w-full pl-4 pr-4 p-2 border border-slate-200 rounded-xl outline-none focus:border-blue-400"
                value={passwordConfirm}
                onChange={(e) => setPasswordConfirm(e.target.value)}
              />
            </div>
          </div>

          <div className="w-full flex flex-row items-center justify-center gap-2">
            <button
              className="w-full cursor-pointer py-2 bg-linear-to-r from-blue-500 to-indigo-600 hover:from-blue-600 hover:to-indigo-700 text-white font-semibold rounded-2xl shadow-lg shadow-blue-500/30 hover:shadow-xl hover:shadow-blue-500/40 transition-all duration-300 transform"
              onClick={handleResetPassword}
            >
              Kaydet
            </button>
          </div>

          <div className="w-full flex flex-row items-center justify-center gap-2 my-4">
            <hr className="w-full border-slate-200" />

            <p className="text-sm text-slate-500 text-center dark:border-neutral-300">
              veya
            </p>

            <hr className="w-full border-slate-200" />
          </div>

          <div className="w-full text-sm text-center text-slate-500 mb-3">
            Giriş yapmak mı istiyorsunuz?{" "}
            <a
              onClick={handleRouteLogIn}
              className="text-blue-500 text-sm hover:text-blue-600 cursor-pointer"
            >
              Giriş Yap
            </a>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ResetPasswordForm;
