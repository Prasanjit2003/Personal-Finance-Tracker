import { useEffect, useState } from "react";
import { getDashboardSummary } from "../../services/dashboardService";

const SummaryCards = () => {
  const [summary, setSummary] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchSummary = async () => {
      try {
        const res = await getDashboardSummary();
        setSummary(res.data);
      } catch (err) {
        console.error("Failed to load dashboard summary", err);
      } finally {
        setLoading(false);
      }
    };

    fetchSummary();
  }, []);

  if (loading) {
    return <p className="text-muted">Loading summary...</p>;
  }

  return (
    <div className="row g-3">
      <div className="col-12 col-sm-6 col-lg-3">
        <div className="card p-3">
          <h6 className="text-muted">Total Income</h6>
          <h4 className="fw-bold text-success">
            ₹ {summary?.totalIncome ?? 0}
          </h4>
        </div>
      </div>

      <div className="col-12 col-sm-6 col-lg-3">
        <div className="card p-3">
          <h6 className="text-muted">Total Expense</h6>
          <h4 className="fw-bold text-danger">
            ₹ {summary?.totalExpense ?? 0}
          </h4>
        </div>
      </div>

      <div className="col-12 col-sm-6 col-lg-3">
        <div className="card p-3">
          <h6 className="text-muted">Balance</h6>
          <h4 className="fw-bold text-primary">
            ₹ {summary?.balance ?? 0}
          </h4>
        </div>
      </div>

      <div className="col-12 col-sm-6 col-lg-3">
        <div className="card p-3">
          <h6 className="text-muted">This Month Expense</h6>
          <h4 className="fw-bold text-warning">
            ₹ {summary?.currentMonthExpense ?? 0}
          </h4>
        </div>
      </div>
    </div>
  );
};

export default SummaryCards;
