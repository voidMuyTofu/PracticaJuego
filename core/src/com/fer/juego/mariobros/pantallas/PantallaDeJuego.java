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
import com.fer.juego.mariobros.caracteres.Cubo;
import com.fer.juego.mariobros.caracteres.Gota;
import com.fer.juego.mariobros.caracteres.Nube;
import com.fer.juego.mariobros.caracteres.Roca;
import com.fer.juego.mariobros.managers.R;

import static com.fer.juego.mariobros.caracteres.Cubo.Estado.*;


public class PantallaDeJuego implements Screen {
    boolean musicaActiva;
    SpriteBatch batch;
    Cubo cubo;
    Nube nube;
    Array<Roca> rocas;
    Array<Gota> gotas;
    Array<Nube> nubes;
    final float RITMO_GOTAS = 0.1f;
    long tiempoUltimaGota = 0;
    long ritmoGotas = 750;
    long rimtoRocas = 1500;
    boolean estaPausado = false;
    long tiempoUltimaRoca=0;
    int vidas = 3;
    int puntos = 0;
    Music musicaLluvia;
    BitmapFont font;
    Texture fondo;

    public PantallaDeJuego(Music music, boolean musicaActiva){
        musicaLluvia = music;
        this.musicaActiva = musicaActiva;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        cubo = new Cubo(R.getTextureRegion("nave_parado"));

        gotas = new Array<Gota>();
        rocas = new Array<Roca>();
        nubes = new Array<Nube>();
        font = new BitmapFont();
        cubo.estado = Cubo.Estado.PARADO;

        fondo = new Texture(Gdx.files.internal("core/assets/sprites/fondo.png"));

        if(musicaActiva) {
            musicaLluvia = R.getMusica("core/assets/sounds/undertreeinrain.mp3");
            musicaLluvia.setVolume(0.1f);
            musicaLluvia.setLooping(true);
            musicaLluvia.play();
        }

        tiempoUltimaGota = TimeUtils.millis();
        tiempoUltimaRoca = TimeUtils.millis();

    }

    @Override
    public void render(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P))
            estaPausado = !estaPausado;

        if (!estaPausado) {

            comprobarTeclado();

            cubo.actualizar(dt);

            generarRocas();
            generarLluvia();
            comprobarBordes();

            for (Gota gota : gotas) {
                gota.caer();
                // La gota desaparece cuando llega al suelo
                if ((gota.posicion.y + gota.tamano.y) < 0) {
                    gotas.removeValue(gota, true);

                }
                // La roca desaparece cuando choca con el cubo
                if (gota.rect.overlaps(cubo.rect)) {
                    if (vidas > 0) {
                        vidas--;
                        gotas.removeValue(gota, true);
                        R.getSonido("core/assets/sounds/laser7.wav").play();
                    } else {
                        musicaLluvia.stop();

                        ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaGameOver(musicaLluvia,musicaActiva));
                    }
                }
            }
            for (Roca roca : rocas){
                roca.caer();
                // La gota desaparece cuando llega al suelo
                if ((roca.posicion.y + roca.tamano.y) < 0) {
                    rocas.removeValue(roca, true);
                }
                // La vida desaparece cuando choca con el cubo
                if (roca.rect.overlaps(cubo.rect)) {
                    if(puntos<300){
                        rocas.removeValue(roca, true);
                        puntos = puntos + 100;
                        R.getSonido("core/assets/sounds/laser1.wav").play();
                    }else{
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaTransicion(musicaLluvia,musicaActiva));
                    }

                }
            }
            if(nube!=null) {
                nube.mover();
                for(Gota gota: gotas){
                    if(nube.rect.overlaps(gota.rect)){
                        gotas.removeValue(gota, true);
                        R.getSonido("core/assets/sounds/laser1.wav").play();
                    }
                }
                if(nube.posicion.y > Gdx.graphics.getHeight())
                    nube=null;
            }
        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo,0,0);
        cubo.pintar(batch);
        for (Gota gota : gotas)
            gota.pintar(batch);
        for(Roca roca : rocas)
            roca.pintar(batch);
        if(nube != null)
            nube.pintar(batch);
        font.draw(batch,"Vidas: "+vidas,5,(Gdx.graphics.getHeight()-30));
        font.draw(batch,"Puntuacion: "+puntos,5, (Gdx.graphics.getHeight()-10));
        batch.end();
    }

    private void generarLluviaConTimer() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                int posicionX = MathUtils.random(0,
                        Gdx.graphics.getWidth() - Gota.ANCHURA);
                Gota gota = new Gota(R.getTextureRegion("droplet"), posicionX);
                gotas.add(gota);
            }
        }, 2, RITMO_GOTAS);
    }

    private void generarLluvia() {
        if ((TimeUtils.timeSinceMillis(tiempoUltimaGota)) > ritmoGotas) {
            int posicionX = MathUtils.random(0,
                    Gdx.graphics.getWidth() - Gota.ANCHURA);
            Gota gota = new Gota(R.getTextureRegion("droplet"), posicionX);
            gotas.add(gota);
            tiempoUltimaGota = TimeUtils.millis();
        }
    }
    private void generarRocas(){
        if((TimeUtils.timeSinceMillis(tiempoUltimaRoca)) > rimtoRocas){
            int posicionX = MathUtils.random(0,
                    Gdx.graphics.getWidth());
            Roca roca = new Roca(R.getTextureRegion("rock"),posicionX);
            rocas.add(roca);
            tiempoUltimaRoca = TimeUtils.millis();
        }
    }

    private void comprobarBordes() {
        if (cubo.posicion.x < 0)
            cubo.posicion.x = 0;

        if ((cubo.posicion.x + cubo.tamano.x) > Gdx.graphics.getWidth())
            cubo.posicion.x = Gdx.graphics.getWidth() - cubo.tamano.x;
    }

    private void comprobarTeclado() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cubo.mover(cubo.velocidad);
            cubo.estado=DERECHA;

        }else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cubo.mover(new Vector2(-10, 0));
            cubo.estado = IZQUIERDA;
        }else
            cubo.estado = PARADO;

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
