/*package com.yasinguzel.ccar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PlayerCarControl extends InputAdapter {
    Texture playerCarTexture;
   private Vector2 playerPosition;
   public float screenWidth;
   public float screenHeight;


   public PlayerCarControl(float screenWidth,float screenHeight){
       this.screenWidth = screenWidth;
       this.screenHeight = screenHeight;
       playerPosition = new Vector2(screenWidth / 2.3f, screenHeight / 17f);
       playerCarTexture = new Texture("playercar.png");

   }

    public void render (SpriteBatch batch) {
        batch.draw(playerCarTexture,playerPosition.x,playerPosition.y);
    }

    public Vector2 getPlayerPosition(){
        return playerPosition;
    }

    public void setPlayerPosition (float x , float y){
        playerPosition.set(x,y);
    }

   public void changeLine(){
       float[] lanePositions = {
               screenWidth / 80f,
               screenWidth / 4.5f,
               screenWidth / 2.3f,
               screenWidth / 1.55f,
               screenWidth / 1.18f
       };

       if (Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() / 2 && playerPosition.x>1.18f) {
           // User touched the right side of the screen.
           if(playerPosition.x == 80f){
               playerPosition.x = 4.5f;
           }else if(playerPosition.x == 4.5f){
               playerPosition.x = 2.3f;
           }else if(playerPosition.x == 2.3f){
               playerPosition.x = 1.55f;
           }else{
               playerPosition.x = 1.18f;
           }
       }
       if (Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 2 && playerPosition.x < 80f) {
           // User touched the left side of the screen.
           if(playerPosition.x == 1.18f){
               playerPosition.x = 1.55f;
           }else if(playerPosition.x == 1.55f){
               playerPosition.x = 2.3f;
           }else if(playerPosition.x == 2.3f){
               playerPosition.x = 4.5f;
           }else{
               playerPosition.x = 80f;
           }
       }
   }

}*/

package com.yasinguzel.ccar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class PlayerCarControl extends InputAdapter {
    private Texture playerCarTexture;
    private Vector2 playerPosition;
    private float screenWidth;
    private float screenHeight;
    private long changeLineTime; // Şerit değiştirme zamanı
    private final long CHANGE_LINE_DELAY = 200; // Şerit değiştirme gecikmesi (0.2 saniye)




    public PlayerCarControl(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        playerPosition = new Vector2(screenWidth / 2.3f, screenHeight / 17f);
        playerCarTexture = new Texture("playercar.png");
        changeLineTime = 0;
    }

    public void render(SpriteBatch batch) {
        batch.draw(playerCarTexture, playerPosition.x, playerPosition.y);
    }

    public Vector2 getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(float x, float y) {
        playerPosition.set(x, y);
    }
    /*public void changeLine() {
        float[] lanePositions = {
                screenWidth / 80f,
                screenWidth / 4.5f,
                screenWidth / 2.3f,
                screenWidth / 1.55f,
                screenWidth / 1.18f
        };

        if (Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() / 2 && playerPosition.x < lanePositions[4]) {
            // Sağ tarafa tıklanırsa ve araba sağda değilse
            for (int i = 0; i < lanePositions.length; i++) {
                if (playerPosition.x == lanePositions[i]) {
                    playerPosition.x = lanePositions[i + 1];
                    break;
                }
            }
        }

        if (Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 2 && playerPosition.x > lanePositions[0]) {
            // Sol tarafa tıklanırsa ve araba solda değilse
            for (int i = lanePositions.length - 1; i >= 0; i--) {
                if (playerPosition.x == lanePositions[i]) {
                    playerPosition.x = lanePositions[i - 1];
                    break;
                }
            }
        }
    }*/

    public void changeLine() {
        float[] lanePositions = {
                screenWidth / 80f,
                screenWidth / 4.5f,
                screenWidth / 2.3f,
                screenWidth / 1.55f,
                screenWidth / 1.18f
        };

        if (Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
            // Sağ tarafa tıklanırsa ve araba sağda değilse
            for (int i = 0; i < lanePositions.length; i++) {
                if (playerPosition.x == lanePositions[i]) {
                    if (i + 1 < lanePositions.length && TimeUtils.timeSinceMillis(changeLineTime) > CHANGE_LINE_DELAY) {
                        playerPosition.x = lanePositions[i + 1];
                        changeLineTime = TimeUtils.millis(); // Zamanı güncelle
                        break;
                    }
                }
            }
        }

        if (Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
            // Sol tarafa tıklanırsa ve araba solda değilse
            for (int i = lanePositions.length - 1; i >= 0; i--) {
                if (playerPosition.x == lanePositions[i]) {
                    if (i - 1 >= 0 && TimeUtils.timeSinceMillis(changeLineTime) > CHANGE_LINE_DELAY) {
                        playerPosition.x = lanePositions[i - 1];
                        changeLineTime = TimeUtils.millis(); // Zamanı güncelle
                        break;
                    }
                }
            }
        }
    }

    public boolean checkCollision(Car car) {
        float playerCarLeft = playerPosition.x;
        float playerCarRight = playerPosition.x + playerCarTexture.getWidth();
        float playerCarTop = playerPosition.y + playerCarTexture.getHeight();
        float playerCarBottom = playerPosition.y;

        float carLeft = car.getPosition().x;
        float carRight = car.getPosition().x + car.getTexture().getWidth();
        float carTop = car.getPosition().y + car.getTexture().getHeight();
        float carBottom = car.getPosition().y;

        // Çarpışma durumunu kontrol et
        if (playerCarRight >= carLeft && playerCarLeft <= carRight && playerCarTop >= carBottom && playerCarBottom <= carTop) {
            return true; // Çarpışma var
        } else {
            return false; // Çarpışma yok
        }
    }


    private Rectangle getPlayerCollisionRectangle() {
        return new Rectangle(playerPosition.x, playerPosition.y, playerCarTexture.getWidth(), playerCarTexture.getHeight());
    }


}

