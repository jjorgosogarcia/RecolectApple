package com.example.sadarik.recolectamanzanas;


import android.app.Activity;
import android.os.Bundle;

public class Play extends Activity {

    private VistaJuego vj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vj = new VistaJuego(this);
        setContentView(vj);
    }
}