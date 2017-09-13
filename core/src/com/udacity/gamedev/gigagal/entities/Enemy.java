package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by hp on 09-Sep-17.
 */

public class Enemy {
    TextureRegion enemysprite;
    Vector2 enemy_pos;
    Platform platform;
    Vector2 enemy_velocity;
    HorizontalMoveState horizontalmovestate;
    VerticalMoveState verticalMoveState;
    float starttimer;

    public Enemy(float height, float left, Platform platform){
        enemy_pos = new Vector2();
        enemy_velocity = new Vector2();
        enemy_pos.x = left;
        enemy_pos.y = height;
        this.platform = platform;
        enemysprite = Assets.instance.enemyAssets.emeny_sprite;
        horizontalmovestate = HorizontalMoveState.MOVING_RIGHT;
        verticalMoveState = VerticalMoveState.MOVING_UP;
        starttimer = TimeUtils.nanoTime() / (float)1e+9;
    }

    public void render(SpriteBatch batch){
        float delta = Gdx.graphics.getDeltaTime();
        update(delta);
        batch.draw(enemysprite.getTexture(),
                enemy_pos.x - enemysprite.getRegionWidth() / 2,
                enemy_pos.y,
                0,
                0,
                enemysprite.getRegionWidth(),
                enemysprite.getRegionHeight(),
                1,
                1,
                0,
                enemysprite.getRegionX(),
                enemysprite.getRegionY(),
                enemysprite.getRegionWidth(),
                enemysprite.getRegionHeight(),
                false,
                false);
    }

    public void update(float delta){
        inBounds();
        Vertical_Movement();

        if(horizontalmovestate == HorizontalMoveState.MOVING_RIGHT)
            enemy_velocity.x = Constants.ENEMY_HORIZONTAL_VELOCITY;

        if(horizontalmovestate == HorizontalMoveState.MOVING_LEFT)
            enemy_velocity.x = -Constants.ENEMY_HORIZONTAL_VELOCITY;

        if(verticalMoveState == VerticalMoveState.MOVING_UP)
            enemy_velocity.y = Constants.ENEMY_VERTICAL_VELOCITY;

        if(verticalMoveState == VerticalMoveState.MOVING_DOWN)
            enemy_velocity.y = -Constants.ENEMY_VERTICAL_VELOCITY;

        enemy_pos.x += delta * enemy_velocity.x;
        enemy_pos.y += delta * enemy_velocity.y;
    }

    public void inBounds(){
        if(enemy_pos.x + 5 > platform.left + platform.width){
            enemy_velocity.x = 0;
            horizontalmovestate = HorizontalMoveState.MOVING_LEFT;
        }

        if(enemy_pos.x - 5 < platform.left){
            enemy_velocity.x = 0;
            horizontalmovestate = HorizontalMoveState.MOVING_RIGHT;
        }
    }

    public void Vertical_Movement(){
        float currentTime = TimeUtils.nanoTime() / (float) 1e+9;
        //Gdx.app.log("EnemyTime", " " + (currentTime - starttimer));
        if(currentTime - starttimer >= Constants.ENEMY_TIMER && verticalMoveState == VerticalMoveState.MOVING_UP){
            enemy_velocity.y = 0;
            verticalMoveState = VerticalMoveState.MOVING_DOWN;
            starttimer = 0;
          //  Gdx.app.log("Executed", "Downward Movement");
        }

        if(enemy_pos.y < platform.bottom + platform.length){
            enemy_velocity.y = 0;
            verticalMoveState = VerticalMoveState.MOVING_UP;
            starttimer = currentTime;
        }

    }
}

enum HorizontalMoveState{
    MOVING_LEFT,
    MOVING_RIGHT;
}

enum VerticalMoveState{
    MOVING_UP,
    MOVING_DOWN;
}