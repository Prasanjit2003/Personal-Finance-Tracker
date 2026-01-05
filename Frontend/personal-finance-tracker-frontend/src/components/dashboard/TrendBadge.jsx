const TrendBadge = ({ label, trend }) => {
  const color =
    trend === "UP"
      ? "success"
      : trend === "DOWN"
      ? "danger"
      : "secondary";

  return (
    <span className={`badge bg-${color}`}>
      {label}: {trend}
    </span>
  );
};

export default TrendBadge;
