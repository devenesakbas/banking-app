import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import logo from '../../assets/Logo.png';

export const Header = () => {
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    navigate("/login");
  };

  return (
    <header className="sticky top-0 z-50 bg-white/80 backdrop-blur-md border-b border-gray-100 shadow-xs">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-20">
          
          <div className="flex items-center gap-2">
            <Link to="/dashboard" className="flex items-center gap-2">
              <img src={logo} width={200} />
            </Link>
          </div>

          <nav className="hidden md:flex items-center gap-8">
            <Link to="/dashboard" className="text-sm font-medium text-gray-700 hover:text-blue-600 transition-colors">
              Yönetim
            </Link>
            <Link to="/accounts" className="text-sm font-medium text-gray-500 hover:text-blue-600 transition-colors">
              Hesaplarım
            </Link>
            <Link to="/transfers" className="text-sm font-medium text-gray-500 hover:text-blue-600 transition-colors">
              Para Transferi
            </Link>
            <Link to="/cards" className="text-sm font-medium text-gray-500 hover:text-blue-600 transition-colors">
              Kartlarım
            </Link>
          </nav>

          {/* 3. SAĞ TARAF (PROFIL & ÇIKIŞ) */}
          <div className="hidden md:flex items-center gap-4">
            <div className="flex items-center gap-3 pl-4 border-l border-gray-200">
              <div className="w-9 h-9 rounded-full bg-blue-50 text-blue-600 font-semibold flex items-center justify-center text-sm border border-blue-100">
                E
              </div>
              <div className="text-left">
                <p className="text-xs font-semibold text-gray-800">Enes Akbaş</p>
                <p className="text-[11px] text-gray-500">Bireysel Müşteri</p>
              </div>
            </div>

            <button 
              onClick={handleLogout}
              className="px-4.5 py-2 text-xs font-semibold text-red-600 bg-red-50 hover:bg-red-100 rounded-xl transition-colors cursor-pointer"
            >
              Çıkış Yap
            </button>
          </div>

          {/* 4. MOBİL HAMBURGER BUTONU */}
          <div className="flex md:hidden">
            <button
              onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
              className="p-2 rounded-lg text-gray-600 hover:bg-gray-100 focus:outline-hidden"
            >
              <svg className="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                {isMobileMenuOpen ? (
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                ) : (
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
                )}
              </svg>
            </button>
          </div>

        </div>
      </div>

      {/* 5. MOBİL AÇILIR MENÜ */}
      {isMobileMenuOpen && (
        <div className="md:hidden bg-white border-b border-gray-100 px-4 pt-2 pb-6 space-y-3 shadow-lg">
          <Link 
            to="/dashboard" 
            onClick={() => setIsMobileMenuOpen(false)}
            className="block px-3 py-2 rounded-lg text-base font-medium text-gray-700 hover:bg-blue-50 hover:text-blue-600"
          >
            Panel
          </Link>
          <Link 
            to="/accounts" 
            onClick={() => setIsMobileMenuOpen(false)}
            className="block px-3 py-2 rounded-lg text-base font-medium text-gray-700 hover:bg-blue-50 hover:text-blue-600"
          >
            Hesaplarım
          </Link>
          <Link 
            to="/transfers" 
            onClick={() => setIsMobileMenuOpen(false)}
            className="block px-3 py-2 rounded-lg text-base font-medium text-gray-700 hover:bg-blue-50 hover:text-blue-600"
          >
            Para Transferi
          </Link>
          <Link 
            to="/cards" 
            onClick={() => setIsMobileMenuOpen(false)}
            className="block px-3 py-2 rounded-lg text-base font-medium text-gray-700 hover:bg-blue-50 hover:text-blue-600"
          >
            Kartlarım
          </Link>
          
          <div className="pt-4 border-t border-gray-100 flex items-center justify-between px-3">
            <div className="flex items-center gap-3">
              <div className="w-9 h-9 rounded-full bg-blue-50 text-blue-600 font-semibold flex items-center justify-center text-sm">
                EA
              </div>
              <div>
                <p className="text-xs font-semibold text-gray-800">Enes Akbaş</p>
                <p className="text-[11px] text-gray-500">Bireysel</p>
              </div>
            </div>
            <button 
              onClick={handleLogout}
              className="px-4 py-2 text-xs font-semibold text-red-600 bg-red-50 rounded-xl"
            >
              Çıkış
            </button>
          </div>
        </div>
      )}
    </header>
  );
};