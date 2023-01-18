package model.entities;

import java.io.Serial;
import java.io.Serializable;

public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String number;
    private Holder holder;
    protected Double balance = 00.0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account() {
    }

    public Account(String number,Double balance, Holder holder) {
        this.number = number;
        this.balance = balance;
        this.holder = holder;
    }

    public Account(String number, Holder holder, Double balance) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Holder getHolder() {
        return holder;
    }

    public void setHolder(Holder holder) {
        this.holder = holder;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void withdraw(Double amount) {
        balance -= amount + 5.0;
    }
    public void deposit(Double amount) {
        balance += amount;
    }

    @Override
    public String toString() {
    	return "Account id = " + id + ", account number = " + number + ", balance= R$" + String.format("%.2f",balance) + "\n" +
    			"Holder = " + holder + "\n";
    			
    }
}
