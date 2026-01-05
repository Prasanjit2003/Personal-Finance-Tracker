import ThemeToggle from "../common/ThemeToggle";
import useAuth from "../../hooks/useAuth";

const Navbar = ({ onMenuClick }) => {
  const { logout } = useAuth();

  return (
    <nav className="navbar border-bottom px-3">
      {}
      <button
        className="btn btn-outline-secondary"
        onClick={onMenuClick}
      >
        <i className="bi bi-list"></i>
      </button>

      <div className="ms-auto d-flex align-items-center gap-3">
        <ThemeToggle />

        <button className="btn btn-outline-danger" onClick={logout}>
          <i className="bi bi-box-arrow-right"></i>
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
