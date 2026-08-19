import { Link } from 'react-router-dom';
import logo from '../../assets/Logo.png';
import { IoLogoInstagram } from 'react-icons/io5';
import { FaFacebookF, FaLinkedinIn, FaXTwitter } from 'react-icons/fa6';

export const Footer: React.FC = () => {
  return (
    <footer className="bg-white border-t border-gray-100 text-gray-600">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12 lg:py-16">
        
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8 lg:gap-12 mb-12">
          
          <div className="md:col-span-1 space-y-4">
            <Link to="/dashboard" className="inline-block">
              <img src={logo} width={160} alt="Logo" />
            </Link>
            <p className="text-xs text-gray-500 leading-relaxed">
              Güvenli ve hızlı dijital bankacılık çözümleriyle finansal işlemlerinizi kolayca yönetin.
            </p>
          </div>

          <div>
            <h3 className="text-xs font-semibold uppercase tracking-wider text-gray-800 mb-4">
              Hızlı Erişim
            </h3>
            <ul className="space-y-2.5">
              <li>
                <Link to="/dashboard" className="text-xs text-gray-500 hover:text-blue-600 transition-colors">
                  Yönetim / Panel
                </Link>
              </li>
              <li>
                <Link to="/accounts" className="text-xs text-gray-500 hover:text-blue-600 transition-colors">
                  Hesaplarım
                </Link>
              </li>
              <li>
                <Link to="/transfers" className="text-xs text-gray-500 hover:text-blue-600 transition-colors">
                  Para Transferi
                </Link>
              </li>
              <li>
                <Link to="/cards" className="text-xs text-gray-500 hover:text-blue-600 transition-colors">
                  Kartlarım
                </Link>
              </li>
            </ul>
          </div>

          <div>
            <h3 className="text-xs font-semibold uppercase tracking-wider text-gray-800 mb-4">
              Destek ve Güvenlik
            </h3>
            <ul className="space-y-2.5">
              <li>
                <a className="text-xs text-gray-500 hover:text-blue-600 transition-colors">
                  Yardım Merkezi
                </a>
              </li>
              <li>
                <a className="text-xs text-gray-500 hover:text-blue-600 transition-colors">
                  Güvenlik İpuçları
                </a>
              </li>
              <li>
                <a className="text-xs text-gray-500 hover:text-blue-600 transition-colors">
                  Bize Ulaşın
                </a>
              </li>
            </ul>
          </div>

          <div>
            <h3 className="text-xs font-semibold uppercase tracking-wider text-gray-800 mb-4">
              Müşteri İletişim Merkezi
            </h3>
            <a href="tel:+90850 000 00 00" className="text-sm font-bold text-gray-800 mb-1 hover:text-blue-600">0850 000 00 00</a>
            <p className="text-[11px] text-gray-500 mb-3">7/24 Kesintisiz Destek Hattı</p>
            <p className="text-gray-800 text-sm font-bold mb-1">Bizleri takip edin</p>
            <div className="flex flex-row align-center justify-start gap-3">

                <div className="rounded-full border p-1 cursor-pointer hover:text-blue-600 hover:scale-105">
                    <FaXTwitter />
                </div>

                <div className="rounded-full border p-1 cursor-pointer hover:text-blue-600 hover:scale-105">
                    <IoLogoInstagram />
                </div>

                <div className="rounded-full border p-1 cursor-pointer hover:text-blue-600 hover:scale-105">
                    <FaLinkedinIn />
                </div>

                <div className="rounded-full border p-1 cursor-pointer hover:text-blue-600 hover:scale-105">
                    <FaFacebookF />
                </div>

            </div>
          </div>

        </div>

        {/* Alt Kısım: Telif ve Yasal Linkler */}
        <div className="pt-8 border-t border-gray-100 flex flex-col sm:flex-row items-center justify-between gap-4">
          <p className="text-xs text-gray-400 text-center sm:text-left">
            &copy; {new Date().getFullYear()} Tüm hakları saklıdır. Enes Akbaş Bankacılık A.Ş.
          </p>
          <div className="flex items-center gap-6 text-xs text-gray-400">
            <a className="hover:text-gray-600 transition-colors">Gizlilik Politikası</a>
            <a className="hover:text-gray-600 transition-colors">Kullanım Koşulları</a>
            <a className="hover:text-gray-600 transition-colors">Çerez Tercihleri</a>
          </div>
        </div>

      </div>
    </footer>
  );
};