package com.lpoo.game.model.entities;

import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static com.lpoo.game.view.screens.GameScreen.PIXEL_TO_METER;

/**
 * Created by andre on 31/05/2017.
 */

public abstract class PowerUp extends EntityModel implements Hittable {

    public PowerUp(World world, RectangleMapObject object, ModelType type) {
        super(world, object.getRectangle().getCenter(new Vector2()).scl(PIXEL_TO_METER), type);

        getBody().setType(BodyDef.BodyType.StaticBody);

        Shape shape = createPolygonShape(new float[]{
                14f, 1f, 18f, 1f, 25f, 7f, 25f, 23f, 18f, 31f, 14f, 31f, 7f, 23f, 7f, 7f
        }, new Vector2(32, 32));

        createSensorFixture(shape, HITTABLE_BIT, BALL_BIT);
    }

    private void createSensorFixture(Shape shape, short category, short mask) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.maskBits = mask;
        fixtureDef.filter.categoryBits = category;

        getBody().createFixture(fixtureDef);

        shape.dispose();
    }

}
