package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by hp on 23-Aug-17.
 */

public class GigaGal extends InputAdapter {
    public Vector2 position;
    public Vector2 velocity;
    boolean has_jumped;
    float jump_start;
    //Viewport viewport;
    boolean top_reached;
    Animation walking;
    Array<TextureRegion> movementLeftspriteArray;
    Array<TextureRegion> movementRighspriteArray;
    static JumpState jumpState;
    Array<Platform> platforms;
    private Vector2 initpos;
    FACING facing;
    TextureRegion region;
    Array<Enemy> enemies;
    boolean hitByEnemy;
    float TimeSinceEnemyHit;

    public GigaGal(){
        position = new Vector2();
        velocity = new Vector2();
        initpos = new Vector2();
        has_jumped = false;
        jump_start = 0;
        //this.viewport = viewport;
        top_reached = false;
        movementLeftspriteArray = new Array<TextureRegion>();
        movementRighspriteArray = new Array<TextureRegion>();
        jumpState = JumpState.GROUNDED;
        facing = FACING.RIGHT;
        enemies = new Array<Enemy>();

        //Seperating the total movement to left and right movement
        for(int i = 4; i < Assets.instance.gigaGalAssets.gigaGalSprites.size; i++){
            TextureRegion textureRegion = Assets.instance.gigaGalAssets.gigaGalSprites.get(i);
            if(i < 7) {
                movementRighspriteArray.add(textureRegion);
            }
            else
                movementLeftspriteArray.add(textureRegion);
        }
        TimeSinceEnemyHit = 0;
    }

    public void Platform_Initializer(Array<Platform> platform){
        this.platforms = platform;
        /*position.x = platform.get(0).left;
        position.y = platform.get(0).bottom + platform.get(0).length - 2;
        platform.get(0).hasLandedOnPlatform = true;
        initpos.x = position.x;
        initpos.y = position.y;*/
    }

    public void initail_platform_assigner(Platform platform){
        position.x = platform.left;
        position.y = platform.bottom + platform.length - 2;
        platform.hasLandedOnPlatform = true;
        initpos.x = position.x;
        initpos.y = position.y;
        hitByEnemy = false;
    }

    public void render(SpriteBatch batch){
        update(Gdx.graphics.getDeltaTime());
        AllPlatformManager();
        region = spriteSelector();
        if(region == null)
            return;
       RespondToEnemies();

      //  Gdx.app.log("Dimensions", "Width: " + region.getRegionWidth() + "Height: "+ region.getRegionHeight());
        batch.draw(region.getTexture(),
                    position.x - Constants.LENGTH_CORRECTION,
                    position.y - Constants.HEIGHT_CORRECTION,
                    0,
                    0,
                    region.getRegionWidth(),
                    region.getRegionHeight(),
                    1,
                    1,
                    0,
                    region.getRegionX(),
                    region.getRegionY(),
                    region.getRegionWidth(),
                    region.getRegionHeight(),
                    false,
                    false);
    }

    //variable to check the last time when click has occured.
    //It is to stop the movement of Gigagal after a certain time when button has not been clicked
    float timesincelastclick;

    public void update(float delta){
        if(TimeUtils.nanoTime() / 1e+9 - TimeSinceEnemyHit > Constants.ENEMY_HIT_FREEZE_TIME)
            hitByEnemy = false;

        if(!hitByEnemy) {

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                timesincelastclick = TimeUtils.nanoTime();
                timesincelastclick /= 1e+9;

                if (velocity.x < 0)
                    velocity.x += 2 * delta * Constants.HORIZONTAL_SPEED_MULTIPLIER;
                else
                    velocity.x += delta * Constants.HORIZONTAL_SPEED_MULTIPLIER;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if (position.x == 1)
                    return;

                timesincelastclick = TimeUtils.nanoTime();
                timesincelastclick /= 1e+9;
                if (velocity.x > 0)
                    velocity.x -= 2 * delta * Constants.HORIZONTAL_SPEED_MULTIPLIER;
                else
                    velocity.x -= delta * Constants.HORIZONTAL_SPEED_MULTIPLIER;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (jumpState == JumpState.GROUNDED) {
                    has_jumped = true;
                    jumpState = JumpState.JUMPING;
                    jump_start = (float) (System.nanoTime() / 1e+9);
                }
            }
        }

        InBounds();
        upwardMovement(delta);

        float currentTime = TimeUtils.nanoTime();
        currentTime /= 1e+9;
        float timeElasped = currentTime - timesincelastclick;

        if(timeElasped >= Constants.MAX_TIME_SINCE_CLICK){
            if(velocity.x > 2)
                velocity.x -= 1.5f * delta * Constants.HORIZONTAL_SPEED_MULTIPLIER;

            else if(velocity.x < -2)
                velocity.x += 1.5f * delta * Constants.HORIZONTAL_SPEED_MULTIPLIER;

            else
                velocity.x = 0;
        }

        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

    }

    public void InBounds(){
   //   Gdx.app.log("Viewport", "Height: " + viewport.getWorldHeight() + " Width: " + viewport.getWorldWidth());
        float height = Assets.instance.gigaGalAssets.gigaGalSprites.get(1).getRegionHeight();
        float width = Assets.instance.gigaGalAssets.standingright.getRegionWidth();

       /* if(position.x  > viewport.getWorldWidth() - width){
            position.x = viewport.getWorldWidth() - width - 1;
            velocity.x = 0;
        }

        if(position.x < 0){
            position.x = 1;
            velocity.x = 0;
        }

        if(position.y > viewport.getWorldHeight() - 40){
            position.y = viewport.getWorldHeight() - 40;
            velocity.y = 0;jumpState = JumpState.FALLING;jump_start = (float)(System.nanoTime() / 1e+9) - Constants.JUMP_DURATION;
        }
*/
        if(position.y < -100){
            position.x = initpos.x;
            position.y = initpos.y + 2;
            Gdx.app.log("position " + initpos.x, "" + initpos.y);
            velocity.y = 0;
            velocity.x = 0;
        }
    }

    float jump_duration;
    public void upwardMovement(float delta){
        if(jumpState == JumpState.GROUNDED)
            return ;

        jump_duration = (float)(System.nanoTime() / 1e+9) - jump_start;
        Gdx.app.log("TimeDur: ", "" + jump_duration);

        if(jump_duration < Constants.JUMP_DURATION){
            jumpState = JumpState.JUMPING;}

        else {
            if(!top_reached){
                velocity.y = 0;
                top_reached = true;
            }
            jumpState = JumpState.FALLING;
        }

        if(jumpState == JumpState.JUMPING)
            velocity.y = Constants.VERTICAL_SPEED_MULTIPLIER;

         if(jumpState == JumpState.FALLING)
            velocity.y = -Constants.VERTICAL_SPEED_MULTIPLIER * 1.25f;
    }

    public void AllPlatformManager(){
        for(Platform platform: platforms){
            PlatformManager(platform);
        }
    }

    public void PlatformManager(Platform platform){
        float top = platform.bottom + platform.length;
        float gigaGalwidth = Assets.instance.gigaGalAssets.standingright.getRegionWidth() / 2;
        float gigaGalHeight = Assets.instance.gigaGalAssets.standingright.getRegionHeight();

        if(withinPlatformWidth(platform, gigaGalwidth) && isfallingOnPlatform(gigaGalHeight, top) && jumpState == JumpState.FALLING ){
            velocity.y = 0;
            jumpState = JumpState.GROUNDED;
            platform.hasLandedOnPlatform = true;
            Gdx.app.log("Height" + position.y, "Width" + position.x);
            has_jumped = false;
            top_reached = false;
            jump_start = 0;
        }

        if(!withinPlatformWidth(platform, gigaGalwidth) && platform.hasLandedOnPlatform){
            platform.hasLandedOnPlatform = false;jumpState = JumpState.FALLING;
        }
    }

    public boolean withinPlatformWidth(Platform platform, float Gigagalwidth){
        float width = platform.width;
        float left = platform.left;
        if(position.x + Gigagalwidth >= left && position.x + Gigagalwidth < left + width)
            return true;
        return false;
    }

    public boolean isfallingOnPlatform(float gigaGalHeight, float top){
        if(position.y + 2 >= top && position.y + 2 < top + 1)
            return true;
        return false;
    }

    boolean isprevleft = false;
    boolean hasanimationstarted = false;

    public TextureRegion spriteSelector(){
        TextureRegion selected = null;

        if(velocity.x > 0)
            facing = FACING.RIGHT;

        if(velocity.x < 0)
            facing = FACING.LEFT;

        if(jumpState != JumpState.GROUNDED && facing == FACING.LEFT){
            selected = Assets.instance.gigaGalAssets.gigaGalSprites.get(3);return selected;}

        if(jumpState != JumpState.GROUNDED && facing == FACING.RIGHT){
            selected = Assets.instance.gigaGalAssets.gigaGalSprites.get(1);return selected;}

        if(velocity.x == 0 && facing == FACING.RIGHT){
            selected = Assets.instance.gigaGalAssets.gigaGalSprites.get(0);
            hasanimationstarted = false;return selected;
        }

        if(velocity.x == 0 && facing == FACING.LEFT){
            selected = Assets.instance.gigaGalAssets.gigaGalSprites.get(2);
            hasanimationstarted = false;return selected;
        }

        if(velocity.x != 0 && jumpState == JumpState.GROUNDED)
        {selected = Animate();}

        return selected;
    }

    float startTime = 0;
    TextureRegion Animate() {

        TextureRegion region = null;
        if(!hasanimationstarted){
            startTime = TimeUtils.nanoTime();hasanimationstarted = true;
        }

        if(facing == FACING.LEFT) {
            walking = new Animation(0.2f, movementLeftspriteArray, Animation.PlayMode.LOOP_PINGPONG);
            float timeElasped = TimeUtils.nanoTime() - startTime;
            timeElasped /= 1e+9;
             region = (TextureRegion) walking.getKeyFrame(timeElasped);
        }

        else{
            walking = new Animation(0.2f, movementRighspriteArray, Animation.PlayMode.LOOP_PINGPONG);
            float timeElasped = TimeUtils.nanoTime() - startTime;
            timeElasped /= 1e+9;
             region = (TextureRegion) walking.getKeyFrame(timeElasped);
        }

        return  region;
    }

    public void EnemyInitializer(Array<Enemy> enemies){
        this.enemies = enemies;
    }

    public void RespondToEnemies(){
        for(Enemy enemy : enemies){
            EnemyCollisionResponse(enemy);
        }
    }

    public void EnemyCollisionResponse(Enemy enemy){

        if(facing == FACING.RIGHT && collisionDetector(enemy)) {
            hitByEnemy = true;
            TimeSinceEnemyHit = TimeUtils.nanoTime() / (float)1e+9;
            velocity.x += -Constants.RECOIL_VELOCITY;Gdx.app.log("Respond", "");
            // velocity.y = Constants.RECOIL_VERTICAL_VELOCITY;
        }

        if(facing == FACING.LEFT && collisionDetector(enemy)){
            hitByEnemy = true;
            TimeSinceEnemyHit = TimeUtils.nanoTime() / (float)1e+9;
            velocity.x += Constants.RECOIL_VELOCITY;
            //velocity.y = Constants.RECOIL_VERTICAL_VELOCITY;
        }
    }

    /*public boolean spriteCollisionChecker(Enemy enemy){
        if(position.x + region.getRegionWidth() > enemy.enemy_pos.x && position.y + region.getRegionHeight() < enemy.enemy_pos.y + enemy.enemysprite.getRegionHeight())
            return true;
        return false;
    }*/

   /* public boolean lengthCollision(Enemy enemy){
        if(position.x + region.getRegionWidth() > enemy.enemy_pos.x && position.x + region.getRegionWidth() < enemy.enemy_pos.x + enemy.enemysprite.getRegionWidth())
            return true;
        return false;
    }

    public boolean heightCollision(Enemy enemy){
        if(position.y + region.getRegionHeight() < enemy.enemy_pos.y + enemy.enemysprite.getRegionHeight() && position.y + region.getRegionHeight() > enemy.enemy_pos.y)
            return true;
        return false;
    }
    */

    public boolean collisionDetector(Enemy enemy){
        if(position.x + region.getRegionWidth() + Constants.LENGTH_CORRECTION< enemy.enemy_pos.x)
            return false;
        if(position.x + Constants.LENGTH_CORRECTION> enemy.enemy_pos.x + enemy.enemysprite.getRegionWidth())
            return false;
        if(position.y + region.getRegionHeight() < enemy.enemy_pos.y)
            return false;
        if(position.y > enemy.enemy_pos.y + enemy.enemysprite.getRegionHeight())
            return false;

        return  true;
    }
}

enum JumpState{
        JUMPING,
        FALLING,
        GROUNDED;
        }

enum FACING{
    LEFT,
    RIGHT;
}