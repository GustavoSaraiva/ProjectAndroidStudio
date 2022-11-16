package com.flappybirdclone.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.TextComponent;
import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture[] bird;
	private Texture back;
	private int widthDevice;
	private int heightDevice;
	private float variation = 0;
	private float velocityFall = 0;
	private float positionInitialWidth;
	private Texture pipeTop;
	private Texture pipeBottom;
	private float positionPipeHeigth;
	private float positionPipeWidth;
	private float betweenpipe;
	private Random numberRandom;
	private float heightPipeRandomic;


	@Override
	public void create () {
		batch = new SpriteBatch();

		bird = new Texture[3];
		bird[0] = new Texture("passaro1.png");
		bird[1] = new Texture("passaro2.png");
		bird[2] = new Texture("passaro3.png");

		back = new Texture("fundo.png");

		pipeBottom = new Texture("cano_baixo.png");
		pipeTop = new Texture("cano_topo.png");


		numberRandom = new Random();

		widthDevice = Gdx.graphics.getWidth();
		heightDevice = Gdx.graphics.getHeight();
		positionInitialWidth = heightDevice / 2;
		positionPipeWidth = widthDevice;
		betweenpipe = 240;
	}

	@Override
	public void render () {
		variation += Gdx.graphics.getDeltaTime() * 10;
		velocityFall ++;
		positionPipeWidth -= Gdx.graphics.getDeltaTime() * 200;
		if (variation > 2.9) variation = 0;

		if (Gdx.input.justTouched())
		{
			velocityFall = -15;
		}

		if(positionInitialWidth > 0 || velocityFall < 0) {
			positionInitialWidth -= velocityFall;
		}

		if(positionPipeWidth < -pipeTop.getWidth())
		{
			heightPipeRandomic = numberRandom.nextInt(400) - 200;
			positionPipeWidth = widthDevice ;
		}



		batch.begin();
		batch.draw(back, 0,0, widthDevice, heightDevice);
		batch.draw(pipeTop, positionPipeWidth, heightDevice / 2 + betweenpipe /2 + heightPipeRandomic);
		batch.draw(pipeBottom, positionPipeWidth, heightDevice / 2 - pipeBottom.getHeight() - betweenpipe / 2 + heightPipeRandomic);
		batch.draw(bird[(int)variation], 50, positionInitialWidth );
		batch.end();
	}
}
