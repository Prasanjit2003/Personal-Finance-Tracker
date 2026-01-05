import { useState } from "react";
import { saveBudget } from "../../services/budgetService";

const BudgetForm = ({ onSuccess }) => {
  const [form, setForm] = useState({
    category: "GROCERIES",
    monthlyLimit: "",
    month: "",
  });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      await saveBudget(form);
      onSuccess();
      setForm({
        category: "GROCERIES",
        monthlyLimit: "",
        month: "",
      });
    } catch (err) {
      alert("Failed to save budget");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card p-3 mb-4">
      <h5 className="fw-semibold mb-3">Set Monthly Budget</h5>

      <form className="row g-3" onSubmit={handleSubmit}>
        <div className="col-md-4">
          <select
            name="category"
            className="form-select"
            value={form.category}
            onChange={handleChange}
          >
            <option value="GROCERIES">Groceries</option>
            <option value="UTILITIES">Utilities</option>
            <option value="TRANSPORTATION">Transportation</option>
            <option value="ENTERTAINMENT">Entertainment</option>
            <option value="HEALTH">Health</option>
            <option value="SHOPPING">Shopping</option>
            <option value="EDUCATION">Education</option>
            <option value="RENT">Rent</option>
            <option value="OTHER">Other</option>
          </select>
        </div>

        <div className="col-md-4">
          <input
            type="number"
            name="monthlyLimit"
            className="form-control"
            placeholder="Monthly Limit"
            value={form.monthlyLimit}
            onChange={handleChange}
            required
          />
        </div>

        <div className="col-md-4">
          <input
            type="month"
            name="month"
            className="form-control"
            value={form.month}
            onChange={handleChange}
            required
          />
        </div>

        <div className="col-md-12 d-grid">
          <button className="btn btn-primary" disabled={loading}>
            {loading ? "Saving..." : "Save Budget"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default BudgetForm;
