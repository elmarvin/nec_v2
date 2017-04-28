package com.example.pedro.inec2015;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Pedro on 07/08/2016.
 */
public class baseActivity extends ActionBarActivity {
    Menu menu;
    public static final int MenuFactorZ = 1;
    public static final int MenuPerfilSuelo = 2;
    public static final int MenuCoeficienteI = 3;
    public static final int MenuCoeficienteR = 4;
    public static final int MenuCoeficienteCt = 5;
    public static final int MenuConfiguracion = 6;
    public static final int MenuOutput = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.utpl_logo);
        actionBar.setTitle(Html.fromHtml("<small>Espectro de Diseño (NEC 2015)</small>"));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        menu.add(0, MenuFactorZ, 0, "Factor Z");
        menu.add(0, MenuPerfilSuelo, 0, "Perfil Suelo");
        menu.add(0, MenuCoeficienteI, 0, "Coeficiente de Importancia");
        menu.add(0, MenuCoeficienteR, 0, "Coeficiente R");
        menu.add(0, MenuCoeficienteCt, 0, "Coeficientes Ct y a");
        menu.add(0, MenuConfiguracion, 0, "Configuración en planta elevación");
        menu.add(0, MenuOutput, 0, "Datos de Salida");
        return true;
    }

    private MenuItem add(int i, int menu32, int j, String string) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MenuFactorZ:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case MenuPerfilSuelo:
                startActivity(new Intent(this, MainActivityPerfilSuelo.class));
                return true;
            case MenuCoeficienteI:
                startActivity(new Intent(this, coeficiente_importancia.class));
                return true;
            case MenuCoeficienteR:
                startActivity(new Intent(this, coeficiente_r.class));
                return true;
            case MenuCoeficienteCt:
                startActivity(new Intent(this, coeficiente_ct.class));
                return true;
            case MenuConfiguracion:
                startActivity(new Intent(this, configuracion.class));
                return true;
            case MenuOutput:
                startActivity(new Intent(this, datos.class));
                return true;
        }

        return false;
    }

    double valor = 0;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}




