package com.flappybirdclone.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.TextComponent;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture[] bird;
	private Texture back;
	private int widthDevice;
	private int heightDevice;
	private float variation = 0;
	private float velocityFall = 0;
	private float positionInitialWidth;


	@Override
	public void create () {
		batch = new SpriteBatch();

		bird = new Texture[3];
		bird[0] = new Texture("passaro1.png");
		bird[1] = new Texture("passaro2.png");
		bird[2] = new Texture("passaro3.png");

		back = new Texture("fundo.png");

		widthDevice = Gdx.graphics.getWidth();
		heightDevice = Gdx.graphics.getHeight();
		positionInitialWidth = heightDevice / 2;
	}

	@Override
	public void render () {
		variation += Gdx.graphics.getDeltaTime() * 10;
		velocityFall ++;
		if (variation > 2.9) variation = 0;

		if(positionInitialWidth > 0) {
			positionInitialWidth -= velocityFall;
		}

		batch.begin();
		batch.draw(back, 0,0, widthDevice, heightDevice);
		batch.draw(bird[(int)variation], 50, positionInitialWidth );
		batch.end();
	}
}
