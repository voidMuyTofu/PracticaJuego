package com.fer.juego.mariobros.managers;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.io.File;

public class R {
    public static AssetManager assets = new AssetManager();

    public static void cargarRecursos(){
        assets.load("core/assets/atlas.pack.atlas", TextureAtlas.class);
        assets.load("core/assets/sounds" + File.separator + "laser1.wav", Sound.class);
        assets.load("core/assets/sounds" + File.separator + "laser7.wav", Sound.class);
        assets.load("core/assets/sounds" + File.separator + "spacelaser.wav", Sound.class);
        assets.load("core/assets/sounds" + File.separator + "undertreeinrain.mp3", Music.class);
        assets.load("core/assets/sounds" + File.separator + "game_over.mp3", Music.class);
        assets.finishLoading();
    }
    public static TextureRegion getTextureRegion(String nombre){
        return assets.get("core/assets/atlas.pack.atlas", TextureAtlas.class).findRegion(nombre);
    }
    public static Sound getSonido(String nombre){
        return assets.get(nombre,Sound.class);
    }
    public static Music getMusica(String nombre){
        return assets.get(nombre,Music.class);
    }
    public static boolean update(){
        return assets.update();
    }
    public static Array<TextureAtlas.AtlasRegion> getAnimacion(String nombre){
        return assets.get("core/assets/atlas.pack.atlas",TextureAtlas.class).findRegions(nombre);
    }

}
