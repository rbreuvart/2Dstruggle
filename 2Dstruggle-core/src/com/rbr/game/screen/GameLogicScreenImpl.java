package com.rbr.game.screen;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rbr.game.IGameLogic;

public interface GameLogicScreenImpl extends IGameLogic{
	
	public Stage getStage();
	public void show() ;
	public void hide() ;
	
}
