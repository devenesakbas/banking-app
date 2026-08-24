import masterCardLogo from "../../assets/mastercard-logo.svg";
import { PiContactlessPaymentLight } from "react-icons/pi";
import Tilt from "react-parallax-tilt";

interface FrontSideProps {
  cardNumber?: string;
  user?: string;
  expired?: string;
}

const parseCardNumber = (cardNumber: string) => {
  const cleaned = cardNumber.replace(/\s+/g, "");
  return cleaned.match(/.{1,4}/g)?.join(" ");
}

export const CreditCardFrontSide = ({ cardNumber="****************", user = "Ad Soyad", expired = "MM/YY"}: FrontSideProps) => {
  return (
    <Tilt
      className="parallax-effect-glare-scale"
      perspective={1000}
      glareEnable={true}
      glareMaxOpacity={0.45}
      glarePosition="all"
      scale={1.05}
      transitionSpeed={1500}
      gyroscope={true}
    >
      <div
        className="rounded-2xl shadow-lg p-6 w-96 h-56 flex flex-col justify-between text-slate-800"
        style={{
          background:
            "linear-gradient(135deg, rgb(255, 255, 255) 0%, rgb(140, 165, 205) 100%)",
        }}
      >
        <div className="flex justify-between items-center">
          <h4 className="font-bold tracking-wider">ENAK BANK</h4>
          <PiContactlessPaymentLight className="text-3xl" />
        </div>

        <div className="w-full text-xl font-mono tracking-widest text-center my-auto">
          {parseCardNumber(cardNumber)}
        </div>

        <div className="flex flex-row items-end justify-between">
          <div className="flex gap-6">
            <div>
              <p className="text-xs text-slate-600 uppercase">Kart Sahibi</p>
              <h3 className="font-semibold text-sm tracking-wide">
                { user }
              </h3>
            </div>

            <div>
              <p className="text-xs text-slate-600 uppercase">Son Kullanma</p>
              <h3 className="font-semibold text-sm">{ expired }</h3>
            </div>
          </div>

          <img src={masterCardLogo} width="50" alt="Mastercard" />
        </div>
      </div>
    </Tilt>
  );
};
