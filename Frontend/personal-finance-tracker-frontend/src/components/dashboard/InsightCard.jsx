const InsightCard = ({ title, icon, text, variant = "info" }) => {
  const colorMap = {
    success: "border-success text-success",
    danger: "border-danger text-danger",
    warning: "border-warning text-warning",
    info: "border-primary text-primary",
  };

  return (
    <div className={`card p-3 h-100 ${colorMap[variant]}`}>
      <h6 className="fw-bold mb-2">
        <i className={`bi ${icon} me-2`}></i>
        {title}
      </h6>
      <p className="mb-0 small">{text}</p>
    </div>
  );
};

export default InsightCard;
