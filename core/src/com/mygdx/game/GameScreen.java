package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import entity.FloorEntity;
import entity.ObstacleEntity;
import entity.PlayerEntity;

public class GameScreen extends BaseScreen {
    private Stage stage;
    private World world;

    private PlayerEntity player;

    private ObstacleEntity obstaculo;

    private FloorEntity floor;



    public GameScreen(MyGdxGame game) {
        super(game);
        stage=new Stage(new FitViewport(640,360));
        world=new World(new Vector2(0,-10),true);


    }

    @Override
    public void show() {
        Texture playerTexture= game.getAssetManager().get("mario.png");
        Texture obstacleTexture= game.getAssetManager().get("flor.png");
        Texture floorTexture= game.getAssetManager().get("suelo.jpg");

        player=new PlayerEntity(world, playerTexture, new Vector2(1,3));
        obstaculo = new ObstacleEntity(world, obstacleTexture, new Vector2(5, 1));
        floor = new FloorEntity(world, floorTexture, new Vector2(0,-1));

        floor.setSize(2000,190);

        stage.addActor(player);
        stage.addActor(obstaculo);
        stage.addActor(floor);
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        world.step(delta, 6,2);
        stage.draw();


    }
}
