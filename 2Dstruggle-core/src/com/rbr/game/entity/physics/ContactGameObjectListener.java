package com.rbr.game.entity.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.rbr.game.manageur.GameObjectManageur;

public class ContactGameObjectListener implements ContactListener{

	GameObjectManageur gameObjectManageur;
	
	public ContactGameObjectListener(GameObjectManageur gameObjectManageur) {
		this.gameObjectManageur = gameObjectManageur;
	}
	
	@Override
	public void beginContact(Contact contact) {
	//	System.out.println("ContactGameObjectListener.beginContact()");
		GameObject go1 = gameObjectManageur.getGObyBody(contact.getFixtureB().getBody());
		GameObject go2 = gameObjectManageur.getGObyBody(contact.getFixtureB().getBody());
		try {
			go1.colisionBegin(go2);
			go2.colisionBegin(go1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void endContact(Contact contact) {
	//	System.out.println("ContactGameObjectListener.endContact()");
		GameObject go1 = gameObjectManageur.getGObyBody(contact.getFixtureB().getBody());
		GameObject go2 = gameObjectManageur.getGObyBody(contact.getFixtureB().getBody());
		try {
			go1.colisionEnd(go2);
			go2.colisionEnd(go1);
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
