import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    public static final String TYPE_DEPOSIT = "Deposit";
    public static final String TYPE_WITHDRAWAL = "Withdrawal";

    public static final String[] TYPES = {
            TYPE_DEPOSIT,
            TYPE_WITHDRAWAL
    };

    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String type;
    private double amount;
    private LocalDateTime dateTime;

    public Transaction(String type, double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be greater than 0."
            );
        }

        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Date: " + dateTime.format(DISPLAY_FORMAT)
                + " | Type: " + type
                + " | Amount: ₹" + amount;
    }
}
