package com.fer.juego.mariobros.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.fer.juego.mariobros.caracteres.*;
import com.fer.juego.mariobros.managers.R;

import static com.fer.juego.mariobros.caracteres.Boss.Estado.MUERTO;


public class PantallaFinal implements Screen {

    boolean musicaActiva;
    SpriteBatch batch;
    Cubo cubo;
    Nube nube;
    Boss boss;
    DisparoBoss disparoBoss;
    DisparoBoss disparoBoss2;
    boolean estaPausado = false;
    final float RITMO_GOTAS = 0.5f;
    int vidas = 3;
    int vidaBoss = 100;
    Vector2 velocidad;
    BitmapFont font;
    Music musicaInicial;
    Music musicaFinal;
    Texture fondo;
    public PantallaFinal(Music musicaInicial, boolean musicaActiva){
        this.musicaInicial = musicaInicial;
        this.musicaActiva = musicaActiva;
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        cubo = new Cubo(R.getTextureRegion("nave_parado"));
        boss = new Boss(R.getTextureRegion("navefinal"));
        velocidad = new Vector2(6,0);
        font = new BitmapFont();

        fondo = new Texture(Gdx.files.internal("core/assets/sprites/fondo_final.png"));
        musicaFinal = R.getMusica("core/assets/sounds/undertreeinrain.mp3");
        if(musicaActiva) {

            musicaFinal.setVolume(0.1f);
            musicaFinal.setLooping(true);
            musicaFinal.play();
        }


    }

    @Override
    public void render(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
            estaPausado = !estaPausado;

        if (!estaPausado) {

            comprobarTeclado();
            disparosEnemigo();
            cubo.actualizar(dt);
            comprobarBordes();

            moverNave();



            if(nube!=null) {
                nube.mover();
                if(nube.rect.overlaps(boss.rect)){
                    if(vidaBoss>0){
                        vidaBoss = vidaBoss-10;
                        R.getSonido("core/assets/sounds/laser1.wav").play();
                        nube=null;
                    }else{
                        float delay = 3;
                        Timer.schedule(new Timer.Task(){
                            @Override
                            public void run() {
                                // Do your work
                                boss.estado = MUERTO;
                            }
                        }, delay);
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal(musicaInicial,musicaActiva));

                    }

                }
                if(!(nube==null))
                    if(nube.posicion.y > Gdx.graphics.getHeight())
                        nube=null;
            }if(disparoBoss!=null){
                disparoBoss.mover();
                if (disparoBoss.rect.overlaps(cubo.rect)) {
                    if (vidas > 0) {
                        vidas --;
                        disparoBoss = null;
                    } else {
                        musicaFinal.stop();
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaGameOver(musicaInicial,musicaActiva));
                    }
                }
                if(!(disparoBoss==null)) {
                    if (disparoBoss.posicion.y < 0) {
                        disparoBoss = null;

                    }
                }
            }


        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo,0,0);
        font.draw(batch,"",5,(Gdx.graphics.getHeight()-10));
        font.draw(batch,"",(Gdx.graphics.getWidth()-10),(Gdx.graphics.getHeight()-10));

        cubo.pintar(batch);
        boss.pintar(batch);
        if(nube != null)
            nube.pintar(batch);
        if(disparoBoss != null){
            disparoBoss.pintar(batch);
        }
        batch.end();
    }
    private void disparosEnemigo() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(disparoBoss==null)
                    disparoBoss = boss.disparar();
            }
        }, 2, RITMO_GOTAS);
    }
    private void moverNave(){
        boss.mover(velocidad);

        if (boss.posicion.x < 0)
            velocidad.x=6;


        if ((boss.posicion.x+(boss.tamano.x/2)) > Gdx.graphics.getWidth())
            velocidad.x=-6;

    }
    private void comprobarBordes() {
        if (cubo.posicion.x < 0)
            cubo.posicion.x = 0;

        if ((cubo.posicion.x + cubo.tamano.x/2) > Gdx.graphics.getWidth())
            cubo.posicion.x = Gdx.graphics.getWidth() - cubo.tamano.x/2;
    }

    private void comprobarTeclado() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cubo.mover(cubo.velocidad);
            cubo.estado = Cubo.Estado.DERECHA;

        }else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cubo.mover(new Vector2(-10, 0));
            cubo.estado = Cubo.Estado.IZQUIERDA;

        }else
            cubo.estado = Cubo.Estado.PARADO;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if(nube==null){
                nube = cubo.disparar();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
