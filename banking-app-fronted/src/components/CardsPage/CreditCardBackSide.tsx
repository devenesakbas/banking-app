import Tilt from "react-parallax-tilt";

interface BackSideProps {
  cvc?: string;
}

export const CreditCardBackSide = ({ cvc = "***" }: BackSideProps) => {
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
        className="rounded-2xl shadow-lg w-96 h-56 flex flex-col justify-between py-6 overflow-hidden"
        style={{
          background:
            "linear-gradient(135deg, rgb(255, 255, 255) 0%, rgb(140, 165, 205) 100%)",
        }}
      >
        <div className="h-10 bg-slate-900 w-full mt-2"></div>

        <div className="px-6">
          <div className="text-xs text-slate-600 mb-1">Yetkili İmza / CVV</div>
          <div className="w-full bg-slate-200 h-9 rounded flex items-center justify-end px-4 font-mono font-bold text-slate-700 shadow-inner">
            { cvc }
          </div>
        </div>

        <div className="px-6 text-[10px] text-slate-500 text-center">
          Bu kart önizleme sürümüdür. İzinsiz kullanılamaz.
        </div>
      </div>
    </Tilt>
  );
};
