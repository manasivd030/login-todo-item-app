# Fullstack Login App

This project is a full-stack application with:
- **Frontend:** React
- **Backend:** Node.js + Express
- **Tests:** REST Assured using Java + TestNG + Maven

---

## 🔧 Folder Structure

```
Login/
├── backend/                  # Node.js + Express backend
├── frontend/                 # React frontend
└── simple-todo-api-tests/    # REST Assured test cases
```

---

## ⚙️ Prerequisites

- Node.js (v18+)
- npm
- Java 11+
- Maven
- Git

---

## 🚀 Setup Instructions

### 1. Clone the repo

```bash
git clone https://github.com/<your-username>/fullstack-login-app.git
cd fullstack-login-app
```

---

### 2. Start the Backend

```bash
cd backend
npm install
node index.js
```

Server starts on **http://localhost:4000**

---

### 3. Start the Frontend

In a new terminal:

```bash
cd frontend
npm install
npm start
```

App opens on **http://localhost:3000**

---

### 4. Run REST Assured Tests

In a new terminal:

```bash
cd simple-todo-api-tests
mvn clean test
```

✔️ Tests will hit the running backend at `http://localhost:4000`.

---

## 🧪 Features

- Login with validation
- Add / Edit / Delete items
- REST API automated test suite

---

## 🙌 Author

Built by [ Mansi Vaidya ] 
