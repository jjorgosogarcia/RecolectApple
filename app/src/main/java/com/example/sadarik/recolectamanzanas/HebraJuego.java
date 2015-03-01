package com.example.sadarik.recolectamanzanas;

import android.graphics.Canvas;

public class HebraJuego extends Thread{

    private VistaJuego vista;
    private boolean funcionando = false;
    private static final long FPS = 20;


    public HebraJuego(VistaJuego vj) {
        this.vista = vj;
    }

    public void setFuncionando(boolean f) {
        funcionando = f;
    }

    @Override
    public void run() {
        long inicio;
        long ticksPS = 1000 / FPS; //Cada 50 milisegundos se dibuja una imagen nueva por ser 20FPS
        long tiempoEspera;
        while (funcionando) {
            Canvas canvas = null;
            inicio = System.currentTimeMillis(); //Se calcula el tiempo de inicio
            try {
                canvas = vista.getHolder().lockCanvas(); //Se bloquea el canvas
                synchronized (vista.getHolder()) { //Garantiza que nadie mas se meta en la hebra
                    vista.draw(canvas);
                }

            } catch (NullPointerException e){
                funcionando =false;
            }
            finally {
                if (canvas != null) {
                    vista.getHolder().unlockCanvasAndPost(canvas); //Se desbloquea el canvas
                }
            }
            tiempoEspera = ticksPS - (System.currentTimeMillis()-inicio);
            try {
                if (tiempoEspera > 0)
                    sleep(tiempoEspera);
                else
                    sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}