package com.celik.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	float birdX;
	float birdY;
	float velocity = 10f;
	float gravity = 0.3f;
	int gameState = 0;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");

		birdX = Gdx.graphics.getWidth()/4f;
		birdY = Gdx.graphics.getHeight()/2f;


	}

	@Override
	public void render () {

		if (Gdx.input.justTouched()) {
			gameState = 1;
		}

		if (gameState==1 && birdY>0) {
			birdY -=velocity;
			velocity +=gravity;
		}




		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/12);
		batch.end();
	}
	
	@Override
	public void dispose () {


	}
}
