package com.rbr.game.utils;

import com.badlogic.gdx.math.Vector2;

public class GameCodeTools {

	
	public static Vector2 getVectorVise(Vector2 originBody,Vector2 originBodyVise){
		return originBody.cpy().sub(originBody.cpy()).nor();
	}
	
	public static String getRandomColor(){
		int lower = 150;
		int higher = 255;
		int lowerA = 255;
		int randomR = (int)(Math.random() * (higher-lower)) + lower;
		int randomG = (int)(Math.random() * (higher-lower)) + lower;
		int randomB = (int)(Math.random() * (higher-lower)) + lower;
		int randomA = (int)(Math.random() * (higher-lowerA)) + lowerA;
		String cR = Integer.toHexString(randomR);
		String cG = Integer.toHexString(randomG);
		String cB = Integer.toHexString(randomB);
		String cA = Integer.toHexString(randomA);
		return ""+cR+""+cG+""+cB+""+cA;
	}
	
}
