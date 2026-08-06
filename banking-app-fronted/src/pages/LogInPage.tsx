import loginSvg from "../assets/login.svg";
import LogInForm from "../components/LogInForm";

function LogInPage() {
  return (
    <div className="flex items-center justify-center min-h-screen px-4 bg-slate-50">
      <div className="w-full max-w-5xl bg-white flex items-center justify-between border border-slate-200 rounded-2xl shadow-lg overflow-hidden">
        <div className="hidden lg:flex flex-col items-center justify-center w-1/2 p-8 bg-[#51a2ff] self-stretch">
          <img
            src={loginSvg}
            alt="Login"
            className="h-auto object-contain mb-6"
          />
          <h6 className="text-slate-100 text-lg font-semibold text-center">
            "Güvenilir bir geleceğin temellerini şimdiden atıyoruz"
          </h6>
        </div>

        <div className="w-full lg:w-1/2 p-6 sm:p-10 flex items-center justify-center">
          <LogInForm />
        </div>
      </div>
    </div>
  );
}

export default LogInPage;
