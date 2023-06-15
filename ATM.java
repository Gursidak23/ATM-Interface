import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.*;

class TransactionHistory {
    private String date;
    private String time;
    static double balance = 10000.00;
    public double amount;
    private String userId;
    private String password;

    public TransactionHistory(String date, String time, double amount, String userId, String password) {
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.userId = userId;
        this.password = password;
    }

    public void storeTransactionHistory() {
        try {
            FileWriter fw = new FileWriter("TransactionHistory.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("Date: " + date + " Time: " + time + " Amount: " + amount + " User ID: " + userId + " Password: "
                    + password);

            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void readTransactionHistory() {
        try {
            FileReader fr = new FileReader("TransactionHistory.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean verifyUser(String userId, String password) {
        if (this.userId.equals(userId) && this.password.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public void updateBalance() {
        try {
            FileWriter fw = new FileWriter("Balance.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(balance);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

class Balance extends TransactionHistory {
    public Balance(String date, String time, double amount, String userId, String password) {
        super(date, time, amount, userId, password);
    }

    public void checkBalance() {
        try {
            FileReader fr = new FileReader("Balance.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                balance = Double.parseDouble(line);
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Your current balance is: " + balance);
    }
}

class Deposit extends TransactionHistory {
    public Deposit(String date, String time, double amount, String userId, String password) {
        super(date, time, amount, userId, password);
    }

    public void depositAmount() {
        balance += amount;
        updateBalance();
        System.out.println("Amount deposited successfully");
        System.out.println("Your current balance is: " + balance);
    }
}

class Withdraw extends TransactionHistory {
    public Withdraw(String date, String time, double amount, String userId, String password) {
        super(date, time, amount, userId, password);
    }

    public void withdrawAmount() {
        if (amount > balance) {
            System.out.println("Insufficient balance");
        } else {
            balance -= amount;
            updateBalance();
            System.out.println("Amount withdrawn successfully");
            System.out.println("Your current balance is: " + balance);
        }
    }
}

class Transfer extends TransactionHistory {
    public Transfer(String date, String time, double amount, String userId, String password) {
        super(date, time, amount, userId, password);
    }

    public void transferAmount(long accountNumber) {
        if (amount > balance) {
            System.out.println("Insufficient balance");
        } else {
            balance -= amount;
            updateBalance();
            System.out.println("Amount transferred to " + accountNumber + " successfully");
            System.out.println("Your current balance is: " + balance);
        }
    }
}

class Exit {
    public void exitATM() {
        System.out.println("Thank you for using our ATM");
        System.exit(0);
    }
}

class ATM {
    char ch;

    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your user id: ");
        String userId = sc.nextLine();
        System.out.println("Enter your password: ");
        String password = sc.nextLine();
        if (userId.equals("admin") && password.equals("admin")) {
            do {
                System.out.println("Welcome to our ATM");
                System.out.println("Enter 1 to view transaction history");
                System.out.println("Enter 2 to check balance");
                System.out.println("Enter 3 to deposit amount");
                System.out.println("Enter 4 to withdraw amount");
                System.out.println("Enter 5 to transfer amount");
                System.out.println("Enter 6 to exit");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        Date date = new Date();
                        DateFormat df = new SimpleDateFormat("dd/MM/yy");
                        DateFormat tf = new SimpleDateFormat("HH:mm:ss");
                        String d = df.format(date);
                        String t = tf.format(date);
                        TransactionHistory th = new TransactionHistory(d, t, 0, userId, password);
                        th.readTransactionHistory();
                        break;
                    case 2:
                        Date date4 = new Date();
                        DateFormat df4 = new SimpleDateFormat("dd/MM/yy");
                        DateFormat tf4 = new SimpleDateFormat("HH:mm:ss");
                        String d5 = df4.format(date4);
                        String t5 = tf4.format(date4);
                        Balance b = new Balance(d5, t5, 0, userId, password);
                        b.checkBalance();
                        break;
                    case 3:
                        System.out.println("Enter the amount to be deposited: ");
                        double amount = sc.nextDouble();
                        Date date1 = new Date();
                        DateFormat df1 = new SimpleDateFormat("dd/MM/yy");
                        DateFormat tf1 = new SimpleDateFormat("HH:mm:ss");
                        String d1 = df1.format(date1);
                        String t1 = tf1.format(date1);
                        Deposit d2 = new Deposit(d1, t1, amount, userId, password);
                        d2.depositAmount();
                        d2.storeTransactionHistory();
                        break;
                    case 4:
                        System.out.println("Enter the amount to be withdrawn: ");
                        double amount1 = sc.nextDouble();
                        Date date2 = new Date();
                        DateFormat df2 = new SimpleDateFormat("dd/MM/yy");
                        DateFormat tf2 = new SimpleDateFormat("HH:mm:ss");
                        String d3 = df2.format(date2);
                        String t2 = tf2.format(date2);
                        Withdraw w = new Withdraw(d3, t2, amount1, userId, password);
                        w.withdrawAmount();
                        w.storeTransactionHistory();
                        break;
                    case 5:
                        System.out.println("Enter the amount to be transferred: ");
                        double amount2 = sc.nextDouble();
                        System.out.println("Enter the account number to which the amount is to be transferred: ");
                        long accountNumber = sc.nextLong();
                        Date date3 = new Date();
                        DateFormat df3 = new SimpleDateFormat("dd/MM/yy");
                        DateFormat tf3 = new SimpleDateFormat("HH:mm:ss");
                        String d4 = df3.format(date3);
                        String t3 = tf3.format(date3);
                        Transfer t4 = new Transfer(d4, t3, amount2, userId, password);
                        t4.transferAmount(accountNumber);
                        t4.storeTransactionHistory();
                        break;
                    case 6:
                        Exit e = new Exit();
                        e.exitATM();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
                System.out.println("Do you want to continue? (y/n)");
                atm.ch = sc.next().charAt(0);
            } while (atm.ch == 'y' || atm.ch == 'Y');
            sc.close();
        } else {
            System.out.println("Invalid user id or password");
        }
    }
}
