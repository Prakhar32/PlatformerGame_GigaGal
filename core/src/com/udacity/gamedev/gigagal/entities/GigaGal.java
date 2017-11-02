package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.gigagal.Level;
import com.udacity.gamedev.gigagal.Overlays.GigaGalHud;
import com.udacity.gamedev.gigagal.Overlays.OnScreenControls;
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
    public TextureRegion region;
    Array<Enemy> enemies;
    boolean hitByEnemy;
    float TimeSinceEnemyHit;
    Array <PowerUp> powerUps;
    boolean ispoweredUp;
    Array<Bullet> bullets;
    public Rectangle GigaGalRect;
    public Rectangle EnemyRect;
    public Rectangle BulletRect;
    public int ammo;
    DelayedRemovalArray<Explosion> explosions;

    public GigaGal(){
        position = new Vector2();
        velocity = new Vector2();
        initpos = new Vector2();
        has_jumped = false;
        jump_start = 0;
        top_reached = false;
        movementLeftspriteArray = new Array<TextureRegion>();
        movementRighspriteArray = new Array<TextureRegion>();
        jumpState = JumpState.GROUNDED;
        facing = FACING.RIGHT;
        enemies = new Array<Enemy>();
        powerUps = new Array<PowerUp>();
        ispoweredUp = false;
        bullets = new Array<Bullet>();
        ammo = 0;
        explosions = new DelayedRemovalArray<Explosion>();

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
        hitByEnemy = false;
        region = Assets.instance.gigaGalAssets.gigaGalSprites.get(1);
        platforms = new Array<Platform>();
    }

    public void Platform_Initializer(Array<Platform> platform){
        this.platforms = platform;
    }

    public void initail_platform_assigner(Vector2 platform){
        position.x = platform.x;
        position.y = platform.y + 18;
        //platform.hasLandedOnPlatform = true;
        jumpState = JumpState.FALLING;
        initpos.x = position.x;
        initpos.y = position.y;
    }

    public void PowerUpsInitializer(Array <PowerUp> powerUps){
        this.powerUps = powerUps;
    }

    public void render(SpriteBatch batch){
        if(region == null)
            return;

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

    public void update(float delta, SpriteBatch batch){
        AllPlatformManager();
        region = spriteSelector();

        if(TimeUtils.nanoTime() / 1e+9 - TimeSinceEnemyHit > Constants.ENEMY_HIT_FREEZE_TIME){
            hitByEnemy = false;
        }

        if(jumpState != JumpState.RECOILING) {

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || OnScreenControls.isRightTouched()) {
                timesincelastclick = TimeUtils.nanoTime();
                timesincelastclick /= 1e+9;

                if (velocity.x < 0)
                    velocity.x += 2 * delta * Constants.HORIZONTAL_SPEED_MULTIPLIER;
                else
                    velocity.x += delta * Constants.HORIZONTAL_SPEED_MULTIPLIER;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || OnScreenControls.isLeftTouched()) {
                if (position.x == 1)
                    return;

                timesincelastclick = TimeUtils.nanoTime();
                timesincelastclick /= 1e+9;
                if (velocity.x > 0)
                    velocity.x -= 2 * delta * Constants.HORIZONTAL_SPEED_MULTIPLIER;
                else
                    velocity.x -= delta * Constants.HORIZONTAL_SPEED_MULTIPLIER;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP) || OnScreenControls.isJumpTouched()) {
                if (jumpState == JumpState.GROUNDED) {
                    has_jumped = true;
                    jumpState = JumpState.JUMPING;
                    jump_start = (float) (System.nanoTime() / 1e+9);
                }
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.Z) || OnScreenControls.isShootTouched()){
                if(ammo > 0){
                    ammo--;
                    Bullet bullet;
                    if(facing == FACING.RIGHT){
                        bullet = new Bullet(position.x + region.getRegionWidth() - Constants.LENGTH_CORRECTION + 1, position.y + region.getRegionHeight() / 2- Constants.HEIGHT_CORRECTION, false);
                        bullet.velocity = Constants.BULLET_VELOCITY;
                    }
                    else{
                        bullet = new Bullet(position.x + Constants.LENGTH_CORRECTION - 5, position.y + region.getRegionHeight() / 2 - Constants.HEIGHT_CORRECTION, true);
                        bullet.velocity = -Constants.BULLET_VELOCITY;
                    }

                    bullets.add(bullet);//Gdx.app.log("ElseBulletCount", " " + currentPowerUp.bulletCount);
                }
            }
        }

        //render and remove bullets
        BulletManager(batch);
        InBounds();
        upwardMovement(delta);

        //TODO: If necessary implement this if statement
        //if(hitByEnemy)
        float currentTime = TimeUtils.nanoTime();
        currentTime /= 1e+9;
        float timeElasped = currentTime - timesincelastclick;

        if(jumpState != JumpState.RECOILING) {
            if (timeElasped >= Constants.MAX_TIME_SINCE_CLICK) {
                if (velocity.x > 2)
                    velocity.x -= 1.5f * delta * Constants.HORIZONTAL_SPEED_MULTIPLIER;

                else if (velocity.x < -2)
                    velocity.x += 1.5f * delta * Constants.HORIZONTAL_SPEED_MULTIPLIER;

                else
                    velocity.x = 0;
            }
        }

        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        RespondToEnemies();
        PowerUpd();
        BulletEnemyCollisionManager(batch);
        ExplosionCauser(batch);

    }

    public void InBounds(){

        if(position.y < -100){
            position.x = initpos.x;
            position.y = initpos.y + 5;
            Gdx.app.log("position " + initpos.x, "" + initpos.y);
            velocity.y = 0;
            velocity.x = 0;

            if(GigaGalHud.lives > 0)
                GigaGalHud.lives--;
        }
    }

    float jump_duration;
    public void upwardMovement(float delta){
        //TODO: Add functionality of recoiling state if needed
        if(jumpState == JumpState.GROUNDED)
            return ;

        if(jumpState == JumpState.RECOILING)
            Gdx.app.log("performing", "" + jumpState);

        jump_duration = (float)(System.nanoTime() / 1e+9) - jump_start;
        Gdx.app.log("TimeDur: ", "" + jump_duration);

        if(jump_duration < Constants.JUMP_DURATION && jumpState == JumpState.RECOILING){
            velocity.y = Constants.VERTICAL_SPEED_MULTIPLIER;return;
        }

        if(jump_duration >= Constants.JUMP_DURATION && jumpState == JumpState.RECOILING){
            velocity.y = -Constants.VERTICAL_SPEED_MULTIPLIER * 1.25f;return;
        }

        if(jump_duration < Constants.JUMP_DURATION){
            jumpState = JumpState.JUMPING;Gdx.app.log("Jump", "" + jumpState);}

        if(jump_duration >= Constants.JUMP_DURATION) {
            if(!top_reached){
                velocity.y = 0;
                top_reached = true;
            }
            jumpState = JumpState.FALLING;Gdx.app.log("Fall", "" + jumpState);
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
        //float gigaGalHeight = Assets.instance.gigaGalAssets.standingright.getRegionHeight();

        if(withinPlatformWidth(platform, gigaGalwidth) && isfallingOnPlatform(top) && velocity.y < -10){
            velocity.y = 0;
            if(jumpState == JumpState.RECOILING)
                velocity.x = 0;

            if(position.y > top - 1)
                position.y = top - 1;

            jumpState = JumpState.GROUNDED;
            platform.hasLandedOnPlatform = true;
            Gdx.app.log("HeightP" + position.y, "WidthP" + position.x);
            has_jumped = false;
            top_reached = false;
            jump_start = 0;
        }

        if(jumpState == JumpState.RECOILING && !isfallingOnPlatform(top)) {
            platform.hasLandedOnPlatform = false;return;
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

    public boolean isfallingOnPlatform(float top){
        if(position.y + 2 >= top && position.y < top)
            return true;
        return false;
    }

    //boolean isprevleft = false;
    boolean hasanimationstarted = false;

    public TextureRegion spriteSelector(){
        TextureRegion selected = null;
        if(jumpState != JumpState.RECOILING) {
            if (velocity.x > 0)
                facing = FACING.RIGHT;
            if (velocity.x < 0)
                facing = FACING.LEFT;
        }

        if(jumpState == JumpState.RECOILING && facing == FACING.LEFT)
        {selected = Assets.instance.gigaGalAssets.gigaGalSprites.get(3);return selected;}
        if(jumpState == JumpState.RECOILING && facing == FACING.RIGHT){
            selected = Assets.instance.gigaGalAssets.gigaGalSprites.get(1);return selected;
        }
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

    //Determines what to do if GigaGal collides with Enemy
    public void EnemyCollisionResponse(Enemy enemy){
        //TODO:In case of multiple collisions with same enemy, uncomment
        if(hitByEnemy)
            return;

        if(CollisionDetect(enemy)) {
            if(Level.score >= Constants.ENEMY_HIT_SCORE)
                Level.score -= Constants.ENEMY_HIT_SCORE;

            hitByEnemy = true;
            TimeSinceEnemyHit = TimeUtils.nanoTime() / (float)1e+9;
           // Gdx.app.log("Respond", "");
            velocity.y = Constants.RECOIL_VERTICAL_VELOCITY;
            position.y += 5f;
            jumpState = JumpState.RECOILING;
            jump_start = TimeUtils.nanoTime() / (float) 1e+9;

            if(position.x > enemy.enemy_pos.x - enemy.enemysprite.getRegionWidth() / 2)
                velocity.x = Constants.RECOIL_VELOCITY;

            else
                velocity.x = -Constants.RECOIL_VELOCITY;
        }
    }

    public void GigaGalRectInit(){
         GigaGalRect = new Rectangle(position.x,
                position.y,
                region.getRegionWidth() - Constants.LENGTH_CORRECTION,
                region.getRegionHeight() - Constants.HEIGHT_CORRECTION);
    }

    public void EnemyRectInit(Enemy enemy){
        EnemyRect = new Rectangle(enemy.enemy_pos.x - enemy.enemysprite.getRegionWidth() / 2,
                enemy.enemy_pos.y,
                enemy.enemysprite.getRegionWidth(),
                enemy.enemysprite.getRegionHeight());
    }

    public void BuuletRectInit(Bullet bullet){
        BulletRect = new Rectangle(bullet.position.x,
                bullet.position.y,
                bullet.region.getRegionWidth(),
                bullet.region.getRegionHeight());
    }

    //Detects Collision with enemy
    public boolean CollisionDetect(Enemy enemy){
        GigaGalRectInit();
        EnemyRectInit(enemy);
        return GigaGalRect.overlaps(EnemyRect);
    }

    public void PowerUpd(){
        for(int i = 0; i < powerUps.size; i++){
            PowerUp powerUp = powerUps.get(i);
            if(GigaGalPowerUps(powerUp)){
                Level.score += Constants.POWERUP_BONUS;
                ammo += powerUp.bulletCount;
                powerUps.removeIndex(i);
            }
        }
    }

    public boolean GigaGalPowerUps(PowerUp powerUp){
        Rectangle powerupRect = new Rectangle(powerUp.position.x,
                powerUp.position.y,
                powerUp.region.getRegionWidth(),
                powerUp.region.getRegionHeight());
        GigaGalRectInit();

        if(GigaGalRect.overlaps(powerupRect))
            return true;
        return false;
    }

    public void BulletEnemyCollisionManager(SpriteBatch batch){
        for(int j = 0; j < bullets.size; j++){
            Bullet bullet = bullets.get(j);
            for(int i = 0; i < enemies.size; i++){
                Enemy enemy = enemies.get(i);boolean enemyDead = false;
                if(bulletEnemyCollision(bullet, enemy)) {
                    enemy.enemyHealth--;
                    if(enemy.enemyHealth == 0){
                        enemyDead = true;
                        Level.score += Constants.ENEMY_KILL_SCORE;
                    }
                    Explosion explosion = new Explosion(bullet.position.x, bullet.position.y, enemyDead);
                    if(enemyDead){
                        explosion.left += enemy.enemy_pos.x - bullet.position.x;
                        enemies.removeIndex(i);
                    }
                    explosions.begin();
                    explosions.add(explosion);
                    explosions.end();
                    bullets.removeIndex(j);
                }
            }
        }
    }

    public boolean bulletEnemyCollision(Bullet bullet, Enemy enemy){
        BuuletRectInit(bullet);
        EnemyRectInit(enemy);
        if(BulletRect.overlaps(EnemyRect))
            return true;
        return false;
    }

    public void BulletManager(SpriteBatch batch){
        for(int i = 0; i < bullets.size; i++){
            bullets.get(i).render(batch);
            if(bullets.get(i).position.x > position.x + Constants.WORLD_SIZE)
                bullets.removeIndex(i);
        }
    }

    public void ExplosionCauser(SpriteBatch batch){
        explosions.begin();
        for(int i = 0; i < explosions.size; i++){
            explosions.get(i).render(batch);
            if(!explosions.get(i).setVisible)
                explosions.removeIndex(i);
        }
        explosions.end();
    }
}

enum JumpState{
        JUMPING,
        FALLING,
        GROUNDED,
        RECOILING;
        }

enum FACING{
    LEFT,
    RIGHT;
}