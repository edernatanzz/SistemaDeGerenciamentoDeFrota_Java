package com.frota.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.NamingException;

public class DatabaseConnection {
    
    // Configurações do banco
    private static final String URL = "jdbc:mysql://localhost:3308/frotaDB";
    private static final String USERNAME = "myuser";
    private static final String PASSWORD = "1234";
    
    private Connection connection = null;

    public Connection getConnection() throws   InstantiationException, IllegalAccessException, ClassNotFoundException,   SQLException, NamingException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        Connection conexao  = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conexao;
    }

    public void close (){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
