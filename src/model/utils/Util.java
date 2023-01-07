package model.utils;

import model.dao.AccountDao;
import model.dao.DaoFactory;
import model.dao.HolderDao;
import model.entities.Account;
import model.entities.Holder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Util {

    public static void createAccount() throws ParseException {

        AccountDao accountDao = DaoFactory.createAccountDao();
        HolderDao holderDao = DaoFactory.createHolderDao();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Your name: ");
        String name = scanner.nextLine();

        System.out.print("Document: ");
        String document = scanner.nextLine();

        System.out.print("Birth date (dd/mm/yyyy): ");
        String date = scanner.nextLine();

        String number = generateNumber();

        System.out.print("Initial balance: ");
        double balance = scanner.nextDouble();

        Holder hd = new Holder(name,document,sdf.parse(date));

        Account acc = new Account(number,balance,hd);

        holderDao.insert(hd);
        accountDao.insert(acc);
    }

    public static String generateNumber() {
        Random rd = new Random();
        String number = "";
        for (int i = 0; i < 8; i++) {
            int n = rd.nextInt(10);
            number += Integer.toString(n);
        }
        return number;
    }

    public static void transfer() {
        AccountDao accountDao = DaoFactory.createAccountDao();
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the account number that will transfer the money: ");
        String number1 = input.nextLine();
        System.out.print("Enter the account number that will receive the money: ");
        String number2 = input.nextLine();
        System.out.print("Enter the transfer amount: ");
        double amount = input.nextDouble();

        Account acc1 = accountDao.findByNumber(number1);
        Account acc2 = accountDao.findByNumber(number2);

        accountDao.transfer(acc1,acc2,amount);
    }

    public static void deposit() {
        AccountDao accountDao = DaoFactory.createAccountDao();
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the account number: ");
        String number = input.nextLine();
        System.out.print("Enter the deposit amount: ");
        double amount = input.nextDouble();

        Account acc = accountDao.findByNumber(number);

        accountDao.deposit(acc,amount);

    }

    public static void withdraw() {
        AccountDao accountDao = DaoFactory.createAccountDao();
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the account number: ");
        String number = input.nextLine();
        System.out.print("Enter the withdraw amount: ");
        double amount = input.nextDouble();

        Account acc = accountDao.findByNumber(number);

        accountDao.withdraw(acc,amount);
    }

    public static void listAll() {
        AccountDao accountDao = DaoFactory.createAccountDao();

        List<Account> list = accountDao.findAll();

        list.forEach(System.out::println);
    }

    public static void menu() throws ParseException {

        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("=======================================================");
            System.out.println("                            MENU");
            System.out.println("=======================================================");
            System.out.println("""
                    1- Create Account
                    2- Deposit
                    3- Withdraw
                    4- Transfer
                    5- List all accounts and break menu
                    """);
            System.out.print("Choice: ");
            int choice = input.nextInt();

            if (choice == 1) {
                createAccount();
            }
            if (choice == 2) {
                deposit();
            }
            if (choice == 3) {
                withdraw();
            }
            if (choice == 4) {
                transfer();
            }
            if (choice == 5) {
                listAll();
                input.close();
                break;
            }
        }
    }
}
