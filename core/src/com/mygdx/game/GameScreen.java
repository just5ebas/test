package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
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

    private boolean colisionDetected = false;

    public GameScreen(MyGdxGame game) {
        super(game);
        stage = new Stage(new FitViewport(640, 360));
        world = new World(new Vector2(0, -10), true);


    }

    @Override
    public void show() {
        Texture playerTexture = game.getAssetManager().get("mario.png");
        Texture obstacleTexture = game.getAssetManager().get("flor.png");
        Texture floorTexture = game.getAssetManager().get("suelo.jpg");

        player = new PlayerEntity(world, playerTexture, new Vector2(0, 3));
        obstaculo = new ObstacleEntity(world, obstacleTexture, new Vector2(3, 1));
        floor = new FloorEntity(world, floorTexture, new Vector2(0, -1));

        floor.setSize(2000, 185);

        stage.addActor(player);
        stage.addActor(obstaculo);
        stage.addActor(floor);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if (fixtureA == player.getFixture() && fixtureB == obstaculo.getFixture()) {
                    colisionDetected = true;
                }
                if (fixtureB == player.getFixture() && fixtureA == obstaculo.getFixture()) {
                    colisionDetected = true;
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();

        if (Gdx.input.justTouched()) {
            player.saltar();
        }

        float velocidadY = player.getBody().getLinearVelocity().y;
        float velocidadX = 2;

//        boolean derecha = true;
//
//        if (player.getBody().getPosition().x >= 6)
//            derecha = false;
//        else if (player.getBody().getPosition().x == 1)
//            derecha = true;
//
//        if (!derecha)
//            velocidadX = -2;
//        else
//            velocidadX = 2;

        if (colisionDetected) {
            velocidadX = 0;
        }

        player.getBody().setLinearVelocity(velocidadX, velocidadY);


        world.step(delta, 6, 2);
        stage.draw();


    }
}
