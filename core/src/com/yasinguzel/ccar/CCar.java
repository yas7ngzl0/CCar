/*package com.yasinguzel.ccar;

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

	private static final int MAX_CARS = 4; // Maksimum araba sayısı
	private final float CAR_DISTANCE = screenHeight / 5f; // Araba aralığı
	private boolean[] lanes = new boolean[5];


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
		//spawnCar(5);


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
				//cars.remove(car);
				removeCarAndLane(car);



			}
		}

		for (int i = 0; i < lanes.length; i++) {
			if (!lanes[i]) {
				spawnCar(i);
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


	private void spawnCar(int lane) {
		Texture carTexture = getRandomCarTexture(); // Rastgele bir araba tekstürü al
		int randomLane = MathUtils.random(1, 5);
		float y = getLanePosition(randomLane); // Seçilen şeridin y koordinatını al
		float x = MathUtils.random(getLaneMinX(randomLane), getLaneMaxX(randomLane)); // Seçilen şeride göre rastgele bir x koordinatı belirle
		float speed = 300f; // Hızı rastgele belirle
		Car car = new Car(carTexture, x, y, speed,lane);
		cars.add(car);
		lanes[lane] = true;
	}



	private void removeCarAndLane(Car car) {
		cars.remove(car);
		lanes[car.getLane()] = false;
	}

	private boolean isOffRoad(Car car) {
		float carWidth = car.getTexture().getWidth();
		float carX = car.getPosition().x;
		return carX < 0 || carX + carWidth > screenWidth;
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

}*/


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

	private ArrayList<Car> cars;
	private boolean[] lanes;

	float screenWidth;
	float screenHeight;
	float deltaTime;
	int score;
	float speedIncrease;
	int gameState;


	private static final int MAX_CARS = 4; // Maksimum araba sayısı
	private final float CAR_DISTANCE = 200f; // Araba aralığı


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
		lanes = new boolean[5];
		speedIncrease = 5f;
		int gameState = 0;


	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(highway, 0, 0);
		batch.draw(playercar, screenWidth / 2.3f, screenHeight / 17);

        if(Gdx.input.justTouched()){
			gameState = 1;
		}
		if(gameState == 1){
			batch.draw(playercar, screenWidth / 2.3f, screenHeight / 17);
			for (int i = 0; i < cars.size(); i++) {
				Car car = cars.get(i);
				car.render(batch);
				car.update(deltaTime);

				if (car.getPosition().y < -car.getTexture().getHeight()) {
					cars.remove(car);
					lanes[car.getLane()] = false;
				}
			}

			for (int i = 0; i < lanes.length; i++) {
				if (!lanes[i] && cars.size() < MAX_CARS) {
					spawnCar(i);
				}
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
		Texture[] carTextures = {car1, car2, car3, car4, car5, car6};
		int randomIndex = MathUtils.random(carTextures.length - 1);
		return carTextures[randomIndex];
	}

	/*private void spawnCar(int lane) {
		Texture carTexture = getRandomCarTexture();
		float y = screenHeight;
		float x = getLanePosition(lane);
		float speed = MathUtils.random(200, 300);
		Car car = new Car(carTexture, x, y, speed, lane);
		cars.add(car);
		lanes[lane] = true;
	}*/

	private void spawnCar(int lane) {
		Texture carTexture = getRandomCarTexture();
		int emptyLane = getEmptyLane(); // Rastgele boş şeriti al

		if (emptyLane != -1) { // Eğer boş şerit varsa
			float y = screenHeight;
			float x = getLanePosition(emptyLane);
			speedIncrease = speedIncrease + 1f;
			float speed = MathUtils.random(100+speedIncrease, 150+speedIncrease);
			Car car = new Car(carTexture, x, y, speed, emptyLane);
			cars.add(car);
			lanes[emptyLane] = true; // Şeriti dolu olarak işaretle
		}
	}

	private int getEmptyLane() {
		ArrayList<Integer> emptyLanes = new ArrayList<>();

		for (int i = 0; i < lanes.length; i++) {
			if (!lanes[i]) { // Boş şeritleri bul
				emptyLanes.add(i);
			}
		}

		if (emptyLanes.size() > 0) {
			int randomIndex = MathUtils.random(emptyLanes.size() - 1);
			return emptyLanes.get(randomIndex);
		} else {
			return -1; // Eğer tüm şeritler doluysa
		}
	}





	private float getLanePosition(int lane) {
		float[] lanePositions = {screenWidth/80f, screenWidth/4.5f, screenWidth/2.3f, screenWidth/1.55f, screenWidth/1.18f};
		float laneWidth = screenWidth / 5f;
		float laneCenter = laneWidth * lane + laneWidth / 2f;
		float carWidth = playercar.getWidth();
		float lanePosition = laneCenter - carWidth / 2f;
		return lanePosition;
	}





}

