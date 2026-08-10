import axios from "axios";

export const GetAxiosInstance = (uri: string, contentType?: string) => {
    return axios.create({
        baseURL: uri,
        timeout: 10000,
        headers: {
            "Content-Type": contentType || "application/json",
        },
    });
};

export const PostAxiosInstance = (uri: string, contentType?: string) => {
    return axios.create({
        baseURL: uri,
        timeout: 10000,
        headers: {
            "Content-Type": contentType || "application/json",
        },
    });
};

export const PutAxiosInstance = (uri: string, contentType?: string) => {
    return axios.create({
        baseURL: uri,
        timeout: 10000,
        headers: {
            "Content-Type": contentType || "application/json",
        },
    });
};

export const DeleteAxiosInstance = (uri: string, contentType?: string) => {
    return axios.create({
        baseURL: uri,
        timeout: 10000,
        headers: {
            "Content-Type": contentType || "application/json",
        },
    });
};

export const PatchAxiosInstance = (uri: string, contentType?: string) => {
    return axios.create({
        baseURL: uri,
        timeout: 10000,
        headers: {
            "Content-Type": contentType || "application/json",
        },
    });
};