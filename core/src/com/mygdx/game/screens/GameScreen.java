package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.SCREEN_HEIGTH;
import static com.mygdx.game.extra.Utils.USERCAR;
import static com.mygdx.game.extra.Utils.USERPARED_DER;
import static com.mygdx.game.extra.Utils.USERPARED_IZQ;
import static com.mygdx.game.extra.Utils.USERPINKCAR;
import static com.mygdx.game.extra.Utils.WORLD_HEIGTH;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actor.PinkCar;
import com.mygdx.game.actor.RedCar;
import com.mygdx.game.extra.AssetMan;
import com.mygdx.game.extra.Utils;

public class GameScreen extends BaseScreen implements ContactListener {

    private Stage stage;
    private Image background;
    private RedCar redCar;
    private Array<PinkCar> arrayPinkCar;
    private World world;

    //representacion en la pantalla del mundo fisico
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera ortCamera;

    //instancio music para la musica de fondo
    private Music music;
    private Music musicColision;

    //constante para el tiempo que van saliendo PinkCar
    private final float TIME_PINKCAR = 1.8f;
    private float time;

    //variable contador
    private int contador;

    public GameScreen(MainGame mainGame) {
        super(mainGame);
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGTH);
        this.stage = new Stage(fitViewport);
        this.world = new World(new Vector2(0,0), true);

        //le paso al mundo el listener para comprobar colisiones
        this.world.setContactListener(this);

        //creamos la camara
        this.ortCamera = (OrthographicCamera) this.stage.getCamera();
        this.debugRenderer = new Box2DDebugRenderer();


        this.arrayPinkCar = new Array<PinkCar>();
        this.time = 0f;

        //inicializo music

        this.music = this.mainGame.as.getMusic();
        this.musicColision = this.mainGame.as.getMusicColision();

    }

    public void addBackground(){
        this.background = new Image(new Texture("carretera2.jpg"));
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH, WORLD_HEIGTH);
        this.stage.addActor(background);
    }

    @Override
    public void show() {
        //metodo que crea fondo
        addBackground();
        addParedes();
        this.redCar = new RedCar(this.mainGame.as.getTxRedCar(), new Vector2(1, 1), this.world);
        this.stage.addActor(this.redCar);

        //pongo en play y en bucle la musica
        this.music.setLooping(true);
        this.music.play();

    }

    @Override
    public void render(float delta) {
        crearPinkCar(delta);

        //ACTUALICE LOS ACTORES
        this.stage.getBatch().setProjectionMatrix(ortCamera.combined);
        this.stage.act();
        this.world.step(delta, 6,2);
        this.stage.draw();

        //ACTUALIZAR CAMARA
        this.ortCamera.update();
        this.debugRenderer.render(this.world, this.ortCamera.combined);
        detectarInput(delta);

        eliminarPinkCar();
    }



    @Override
    public void hide() {
        this.redCar.detach();
        this.redCar.remove();
        this.music.stop();

    }

    //eliminamos recursos
    @Override
    public void dispose() {
        this.stage.dispose();
        this.world.dispose();

        this.music.dispose();
        this.musicColision.dispose();
    }


    //metodo para crear PinkCar
    public void crearPinkCar(float delta){
            Texture tx = this.mainGame.as.getTxPinkCar();
            //añadir estados de vida a red car y comprobarlos aqui
            //si sigue vivo
            if(redCar.state == redCar.STATE_LIVE){
                this.time = this.time + delta;
                //si ha pasado el tiempo que he definido antes lo creo
                if(this.time >= this.TIME_PINKCAR){
                    this.time = this.time - this.TIME_PINKCAR;
                    //le asigno una posicion random en el eje x y lo creo y lo añado al array y al stage
                    float posRandomX = MathUtils.random(1f, 4f);
                    PinkCar pinkCar = new PinkCar(tx, new Vector2(posRandomX, 7f), this.world);
                    arrayPinkCar.add(pinkCar);
                    this.stage.addActor(pinkCar);
                }
            }
    }



    //Meotodo que me borra coches rosa de la pantalla si estan fuera de la pantalla
    private void eliminarPinkCar() {
        //elimino los pinkcar que esten fuera de la pantalla
        for(PinkCar pinkCar : this.arrayPinkCar){
            if(!this.world.isLocked()){
                if(pinkCar.estaFuera()){
                    //se borra la parte fisica y grafica
                    pinkCar.detach();
                    pinkCar.remove();
                    arrayPinkCar.removeValue(pinkCar, false);
                }
            }
        }
    }


    //metodo para detectar input si he tocado la pantalla y saber donde muevo redCar
    public void detectarInput(float delta){
        if(Gdx.input.justTouched()){
            //obtengo la x de la pantalla donde he tocado
            float xTouchPixels = Gdx.input.getX();
            System.out.println("-------->>" + xTouchPixels);
            System.out.println("Getx cada vex que toco" + redCar.getX());
            //si es mayor de 330, has tocado en la derecha
            if(xTouchPixels <= SCREEN_HEIGTH/2){
                //se mueve a la derecha
                redCar.moverDerecha();
            }else if(xTouchPixels > SCREEN_HEIGTH/2){
                //se mueve izquierda
                redCar.moverIzquierda();
            }
        }
    }

    //metodo para añadir paredes para que no se salga mi coche de pantalla
    public void addParedes(){
        //pared derecha
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0.2f, 1f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);
        body.setUserData(USERPARED_DER);

        PolygonShape edge = new PolygonShape();
        edge.setAsBox(0.2f, 5f);
        body.createFixture(edge, 3);
        edge.dispose();

        //pared izq
        BodyDef bodyDefizq = new BodyDef();
        bodyDefizq.position.set(4.6f, 1f);
        bodyDefizq.type = BodyDef.BodyType.StaticBody;
        Body bodyizq = world.createBody(bodyDefizq);
        bodyizq.setUserData(USERPARED_IZQ);

        PolygonShape edgeizq = new PolygonShape();
        edgeizq.setAsBox(0.2f, 5f);
        bodyizq.createFixture(edgeizq,3);
        edgeizq.dispose();
    }


    @Override
    public void beginContact(Contact contact) {
        try {
            //si obja colisiona con objb y viceversa
            if (contact.getFixtureA().getUserData().equals(USERCAR) &&
                    contact.getFixtureB().getUserData().equals(USERPINKCAR)
            || contact.getFixtureA().getUserData().equals(USERPINKCAR) && contact.getFixtureB().getUserData().equals(USERCAR)) {
                this.musicColision.play();
                redCar.morir();
                for (PinkCar p : arrayPinkCar){
                    p.pararPinkCar();
                }
                this.music.stop();
                //abrir pantalla de game over y sonido colision
                this.stage.addAction(Actions.sequence(
                        Actions.delay(1f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                mainGame.setScreen(mainGame.gameOverScreen);
                            }
                        })
                ));
            }
        }catch(NullPointerException e){

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
}
