import { useState } from "react";
import { addIncome } from "../../services/incomeService";

const IncomeForm = ({ onSuccess }) => {
  const [form, setForm] = useState({
    amount: "",
    source: "",
    frequency: "MONTHLY",
    incomeDate: "",
  });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      await addIncome(form);
      onSuccess();
      setForm({
        amount: "",
        source: "",
        frequency: "MONTHLY",
        incomeDate: "",
      });
    } catch (err) {
      alert("Failed to add income");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card p-3 mb-4">
      <h5 className="fw-semibold mb-3">Add Income</h5>

      <form onSubmit={handleSubmit} className="row g-3">
        <div className="col-md-3">
          <input
            type="number"
            name="amount"
            className="form-control"
            placeholder="Amount"
            value={form.amount}
            onChange={handleChange}
            required
          />
        </div>

        <div className="col-md-3">
          <input
            name="source"
            className="form-control"
            placeholder="Source (Salary, Freelance)"
            value={form.source}
            onChange={handleChange}
            required
          />
        </div>

        <div className="col-md-2">
          <select
            name="frequency"
            className="form-select"
            value={form.frequency}
            onChange={handleChange}
          >
            <option value="WEEKLY">Weekly</option>
            <option value="MONTHLY">Monthly</option>
            <option value="YEARLY">Yearly</option>
            <option value="ONE_TIME">One Time</option>
          </select>
        </div>

        <div className="col-md-2">
          <input
            type="date"
            name="incomeDate"
            className="form-control"
            value={form.incomeDate}
            onChange={handleChange}
            required
          />
        </div>

        <div className="col-md-2 d-grid">
          <button className="btn btn-primary" disabled={loading}>
            {loading ? "Saving..." : "Add"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default IncomeForm;
