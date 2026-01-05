import { Link } from "react-router-dom";
import ThemeToggle from "../../components/common/ThemeToggle";

const LandingPage = () => {
  return (
    <div className="min-vh-100 d-flex flex-column">
      {/* Navbar */}
      <nav className="navbar navbar-expand-lg px-4">
        <span className="navbar-brand fw-bold">
          <i className="bi bi-wallet2 me-2"></i>
          Finance Tracker
        </span>

        <div className="ms-auto d-flex gap-2">
          <ThemeToggle />
          <Link to="/login" className="btn btn-outline-primary">
            Login
          </Link>
          <Link to="/register" className="btn btn-primary">
            Register
          </Link>
        </div>
      </nav>

      {/* Hero Section */}
      <div className="container flex-grow-1 d-flex align-items-center">
        <div className="row align-items-center w-100">
          <div className="col-md-6 text-center text-md-start">
            <h1 className="fw-bold display-5">
              Manage Your Money <br /> Smarter & Faster
            </h1>
            <p className="lead mt-3">
              Track expenses, manage income, set budgets and gain insights into
              your financial life.
            </p>

            <div className="mt-4">
              <Link to="/register" className="btn btn-primary btn-lg me-3">
                Get Started
              </Link>
              <Link to="/login" className="btn btn-outline-secondary btn-lg">
                Login
              </Link>
            </div>
          </div>

          <div className="col-md-6 text-center mt-5 mt-md-0">
            <i
              className="bi bi-bar-chart-line"
              style={{ fontSize: "180px", opacity: 0.8 }}
            ></i>
          </div>
        </div>
      </div>

      {/* Footer */}
      <footer className="text-center py-3 small opacity-75">
        © 2025 Personal Finance Tracker
      </footer>
    </div>
  );
};

export default LandingPage;
