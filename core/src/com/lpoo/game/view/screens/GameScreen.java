package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.lpoo.game.Spheral;
import com.lpoo.game.controller.GameController;
import com.lpoo.game.controller.InputHandler;
import com.lpoo.game.model.GameModel;
import com.lpoo.game.model.entities.EntityModel;
import com.lpoo.game.view.entities.EntityView;
import com.lpoo.game.view.entities.ViewFactory;

import java.util.List;

/**
 * A view representing the game screen. Draws all the other views and
 * controls the camera.
 */
public class GameScreen extends ScreenAdapter {
    /**
     * Used to debug the position of the physics fixtures
     */
    private static final boolean DEBUG_PHYSICS = true;

    /**
     * How much meters does a pixel represent.
     */
    public final static float PIXEL_TO_METER = 0.04f;

    /**
     * The width of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float VIEWPORT_WIDTH = 35 * 3.5f;

    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    /**
     * A renderer used to debug the physical fixtures.
     */
    private Box2DDebugRenderer debugRenderer;

    /**
     * The transformation matrix used to transform meters into
     * pixels in order to show fixtures in their correct places.
     */
    private Matrix4 debugCamera;

    private OrthogonalTiledMapRenderer mapRenderer;

    private InputHandler controller;

    private GameModel model;

    private Spheral game;

    public GameScreen(Spheral game) {
        this.game = game;

        loadAssets();

        model = GameModel.getInstance();

        camera = createCamera();

        controller = new GameController(camera);

        mapRenderer = new OrthogonalTiledMapRenderer(model.getMap());
    }

    @Override
    public void render(float delta) {
        controller.handleInput(delta);
        model.update(delta);

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

//        updateCamera();
        drawBackground();

        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        drawEntities();
        game.getBatch().end();

        //check
        mapRenderer.setView(camera);
        mapRenderer.render();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(model.getWorld(), debugCamera);
        }

    }

    private void drawEntities() {
        List<EntityModel> models = model.getModels();
        for (EntityModel model : models) {
            EntityView view = ViewFactory.makeView(game, model);
            view.update(model);
            view.draw(game.getBatch());
        }
    }

    private void drawBackground() {

    }

    private void updateCamera() {

        // Follow player
        Vector3 vec = new Vector3(model.getBall().getX(), model.getBall().getY(), 0);
        camera.unproject(vec);
        camera.position.set(vec);

        camera.update();
    }

    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {

        // Load Assets
        game.getAssetManager().load( "ball.png" , Texture.class);

        game.getAssetManager().finishLoading();
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

}
