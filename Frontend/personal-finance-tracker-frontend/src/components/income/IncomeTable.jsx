import { useEffect, useState } from "react";
import { deleteIncome, getIncomesPaged } from "../../services/incomeService";
import {
  exportIncomeCsv,
  exportIncomeExcel,
} from "../../services/exportService";

const IncomeTable = ({ refresh }) => {
  const [data, setData] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const loadData = async () => {
    try {
      const res = await getIncomesPaged({ page, size: 10 });
      setData(res.data.content);
      setTotalPages(res.data.totalPages);
    } catch (err) {
      console.error("Failed to load incomes");
    }
  };

  useEffect(() => {
    loadData();
  }, [page, refresh]);

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this income?")) return;

    await deleteIncome(id);
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
    const res = await exportIncomeCsv();
    downloadFile(res.data, "income.csv");
  };

  const handleExportExcel = async () => {
    const res = await exportIncomeExcel();
    downloadFile(res.data, "income.xlsx");
  };

  return (
    <div className="card p-3">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h5 className="fw-semibold mb-0">Income List</h5>

        {/* EXPORT BUTTONS */}
        <div className="d-flex gap-2">
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
              <th>Source</th>
              <th>Frequency</th>
              <th>Date</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {data.map((i) => (
              <tr key={i.id}>
                <td>₹ {i.amount}</td>
                <td>{i.source}</td>
                <td>{i.frequency}</td>
                <td>{i.incomeDate}</td>
                <td>
                  <button
                    className="btn btn-sm btn-outline-danger"
                    onClick={() => handleDelete(i.id)}
                  >
                    <i className="bi bi-trash"></i>
                  </button>
                </td>
              </tr>
            ))}

            {data.length === 0 && (
              <tr>
                <td colSpan="5" className="text-center text-muted">
                  No income records found
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

export default IncomeTable;
