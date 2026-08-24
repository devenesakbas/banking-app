import { IoIosWallet } from "react-icons/io";
import {
  FiMessageSquare,
  FiArrowUpRight,
  FiArrowDownLeft,
} from "react-icons/fi";
import { GrMoney } from "react-icons/gr";
import { IoSettingsOutline } from "react-icons/io5";
import { LuTicket, LuClipboardList } from "react-icons/lu";
import { LiaMoneyBillWaveAltSolid } from "react-icons/lia";
import { FaBarsProgress } from "react-icons/fa6";
import { liveCurrency } from "../../services/GenelParaService/currencyService";
import { useEffect, useState } from "react";

interface Currency {
  price: number;
  ration: string;
  changeType: string;
  symbol: string;
}

export const Dashboard = () => {
  const [currencyGA, setCurrencyGA] = useState<Currency | null>(null);
  const [currencyUSD, setCurrencyUSD] = useState<Currency | null>(null);
  const [currencyEUR, setCurrencyEUR] = useState<Currency | null>(null);
  const [currencyBTC, setCurrencyBTC] = useState<Currency | null>(null);

  const date = new Date();
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");
  const sessionTimestamp = `${hours}:${minutes}`;

  useEffect(() => {
    getCurrency();
  }, []);

  const getCurrency = async () => {
    const currency = await liveCurrency();

    if (currency !== false && currency.success) {
      const setters = {
        GA: setCurrencyGA,
        USD: setCurrencyUSD,
        EUR: setCurrencyEUR,
        BTC: setCurrencyBTC,
      };

      Object.entries(currency.data).forEach(([key, value]) => {
        const setter = setters[key as keyof typeof setters];

        if (!setter) return;

        setter({
          price: Number(value.satis),
          ration: value.oran,
          changeType: value.yon,
          symbol: value.sembol,
        });
      });
    } else {
      setCurrencyGA(null);
      setCurrencyUSD(null);
      setCurrencyEUR(null);
      setCurrencyBTC(null);
    }
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 sm:py-6 lg:px-8 lg:py-8 space-y-6">
      <div>
        <p className="text-slate-700 text-3xl font-semibold flex items-center gap-3">
          <FiMessageSquare className="text-4xl text-blue-500" /> Hoş Geldiniz,
          Enes Akbaş
        </p>
        <p className="text-slate-400 text-sm mt-1">
          Son Giriş: Bugün, {sessionTimestamp} | Güvenli Oturum
        </p>
      </div>

      <hr className="border-gray-200" />

      {currencyUSD !== null &&
        currencyEUR !== null &&
        currencyGA !== null &&
        currencyBTC !== null && (
          <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
            <div className="bg-white p-4 rounded-xl border border-gray-200 shadow-xs flex items-center justify-between">
              <div>
                <p className="text-xs text-slate-400 font-medium">DOLAR / TL</p>
                <p className="text-lg font-bold text-slate-700">
                  {currencyUSD.price} {currencyUSD.symbol}
                </p>
              </div>

              <span className="text-emerald-600 bg-emerald-50 text-xs font-semibold px-2 py-1 rounded">
                %{currencyUSD.ration}
              </span>
            </div>

            <div className="bg-white p-4 rounded-xl border border-gray-200 shadow-xs flex items-center justify-between">
              <div>
                <p className="text-xs text-slate-400 font-medium">EURO / TL</p>
                <p className="text-lg font-bold text-slate-700">
                  {currencyEUR.price} {currencyEUR.symbol}
                </p>
              </div>

              <span className="text-emerald-600 bg-emerald-50 text-xs font-semibold px-2 py-1 rounded">
                %{currencyEUR.ration}
              </span>
            </div>

            <div className="bg-white p-4 rounded-xl border border-gray-200 shadow-xs flex items-center justify-between">
              <div>
                <p className="text-xs text-slate-400 font-medium">GRAM ALTIN</p>
                <p className="text-lg font-bold text-slate-700">
                  {currencyGA.price} {currencyGA.symbol}
                </p>
              </div>

              <span className="text-emerald-600 bg-emerald-50 text-xs font-semibold px-2 py-1 rounded">
                %{currencyGA.ration}
              </span>
            </div>

            <div className="bg-white p-4 rounded-xl border border-gray-200 shadow-xs flex items-center justify-between">
              <div>
                <p className="text-xs text-slate-400 font-medium">BITCOIN</p>
                <p className="text-lg font-bold text-slate-700">
                  {currencyBTC.price} {currencyBTC.symbol}
                </p>
              </div>

              <span className="text-emerald-600 bg-emerald-50 text-xs font-semibold px-2 py-1 rounded">
                %{currencyBTC.ration}
              </span>
            </div>
          </div>
        )}

      <div className="grid grid-cols-1 md:grid-cols-12 gap-6">
        <div className="lg:col-span-8 bg-blue-600 text-white p-6 rounded-2xl border border-blue-500 shadow-sm flex flex-col justify-between relative overflow-hidden">
          <div className="absolute inset-y-0 -right-8 flex items-center pointer-events-none">
            <GrMoney className="text-[300px] text-slate-50 opacity-10" />
          </div>

          <div className="relative mt-2 z-10">
            <h3 className="text-sm font-semibold text-white-300 mb-2 flex items-center gap-2">
              <IoIosWallet className="text-lg" /> Toplam Varlıklarım
            </h3>
            <p className="text-5xl font-semibold tracking-tight text-white">
              ₺ 142.750,50
            </p>
          </div>

          <div className="mt-3 pt-4 border-t border-white/20 flex items-center justify-between text-xs text-white/80 relative z-10">
            <span>Bu ayki değişim</span>
            <span className="text-emerald-700 font-semibold bg-white px-2 py-1 rounded-lg">
              %+4.2
            </span>
          </div>
        </div>

        <div className="lg:col-span-4 bg-white px-6 py-4 rounded-2xl border border-gray-200 shadow-xs flex flex-col justify-between">
          <div>
            <div className="flex flex-row justify-between items-center mb-2 px-1">
              <p className="text-slate-700 font-semibold">Hızlı İşlemler</p>
              <div className="bg-blue-50 p-2 rounded-xl border border-blue-100">
                <LuClipboardList className="text-blue-600" />
              </div>
            </div>

            <div className="space-y-1">
              <a className="py-2.5 px-2 rounded-lg text-slate-600 flex items-center gap-3 hover:bg-blue-50 hover:text-blue-600 active:bg-blue-100 cursor-pointer transition-all">
                <LiaMoneyBillWaveAltSolid className="text-xl" /> Para Gönder
              </a>
              <a className="py-2.5 px-2 rounded-lg text-slate-600 flex items-center gap-3 hover:bg-blue-50 hover:text-blue-600 active:bg-blue-100 cursor-pointer transition-all">
                <LuTicket className="text-xl" /> Fatura Öde
              </a>
              <a className="py-2.5 px-2 rounded-lg text-slate-600 flex items-center gap-3 hover:bg-blue-50 hover:text-blue-600 active:bg-blue-100 cursor-pointer transition-all">
                <IoSettingsOutline className="text-xl" /> Kart Ayarları
              </a>
            </div>
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-12 gap-6">
        <div className="lg:col-span-8 bg-white p-6 rounded-2xl border border-gray-200 shadow-xs">
          <div className="flex justify-between items-center mb-4">
            <h3 className="font-semibold text-slate-700">Son İşlemler</h3>
            <span className="text-xs text-blue-600 hover:underline cursor-pointer font-medium">
              Tümünü Gör
            </span>
          </div>

          <div className="space-y-3">
            <div className="flex items-center justify-between p-3 hover:bg-slate-50 rounded-xl transition-colors border border-transparent hover:border-gray-100">
              <div className="flex items-center gap-3">
                <div className="bg-emerald-50 p-2.5 rounded-xl text-emerald-600">
                  <FiArrowDownLeft className="text-lg" />
                </div>
                <div>
                  <p className="text-sm font-semibold text-slate-700">
                    Maaş Ödemesi
                  </p>
                  <p className="text-xs text-slate-400">Bugün, 09:30</p>
                </div>
              </div>
              <span className="text-emerald-600 font-bold text-sm">
                +₺ 45.000,00
              </span>
            </div>

            <div className="flex items-center justify-between p-3 hover:bg-slate-50 rounded-xl transition-colors border border-transparent hover:border-gray-100">
              <div className="flex items-center gap-3">
                <div className="bg-rose-50 p-2.5 rounded-xl text-rose-600">
                  <FiArrowUpRight className="text-lg" />
                </div>
                <div>
                  <p className="text-sm font-semibold text-slate-700">
                    Market Alışverişi
                  </p>
                  <p className="text-xs text-slate-400">Dün, 18:45</p>
                </div>
              </div>
              <span className="text-slate-700 font-bold text-sm">
                -₺ 1.250,00
              </span>
            </div>
          </div>
        </div>

        <div className="lg:col-span-4 bg-white p-6 rounded-2xl border border-gray-200 shadow-xs flex flex-col justify-between">
          <div>
            <div className="flex flex-row items-center justify-between mb-4">
              <h3 className="font-semibold text-slate-700">Harcama Dağılımı</h3>
              <div className="bg-blue-50 p-2 rounded-xl border border-blue-100">
                <FaBarsProgress className="text-blue-600" />
              </div>
            </div>

            <div className="space-y-4">
              <div>
                <div className="flex justify-between text-xs mb-1 text-slate-600">
                  <span>Market & Gıda</span>
                  <span className="font-semibold">%45</span>
                </div>
                <div className="w-full bg-slate-100 rounded-full h-2">
                  <div
                    className="bg-blue-600 h-2 rounded-full"
                    style={{ width: "45%" }}
                  ></div>
                </div>
              </div>

              <div>
                <div className="flex justify-between text-xs mb-1 text-slate-600">
                  <span>Faturalar</span>
                  <span className="font-semibold">%25</span>
                </div>
                <div className="w-full bg-slate-100 rounded-full h-2">
                  <div
                    className="bg-indigo-500 h-2 rounded-full"
                    style={{ width: "25%" }}
                  ></div>
                </div>
              </div>

              <div>
                <div className="flex justify-between text-xs mb-1 text-slate-600">
                  <span>Eğlence & Diğer</span>
                  <span className="font-semibold">%30</span>
                </div>
                <div className="w-full bg-slate-100 rounded-full h-2">
                  <div
                    className="bg-amber-500 h-2 rounded-full"
                    style={{ width: "30%" }}
                  ></div>
                </div>
              </div>
            </div>
          </div>

          <p className="text-[11px] text-slate-400 mt-4 text-center">
            Bu ay toplam 12.400 TL harcandı.
          </p>
        </div>
      </div>
    </div>
  );
};
