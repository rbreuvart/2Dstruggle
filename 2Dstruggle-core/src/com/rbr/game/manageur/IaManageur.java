package com.rbr.game.manageur;

import com.badlogic.gdx.utils.Array;
import com.rbr.game.player.PlayerIa;
import com.rbr.game.screen.game.ScreenGame;

public class IaManageur {

	private Array<PlayerIa> listIaPlayer;

	public Array<PlayerIa> getListIaPlayer() {
		return listIaPlayer;
	}

	public void setListIaPlayer(Array<PlayerIa> listIaPlayer) {
		this.listIaPlayer = listIaPlayer;
	}
	public IaManageur() {
		listIaPlayer = new Array<PlayerIa>();
	}

	public void update(ScreenGame screenGame, float delta) {
	
		
		
	}
	
	
	
	
	
}
