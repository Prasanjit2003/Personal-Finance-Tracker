import { useEffect, useState } from "react";
import MonthlyChart from "./MonthlyChart";
import CategoryPie from "./CategoryPie";
import ForecastChart from "./ForecastChart";
import TrendBadge from "./TrendBadge";
import InsightCard from "./InsightCard";
import { getForecast, getTrend } from "../../services/forecastService";
import {
  exportYearlyComparisonCsv,
  exportYearlyComparisonExcel,
  exportMonthlyComparisonCsv,
  exportMonthlyComparisonExcel,
} from "../../services/exportService";

const ChartsSection = () => {
  const [incomeForecast, setIncomeForecast] = useState([]);
  const [expenseForecast, setExpenseForecast] = useState([]);
  const [incomeTrend, setIncomeTrend] = useState(null);
  const [expenseTrend, setExpenseTrend] = useState(null);

  useEffect(() => {
    getForecast("INCOME", 3).then((res) => setIncomeForecast(res.data));
    getForecast("EXPENSE", 3).then((res) => setExpenseForecast(res.data));
    getTrend("INCOME").then((res) => setIncomeTrend(res.data));
    getTrend("EXPENSE").then((res) => setExpenseTrend(res.data));
  }, []);

  /* ======================
     UTILS
  ====================== */

  const sumForecast = (list) =>
    list.reduce((sum, i) => sum + Number(i.predictedAmount || 0), 0);

  const incomeSum = sumForecast(incomeForecast);
  const expenseSum = sumForecast(expenseForecast);

  const downloadFile = (blob, filename) => {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = filename;
    a.click();
    window.URL.revokeObjectURL(url);
  };

  /* ======================
     EXPORT HANDLERS
  ====================== */

  const handleYearlyCsv = async () => {
    const res = await exportYearlyComparisonCsv();
    downloadFile(res.data, "yearly-comparison.csv");
  };

  const handleYearlyExcel = async () => {
    const res = await exportYearlyComparisonExcel();
    downloadFile(res.data, "yearly-comparison.xlsx");
  };

  const handleMonthlyCsv = async () => {
    const res = await exportMonthlyComparisonCsv();
    downloadFile(res.data, "monthly-comparison.csv");
  };

  const handleMonthlyExcel = async () => {
    const res = await exportMonthlyComparisonExcel();
    downloadFile(res.data, "monthly-comparison.xlsx");
  };

  return (
    <>
      {/* ======================
          EXPORT TOOLBAR
      ====================== */}
      <div className="d-flex justify-content-end flex-wrap gap-2 mb-3">
        <div className="btn-group">
          <button className="btn btn-outline-primary btn-sm" disabled>
            Yearly
          </button>
          <button
            className="btn btn-outline-primary btn-sm"
            onClick={handleYearlyCsv}
          >
            CSV
          </button>
          <button
            className="btn btn-outline-primary btn-sm"
            onClick={handleYearlyExcel}
          >
            Excel
          </button>
        </div>
      </div>

      {/* ======================
          AI INSIGHTS
      ====================== */}
      <div className="row g-3 mb-4">
        {incomeTrend && (
          <div className="col-12 col-md-6">
            <InsightCard
              title="Income Insight"
              icon="bi-graph-up-arrow"
              variant={
                incomeTrend.trend === "UP"
                  ? "success"
                  : incomeTrend.trend === "DOWN"
                  ? "warning"
                  : "info"
              }
              text={
                incomeTrend.trend === "UP"
                  ? `Your income is trending upward. If this continues, you may earn approximately ₹${incomeSum.toLocaleString()} over the next 3 months.`
                  : incomeTrend.trend === "DOWN"
                  ? "Your income is declining. Consider diversifying or adding new income sources."
                  : "Your income has remained stable over recent months."
              }
            />
          </div>
        )}

        {expenseTrend && (
          <div className="col-12 col-md-6">
            <InsightCard
              title="Expense Insight"
              icon="bi-cash-stack"
              variant={
                expenseTrend.trend === "DOWN"
                  ? "success"
                  : expenseTrend.trend === "UP"
                  ? "danger"
                  : "info"
              }
              text={
                expenseTrend.trend === "DOWN"
                  ? `Good job! Your expenses are decreasing. Estimated spend for next 3 months is ₹${expenseSum.toLocaleString()}.`
                  : expenseTrend.trend === "UP"
                  ? "Your expenses are increasing. Consider setting stricter budgets."
                  : "Your expenses are stable with no major fluctuations."
              }
            />
          </div>
        )}
      </div>

      {/* ======================
          CHARTS
      ====================== */}
      <div className="row g-3 mt-2">
        <div className="col-12 col-lg-6">
          <MonthlyChart />
        </div>

        <div className="col-12 col-lg-6">
          <CategoryPie />
        </div>

        <div className="col-12 col-lg-6">
          <ForecastChart
            title="Income Forecast (Next 3 Months)"
            data={incomeForecast}
          />
          {incomeTrend && (
            <div className="mt-2">
              <TrendBadge label="Income Trend" trend={incomeTrend.trend} />
            </div>
          )}
        </div>

        <div className="col-12 col-lg-6">
          <ForecastChart
            title="Expense Forecast (Next 3 Months)"
            data={expenseForecast}
          />
          {expenseTrend && (
            <div className="mt-2">
              <TrendBadge label="Expense Trend" trend={expenseTrend.trend} />
            </div>
          )}
        </div>
      </div>
    </>
  );
};

export default ChartsSection;
