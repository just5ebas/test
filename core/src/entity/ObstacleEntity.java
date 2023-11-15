package entity;

import static entity.Constants.PIXELS2METER;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ObstacleEntity extends Actor {


    private Texture texture;
    private Body body;
    private World world;
    private Fixture fixture;

    public ObstacleEntity(World world, Texture texture, Vector2 position){
        this.world=world;
        this.texture=texture;

        BodyDef bodyDef=createBody2DDef(position);
        body=world.createBody(bodyDef);

        PolygonShape polygonShape=new PolygonShape();
        polygonShape.setAsBox(1,1);
        fixture= body.createFixture(polygonShape,1);
        polygonShape.dispose();

//        BodyDef bodyRoca=createBody2DDef(new Vector2(-4.5f, 5.5f));
//        body=world.createBody(bodyRoca);
//
//        Vector2[]vertices =new Vector2[3];
//        vertices[0]=new  Vector2(-0.5f,-0.5f);
//        vertices[1]=new  Vector2(0.5f,-0.5f);
//        vertices[2]=new  Vector2(0,0.5f);
//
//
//        PolygonShape polygonRoca=new PolygonShape();
//        polygonRoca.set(vertices);
////        polygonShape.setAsBox(1,1);
//        fixture= body.createFixture(polygonRoca,1);
//
        setSize(PIXELS2METER, PIXELS2METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        setPosition(body.getPosition().x * PIXELS2METER, body.getPosition().y * PIXELS2METER);

        batch.draw(this.texture, getX(), getY(), getWidth(), getHeight());

    }

    private BodyDef createBody2DDef(Vector2 position){
        BodyDef def=new BodyDef();
        def.position.set(position);
        def.type=BodyDef.BodyType.StaticBody;
        return def;
    }
}
