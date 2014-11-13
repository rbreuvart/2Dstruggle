package com.rbr.game.entity.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.rbr.game.entity.projectile.Projectile;
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
		if (!"Mur".equals(contact.getFixtureA().getUserData())) {
			go1 = screenGame.getGameObjectManageur().getGObyBody(contact.getFixtureA().getBody());
			
		}
		
		GameObject go2 = null;
		if(!"Mur".equals(contact.getFixtureB().getUserData())) {
			go2 = screenGame.getGameObjectManageur().getGObyBody(contact.getFixtureB().getBody());
			
		}
		
	//	System.out.println("go1"+go1+" go2"+go2);
		
		
		try {
			go1.colisionBegin(go2,screenGame);			
			go2.colisionBegin(go1,screenGame);
		} catch (Exception e) {
		}
		
		if (go1 instanceof Projectile) {		
			go1.setRemove(true);
		}
		if (go2 instanceof Projectile) {
			go2.setRemove(true);
		}
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
