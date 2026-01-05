import { useState } from "react";
import AppLayout from "../../components/layout/AppLayout";
import IncomeForm from "../../components/income/IncomeForm";
import IncomeTable from "../../components/income/IncomeTable";

const IncomePage = () => {
  const [refresh, setRefresh] = useState(false);

  return (
    <AppLayout>
      <h2 className="fw-bold mb-3">Income</h2>

      <IncomeForm onSuccess={() => setRefresh(!refresh)} />
      <IncomeTable refresh={refresh} />
    </AppLayout>
  );
};

export default IncomePage;
