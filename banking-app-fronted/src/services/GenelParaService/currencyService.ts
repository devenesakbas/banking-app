import axios from "axios";
import type { CurrencyResponse } from "../../types/currency";

const BASE_URI = import.meta.env.VITE_GENELPARA_BASE_URI;

export const liveCurrency = async (): Promise<CurrencyResponse | false> => {

    const uri = `${BASE_URI}?list=doviz,altin,kripto&semboll=all`;

    try{
        const result = await axios.get(uri);

        if(result.data){
            return result.data;
        }
        else {
            return false;
        }
    }
    catch (error){
        console.log("Currency Error: " + error);
        return false;
    }

}