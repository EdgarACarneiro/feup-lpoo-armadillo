package com.lpoo.game.model.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.game.model.controllers.BuoyancyController;

import static com.lpoo.game.model.entities.EntityModel.FLUID_BIT;
import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * A model representing a single body of water (or any other newtonian fluid).
 */
public class WaterModel {

    /**
     * The Fluid's body.
     */
    private Body body;

    /**
     * Water Model's constructor.
     * Creates a water model from the given object, into the given world.
     *
     * @param world The world the water model will be in.
     * @param rect  The rectangle to create the water model with.
     */
    public WaterModel(World world, Rectangle rect) {
        // Body and Fixture variables
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(PIXEL_TO_METER * (rect.getX() + rect.getWidth() / 2), PIXEL_TO_METER * (rect.getY() + rect.getHeight() / 2));

        this.body = world.createBody(bdef);

        shape.setAsBox((rect.getWidth() / 2) * PIXEL_TO_METER, (rect.getHeight() / 2) * PIXEL_TO_METER);
        fdef.shape = shape;
        fdef.filter.categoryBits = FLUID_BIT;
        fdef.isSensor = true;
        fdef.density = 1f;
        fdef.friction = 0.1f;
        fdef.restitution = 0f;
        body.createFixture(fdef);

        body.setUserData(new BuoyancyController(world, body.getFixtureList().first()));

    }

    /**
     * Function used to update the water model. This function is called in the game loop.
     */
    public void step() {
        ((BuoyancyController) this.body.getUserData()).step();
    }
}
