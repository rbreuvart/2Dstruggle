package com.rbr.game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.rbr.game.screen.menu.ScreenLoading;
import com.rbr.game.utils.ConfigPref;
import com.rbr.game.utils.ConfigPref.TypeMsg;
import com.rbr.game.utils.FileLoader;

public class MainGame extends Game {

	
	private AssetManager manager;
	
	
	private Map<String, FileHandle> mapFileHandle;
	
	public AssetManager getManager() {
		return manager;
	}
	public void setManager(AssetManager manager) {
		this.manager = manager;
	}
	public Map<String, FileHandle> getMapFileHandle() {
		return mapFileHandle;
	}
	public void setMapFileHandle(Map<String, FileHandle> mapFileHandle) {
		this.mapFileHandle = mapFileHandle;
	}
	
	
	@Override
	public void create() {
		
	
		manager = new AssetManager();
		mapFileHandle = new HashMap<String, FileHandle>();
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
		FileLoader loader = new FileLoader();
		loader.addAllFile(this);
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
