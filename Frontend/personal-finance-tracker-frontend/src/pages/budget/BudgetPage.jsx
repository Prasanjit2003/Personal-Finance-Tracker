import { useState } from "react";
import AppLayout from "../../components/layout/AppLayout";
import BudgetForm from "../../components/budget/BudgetForm";
import BudgetProgress from "../../components/budget/BudgetProgress";

const BudgetPage = () => {
  const [refresh, setRefresh] = useState(false);

  return (
    <AppLayout>
      <h2 className="fw-bold mb-3">Budget</h2>

      <BudgetForm onSuccess={() => setRefresh(!refresh)} />
      <BudgetProgress refresh={refresh} />
    </AppLayout>
  );
};

export default BudgetPage;
