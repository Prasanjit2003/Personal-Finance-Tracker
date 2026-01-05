import api from "./api";

export const addExpense = (data) => {
  return api.post("/api/expenses", data);
};

export const getExpensesPaged = (params) => {
  return api.get("/api/expenses/page", { params });
};

export const deleteExpense = (id) => {
  return api.delete(`/api/expenses/${id}`);
};
