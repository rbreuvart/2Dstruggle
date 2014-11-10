package com.rbr.game.entity.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.rbr.game.screen.game.ScreenGame;

public interface ISpriteRender {

	public boolean getApplyRotate();
	public void setApplyRotate(boolean applyRotate);
	
	public void update(ScreenGame screenGame, float delta) ;
	
	public void render(ScreenGame screenGame, Vector2 position, float angle,float scale ,SpriteBatch batch);
	
	
		
}
