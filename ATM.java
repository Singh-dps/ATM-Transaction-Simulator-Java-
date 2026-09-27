import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class ATM {

    public static final String[] MENU = {
            "Balance Enquiry",
            "Deposit",
            "Withdrawal",
            "Mini Statement",
            "Transaction History",
            "Account Search",
            "Reports",
            "Logout"
    };

    private ArrayList<Customer> customers;
    private HashMap<String, Account> accounts;
    private TreeMap<String, Account> sortedAccounts;

    public ATM() {
        customers = new ArrayList<>();
        accounts = new HashMap<>();
        sortedAccounts = new TreeMap<>();
    }

    public void addCustomer(Customer customer) {

        customers.add(customer);

        Account account = customer.getAccount();

        accounts.put(account.getAccountNumber(), account);
        sortedAccounts.put(account.getAccountNumber(), account);
    }

    public Account findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public Customer findCustomerByAccount(String accountNumber) {

        for (Customer customer : customers) {

            if (customer.getAccount().getAccountNumber().equals(accountNumber)) {
                return customer;
            }
        }

        return null;
    }

    public String searchAccountText(String accountNumber) {

        Account account = accounts.get(accountNumber);

        if (account == null) {
            return "Account not found.";
        }

        Customer customer = findCustomerByAccount(accountNumber);
        StringBuilder text = new StringBuilder();

        text.append("Account Number: ")
                .append(account.getAccountNumber())
                .append('\n');

        if (customer != null) {
            text.append("Customer: ").append(customer.getName()).append('\n');
            text.append("Phone: ").append(customer.getPhone()).append('\n');
        }

        text.append("Current Balance: ₹")
                .append(account.getBalance())
                .append('\n');
        text.append("Transactions: ")
                .append(account.getTransactions().size());

        return text.toString();
    }

    public void searchAccount(String accountNumber) {

        Account account = accounts.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println();
        System.out.println(searchAccountText(accountNumber));
    }

    public String accountReportText() {

        double totalBalance = 0;

        for (Account account : accounts.values()) {
            totalBalance += account.getBalance();
        }

        return "===== ACCOUNT REPORT =====\n"
                + "Total Customers: " + customers.size() + "\n"
                + "Total Accounts: " + accounts.size() + "\n"
                + "Total Bank Balance: ₹" + totalBalance + "\n";
    }

    public String transactionReportText() {

        int totalTransactions = 0;
        StringBuilder lines = new StringBuilder();

        for (Account account : sortedAccounts.values()) {

            int count = account.getTransactions().size();
            totalTransactions += count;

            Customer customer = findCustomerByAccount(account.getAccountNumber());

            lines.append(account.getAccountNumber());

            if (customer != null) {
                lines.append(" | ").append(customer.getName());
            }

            lines.append(" | Transactions: ").append(count).append('\n');
        }

        return "===== TRANSACTION REPORT =====\n"
                + "Total Transactions: " + totalTransactions + "\n\n"
                + lines;
    }

    public String reportsText() {
        return accountReportText() + "\n" + transactionReportText();
    }

    public void generateAccountReport() {
        System.out.println();
        System.out.print(accountReportText());
    }

    public void generateTransactionReport() {
        System.out.println();
        System.out.print(transactionReportText());
    }

    public void displayCustomers() {

        System.out.println("\n===== CUSTOMER RECORDS =====");

        if (customers.isEmpty()) {
            System.out.println("No customers available.");
            return;
        }

        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public void displaySortedAccounts() {

        System.out.println("\n===== SORTED ACCOUNTS =====");

        for (Account account : sortedAccounts.values()) {
            System.out.println(
                    "Account Number: " + account.getAccountNumber()
                            + " | Balance: ₹" + account.getBalance()
            );
        }
    }

    public Account login(String accountNumber, int pin) {

        Account account = accounts.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return null;
        }

        if (!account.validatePin(pin)) {
            System.out.println("Invalid PIN.");
            return null;
        }

        System.out.println("\nLogin successful!");
        return account;
    }

    public void showMenu(Account account) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n==========================");
            System.out.println("       ATM MENU");
            System.out.println("==========================");

            for (int i = 0; i < MENU.length; i++) {
                System.out.println((i + 1) + ". " + MENU[i]);
            }

            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = scanner.nextInt();
            } catch (Exception ex) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.println("\n===== BALANCE ENQUIRY =====");
                    System.out.println("Account Number: " + account.getAccountNumber());
                    System.out.println("Available Balance: ₹" + account.getBalance());
                    break;

                case 2:
                    System.out.print("\nEnter deposit amount: ₹");
                    try {
                        account.deposit(scanner.nextDouble());
                        System.out.println("Amount deposited successfully.");
                        System.out.println("New Balance: ₹" + account.getBalance());
                    } catch (IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                    } catch (Exception ex) {
                        System.out.println("Invalid amount.");
                        scanner.nextLine();
                    }
                    break;

                case 3:
                    System.out.print("\nEnter withdrawal amount: ₹");
                    try {
                        account.withdraw(scanner.nextDouble());
                        System.out.println("Please collect your cash.");
                        System.out.println("Remaining Balance: ₹" + account.getBalance());
                    } catch (IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                    } catch (Exception ex) {
                        System.out.println("Invalid amount.");
                        scanner.nextLine();
                    }
                    break;

                case 4:
                case 5:
                    account.displayTransactions();
                    break;

                case 6:
                    System.out.print("Enter account number: ");
                    searchAccount(scanner.next());
                    break;

                case 7:
                    generateAccountReport();
                    generateTransactionReport();
                    break;

                case 8:
                    System.out.println("\nLogged out successfully.");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public TreeMap<String, Account> getSortedAccounts() {
        return sortedAccounts;
    }
}
