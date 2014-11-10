package com.rbr.game.entity.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.rbr.game.screen.game.ScreenGame;

public  class GameObject {
	
	private String name;	
	private BodyDef bodyDef;
	private Body body ;
	private FixtureDef fixtureDef;
	
	private float ratioDeceleration;
	private boolean autoDeceleration ;
	
	private boolean remove;
	
	private Array<GameObjectCollisionListener> listObservables;
	
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
		remove = false;
		
		listObservables = null;
	}
	/*public  void colisionBegin(GameObject contact, ScreenGame screenGame){
		
	}
	public  void colisionEnd(GameObject contact, ScreenGame screenGame){
		
	}*/
	
	public  void render(ScreenGame screenGame , SpriteBatch batch){};
	public  void update(ScreenGame screenGame , float delta){
		if (isAutoDeceleration()) {
				deceleration(getRatioDeceleration());
		}
		
	};
	
	public void lookAt(Vector2 position){
		Vector2 toTarget = position.sub(this.getBody().getPosition()) ;	
		//System.out.println("GameObject.lookAt()");
		float desiredAngle = (float) Math.atan2( -toTarget.x, toTarget.y );	
		this.getBody().setTransform(this.getBody().getPosition(), desiredAngle);
	}
	

	public void deceleration(float ratioDeceleration){
	//	System.out.println("deceleration : "+getName());
		Vector2 vel = getBody().getLinearVelocity().cpy().scl(ratioDeceleration-1);
		if (Math.sqrt(vel.x)+Math.sqrt(vel.y) <=0.05) {
			 getBody().setLinearVelocity(new Vector2());
			 getBody().setLinearDamping(0);
			 getBody().setAngularDamping(0);
			 getBody().setAngularVelocity(0);
		}
		getBody().applyLinearImpulse(vel, getBody().getPosition(), true);
	}
	
	public float getRatioDeceleration() {
		return ratioDeceleration;
	}
	public void setRatioDeceleration(float ratioDeceleration) {
		this.ratioDeceleration = ratioDeceleration;
	}
	public boolean isAutoDeceleration() {
		return autoDeceleration;
	}
	public void setAutoDeceleration(boolean autoDeceleration) {
		this.autoDeceleration = autoDeceleration;
	}
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	
	
	
	public  void colisionBegin(GameObject contact, ScreenGame screenGame){
		
		if (listObservables != null) {
			//System.out.println("called:"+this.getName()+" contact:"+contact.getName());
			for (GameObjectCollisionListener gameObjectCollisionListener : listObservables) {
				//System.out.println("GameObject.colisionBegin()"+listObservables.size);
				if (gameObjectCollisionListener!=null) {
					gameObjectCollisionListener.colisionBegin(this,contact,screenGame);
				}				
			}
		}
		
	}
	public  void colisionEnd(GameObject contact, ScreenGame screenGame){
		if (listObservables != null) {
			for (GameObjectCollisionListener gameObjectCollisionListener : listObservables) {
			//	System.out.println("GameObject.colisionEnd()"+listObservables.size);
				if (gameObjectCollisionListener!=null) {
					gameObjectCollisionListener.colisionEnd(this,contact,screenGame);
				}				
			}
		}
	}
	

	public void addCollisionObservateur(GameObjectCollisionListener observeur){
		if (listObservables == null) {
			listObservables = new Array<GameObjectCollisionListener>();
		}
		listObservables.add(observeur);
	}
	
	public void removeCollisionObservateur(GameObjectCollisionListener observeur){
		if (listObservables != null) {
			listObservables.removeValue(observeur, false);
		}
	}
	
}
