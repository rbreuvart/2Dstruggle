package com.rbr.game.entity.physics;

import com.rbr.game.screen.game.ScreenGame;

public interface GameObjectCollisionListener {

	public void colisionBegin(GameObject called, GameObject contact2, ScreenGame screenGame);

	public void colisionEnd(GameObject called, GameObject contact2, ScreenGame screenGame);

}
