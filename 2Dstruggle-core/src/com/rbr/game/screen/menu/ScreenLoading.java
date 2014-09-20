package com.rbr.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rbr.game.MainGame;

public class ScreenLoading implements Screen{
	SpriteBatch spriteBatchLoading;
	BitmapFont bitmapfont;
	MainGame mainGame;
	Actor actor;
	public ScreenLoading(MainGame mainGame) {
		this.mainGame = mainGame;
		
		spriteBatchLoading = new SpriteBatch();
		bitmapfont = new BitmapFont();
		actor = new Actor();
	}

	
	@Override
	public void render(float delta) {
		//MainGame.printErr(this.getClass()+" render loading", TypeMsg.Batch);
		 float progress = mainGame.getManager().getProgress();
		 Gdx.gl.glClearColor(0, 0, 0, 1);
		 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
		  spriteBatchLoading.begin();
		  bitmapfont.draw(spriteBatchLoading, "Loading : "+Math.round(progress*100)+" % ", 10, 500);
		 
		  for (int i = mainGame.getManager().getAssetNames().size-1; i >= 0; i--) {
			  
			bitmapfont.draw(spriteBatchLoading, mainGame.getManager().getAssetNames().get(i), 200, 500-((i-1)*12));
		  }
		  spriteBatchLoading.end();
		  
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
