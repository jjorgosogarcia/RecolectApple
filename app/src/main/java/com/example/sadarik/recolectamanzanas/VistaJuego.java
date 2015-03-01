package com.example.sadarik.recolectamanzanas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;

public class VistaJuego  extends SurfaceView implements SurfaceHolder.Callback  {


    private Bitmap cestaLlena, cestaVacia, imagengusano,imagenmanzana, background, vida,vida2,vida3;
    private int alto, ancho;
    private HebraJuego hebraJuego;
    private Gusano[] gusanos;
    private Manzana[] manzanas;
    private int manzanasRecogidas=0;
    private int vidas=3;
    private Context context;
    private Random r;
    private long ultimoClick = 0;
    private MediaPlayer mp1,jump,musica;
    private int min=1;
    private int max=10;

    /*******************************************************************************************/
    /*                                   Constructor                                           */
    /*******************************************************************************************/

    public VistaJuego(Context contexto){
        super(contexto);
        this.context=contexto;
        getHolder().addCallback(this);
        mp1=MediaPlayer.create(contexto,R.raw.manzana);
        jump=MediaPlayer.create(contexto,R.raw.gusano);
        musica=MediaPlayer.create(contexto,R.raw.musica);
        initComponents();

        for (int i = 0; i < gusanos.length; i++) {
            gusanos[i] = new Gusano(imagengusano);
        }

        for (int j = 0; j < manzanas.length; j++) {
            manzanas[j] = new Manzana(imagenmanzana);
        }
    }

    /*******************************************************************************************/
    /*                    Métodos de la clase surfaceview                                      */
    /*******************************************************************************************/

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(background, 0, 0, null);
        r = new Random();

        for(Gusano b: gusanos){
            b.dibujar(canvas);
        }
        for(Manzana m: manzanas){
            m.dibujar(canvas);
        }

    // Iconos que cambian dependiendo de las circunstancias
        if(manzanasRecogidas==0){
            canvas.drawBitmap(cestaVacia, 0, 0, null);
        }else{
            canvas.drawBitmap(cestaLlena, 0, 0, null);
        }

        if(vidas==0){
            Intent i = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("recolecta", manzanasRecogidas);
            i.putExtras(bundle);
            ((Activity) context).setResult(Activity.RESULT_OK, i);
            ((Activity) context).finish();
        }
        if(vidas==1){
            canvas.drawBitmap(vida, getWidth()-60, 32, null);

        }if (vidas==2){
            canvas.drawBitmap(vida,  getWidth()-60, 32, null);
            canvas.drawBitmap(vida2, getWidth()-110, 32, null);
        }
        if (vidas==3){
            canvas.drawBitmap(vida, getWidth()-160, 32, null);
            canvas.drawBitmap(vida2, getWidth()-110, 32, null);
            canvas.drawBitmap(vida3, getWidth()-60, 32, null);
        }

        for (int j=0;j<manzanas.length;j++){
            if (manzanas[j].getEjeY()>alto){
                int i1 = r.nextInt(max - min) + min;
                manzanas[j].setPosicionAleatorio(i1);
            }
        }

        for (int i=0;i<gusanos.length;i++){
            if (gusanos[i].getEjeY()>alto){
                int i1 = r.nextInt(max - min) + min;
                gusanos[i].setPosicionAleatorio(i1);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - ultimoClick > 300) {
            ultimoClick = System.currentTimeMillis(); //recogemos el tiempo
            float x, y;
            x = event.getX();
            y = event.getY();
            synchronized (getHolder()) {
                for (Gusano g : gusanos) {
                    if (g.tocado(x, y)) { //Si he tocado la figura...
                        mp1.start();
                        g.eliminar(); // new Figura(this, bmp);
                        vidas--;
                        g.setPosicionAleatorio((max - min) + min); //Aparece en una nueva posición
                        break;
                    }
                }
                for (Manzana m : manzanas) {
                    if (m.tocado(x, y)) {
                        jump.start();
                        m.eliminar();
                        manzanasRecogidas++;
                        m.setPosicionAleatorio((max - min) + min);
                        max= max +5;
                        min = min +2;
                        break;
                    }
                }
            }
        }
        return true;
    }

    /*******************************************************************************************/
    /*                                  Interfaz callback                                      */
    /*******************************************************************************************/

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        hebraJuego = new HebraJuego(this);
        hebraJuego.setFuncionando(true);
        hebraJuego.start();
    }

    @Override
    public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {
        alto=height;
        ancho=width;
        Gusano.setDimension(ancho,alto);

        for (int i=0;i<gusanos.length;i++) {
            gusanos[i].setPosicionAleatorio(660);
        }

        Manzana.setDimension(ancho,alto);
        for (int j=0;j<manzanas.length;j++) {
            manzanas[j].setPosicionAleatorio(660);
        }
        musica.setLooping(true);
        musica.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean reintentar = true;
        musica.pause();
        hebraJuego.setFuncionando(false);
        while (reintentar) {
            try {
                hebraJuego.join();
                reintentar = false;
            } catch (InterruptedException e) {
            }
        }
    }

    /*******************************************************************************************/
    /*                                  Metodos auxiliares                                     */
    /*******************************************************************************************/

    public void initComponents(){
        background = BitmapFactory.decodeResource(getResources(), R.drawable.ic_fondo);
        vida = BitmapFactory.decodeResource(getResources(), R.drawable.life);
        vida2 = BitmapFactory.decodeResource(getResources(), R.drawable.life);
        vida3 = BitmapFactory.decodeResource(getResources(), R.drawable.life);
        cestaVacia = BitmapFactory.decodeResource(getResources(), R.drawable.cesta);
        cestaLlena = BitmapFactory.decodeResource(getResources(), R.drawable.cestal);
        imagengusano = BitmapFactory.decodeResource(getResources(), R.drawable.worm);
        imagenmanzana = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
        hebraJuego = new HebraJuego(this);
        gusanos = new Gusano[8];
        manzanas = new Manzana[2];
    }
}