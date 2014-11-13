package com.rbr.game.utils;

import com.badlogic.gdx.math.Vector2;

public class Vector2Utils {

	
	public static Vector2 getVectorVise(Vector2 originBody,Vector2 originBodyVise){
		return originBody.cpy().sub(originBody.cpy()).nor();
	}
	
}
