import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

const ForecastChart = ({ title, data }) => {
  if (!data || data.length === 0) {
    return <p className="text-muted">Not enough data for forecast</p>;
  }

  return (
    <div className="card p-3 h-100">
      <h6 className="fw-semibold mb-3">{title}</h6>

      <ResponsiveContainer width="100%" height={250}>
        <LineChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="period" />
          <YAxis />
          <Tooltip />
          <Line
            type="monotone"
            dataKey="predictedAmount"
            stroke="#0d6efd"
            strokeWidth={2}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default ForecastChart;
