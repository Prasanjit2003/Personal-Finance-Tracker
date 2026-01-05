import api from "./api";

export const getTransactions = (params) => {
  return api.get("/api/transactions", { params });
};
