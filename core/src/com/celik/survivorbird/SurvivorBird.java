package com.celik.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	//game
	SpriteBatch batch;
	Texture background;
	int gameState = 0;

	//bird
	Texture bird;
	float birdX;
	float birdY;
	float velocityOfBird = 10f;
	float gravity = 0.3f;


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




	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");


		//bird
		bird = new Texture("bird.png");
		birdX = Gdx.graphics.getWidth()/4f;
		birdY = Gdx.graphics.getHeight()/2f;


		//bee
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
		}

	}



	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/15f,Gdx.graphics.getHeight()/12f);

		//game start
		if (Gdx.input.justTouched()) {
			gameState = 1;
		}



		if (gameState==1 && birdY>0) {

			//fly the bird
			if (Gdx.input.justTouched()) {
				velocityOfBird -=7;
			}

			//gravity for bird
			birdY -=velocityOfBird;
			velocityOfBird +=gravity;


			//every call render function drawing 9 bee at different position
			for (int i=0; i<numberOfBeeSet; i++){

				batch.draw(bee,beeX[i],randomYSet1[i],widthOfBee,heightOfBee);
				batch.draw(bee,beeX[i],randomYSet2[i],widthOfBee,heightOfBee);
				batch.draw(bee,beeX[i],randomYSet3[i],widthOfBee,heightOfBee);

				beeX[i] -= velocityOfBee;

				//if the bee set goes off the screen
				if (beeX[i] < 0) {
					beeX[i] = Gdx.graphics.getWidth()+distanceOfBee;

					//new random y position for bird set
					randomYSet1[i] = random.nextFloat() * Gdx.graphics.getHeight();
					randomYSet2[i] = random.nextFloat() * Gdx.graphics.getHeight();
					randomYSet3[i] = random.nextFloat() * Gdx.graphics.getHeight();
				}

			}


		}


		batch.end();
	}



	@Override
	public void dispose () {


	}
}
