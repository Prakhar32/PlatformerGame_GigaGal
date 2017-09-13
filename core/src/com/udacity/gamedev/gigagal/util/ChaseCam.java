package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.gigagal.GamePlayScreen;
import com.udacity.gamedev.gigagal.entities.GigaGal;


/**
 * Created by hp on 02-Sep-17.
 */

public class ChaseCam extends InputAdapter{
    private Camera camera;
    private GigaGal target;
    private Vector2 camMove;
    public boolean camera_follow_Gigagal;

    public ChaseCam(Camera camerav, GigaGal gigaGal){
        this.camera = camerav;
        target = gigaGal;
        camera_follow_Gigagal = true;
    }

    public void update(){
        cameraToggleMode();

        if(camera_follow_Gigagal){
        camera.position.x = target.position.x + Constants.LENGTH_CORRECTION;
        camera.position.y = target.position.y + Constants.HEIGHT_CORRECTION;
        }

        else
            cameraMovesAlone();
    }

    public void cameraMovesAlone(){
        float delta = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.W))
            camera.position.y += delta * Constants.COMERA_MOVEMENT_SPEED;

        if(Gdx.input.isKeyPressed(Input.Keys.S))
            camera.position.y += -delta * Constants.COMERA_MOVEMENT_SPEED;

        if(Gdx.input.isKeyPressed(Input.Keys.A))
            camera.position.x += -delta * Constants.COMERA_MOVEMENT_SPEED;

        if(Gdx.input.isKeyPressed(Input.Keys.D))
            camera.position.x += delta * Constants.COMERA_MOVEMENT_SPEED;
    }

    public void cameraToggleMode(){
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
            camera_follow_Gigagal = !camera_follow_Gigagal;

        if(camera_follow_Gigagal){
            camera.position.x = target.position.x + Constants.LENGTH_CORRECTION;
            camera.position.y = target.position.y + Constants.HEIGHT_CORRECTION;
        }
    }
}
