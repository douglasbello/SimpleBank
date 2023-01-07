package model.dao;

import model.entities.Account;
import model.entities.Holder;

import java.util.List;

public interface AccountDao {
    void insert(Account obj);
    void withdraw(Account obj,Double amount);
    void deposit(Account obj,Double amount);
    Account findByNumber(String number);
    void transfer(Account acc1,Account acc2,Double amount);
    void update(Account obj);
    List<Account> findAll();
}
