package com.example.DAO;


import com.example.classes.Facture;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;

public class DAOFacture {
    public static ArrayList<Facture> lister(){
        ArrayList<Facture> FL = new ArrayList<>();
        Connection cn= LaConnexion.seConnecter();
        String requete = "select * from Facture;";
        String numFact,refCli;
        Date dateFact;
        float totalFact;
        Facture f;

        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);

            while (resultSet.next()){
                numFact = resultSet.getString(1);
                dateFact = resultSet.getDate(2);
                totalFact = resultSet.getFloat(3);
                refCli = resultSet.getString(4);

                f = new Facture(numFact,dateFact,totalFact,refCli);
                FL.add(f);

            }
        } catch (SQLException e) {
            System.out.println("Probleme de consultation  : "+e.getMessage() );
        }
        return FL;
    }


    public static Facture chercher(String numFact){
        Facture f = null;

        Connection cn = LaConnexion.seConnecter();
        String requete = "select * from Facture where numFacture = ?";
        try {
            PreparedStatement preparedStatement = cn.prepareStatement(requete);
            preparedStatement.setString(1,numFact);
            ResultSet resultSet = preparedStatement.executeQuery();
            String numFacture ,refCli;
            Date dateFact;
            float totalFact;
            if(resultSet!= null){
                while(resultSet.next()){
                    numFacture = resultSet.getString("numFacture");
                    dateFact = resultSet.getDate("dateFacture");
                    totalFact = resultSet.getFloat("total");
                    refCli = resultSet.getString("refCli");

                    f = new Facture(numFacture,dateFact,totalFact,refCli);
                }
            }
        } catch (SQLException e) {
            System.out.println("probleme de requette de select  : "+ e.getMessage());
        }

        return f;
    }


    public static boolean ajouter(String num, Date date, Float total, String refCli){
        Connection cn = LaConnexion.seConnecter();
        String requete = "insert into Facture values('"+num +"','"+ date+"','"+total+"','"+ refCli+"');";
        Statement statement = null;
        try {
            statement = cn.createStatement();
            int n = statement.executeUpdate(requete);
            if (n>=1){
                System.out.println("Ajout reussi de "+n+" lignes");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean modifier(String num, Date date, Float total, String refCli){
        Connection cn = LaConnexion.seConnecter();
        String requete = "UPDATE Facture SET numFacture = '"+num+"',dateFacture = '"+date+"',total = '"+total+"'Where refCli = '"+refCli +"';";
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

    public static boolean archiver(String num){
        Connection cn = LaConnexion.seConnecter();
        String requete = "DELETE FROM Facture Where numFacture = '"+num +"';";
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
