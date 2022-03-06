package com.mygdx.game.extra;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
//esta clase se encarga de cargar todos los recursos.
public class AssetMan {
    private AssetManager as;
    public AssetMan(){
        //los cargo
        this.as = new AssetManager();
        as.load(Utils.MUSIC, Music.class);
        as.load(Utils.MUSIC_COLISION, Music.class);
        as.load(Utils.TX_PINKCAR, Texture.class);
        as.load(Utils.TX_REDKCAR, Texture.class);
        as.load(Utils.TX_BACKGROUNDINIT, Texture.class);
        as.load(Utils.TX_START, Texture.class);
        as.load(Utils.TX_GAME_OVER, Texture.class);
        as.load(Utils.TX_COCHE_EST, Texture.class);
        as.finishLoading();
    }
    //metodos que me devuelven los recursos para cuando me hagan falta
    public Music getMusic(){
        return this.as.get(Utils.MUSIC);
    }
    public Music getMusicColision(){
        return this.as.get(Utils.MUSIC_COLISION);
    }
    public Texture getTxRedCar(){ return this.as.get(Utils.TX_REDKCAR);}
    public Texture getTxPinkCar(){ return this.as.get(Utils.TX_PINKCAR);}
    public Texture getTxFondoInicio(){ return this.as.get(Utils.TX_BACKGROUNDINIT);}
    public Texture getTxStart(){ return this.as.get(Utils.TX_START);}
    public Texture getTxGameOver(){ return this.as.get(Utils.TX_GAME_OVER);}
    public Texture getTxCocheEst(){ return this.as.get(Utils.TX_COCHE_EST);}
}
