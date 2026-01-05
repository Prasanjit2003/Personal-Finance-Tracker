# 📊 Personal Finance Tracker (Full-Stack Application)

Personal Finance Tracker is a **full-stack web application** that helps users manage **budgets, expenses, incomes, and transactions** securely with JWT authentication and a modern UI.

This project is **public-repository safe**, follows **industry standards**, and is suitable for **portfolio and recruiter review**.

---

## 🔑 What This Project Does

- Secure user authentication using JWT
- Track monthly budgets by category
- Track expenses and incomes
- Automatically calculate:
  - Total spent
  - Remaining budget
- Maintain historical financial data
- Provide REST APIs with Swagger documentation
- Connect React frontend with Spring Boot backend

---

## 🧱 Tech Stack

### Backend
- Java 17+
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- MySQL
- Swagger / OpenAPI

### Frontend
- React (Vite)
- Axios
- React Router
- CSS

### Database
- MySQL

---

## 📁 Project Structure (High Level)

personal-finance-tracker/
├── backend → Spring Boot REST API
├── frontend → React UI
└── README.md

yaml
Copy code

---

## 🧑‍💻 Prerequisites (Must Be Installed)

- Java 17 or later
- Maven
- Node.js 18+
- npm
- MySQL
- Git

---

## 🗄️ Database Setup

### Step 1: Create Database

```sql
CREATE DATABASE financetracker_db;
Step 2: Important Schema Rule
Budget month is stored as TEXT, not binary

Format used:

yaml
Copy code
YYYY-MM   (example: 2025-12)
If needed, ensure:

sql
Copy code
ALTER TABLE budgets MODIFY month VARCHAR(7);
⚙️ Backend Configuration (Example)
properties
Copy code
spring.application.name=Personal-Finance-Tracker
server.port=8080

# DATABASE
spring.datasource.url=jdbc:mysql://localhost:3306/financetracker_db
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

# JPA / HIBERNATE
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.defer-datasource-initialization=true

# SQL INIT (safe, idempotent)
spring.sql.init.mode=always

# JWT
app.jwt.secret=CHANGE_ME_FOR_PRODUCTION
app.jwt.expiration=86400000

# SWAGGER
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true

# CORS
spring.mvc.cors.allowed-origins=http://localhost:5173
spring.mvc.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.mvc.cors.allowed-headers=*
spring.mvc.cors.allow-credentials=true
⚠️ Never commit real secrets to a public repository.

▶️ Running the Backend
bash
Copy code
cd backend
mvn spring-boot:run
Backend runs at:

dts
Copy code
http://localhost:8080
📘 API Documentation (Swagger)
Open in browser:

bash
Copy code
http://localhost:8080/swagger-ui/index.html
Using Secured APIs
Login

Copy JWT token

Click Authorize

Paste:

php-template
Copy code
Bearer <token>
🎨 Frontend Setup
Step 1: Install Dependencies
bash
Copy code
cd frontend
npm install
Step 2: Run Frontend
bash
Copy code
npm run dev
Frontend runs at:

dts
Copy code
http://localhost:5173
🔄 Frontend ↔ Backend Communication
Axios is used for API calls

JWT is sent via:

nix
Copy code
Authorization: Bearer <token>
CORS is enabled in backend

🧪 Demo / Seed Data
The project contains demo data for:

Budgets

Expenses

Incomes

Transactions

Data Safety
SQL is idempotent

Uses conditional inserts

No duplicate data on restart

Safe for development environments

🛠️ Common Problems & Fixes
❌ Error: SerializationException: invalid stream header
Cause: Database column stored as binary
Fix:

sql
Copy code
ALTER TABLE budgets MODIFY month VARCHAR(7);
Ensure entity field is:

java
Copy code
private String month;
❌ Swagger Not Opening
Ensure backend is running and these paths are allowed:

bash
Copy code
/swagger-ui/**
/v3/api-docs/**

❌ 401 Unauthorized in Frontend
User not logged in

JWT missing or expired

Authorization header not sent

🔐 Security Notes (Public Repo Safe)
No hardcoded credentials

JWT secret is configurable

Email passwords must use app passwords

Environment variables recommended for production

🚀 Future Enhancements
Analytics & charts

Export reports (PDF / Excel)

Recurring expenses

Multi-currency support

Docker deployment

📄 License
MIT License
Free to use, modify, and distribute.

👨‍💻 Author
Prasanjit Behera
Java | Spring Boot | React