package com.rbr.game.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.rbr.game.screen.game.ScreenGame;

public class LifeBarRender {

	private float height;
	private float width;
	
	public LifeBarRender(float width , float height ) {
		this.height = height;
		this.width = width;
	}
	
	
	public void render(ScreenGame screenGame, SpriteBatch spriteBatch,	ShapeRenderer shapeRenderer,Vector2 positionBar,float life, float lifeMax) {
		//System.out.println("LifeBarRender.render()");
		//System.out.println(positionBar+" "+life+"/"+lifeMax);
		float centreX = (float)width/2f;
		Vector2 position = positionBar.cpy();
		//position.add(-5, 5);
		//float lenght = 40f;
		float ratiovie = life/lifeMax;
		shapeRenderer.setColor(0,0,0, 1);
		shapeRenderer.rectLine(position.x-centreX-(0.02f),
					position.y,
					position.x+(getWidth()*1)-centreX+(0.02f),
					position.y,
					getHeight()+0.03f);
	
		shapeRenderer.setColor((ratiovie*-1)+1, 1*ratiovie, 0, 1);
		shapeRenderer.rectLine(position.x-centreX, position.y, position.x+(getWidth()*ratiovie)-centreX, position.y,getHeight());
		
		
	
		//shapeRenderer.rectLine(new Vector2(10, 10), new Vector2(50, 50), 5);
		shapeRenderer.setColor(1,1,1, 1);
		
		
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}
	
}
