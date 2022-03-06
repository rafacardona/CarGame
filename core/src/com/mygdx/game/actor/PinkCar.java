package com.mygdx.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.extra.Utils;

public class PinkCar extends Actor {
    //constructor le pasamos posicion y textura
    private Vector2 position;
    private Texture texture;

    private World world;
    private Body body;
    private Fixture fixture;

    public PinkCar(Texture texture, Vector2 position, World world){
        this.texture = texture;
        this.position = position;
        this.world = world;
        createBody();
        createFixture();
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x-0.35f, body.getPosition().y-0.7f);
        batch.draw(this.texture, getX(), getY(), 0.7f, 1.4f);
    }
    public void createBody(){
        //creacion del body y le doy posicion
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(position);
        //digo de que tipo va a ser RedCar
        bodydef.type = BodyDef.BodyType.DynamicBody;

        this.body = this.world.createBody(bodydef);
        body.setLinearVelocity(0, -2f);
    }

    public void createFixture(){
        PolygonShape shapePinkCar = new PolygonShape();
        //widht de pinkcar /2 y height de pinkcar/2
        shapePinkCar.setAsBox((0.7f/2), (1.4f/2));
        this.fixture = this.body.createFixture(shapePinkCar, 8);
        //le pongo un identificador a pinkcar
        this.fixture.setUserData(Utils.USERPINKCAR);
        //dispose de PolygonShape
        shapePinkCar.dispose();
    }

    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);

    }

    public boolean estaFuera(){
        if(body.getPosition().y <= -1f){
            return true;
        }else return false;
    }
    public void pararPinkCar(){
        this.body.setLinearVelocity(0,0);
    }
}
