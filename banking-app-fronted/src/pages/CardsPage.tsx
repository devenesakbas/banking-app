import { CreditCardBackSide } from "../components/CardsPage/CreditCardBackSide";
import { CreditCardFrontSide } from "../components/CardsPage/CreditCardFrontSide";
import { FooterPage } from "./FooterPage";
import { HeaderPage } from "./HeaderPage";


export const CardsPage = () => {
  return (
    <>
      <HeaderPage />

      <div className="max-w-7xl mx-auto px-4 sm:px-6 sm:py-6 lg:px-8 lg:py-8 space-y-6">
        <div>
          <p className="text-slate-700 text-3xl font-semibold flex items-center gap-3">
            Kartlarım
          </p>
          <p className="text-slate-400 text-sm mt-1">
            Kartlarınızı görüntüleyebilir veya yeni kart talebinde
            bulunabilirsiniz.
          </p>
        </div>

        <hr className="border-gray-200" />

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          
          <CreditCardFrontSide />

          <CreditCardBackSide />

          <div></div>
        </div>
      </div>

      <FooterPage />
    </>
  );
};
