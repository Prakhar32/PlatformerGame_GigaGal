package com.udacity.gamedev.gigagal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.udacity.gamedev.gigagal.entities.GigaGal;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.ChaseCam;
import com.udacity.gamedev.gigagal.util.Constants;

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

    public void show(){
        Assets.instance.init();
        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        level = new Level();
        renderer = new ShapeRenderer();
        chaseCam = new ChaseCam(viewport.getCamera(), level.gigaGal);
    }

    public void resize(int width, int height){
        viewport.update(width, height, true);
    }

    public void dispose(){
        Assets.instance.dispose();
        batch.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta){
        chaseCam.update();
        viewport.apply();
        //Gdx.app.log("Camerax" + viewport.getCamera().position.x, "Cameray" + viewport.getCamera().position.y);

        Gdx.gl.glClearColor(Constants.BACKGROUND.r, Constants.BACKGROUND.g, Constants.BACKGROUND.b, Constants.BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        TextureRegion region = Assets.instance.gigaGalAssets.standingright;

        batch.begin();
        level.render(batch);
        batch.end();
       /*renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        //renderer.line();
        renderer.end();*/
    }
}
