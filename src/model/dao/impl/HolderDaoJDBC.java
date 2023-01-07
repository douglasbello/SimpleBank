package model.dao.impl;

import db.DB;
import db.DBException;
import model.dao.HolderDao;
import model.entities.Holder;

import java.sql.*;
import java.text.SimpleDateFormat;

public class HolderDaoJDBC implements HolderDao {
    private Connection conn;

    public HolderDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    @Override
    public void insert(Holder hd) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        PreparedStatement st = null;

        try {
            conn.setAutoCommit(false);
            st = conn.prepareStatement("INSERT INTO holder (name,document,birthDate) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1,hd.getName());
            st.setString(2,hd.getDocument());
            st.setDate(3,new java.sql.Date(hd.getBirthDate().getTime()));

            int rowsAffected = st.executeUpdate();

            conn.commit();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    hd.setId(id);
                } DB.closeResultSet(rs);
            } else {
                throw new DBException("Unexpected error: No lines are affected.");
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
}
