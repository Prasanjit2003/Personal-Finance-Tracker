import api from "./api";

export const getForecast = (type, months = 3) => {
  return api.get("/api/forecast", {
    params: { type, months },
  });
};

export const getTrend = (type) => {
  return api.get("/api/forecast/trend", {
    params: { type },
  });
};
