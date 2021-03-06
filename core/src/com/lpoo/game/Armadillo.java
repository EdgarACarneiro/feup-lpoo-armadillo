package com.lpoo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lpoo.game.view.screens.MainMenuScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * The Game's main class.
 */
public class Armadillo extends Game {

    /**
     * The Game's Sprite Batch.
     */
    private SpriteBatch batch;

    /**
     * The Game's Asset Manager.
     */
    private AssetManager assetManager;

    /**
     * One of the game's skins.
     */
    private Skin skin1;
    /**
     * Another game skin.
     * This is one is mostly used for square shaped buttons.
     */
    private Skin skin2;

    /**
     * Number of available Skins.
     */
    private static final int NUMBER_OF_SKINS = 6;

    /**
     * HashMAp containing the Game Maps.
     */
    private static final Map<Integer, String> gameMaps = new HashMap<>();

    /**
     * Game Services used in the game for connection with Google Play Services.
     */
    private final GameServices gameServices;

    static {
        gameMaps.put(0, "maps/map0.tmx");
        gameMaps.put(1, "maps/map10.tmx");
        gameMaps.put(2, "maps/map1.tmx");
        gameMaps.put(3, "maps/map11.tmx");
        gameMaps.put(4, "maps/map2.tmx");
        gameMaps.put(5, "maps/map3.tmx");
        gameMaps.put(6, "maps/map4.tmx");
        gameMaps.put(7, "maps/map5.tmx");
        gameMaps.put(8, "maps/map6.tmx");
        gameMaps.put(9, "maps/map12.tmx");
        gameMaps.put(10, "maps/map7.tmx");
        gameMaps.put(11, "maps/map13.tmx");
        gameMaps.put(12, "maps/map14.tmx");
        gameMaps.put(13, "maps/map16.tmx");
        gameMaps.put(14, "maps/map15.tmx");
        gameMaps.put(15, "maps/map8.tmx");
        gameMaps.put(16, "maps/map9.tmx");
    }

    /**
     * Armadillo's Constructor
     *
     * @param gameServices The game Services that will be used during the game.
     */
    public Armadillo(GameServices gameServices) {
        this.gameServices = gameServices;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();

        skin1 = new Skin(Gdx.files.internal("appearance/Armadillo.json"), new TextureAtlas("appearance/Armadillo.atlas"));
        skin2 = new Skin(Gdx.files.internal("appearance/smallBtn.json"), new TextureAtlas("appearance/smallBtn.atlas"));

        loadAssets();
        setMusic();
        startGame();
    }

    /**
     * Set the Game's Music.
     */
    private void setMusic() {
        Music music = Gdx.audio.newMusic(Gdx.files.internal("music/Artofescapism_-_Three_Star_Sky.mp3"));
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
    }

    /**
     * Starts the Game.
     */
    private void startGame() {
        setScreen(new MainMenuScreen(this));
    }

    /**
     * Loads the assets needed by all screens.
     */
    private void loadAssets() {
        //Load Main Menu Background and Title
        assetManager.load("armadillo_title.png", Texture.class);
        assetManager.load("background.png", Texture.class);

        //Load Game's Virtual Components
        assetManager.load("pause.png", Texture.class);

        loadEntitySkins();
        loadLevels();
        loadAnimations();

        assetManager.finishLoading();
    }

    /**
     * Load the skins used in the game entities.
     */
    private void loadEntitySkins() {
        // Box Skin
        assetManager.load("box.png", Texture.class);

        // Ball Skins
        for (int i = 0; i < NUMBER_OF_SKINS; i++) {
            assetManager.load(("skins/skin" + (i < 10 ? "0" : "") + i + ".png"), Texture.class);
            assetManager.load(("big_skins/skin" + (i < 10 ? "0" : "") + i + ".png"), Texture.class);
        }
    }

    /**
     * Load the game levels.
     */
    private void loadLevels() {
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        for (String entry : gameMaps.values())
            assetManager.load(entry, TiledMap.class);
    }

    /**
     * Load all the animations used in the game.
     */
    private void loadAnimations() {
        assetManager.load("animations/crystal-32-blue.png", Texture.class);
        assetManager.load("animations/crystal-32-green.png", Texture.class);
        assetManager.load("animations/crystal-32-grey.png", Texture.class);
        assetManager.load("animations/crystal-32-orange.png", Texture.class);
        assetManager.load("animations/crystal-32-pink.png", Texture.class);
        assetManager.load("animations/crystal-32-yellow.png", Texture.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
        super.dispose();
    }

    //Getters

    /**
     * @param i Index of the map.
     * @return The map of index i.
     */
    public String getMap(int i) {
        return gameMaps.get(i);
    }

    /**
     * @return The number of maps in the game.
     */
    public int getNumMaps() {
        return gameMaps.size();
    }

    /**
     * @return The number of skins in the game.
     */
    public int getNumSkins() {
        return NUMBER_OF_SKINS;
    }

    /**
     * @return The Game's Sprite Batch.
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * @return The Game's Asset Manager.
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * @return The mainly used game's skin.
     */
    public Skin getPrimarySkin() { return skin1; }

    /**
     * @return The skins used for square shaped buttons.
     */
    public Skin getSecondarySkin() { return skin2; }

    /**
     * @return The Game Services used in the game for connection with Google Play Services.
     */
    public GameServices getGameServices() {
        return gameServices;
    }
}
