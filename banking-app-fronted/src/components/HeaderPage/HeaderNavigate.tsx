import { Link } from 'react-router-dom';

export const HeaderNavigate = () => {

    return (

        <nav className="hidden md:flex items-center gap-8">
            <Link to="/dashboard" className="text-sm font-medium text-gray-700 hover:text-blue-600 transition-colors">
              Panel
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

    );

}