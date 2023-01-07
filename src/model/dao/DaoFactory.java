package model.dao;

import db.DB;
import model.dao.impl.AccountDaoJDBC;
import model.dao.impl.HolderDaoJDBC;

public class DaoFactory {
    public static AccountDao createAccountDao() {
        return new AccountDaoJDBC(DB.getConnection());
    }

    public static HolderDao createHolderDao() {
        return new HolderDaoJDBC(DB.getConnection());
    }
}
