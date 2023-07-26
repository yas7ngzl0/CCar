
package com.yasinguzel.ccar;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;

public class CCar extends ApplicationAdapter {
	SpriteBatch batch;
	Texture highway;
	Texture playercar;
	BitmapFont font;
	BitmapFont gameOverFont;

	BitmapFont scoreFont;
	BitmapFont highScoreFont;
	FreeTypeFontGenerator scoreGen;
	FreeTypeFontGenerator overGen;
	FreeTypeFontGenerator highGen;


	private CarManager carManager;

	float screenWidth;
	float screenHeight;
	float deltaTime;
	int score;
	int highScore = 0;
	float increaseScore;
	int gameState;

	OrthographicCamera camera;
	private PlayerCarControl playerCarControl;
	private boolean canReset = true;




//uygulama kimliği : ca-app-pub-9582732495383626~8235623486
	//reklam kimligi : ca-app-pub-9582732495383626/8044051790
	//deneme için reklam kimliği : ca-app-pub-3940256099942544/6300978111

	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		gameState = 0;
		highway = new Texture("highway.png");
		playercar = new Texture("playercar.png");
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		deltaTime = Gdx.graphics.getDeltaTime();

		carManager = new CarManager(screenWidth, screenHeight);
		playerCarControl = new PlayerCarControl(screenWidth, screenHeight);
		score =0;
		increaseScore = 0;
		font = new BitmapFont();
		font.setColor(Color.GOLD);
		font.getData().setScale(4);

		overGen = new FreeTypeFontGenerator(Gdx.files.internal("overgen.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params.color = Color.MAGENTA;
		params.size = 100;
		params.characters = "GAMEOVR";
		gameOverFont = overGen.generateFont(params);

		scoreGen = new FreeTypeFontGenerator(Gdx.files.internal("scoregen.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params2.color = Color.WHITE;
		params2.size = 70;
		params2.characters = "YOURSCE : 0123456789";
		scoreFont = scoreGen.generateFont(params2);

		highGen = new FreeTypeFontGenerator(Gdx.files.internal("scoregen.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params3.color = Color.GOLD;
		params3.size = 65;
		params3.characters = "YOURSCEHIG : 0123456789";
		highScoreFont = highGen.generateFont(params3);



		Preferences preferences = Gdx.app.getPreferences("CCarPreferences");
		highScore = preferences.getInteger("highScore",0);

	}

	private void resetGame() {


			gameState = 0;
			score = 0;
			increaseScore = 0f;
			playerCarControl.setPlayerPosition(screenWidth / 2.3f, screenHeight / 17f);
			carManager.resetCars();

	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(highway, 0, 0);


		if(gameState == 0){
			batch.draw(playercar, screenWidth / 2.3f, screenHeight / 17);
		}

		if (Gdx.input.justTouched()) {
			gameState = 1;
		}
		if (gameState == 1) {
			carManager.update(deltaTime);
			for (Car car : carManager.getCars()) {
				if (playerCarControl.checkCollision(car)) {
					// Oyuncu arabası ve bir rakip araba arasında çarpışma tespit edildi
					 // Oyun durumu "Oyun Bitti" olarak güncelle
					if(score > highScore){
						highScore = score;
						Preferences preferences = Gdx.app.getPreferences("CCarPreferences");
						preferences.putInteger("highScore", highScore);
						preferences.flush();
					}
					gameState = 2;
					break;
				}
             increaseScore = increaseScore + 0.015f;
				score = (int) (score + increaseScore) /2;

			}
			carManager.render(batch);
			playerCarControl.render(batch);
			playerCarControl.changeLine();
		}

		if(gameState == 2){
			gameOverFont.draw(batch,"GAME OVER",260,1500);
			scoreFont.draw(batch,"YOUR SCORE : " +score,150,1000);
			highScoreFont.draw(batch,"HIGH SCORE : " + highScore,150,500);


				if (Gdx.input.justTouched() && canReset) {
					// Oyunu sıfırlama işlemleri burada yapılır.
					//resetGame();
					canReset = false; // Tekrar tıklamayı engellemek için flag'i false yap.
					// 2 saniye sonra tekrar tıklamayı kabul etmek için bekleme işlemi başlat.
					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							canReset = true;
							resetGame();
						}
					}, 0.15f); // 2 saniye (2f) sonra canReset flag'ini tekrar true yaparak tıklamayı kabul eder hale gelir.
				}




		}



        font.draw(batch,String.valueOf(score),100,1700);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		camera.update();
	}

	@Override
	public void dispose() {
		batch.dispose();
		highway.dispose();
		playercar.dispose();
		carManager.dispose();
		overGen.dispose();
		gameOverFont.dispose();
		scoreFont.dispose();
		scoreGen.dispose();
		highScoreFont.dispose();
		highGen.dispose();
	}
}


