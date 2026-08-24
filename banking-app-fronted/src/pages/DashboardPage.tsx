import { Dashboard } from "../components/DashboardPage/Dashboard";
import { FooterPage } from "./FooterPage";
import { HeaderPage } from "./HeaderPage";

function DashboardPage() {

  return (
    <>
      <HeaderPage />

      <Dashboard />
      
      <FooterPage />
    </>
  );
}

export default DashboardPage;
