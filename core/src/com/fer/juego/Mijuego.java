package com.fer.juego;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fer.juego.mariobros.managers.R;
import com.fer.juego.mariobros.pantallas.PantallaFinal;
import com.fer.juego.mariobros.pantallas.PantallaMenuPrincipal;
import com.fer.juego.mariobros.pantallas.PantallaSplash;

public class Mijuego extends Game {
	
	@Override
	public void create () {
		R.cargarRecursos();
		setScreen(new PantallaSplash());
	}

	@Override
	public void render () {
		super.render();
	}
}
