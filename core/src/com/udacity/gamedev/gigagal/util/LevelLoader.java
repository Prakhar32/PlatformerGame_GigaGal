package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.gigagal.Level;
import com.udacity.gamedev.gigagal.entities.Enemy;
import com.udacity.gamedev.gigagal.entities.ExitPortal;
import com.udacity.gamedev.gigagal.entities.Platform;
import com.udacity.gamedev.gigagal.entities.PowerUp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;

/**
 * Created by hp on 26-Sep-17.
 */

public class LevelLoader {
    public static String TAG = LevelLoader.class.toString();
    public static boolean hasExceptionOccured = false;

    public static Level load(String LevelName){
        Level level = new Level();

        String path = Constants.LEVEL_DIR + File.separator + LevelName + "." + Constants.LEVEL_FILE_EXTENSION;

        try{
            FileHandle handle = Gdx.files.internal(path);
            JSONParser parser = new JSONParser();
            JSONObject rootJsonObject = (JSONObject) parser.parse(handle.reader());
            Gdx.app.log(TAG, rootJsonObject.keySet().toString());

            JSONObject composite = (JSONObject) rootJsonObject.get(Constants.LEVEL_COMPOSITE);
            Gdx.app.log(TAG, rootJsonObject.keySet().toString());

            JSONArray platforms = (JSONArray)composite.get(Constants.LEVEL_9PATCHES);
            JSONObject firstPlatform = (JSONObject) platforms.get(0);
            loadPlatforms(platforms, level);
            Gdx.app.log(TAG, firstPlatform.keySet().toString());

            JSONArray nonPlatformObjects = (JSONArray) composite.get(Constants.LEVEL_IMAGES);
            loadNonPlatformEntities(level, nonPlatformObjects);
        }

        catch (Exception e){
            Gdx.app.log(TAG, e.getMessage());
            Gdx.app.log(TAG, Constants.LEVEL_ERROR_MESSAGE);
            hasExceptionOccured = true;
        }

        return level;
    }

    private static float safeGetFloat(JSONObject object, String key){
        Number number = (Number) object.get(key);
        return (number == null) ? 0 : number.floatValue();
    }

    public static void loadPlatforms(JSONArray array, Level level){
        Array<Platform> platformArray = new Array<Platform>();

        for(Object object : array){
            final JSONObject platformObject = (JSONObject) object;
            final float x = safeGetFloat(platformObject, Constants.LEVEL_X_KEY);
            final float y = safeGetFloat(platformObject, Constants.LEVEL_Y_KEY);
            final float width = ((Number)platformObject.get(Constants.LEVEL_WIDTH_KEY)).floatValue();
            final float height = ((Number)platformObject.get(Constants.LEVEL_HEIGHT_KEY)).floatValue();
            Gdx.app.log(TAG, "Loaded platform at x" + x + " y " + y + " width " + width + " height " + height);
            final Platform platform = new Platform(x, y, width, height);
            platformArray.add(platform);

            final String Identifier = (String) platformObject.get(Constants.LEVEL_IDENTIFIER_KEY);
            if(Identifier != null && Identifier.equals(Constants.LEVEL_ENEMY_TAG)){
                Gdx.app.log(TAG, "Enemy located");
                final Enemy enemy = new Enemy(platform);
                level.getEnemies().add(enemy);
            }
        }

        level.getPlatforms().addAll(platformArray);
    }

    public static void loadNonPlatformEntities(Level level, JSONArray nonPlatformObject){
        for(Object o : nonPlatformObject){
            JSONObject item = (JSONObject) o;
            Vector2 lowerleftCorner = new Vector2();
            final float x = safeGetFloat(item, Constants.LEVEL_X_KEY);
            final float y = safeGetFloat(item, Constants.LEVEL_Y_KEY);
            lowerleftCorner = new Vector2(x, y);

            if(item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.STANDING_RIGHT)){
                Gdx.app.log(TAG, "loaded gigagal at position" + lowerleftCorner);
                level.setGigaGalposition(lowerleftCorner);
                level.gigaPlat(lowerleftCorner);
            }

            else if(item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.EXIT_PORTAL_NAME_1)){
                level.setExitPortal(new ExitPortal(lowerleftCorner));
                Gdx.app.log(TAG, "Exit here" + lowerleftCorner);
            }

            else if(item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.POWERUP)){
                level.getPowerUpes().add(new PowerUp(lowerleftCorner));
            }
        }
        level.otherEntitiesInitializer();
    }
}