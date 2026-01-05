import api from "./api";

export const registerUser = (data) => {
  return api.post("/api/auth/register", data);
};

export const verifyOtp = (data) => {
  return api.post("/api/auth/verify-otp", data);
};

export const loginUser = (data) => {
  return api.post("/api/auth/login", data);
};
