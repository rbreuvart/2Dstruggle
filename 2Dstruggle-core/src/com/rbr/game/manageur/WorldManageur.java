package com.rbr.game.manageur;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.rbr.game.CameraManageur;

public class WorldManageur {
	
	private World world;
	
	
	
	private Box2DDebugRenderer debugRenderer;
	
	
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public Box2DDebugRenderer getDebugRenderer() {
		return debugRenderer;
	}
	public void setDebugRenderer(Box2DDebugRenderer debugRenderer) {
		this.debugRenderer = debugRenderer;
	}
	
	int velocityIterations = 8;
	int positiontiteration = 3;
	
	
	public WorldManageur(int pixelMeter) {
		
		setDebugRenderer(new Box2DDebugRenderer());
		setWorld(new World(new Vector2(0, 0), true)); 
		
		debugRenderer.setDrawVelocities(true);
		
		
	}
	
	
	
	public void update(float delta, CameraManageur camManageur){
		world.step(delta,velocityIterations, positiontiteration);
		
	}
	public void render(float delta, CameraManageur camManageur) {	
		//debugRenderer.render(world, camManageur.getOrthographicCamera().combined);
	}
	
	public void dispose(){
		world.dispose();
	}

	
}
