import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class Account {

    private String accountNumber;
    private int pin;
    private double balance;
    private LinkedList<Transaction> transactions;

    public Account(String accountNumber, int pin, double balance) {

        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Invalid account number.");
        }

        if (pin < 1000 || pin > 9999) {
            throw new IllegalArgumentException(
                    "PIN must contain exactly 4 digits."
            );
        }

        if (balance < 0) {
            throw new IllegalArgumentException(
                    "Initial balance cannot be negative."
            );
        }

        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new LinkedList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public boolean validatePin(int enteredPin) {
        return pin == enteredPin;
    }

    public void deposit(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Deposit amount must be greater than 0."
            );
        }

        balance += amount;
        transactions.add(new Transaction(Transaction.TYPE_DEPOSIT, amount));
    }

    public void withdraw(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Withdrawal amount must be greater than 0."
            );
        }

        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        balance -= amount;
        transactions.add(new Transaction(Transaction.TYPE_WITHDRAWAL, amount));
    }

    public LinkedList<Transaction> getTransactions() {
        return transactions;
    }

    public void displayTransactions() {

        System.out.println("\n===== TRANSACTION HISTORY =====");

        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public void sortTransactionsByAmount() {

        ArrayList<Transaction> sorted = new ArrayList<>(transactions);
        sorted.sort(Comparator.comparingDouble(Transaction::getAmount));

        System.out.println("\n===== TRANSACTIONS SORTED BY AMOUNT =====");

        for (Transaction transaction : sorted) {
            System.out.println(transaction);
        }
    }

    public void sortTransactionsByDate() {

        ArrayList<Transaction> sorted = new ArrayList<>(transactions);
        sorted.sort(Comparator.comparing(Transaction::getDateTime));

        System.out.println("\n===== TRANSACTIONS SORTED BY DATE =====");

        for (Transaction transaction : sorted) {
            System.out.println(transaction);
        }
    }
}
