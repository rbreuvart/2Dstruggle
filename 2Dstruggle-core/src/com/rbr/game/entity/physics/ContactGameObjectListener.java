package com.rbr.game.entity.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.rbr.game.entity.projectile.Projectile;
import com.rbr.game.player.Player;
import com.rbr.game.screen.game.ScreenGame;

public class ContactGameObjectListener implements ContactListener{

	ScreenGame screenGame;
	
	public ContactGameObjectListener(ScreenGame	screenGame) {
		this.screenGame = screenGame;
	}
	
	
	@Override
	public void beginContact(Contact contact) {
	//	System.out.println("ContactGameObjectListener.beginContact()");
	
		
		GameObject go1 = null;
		boolean go1IsMur = false;
		if (!"Mur".equals(contact.getFixtureA().getUserData())) {
			go1 = screenGame.getGameObjectManageur().getGObyBody(contact.getFixtureA().getBody());
		}else{
			go1IsMur = true;
		}
		
		GameObject go2 = null;
		boolean go2IsMur = false;
		if(!"Mur".equals(contact.getFixtureB().getUserData())) {
			go2 = screenGame.getGameObjectManageur().getGObyBody(contact.getFixtureB().getBody());	
		}else{
			go2IsMur = true;
		}
		
	//	System.out.println("Collision \n go1:"+go1+"\n go1IsMur:"+go1IsMur+"\n go2:"+go2+" \n go2IsMur:"+go2IsMur);
		
		
		try {
			if (go1IsMur||go2IsMur) {
				//System.out.println("MURRR");
				if (go1 instanceof Projectile) {
					go1.setRemove(true);
				}
				if (go2 instanceof Projectile) {
					go2.setRemove(true);
				}
			}else{
				/////////
				if (go1 instanceof Projectile) {
					Projectile proj1 = (Projectile)go1;				
					if (!proj1.getPlayerEmeteur().getGameObject().equals(go2)) {				
						proj1.colisionBegin(go2,screenGame);					
						proj1.setRemove(true);
						//System.out.println("delete Proj1 after colide");
					}else{
						//proj1.colisionBegin(go2,screenGame);
					}
				}
				
				if (go2 instanceof Projectile) {					
					Projectile proj2 = (Projectile)go2;
					if (!proj2.getPlayerEmeteur().getGameObject().equals(go1)) {
						proj2.colisionBegin(go1,screenGame);
						proj2.setRemove(true);
						//System.out.println("delete Proj2 after colide");
					}else{				
						//proj2.colisionBegin(go1,screenGame);
					}				
				}
				/////////
			}
			
			
			
			
			
			
						
			
		} catch (Exception e) {
		}
		
		/*
		if (go1 instanceof Projectile) {		
			go1.setRemove(true);
		}
		if (go2 instanceof Projectile) {
			go2.setRemove(true);
		}*/
	}

	@Override
	public void endContact(Contact contact) {
	//	System.out.println("ContactGameObjectListener.endContact()");
		GameObject go1 = screenGame.getGameObjectManageur().getGObyBody(contact.getFixtureA().getBody());
		GameObject go2 = screenGame.getGameObjectManageur().getGObyBody(contact.getFixtureB().getBody());
		try {
			go1.colisionEnd(go2,screenGame);
			go2.colisionEnd(go1,screenGame);
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
	//	System.out.println("ContactGameObjectListener.preSolve()");
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
	//	System.out.println("ContactGameObjectListener.postSolve()");
	}

}
