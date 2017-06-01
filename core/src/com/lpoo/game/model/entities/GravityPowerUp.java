package com.lpoo.game.model.entities;

import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by andre on 01/06/2017.
 */

public class GravityPowerUp extends PowerUp {

    public GravityPowerUp(World world, RectangleMapObject object) {
        super(world, object, ModelType.POWERUP_GRAVITY);
    }


    @Override
    public void onHit(BallModel model) {
        flagForRemoval();
        model.flipGravity();
    }
}
