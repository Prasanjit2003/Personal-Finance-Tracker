import { useEffect, useState } from "react";
import { getExpenseByCategory } from "../../services/dashboardService";
import {
  PieChart,
  Pie,
  Tooltip,
  ResponsiveContainer,
  Cell,
} from "recharts";

const COLORS = [
  "#0d6efd",
  "#198754",
  "#ffc107",
  "#dc3545",
  "#6f42c1",
  "#20c997",
];

const CategoryPie = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await getExpenseByCategory();
        setData(res.data);
      } catch (err) {
        console.error("Failed to load category expense", err);
      }
    };

    fetchData();
  }, []);

  return (
    <div className="card p-3 h-100">
      <h6 className="fw-semibold mb-3">Expense by Category</h6>

      <ResponsiveContainer width="100%" height={300}>
        <PieChart>
          <Pie
            data={data}
            dataKey="amount"
            nameKey="category"
            outerRadius={100}
            label
          >
            {data.map((_, index) => (
              <Cell
                key={index}
                fill={COLORS[index % COLORS.length]}
              />
            ))}
          </Pie>
          <Tooltip />
        </PieChart>
      </ResponsiveContainer>
    </div>
  );
};

export default CategoryPie;
