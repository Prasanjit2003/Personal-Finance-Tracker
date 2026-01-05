import api from "./api";

export const addIncome = (data) => {
  return api.post("/api/incomes", data);
};

export const getIncomesPaged = (params) => {
  return api.get("/api/incomes/page", { params });
};

export const deleteIncome = (id) => {
  return api.delete(`/api/incomes/${id}`);
};
