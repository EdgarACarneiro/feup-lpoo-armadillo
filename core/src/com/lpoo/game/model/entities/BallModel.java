package com.lpoo.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A model representing the player's ball.
 */
public class BallModel extends EntityModel {
    private static final float POWERUP_RATIO = 1.5f;

    public enum State {LANDED, FLYING, DUNKING}

    private static final float ANGULAR_DAMP = 5f;
    private static final float LINEAR_DAMP = 0.1f;

    public static final float RADIUS = 32 * PIXEL_TO_METER;

    private float density = .5f;
    private float friction = 10f;
    private float restitution = 0.6f;

    private State state = State.LANDED;

    private float angular_accel = density * 22000f;
    private float jump_force = density * 60f;

    public BallModel(World world, Vector2 pos) {
        super(world, pos, ModelType.BALL, ANGULAR_DAMP, LINEAR_DAMP);

        // Create Fixture's Shape
        Shape circle = new CircleShape();
        circle.setRadius(RADIUS);

        createFixture(circle, density, friction, restitution, BALL_BIT, (short) (BALL_BIT | GROUND_BIT | FLUID_BIT | HITTABLE_BIT));
    }

    public void rotate(float delta) {
        getBody().applyTorque(delta * angular_accel, true);
    }

    public void jump() {
        Body body = getBody();
        switch (this.state) {
            case LANDED:
                body.applyLinearImpulse(new Vector2(0, jump_force), body.getWorldCenter(), true);
                this.state = State.FLYING;
                break;
            case FLYING:
                body.applyLinearImpulse(new Vector2(0, -1.5f * jump_force), body.getWorldCenter(), true);
                this.state = State.DUNKING;
                break;
            case DUNKING:
                // Do nothing
                break;
            default:
                System.err.println("Unhandled state in BallEntity");
        }
    }

    public void setState(State st) {
        this.state = st;
    }

    public void increaseDensity() {
        density *= POWERUP_RATIO;
        getBody().getFixtureList().first().setDensity(density);
    }

    public void decreaseDensity() {
        density *= 1 / POWERUP_RATIO;
        getBody().getFixtureList().first().setDensity(density);
    }

    public void increaseVelocity() {
        angular_accel *= POWERUP_RATIO;
    }

    public void decreaseVelocity() {
        angular_accel *= 1 / POWERUP_RATIO;
    }

    public void flipGravity() {
        getBody().setGravityScale(-1);
        jump_force *= -1;
    }

}
