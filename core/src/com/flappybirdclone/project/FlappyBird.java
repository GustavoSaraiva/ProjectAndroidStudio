package com.flappybirdclone.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
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
	private int gameState = 0;
	private BitmapFont font;
	private int pointing = 0;
	private boolean point = false;
	private Circle cicleBird;
	private Rectangle pipeTopRectangle;
	private Rectangle pipeBottomRectangle;
	private ShapeRenderer shape;
	private Texture gameOver;
	private BitmapFont message;


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
		gameOver = new Texture("game_over.png");

		numberRandom = new Random();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(5);
		message = new BitmapFont();
		message.setColor(Color.WHITE);
		message.getData().setScale(2);

		cicleBird = new Circle();



		shape = new ShapeRenderer();

		widthDevice = Gdx.graphics.getWidth();
		heightDevice = Gdx.graphics.getHeight();
		positionInitialWidth = heightDevice / 2;
		positionPipeWidth = widthDevice;
		betweenpipe = 240;
	}

	@Override
	public void render () {
		variation += Gdx.graphics.getDeltaTime() * 10;
		if (variation > 2.9) variation = 0;

		if(gameState == 0)
		{
			if(Gdx.input.justTouched())
			{
				gameState = 1;
			}
		} else
		{
			velocityFall ++;
			if(positionInitialWidth > 0 || velocityFall < 0) {
				positionInitialWidth -= velocityFall;
			}

			if (gameState == 1)
			{
				positionPipeWidth -= Gdx.graphics.getDeltaTime() * 200;

				if (Gdx.input.justTouched())
				{
					velocityFall = -15;
				}

				if(positionPipeWidth < -pipeTop.getWidth())
				{
					heightPipeRandomic = numberRandom.nextInt(400) - 200;
					positionPipeWidth = widthDevice ;
					point = false;
				}
				if(positionPipeWidth < 80){
					if(!point) {
						point = true;
						pointing++;
					}
				}

			}else{
				if(Gdx.input.justTouched())
				{
					gameState = 0;
					pointing = 0;
					velocityFall = 0;
					positionInitialWidth = heightDevice / 2;
					positionPipeWidth = widthDevice;
				}

			}



		}

		batch.begin();
		batch.draw(back, 0,0, widthDevice, heightDevice);
		batch.draw(pipeTop, positionPipeWidth, heightDevice / 2 + betweenpipe /2 + heightPipeRandomic);
		batch.draw(pipeBottom, positionPipeWidth, heightDevice / 2 - pipeBottom.getHeight() - betweenpipe / 2 + heightPipeRandomic);
		batch.draw(bird[(int)variation], 80, positionInitialWidth );
		font.draw(batch, String.valueOf(pointing), widthDevice / 2 - 20, heightDevice -50);
		if(gameState == 2)
		{
			batch.draw(gameOver, widthDevice /2 - gameOver.getWidth() / 2, heightDevice / 2);
			message.draw(batch, "Toque para Reiniciar", widthDevice /2 - message.getRegion().getRegionWidth() /2 , heightDevice /2 - message.getLineHeight() /2 );
		}
		batch.end();

		cicleBird.set(80 + bird[0].getWidth() /2, positionInitialWidth + bird[0].getHeight() / 2 , bird[0].getWidth() / 2);
		pipeBottomRectangle = new Rectangle(
				positionPipeWidth, heightDevice / 2 - pipeBottom.getHeight() - betweenpipe / 2 + heightPipeRandomic,
				pipeBottom.getWidth(), pipeBottom.getHeight()
				);
		pipeTopRectangle = new Rectangle(
				positionPipeWidth, heightDevice / 2 + betweenpipe /2 + heightPipeRandomic,
				pipeTop.getWidth(), pipeTop.getHeight()
				);

		/*shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.circle(cicleBird.x, cicleBird.y, cicleBird.radius);
		shape.rect(pipeBottomRectangle.x, pipeBottomRectangle.y, pipeBottomRectangle.width, pipeBottomRectangle.height);
		shape.rect(pipeTopRectangle.x, pipeTopRectangle.y, pipeTopRectangle.width, pipeTopRectangle.height);
		shape.setColor(Color.RED);
		shape.end();*/

		if(Intersector.overlaps(cicleBird, pipeBottomRectangle) || Intersector.overlaps(cicleBird, pipeTopRectangle) || positionInitialWidth <= 0 || positionInitialWidth >= heightDevice)
		{
			gameState = 2;

		}




	}
}
