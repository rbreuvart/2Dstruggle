package com.rbr.game.entity.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.rbr.game.screen.game.ScreenGame;

public  class GameObject {
	
	private String name;	
	private BodyDef bodyDef;
	private Body body ;
	private FixtureDef fixtureDef;
	/*
	private ArrayList<GameObjectCollisionListener> listObservables ;
	*/
	//get/set
	public BodyDef getBodyDef() {
		return bodyDef;
	}
	public void setBodyDef(BodyDef bodyDef) {
		this.bodyDef = bodyDef;
	}
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
	}
	public FixtureDef getFixtureDef() {
		return fixtureDef;
	}
	public void setFixtureDef(FixtureDef fixtureDef) {
		this.fixtureDef = fixtureDef;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public GameObject(String name, BodyDef bodyDef, Body body,FixtureDef fixtureDef) {
		super();
		this.setName(name);
		this.bodyDef = bodyDef;
		this.body = body;
		this.fixtureDef = fixtureDef;
		
		this.ratioDeceleration = 0.95f;
		autoDeceleration = true;
	}
	public  void colisionBegin(GameObject contact){
		
	}
	public  void colisionEnd(GameObject contact){
		
	}
	
	public  void render(ScreenGame screenGame , SpriteBatch batch){};
	public  void update(ScreenGame screenGame , float delta){};
	
	public void lookAt(Vector2 position){
		Vector2 toTarget = position.sub(this.getBody().getPosition()) ;	
		//System.out.println("GameObject.lookAt()");
		float desiredAngle = (float) Math.atan2( -toTarget.x, toTarget.y );	
		this.getBody().setTransform(this.getBody().getPosition(), desiredAngle);
	}
	
	/*public void acceleration(Vector2 vel){
		
	}*/
	private float ratioDeceleration;
	
	public void deceleration(float ratioDeceleration){
		Vector2 vel = getBody().getLinearVelocity().cpy().scl(ratioDeceleration-1);
		
		getBody().applyLinearImpulse(vel, getBody().getPosition(), true);
	}
	public float getRatioDeceleration() {
		return ratioDeceleration;
	}
	public void setRatioDeceleration(float ratioDeceleration) {
		this.ratioDeceleration = ratioDeceleration;
	}
	
	private boolean autoDeceleration ;
	
	public boolean isAutoDeceleration() {
		return autoDeceleration;
	}
	public void setAutoDeceleration(boolean autoDeceleration) {
		this.autoDeceleration = autoDeceleration;
	}
	
	
	/*
	public  void colisionBegin(GameObject contact){
		if (listObservables != null) {
			for (GameObjectCollisionListener gameObjectCollisionListener : listObservables) {
				System.out.println("GameObject.colisionBegin()"+listObservables.size());
				gameObjectCollisionListener.colisionBegin(contact);
			}
		}
		
	}
	public  void colisionEnd(GameObject contact){
		if (listObservables != null) {
			for (GameObjectCollisionListener gameObjectCollisionListener : listObservables) {
				System.out.println("GameObject.colisionEnd()"+listObservables.size());
				gameObjectCollisionListener.colisionEnd(contact);
			}
		}
	}
	
	public void addObserverCollision(GameObjectCollisionListener collisionListener){
		
		if (listObservables == null) {
			listObservables = new ArrayList<GameObjectCollisionListener>();
		}
		
		boolean dejala = false;
		for (GameObjectCollisionListener gameObjectCollisionListener : listObservables) {
			if (collisionListener.equals(gameObjectCollisionListener)) {
				dejala = true;
			}
		}
		if (!dejala) {			
			listObservables.add(collisionListener);
		}
		
	}
	public void removeObserverCollision(GameObjectCollisionListener collisionListener){
		if (listObservables != null) {
			listObservables.remove(collisionListener);
		}
	}
	*/
	/*
	public final void gameLogicInterface(ScreenGame screenGame, SpriteBatch batch,float delta) {
        //do the things you always want to have happen here

		update( screenGame,  delta);
		render( screenGame,  batch);
    }*/

  
	

	
}
