package com.fer.juego.mariobros.caracteres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Roca extends Caracter {

    public static final int ANCHURA = 64;

    public final int VELOCIDAD_INICIAL = 5;

    @Override
    public void dispose() {

    }

    public enum Tipo {
        GRANDE, MEDIANA, PEQUENA
    }

    public Tipo tipo;
    public int vidas;

    public Roca(TextureRegion imagen, int posicionX) {
        super(imagen);
        tipo = Tipo.PEQUENA;
        vidas = 1;
        posicion.x=posicionX;
        posicion.y = Gdx.graphics.getHeight();
        velocidad = new Vector2(0, -VELOCIDAD_INICIAL);
    }

    public Roca(TextureRegion imagen, Tipo tipo) {
        super(imagen);
        velocidad = new Vector2(0, -VELOCIDAD_INICIAL);
        this.tipo = tipo;
        switch (tipo) {
            case PEQUENA:
                vidas = 1;
                break;
            case MEDIANA:
                vidas = 2;
                break;
            case GRANDE:
                vidas = 3;
                break;
        }
    }
    public void caer() {
        mover();
    }
}
