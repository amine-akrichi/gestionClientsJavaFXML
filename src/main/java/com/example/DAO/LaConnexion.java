package com.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LaConnexion {
    private static Connection con;
    private static String user,password;

    public static Connection seConnecter() {
        if (con == null){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdGestion",user,password);

            } catch (ClassNotFoundException e) {
                System.out.println("Driver n'est pas trouve "+e.getMessage());
            }
            catch (SQLException e) {
                System.out.println("Base de donnee n'est pas trouve ou problème d'inthentification "+e.getMessage());
            }
        }


        return con;
    }

    public void setUser(String user) {
        LaConnexion.user = user;
    }

    public void setPassword(String password) {
        LaConnexion.password = password;
    }
}
