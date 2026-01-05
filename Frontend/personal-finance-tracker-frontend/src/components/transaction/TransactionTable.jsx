import { useEffect, useState } from "react";
import { getTransactions } from "../../services/transactionService";
import {
  exportTransactionsCsv,
  exportTransactionsExcel,
} from "../../services/exportService";

const TransactionTable = () => {
  const [data, setData] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const [type, setType] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  const loadData = async () => {
    try {
      const res = await getTransactions({
        page,
        size: 10,
        type: type || undefined,
        startDate: startDate || undefined,
        endDate: endDate || undefined,
      });

      setData(res.data.content);
      setTotalPages(res.data.totalPages);
    } catch (err) {
      console.error("Failed to load transactions");
    }
  };

  useEffect(() => {
    loadData();
  }, [page, type, startDate, endDate]);

  // DOWNLOAD HANDLER
  const downloadFile = (blob, filename) => {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = filename;
    a.click();
    window.URL.revokeObjectURL(url);
  };

  const handleExportCsv = async () => {
    const res = await exportTransactionsCsv();
    downloadFile(res.data, "transactions.csv");
  };

  const handleExportExcel = async () => {
    const res = await exportTransactionsExcel();
    downloadFile(res.data, "transactions.xlsx");
  };

  return (
    <div className="card p-3">
      {/* Filters */}
      <div className="row g-2 mb-3">
        <div className="col-md-3">
          <select
            className="form-select"
            value={type}
            onChange={(e) => setType(e.target.value)}
          >
            <option value="">All Types</option>
            <option value="INCOME">Income</option>
            <option value="EXPENSE">Expense</option>
          </select>
        </div>

        <div className="col-md-3">
          <input
            type="date"
            className="form-control"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
          />
        </div>

        <div className="col-md-3">
          <input
            type="date"
            className="form-control"
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
          />
        </div>

        <div className="col-md-3 d-grid">
          <button
            className="btn btn-outline-secondary"
            onClick={() => {
              setType("");
              setStartDate("");
              setEndDate("");
              setPage(0);
            }}
          >
            Clear Filters
          </button>
        </div>
      </div>

      {/* EXPORT BUTTONS */}
      <div className="d-flex gap-2 mb-3">
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

      {/* Table */}
      <div className="table-responsive">
        <table className="table table-hover">
          <thead>
            <tr>
              <th>Type</th>
              <th>Amount</th>
              <th>Category</th>
              <th>Date</th>
            </tr>
          </thead>
          <tbody>
            {data.map((t) => (
              <tr key={t.id}>
                <td>
                  <span
                    className={`badge ${
                      t.type === "INCOME"
                        ? "bg-success"
                        : "bg-danger"
                    }`}
                  >
                    {t.type}
                  </span>
                </td>
                <td>₹ {t.amount}</td>
                <td>{t.category || "-"}</td>
                <td>
                  {new Date(t.transactionDate).toLocaleDateString()}
                </td>
              </tr>
            ))}

            {data.length === 0 && (
              <tr>
                <td colSpan="4" className="text-center text-muted">
                  No transactions found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {/* Pagination */}
      <div className="d-flex justify-content-between align-items-center mt-3">
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

export default TransactionTable;
