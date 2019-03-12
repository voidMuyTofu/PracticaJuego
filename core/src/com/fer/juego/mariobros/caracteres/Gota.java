    package com.fer.juego.mariobros.caracteres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

    public class Gota extends Caracter {

    public final int VELOCIDAD_INICIAL = 5;
    public static final int ANCHURA = 64;


    public Gota(TextureRegion imagen, int posicionX) {
        super(imagen);
        posicion.y = Gdx.graphics.getHeight();
        posicion.x = posicionX;

        velocidad = new Vector2(0, -VELOCIDAD_INICIAL);
    }
    @Override
    public void dispose() {

    }
    public void caer() {
        mover();
    }
}
