package com.rbr.game.entity.arme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.rbr.game.entity.physics.FabriqueAll;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.entity.projectile.Projectile;
import com.rbr.game.player.Player;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;

public class Arme {

	private float cooldown;
	private float range;
	private float degat;
	private float speed;
	private float presision;
	
	
	private long lastShootTime;
	
	
	
	
	
	public Arme(float cooldown, float range, float degat, float speed, float presision) {
		super();
		this.cooldown = cooldown;
		this.range = range;
		this.degat = degat;
		this.speed = speed;
		this.presision = presision;
		
		this.lastShootTime = 0;
	}

	/**
	 * execute shooot si le cooldown et fini 
	 * @param starposition
	 * @param direction
	 * @param player 
	 */
	public void shootUpdate(ScreenGame screenGame,Vector2 starposition,Vector2 direction, Player player){
		long timedif = System.currentTimeMillis()-lastShootTime;
		//System.out.println((cooldown)+" timedif:"+timedif);
		if (cooldown<=timedif) {
			lastShootTime = System.currentTimeMillis();
			shoooot(screenGame,starposition, direction,player);		
		}
	}
	
	/**
	 * 
	 * @param starposition
	 * @param direction
	 * @param player 
	 */
	public void shoooot(final ScreenGame screenGame,final Vector2 starposition,final Vector2 direction, final Player player){
	//	System.out.println("Shooooooot !!!");
		
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run() {
			//	System.out.println("starposition:"+starposition);
				//new FabriqueAll();
				
				float density = 1f;
				float friction =0.01f;
				float restitution =0f;
				
				Vector2 vecStart = starposition.cpy().sub(direction.cpy().nor().scl(0.05f));
				
								
				GameObject gameObject = FabriqueAll.creationGameObjectSquare(screenGame.getWorldManageur().getWorld(),
						"bulletShoot",
						vecStart.x, vecStart.y,
						0.2f, 0.2f,
						ConfigPref.pixelMeter, BodyType.DynamicBody,density,friction,restitution,
						(short) (player.getProjectileFilterCategory()),//CATEGORY type
						(short) (player.getProjectileFilterGroup()),//GROUP traverse
						(short) (player.getProjectileFilterMask()));//MASK Inverse
				Sprite spritebullet = new Sprite(screenGame.getMainGame().getManager().get(ConfigPref.File_Bullet1, Texture.class));
				//float unitscale = (float)(1/ConfigPref.pixelMeter);
				//System.out.println("unitscale"+unitscale);
				spritebullet.setScale(0.2f/ConfigPref.pixelMeter);
				//spritebullet.setAlpha(0.3f);
				Projectile projectile = new Projectile(gameObject,spritebullet,player,getDegat());
				
				projectile.getBody().applyLinearImpulse(direction.cpy().nor().scl((float) -1), projectile.getBody().getPosition(), true);
				screenGame.getGameObjectManageur().add(projectile);
			}
		});
		
	}

	public float getCooldown() {
		return cooldown;
	}

	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public float getDegat() {
		return degat;
	}

	public void setDegat(float degat) {
		this.degat = degat;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getPresision() {
		return presision;
	}

	public void setPresision(float presision) {
		this.presision = presision;
	}

	public long getLastShootTime() {
		return lastShootTime;
	}

	public void setLastShootTime(long lastShootTime) {
		this.lastShootTime = lastShootTime;
	}
	
	
}
