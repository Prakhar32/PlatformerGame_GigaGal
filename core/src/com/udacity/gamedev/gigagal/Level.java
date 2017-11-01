package com.udacity.gamedev.gigagal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.udacity.gamedev.gigagal.entities.Enemy;
import com.udacity.gamedev.gigagal.entities.ExitPortal;
import com.udacity.gamedev.gigagal.entities.GigaGal;
import com.udacity.gamedev.gigagal.entities.Platform;
import com.udacity.gamedev.gigagal.entities.PowerUp;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by hp on 23-Aug-17.
 */

public class Level {
    static GigaGal gigaGal;
    Array<Platform> platforms;
    Array<Platform> enemyPlatforms;
    static Array<Enemy> enemies;
    Array<PowerUp> powerUpes;
    static ExitPortal exitPortal;
    public static float score;

    public Level(){
        gigaGal = new GigaGal();
        platforms = new Array<Platform>();
        enemies = new Array<Enemy>();
        enemyPlatforms = new Array<Platform>();
        powerUpes = new Array<PowerUp>();
        int noOfPlatforms = (int)MathUtils.random(10, Constants.MAX_NUMBER_OF_PLATFORM);
        Gdx.app.log("number", " = "+noOfPlatforms);
        score = 0;

        /*for(int i = 0; i < noOfPlatforms; i++){
            platforms.add(new Platform());
        }*/

        /*gigaGal.initail_platform_assigner(platformToVector2(platforms.first()));

        for(int i = 1; i < noOfPlatforms; i += 3)
        {
            enemyPlatforms.add(platforms.get(i));
            enemies.add( new Enemy(platforms.get(i)));
        }*/

        /*for(int i = 2; i < platforms.size; i += 5) {
            PowerUp powerUp = new PowerUp(new Vector2(platforms.get(i).left, platforms.get(i).bottom + platforms.get(i).length));
            powerUpes.add(powerUp);
        }*/
        /*
        exitPortal = new ExitPortal(platformToVector2(platforms.get(platforms.size - 1)));*/
    }

    public void render(SpriteBatch batch){
        for(Platform platform : platforms){
            platform.render(batch);
        }
        for(Enemy enemy : enemies){
            enemy.render(batch);
        }
        for(PowerUp powerUp : powerUpes){
            powerUp.render(batch);
        }

        exitPortal.render(batch);
        gigaGal.render(batch);
    }

    public Array<Enemy> getEnemies(){
        return enemies;
    }

    public Array<Platform> getPlatforms(){
        return platforms;
    }

    public ExitPortal getExitPortals(){
        return exitPortal;
    }

    public static void setGigaGalposition(Vector2 galposition){
        gigaGal.initail_platform_assigner(galposition);
    }

    public void setExitPortal(ExitPortal exitPortal){
        this.exitPortal = exitPortal;
    }

    public Array<PowerUp> getPowerUpes(){
        return powerUpes;
    }

    public GigaGal getGigaGal(){
        return gigaGal;
    }

    public void gigaPlat(Vector2 vector2){
        gigaGal.Platform_Initializer(platforms);
        Gdx.app.log("Level", "At least till here");
        try{
        Vector2ToPlatform(vector2).hasLandedOnPlatform = true;}
        catch (NullPointerException e){
            Gdx.app.log("Level", "Caught here");
        }
        platforms.sort();
    }

    public void otherEntitiesInitializer(){
        gigaGal.EnemyInitializer(enemies);
        gigaGal.PowerUpsInitializer(powerUpes);
    }

    public void update(float delta, SpriteBatch batch){
        gigaGal.update(delta, batch);
    }

   /* public Vector2 platformToVector2(Platform platform){
        Vector2 afterConversion = new Vector2();
        afterConversion.x = platform.left;
        afterConversion.y = platform.bottom + platform.length;
        return afterConversion;
    }*/

    public Platform Vector2ToPlatform(Vector2 vector2){
        Platform p = null;
        for(Platform platform : platforms){
            if(platform.left == vector2.x && platform.bottom + platform.length == vector2.y)
                p = platform;
        }
        return p;
    }

    public boolean LevelEnd(){
        gigaGal.GigaGalRectInit();
        Rectangle Exit = new Rectangle(exitPortal.left, exitPortal.bottom, exitPortal.region.getRegionWidth(), exitPortal.region.getRegionHeight());
        if(gigaGal.GigaGalRect.overlaps(Exit))
            return true;
        return false;
    }
}
