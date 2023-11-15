package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DScreen extends BaseScreen{
    private World world;
    private Box2DDebugRenderer renderer;

    private OrthographicCamera camera;

    private Body body, sueloBody, rocaBody;

    private Fixture fixture, sueloFixture, rocaFixture;

    private boolean colisionDetected = false;

    public Box2DScreen(MyGdxGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.justTouched() && !colisionDetected){


            saltar();
//            colisionDetected = true;
        }

        float velocidadY = body.getLinearVelocity().y;
        float velocidadX = 2;

        if(colisionDetected){
            velocidadX = 0;
        }

        body.setLinearVelocity(velocidadX, velocidadY);

        this.world.step(delta, 6,2);
        camera.update();
        renderer.render(world, camera.combined );
    }

    @Override
    public void show() {

        world=new World(new Vector2(0,-10),true);
        renderer=new Box2DDebugRenderer();
        camera=new OrthographicCamera(32,18);
        camera.translate(0,-2);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if(fixtureA == fixture && fixtureB == rocaFixture){
                    colisionDetected = true;
                }
                if(fixtureB == fixture && fixtureA == rocaFixture){
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

        BodyDef bodyDef=createBody2DDef();
        body=world.createBody(bodyDef);
        PolygonShape polygonShape=new PolygonShape();
        polygonShape.setAsBox(1,1);
        fixture= body.createFixture(polygonShape,1);

        BodyDef sueloDef=createSueloBody2DDef();
        sueloBody=world.createBody(sueloDef);
        PolygonShape polygonShapeSuelo=new PolygonShape();
        polygonShapeSuelo.setAsBox(500,1);
        sueloFixture= sueloBody.createFixture(polygonShapeSuelo,1);

//        if( body.getPosition().y + 1 > sueloBody.getPosition().y) {
//            System.out.println("Se ha producido una colision");
//
//        }

        BodyDef bodyRoca=createBodyRocaDef();
        rocaBody=world.createBody(bodyRoca);

        Vector2[]vertices =new Vector2[3];
        vertices[0]=new  Vector2(-0.5f,-0.5f);
        vertices[1]=new  Vector2(0.5f,-0.5f);
        vertices[2]=new  Vector2(0,0.5f);


        PolygonShape polygonRoca=new PolygonShape();
        polygonRoca.set(vertices);
//        polygonShape.setAsBox(1,1);
        rocaFixture= rocaBody.createFixture(polygonRoca,1);

    }

    private BodyDef createBody2DDef(){
        BodyDef def=new BodyDef();
        def.position.set(-2,-4);
        def.type=BodyDef.BodyType.DynamicBody;
        return def;
    }

    private BodyDef createBodyRocaDef(){
        BodyDef def=new BodyDef();
        def.position.set(4.5f,-5.5f);
        def.type=BodyDef.BodyType.StaticBody;
        return def;
    }
    private BodyDef createSueloBody2DDef(){
        BodyDef def=new BodyDef();
        def.position.set(0,-7);
        def.type=BodyDef.BodyType.StaticBody;
        return def;
    }
    @Override
    public void dispose() {
        this.body.destroyFixture(fixture);
        this.world.destroyBody(body);
        this.world.dispose();
        this.renderer.dispose();

    }

    public void saltar(){
        Vector2 position = body.getPosition();
        body.applyLinearImpulse(5, 30, position.x, position.y, true);

//        body.applyAngularImpulse(-45, true);
    }

}
