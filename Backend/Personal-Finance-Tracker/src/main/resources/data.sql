-- =========================
-- INCOMES (2025)
-- =========================
INSERT INTO incomes (
    id, amount, source, frequency, income_date, user_id
)
SELECT 1, 50000, 'Salary', 'MONTHLY', '2025-12-01', 1
WHERE NOT EXISTS (SELECT 1 FROM incomes WHERE id = 1);

INSERT INTO incomes (
    id, amount, source, frequency, income_date, user_id
)
SELECT 2, 8000, 'Freelancing', 'MONTHLY', '2025-12-15', 1
WHERE NOT EXISTS (SELECT 1 FROM incomes WHERE id = 2);

-- =========================
-- EXPENSES (2025)
-- =========================
INSERT INTO expenses (
    id, amount, category, description, expense_date, user_id
)
SELECT 1, 12000, 'GROCERIES', 'Monthly groceries', '2025-12-05', 1
WHERE NOT EXISTS (SELECT 1 FROM expenses WHERE id = 1);

INSERT INTO expenses (
    id, amount, category, description, expense_date, user_id
)
SELECT 2, 6000, 'RENT', 'House rent', '2025-12-01', 1
WHERE NOT EXISTS (SELECT 1 FROM expenses WHERE id = 2);

INSERT INTO expenses (
    id, amount, category, description, expense_date, user_id
)
SELECT 3, 3000, 'OTHER', 'Fuel & travel', '2025-12-10', 1
WHERE NOT EXISTS (SELECT 1 FROM expenses WHERE id = 3);

-- =========================
-- TRANSACTIONS (2025)
-- =========================
INSERT INTO transactions (
    id, type, amount, category, transaction_date, user_id
)
SELECT 1, 'INCOME', 50000, 'Salary', '2025-12-01 09:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM transactions WHERE id = 1);

INSERT INTO transactions (
    id, type, amount, category, transaction_date, user_id
)
SELECT 2, 'INCOME', 8000, 'Freelancing', '2025-12-15 11:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM transactions WHERE id = 2);

INSERT INTO transactions (
    id, type, amount, category, transaction_date, user_id
)
SELECT 3, 'EXPENSE', 12000, 'GROCERIES', '2025-12-05 18:30:00', 1
WHERE NOT EXISTS (SELECT 1 FROM transactions WHERE id = 3);

INSERT INTO transactions (
    id, type, amount, category, transaction_date, user_id
)
SELECT 4, 'EXPENSE', 6000, 'RENT', '2025-12-01 10:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM transactions WHERE id = 4);

-- =========================
-- BUDGETS (2025)
-- =========================
INSERT INTO budgets (
    id, category, month, monthly_limit, user_id
)
SELECT 1, 'GROCERIES', '2025-12', 15000, 1
WHERE NOT EXISTS (SELECT 1 FROM budgets WHERE id = 1);

INSERT INTO budgets (
    id, category, month, monthly_limit, user_id
)
SELECT 2, 'RENT', '2025-12', 7000, 1
WHERE NOT EXISTS (SELECT 1 FROM budgets WHERE id = 2);

-- =========================
-- INCOMES (2024–2025)
-- =========================
INSERT INTO incomes (id, amount, source, frequency, income_date, user_id)
SELECT 10, 42000, 'Salary', 'MONTHLY', '2024-01-01', 1
WHERE NOT EXISTS (SELECT 1 FROM incomes WHERE id = 10);

INSERT INTO incomes (id, amount, source, frequency, income_date, user_id)
SELECT 11, 45000, 'Salary', 'MONTHLY', '2024-06-01', 1
WHERE NOT EXISTS (SELECT 1 FROM incomes WHERE id = 11);

INSERT INTO incomes (id, amount, source, frequency, income_date, user_id)
SELECT 12, 48000, 'Salary', 'MONTHLY', '2024-12-01', 1
WHERE NOT EXISTS (SELECT 1 FROM incomes WHERE id = 12);

INSERT INTO incomes (id, amount, source, frequency, income_date, user_id)
SELECT 13, 50000, 'Salary', 'MONTHLY', '2025-01-01', 1
WHERE NOT EXISTS (SELECT 1 FROM incomes WHERE id = 13);

INSERT INTO incomes (id, amount, source, frequency, income_date, user_id)
SELECT 14, 52000, 'Salary', 'MONTHLY', '2025-06-01', 1
WHERE NOT EXISTS (SELECT 1 FROM incomes WHERE id = 14);

INSERT INTO incomes (id, amount, source, frequency, income_date, user_id)
SELECT 15, 55000, 'Salary', 'MONTHLY', '2025-12-01', 1
WHERE NOT EXISTS (SELECT 1 FROM incomes WHERE id = 15);

-- =========================
-- EXPENSES (2024–2025)
-- =========================
INSERT INTO expenses (id, amount, category, description, expense_date, user_id)
SELECT 20, 9000, 'GROCERIES', 'Groceries', '2024-01-10', 1
WHERE NOT EXISTS (SELECT 1 FROM expenses WHERE id = 20);

INSERT INTO expenses (id, amount, category, description, expense_date, user_id)
SELECT 21, 6000, 'RENT', 'House rent', '2024-06-05', 1
WHERE NOT EXISTS (SELECT 1 FROM expenses WHERE id = 21);

INSERT INTO expenses (id, amount, category, description, expense_date, user_id)
SELECT 22, 3000, 'OTHER', 'Travel', '2024-12-12', 1
WHERE NOT EXISTS (SELECT 1 FROM expenses WHERE id = 22);

INSERT INTO expenses (id, amount, category, description, expense_date, user_id)
SELECT 23, 11000, 'GROCERIES', 'Groceries', '2025-01-10', 1
WHERE NOT EXISTS (SELECT 1 FROM expenses WHERE id = 23);

INSERT INTO expenses (id, amount, category, description, expense_date, user_id)
SELECT 24, 7000, 'RENT', 'House rent', '2025-06-05', 1
WHERE NOT EXISTS (SELECT 1 FROM expenses WHERE id = 24);

INSERT INTO expenses (id, amount, category, description, expense_date, user_id)
SELECT 25, 4000, 'ENTERTAINMENT', 'Movies & outings', '2025-12-15', 1
WHERE NOT EXISTS (SELECT 1 FROM expenses WHERE id = 25);

-- =========================
-- TRANSACTIONS (2024–2025)
-- =========================
INSERT INTO transactions (id, type, amount, category, transaction_date, user_id)
SELECT 30, 'INCOME', 42000, 'Salary', '2024-01-01 09:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM transactions WHERE id = 30);

INSERT INTO transactions (id, type, amount, category, transaction_date, user_id)
SELECT 31, 'EXPENSE', 9000, 'GROCERIES', '2024-01-10 18:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM transactions WHERE id = 31);

INSERT INTO transactions (id, type, amount, category, transaction_date, user_id)
SELECT 32, 'INCOME', 48000, 'Salary', '2024-12-01 09:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM transactions WHERE id = 32);

INSERT INTO transactions (id, type, amount, category, transaction_date, user_id)
SELECT 33, 'INCOME', 50000, 'Salary', '2025-01-01 09:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM transactions WHERE id = 33);

INSERT INTO transactions (id, type, amount, category, transaction_date, user_id)
SELECT 34, 'EXPENSE', 11000, 'GROCERIES', '2025-01-10 18:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM transactions WHERE id = 34);

INSERT INTO transactions (id, type, amount, category, transaction_date, user_id)
SELECT 35, 'INCOME', 55000, 'Salary', '2025-12-01 09:00:00', 1
WHERE NOT EXISTS (SELECT 1 FROM transactions WHERE id = 35);

-- =========================
-- BUDGETS (2024–2025)
-- =========================
INSERT INTO budgets (id, category, month, monthly_limit, user_id)
SELECT 40, 'GROCERIES', '2024-12', 12000, 1
WHERE NOT EXISTS (SELECT 1 FROM budgets WHERE id = 40);

INSERT INTO budgets (id, category, month, monthly_limit, user_id)
SELECT 41, 'GROCERIES', '2025-01', 15000, 1
WHERE NOT EXISTS (SELECT 1 FROM budgets WHERE id = 41);

INSERT INTO budgets (id, category, month, monthly_limit, user_id)
SELECT 42, 'RENT', '2025-06', 8000, 1
WHERE NOT EXISTS (SELECT 1 FROM budgets WHERE id = 42);

-- =========================
-- Forcast Dummy Data
-- =========================
INSERT INTO transactions (amount, type, category, transaction_date, user_id)
VALUES
(10000, 'INCOME', 'Salary',  '2025-01-05 10:00:00', 1),
(12000, 'INCOME', 'Salary',  '2025-02-05 10:00:00', 1),
(11000, 'INCOME', 'Salary',  '2025-03-05 10:00:00', 1),

(5000,  'EXPENSE', 'Food',   '2025-01-10 18:00:00', 1),
(6000,  'EXPENSE', 'Travel', '2025-02-10 18:00:00', 1),
(5500,  'EXPENSE', 'Food',   '2025-03-10 18:00:00', 1);

