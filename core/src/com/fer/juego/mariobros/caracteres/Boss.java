package com.fer.juego.mariobros.caracteres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.fer.juego.mariobros.managers.R;

public class Boss extends Caracter{
    public enum Estado{
        VIVO,
        MUERTO
    }
    public Estado estado;
    long tiempo;
    public final int VELOCIDAD_INICIAL = 6;
    public Animation<TextureAtlas.AtlasRegion> animacionMuerte;
    public Boss(TextureRegion imagen){
        super(imagen);
        estado = Estado.VIVO;
        posicion.set((Gdx.graphics.getWidth()/2)-((tamano.x/3)),Gdx.graphics.getHeight()-(tamano.y*1.34f));
        velocidad = new Vector2(VELOCIDAD_INICIAL, 0);
        animacionMuerte = new Animation<TextureAtlas.AtlasRegion>(0.35f,R.getAnimacion("explosion"));
    }

    public void actualizar(float dt){
        tiempo += dt;
        switch(estado){
            case VIVO:
                imagen = R.getTextureRegion("navefinal");
                break;
            case MUERTO:
                imagen = animacionMuerte.getKeyFrame(tiempo,false);
                break;
        }
    }

    public DisparoBoss disparar(){
        DisparoBoss disparo = new DisparoBoss(R.getTextureRegion("Missile"));
        disparo.posicion.x = posicion.x + (tamano.x /2) - disparo.tamano.x/2;
        disparo.posicion.y = posicion.y + tamano.y;
        return disparo;
    }

    @Override
    public void dispose() {

    }
}
