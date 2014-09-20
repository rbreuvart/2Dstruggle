package com.rbr.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.rbr.game.ConfigPref;
import com.rbr.game.MainGame;

public abstract class AbsctactScreen implements  GameLogicScreenImpl ,Screen {
	private MainGame mainGame;
	
	private Color backGroundColor;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	private Stage stage;
	
	
	public AbsctactScreen(MainGame mainGame) {
		this.setMainGame(mainGame);
		create();
	}
	@Override
	public void create() {
		backGroundColor = new Color(Color.DARK_GRAY);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		StretchViewport view = new StretchViewport(ConfigPref.viewPortWidth,ConfigPref.viewPortHeight);
		stage = new Stage(view, batch);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public  void render(float delta) {
		Gdx.gl.glClearColor(backGroundColor.r, backGroundColor.g, backGroundColor.b, backGroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}
	
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
	}

	
	public Color getBackGroundColor() {
		return backGroundColor;
	}

	public void setBackGroundColor(Color backGroundColor) {
		this.backGroundColor = backGroundColor;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public void setShapeRenderer(ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	@Override
	public Stage getStage() {
		return this.stage;
	}
	public MainGame getMainGame() {
		return mainGame;
	}
	public void setMainGame(MainGame mainGame) {
		this.mainGame = mainGame;
	}
	

}
