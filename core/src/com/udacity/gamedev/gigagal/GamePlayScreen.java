package com.udacity.gamedev.gigagal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.gigagal.Overlays.GameOverOverlay;
import com.udacity.gamedev.gigagal.Overlays.GigaGalHud;
import com.udacity.gamedev.gigagal.Overlays.OnScreenControls;
import com.udacity.gamedev.gigagal.Overlays.VictoryOverlay;
import com.udacity.gamedev.gigagal.entities.GigaGal;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.ChaseCam;
import com.udacity.gamedev.gigagal.util.Constants;
import com.udacity.gamedev.gigagal.util.LevelLoader;

/**
 * Created by hp on 23-Aug-17.
 */

public class GamePlayScreen extends  ScreenAdapter{

    SpriteBatch batch;
    ExtendViewport viewport;
    //GigaGal gigaGal;
    LevelLoader currentlevelLoader;
    LevelLoader nextlevelLoader;
    Level level;
    ShapeRenderer renderer;
    private ChaseCam chaseCam;
    //private ChaseCam HUDCamera;
    Viewport HUDviewport;
    GigaGalHud gigaGalHud;
    BitmapFont font;
    //DelayedRemovalArray <Level> levels;
    int currentlevel;
    boolean VictoryAchieved;
    boolean GameOver;
    boolean End;
    int previousScore;
    VictoryOverlay victoryOverlay;
    GameOverOverlay gameOverOverlay;
    Vector2 finalposition;
    float timer;
    boolean timerStarted;
    OnScreenControls onScreenControls;

    public void show(){
        Assets.instance.init();
        batch = new SpriteBatch();
        //levels = new DelayedRemovalArray<Level>();
        //LevelLoad();
        currentlevelLoader = new LevelLoader();
        nextlevelLoader = new LevelLoader();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        HUDviewport = new ExtendViewport(Constants.HUD_SIZE, Constants.HUD_SIZE);
        level = currentlevelLoader.load("Level1");
        renderer = new ShapeRenderer();
        chaseCam = new ChaseCam(viewport.getCamera(), level.gigaGal);
        //HUDCamera = new ChaseCam(HUDviewport.getCamera(), level.getGigaGal());

        gigaGalHud = new GigaGalHud(level);
        font = new BitmapFont();
        currentlevel = 1;
        VictoryAchieved = false;
        GameOver = false;
        End = false;
        previousScore = 0;
        finalposition = new Vector2();
        victoryOverlay = new VictoryOverlay(0);
        gameOverOverlay = new GameOverOverlay();
        timer = 0;
        timerStarted = false;
        onScreenControls = new OnScreenControls(viewport);
    }

    public void resize(int width, int height){
        viewport.update(width, height, true);
        HUDviewport.update(width, height, true);
    }

    public void dispose(){
        Assets.instance.dispose();
        batch.dispose();
        renderer.dispose();
        font.dispose();
    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(Constants.BACKGROUND.r, Constants.BACKGROUND.g, Constants.BACKGROUND.b, Constants.BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //HUDCamera.update();
        viewport.apply();
        //Gdx.app.log("Camerax" + viewport.getCamera().position.x, "Cameray" + viewport.getCamera().position.y);
        HUDviewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        chaseCam.update();
        TextureRegion region = Assets.instance.controlAssets.move_left_button;

        batch.begin();
        onScreenControls.update(finalposition);
        if(!End)
        {level.update(Gdx.graphics.getDeltaTime(), batch);}
        level.render(batch);
        onScreenControls.render(batch);
        batch.end();

        batch.setProjectionMatrix(HUDviewport.getCamera().combined);
        batch.begin();
        gigaGalHud.render(font, batch);
        batch.end();
        if(!End)
            update();

        if(VictoryAchieved){
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();
            victoryOverlay.update(finalposition);
            victoryOverlay.render(font, batch, previousScore);
            batch.end();
            //return;
        }

        if(GameOver){
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();
            gameOverOverlay.update(finalposition);
            gameOverOverlay.render(batch, font, previousScore + (int)level.score);
            batch.end();
            if(!timerStarted) {
                timer = TimeUtils.nanoTime() / (float) 1e+9;
                timerStarted = true;
            }

            if(TimeUtils.nanoTime() / (float) 1e+9 - timer > Constants.OVERLAY_TIMER){
                GameOver = false;
                End = false;
                GigaGalHud.lives = 3;
                level.score = 0;
                level.getGigaGal().ammo = 0;
                currentlevel--;
                LoadNextLevel();
                font.setColor(Color.WHITE);
                timerStarted = false;
            }
        }

        /*renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        //renderer.rect(Level.gigaGal.position.x, Level.gigaGal.position.y, Level.gigaGal.region.getRegionWidth() - Constants.LENGTH_CORRECTION, Level.gigaGal.region.getRegionHeight() - Constants.HEIGHT_CORRECTION);
        //renderer.rect(Level.enemies.get(0).enemy_pos.x - region.getRegionWidth() / 2, Level.enemies.get(0).enemy_pos.y, region.getRegionWidth(), region.getRegionHeight());
        //renderer.circle(finalposition.x - Constants.BUTTON_POSITION1_X - 3, finalposition.y - Constants.BUTTON_POSITION1_Y + 7, region.getRegionWidth() / 3.5f);
        Gdx.app.log("Mouse", "X: " + Gdx.input.getX() + " Y: " + Gdx.input.getY());
        //Probably takes care of converting Screen to World Coordinate System too
        Vector3 newCoor = viewport.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0), viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
        renderer.rect(newCoor.x , newCoor.y , region.getRegionWidth() / 2, region.getRegionHeight() / 2);
        renderer.end();*/
    }

    public void update(){
        finalposition.x = level.getGigaGal().position.x;
        finalposition.y = level.getGigaGal().position.y;

        if(level.LevelEnd()){
            previousScore += (int)level.score;
            Gdx.app.log("Load", " " + level.getGigaGal().position.y + " , " + finalposition.y);
            LoadNextLevel();
        }

        if(GigaGalHud.lives == 0){
            End = true;
            GameOver = true;
        }
    }

    public void LoadNextLevel(){
        currentlevel++;
        nextlevelLoader.load("Level" + currentlevel);

        if(nextlevelLoader.hasExceptionOccured){
            Gdx.app.log("Error", "level");
            End = true;
            VictoryAchieved = true;
        }

        else{
            //Gdx.app.log("This","should not happen");
            this.level = currentlevelLoader.load("Level" + currentlevel);
            chaseCam = new ChaseCam(viewport.getCamera(), level.getGigaGal());
        }
    }
}
