package com.lpoo.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lpoo.game.Armadillo;

/**
 * A view representing the Main Menu screen. This Menu is presented
 * to the User when the Application is initialized. In this Menu the
 * User chooses what he wishes to do.
 */
public class MainMenuScreen extends MenuScreen {

    //Layout Macros
    /**
     * Constant representing all the Buttons' Width.
     */
    protected static final float BUTTON_WIDTH = VIEWPORT_WIDTH / 2;
    /**
     * Constant representing the extra space around the edges of all Buttons.
     */
    protected static final float BUTTON_EDGE = VIEWPORT_WIDTH / 75;
    /**
     * Constant representing the extra space around the bottom edge of the bottom Button.
     */
    protected static final float BOTTOM_EDGE = VIEWPORT_WIDTH / 75;

    /**
     * Main Menu Screen's Constructor.
     * It creates a Main Menu for the given game.
     *
     * @param game The current Game session.
     */
    public MainMenuScreen(final Armadillo game) {
        super(game);
    }

    /**
     * Function responsible for creating and setting the Menu Buttons.
     * It also sets the buttons Layout in the given table.
     *
     * @param table Table where the Menu buttons will be organized
     */
    protected void createMenuButtons(Table table) {

        table.bottom();

        addPlayButton(table);
        addOptionsButton(table);
        addNetworkingButton(table);
        addExitButton(table);

        table.padBottom(BOTTOM_EDGE);
    }

    /**
     * Adds the Exit Button to the Main Menu.
     *
     * @param table The table to where the Exit button will be added.
     */
    private void addExitButton(Table table) {
        TextButton exitButton = new TextButton("Exit", skin1);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(exitButton).size(BUTTON_WIDTH, DEFAULT_BUTTON_SIZE).pad(BUTTON_EDGE).row();
    }

    /**
     * Adds the Networking Button to the Main Menu.
     *
     * @param table The table to where the Networking button will be added.
     */
    private void addNetworkingButton(Table table) {
        TextButton networkingButton = new TextButton("Networking", skin1);
        networkingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new NetworkingMenuScreen(game));
            }
        });
        table.add(networkingButton).size(BUTTON_WIDTH, DEFAULT_BUTTON_SIZE).pad(BUTTON_EDGE).row();
    }

    /**
     * Adds the Options Button to the Main Menu.
     *
     * @param table The table to where the Options button will be added.
     */
    private void addOptionsButton(Table table) {
        TextButton optionsButton = new TextButton("Options", skin1);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CustomizeMenuScreen(game));
            }
        });
        table.add(optionsButton).size(BUTTON_WIDTH, DEFAULT_BUTTON_SIZE).pad(BUTTON_EDGE).row();
    }

    /**
     * Adds the play Button to the Main Menu.
     *
     * @param table The table to where the play button will be added.
     */
    private void addPlayButton(Table table) {
        TextButton playButton = new TextButton("Play", skin1);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelMenuScreen(game));
            }
        });
        table.add(playButton).size(BUTTON_WIDTH, DEFAULT_BUTTON_SIZE).pad(BUTTON_EDGE).row();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        super.show();

        Table table = new Table();
        table.setFillParent(true);

        createMenuButtons(table);

        stage.addActor(table);
    }
}