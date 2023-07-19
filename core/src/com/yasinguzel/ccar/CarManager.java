package com.yasinguzel.ccar;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.yasinguzel.ccar.Car;

import java.util.ArrayList;
import java.util.Random;

public class CarManager {
    private ArrayList<Car> cars;
    private boolean[] lanes;
    private Random random;
    private Texture[] carTextures;
    private final int MAX_CARS = 4;
    private float screenWidth;
    private float screenHeight;
    private float speedIncrease;

    public CarManager(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        cars = new ArrayList<>();
        lanes = new boolean[5];
        random = new Random();
        speedIncrease = 5f;
        carTextures = new Texture[6];
        for (int i = 0; i < 6; i++) {
            carTextures[i] = new Texture("car" + (i + 1) + ".png");
        }
    }

    public void update(float deltaTime) {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            car.update(deltaTime);
            if (car.getPosition().y < -car.getTexture().getHeight()) {
                cars.remove(car);
                lanes[car.getLane()] = false;
            }
        }
        spawnNewCars();
    }

    private void spawnNewCars() {
        for (int i = 0; i < lanes.length; i++) {
            if (!lanes[i] && cars.size() < MAX_CARS) {
                spawnCar(i);
                break;
            }
        }
    }

    private float getLanePosition(int lane) {
        float[] lanePositions = {
                screenWidth / 80f,
                screenWidth / 4.5f,
                screenWidth / 2.3f,
                screenWidth / 1.55f,
                screenWidth / 1.18f
        };
        float laneWidth = screenWidth / 5f;
        float laneCenter = laneWidth * lane + laneWidth / 2f;
        float carWidth = carTextures[0].getWidth(); // Burada herhangi bir arabaya göre genişlik alıyoruz.
        float lanePosition = laneCenter - carWidth / 2f;
        return lanePosition;
    }

    private void spawnCar(int lane) {
        Texture carTexture = getRandomCarTexture();
        int emptyLane = getEmptyLane();
        if (emptyLane != -1) {
            float y = screenHeight;
            float x = getLanePosition(emptyLane);
            speedIncrease = speedIncrease + 1f;
            float speed = MathUtils.random(100 + speedIncrease, 150 + speedIncrease);
            Car car = new Car(carTexture, x, y, speed, emptyLane);
            cars.add(car);
            lanes[emptyLane] = true;
        }
    }

    private int getEmptyLane() {
        ArrayList<Integer> emptyLanes = new ArrayList<>();
        for (int i = 0; i < lanes.length; i++) {
            if (!lanes[i]) {
                emptyLanes.add(i);
            }
        }
        if (emptyLanes.size() > 0) {
            int randomIndex = random.nextInt(emptyLanes.size());
            return emptyLanes.get(randomIndex);
        } else {
            return -1;
        }
    }

    private Texture getRandomCarTexture() {
        int randomIndex = random.nextInt(carTextures.length);
        return carTextures[randomIndex];
    }

    public void render(SpriteBatch batch) {
        for (Car car : cars) {
            car.render(batch);
        }
    }

    public void dispose() {
        for (Car car : cars) {
            car.dispose();
        }
        for (Texture texture : carTextures) {
            texture.dispose();
        }
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void resetCars() {
        cars.clear();
        for (int i = 0; i < lanes.length; i++) {
            lanes[i] = false;
        }
        speedIncrease = 5f;
    }

}
