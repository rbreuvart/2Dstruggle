package com.rbr.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.rbr.game.ConfigPref;
import com.rbr.game.MainGame;
import com.rbr.game.screen.AbsctactScreen;

public class ScreenLoading extends AbsctactScreen{
	SpriteBatch spriteBatchLoading;
	BitmapFont bitmapfont;
	MainGame mainGame;
	Label label;
	Label labelProgression;
	ProgressBar bar;
	
	public ScreenLoading(MainGame mainGame) {
		super(mainGame);
		this.mainGame = mainGame;
		
		
		
	}

	@Override
	public void create() {	
		super.create();
		spriteBatchLoading = new SpriteBatch();
		bitmapfont = new BitmapFont();
		
	//	stage = new Stage(new StretchViewport(ConfigPref.viewPortWidth,ConfigPref.viewPortHeight));
		
		Skin skin = new Skin(Gdx.files.internal(ConfigPref.file_UiSkin));
		
		Table table = new Table(skin);
		table.align(Align.top+Align.left);
		table.setFillParent(true);
		
		
		
		bar = new ProgressBar(0, 1, 0.5f, false, skin);
		bar.setWidth(ConfigPref.viewPortWidth*0.9f);
		bar.setAnimateDuration(0.1f);
		bar.setCenterPosition(ConfigPref.viewPortWidth/2,20);
		
		label = new Label("", skin);
		table.add(label);
		
			
		labelProgression = new Label("", skin);
		labelProgression.setCenterPosition(ConfigPref.viewPortWidth/2-10,35);
		getStage().addActor(labelProgression);
		getStage().addActor(bar);
		getStage().addActor(table);
	}
	
	@Override
	public void render(float delta) {
		//MainGame.printErr(this.getClass()+" render loading", TypeMsg.Batch);
		
		 Gdx.gl.glClearColor(0, 0, 0, 1);
		 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		 
		 float progress = mainGame.getManager().getProgress();
		 String lib = "";
		 for (int i = mainGame.getManager().getAssetNames().size-1; i >= 0; i--) {
			 lib+="\n"+mainGame.getManager().getAssetNames().get(i);
		 }
		 label.setText(lib);
		 
		 labelProgression.setText(Math.round(progress*100)+" %");
		
		 bar.setValue(progress);
		 
		 /*
		 spriteBatchLoading.begin();
		 bitmapfont.draw(spriteBatchLoading, "Loading : "+Math.round(progress*100)+" % ", 10, 500);
		 
		 for (int i = mainGame.getManager().getAssetNames().size-1; i >= 0; i--) {
			  
		 bitmapfont.draw(spriteBatchLoading, mainGame.getManager().getAssetNames().get(i), 200, 500-((i-1)*12));
		 }
		  spriteBatchLoading.end();
		  */
		 getStage().act();
		 getStage().draw();
		
		  if (mainGame.getManager().update()&& Gdx.input.isTouched()) {
			  mainGame.setScreen(new ScreenAccueil(mainGame));			
		}
	}


	@Override
	public void resize(int width, int height) {		
	}
	@Override
	public void show() {
	}
	@Override
	public void hide() {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
	@Override
	public void dispose() {
	}
	
	
}
