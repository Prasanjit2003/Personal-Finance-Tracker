import { useState } from "react";
import AppLayout from "../../components/layout/AppLayout";
import ExpenseForm from "../../components/expense/ExpenseForm";
import ExpenseTable from "../../components/expense/ExpenseTable";

const ExpensePage = () => {
  const [refresh, setRefresh] = useState(false);

  return (
    <AppLayout>
      <h2 className="fw-bold mb-3">Expense</h2>

      <ExpenseForm onSuccess={() => setRefresh(!refresh)} />
      <ExpenseTable refresh={refresh} />
    </AppLayout>
  );
};

export default ExpensePage;
