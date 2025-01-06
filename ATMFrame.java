import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ATMFrame extends JFrame implements ActionListener 
 {
    private static final String USER_DATA_DIRECTORY = "user_data/";
    private static final String TRANSACTION_DIRECTORY = "transaction_data/";
    private static final String DEFAULT_CURRENCY = "Indian (INR)";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    JButton registerButton, loginButton, exitButton;
    JTextField nameField, mobileField, addressField, pinField, balanceField, atmCardField, pinLoginField, amountField, newPinField, oldPinField;
    JButton createAccountButton, backButton, loginAccountButton, withdrawButton, depositButton, balanceInquiryButton, changePinButton, backButton2;
    JButton withdrawConfirmButton, depositConfirmButton, changePinConfirmButton;
    JComboBox<String> currencyComboBox;
    JLabel nameLabel, mobileLabel, addressLabel, pinLabel, balanceLabel, atmCardLabel, pinLoginLabel, oldPinLabel;
    JLabel currencyLabel, amountLabel, messageLabel;

    private long currentAccountNumber;

    public ATMFrame() {
        setTitle("ATM Management ");
        setSize(700, 600);
        setLayout(null); // Set layout to null

        // Create buttons
        registerButton = new JButton("Register");
        registerButton.setBounds(250, 50, 200, 70); 

        loginButton = new JButton("Login");
        loginButton.setBounds(250, 200, 200, 70); 

        exitButton = new JButton("Exit");
        exitButton.setBounds(250, 350, 200, 70); 

        // Add action listeners
        registerButton.addActionListener(this);
        loginButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Add buttons to the frame
        add(registerButton);
        add(loginButton);
        add(exitButton);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setResizable(false); // Disable resizing
        setVisible(true);

         // Create user data directory if it doesn't exist
        File directory = new File(USER_DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs(); // Create all necessary parent directories
        }

        // Create transaction data directory if it doesn't exist
        File transactionDirectory = new File(TRANSACTION_DIRECTORY);
        if (!transactionDirectory.exists()) {
            transactionDirectory.mkdirs(); // Create all necessary parent directories
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            register();
        } else if (e.getSource() == loginButton) {
            login();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == createAccountButton) {
            createAccount();
        } else if (e.getSource() == loginAccountButton) {
            loginAccount();
        } else if (e.getSource() == backButton || e.getSource() == backButton2) {
            resetFrame();
        } else if (e.getSource() == withdrawButton) {
            withdraw();
        } else if (e.getSource() == depositButton) {
            deposit();
        } else if (e.getSource() == balanceInquiryButton) {
            balanceInquiry();
        } else if (e.getSource() == changePinButton) {
            changePin();
        } else if (e.getSource() == withdrawConfirmButton) {
            withdrawConfirm();
        } else if (e.getSource() == depositConfirmButton) {
            depositConfirm();
        } else if (e.getSource() == changePinConfirmButton) {
            changePinConfirm();
        }
    }

    private void register() {
        clearFrame();

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30);
        add(nameField);

        mobileLabel = new JLabel("Mobile Number:");
        mobileLabel.setBounds(50, 100, 100, 30);
        add(mobileLabel);

        mobileField = new JTextField();
        mobileField.setBounds(150, 100, 200, 30);
        add(mobileField);

        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 150, 100, 30);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(150, 150, 200, 30);
        add(addressField);

        pinLabel = new JLabel("Create PIN:");
        pinLabel.setBounds(50, 200, 100, 30);
        add(pinLabel);

        pinField = new JTextField();
        pinField.setBounds(150, 200, 200, 30);
        add(pinField);

        balanceLabel = new JLabel("Starting Balance:");
        balanceLabel.setBounds(50, 250, 100, 30);
        add(balanceLabel);

        balanceField = new JTextField();
        balanceField.setBounds(150, 250, 200, 30);
        add(balanceField);

        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(100, 350, 150, 50);
        createAccountButton.addActionListener(this);
        add(createAccountButton);

        backButton = new JButton("Back");
        backButton.setBounds(300, 350, 150, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFrame(); // Go back to the main frame
            }
        });
        add(backButton);
    }

    private void createAccount() {
        try {
            String name = nameField.getText();
            String mobile = mobileField.getText();
            String address = addressField.getText();
            String pin = pinField.getText();
            double balance = Double.parseDouble(balanceField.getText());
    
            // Minimum starting balance
            if (balance < 1000) {
                JOptionPane.showMessageDialog(this, "Minimum starting balance should be 1000 Rs.");
                return; // Exit
            }
    
            // Generate 8 digit ATM card number randomly
            Random rand = new Random();
            long atmCardNumber = 10000000 + rand.nextInt(90000000);
    
            // Write user data to file
            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_DIRECTORY + atmCardNumber + ".txt"));
            writer.write("Name: " + name + "\n");
            writer.write("Mobile Number: " + mobile + "\n");
            writer.write("Address: " + address + "\n");
            writer.write("PIN: " + pin + "\n");
            writer.write("Balance: " + balance + "\n");
            writer.write("ATM Card Number: " + atmCardNumber + "\n");
            writer.close();
    
            JOptionPane.showMessageDialog(this, "Account created successfully!\nATM Card Number: " + atmCardNumber);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid balance amount. Please enter a valid numeric value.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    private void login() {
        clearFrame();

        atmCardLabel = new JLabel("ATM Card Number:");
        atmCardLabel.setBounds(50, 50, 150, 30);
        add(atmCardLabel);

        atmCardField = new JTextField();
        atmCardField.setBounds(200, 50, 200, 30);
        add(atmCardField);

        pinLoginLabel = new JLabel("PIN:");
        pinLoginLabel.setBounds(50, 100, 100, 30);
        add(pinLoginLabel);

        pinLoginField = new JTextField();
        pinLoginField.setBounds(200, 100, 200, 30);
        add(pinLoginField);

        loginAccountButton = new JButton("Login");
        loginAccountButton.setBounds(100, 200, 150, 50);
        loginAccountButton.addActionListener(this);
        add(loginAccountButton);

        backButton2 = new JButton("Back");
        backButton2.setBounds(300, 200, 150, 50);
        backButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFrame(); // Go back to the main frame
            }
        });
        add(backButton2);
    }

    private void loginAccount() {
        try {
            long atmCardNumber = Long.parseLong(atmCardField.getText());
            String pin = pinLoginField.getText();

            // Read user data from file
            BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_DIRECTORY + atmCardNumber + ".txt"));
            String line;
            boolean loggedIn = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("PIN:")) {
                    String savedPIN = line.split(":")[1].trim();
                    if (pin.equals(savedPIN)) {
                        loggedIn = true;
                        break;
                    }
                }
            }
            reader.close();

            if (loggedIn) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                currentAccountNumber = atmCardNumber;
                loggedInFrame();

                // Add action listener to the back button
                backButton2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        resetFrame(); // Go back to the main frame
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ATM Card Number or PIN!");
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "User not found!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loggedInFrame() {
        clearFrame();

        withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(100, 50, 150, 50);
        withdrawButton.addActionListener(this);
        add(withdrawButton);

        depositButton = new JButton("Deposit");
        depositButton.setBounds(300, 50, 150, 50);
        depositButton.addActionListener(this);
        add(depositButton);

        balanceInquiryButton = new JButton("Balance Inquiry");
        balanceInquiryButton.setBounds(100, 150, 150, 50);
        balanceInquiryButton.addActionListener(this);
        add(balanceInquiryButton);

        changePinButton = new JButton("Change PIN");
        changePinButton.setBounds(300, 150, 150, 50);
        changePinButton.addActionListener(this);
        add(changePinButton);

        backButton2 = new JButton("Back");
        backButton2.setBounds(250, 250, 150, 50);
        backButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFrame(); // Go back to the main frame
            }
        });
        add(backButton2);
    }

    private void withdraw() {
        clearFrame();
    
        currencyLabel = new JLabel("Choose Currency:");
        currencyLabel.setBounds(50, 50, 150, 30);
        add(currencyLabel);
    
        String[] currencies = {"Indian (INR)", "American (USD)", "Japanese (JPY)", "German (EUR)", "British (GBP)"};
        currencyComboBox = new JComboBox<>(currencies);
        currencyComboBox.setSelectedItem(DEFAULT_CURRENCY);
        currencyComboBox.setBounds(200, 50, 150, 30);
        add(currencyComboBox);
    
        amountLabel = new JLabel("Enter Amount:");
        amountLabel.setBounds(50, 100, 150, 30);
        add(amountLabel);
    
        amountField = new JTextField();
        amountField.setBounds(200, 100, 150, 30);
        add(amountField);
    
        withdrawConfirmButton = new JButton("Withdraw");
        withdrawConfirmButton.setBounds(150, 150, 150, 50);
        withdrawConfirmButton.addActionListener(this);
        add(withdrawConfirmButton);
    
        backButton2 = new JButton("Back");
        backButton2.setBounds(300, 150, 150, 50);
        backButton2.addActionListener(this);
        add(backButton2);
    }
    
    
    private void withdrawConfirm() {
        try {
            String currency = (String) currencyComboBox.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());
    
            // Conversion rates
            double conversionRate = 1.0;
            switch (currency) {
                case "American (USD)":
                    conversionRate = 0.012;
                    break;
                case "Japanese (JPY)":
                    conversionRate = 1.886;
                    break;
                case "German (EUR)":
                    conversionRate = 0.011;
                    break;
                case "British (GBP)":
                    conversionRate = 0.009;
                    break;
            }
    
            // Convert amount to default currency (INR)
            double convertedAmount = amount / conversionRate;
    
            // Read user data from file
            BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_DIRECTORY + currentAccountNumber + ".txt"));
            StringBuilder userData = new StringBuilder();
            String line;
            double balance = 0;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Balance:")) {
                    balance = Double.parseDouble(line.split(":")[1].trim());
                    // Check if withdrawal amount is valid
                    if (balance - convertedAmount < 1000) {
                        JOptionPane.showMessageDialog(this, "You cannot withdraw below or equal to 1000 Rs. Your balance after withdrawal should be more than 1000 Rs.");
                        reader.close();
                        return;
                    }
                    // Update balance after withdrawal
                    line = "Balance: " + DECIMAL_FORMAT.format(balance - convertedAmount);
                }
                userData.append(line).append("\n");
            }
            reader.close();
    
            // Write updated user data to file
            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_DIRECTORY + currentAccountNumber + ".txt"));
            writer.write(userData.toString());
            writer.close();
    
            // Record transaction with date and time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            BufferedWriter transactionWriter = new BufferedWriter(new FileWriter(TRANSACTION_DIRECTORY + currentAccountNumber + ".txt", true));
            transactionWriter.write("Withdrawal: " + DECIMAL_FORMAT.format(convertedAmount) + " " + currency +
                    " on " + dtf.format(now) + "\n");
            transactionWriter.close();
    
            JOptionPane.showMessageDialog(this, "Withdrawal successful!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    
    

    private void deposit() {
        clearFrame();

        currencyLabel = new JLabel("Choose Currency:");
        currencyLabel.setBounds(50, 50, 150, 30);
        add(currencyLabel);

        String[] currencies = {"Indian (INR)", "American (USD)", "Japanese (JPY)", "German (EUR)", "British (GBP)"};
        currencyComboBox = new JComboBox<>(currencies);
        currencyComboBox.setSelectedItem(DEFAULT_CURRENCY);
        currencyComboBox.setBounds(200, 50, 150, 30);
        add(currencyComboBox);

        amountLabel = new JLabel("Enter Amount:");
        amountLabel.setBounds(50, 100, 150, 30);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(200, 100, 150, 30);
        add(amountField);

        depositConfirmButton = new JButton("Deposit");
        depositConfirmButton.setBounds(150, 150, 150, 50);
        depositConfirmButton.addActionListener(this);
        add(depositConfirmButton);

        backButton2 = new JButton("Back");
        backButton2.setBounds(300, 150, 150, 50);
        backButton2.addActionListener(this);
        add(backButton2);
    }

    private void depositConfirm() {
        try {
            String currency = (String) currencyComboBox.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());

            // Conversion rates
            double conversionRate = 1.0;
            switch (currency) {
                case "American (USD)":
                    conversionRate = 0.012;
                    break;
                case "Japanese (JPY)":
                    conversionRate = 1.886;
                    break;
                case "German (EUR)":
                    conversionRate = 0.011;
                    break;
                case "British (GBP)":
                    conversionRate = 0.009;
                    break;
            }

            // Convert amount to default currency (INR)
            double convertedAmount = amount / conversionRate;

            // Read user data from file
            BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_DIRECTORY + currentAccountNumber + ".txt"));
            StringBuilder userData = new StringBuilder();
            String line;
            double balance = 0;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Balance:")) {
                    balance = Double.parseDouble(line.split(":")[1].trim());
                    // Update balance after deposit
                    line = "Balance: " + DECIMAL_FORMAT.format(balance + convertedAmount);
                }
                userData.append(line).append("\n");
            }
            reader.close();

            // Write updated user data to file
            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_DIRECTORY + currentAccountNumber + ".txt"));
            writer.write(userData.toString());
            writer.close();

            // Record transaction with date and time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            BufferedWriter transactionWriter = new BufferedWriter(new FileWriter(TRANSACTION_DIRECTORY + currentAccountNumber + ".txt", true));
            transactionWriter.write("Deposit: " + DECIMAL_FORMAT.format(convertedAmount) + " " + DEFAULT_CURRENCY +
                    " on " + dtf.format(now) + "\n");
            transactionWriter.close();

            JOptionPane.showMessageDialog(this, "Deposit successful!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void balanceInquiry() {
        clearFrame();
    
        currencyLabel = new JLabel("Choose Currency:");
        currencyLabel.setBounds(50, 50, 150, 30);
        add(currencyLabel);
    
        String[] currencies = {"Indian (INR)", "American (USD)", "Japanese (JPY)", "German (EUR)", "British (GBP)"};
        currencyComboBox = new JComboBox<>(currencies);
        currencyComboBox.setSelectedItem(DEFAULT_CURRENCY);
        currencyComboBox.setBounds(200, 50, 150, 30);
        add(currencyComboBox);
    
        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setBounds(150, 100, 150, 50);
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        add(checkBalanceButton);
    
        backButton2 = new JButton("Back");
        backButton2.setBounds(300, 100, 150, 50);
        backButton2.addActionListener(this);
        add(backButton2);
    }
    
    private void checkBalance() {
        try {
            String selectedCurrency = (String) currencyComboBox.getSelectedItem();
            BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_DIRECTORY + currentAccountNumber + ".txt"));
            String line;
            double balance = 0;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Balance:")) {
                    balance = Double.parseDouble(line.split(":")[1].trim());
                    break;
                }
            }
            reader.close();
    
            // Conversion rates
            double conversionRate = 1.0;
            switch (selectedCurrency) {
                case "American (USD)":
                    conversionRate = 0.012;
                    break;
                case "Japanese (JPY)":
                    conversionRate = 1.886;
                    break;
                case "German (EUR)":
                    conversionRate = 0.011;
                    break;
                case "British (GBP)":
                    conversionRate = 0.009;
                    break;
            }
    
            // Convert balance to selected currency
            double convertedBalance = balance * conversionRate;
    
            JOptionPane.showMessageDialog(this, "Your current balance is: " + DECIMAL_FORMAT.format(convertedBalance) + " " + selectedCurrency);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    private void changePin() {
        clearFrame();

        oldPinLabel = new JLabel("Old PIN:");
        oldPinLabel.setBounds(50, 50, 100, 30);
        add(oldPinLabel);

        oldPinField = new JTextField();
        oldPinField.setBounds(150, 50, 200, 30);
        add(oldPinField);

        pinLabel = new JLabel("New PIN:");
        pinLabel.setBounds(50, 100, 100, 30);
        add(pinLabel);

        newPinField = new JTextField();
        newPinField.setBounds(150, 100, 200, 30);
        add(newPinField);

        changePinConfirmButton = new JButton("Change PIN");
        changePinConfirmButton.setBounds(150, 150, 150, 50);
        changePinConfirmButton.addActionListener(this);
        add(changePinConfirmButton);

        backButton2 = new JButton("Back");
        backButton2.setBounds(300, 150, 150, 50);
        backButton2.addActionListener(this);
        add(backButton2);
    }

    private void changePinConfirm() {
        try {
            String oldPin = oldPinField.getText();
            String newPin = newPinField.getText();
    
            // Read user data from file
            BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_DIRECTORY + currentAccountNumber + ".txt"));
            StringBuilder userData = new StringBuilder();
            String line;
            boolean pinUpdated = false; // Flag to track if PIN has been updated
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("PIN:")) {
                    String savedPIN = line.split(":")[1].trim();
                    if (oldPin.equals(savedPIN)) {
                        // Update PIN
                        line = "PIN: " + newPin;
                        pinUpdated = true; // Set flag to indicate PIN has been updated
                    } else {
                        JOptionPane.showMessageDialog(this, "Old PIN is incorrect!"); // Display message if old PIN is incorrect
                    }
                }
                userData.append(line).append("\n");
            }
            reader.close();
    
            // Write updated user data to file if PIN has been updated
            if (pinUpdated) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_DIRECTORY + currentAccountNumber + ".txt"));
                writer.write(userData.toString());
                writer.close();
                JOptionPane.showMessageDialog(this, "PIN changed successfully!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    
    

    private void clearFrame() {
        getContentPane().removeAll();
        revalidate();
        repaint();
    }

    private void resetFrame() {
        clearFrame();
        if (currentAccountNumber != 0) {
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to return to the main frame?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                currentAccountNumber = 0; // Reset current account number
                getContentPane().add(registerButton);
                getContentPane().add(loginButton);
                getContentPane().add(exitButton);
            } else {
                loggedInFrame(); // Add buttons for logged-in state
            }
        } else {
            getContentPane().add(registerButton);
            getContentPane().add(loginButton);
            getContentPane().add(exitButton);
        }
    }
    

    public static void main(String[] args) {
        ATMFrame A=new ATMFrame(); // object of constructor
    }
}


