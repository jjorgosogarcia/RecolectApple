package com.example.sadarik.recolectamanzanas;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;


public class Manzana {
    private Bitmap bmp;
    private int ancho, alto;
    private int ejeY = 0;
    private int direccionY;
    private int direccionX;
    private int ejeX = 0;
    private static int anchoMax=0, altoMax=0;

    public Manzana(Bitmap bmp) {
        this.bmp = bmp;
        this.ancho=bmp.getWidth();
        this.alto=bmp.getHeight();
        setPosicion(); //Para que se vaya a la posición 0,0
    }
    public boolean tocado(float x, float y){
        return x > ejeX && x < ejeX + ancho && //esta en la posición x desde donde empieza la figura hasta donde acaba
                y > ejeY && y < ejeY + alto;
    }

    public static void setDimension(int ancho, int alto){
        anchoMax = ancho;
        altoMax = alto;
    }

    public void setPosicion(){ //para calcular la posicion
        ejeX = 0;
        ejeY = 0;
    }

    public void setPosicionAleatorio(int velocidad){
        Random rnd = new Random();
        ejeX = ancho + rnd.nextInt(anchoMax-ancho*2);
        ejeY = 0;
        direccionY=velocidad;
    }

    public void dibujar(Canvas canvas) {
        movimiento();
        canvas.drawBitmap(bmp, ejeX, ejeY, null);
    }

    public void eliminar(){
        direccionX = 0;
        direccionY =0;
        ejeX = 0;
        ejeY = 0;
    }

    private void movimiento(){
        ejeY = ejeY + direccionY;
    }

    public int  getEjeY(){
        return ejeY;
    }

    public int  getEjeX(){
        return ejeX;
    }

    public int  getAlto(){
        return alto;
    }

    public int  getAncho(){
        return ancho;
    }

}