package com.rbr.game.manageur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Array;

public class ParticleManageur {
	
	Array<PooledEffect> effects ;
	ParticleEffectPool bombEffectPool;
	
	public ParticleManageur() {
		effects = new Array<PooledEffect>();
		
		ParticleEffect bombEffect = new ParticleEffect();
		//bombEffect.load(Gdx.files.internal("particles/bomb.p"), atlas);
		bombEffectPool = new ParticleEffectPool(bombEffect, 1, 2);
		
		PooledEffect effect = bombEffectPool.obtain();
	//	effect.setPosition(x, y);
		effects.add(effect);
	}
	
	
	public void update(){
	
	}
	
	public void render(){/*
		for (int i = effects.size - 1; i >= 0; i--) {
		    PooledEffect effect = effects.get(i);
		    effect.draw(batch, delta);
		    if (effect.isComplete()) {
		        effect.free();
		        effects.removeIndex(i);
		    }
		}*/
	}
	
	
	public void resetAll(){
		for (int i = effects.size - 1; i >= 0; i--)
		    effects.get(i).free();
		effects.clear();
	}
}
 