package com.celik.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	//game
	SpriteBatch batch;
	Texture background;
	int gameState = 0;
	int gameScore = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;


	//bird
	Texture bird;
	float birdX;
	float birdY;
	float velocityOfBird = 10f;
	float gravity = 0.3f;
	Circle birdCircle;



	//bee
	Texture bee;
	int numberOfBeeSet = 4;
	float widthOfBee;
	float heightOfBee;
	float distanceOfBee;
	float velocityOfBee = 10f;
	Random random;

	float[] beeX = new float[numberOfBeeSet];

	float[] randomYSet1 = new float[numberOfBeeSet];
	float[] randomYSet2 = new float[numberOfBeeSet];
	float[] randomYSet3 = new float[numberOfBeeSet];

	Circle[] circleSet1 = new Circle[numberOfBeeSet];
	Circle[] circleSet2 = new Circle[numberOfBeeSet];
	Circle[] circleSet3 = new Circle[numberOfBeeSet];









	@Override
	public void create () {

		//game initializes
		batch = new SpriteBatch();
		background = new Texture("background.png");


		//bird initializes
		bird = new Texture("bird.png");
		birdX = Gdx.graphics.getWidth()/4f;
		birdY = Gdx.graphics.getHeight()/2f;
		birdCircle = new Circle();


		//bee initializes
		bee = new Texture("bee.png");
		heightOfBee = Gdx.graphics.getHeight()/12f;
		widthOfBee =Gdx.graphics.getWidth()/15f;
		distanceOfBee = bee.getWidth()*1.5f;
		random = new Random();

		for (int i=0; i<numberOfBeeSet; i++) {
			beeX[i] = Gdx.graphics.getWidth()+i*distanceOfBee;

			randomYSet1[i] = random.nextFloat() * Gdx.graphics.getHeight();
			randomYSet2[i] = random.nextFloat() * Gdx.graphics.getHeight();
			randomYSet3[i] = random.nextFloat() * Gdx.graphics.getHeight();

			circleSet1[i] = new Circle();
			circleSet2[i] = new Circle();
			circleSet3[i] = new Circle();
		}


		//font initializes
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);

	}

	@Override
	public void render () {


		//game start
		if (Gdx.input.justTouched()) {
			gameState = 1;
		}


		batch.begin();

		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/15f,Gdx.graphics.getHeight()/12f);



		//game
		if (gameState==1) {


			//fly the bird
			if (Gdx.input.justTouched()) {
				velocityOfBird -=7;
			}

			//gravity for bird
			birdY -=velocityOfBird;
			velocityOfBird +=gravity;


			//every call render function drawing 9 bee+circle at different position
			for (int i=0; i<numberOfBeeSet; i++){

				batch.draw(bee,beeX[i],randomYSet1[i],widthOfBee,heightOfBee);
				batch.draw(bee,beeX[i],randomYSet2[i],widthOfBee,heightOfBee);
				batch.draw(bee,beeX[i],randomYSet3[i],widthOfBee,heightOfBee);

				beeX[i] -= velocityOfBee;


				circleSet1[i] = new Circle(beeX[i] + widthOfBee/2,  randomYSet1[i]+heightOfBee/2 ,widthOfBee/2.5f);
				circleSet2[i] = new Circle(beeX[i]+widthOfBee/2,  randomYSet2[i]+heightOfBee/2 ,widthOfBee/2.5f);
				circleSet3[i] = new Circle(beeX[i]+widthOfBee/2,  randomYSet3[i]+heightOfBee/2 ,widthOfBee/2.5f);


				//if the bee set goes off the screen
				if (beeX[i] < 0) {
					beeX[i] = Gdx.graphics.getWidth()+distanceOfBee;

					//new random y position for bird set
					randomYSet1[i] = random.nextFloat() * Gdx.graphics.getHeight();
					randomYSet2[i] = random.nextFloat() * Gdx.graphics.getHeight();
					randomYSet3[i] = random.nextFloat() * Gdx.graphics.getHeight();
				}


			}


			//increase score
			if (beeX[scoredEnemy] < Gdx.graphics.getWidth() / 2f - bird.getHeight() / 2f) {
				gameScore++;
				if (scoredEnemy < numberOfBeeSet - 1) {
					scoredEnemy++;
				} else {
					scoredEnemy = 0;
				}
			}



		} else if (gameState==2){ //game over


			font2.draw(batch,"Game Over! Tap To Play Again!",Gdx.graphics.getWidth()/4f,Gdx.graphics.getHeight() / 2f);

			//set game start position

			gameScore = 0;
			velocityOfBird = 0;


			birdX = Gdx.graphics.getWidth()/4f;
			birdY = Gdx.graphics.getHeight()/2f;


			for (int i=0; i<numberOfBeeSet; i++) {
				beeX[i] = Gdx.graphics.getWidth()+i*distanceOfBee;
				randomYSet1[i] = random.nextFloat() * Gdx.graphics.getHeight();
				randomYSet2[i] = random.nextFloat() * Gdx.graphics.getHeight();
				randomYSet3[i] = random.nextFloat() * Gdx.graphics.getHeight();
			}

		}



		//game over state
		if (birdY <= 0) {
			gameState = 2;
		}

		//game over state
		if (birdY >= Gdx.graphics.getHeight()) {
			gameState = 2;
		}

		//game over state collision
		for ( int i = 0; i < numberOfBeeSet; i++) {

			if (Intersector.overlaps(birdCircle,circleSet1[i])
					|| Intersector.overlaps(birdCircle,circleSet2[i])
					|| Intersector.overlaps(birdCircle,circleSet3[i])) {
				gameState = 2;
			}
		}


		font.draw(batch,String.valueOf(gameScore),100,200);
		batch.end();

		birdCircle.set(birdX +Gdx.graphics.getWidth() / 30f ,birdY + Gdx.graphics.getHeight() / 20f,Gdx.graphics.getWidth() / 30f);

	}

	@Override
	public void dispose () {

	}
}
