public class Customer {

    private int customerId;
    private String name;
    private String phone;
    private Account account;

    public Customer(int customerId, String name, String phone, Account account) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.account = account;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId +
                ", Name: " + name +
                ", Phone: " + phone;
    }
}
