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

import db.DB;

public class Menu {
	
	private static final Scanner scanner = new Scanner(System.in);

    public static void createAccount() throws ParseException {

        AccountDao accountDao = DaoFactory.createAccountDao();
        HolderDao holderDao = DaoFactory.createHolderDao();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.print("Your name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Document: ");
        String document = scanner.nextLine();

        System.out.print("Birth date (dd/mm/yyyy): ");
        String date = scanner.nextLine();

        String number = generateAccountNumber();

        System.out.print("Initial balance: ");
        double balance = scanner.nextDouble();

        Holder hd = new Holder(name,document,sdf.parse(date));

        Account acc = new Account(number,balance,hd);

        holderDao.insert(hd);
        accountDao.insert(acc);
    }

    public static String generateAccountNumber() {
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
        System.out.print("Enter the account id that will transfer the money: ");
        Integer id1 = scanner.nextInt();
        System.out.print("Enter the account id that will receive the money: ");
        Integer id2 = scanner.nextInt();
        System.out.print("Enter the transfer amount: ");
        double amount = scanner.nextDouble();

        Account acc1 = accountDao.findById(id1);
        Account acc2 = accountDao.findById(id2);

        accountDao.transfer(acc1,acc2,amount);
    }

    public static void deposit() {
        AccountDao accountDao = DaoFactory.createAccountDao();
        System.out.print("Enter the account id: ");
        Integer id = scanner.nextInt();
        System.out.print("Enter the deposit amount: ");
        double amount = scanner.nextDouble();

        Account acc = accountDao.findById(id);

        accountDao.deposit(acc,amount);
    }

    public static void withdraw() {
        AccountDao accountDao = DaoFactory.createAccountDao();
        System.out.print("Enter the account id: ");
        Integer id = scanner.nextInt();
        System.out.print("Enter the withdraw amount: ");
        double amount = scanner.nextDouble();

        Account acc = accountDao.findById(id);

        accountDao.withdraw(acc,amount);
    }

    public static void listAll() {
        AccountDao accountDao = DaoFactory.createAccountDao();

        List<Account> list = accountDao.findAll();

        list.forEach(System.out::println);
    }

    public static void menu() throws ParseException {

        while (true) {
            System.out.println("=======================================================");
            System.out.println("                            MENU");
            System.out.println("=======================================================");
            System.out.println("""
                    1- Create Account
                    2- Deposit
                    3- Withdraw
                    4- Transfer
                    5- List all accounts
                    6- Break menu
                    """);
            System.out.print("Choice: ");
            int choice = scanner.nextInt();

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
            }
            if (choice == 6) {
            	System.out.println("END");
            	break;
            }
        }
        scanner.close();
        DB.closeConnection();
    }
}