import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import { useEffect, useState } from "react";

const AppLayout = ({ children }) => {
  const [sidebarOpen, setSidebarOpen] = useState(
    window.innerWidth >= 992
  );

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth < 992) {
        setSidebarOpen(false);
      }
    };

    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  return (
    <div className="d-flex">
      <Sidebar open={sidebarOpen} onClose={() => setSidebarOpen(false)} />

      <div className="flex-grow-1 main-content">
        <Navbar onMenuClick={() => setSidebarOpen((prev) => !prev)} />
        <main className="container-fluid mt-4">{children}</main>
      </div>
    </div>
  );
};

export default AppLayout;
