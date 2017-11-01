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
    public static final float JUMP_DURATION = 1.2f;
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
    public static final float MAXIMUM_PLATFORM_HEIGHT = 200f;
    public static final float MAXIMUM_PLATFORM_LEFT = 200f;
    public static final String PLATFORM_NAME = "platform";
    public static final int EDGE = 8;

    public static final float MAX_NUMBER_OF_PLATFORM = 30;

    public static final float COMERA_MOVEMENT_SPEED = 128;

    public static final String ENEMY_NAME = "enemy";
    public static final float ENEMY_HORIZONTAL_VELOCITY = 5f;
    public static final float ENEMY_VERTICAL_VELOCITY = 2f;
    public static final float ENEMY_TIMER = 2;

    public static final float RECOIL_VELOCITY = 150f;
    public static final float RECOIL_VERTICAL_VELOCITY = 200f;
    public static final float ENEMY_HIT_FREEZE_TIME = 0.5f;
    public static final float ENEMY_LENGTH_CORRECTION = 4f;
    public static final float ENEMY_HEIGHT_CORRECTION = 2f;

    public static final String EXPLOSION_SMALL = "explosion-small";
    public static final String EXPLOSION_MEDIUM = "explosion-medium";
    public static final String EXPLOSION_LARGE = "explosion-large";

    public static final String POWERUP = "powerup";
    public static final int BULLET_PER_POWERUP = 10;

    public static final String BULLET = "bullet";
    public static final float BULLET_VELOCITY = 180f;
    public static final float EXPLOSION_TIME_DURATION = 0.5f;

    public static final String LEVEL_DIR = "levels";
    public static final String LEVEL_FILE_EXTENSION = "dt";
    public static final String LEVEL_COMPOSITE = "composite";
    public static final String LEVEL_9PATCHES = "sImage9patchs";
    public static final String LEVEL_IMAGES = "sImages";
    public static final String LEVEL_ERROR_MESSAGE = "Problem loading level";
    public static final String LEVEL_IMAGENAME_KEY = "imageName";
    public static final String LEVEL_X_KEY = "x";
    public static final String LEVEL_Y_KEY = "y";
    public static final String LEVEL_WIDTH_KEY = "width";
    public static final String LEVEL_HEIGHT_KEY = "height";
    public static final String LEVEL_IDENTIFIER_KEY = "itemIdentifier";
    public static final String LEVEL_ENEMY_TAG = "Enemy";

    public static final String EXIT_PORTAL_NAME_1 = "exit-portal-1";
    public static final String EXIT_PORTAL_NAME_2 = "exit-portal-2";
    public static final String EXIT_PORTAL_NAME_3 = "exit-portal-3";
    public static final String EXIT_PORTAL_NAME_4 = "exit-portal-4";
    public static final String EXIT_PORTAL_NAME_5 = "exit-portal-5";
    public static final String EXIT_PORTAL_NAME_6 = "exit-portal-6";

    public static final float HUD_SIZE = 280;
    public static final String AMMO = "AMMO";
    public static final String SCORE = "SCORE";

    public static final float ENEMY_KILL_SCORE = 100;
    public static final float ENEMY_HIT_SCORE = 25;
    public static final float POWERUP_BONUS = 50;

    public static final float SPACING_BETWEEN_LIVES = 35;
    public static final float OVERLAY_TIMER = 5;

    public static final String JUMP_BUTTON = "button-jump";
    public static final String MOVE_LEFT_BUTTON = "button-move-left";
    public static final String MOVE_RIGHT_BUTTON = "button-move-right";
    public static final String SHOOT_BUTTON = "button-shoot";

    public static final float BUTTON_POSITION1_X = 70;
    public static final float BUTTON_POSITION1_Y = 40;
    public static final float BUTTON_POSITION2_X = 55f;
    public static final float BUTTON_POSITION2_Y = 50f;
}