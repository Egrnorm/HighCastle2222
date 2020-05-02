package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Unit {
    private Body body;
    private Texture img;

    public static final float RADIUS = 20f;

    public Unit(float x, float y, World world, Texture img){
        body = createCircle(x, y, RADIUS * MainScreen.UNIT_SCALE,  world);
        this.img = img;
        body.setLinearDamping(1);
    }


    public void draw(SpriteBatch batch){
        float divR = RADIUS *  MainScreen.UNIT_SCALE;
        batch.draw(img, body.getPosition().x - divR, body.getPosition().y - divR, 2 * RADIUS * MainScreen.UNIT_SCALE, 2 * RADIUS * MainScreen.UNIT_SCALE);
    }

    public void applyForce(Vector2 v){
        body.applyForceToCenter(v, true);

    }

    public void setVelocity(float x, float y){
        body.setLinearVelocity(x, y);
    }

    private Body createCircle(float x, float y, float radius, World world){
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        Body body = world.createBody(def);

        CircleShape circle = new CircleShape();
        circle.setPosition(new Vector2(0, 0));
        circle.setRadius(radius);
        Fixture f = body.createFixture(circle, 1f);
        circle.dispose();

        body.setTransform(x, y, 0);

        return body;
    }
}
