import { useEffect, useState } from "react";
import { getMonthlyComparison } from "../../services/dashboardService";
import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
} from "recharts";

const MonthlyChart = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await getMonthlyComparison();
        setData(res.data);
      } catch (err) {
        console.error("Failed to load monthly comparison", err);
      }
    };

    fetchData();
  }, []);

  return (
    <div className="card p-3 h-100">
      <h6 className="fw-semibold mb-3">Monthly Income vs Expense</h6>

      <ResponsiveContainer width="100%" height={300}>
        <LineChart data={data}>
          <XAxis dataKey="month" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Line type="monotone" dataKey="income" stroke="#198754" />
          <Line type="monotone" dataKey="expense" stroke="#dc3545" />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default MonthlyChart;
