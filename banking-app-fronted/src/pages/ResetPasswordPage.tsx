import { useLocation } from "react-router-dom";
import ResetPasswordForm from "../components/ResetPasswordPage/ResetPasswordForm";

function ResetPasswordPage(){

    const location = useLocation();
    const email = location.state?.email;

    return (
        <div className="flex items-center justify-center min-h-screen px-4 p-8 bg-slate-50">
            <ResetPasswordForm email={email} />
        </div>
    );

}

export default ResetPasswordPage;