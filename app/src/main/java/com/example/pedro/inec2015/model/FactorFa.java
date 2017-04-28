package com.example.pedro.inec2015.model;

/**
 * Created by Pedro on 01/08/2016.
 */
public class FactorFa {
    public int id;
    public String fa;
    public String uno;
    public String dos;
    public String tres;
    public String tresc;
    public String cuatro;
    public String cinco;

    public FactorFa(int id, String fa, String uno, String dos, String tres, String tresc, String cuatro, String cinco) {
        this.id = id;
        this.fa = fa;
        this.uno = uno;
        this.dos = dos;
        this.tres = tres;
        this.tresc = tresc;
        this.cuatro = cuatro;
        this.cinco = cinco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFa() {
        return fa;
    }

    public void setFa(String fa) {
        this.fa = fa;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getDos() {
        return dos;
    }

    public void setDos(String dos) {
        this.dos = dos;
    }

    public String getTres() {
        return tres;
    }

    public void setTres(String tres) {
        this.tres = tres;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getCuatro() {
        return cuatro;
    }

    public void setCuatro(String cuatro) {
        this.cuatro = cuatro;
    }

    public String getCinco() {
        return cinco;
    }

    public void setCinco(String cinco) {
        this.cinco = cinco;
    }
}
