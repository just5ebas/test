package actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorRoca extends Actor {

    private Texture texturaJ;

    public ActorRoca(Texture texturaJ){
        this.texturaJ=texturaJ;


    }


    @Override
    public void act(float delta) {
        setX(getX()-250*delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texturaJ,getX(), getY());
    }


}
