package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.game.Spheral;

/**
 * Abstract Class used to simbolize all Views that are Menus.
 */
public abstract class MenuScreen extends ScreenAdapter {
    protected final Spheral game;

    protected Stage stage;
    protected Viewport viewport;
    protected Camera camera;
    protected SpriteBatch batch;
    protected Skin skin;
    protected TextureAtlas atlas;

    /**
     * How much meters does a pixel represent.
     */
    public final static float PIXEL_TO_METER = 0.04f;

    /**
     * The width of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float VIEWPORT_WIDTH = 30;

    /**
     * The height of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float VIEWPORT_HEIGHT = VIEWPORT_WIDTH * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());

    //LayOut
    /**
     * Image representing the Menu's background image.
     */
    protected Image backgroundImg;
    /**
     * Image representing the Menu's Title image.
     */
    protected Image titleImg;

    protected MenuScreen(final Spheral game) {
        this.game = game;
        this.batch = game.getBatch();

        atlas = new TextureAtlas("uiskin.atlas");
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);

        camera = new OrthographicCamera();

        viewport = new FitViewport(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_HEIGHT / PIXEL_TO_METER);
        viewport.apply();

        stage = new Stage(viewport, batch);

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        backgroundImg = new Image(new Texture("background.png"));
        backgroundImg.setScale(VIEWPORT_WIDTH / PIXEL_TO_METER  /  backgroundImg.getWidth(), VIEWPORT_HEIGHT / PIXEL_TO_METER  / backgroundImg.getHeight());

        titleImg = new Image(new Texture("spheral.png"));
        titleImg.setSize(0.8f * titleImg.getWidth(), 0.8f * titleImg.getHeight());
        titleImg.setPosition(VIEWPORT_WIDTH / PIXEL_TO_METER / 2 - titleImg.getWidth() / 2, VIEWPORT_HEIGHT * 0.98f / PIXEL_TO_METER - titleImg.getHeight());
    }

    @Override
    public void show() {
        //Displaying the background and the Image
        stage.addActor(backgroundImg);
        stage.addActor(titleImg);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.begin();
        //batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

}