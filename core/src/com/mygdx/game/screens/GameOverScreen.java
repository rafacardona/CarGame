package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.WORLD_HEIGTH;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.extra.Utils;

public class GameOverScreen extends BaseScreen{
    private Stage stage;



    public GameOverScreen(MainGame mainGame) {
        super(mainGame);
        FitViewport fitViewport = new FitViewport(Utils.WORLD_WIDTH, Utils.WORLD_HEIGTH);
        this.stage = new Stage(fitViewport);
    }

    public void addBackground(){
        Image background;
        background = new Image(this.mainGame.as.getTxFondoInicio());
        background.setPosition(0,0);
        background.setSize(WORLD_WIDTH, WORLD_HEIGTH);
        this.stage.addActor(background);

        //img de game over
        Image gv = new Image(this.mainGame.as.getTxGameOver());
        gv.setSize(3f,2f);
        gv.setPosition(1,5);
        this.stage.addActor(gv);
        //imagen de coche estrellado

        Image estrellado = new Image(this.mainGame.as.getTxCocheEst());
        estrellado.setSize(3f, 3f);
        estrellado.setPosition(1,0.5f);
        this.stage.addActor(estrellado);
    }
    @Override
    public void show() {
        addBackground();

    }

    @Override
    public void render(float delta) {
        this.stage.draw();
        if(Gdx.input.justTouched()){
            mainGame.setScreen(new GameReadyScreen(this.mainGame));
        }
    }


    @Override
    public void dispose() {
        super.dispose();
        this.stage.dispose();
    }
}
