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

public class PlayerEntity extends Actor {

    private Texture texture;
    private Body body;
    private World world;
    private Fixture fixture;
    private boolean alive;

    public PlayerEntity(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = texture;
        this.alive=true;

        BodyDef bodyDef = createBody2DDef(position);
        body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(1,1);
        fixture = body.createFixture(polygonShape, 1);
        fixture.setUserData("player");
        polygonShape.dispose();

        setSize(PIXELS2METER, PIXELS2METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x * PIXELS2METER, body.getPosition().y * PIXELS2METER);
        batch.draw(this.texture, getX(), getY(), getWidth(), getHeight());
    }

    private BodyDef createBody2DDef(Vector2 position) {
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    public void saltar() {
        Vector2 position = body.getPosition();
        body.applyLinearImpulse(10,35 , position.x, position.y, true);
    }

    public Body getBody() {
        return body;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void liberar(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

}