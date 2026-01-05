import { Link } from "react-router-dom";
import { useEffect, useState } from "react";

const Sidebar = ({ open, onClose }) => {
  const [isDesktop, setIsDesktop] = useState(window.innerWidth >= 992);

  useEffect(() => {
    const handleResize = () => {
      setIsDesktop(window.innerWidth >= 992);
    };
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  const handleLinkClick = () => {
    if (!isDesktop) {
      onClose();
    }
  };

  return (
    <>
      {/* Overlay for mobile only */}
      {!isDesktop && open && (
        <div
          className="position-fixed top-0 start-0 w-100 h-100 bg-dark opacity-50"
          onClick={onClose}
          style={{ zIndex: 1040 }}
        />
      )}

      <aside
        className={`sidebar ${open ? "open" : ""}`}
        style={{ zIndex: 1050 }}
      >
        <h5 className="fw-bold mb-4">
          <i className="bi bi-wallet2 me-2"></i>
          Finance Tracker
        </h5>

        <ul className="nav flex-column gap-2">
          <li className="nav-item">
            <Link to="/dashboard" className="nav-link" onClick={handleLinkClick}>
              <i className="bi bi-speedometer2 me-2"></i> Dashboard
            </Link>
          </li>
          <li className="nav-item">
            <Link to="/income" className="nav-link" onClick={handleLinkClick}>
              <i className="bi bi-cash-stack me-2"></i> Income
            </Link>
          </li>
          <li className="nav-item">
            <Link to="/expense" className="nav-link" onClick={handleLinkClick}>
              <i className="bi bi-receipt me-2"></i> Expense
            </Link>
          </li>
          <li className="nav-item">
            <Link to="/budget" className="nav-link" onClick={handleLinkClick}>
              <i className="bi bi-pie-chart me-2"></i> Budget
            </Link>
          </li>
          <li className="nav-item">
            <Link
              to="/transactions"
              className="nav-link"
              onClick={handleLinkClick}
            >
              <i className="bi bi-list-ul me-2"></i> Transactions
            </Link>
          </li>
        </ul>
      </aside>
    </>
  );
};

export default Sidebar;
