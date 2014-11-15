package com.rbr.game.manageur;

import java.util.Iterator;

import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.utils.Array;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.screen.game.ScreenGame;

public class GarbageManageur {

	public GarbageManageur(ScreenGame screenGame) {
	
	}
	
	
	public void update(ScreenGame screenGame){
		gameManageurClear(screenGame);
	}

	/**
	 * supprime proprement les Game object et les Body physics
	 * @param screenGame
	 */
	private void gameManageurClear(ScreenGame screenGame){
		
		for (Iterator<GameObject> iter = screenGame.getGameObjectManageur().getGameObjectArray().iterator();iter.hasNext();) {
			GameObject go = iter.next();
			if (go.isRemove()) {
				
				Array<JointEdge>list = go.getBody().getJointList();
			    while (list.size > 0) {
			    	screenGame.getWorldManageur().getWorld().destroyJoint(list.get(0).joint);
			    }
			    // actual remove
			    screenGame.getWorldManageur().getWorld().destroyBody(go.getBody());
			    screenGame.getGameObjectManageur().getGameObjectArray().removeValue(go,false);
			}
			
		}
		
	}
	
	
	
	
	
}
