package com.yasinguzel.ccar;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Car {
    String Cars[] = {"car1.png","car2.png","car3.png","car4.png","car5.png","car6.png"};
    private Texture texture;
    private Vector2 position;
    private float speed;
    private int lane;
    Random random = new Random();


    public Car(Texture texture, float x, float y, float speed,int lane) {
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.speed = speed;
        this.lane = lane;
    }

    public void update(float deltaTime) {
        position.y -= speed/15;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }


    public Texture getTexture() {
        return texture;
    }

    public int getLane() {
        return lane;
    }

    public Rectangle getCollisionRectangle() {
        return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

}

