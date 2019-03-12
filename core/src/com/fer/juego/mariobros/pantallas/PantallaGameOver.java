package com.fer.juego.mariobros.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fer.juego.mariobros.managers.R;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class PantallaGameOver implements Screen {
    boolean musicaActiva;
    Stage stage;
    Music musicaMuerte;
    Music musicaInicial;

    public PantallaGameOver(Music musicaInicial, boolean musicaActiva){
        this.musicaMuerte = musicaInicial;
        this.musicaActiva = musicaActiva;
    }

    @Override
    public void show() {
        musicaMuerte = R.getMusica("core/assets/sounds/game_over.mp3");

        if(!VisUI.isLoaded())
            VisUI.load();
        stage = new Stage();
        if(musicaActiva) {
            musicaMuerte = R.getMusica("core/assets/sounds/undertreeinrain.mp3");
            musicaMuerte.setVolume(0.1f);
            musicaMuerte.setLooping(true);
            musicaMuerte.play();
        }

        VisTable table = new VisTable(true);
        table.setFillParent(true);
        stage.addActor(table);

        VisTextButton playButton = new VisTextButton("Volver a jugar");
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener())
                        .setScreen(new PantallaDeJuego(musicaInicial,musicaActiva));
                dispose();
            }
        });
        VisTextButton exitButton = new VisTextButton("Salir");
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                System.exit(0);
            }
        });
        VisLabel aboutLabel = new VisLabel("Demo libGDX\\n(c) Fernandao 2019");
        //Añadir los componentes
        table.row();
        table.add(playButton).center().width(200).height(100).pad(5);
        table.row();
        table.add(exitButton).center().width(200).height(100).pad(5);
        table.row();
        table.add(aboutLabel).left().width(200).height(20).pad(5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0.62f,0.47f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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
        stage.dispose();
    }
}
