package com.rbr.game.entity.physics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.rbr.game.screen.game.ScreenGame;

public class GameObjectWall extends GameObjectSprite{
	
	
	
	public GameObjectWall(GameObject go,Sprite sprite) {
		super(go,sprite);
		getSprite().setOrigin(getSprite().getWidth()/2, getSprite().getHeight()/2);
	}


	
	@Override
	public void update(ScreenGame screenGame, float delta) {
		getBody().setTransform(getBody().getPosition(), 5f);
		getSprite().setPosition(getBody().getPosition().x-getSprite().getWidth()/2,	getBody().getPosition().y-getSprite().getHeight()/2);
		getSprite().setRotation((float) (getBody().getAngle()* MathUtils.radiansToDegrees));
	}

}
