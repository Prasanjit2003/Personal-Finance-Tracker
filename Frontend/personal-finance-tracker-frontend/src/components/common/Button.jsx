const Button = ({
  children,
  variant = "primary",
  type = "button",
  loading = false,
  ...props
}) => {
  return (
    <button
      type={type}
      className={`btn btn-${variant}`}
      disabled={loading}
      {...props}
    >
      {loading ? "Please wait..." : children}
    </button>
  );
};

export default Button;
