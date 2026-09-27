import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ATMGUI extends JFrame {

    private static final Color BACKGROUND = new Color(232, 238, 244);
    private static final Color HEADER = new Color(14, 77, 130);

    private final ATM atm;
    private Account loggedInAccount;
    private JTextField accountField;
    private JPasswordField pinField;

    public ATMGUI(ATM atm) {

        this.atm = atm;

        setTitle("ATM Transaction Simulator");
        setSize(760, 620);
        setMinimumSize(new Dimension(680, 560));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);

        showLoginScreen();
    }

    private void showLoginScreen() {

        JPanel card = new JPanel(new BorderLayout(0, 16));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 230)),
                BorderFactory.createEmptyBorder(0, 0, 18, 0)
        ));

        card.add(
                header("ATM TRANSACTION SIMULATOR", "Customer Login"),
                BorderLayout.NORTH
        );

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(8, 28, 8, 28));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        accountField = new JTextField(16);
        pinField = new JPasswordField(16);
        pinField.addActionListener(event -> login());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        form.add(new JLabel("Account Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        form.add(accountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        form.add(new JLabel("PIN:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        form.add(pinField, gbc);

        JButton loginButton = button("LOGIN");
        loginButton.addActionListener(event -> login());

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        form.add(loginButton, gbc);

        card.add(form, BorderLayout.CENTER);

        JLabel samples = new JLabel(
                "<html><center>Test Accounts<br>"
                        + "10001 / 1234 &nbsp;&nbsp; "
                        + "10002 / 5678 &nbsp;&nbsp; "
                        + "10003 / 4321"
                        + "</center></html>",
                SwingConstants.CENTER
        );
        samples.setFont(new Font("Arial", Font.PLAIN, 13));
        samples.setBorder(BorderFactory.createEmptyBorder(0, 16, 8, 16));
        card.add(samples, BorderLayout.SOUTH);

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(BACKGROUND);
        background.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        background.add(card);

        swap(background);
        getRootPane().setDefaultButton(loginButton);
    }

    private void login() {

        String accountNumber = accountField.getText().trim();
        String pinText = new String(pinField.getPassword()).trim();

        if (accountNumber.isEmpty() || pinText.isEmpty()) {
            showError("Validation Error", "Please enter account number and PIN.");
            return;
        }

        try {

            int pin = Integer.parseInt(pinText);
            Account account = atm.findAccount(accountNumber);

            if (account == null) {
                showError("Login Failed", "Account not found.");
                return;
            }

            if (!account.validatePin(pin)) {
                showError("Login Failed", "Invalid PIN.");
                pinField.setText("");
                return;
            }

            loggedInAccount = account;
            showInfo("Welcome", "Login successful!");
            showDashboard();

        } catch (NumberFormatException ex) {
            showError("Invalid PIN", "PIN must contain numbers only.");
        }
    }

    private void showDashboard() {

        Customer customer = atm.findCustomerByAccount(
                loggedInAccount.getAccountNumber()
        );
        String name = customer == null ? "Customer" : customer.getName();

        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBackground(BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        root.add(
                header(
                        "ATM DASHBOARD",
                        name + "   |   Account "
                                + loggedInAccount.getAccountNumber()
                                + "   |   Balance: ₹"
                                + loggedInAccount.getBalance()
                ),
                BorderLayout.NORTH
        );

        JPanel buttons = new JPanel(new GridLayout(0, 2, 12, 12));
        buttons.setBackground(BACKGROUND);

        for (String action : ATM.MENU) {

            JButton actionButton = button(action);
            actionButton.addActionListener(event -> onAction(action));
            buttons.add(actionButton);
        }

        root.add(buttons, BorderLayout.CENTER);
        swap(root);
    }

    private void onAction(String action) {

        switch (action) {

            case "Balance Enquiry":
                showBalance();
                break;

            case "Deposit":
                deposit();
                break;

            case "Withdrawal":
                withdraw();
                break;

            case "Mini Statement":
                showText("Mini Statement", statementText(true));
                break;

            case "Transaction History":
                showText("Transaction History", statementText(false));
                break;

            case "Account Search":
                searchAccount();
                break;

            case "Reports":
                showText("Reports", atm.reportsText());
                break;

            case "Logout":
                logout();
                break;

            default:
                showError("Error", "Unknown action.");
        }
    }

    private void showBalance() {

        Customer customer = atm.findCustomerByAccount(
                loggedInAccount.getAccountNumber()
        );
        String name = customer == null
                ? ""
                : "Customer: " + customer.getName() + "\n\n";

        showInfo(
                "Balance Enquiry",
                name
                        + "Account Number: "
                        + loggedInAccount.getAccountNumber()
                        + "\n\nCurrent Balance: ₹"
                        + loggedInAccount.getBalance()
        );
    }

    private void deposit() {

        String input = JOptionPane.showInputDialog(this, "Enter deposit amount:");

        if (input == null) {
            return;
        }

        try {

            double amount = Double.parseDouble(input.trim());
            loggedInAccount.deposit(amount);

            showInfo(
                    "Deposit",
                    "Deposit successful!\n\n"
                            + "Deposited Amount: ₹" + amount
                            + "\nNew Balance: ₹" + loggedInAccount.getBalance()
            );
            showDashboard();

        } catch (NumberFormatException ex) {
            showError("Invalid Amount", "Please enter a valid amount.");
        } catch (IllegalArgumentException ex) {
            showError("Deposit Error", ex.getMessage());
        }
    }

    private void withdraw() {

        String input = JOptionPane.showInputDialog(this, "Enter withdrawal amount:");

        if (input == null) {
            return;
        }

        try {

            double amount = Double.parseDouble(input.trim());
            loggedInAccount.withdraw(amount);

            showInfo(
                    "Withdrawal",
                    "Withdrawal successful!\n\n"
                            + "Withdrawn Amount: ₹" + amount
                            + "\nRemaining Balance: ₹"
                            + loggedInAccount.getBalance()
            );
            showDashboard();

        } catch (NumberFormatException ex) {
            showError("Invalid Amount", "Please enter a valid amount.");
        } catch (IllegalArgumentException ex) {
            showError("Withdrawal Error", ex.getMessage());
        }
    }

    private void searchAccount() {

        String accountNumber = JOptionPane.showInputDialog(
                this,
                "Enter account number:"
        );

        if (accountNumber == null) {
            return;
        }

        accountNumber = accountNumber.trim();

        if (accountNumber.isEmpty()) {
            showError("Account Search", "Invalid account number.");
            return;
        }

        showText("Account Search", atm.searchAccountText(accountNumber));
    }

    private String statementText(boolean mini) {

        StringBuilder text = new StringBuilder();

        text.append(mini ? "===== MINI STATEMENT =====" : "===== TRANSACTION HISTORY =====");
        text.append("\n\nAccount Number: ")
                .append(loggedInAccount.getAccountNumber())
                .append("\n\n");

        if (loggedInAccount.getTransactions().isEmpty()) {
            text.append("No transactions available.");
            return text.toString();
        }

        int from = 0;

        if (mini) {
            from = Math.max(0, loggedInAccount.getTransactions().size() - 5);

            if (from > 0) {
                text.append("Showing the latest 5 of ")
                        .append(loggedInAccount.getTransactions().size())
                        .append(" transactions.\n\n");
            }
        }

        int index = 0;

        for (Transaction transaction : loggedInAccount.getTransactions()) {

            if (index >= from) {
                text.append(transaction).append('\n');
            }

            index++;
        }

        return text.toString();
    }

    private void logout() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            loggedInAccount = null;
            showLoginScreen();
        }
    }

    private JPanel header(String title, String subtitle) {

        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 4));
        panel.setBackground(HEADER);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(subtitle, SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(220, 232, 242));

        panel.add(titleLabel);
        panel.add(subtitleLabel);
        return panel;
    }

    private JButton button(String text) {

        JButton actionButton = new JButton(text);
        actionButton.setFont(new Font("Arial", Font.BOLD, 15));
        actionButton.setMargin(new Insets(10, 12, 10, 12));
        return actionButton;
    }

    private void showText(String title, String body) {

        JTextArea area = new JTextArea(body);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        area.setCaretPosition(0);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(520, 280));

        JOptionPane.showMessageDialog(
                this,
                scroll,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showInfo(String title, String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showError(String title, String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void swap(JComponent screen) {
        getContentPane().removeAll();
        add(screen);
        getRootPane().setDefaultButton(null);
        revalidate();
        repaint();
    }
}
