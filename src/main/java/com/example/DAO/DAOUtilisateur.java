package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOUtilisateur {
    public static boolean login(String username, String password) {
        Connection cn= LaConnexion.seConnecter();
        PreparedStatement ps=null;
        boolean verif = false;
        String requete = "select * from utilisateur where username = ? and password = ?";
        try {
            ps = cn.prepareStatement(requete);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet resultSet = ps.executeQuery();
            verif = resultSet.next();
        } catch (Exception e) {
            System.out.println(e.getMessage() );
        }
        return verif;
    }

    public static void ajouter(String username, String password) {
        Connection cn= LaConnexion.seConnecter();
        PreparedStatement ps=null;
        String requete = "insert into utilisateur values(?,?)";
        try {
            ps = cn.prepareStatement(requete);
            ps.setString(1,username);
            ps.setString(2,password);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage() );
        }
    }
}
