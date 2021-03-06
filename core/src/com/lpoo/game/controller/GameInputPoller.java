package com.lpoo.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lpoo.game.model.GameModel;

/**
 * Input poller for in-game events. Accelerometer data can only be polled.
 */
public class GameInputPoller implements InputPoller {

    /**
     * The current game being played by the User.
     */
    private GameModel model;

    /**
     * Boolean regarding the Accelerometer availability.
     */
    private Boolean accelerometerAvailable;

    /**
     * Game Controller's constructor.
     *
     * @param model The current game being played by the USer.
     */
    public GameInputPoller(GameModel model) {
        this.model = model;

        if (!(accelerometerAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)))
            System.err.println("Accelerometer unavailable");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pollInput(float delta) {
        if (accelerometerAvailable)
            pollAccelerometer(delta);
        else
            pollKeys(delta);
    }

    /**
     * Decide what to do when the keys are pressed.
     *
     * @param delta The time elapsed since the last update
     */
    private void pollKeys(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            model.getBallModel().rotate(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            model.getBallModel().rotate(delta * -1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            model.getBallModel().jump();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            model.togglePause();
        }
    }

    /**
     * Decide what to do when the Accelerometer is available and there is movement.
     *
     * @param delta The tme elapsed since the last update
     */
    private void pollAccelerometer(float delta) {
        float y = Gdx.input.getAccelerometerY();
        float z = Gdx.input.getAccelerometerZ();

        float roll = (float) Math.atan2(y, z) * 180 / ((float) Math.PI);

        if (roll > 45)
            roll = 45;
        else if (roll < -45)
            roll = -45;

        model.getBallModel().rotate(delta * (roll / -45));
    }

}
