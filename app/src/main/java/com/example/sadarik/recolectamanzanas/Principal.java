package com.example.sadarik.recolectamanzanas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class Principal extends Activity {

    private final static int PARTIDA=1;
    private int manzanas=0, record;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        SharedPreferences pc;
        pc = PreferenceManager.getDefaultSharedPreferences(
                getApplicationContext());
        record=pc.getInt("record",0);
        tv=(TextView)findViewById(R.id.tvRecord);
        tv.setText(getString(R.string.record) + " " + record);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK &&
                requestCode == PARTIDA) {
            manzanas = data.getIntExtra("recolecta", 0);
            if (record<manzanas){
                tv.setText(manzanas+"");
                SharedPreferences pc;
                pc = PreferenceManager.getDefaultSharedPreferences(
                        getApplicationContext());
                SharedPreferences.Editor ed = pc.edit();
                ed.putInt("record", manzanas);
                ed.commit();
            }
            Intent i=new Intent(this,GameOver.class);
            i.putExtra("recolecta",manzanas);
            startActivity(i);
        }
    }

    public void play(View view){
        Intent i= new Intent(this, Play.class);
        startActivityForResult(i, PARTIDA);
    }

    public void exit(View view){
        this.finish();
    }
}
