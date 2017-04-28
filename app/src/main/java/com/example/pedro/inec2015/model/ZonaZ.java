package com.example.pedro.inec2015.model;

/**
 * Created by Pedro on 27/07/2016.
 */
public class ZonaZ {
    public int id;
    public String poblacion;
    public String parroquia;
    public String canton;
    public String provincia;
    public String factor;

    public ZonaZ(int id, String poblacion, String parroquia, String canton, String provincia, String factor) {
        this.id = id;
        this.poblacion = poblacion;
        this.parroquia = parroquia;
        this.canton = canton;
        this.provincia = provincia;
        this.factor = factor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

}
