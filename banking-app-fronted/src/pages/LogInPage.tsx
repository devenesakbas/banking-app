import loginSvg from "../assets/login.svg";
import LogInForm from "../components/LogInForm";

function LogInPage() {
  return (
    <div className="flex items-center justify-center h-screen px-4">
      <div className="border w-full xl:w-7xl lg:w-5xl md:w-xl sm:w-full flex items-center justify-center border-slate-200 rounded-xl shadow-sm">
        <div className="p-8 hidden lg:block">
          <img src={loginSvg} alt="Login" className="w-2xl h-2xl" />
        </div>

        <LogInForm />
      </div>
    </div>
  );
}

export default LogInPage;
