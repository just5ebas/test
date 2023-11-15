package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends Game {
	private AssetManager assetManager;

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	@Override
	public void create() {
		assetManager=new AssetManager();
		assetManager.load("mario.png", Texture.class);
		assetManager.load("flor.png", Texture.class);
		assetManager.load("suelo.jpg", Texture.class);
		assetManager.finishLoading();
		setScreen(new GameScreen(this));

		//setScreen(new Box2DScreen(this));
	}
}
