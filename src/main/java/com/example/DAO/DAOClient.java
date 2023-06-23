package com.example.DAO;



import com.example.classes.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class DAOClient {
    public static ArrayList<Client> lister(){
        ArrayList<Client> Cl = new ArrayList<>();
        Connection cn= LaConnexion.seConnecter();
        String requete = "select * from Client;";
        String codeCl, nomCl, adrCl, emailCl;
        Client c;

        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);

            while (resultSet.next()){
                codeCl = resultSet.getString(1);
                nomCl = resultSet.getString(2);
                adrCl = resultSet.getString(3);
                emailCl = resultSet.getString(4);

                c = new Client(codeCl,nomCl,adrCl,emailCl);
                Cl.add(c);

            }
        } catch (SQLException e) {
            System.out.println("Probleme de consultation  : "+e.getMessage() );
        }
        return Cl;
    }





    public static boolean ajouter(String code, String nom, String adr, String email){
        Connection cn = LaConnexion.seConnecter();
        String requete = "insert into Client values('"+code +"','"+ nom+"','"+  adr+"','"+ email+"');";
        Statement statement = null;
        try {
            statement = cn.createStatement();
            int n = statement.executeUpdate(requete);
            if (n>=1){
                System.out.println("Ajout reussi");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean modifier(String code, String nom, String adr, String email){
        Connection cn = LaConnexion.seConnecter();
        String requete = "UPDATE Client SET nomCli = '"+nom+"',adresseCli = '"+adr+"',emailCli = '"+email+"'Where codeCli = '"+code +"';";
        Statement statement = null;
        try {
            statement = cn.createStatement();
            int n = statement.executeUpdate(requete);
            if (n>=1){
                System.out.println("Modification reussite");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean archiever(String code){
        Connection cn = LaConnexion.seConnecter();
        String requete = "DELETE FROM Client Where codeCli = '"+code +"';";
        Statement statement = null;
        try {
            statement = cn.createStatement();
            int n = statement.executeUpdate(requete);
            if (n>=1){
                System.out.println("Archivation reussite");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}

