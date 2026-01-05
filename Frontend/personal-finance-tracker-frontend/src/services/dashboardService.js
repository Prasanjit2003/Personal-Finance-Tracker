import api from "./api";

export const getDashboardSummary = () => {
  return api.get("/api/dashboard/summary");
};

export const getMonthlyComparison = () => {
  return api.get("/api/dashboard/monthly-comparison");
};

export const getExpenseByCategory = () => {
  return api.get("/api/dashboard/expense-by-category");
};
