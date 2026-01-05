import { useEffect, useState } from "react";
import { deleteExpense, getExpensesPaged } from "../../services/expenseService";
import {
  exportExpensesCsv,
  exportExpensesExcel,
} from "../../services/exportService";

const ExpenseTable = ({ refresh }) => {
  const [data, setData] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [category, setCategory] = useState("");

  const loadData = async () => {
    try {
      const res = await getExpensesPaged({
        page,
        size: 10,
        category: category || undefined,
      });
      setData(res.data.content);
      setTotalPages(res.data.totalPages);
    } catch (err) {
      console.error("Failed to load expenses");
    }
  };

  useEffect(() => {
    loadData();
  }, [page, refresh, category]);

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this expense?")) return;
    await deleteExpense(id);
    loadData();
  };

  // COMMON DOWNLOAD HANDLER
  const downloadFile = (blob, filename) => {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = filename;
    a.click();
    window.URL.revokeObjectURL(url);
  };

  // EXPORT HANDLERS
  const handleExportCsv = async () => {
    const res = await exportExpensesCsv();
    downloadFile(res.data, "expenses.csv");
  };

  const handleExportExcel = async () => {
    const res = await exportExpensesExcel();
    downloadFile(res.data, "expenses.xlsx");
  };

  return (
    <div className="card p-3">
      <div className="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
        <h5 className="fw-semibold mb-0">Expense List</h5>

        <div className="d-flex gap-2 align-items-center">
          {/* CATEGORY FILTER */}
          <select
            className="form-select w-auto"
            value={category}
            onChange={(e) => {
              setCategory(e.target.value);
              setPage(0);
            }}
          >
            <option value="">All Categories</option>
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

          {/* EXPORT BUTTONS */}
          <button
            className="btn btn-outline-primary btn-sm"
            onClick={handleExportCsv}
          >
            Export CSV
          </button>
          <button
            className="btn btn-outline-success btn-sm"
            onClick={handleExportExcel}
          >
            Export Excel
          </button>
        </div>
      </div>

      <div className="table-responsive">
        <table className="table table-hover">
          <thead>
            <tr>
              <th>Amount</th>
              <th>Category</th>
              <th>Description</th>
              <th>Date</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {data.map((e) => (
              <tr key={e.id}>
                <td>₹ {e.amount}</td>
                <td>{e.category}</td>
                <td>{e.description || "-"}</td>
                <td>{e.expenseDate}</td>
                <td>
                  <button
                    className="btn btn-sm btn-outline-danger"
                    onClick={() => handleDelete(e.id)}
                  >
                    <i className="bi bi-trash"></i>
                  </button>
                </td>
              </tr>
            ))}

            {data.length === 0 && (
              <tr>
                <td colSpan="5" className="text-center text-muted">
                  No expense records found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {/* Pagination */}
      <div className="d-flex justify-content-between">
        <button
          className="btn btn-outline-secondary btn-sm"
          disabled={page === 0}
          onClick={() => setPage((p) => p - 1)}
        >
          Prev
        </button>
        <span>
          Page {page + 1} of {totalPages}
        </span>
        <button
          className="btn btn-outline-secondary btn-sm"
          disabled={page + 1 >= totalPages}
          onClick={() => setPage((p) => p + 1)}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default ExpenseTable;
