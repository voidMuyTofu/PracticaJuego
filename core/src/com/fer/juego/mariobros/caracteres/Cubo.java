package com.fer.juego.mariobros.caracteres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.fer.juego.mariobros.managers.R;


public class Cubo extends Caracter {

    public final int VELOCIDAD_INICIAL = 10;

    public int vidas;
    public int municion;

    @Override
    public void dispose() {

    }

    public enum Estado{
        DERECHA,
        IZQUIERDA,
        PARADO,
    }

    private Animation<TextureAtlas.AtlasRegion> animacionDerecha;
    private Animation<TextureAtlas.AtlasRegion> animacionIzquierda;
    private Animation<TextureAtlas.AtlasRegion> animacionDisparo;
    public Estado estado;
    private float tiempo;

    public Cubo(TextureRegion imagen) {
        super(imagen);
        vidas = 3;
        municion = 0;
        velocidad = new Vector2(VELOCIDAD_INICIAL, 0);

        animacionDerecha = new Animation<TextureAtlas.AtlasRegion>(0.35f , R.getAnimacion("nave_dch"));
        animacionIzquierda= new Animation<TextureAtlas.AtlasRegion>(0.35f,R.getAnimacion("nave_iz"));
        animacionDisparo= new Animation<TextureAtlas.AtlasRegion>(0.35f,R.getAnimacion("disparo"));

        estado = Estado.PARADO;
    }
    public void actualizar(float dt){
        tiempo += dt;
        switch(estado){
            case DERECHA:
                imagen = animacionDerecha.getKeyFrame(tiempo,false);
                break;
            case IZQUIERDA:
                imagen = animacionIzquierda.getKeyFrame(tiempo,false);
                break;
            case PARADO:
                imagen = R.getTextureRegion("nave_parado");
                break;
        }
    }
    public Nube disparar(){
        Nube nube = new Nube(R.getTextureRegion("disparo"));
        nube.imagen = animacionDisparo.getKeyFrame(Gdx.graphics.getDeltaTime(),true);
        nube.posicion.x = posicion.x + (tamano.x /2) - nube.tamano.x/2;
        nube.posicion.y = posicion.y + tamano.y;
        return nube;
    }
}
