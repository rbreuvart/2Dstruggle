package com.rbr.game.player;

import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.screen.game.ScreenGame;

public class PlayerIa extends Player{

	
	
	public PlayerIa(GameObject gameObject) {
		super(gameObject);	
		
		getGameObject().setAutoDeceleration(false);
	//	arme = new Arme(100, 100, 5, 300, 0.99f);
	}
	
	

	public void update(ScreenGame screenGame,float delta){
	
		//code de l'Ia
		
		
		
	}
}
