package com.example.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client implements Comparable<Client>{
    private StringProperty codeCli,nomCli,adresseCli,emailCli;

    public Client(String codeCli, String nomCli, String adresseCli, String emailCli) {
        this.codeCli = new SimpleStringProperty(codeCli);
        this.nomCli = new SimpleStringProperty(nomCli);
        this.adresseCli =  new SimpleStringProperty(adresseCli);
        this.emailCli =  new SimpleStringProperty(emailCli);
    }



    @Override
    public String toString() {
        return  "-----------------------------\n"+
                "codeCli : " + codeCli + "\n"+
                "nomCli : " + nomCli + "\n"+
                "adresseCli : " + adresseCli + "\n"+
                "emailCli : " + emailCli +
                "\n-----------------------------\n";
    }

    @Override
    public int compareTo(Client c) {
        int c1 = Integer.parseInt(this.codeCli.get());
        int c2 = Integer.parseInt(c.codeCli.get());
        if (c1>c2){
            return 1;
        } else if (c1<c2) {
            return -1;
        }
        return 0;
    }

    public String getCodeCli() {
        return codeCli.get();
    }

    public void setCodeCli(String codeCli) {
        this.codeCli.set(codeCli);
    }

    public String getNomCli() {
        return nomCli.get();
    }

    public void setNomCli(String nomCli) {
        this.nomCli.set(nomCli);
    }

    public String getAdresseCli() {
        return adresseCli.get();
    }

    public void setAdresseCli(String adresseCli) {
        this.adresseCli.set(adresseCli);
    }

    public String getEmailCli() {
        return emailCli.get();
    }

    public void setEmailCli(String emailCli) {
        this.emailCli.set(emailCli);
    }
}
