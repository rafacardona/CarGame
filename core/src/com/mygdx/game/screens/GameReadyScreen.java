package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.WORLD_HEIGTH;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actor.RedCar;

public class GameReadyScreen extends BaseScreen{
    private Stage stage;
    public GameReadyScreen(MainGame mainGame) {
        super(mainGame);
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGTH);
        this.stage = new Stage(fitViewport);

    }
    //metodo para fondo
    public void addBackground(){
        Image background;
        background = new Image(this.mainGame.as.getTxFondoInicio());
        background.setPosition(0,0);
        background.setSize(WORLD_WIDTH, WORLD_HEIGTH);
        this.stage.addActor(background);
    }

    //añado dos coches uno arriba y otro abajo
    public void addCoches(){
        //coche rojo
        Texture tx = this.mainGame.as.getTxRedCar();
        Image i = new Image(tx);
        i.setPosition(3.5f,1);
        i.setSize(1.4f, 2.8f);
        i.rotateBy(90);
        //coche rosa
        Image i2 = new Image(this.mainGame.as.getTxPinkCar());
        i2.setPosition(3.5f, 5.5f);
        i2.setSize(1.4f, 2.8f);
        i2.rotateBy(90);
        this.stage.addActor(i);
        this.stage.addActor(i2);

        //img de empezar en medio de los dos
        Image bt = new Image(this.mainGame.as.getTxStart());
        bt.setPosition(1.2f, 3.5f);
        bt.setSize(2f, 1f);
        this.stage.addActor(bt);
    }

    @Override
    public void show() {
        addBackground();
        addCoches();

    }

    @Override
    public void render(float delta) {

        this.stage.draw();
        //si toco la pantalla, empieza el juego
        if(Gdx.input.justTouched()){
            mainGame.setScreen(new GameScreen(mainGame));
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        this.stage.dispose();
    }
}
