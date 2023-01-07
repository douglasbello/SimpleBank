package model.dao.impl;

import db.DB;
import db.DBException;
import model.dao.AccountDao;
import model.entities.Account;
import model.entities.Holder;

import java.sql.*;
import java.util.*;

public class AccountDaoJDBC implements AccountDao {
    public Connection conn;

    public AccountDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Account obj) {
        PreparedStatement st = null;
        try {
            conn.setAutoCommit(false);
            st = conn.prepareStatement("INSERT INTO account (number,holder,balance) VALUES (?,?,?)"
                    ,Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNumber());
            st.setInt(2,obj.getHolder().getId());
            st.setDouble(3,obj.getBalance());

            int rowsAffected = st.executeUpdate();

            conn.commit();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DBException("Unexpected error. No lines are affected.");
            }
        } catch (SQLException exception) {
            try {
                conn.rollback();
                throw new DBException("Transation rolled back! Caused by: " + exception.getMessage());
            } catch (SQLException ex) {
                throw new DBException("Error trying to rollback! Caused by: " + ex.getMessage());
            }
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void withdraw(Account obj,Double amount) {
        obj.withdraw(amount);

        update(obj);
    }

    @Override
    public void deposit(Account obj,Double amount) {
        obj.deposit(amount);

        update(obj);
    }

    public void update(Account obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE account SET number = ?, balance = ?, holder = ? WHERE id = ?");
            st.setString(1, obj.getNumber());
            st.setDouble(2,obj.getBalance());
            st.setInt(3,obj.getHolder().getId());
            st.setInt(4,obj.getId());

            st.executeUpdate();

        } catch (SQLException exception) {
            throw new DBException(exception.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    public void transfer(Account acc1,Account acc2,Double amount) {
        acc1.withdraw(amount);
        acc2.deposit(amount);

        update(acc1);
        update(acc2);
    }


    @Override
    public Account findByNumber(String number) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT a.*,h.name,h.document,h.birthDate FROM account a INNER JOIN " +
                    "holder h " +
                    "ON a.holder = h.id WHERE a.number = ?");
            st.setString(1,number);
            rs = st.executeQuery();

            if (rs.next()) {
                Holder holder = instantiateHolder(rs);

                Account acc = instantiateAccount(rs,holder);
                return acc;
            }
            return null;
        } catch (SQLException exception) {
            throw new DBException(exception.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Account> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT a.*,h.name,h.document,h.birthDate FROM account a " +
                    "INNER JOIN holder h ON a.holder = h.id ORDER BY h.name");

            rs = st.executeQuery();

            List<Account> listAcc = new ArrayList<>();

            Map<Integer,Holder> listHolder = new HashMap<>();

            while (rs.next()) {
                Holder hd = listHolder.get(rs.getInt("holder"));

                if (hd == null) {
                    hd = instantiateHolder(rs);
                    listHolder.put(rs.getInt("holder"),hd);
                }
                Account acc = instantiateAccount(rs,hd);
                listAcc.add(acc);
            }
            return listAcc;
        } catch (SQLException exception) {
            throw new DBException(exception.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }


    }

    private Account instantiateAccount(ResultSet rs, Holder holder) throws SQLException {
        Account acc = new Account();
        acc.setId(rs.getInt("id"));
        acc.setHolder(holder);
        acc.setBalance(rs.getDouble("balance"));
        acc.setNumber(rs.getString("number"));
        return acc;
    }

    private Holder instantiateHolder(ResultSet rs) throws SQLException {
        Holder hd = new Holder();
        hd.setId(rs.getInt("id"));
        hd.setDocument(rs.getString("document"));
        hd.setName(rs.getString("name"));
        hd.setBirthDate(rs.getDate("birthDate"));
        return hd;
    }
}
