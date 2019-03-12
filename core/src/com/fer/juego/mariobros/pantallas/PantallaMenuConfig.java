package com.fer.juego.mariobros.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class PantallaMenuConfig implements Screen {
    VisCheckBox cbMusica;
    VisSelectBox<String> sbDificultad;
    Music music;
    boolean musicaActiva;
    Stage stage;

    public PantallaMenuConfig(Music music,boolean musicaActiva){
        this.music = music;
        this.musicaActiva = musicaActiva;
    }
    @Override
    public void show() {
        if(!VisUI.isLoaded())
            VisUI.load();
        stage = new Stage();

        VisTable table = new VisTable(true);
        table.setFillParent(true);
        stage.addActor(table);

        cbMusica = new VisCheckBox("Musica");
        cbMusica.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(cbMusica.isChecked()){
                    music.play();
                    musicaActiva = true;
                }else{
                    music.stop();
                    musicaActiva = false;
                }

            }
        });
        if(music.isPlaying()){
            cbMusica.setChecked(true);
            musicaActiva = true;
        }else{
            cbMusica.setChecked(false);
            musicaActiva = false;
        }
        VisTextButton backButton = new VisTextButton("Volver");
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener())
                        .setScreen(new PantallaMenuPrincipal(music,musicaActiva));
                dispose();
            }
        });
        table.row();
        table.add(cbMusica).center().width(200).height(100).pad(5);
        table.row();
        table.add(backButton).center().width(200).height(100).pad(5);

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
