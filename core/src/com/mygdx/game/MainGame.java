package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.extra.AssetMan;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.GameReadyScreen;

//esta clase va a ser la encargada de administrar las pantallas del juego
public class MainGame extends Game {
	public GameScreen gameScreen;

	public GameReadyScreen gameReadyScreen;

	public GameOverScreen gameOverScreen;


	//Asset man para cargar recursos
	public AssetMan as;
	@Override
	public void create() {
		this.as = new AssetMan();
		this.gameReadyScreen = new GameReadyScreen(this);
		this.gameScreen = new GameScreen(this);
		this.gameOverScreen = new GameOverScreen(this);
		setScreen(this.gameReadyScreen);
	}
}
