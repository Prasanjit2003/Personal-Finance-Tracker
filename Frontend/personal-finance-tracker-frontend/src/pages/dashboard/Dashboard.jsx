import AppLayout from "../../components/layout/AppLayout";
import SummaryCards from "../../components/dashboard/SummaryCards";
import ChartsSection from "../../components/dashboard/ChartsSection";

const Dashboard = () => {
  return (
    <AppLayout>
      <div className="mb-4">
        <h2 className="fw-bold">Dashboard</h2>
        <p className="text-muted mb-0">
          Overview of your financial activities
        </p>
      </div>

      <SummaryCards />
      <ChartsSection />
    </AppLayout>
  );
};

export default Dashboard;
