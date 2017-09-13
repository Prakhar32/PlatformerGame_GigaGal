package com.udacity.gamedev.gigagal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.gigagal.entities.Enemy;
import com.udacity.gamedev.gigagal.entities.GigaGal;
import com.udacity.gamedev.gigagal.entities.Platform;
import com.udacity.gamedev.gigagal.util.Constants;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by hp on 23-Aug-17.
 */

public class Level {
    static GigaGal gigaGal;
    Array<Platform> platforms;
    Array<Platform> enemyPlatforms;
    Array<Enemy> enemies;

    Level(){
        gigaGal = new GigaGal();
        platforms = new Array<Platform>();
        enemies = new Array<Enemy>();
        enemyPlatforms = new Array<Platform>();
        int noOfPlatforms = (int)MathUtils.random(10, Constants.MAX_NUMBER_OF_PLATFORM);
        Gdx.app.log("number", " = "+noOfPlatforms);

        for(int i = 0; i < noOfPlatforms; i++){
            platforms.add(new Platform());
        }

        gigaGal.Platform_Initializer(platforms);
        platforms.sort();
        gigaGal.initail_platform_assigner(platforms.first());

        for(int i = 1; i < noOfPlatforms; i += 3)
        {
            enemyPlatforms.add(platforms.get(i));
            enemies.add( new Enemy(platforms.get(i).bottom + platforms.get(i).length,
                    platforms.get(i).left + platforms.get(i).width / 2,
                    platforms.get(i) ));
        }
        gigaGal.EnemyInitializer(enemies);

    }

    public void render(SpriteBatch batch){
        for(Platform platform: platforms){
            platform.render(batch);
        }
        for(Enemy enemy:enemies){
            enemy.render(batch);
        }

        gigaGal.render(batch);
    }

    public void update(float delta){

    }
}
