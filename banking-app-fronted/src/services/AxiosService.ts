

import axios from "axios";

export const GetAxiosInstance = (uri: string, contentType?: string) => {

    const result = axios.create({
        baseURL: uri,
        headers: {
            "Content-Type": contentType || "application/json",
        },
    });

    return result;

};
