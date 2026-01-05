import { useEffect, useState } from "react";
import { deleteBudget, getBudgets } from "../../services/budgetService";

const BudgetProgress = ({ refresh }) => {
  const [budgets, setBudgets] = useState([]);

  const loadBudgets = async () => {
    try {
      const res = await getBudgets();
      setBudgets(res.data);
    } catch (err) {
      console.error("Failed to load budgets");
    }
  };

  useEffect(() => {
    loadBudgets();
  }, [refresh]);

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this budget?")) return;
    await deleteBudget(id);
    loadBudgets();
  };

  return (
    <div className="row g-3">
      {budgets.map((b) => {
        const percent =
          b.monthlyLimit > 0
            ? Math.min((b.spentAmount / b.monthlyLimit) * 100, 100)
            : 0;

        return (
          <div className="col-12 col-md-6 col-lg-4" key={b.id}>
            <div className="card p-3 h-100">
              <div className="d-flex justify-content-between align-items-center mb-2">
                <h6 className="fw-semibold mb-0">{b.category}</h6>
                <button
                  className="btn btn-sm btn-outline-danger"
                  onClick={() => handleDelete(b.id)}
                >
                  <i className="bi bi-trash"></i>
                </button>
              </div>

              <p className="mb-1 text-muted">
                Month: <strong>{b.month}</strong>
              </p>

              <div className="progress mb-2" style={{ height: "8px" }}>
                <div
                  className={`progress-bar ${
                    percent >= 90 ? "bg-danger" : "bg-success"
                  }`}
                  style={{ width: `${percent}%` }}
                ></div>
              </div>

              <small className="text-muted">
                Spent ₹{b.spentAmount} / ₹{b.monthlyLimit}
              </small>
              <br />
              <small
                className={
                  b.remainingAmount < 0 ? "text-danger" : "text-success"
                }
              >
                Remaining: ₹{b.remainingAmount}
              </small>
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default BudgetProgress;
