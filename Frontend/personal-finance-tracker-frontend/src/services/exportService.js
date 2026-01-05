import api from "./api";

// ===== TRANSACTION EXPORT =====
export const exportTransactionsCsv = () => {
  return api.get("/api/export/transactions/csv", {
    responseType: "blob",
  });
};

export const exportTransactionsExcel = () => {
  return api.get("/api/export/transactions/excel", {
    responseType: "blob",
  });
};

// ===== INCOME EXPORT =====
export const exportIncomeCsv = () => {
  return api.get("/api/export/income/csv", {
    responseType: "blob",
  });
};

export const exportIncomeExcel = () => {
  return api.get("/api/export/income/excel", {
    responseType: "blob",
  });
};

// ===== EXPENSE EXPORT =====
export const exportExpensesCsv = () => {
  return api.get("/api/export/expenses/csv", {
    responseType: "blob",
  });
};

export const exportExpensesExcel = () => {
  return api.get("/api/export/expenses/excel", {
    responseType: "blob",
  });
};

// ===== DASHBOARD EXPORT =====
export const exportYearlyComparisonCsv = () => {
  return api.get("/api/export/yearly-comparison/csv", {
    responseType: "blob",
  });
};

export const exportYearlyComparisonExcel = () => {
  return api.get("/api/export/yearly-comparison/excel", {
    responseType: "blob",
  });
};

export const exportMonthlyComparisonCsv = () => {
  return api.get("/api/export/monthly-comparison/csv", {
    responseType: "blob",
  });
};

export const exportMonthlyComparisonExcel = () => {
  return api.get("/api/export/monthly-comparison/excel", {
    responseType: "blob",
  });
};