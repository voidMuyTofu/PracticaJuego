package com.fer.juego.mariobros.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.fer.juego.mariobros.managers.R;


public class PantallaSplash implements Screen {

    private Texture splashTexture;
    private Image splashImage;
    private Stage stage;

    private boolean splashDone = false;

    public PantallaSplash() {
        splashTexture = new Texture(Gdx.files.internal("core/assets/sprites/logo.jpg"));
        splashImage = new Image(splashTexture);
    }

    @Override
    public void show() {

        stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        // Muestra la imagen de SplashScreen como una animación
        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f),
                Actions.delay(1.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        splashDone = true;
                    }
                })
        ));

        table.row().height(splashTexture.getHeight());
        table.add(splashImage).center();
        stage.addActor(table);

        // Lanza la carga de recursos
        R.cargarRecursos();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_CLEAR_VALUE);

        stage.act(dt);
        stage.draw();

        // Comprueba si se han cargado todos los recursos
        if (R.update()) {
            // Si la animación ha terminado se muestra ya el menú principal
            if (splashDone) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal());
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
        splashTexture.dispose();
        stage.dispose();
    }
}