import useTheme from "../../hooks/useTheme";

const ThemeToggle = () => {
  const { theme, toggleTheme } = useTheme();

  return (
    <button
      className="btn btn-outline-secondary"
      onClick={toggleTheme}
      title="Toggle theme"
    >
      <i
        className={`bi ${
          theme === "light" ? "bi-moon-fill" : "bi-sun-fill"
        }`}
      ></i>
    </button>
  );
};

export default ThemeToggle;
