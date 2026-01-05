export const formatDate = (date) => {
  if (!date) return "";
  return new Date(date).toLocaleDateString();
};

export const formatMonth = (month) => {
  if (!month) return "";
  return new Date(`${month}-01`).toLocaleString("default", {
    month: "long",
    year: "numeric",
  });
};
