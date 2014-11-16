package com.rbr.game.entity.projectile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.entity.physics.GameObjectSprite;
import com.rbr.game.player.Player;
import com.rbr.game.screen.game.ScreenGame;

public class Projectile extends  GameObjectSprite{

	
	private Player playerEmeteur;
	private float degat;
	
	
	
	public Projectile(GameObject go,Sprite sprite,Player playerEmeteur,float degat) {
		super(go, sprite);
		this.degat = degat;
		//super(go.getName(), go.getBodyDef(), go.getBody(), go.getFixtureDef());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		go.getBody().setBullet(true);
		go.getBody().setGravityScale(0);
		setAutoDeceleration(false);
		
	}
	
	@Override
	public void colisionBegin(GameObject contact, final ScreenGame screenGame) {
		//System.out.println("Contact ");
		super.colisionBegin(contact,screenGame);
		if (contact!=null) {			
			infigeDegat(contact,screenGame);
		}		
		//setRemove(true);
	}
	
	public void render(ScreenGame screenGame, SpriteBatch batch) {
		super.render(screenGame, batch);
		//		this.iSpriteRender.render(screenGame, getBody().getPosition(),  getBody().getLinearVelocity().angle()-90, 0, batch);
		
		
		getISpriteRender().render(screenGame, 	getBody().getPosition(),
												getBody().getLinearVelocity().angle()-90, 0, batch);
		/*getSprite().draw(batch);*/
	}
	
	public void infigeDegat(GameObject contact, ScreenGame screenGame) {
		
		Player playerContact = screenGame.getPlayerManageur().getPlayerByGameObject(contact);
		if (playerContact != null) {
			playerContact.subitDegat(screenGame,getDegat());
		}
	}


	public Player getPlayerEmeteur() {
		return playerEmeteur;
	}
	public void setPlayerEmeteur(Player playerEmeteur) {
		this.playerEmeteur = playerEmeteur;
	}	
	public float getDegat() {
		return degat;
	}
	public void setDegat(float degat) {
		this.degat = degat;
	}

}
