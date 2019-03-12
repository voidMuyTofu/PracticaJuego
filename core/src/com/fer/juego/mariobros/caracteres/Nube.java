package com.fer.juego.mariobros.caracteres;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Nube extends Caracter{
    public Nube(TextureRegion imagen) {
        super(imagen);
        velocidad = new Vector2(0,10);
    }

    @Override
    public void dispose() {

    }
}
