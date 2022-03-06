package com.mygdx.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.extra.Utils;

public class RedCar extends Actor {

    //constructor le pasamos posicion y textura
    private Vector2 position;
    private Texture texture;

    private World world;
    private Body body;
    private Fixture fixture;

    public static final int STATE_LIVE = 1;
    public static final int STATE_DEAD = 0;
    public int state;

    private PolygonShape shapeRedCar;
    public RedCar(Texture texture, Vector2 vector2, World world){
        this.position = vector2;
        this.texture = new Texture("redcar2.png");
        this.world = world;

        state = STATE_LIVE;

        createBody();
        createFixture();
    }


    //aqui
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
    }

    public void createFixture(){
        shapeRedCar = new PolygonShape();

        //widht de redcar /2 y height de redcar/2
        shapeRedCar.setAsBox(0.7f/2, 1.4f/2);

        this.fixture = this.body.createFixture(shapeRedCar, 8);
        //le pongo un identificador a redcar
        this.fixture.setUserData(Utils.USERCAR);
        //dispose de PolygonShape
        shapeRedCar.dispose();
    }

    //metodo que me sirve para liberar los recursos
    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

    public void moverDerecha(){
        //si esta vivo
        if(state == STATE_LIVE){
            body.setLinearVelocity(-1.3f, 0);
        }
    }
    public void moverIzquierda(){
        //si esta vivo
        if(state == STATE_LIVE){
            body.setLinearVelocity(1.3f, 0);
        }
    }


    public void morir(){
        body.setLinearVelocity(0,0);
        this.state = STATE_DEAD;

    }
}
