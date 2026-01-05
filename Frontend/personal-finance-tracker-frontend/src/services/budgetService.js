import api from "./api";

export const saveBudget = (data) => {
  return api.post("/api/budgets", data);
};

export const getBudgets = () => {
  return api.get("/api/budgets");
};

export const deleteBudget = (id) => {
  return api.delete(`/api/budgets/${id}`);
};
