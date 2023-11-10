package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import actors.ActorJugador;
import actors.ActorRoca;

public class MainGameScreen extends BaseScreen {
    public MainGameScreen(MyGdxGame game) {
        super(game);
        texturaJugador=new Texture("mario.png");
        texturaRoca=new Texture("flor.png");
    }
    private Stage stage;
    private ActorJugador jugador;
    private ActorRoca roca;
    private Texture texturaJugador;
    private Texture texturaRoca;

    @Override
    public void show() {
        this.stage=new Stage();
        this.stage.setDebugAll(true);
        this.jugador=new ActorJugador(texturaJugador);
        this.roca=new ActorRoca(texturaRoca);
        this.stage.addActor(jugador);
        this.stage.addActor(roca);
        this.jugador.setPosition(20,100);
        this.roca.setPosition(400,100);

    }

    public void comprobarColision(){
        if(jugador.isAlive() && jugador.getX() + jugador.getWidth() > roca.getX()) {
            System.out.println("Se ha producido una colision");
            this.jugador.setAlive(false);
        }
    }

    @Override
    public void hide() {
        this.stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        comprobarColision();
        if(!jugador.isAlive()){
//            stage.clear();
//            stage.dispose();
            jugador.setVisible(false);
            roca.setX(jugador.getX() + 70);
//            roca.setVisible(false);
        }
        stage.draw();
    }

    @Override
    public void dispose() {
        texturaJugador.dispose();
        texturaRoca.dispose();
    }
}
