package com.mygdx.game;

import static entity.Constants.PIXELS2METER;

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

import java.util.ArrayList;
import java.util.List;

import entity.Constants;
import entity.FloorEntity;
import entity.ObstacleEntity;
import entity.PlayerEntity;

public class GameScreen extends BaseScreen {
    private Stage stage;
    private World world;
    private PlayerEntity player;

    private List<FloorEntity> listaSuelos = new ArrayList<FloorEntity>();
    private List<ObstacleEntity> listaObstaculos = new ArrayList<ObstacleEntity>();

//    private Integer x = 10;
//    private Integer y = 30;

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
        stage.addActor(player);

        listaSuelos.add(new FloorEntity(world, floorTexture, new Vector2(0, -1)));

        for (int i = 1; i <= 15; i++){
            listaSuelos.add(new FloorEntity(world, floorTexture, new Vector2(0, -1)));
        }

        for (int i = 1; i <= 15; i++){
            listaObstaculos.add(new ObstacleEntity(world, obstacleTexture, new Vector2(i * 6, 1)));
        }



        for (FloorEntity floor : listaSuelos) {
            floor.setSize(4000, 92);
            stage.addActor(floor);
        }

        for (ObstacleEntity obstacle : listaObstaculos) {
            stage.addActor(obstacle);
        }

        world.setContactListener(new ContactListener() {

            private boolean colision(Contact contact, Object userA, Object userB) {
                return ((contact.getFixtureA().getUserData()).equals(userA) &&
                        (contact.getFixtureB().getUserData()).equals(userB)) ||
                        ((contact.getFixtureA().getUserData()).equals(userB) &&
                                (contact.getFixtureB().getUserData()).equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                for (ObstacleEntity obstacle : listaObstaculos) {
                    if (colision(contact, "player", "obstaculo") || colision(contact, "obstaculo", "player")) {
                        colisionDetected = true;
                    }
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

//        world.setContactListener(new ContactListener() {
//            @Override
//            public void beginContact(Contact contact) {
//                Fixture fixtureA = contact.getFixtureA();
//                Fixture fixtureB = contact.getFixtureB();
//
//                for (ObstacleEntity obstacle : listaObstaculos) {
//                    if (fixtureA == player.getFixture() && fixtureB == obstacle.getFixture()) {
//                        colisionDetected = true;
//                    }
//                    if (fixtureB == player.getFixture() && fixtureA == obstacle.getFixture()) {
//                        colisionDetected = true;
//                    }
//                }
//            }
//
//            @Override
//            public void endContact(Contact contact) {
//
//            }
//
//            @Override
//            public void preSolve(Contact contact, Manifold oldManifold) {
//
//            }
//
//            @Override
//            public void postSolve(Contact contact, ContactImpulse impulse) {
//
//            }
//        });
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(player.getX() > 100 && player.isAlive()){
        stage.getCamera().translate(1.95f * delta * PIXELS2METER, 0, 0);
        }

        stage.act();

        if (Gdx.input.justTouched() && !colisionDetected) {
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
            player.setAlive(false);
        }

        player.getBody().setLinearVelocity(velocidadX, velocidadY);


        world.step(delta, 6, 2);
        stage.draw();
    }

    @Override
    public void hide() {
        player.liberar();
        player.remove();

        for (FloorEntity floor : listaSuelos) {
            floor.liberar();
        }

        for (ObstacleEntity obstacle : listaObstaculos) {
            obstacle.liberar();
        }
    }
}
