import { useState } from "react";
import logo from "../assets/logo.png";
import { MdOutlineErrorOutline, MdOutlineCheckCircle } from "react-icons/md";
import { useNavigate } from "react-router-dom";

function RegisterForm() {
  const [firstName, setFirstName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [confirmPassword, setConfirmPassword] = useState<string>("");
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [message, setMessage] = useState<{
    text: string;
    type: "success" | "error";
  } | null>(null);

  const navigate = useNavigate();

  const handleRegister = () => {
    setMessage(null);

    if (!firstName || !lastName || !email || !password || !confirmPassword) {
      setMessage({ text: "Lütfen tüm alanları doldurunuz.", type: "error" });
      return;
    }
    if (password !== confirmPassword) {
      setMessage({ text: "Girilen şifreler eşleşmiyor.", type: "error" });
      return;
    }

    setIsLoading(true);

    setTimeout(() => {
      setIsLoading(false);
      setFirstName("");
      setLastName("");
      setEmail("");
      setPassword("");
      setConfirmPassword("");

      setMessage({ text: "Kayıt işlemi başarılı.", type: "success" });

      navigate("/");
    }, 2000);
  };

  const handleRouteLogin = () => {
    navigate("/login");
  };

  return (
    <div className="w-full max-w-xl bg-white flex flex-col items-center justify-between border border-slate-200 rounded-2xl shadow-lg overflow-hidden p-4 gap-4">
      <img src={logo} alt="Logo" className="h-24 mx-auto" />

      <div className="px-3 text-start w-full">
        <h1 className="text-2xl font-bold text-slate-500">Hesap oluşturun</h1>
      </div>

      {message && (
        <div className={`px-3 w-full transition-all duration-300`}>
          <div
            className={`p-3 rounded-xl text-sm font-medium border flex items-center gap-2 ${message.type === "success"
                ? "bg-emerald-50 border-emerald-200 text-emerald-700"
                : "bg-rose-50 border-rose-200 text-rose-700"
              }`}
          >
            {message.type === "success" ? (
              <MdOutlineCheckCircle />
            ) : (
              <MdOutlineErrorOutline />
            )}
            {message.text}
          </div>
        </div>
      )}

      <div className="px-3 w-full">
        <div className="flex flex-col md:flex-row items-center justify-between gap-4">
          <div className="w-full">
            <div className="text-sm text-slate-600 font-bold mb-1">Ad</div>
            <div className="relative flex items-center rounded-xl">
              <input
                type="text"
                placeholder="Ad"
                className="w-full pl-4 pr-4 p-2 border border-slate-200 rounded-xl outline-none focus:border-blue-400"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
              />
            </div>
          </div>
          <div className="w-full">
            <div className="text-sm text-slate-600 font-bold mb-1">Soyad</div>
            <div className="relative flex items-center rounded-xl">
              <input
                type="text"
                placeholder="Soyad"
                className="w-full pl-4 pr-4 p-2 border border-slate-200 rounded-xl outline-none focus:border-blue-400"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
              />
            </div>
          </div>
        </div>
      </div>

      <div className="px-3 w-full">
        <div className="text-sm text-slate-600 font-bold mb-1">E-Posta</div>
        <div className="relative flex items-center rounded-xl">
          {/* <span className="absolute left-3 text-slate-400">
              <IoMail size={20} />
            </span> */}

          <input
            type="text"
            placeholder="E-Posta"
            className="w-full pl-4 pr-4 p-2 border border-slate-200 rounded-xl outline-none focus:border-blue-400"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
      </div>

      <div className="px-3 w-full">
        <div className="text-sm text-slate-600 font-bold mb-1">Şifre</div>
        <div className="relative flex items-center rounded-xl">
          {/* <span className="absolute left-3 text-slate-400">
              <IoLockClosed size={20} />
            </span> */}

          <input
            type="password"
            placeholder="Şifre"
            className="w-full pl-4 pr-4 p-2 border border-slate-200 rounded-xl outline-none focus:border-blue-400"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          {/* <span className="absolute right-3 text-slate-400 cursor-pointer hover:text-slate-600">
              <IoEye size={20} />
            </span> */}
        </div>
      </div>

      <div className="px-3 w-full">
        <div className="text-sm text-slate-600 font-bold mb-1">
          Şifre Tekrar
        </div>
        <div className="relative flex items-center rounded-xl">
          {/* <span className="absolute left-3 text-slate-400">
              <IoLockClosed size={20} />
            </span> */}

          <input
            type="password"
            placeholder="Şifre Tekrar"
            className="w-full pl-4 pr-4 p-2 border border-slate-200 rounded-xl outline-none focus:border-blue-400"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />

          {/* <span className="absolute right-3 text-slate-400 cursor-pointer hover:text-slate-600">
              <IoEye size={20} />
            </span> */}
        </div>
      </div>

      <div className="px-3 w-full mt-8">
        <button
          className="w-full cursor-pointer py-3 bg-gradient-to-r from-blue-500 to-indigo-600 hover:from-blue-600 hover:to-indigo-700 text-white font-semibold rounded-2xl shadow-lg shadow-blue-500/30 hover:shadow-xl hover:shadow-blue-500/40 transition-all duration-300 transform"
          //className="px-3 w-full bg-blue-400 hover:bg-blue-500 duration-300 hover:cursor-pointer text-white rounded-xl py-2"
          onClick={handleRegister}
          disabled={isLoading}
        >
          {isLoading ? "Kayıt olunuyor..." : "Kayıt Ol"}
        </button>
      </div>

      <div className="flex items-center gap-4 my-4 w-full">
        <hr className="w-full border-slate-200 dark:border-neutral-300" />
        <p className="text-sm text-slate-700 text-center dark:text-slate-500">
          veya
        </p>
        <hr className="w-full border-slate-200 dark:border-neutral-300" />
      </div>

      <div className="px-3 w-full mb-3">
        <div className="text-sm text-slate-500 text-center">
          Zaten bir hesabınız var mı?{" "}
          <span
            onClick={handleRouteLogin}
            className="text-blue-500 cursor-pointer text-sm hover:text-blue-600"
          >
            Giriş Yap
          </span>
        </div>
      </div>
    </div>
  );
}

export default RegisterForm;
