package com.rbr.game.entity.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.rbr.game.screen.game.ScreenGame;

public class AnimationRender implements ISpriteRender{


	private Animation           animation;    
	//private Texture             sheetAnimation;      
	private TextureRegion[]         frames;     
//	private SpriteBatch         spriteBatch;       
	private TextureRegion           currentFrame;      

    float stateTime;                  
    private boolean applyRotate;
	
    public AnimationRender(Texture sheetAnimation,int framecols,int framerows,float duration,PlayMode playMode) {
    	//this.sheetAnimation = sheetAnimation;
    
    	TextureRegion[][] tmp = TextureRegion.split(sheetAnimation, sheetAnimation.getWidth()/framecols, sheetAnimation.getHeight()/framerows);            
    	frames = new TextureRegion[framecols * framerows];
    	int index = 0;
        for (int i = 0; i < framerows; i++) {
	        for (int j = 0; j < framecols; j++) {
	        	frames[index++] = tmp[i][j];
	        }
        }
        animation = new Animation(duration, frames); 
        animation.setPlayMode(playMode);
        stateTime = 0f; 
	}
    
    
	@Override
	public void update(ScreenGame screenGame, float delta) {
		 stateTime +=delta;
		 currentFrame = animation.getKeyFrame(stateTime, true);
		 

	}

	@Override
	public void render(ScreenGame screenGame,Vector2 position,float angle,float scale, SpriteBatch batch) {
		/* currentFrame.setPosition(position.x-currentFrame.getRegionWidth()/2,	position.y-currentFrame.getRegionHeight()/2);
		 currentFrame.setRotation((float) (angle* MathUtils.radiansToDegrees));*/

		//FIXME  il faut /2 la position ? ?
		batch.draw(currentFrame,position.x/2,position.y/2,
				currentFrame.getRegionWidth()/2,currentFrame.getRegionHeight()/2,//origine
				currentFrame.getRegionWidth(),currentFrame.getRegionHeight(),//dimention
				scale,scale,//ratio
				(float) (angle* MathUtils.radiansToDegrees));//angle
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}


	@Override
	public boolean getApplyRotate() {
		return applyRotate;
	}


	@Override
	public void setApplyRotate(boolean applyRotate) {
		this.applyRotate = applyRotate;
	}
	
}
