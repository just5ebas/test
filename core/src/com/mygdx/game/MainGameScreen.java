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
        this.jugador=new ActorJugador(texturaJugador);
        this.roca=new ActorRoca(texturaRoca);
        this.stage.addActor(jugador);
        this.stage.addActor(roca);
        this.jugador.setPosition(20,100);
        this.roca.setPosition(400,100);

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

        stage.draw();
    }

    @Override
    public void dispose() {
        texturaJugador.dispose();
        texturaRoca.dispose();
    }
}
