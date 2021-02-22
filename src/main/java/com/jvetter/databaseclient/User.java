package com.jvetter.databaseclient;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class User {
    private String first;
    private String last;

    public User(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public static void initializeDatabase() {
        String conn_url = "jdbc:sqlite:user.db";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(conn_url);

            String createTableSql = "CREATE TABLE IF NOT EXISTS user (\n"
                    + "	first text NOT NULL,\n"
                    + "	last text NOT NULL\n"
                    + ");";

            Statement statement = conn.createStatement();
            statement.executeUpdate(createTableSql);

            int rowCount = getRowCount(conn);

            if (rowCount == 0) {
                String insertUsersSql = "INSERT INTO user (first, last) VALUES('John', 'Lennon'), ('Paul', 'McCartney'), ('George', 'Harrison'), ('Ringo', 'Star');";
                Statement insertStatement = conn.createStatement();
                insertStatement.executeUpdate(insertUsersSql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ObservableList<User> getUsers(String conn_url, String sqlStatement) throws ClassNotFoundException {
        ObservableList<User> ret_val = FXCollections.observableArrayList();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(conn_url);
            ResultSet rs = null;

            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlStatement);

            while (rs != null && rs.next()) {
                String first = rs.getString(1);
                String last = rs.getString(2).toString();
                ret_val.add(new User(first, last));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret_val;
    }

    private static int getRowCount(Connection conn) {
        Statement s = null;
        int count = 0;
        try {
            s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM user");
            r.next();
            count = r.getInt("rowcount");
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return count;
        }
    }
}
