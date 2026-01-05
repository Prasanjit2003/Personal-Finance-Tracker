import AppLayout from "../../components/layout/AppLayout";
import TransactionTable from "../../components/transaction/TransactionTable";

const TransactionsPage = () => {
  return (
    <AppLayout>
      <h2 className="fw-bold mb-3">Transactions</h2>
      <TransactionTable />
    </AppLayout>
  );
};

export default TransactionsPage;
