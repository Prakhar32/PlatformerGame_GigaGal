package com.udacity.gamedev.gigagal.Overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

import javax.swing.text.View;

/**
 * Created by hp on 30-Oct-17.
 */

public class OnScreenControls extends InputAdapter{
    public static Vector2 leftKeyPosition;
    public static Vector2 rightKeyPosition;
    public static Vector2 jumpKeyPosition;
    public static Vector2 shootKeyPosition;
    static TextureRegion left;
    static TextureRegion right;
    static TextureRegion jump;
    static TextureRegion shoot;
    static Vector2 worldSize;
    static Viewport viewport;

    public OnScreenControls(Viewport viewport){
        leftKeyPosition = new Vector2();
        rightKeyPosition = new Vector2();
        jumpKeyPosition = new Vector2();
        shootKeyPosition = new Vector2();
        worldSize = new Vector2();
        left = Assets.instance.controlAssets.move_left_button;
        right = Assets.instance.controlAssets.move_right_button;
        jump = Assets.instance.controlAssets.jump_button;
        shoot = Assets.instance.controlAssets.shoot_button;
        this.viewport = viewport;
    }

    public void update(Vector2 gigaGalPosition){
        leftKeyPosition.x = gigaGalPosition.x - Constants.BUTTON_POSITION1_X - 10;
        leftKeyPosition.y = gigaGalPosition.y - Constants.BUTTON_POSITION1_Y;
        rightKeyPosition.x = gigaGalPosition.x - Constants.BUTTON_POSITION2_X - 10;
        rightKeyPosition.y = gigaGalPosition.y - Constants.BUTTON_POSITION2_Y;
        jumpKeyPosition.x = gigaGalPosition.x + Constants.BUTTON_POSITION1_X;
        jumpKeyPosition.y = gigaGalPosition.y - Constants.BUTTON_POSITION1_Y;
        shootKeyPosition.x = gigaGalPosition.x + Constants.BUTTON_POSITION2_X;
        shootKeyPosition.y = gigaGalPosition.y - Constants.BUTTON_POSITION2_Y;

        worldSize.x = gigaGalPosition.x - Constants.WORLD_SIZE / 2.5f;
        worldSize.y = gigaGalPosition.y + Constants.WORLD_SIZE / 10;//+ Constants.WORLD_SIZE / 2.5f;
    }

    public void render(SpriteBatch batch){
        rendering(batch, left, leftKeyPosition);
        rendering(batch, right, rightKeyPosition);
        rendering(batch, jump, jumpKeyPosition);
        rendering(batch, shoot, shootKeyPosition);
    }

    void rendering(SpriteBatch batch, TextureRegion region, Vector2 position){
        batch.draw(region.getTexture(),
                position.x,
                position.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                0.5f,
                0.5f,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    public static boolean isLeftTouched(){
        return isButtonTouched(left, leftKeyPosition, 1);
    }

    public static boolean isRightTouched(){
        return isButtonTouched(right, rightKeyPosition, 1);
    }

    public static boolean isJumpTouched(){
        return isButtonTouched(jump, jumpKeyPosition, 1);
    }

    public static boolean isShootTouched(){
        return isButtonTouched(shoot, shootKeyPosition, 2);
    }

    static boolean isButtonTouched(TextureRegion region, Vector2 position, int keyCode){
        Vector3 newPosition = viewport.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0), viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
        boolean alongLength = false;
        boolean alongWidth = false;
        boolean Touched = false;
        switch(keyCode){
            case 1:
                if(Gdx.input.isTouched())
                    Touched = true;
                break;

            case 2:
                if(Gdx.input.justTouched())
                    Touched = true;
                break;

            default:
                Gdx.app.log("Error", "Dont know what went wrong");
        }

        if(Touched){
            if(newPosition.x > position.x && newPosition.x < position.x + region.getRegionWidth() / 2)
                alongLength = true;
            if(newPosition.y > position.y  && newPosition.y < position.y + region.getRegionHeight() / 2)
                alongWidth = true;
        }

        /*ShapeRenderer renderer = new ShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.rect(Gdx.input.getX(),Gdx.graphics.getHeight() -Gdx.input.getY(), region.getRegionWidth() / 2, region.getRegionHeight() / 2);
        renderer.end();*/
        if(alongLength && alongWidth)
            return true;
        return false;
    }

    //static boolean alongLength()
}
