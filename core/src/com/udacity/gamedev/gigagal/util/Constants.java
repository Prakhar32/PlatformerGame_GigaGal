 package com.udacity.gamedev.gigagal.util;
/**
 * Created by hp on 23-Aug-17.
 */
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final com.badlogic.gdx.graphics.Color BACKGROUND = com.badlogic.gdx.graphics.Color.SKY;
    public static final float WORLD_SIZE = 128;
    public static final float HEIGHT_CORRECTION = 8;
    public static final float LENGTH_CORRECTION = 2;

    public static final String TEXTURE_ATLUS = "images/gigagal.pack.atlas";
    public static final String STANDING_RIGHT = "standing-right";
    public static final String WALK_1_RIGHT = "walk-1-right";
    public static final String WALK_2_RIGHT = "walk-2-right";
    public static final String WALK_3_RIGHT = "walk-3-right";
    public static final String JUMP_RIGHT = "jumping-right";

    public static final String STANDING_LEFT = "standing-left";
    public static final String WALK_1_LEFT = "walk-1-left";
    public static final String WALK_2_LEFT = "walk-2-left";
    public static final String WALK_3_LEFT = "walk-3-left";
    public static final String JUMP_LEFT = "jumping-left";

    //public static  final Vector2 EYE_POSITION = new Vector2(16, 24);
    //public static final float MAX_SPEED_HORIZONTAL = 20f;
    public static final float HORIZONTAL_SPEED_MULTIPLIER = 50f;
    public static final float JUMP_DURATION = 0.9f;
    //public static  final float MAX_VERTICAL_VELOCITY = 10;
    public static final float VERTICAL_SPEED_MULTIPLIER = 50f;
    //public static final float HEIGHT_ABOVE_GROUND = 16;
    public static final float MAX_TIME_SINCE_CLICK = 1;

    public static final float PLATFORM_MINIMUM_WIDTH = 50f;
    public static final float PLATFORM_MINIMUM_LENGTH = 16f;
    public static final float PLATFORM_MAXIMUM_WIDTH = 100f;
    public static final float PLATFORM_MAXIMUM_LENGTH = 80f;
    public static final float MINIMUM_PLATFORM_HEIGHT = -50f;
    public static final float MINIMUM_PLATFORM_LEFT = -50f;
    public static final float MAXIMUM_PLATFORM_HEIGHT = 400f;
    public static final float MAXIMUM_PLATFORM_LEFT = 400f;
    public static final String PLATFORM_NAME = "platform";
    public static final int EDGE = 8;

    public static final float MAX_NUMBER_OF_PLATFORM = 60;

    public static final float COMERA_MOVEMENT_SPEED = 128;

    public static final String ENEMY_NAME = "enemy";
    public static final float ENEMY_HORIZONTAL_VELOCITY = 5f;
    public static final float ENEMY_VERTICAL_VELOCITY = 2f;
    public static final float ENEMY_TIMER = 2;

    public static final float RECOIL_VELOCITY = 150f;
    public static final float RECOIL_VERTICAL_VELOCITY = 100f;
    public static final float ENEMY_HIT_FREEZE_TIME = 1.2f;
}