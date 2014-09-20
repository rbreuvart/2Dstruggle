package com.rbr.game.manageur;

import com.rbr.game.inteligence.Player;
import com.rbr.game.screen.game.ScreenGame;

public class PlayerManageur {

	public Player player;
	
	
	public PlayerManageur(Player player){
		this.player = player;
	}
	public void update(float delta,ScreenGame screenGame){
		player.update(screenGame,delta);
	}

	
	
	
}
