package com.rbr.game;

import java.lang.reflect.Field;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rbr.game.ConfigPref.TypeMsg;
import com.rbr.game.screen.menu.ScreenLoading;

public class MainGame extends Game {

	
	private AssetManager manager;
	
	

	public AssetManager getManager() {
		return manager;
	}

	public void setManager(AssetManager manager) {
		this.manager = manager;
	}

	
	
	@Override
	public void create() {
		
	
		
		manager = new AssetManager();
	/*	FileHandle[] files = Gdx.files.internal("data/").list();
		for(FileHandle file: files) {
			if (file.name().contains("skin")) {
			
			}
			System.out.println(file.name());
			MainGame.printErr(file.name());
		}*/
		//manager.finishLoading();
		/*manager.load(GameConfigPref.file_UiSkin, Skin.class);
		manager.load(GameConfigPref.file_Vaisseau1,Texture.class);
		manager.load(GameConfigPref.file_redBox,Texture.class);
		manager.load(GameConfigPref.file_redCircle,Texture.class);
		*/
		Class<ConfigPref> c = ConfigPref.class;
		Field[] fields = c.getDeclaredFields();
 
		for(Field f : fields){
			if (f.getName().contains("file")) {
			
				
				
				try {
					System.out.println(f.getName()+ " "+(String)f.get(ConfigPref.class));
					String pathFile = (String)f.get(ConfigPref.class);
					if (f.getName().contains("Skin")) {
						manager.load( pathFile, Skin.class);
					}else if(f.getName().contains("Map")){
						manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
						manager.load(pathFile, TiledMap.class);
					}else{						
						manager.load( pathFile, Texture.class);
					}
					
				} catch (IllegalArgumentException e) {e.printStackTrace();} catch (IllegalAccessException e) {e.printStackTrace();}
				
			}
			
		}
		
		setScreen(new ScreenLoading(this) );
	}

	public void render() {
		
		getScreen().render(Gdx.graphics.getDeltaTime());
		
		 
	}

	@Override
	public void dispose() {
		// getScreen().dispose();
		super.dispose();
	}

//	@Override
//	public boolean keyDown(int keycode) {
//		return controleManageur.keyDown(keycode);
//	}
//
//	@Override
//	public boolean keyUp(int keycode) {
//		controleManageur.keyUp(keycode);
//		return false;
//	}
//
//	@Override
//	public boolean keyTyped(char character) {
//		controleManageur.keyTyped(character);
//		return false;
//	}
//
//	@Override
//	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//		return controleManageur.touchDown(screenX, screenY, pointer, button);
//	}
//
//	@Override
//	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//		return controleManageur.touchUp(screenX, screenY, pointer, button);
//	}
//
//	@Override
//	public boolean touchDragged(int screenX, int screenY, int pointer) {
//		return controleManageur.touchDragged(screenX, screenY, pointer);
//	}
//
//	@Override
//	public boolean mouseMoved(int screenX, int screenY) {
//		return controleManageur.mouseMoved(screenX, screenY);
//	}
//
//	@Override
//	public boolean scrolled(int amount) {
//		return controleManageur.scrolled(amount);
//	}
//
//
//	
	
	public static final void printMsg(String msg) {
		if (ConfigPref.debugMsg) {
			System.out.println(msg);
		}
	}

	public static final void printMsg(String msg, TypeMsg typeMsg) {
		switch (typeMsg) {
		case Debug:
			if (ConfigPref.debug) {
				System.out.println(msg);
			}
			break;
		case ShapeRender:
			if (ConfigPref.debugShapeRender) {
				System.out.println(msg);
			}
			break;

		case Batch:
			if (ConfigPref.debugBatch) {
				System.out.println(msg);
			}
			break;
		case Controlleur:
			if (ConfigPref.debugControlleur) {
				System.out.println(msg);
			}
			break;
		default:
			System.out.println(msg);
			break;
		}

	}

	public static final void printErr(String msg) {
		if (ConfigPref.debugMsgErr) {
			System.err.println(msg);
		}
	}

	public static final void printErr(String msg, TypeMsg typeMsg) {
		switch (typeMsg) {
		case Debug:
			if (ConfigPref.debug) {
				System.err.println(msg);
			}
			break;
		case ShapeRender:
			if (ConfigPref.debugShapeRender) {
				System.err.println(msg);
			}
			break;

		case Batch:
			if (ConfigPref.debugBatch) {
				System.err.println(msg);
			}
			break;
		case Controlleur:
			if (ConfigPref.debugControlleur) {
				System.err.println(msg);
			}
			break;
		case Stage:
			if (ConfigPref.debugStage) {
				System.err.println(msg);
			}
			break;
		default:
			System.err.println(msg);
			break;
		}

	}
	
	
	
}
