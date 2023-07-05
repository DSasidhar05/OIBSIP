import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class BankAccount {
    private String accountId;
    private String accountPin;
    private double balance;
    private List<Transaction> transactionHistory;

    public BankAccount(String accountId, String accountPin, double balance) {
        this.accountId = accountId;
        this.accountPin = accountPin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean authenticate(String enteredAccountId, String enteredAccountPin) {
        return accountId.equals(enteredAccountId) && accountPin.equals(enteredAccountPin);
    }

    public double getBalance() {
        return balance;
    }

    public boolean isValidWithdrawalAmount(double amount) {
        return amount > 0 && amount % 500 == 0;
    }

    public boolean hasSufficientBalance(double amount) {
        return amount <= balance;
    }

    public void withdraw(double amount) {
        if (isValidWithdrawalAmount(amount)) {
            if (hasSufficientBalance(amount)) {
                balance -= amount;
                transactionHistory.add(new Transaction("Withdrawal", amount));
                System.out.println("Amount withdrawn: $" + amount);
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            System.out.println("Amount deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void transfer(double amount, BankAccount recipient) {
        if (amount > 0 && hasSufficientBalance(amount)) {
            balance -= amount;
            recipient.balance += amount;
            transactionHistory.add(new Transaction("Transfer to " + recipient.accountId, amount));
            System.out.println("Amount transferred: $" + amount);
        } else {
            System.out.println("Invalid transfer amount or insufficient balance.");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transaction history found.");
        } else {
            for (Transaction transaction : transactionHistory) {
                System.out.println(transaction.getType() + ": $" + transaction.getAmount());
            }
        }
    }
}

public class ATM {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            BankAccount account = new BankAccount("33008", "1430", 10000.0);

            System.out.println("Welcome to the ATM System!");

            System.out.print("Enter Account ID: ");
            String enteredAccountId = scanner.nextLine();

            System.out.print("Enter Account PIN: ");
            String enteredAccountPin = scanner.nextLine();

            if (account.authenticate(enteredAccountId, enteredAccountPin)) {
                int choice;
                do {
                    System.out.println("ATM Menu:");
                    System.out.println("1. Print Transaction History");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Deposit");
                    System.out.println("4. Transfer");
                    System.out.println("5. CheckBalance");
                    System.out.println("6. Exit");

                    System.out.print("Enter your choice: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            account.printTransactionHistory();
                            break;
                        case 2:
                            System.out.print("Enter the amount to withdraw (in multiples of 500): $");
                            double withdrawalAmount = scanner.nextDouble();
                            scanner.nextLine();
                            account.withdraw(withdrawalAmount);
                            break;
                        case 3:
                            System.out.print("Enter the amount to deposit: $");
                            double depositAmount = scanner.nextDouble();
                            scanner.nextLine();
                            account.deposit(depositAmount);
                            break;
                        case 4:
                            System.out.print("Enter the recipient's Account ID: ");
                            String recipientAccountId = scanner.nextLine();
                            System.out.print("Enter the amount to transfer: $");
                            double transferAmount = scanner.nextDouble();
                            scanner.nextLine();
                            BankAccount recipient = new BankAccount(recipientAccountId, "", 0);
                            account.transfer(transferAmount, recipient);
                            break;
                        case 5:
                            System.out.println("Current Balance: $" + account.getBalance());
                            break;
                        case 6:
                            System.out.println("Thank you for using our ATM System.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                    System.out.println();
                } while (choice != 6);
            } else {
                System.out.println("Authentication failed. Invalid Account ID or PIN.");
            }
        }
    }
}
