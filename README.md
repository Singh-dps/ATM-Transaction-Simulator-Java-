<div align="center">

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&color=0:0057B7,100:00C2A8&height=180&section=header&text=ATM%20Transaction%20Simulator&fontSize=42&fontColor=ffffff&animation=fadeIn&fontAlignY=38&desc=Java%20%7C%20Swing%20%7C%20Collections%20%7C%20OOP&descAlignY=58&descSize=18" />

<img src="https://readme-typing-svg.demolab.com/?font=Fira+Code&weight=600&size=20&pause=1000&color=0057B7&center=true&vCenter=true&width=650&lines=Secure+PIN+Authentication;Deposits+%7C+Withdrawals+%7C+Mini+Statements;HashMap+Search+%2B+TreeMap+Sorting;Built+with+Java+Swing+GUI" alt="Typing SVG" />

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue?style=for-the-badge)
![License](https://img.shields.io/badge/License-Academic-lightgrey?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen?style=for-the-badge)

</div>

---

## 📌 Overview

The **ATM Transaction Simulator** lets registered customers securely log in with an account number and PIN, then perform core banking operations — balance enquiry, deposits, withdrawals, mini statements, and more — through a desktop Swing GUI, all backed by Java's Collections Framework.

<div align="center">

| 🔐 Login & PIN Auth | 💰 Deposit / Withdraw | 🧾 Mini Statement | 🔎 Search & Sort | 📊 Reports |
|:---:|:---:|:---:|:---:|:---:|

</div>

---

## ✨ Features

- **Authentication** — Account number + 4-digit PIN, with validation for invalid entries
- **Balance Enquiry** — Instant view of account number and current balance
- **Deposit / Withdrawal** — Validated amounts, live balance updates, insufficient-balance protection
- **Mini Statement** — Latest transactions, with date, type, and amount
- **Transaction History** — Full transaction log
- **Account Search** — Look up an account by number
- **Reports** — Total customers, total accounts, total bank balance, total transactions
- **Robust Validation** — Negative/zero amounts, bad input, invalid PINs all handled gracefully

---

## 🛠️ Tech Stack

| Technology | Role |
|---|---|
| **Java** | Core language |
| **Java Swing** | GUI (`JFrame`, `JPanel`, `JButton`, `JOptionPane`, etc.) |
| **ArrayList** | Customer records |
| **LinkedList** | Transaction history |
| **HashMap** | Fast account lookup |
| **TreeMap** | Sorted account records |
| **Java Time API** | Transaction timestamps |
| **Exceptions** | Input validation & error handling |

---

## 🏗️ Architecture

```mermaid
flowchart TD
    A[Main.java<br/>Entry Point] --> B[ATMGUI.java<br/>Swing Interface]
    B --> C[ATM.java<br/>Core Operations]
    C --> D[Account.java]
    C --> E[Customer.java]
    C --> F[Transaction.java]
```

## 📁 Project Structure

```text
ATM-Transaction-Simulator-Java-/
├── Main.java            # Entry point
├── ATM.java             # Core operations, search, reports
├── ATMGUI.java          # Swing login and dashboard
├── Account.java         # Balance, PIN, transactions
├── Customer.java        # Customer data
└── Transaction.java     # Transaction record
```

---

## 📄 Class Responsibilities

| Class | Responsibility |
|---|---|
| `Main.java` | Creates the sample accounts and launches the GUI |
| `ATM.java` | Login, account management, search, sort, reports |
| `ATMGUI.java` | Login screen, dashboard, all user interactions |
| `Account.java` | Balance, PIN validation, deposit/withdraw, transaction history |
| `Customer.java` | ID, name, phone, linked account |
| `Transaction.java` | Type, amount, timestamp |

---

## 🔄 Workflow

```mermaid
flowchart TD
    Start([Start]) --> Login[Login Screen]
    Login --> Auth{Valid Credentials?}
    Auth -- No --> Error[Show Error] --> Login
    Auth -- Yes --> Dash[Dashboard]
    Dash --> Bal[Balance Enquiry]
    Dash --> Dep[Deposit]
    Dash --> With[Withdrawal]
    Dash --> Hist[Transaction History]
    Dash --> Search[Account Search]
    Dash --> Report[Reports]
    Bal & Dep & With & Hist & Search & Report --> Logout([Logout]) --> Login
```

---

## ▶️ Getting Started

```bash
# Check Java is installed
java -version && javac -version

# Clone & enter the folder that contains Main.java
git clone <your-github-repository-url>
cd ATM-Transaction-Simulator-Java-

# Compile & run
javac *.java
java Main
```

---

## 🔑 Sample Test Accounts

`Main` creates these accounts, then adds the sample transactions before the window opens.

| Account No. | PIN | Balance at login |
|:---:|:---:|---:|
| `10001` | `1234` | ₹11500 |
| `10002` | `5678` | ₹17000 |
| `10003` | `4321` | ₹8000 |

---

## 🧪 Testing Checklist

<table>
<tr>
<td valign="top">

**Login**
- ✅ Valid account + PIN
- ❌ Invalid account
- ❌ Wrong PIN
- ❌ Empty fields

</td>
<td valign="top">

**Deposit / Withdrawal**
- ✅ Valid amount
- ❌ Zero / negative amount
- ❌ Withdrawal > balance
- ❌ Non-numeric input

</td>
<td valign="top">

**Transactions**
- ✅ View history
- ✅ Sort by amount
- ✅ Sort by date
- ✅ Logout & re-login

</td>
</tr>
</table>

---

<div align="center">

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&color=0:00C2A8,100:0057B7&height=120&section=footer" />

## 👨‍💻 Author

**Pawan Mishra**
B.Tech Computer Science Engineering

**Karamveer Singh Qaumi**
B.Tech Computer Science Engineering

**SANJANA BASHYA Gundra**
B.Tech Computer Science Engineering

**Hemanshu Jivnani**
B.Tech Computer Science Engineering

*Developed as a Java Programming Mini Project to demonstrate OOP, Collections, Exception Handling, and GUI development.*

📜 Educational / Academic use only — not intended for real banking or financial use.

</div>
