package com.rbr.game.player;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.rbr.game.entity.arme.Arme;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.entity.physics.GameObjectCollisionListener;
import com.rbr.game.entity.physics.GameObjectSprite;
import com.rbr.game.entity.projectile.Projectile;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;

public class PlayerLocal extends Player implements GameObjectCollisionListener{

	private Arme arme;
	
	public PlayerLocal(GameObject gameObject) {
		super(gameObject);		
		//prend en charge manuelement dans l'update de playerControle l'auto deceleration
		getGameObject().setAutoDeceleration(false);
		getGameObject().addCollisionObservateur(this);
		arme = new Arme(100, 100, 5, 300, 0.99f);
		//la rotation n'est pas appliqu�
		((GameObjectSprite)getGameObject()).getISpriteRender().setApplyRotate(true);
	}
	
	@Override
	public void colisionBegin(GameObject called,GameObject contact, ScreenGame screenGame) {
		System.out.println("called:"+called.getName()+" contact:"+contact.getName());
		if (called instanceof Projectile) {
			System.out.println("projectile");
			if (((Projectile)called).getPlayerEmeteur().equals(this)) {
				System.out.println("le player se colisionne eavec ces balles");
			}else{
				System.out.println("le player local colisione avec "+called);
			}
		}
	}


	@Override
	public void colisionEnd(GameObject called,GameObject contact, ScreenGame screenGame) {
		// TODO Auto-generated method stub
		
	}
	
	
	float spesificImpulse;
	public void update(ScreenGame screenGame ,float delta){
		
		spesificImpulse = 0.5f;
		
		Vector2 vel = getGameObject().getBody().getLinearVelocity();
		Vector2 pos = getGameObject().getBody().getPosition();	
	//	boolean actionmove = false;
		
		Vector2 vectorVise = new Vector2();
		float angleVise = 0;
		boolean shoot  = false;
		
		if (Gdx.app.getType().equals(ApplicationType.Desktop)) {
			
			Vector3 souriePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			screenGame.getCamManageur().getOrthographicCamera().unproject(souriePos);

			//System.out.println("souriePos:"+souriePos);
			vectorVise = new Vector2(souriePos.x, souriePos.y);
			vectorVise = pos.cpy().sub(vectorVise);
			angleVise = vectorVise.angle()+90;
			if (Gdx.input.isTouched(0)) {
				shoot = true;
			}
			
			if (Gdx.input.isKeyPressed(Keys.LEFT) && vel.x > -ConfigPref.Player_MAX_VELOCITY) {          
				getGameObject().getBody().applyLinearImpulse(-spesificImpulse, 0, pos.x, pos.y, true);
			}
	
			if (Gdx.input.isKeyPressed(Keys.RIGHT) && vel.x < ConfigPref.Player_MAX_VELOCITY) {
				getGameObject().getBody().applyLinearImpulse(spesificImpulse, 0, pos.x, pos.y, true);
			}
			
			if (Gdx.input.isKeyPressed(Keys.DOWN) && vel.y > -ConfigPref.Player_MAX_VELOCITY) {          
				getGameObject().getBody().applyLinearImpulse(0, -spesificImpulse, pos.x, pos.y, true);
			}
	
			if (Gdx.input.isKeyPressed(Keys.UP) && vel.y < ConfigPref.Player_MAX_VELOCITY) {
				getGameObject().getBody().applyLinearImpulse(0,spesificImpulse, pos.x, pos.y, true);
			}
		
			getGameObject().deceleration(getGameObject().getRatioDeceleration());
			
		
			
		}
		if (Gdx.app.getType().equals(ApplicationType.Android)) {
			
			//touchPad	aim		
			vectorVise = new Vector2(screenGame.getTouchpadAim().getKnobPercentX(),screenGame.getTouchpadAim().getKnobPercentY());
			vectorVise = vectorVise.nor().scl(-1, -1);
			angleVise = -vectorVise.angle(pos)+90f;
			
			//angleVise = vectorVise.angle(pos)-90;
			if (!vectorVise.isZero(0.5f)) {
				shoot = true;
			}
			
			
			//touch pad position
			Vector2 touchpadVec = new Vector2(screenGame.getTouchpad().getKnobPercentX(),screenGame.getTouchpad().getKnobPercentY());
			touchpadVec = touchpadVec.nor().scl(spesificImpulse);
			
			if (vel.y < ConfigPref.Player_MAX_VELOCITY  && vel.y > -ConfigPref.Player_MAX_VELOCITY) {
				getGameObject().getBody().applyLinearImpulse(0,touchpadVec.y, pos.x, pos.y, true);
			}
			
			if (vel.x < ConfigPref.Player_MAX_VELOCITY && vel.x > -ConfigPref.Player_MAX_VELOCITY ) {
				getGameObject().getBody().applyLinearImpulse(touchpadVec.x,0, pos.x, pos.y, true);
			}
			
			getGameObject().deceleration(getGameObject().getRatioDeceleration());
	
		}
		//
		getGameObject().getBody().setTransform(pos,angleVise);
		
		
		if (shoot) {
			arme.shootUpdate(screenGame,pos, vectorVise,this);
		}
		
		//camera follow cette position
		screenGame.getCamManageur().folowTarget(getGameObject().getBody().getPosition(),getGameObject().getBody().getAngle());
		
	}
	
	
	public void render(ScreenGame screenGame, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer){
	
	}

	
	

	public Arme getArme() {
		return arme;
	}


	public void setArme(Arme arme) {
		this.arme = arme;
	}


	
	
	
}