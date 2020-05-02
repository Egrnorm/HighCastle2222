package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.gameui.Joystick;
import com.mygdx.game.gameui.JoystickArea;


public class MainScreen implements Screen, InputProcessor {

    SpriteBatch batch;
    Texture img;
    float size = 100;
    int count = 5;

    private Unit[] units = new Unit[count];

    public static final float UNIT_SCALE = 1f / 16f;

    private World world;
    private TiledMap map;
    private OrthographicCamera camera = new OrthographicCamera();
    private OrthogonalTiledMapRenderer renderer;

    private Stage stage = new Stage();

    private JoystickArea joystickArea;

    public MainScreen() {
        batch = new SpriteBatch();
        img = new Texture("anim.png");
        map = new TmxMapLoader().load("maptest.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);
        camera.setToOrtho(false, 100, 100);
        world = new World(new Vector2(), false);
        //box2DDebugRenderer =new Box2DDebugRenderer();

        units[0] = new Unit(10, 10, world, img);
        units[1] = new Unit(15, 10, world, img);
        units[2] = new Unit(10, 15, world, img);
        units[3] = new Unit(15, 15, world, img);
        units[4] = new Unit(20, 10, world, img);
        units[0].applyForce(new Vector2(100000, 0));


        Texture circle = new Texture("jcircle.png");
        Texture curJoystick = new Texture("JoyStickKrug.png");
        joystickArea = new JoystickArea(circle, curJoystick);




        stage.addActor(joystickArea);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if(joystickArea.isJoystickDown()){
            float x = 10 * joystickArea.getValueX();
            float y = 10 * joystickArea.getValueY();
            units[0].setVelocity(x, y);
        }
        else{
            units[0].setVelocity(0, 0);
        }

        world.step(delta, 4, 4);



        camera.update();
        renderer.setView(camera);
        renderer.render();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //batch.draw(img, 20, 20, size * UNIT_SCALE, size * UNIT_SCALE);
        for (int i = 0; i < count; i++) {
            units[i].draw(batch);
        }
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        world.dispose();
        batch.dispose();
        img.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}