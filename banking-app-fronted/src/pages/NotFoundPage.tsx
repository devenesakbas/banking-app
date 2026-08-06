import { useNavigate } from "react-router-dom";
import errorPage from "../assets/404ErrorPage.svg";

function NotFoundPage() {
  const navigate = useNavigate();

  const handleHome = () => {
    navigate("/");
  };

  return (
    <div className="flex flex-col md:flex-row items-center justify-center h-screen gap-8 px-4">
      <div>
        <h1 className="text-8xl font-bold text-blue-400">404</h1>
        <h1 className="text-4xl font-bold text-slate-400">
          Sayfa Bulunamadı
        </h1>
        <h6 className="text-slate-400">
          Bu sayfa ya mevcut değil ya da başka bir yere taşındı.
        </h6>
        <button
          className="mt-8 bg-blue-400 hover:bg-blue-500 hover:scale-105 duration-400 text-white font-bold py-2 px-4 rounded-xl hover:cursor-pointer"
          onClick={handleHome}
        >
          Ana Sayfaya Dön
        </button>
      </div>

      <img src={errorPage} alt="404" className="w-md h-md md:w-xl md:h-xl" />
    </div>
  );
}

export default NotFoundPage;
