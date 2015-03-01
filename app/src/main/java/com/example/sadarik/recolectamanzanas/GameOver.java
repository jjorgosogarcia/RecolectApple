package com.example.sadarik.recolectamanzanas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class GameOver extends Activity {


    private final int SPLASH_DISPLAY_LENGTH = 9000;
    private TextView tvRecolectadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Bundle b = getIntent().getExtras();
        int recolecta = b.getInt("recolecta");
        tvRecolectadas=(TextView)findViewById(R.id.manzanasTotales);
        tvRecolectadas.setText(""+recolecta);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(GameOver.this,Principal.class);
                GameOver.this.startActivity(mainIntent);
                GameOver.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
