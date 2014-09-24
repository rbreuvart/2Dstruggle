package com.rbr.game.manageur;

import com.badlogic.gdx.utils.Array;
import com.rbr.game.player.IaPlayer;
import com.rbr.game.screen.game.ScreenGame;

public class IaManageur {

	private Array<IaPlayer> listIaPlayer;

	public Array<IaPlayer> getListIaPlayer() {
		return listIaPlayer;
	}

	public void setListIaPlayer(Array<IaPlayer> listIaPlayer) {
		this.listIaPlayer = listIaPlayer;
	}
	public IaManageur() {
		listIaPlayer = new Array<IaPlayer>();
	}

	public void update(ScreenGame screenGame, float delta) {
	
		
		
	}
	
	
	
	
	
}
