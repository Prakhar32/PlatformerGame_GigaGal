package com.udacity.gamedev.gigagal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.gigagal.Overlays.GigaGalHud;
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

    public void show(){
        Assets.instance.init();
        batch = new SpriteBatch();
        //levels = new DelayedRemovalArray<Level>();
        //LevelLoad();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        HUDviewport = new ExtendViewport(Constants.HUD_SIZE, Constants.HUD_SIZE);
        level = LevelLoader.load("Level1");
        renderer = new ShapeRenderer();
        chaseCam = new ChaseCam(viewport.getCamera(), level.gigaGal);
        //HUDCamera = new ChaseCam(HUDviewport.getCamera(), level.getGigaGal());

        gigaGalHud = new GigaGalHud(level);
        font = new BitmapFont();
        currentlevel = 1;
        VictoryAchieved = false;
        GameOver = false;
        End = false;
    }

    public void resize(int width, int height){
        viewport.update(width, height, true);
        HUDviewport.update(width, height, true);
    }

    public void dispose(){
        Assets.instance.dispose();
        batch.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta){
        if(!End){
        Gdx.gl.glClearColor(Constants.BACKGROUND.r, Constants.BACKGROUND.g, Constants.BACKGROUND.b, Constants.BACKGROUND.a);}
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        chaseCam.update();
        //HUDCamera.update();
        viewport.apply();
        //Gdx.app.log("Camerax" + viewport.getCamera().position.x, "Cameray" + viewport.getCamera().position.y);
        HUDviewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        TextureRegion region = Assets.instance.enemyAssets.emeny_sprite;

        if(End){
            batch.begin();
            VictoryOverlay.render(font, batch, level);
            batch.end();return;
        }

        batch.begin();
        level.render(batch);
        batch.end();

        batch.setProjectionMatrix(HUDviewport.getCamera().combined);
        batch.begin();
        gigaGalHud.render(font, batch);
        batch.end();
        update();
        /*renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.rect(Level.gigaGal.position.x, Level.gigaGal.position.y, Level.gigaGal.region.getRegionWidth() - Constants.LENGTH_CORRECTION, Level.gigaGal.region.getRegionHeight() - Constants.HEIGHT_CORRECTION);
        renderer.rect(Level.enemies.get(0).enemy_pos.x - region.getRegionWidth() / 2, Level.enemies.get(0).enemy_pos.y, region.getRegionWidth(), region.getRegionHeight());
        renderer.end();*/
    }

    /*public void LevelLoad(){
        int i = 1;
        levels.begin();

        while(true){
            levels.add(LevelLoader.load("Level" + i));
            i++;

            if(LevelLoader.hasExceptionOccured){
                levels.end();
                return;
            }
        }
    }*/
    public void update(){
        if(level.LevelEnd())
            LoadNextLevel();
    }

    public void LoadNextLevel(){
        currentlevel++;
        Level level = LevelLoader.load("Level" + currentlevel);

        if(LevelLoader.hasExceptionOccured){
            Gdx.app.log("Error", "level");
            End = true;
        }

        else{
            this.level = level;
            chaseCam = new ChaseCam(viewport.getCamera(), level.getGigaGal());
        }
    }
}
