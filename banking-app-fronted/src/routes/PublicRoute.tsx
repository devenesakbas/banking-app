import { Navigate, Outlet } from "react-router-dom";

export const PublicRoute = () => {

    const isAuthenticated = !!localStorage.getItem("accessToken");

    if(isAuthenticated){
        return <Navigate to="/dashboard" replace />
    }

    return <Outlet />

}