import { useState } from "react";
import { addExpense } from "../../services/expenseService";

const ExpenseForm = ({ onSuccess }) => {
  const [form, setForm] = useState({
    amount: "",
    category: "GROCERIES",
    description: "",
    expenseDate: "",
  });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      await addExpense(form);
      onSuccess();
      setForm({
        amount: "",
        category: "GROCERIES",
        description: "",
        expenseDate: "",
      });
    } catch (err) {
      alert("Failed to add expense");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card p-3 mb-4">
      <h5 className="fw-semibold mb-3">Add Expense</h5>

      <form className="row g-3" onSubmit={handleSubmit}>
        <div className="col-md-2">
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

        <div className="col-md-3">
          <input
            name="description"
            className="form-control"
            placeholder="Description (optional)"
            value={form.description}
            onChange={handleChange}
          />
        </div>

        <div className="col-md-2">
          <input
            type="date"
            name="expenseDate"
            className="form-control"
            value={form.expenseDate}
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

export default ExpenseForm;
