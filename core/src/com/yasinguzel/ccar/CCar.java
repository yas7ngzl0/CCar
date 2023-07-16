package com.yasinguzel.ccar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class CCar extends ApplicationAdapter {
	SpriteBatch batch;
	Texture highway;
	Texture playercar;
	Texture car1;
	Texture car2;
	Texture car3;
	Texture car4;
	Texture car5;
	Texture car6;
	//float way5 = screenWidth/1.18f,screenHeight/17
	//float way4  = screenWidth/1.55f,screenHeight/17
	//float way3 = screenWidth/2.3f,screenHeight/17
	//float way2 = screenWidth/4.5f,screenHeight/17
	//float way1 = screenWidth/80f,screenHeight/17

	private ArrayList<Car> cars;

	float screenWidth;
	float screenHeight;
    float deltaTime;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		highway = new Texture("highway.png");
		playercar = new Texture("playercar.png");
		car1 = new Texture("car1.png");
		car2 = new Texture("car2.png");
		car3 = new Texture("car3.png");
		car4 = new Texture("car4.png");
		car5 = new Texture("car5.png");
		car6 = new Texture("car6.png");
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		deltaTime = Gdx.graphics.getDeltaTime();
		cars = new ArrayList<>();
		spawnCar();


	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(highway, 0, 0);
		batch.draw(playercar, screenWidth / 2.3f, screenHeight / 17);

		for (int i = 0; i < cars.size(); i++) {
			Car car = cars.get(i);
			car.render(batch);
			car.update(deltaTime);

			if (car.getPosition().y < -car.getTexture().getHeight()) {
				cars.remove(car);
				spawnCar();
			}
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		highway.dispose();
		playercar.dispose();
		for (Car car : cars) {
			car.dispose();
		}
	}

	private Texture getRandomCarTexture() {
		Texture[] carTextures = {car1, car2, car3, car4, car5, car6}; // Araba tekstürlerini bir diziye ekleyin
		int randomIndex = MathUtils.random(carTextures.length - 1); // Rastgele bir indeks seçin
		return carTextures[randomIndex]; // Rastgele arabayı döndürün
	}


	private void spawnCar() {
		Texture carTexture = getRandomCarTexture(); // Rastgele bir araba tekstürü al
		int randomLane = MathUtils.random(1, 5);
		float y = getLanePosition(randomLane); // Seçilen şeridin y koordinatını al
		float x = MathUtils.random(getLaneMinX(randomLane), getLaneMaxX(randomLane)); // Seçilen şeride göre rastgele bir x koordinatı belirle
		float speed = 100f; // Hızı rastgele belirle
		Car car = new Car(carTexture, x, y, speed);
		cars.add(car);
	}

	private float getLanePosition(int lane) {
		float[] lanePositions = {screenHeight, screenHeight, screenHeight, screenHeight, screenHeight}; // Şeritlerin y koordinatlarını buraya ekleyin
		return lanePositions[lane-1]; // Seçilen şeritin y koordinatını döndür
	}

	private float getLaneMinX(int lane) {
		float[] laneMinX = {screenWidth/80f, screenWidth/4.5f, screenWidth/2.3f, screenWidth/1.55f, screenWidth/1.18f}; // Şeritlerin minimum x koordinatlarını buraya ekleyin
		return laneMinX[lane-1]; // Seçilen şeridin minimum x koordinatını döndür
	}

	private float getLaneMaxX(int lane) {
		float[] laneMaxX = {screenWidth/80f, screenWidth/4.5f, screenWidth/2.3f, screenWidth/1.55f, screenWidth/1.18f}; // Şeritlerin maksimum x koordinatlarını buraya ekleyin
		return laneMaxX[lane-1]; // Seçilen şeridin maksimum x koordinatını döndür
	}

}
