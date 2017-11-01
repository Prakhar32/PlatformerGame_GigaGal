package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by hp on 23-Aug-17.
 */

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    public GigaGalAssets gigaGalAssets;
    private AssetManager assetManager;
    public PlatformAssets platformAssets;
    public EnemyAssets enemyAssets;
    public ExplosionAssets explosionAssets;
    public PowerUpAssets powerUpAssets;
    public BulletAssets bulletAssets;
    public ExitPortalAssets exitPortalAssets;
    public ControlAssets controlAssets;

    private Assets(){

    }

    public void init(){
        assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLUS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLUS);
        gigaGalAssets = new GigaGalAssets(atlas);
        platformAssets = new PlatformAssets(atlas);
        enemyAssets = new EnemyAssets(atlas);
        explosionAssets = new ExplosionAssets(atlas);
        powerUpAssets = new PowerUpAssets(atlas);
        bulletAssets = new BulletAssets(atlas);
        exitPortalAssets = new ExitPortalAssets(atlas);
        controlAssets = new ControlAssets(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}

