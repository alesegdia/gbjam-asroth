package com.alesegdia.asroth.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.alesegdia.asroth.asset.Spritesheet;

public class Gfx {

	public static Texture mapTiles;
	public static Spritesheet playerTiles;
	
	public static Animation playerStand;
	public static Animation playerWalk;
	
	public static void Initialize() {
		mapTiles = new Texture(Gdx.files.internal("flyinGB_tiles.png"));
		Texture pt = new Texture(Gdx.files.internal("flyinGB.png"));
		TextureRegion tr = new TextureRegion();
		tr.setRegion(pt);
		playerTiles = new Spritesheet(tr, 3, 3);
		
		playerStand = new Animation(0.2f, playerTiles.get(0));
		playerWalk = new Animation(0.2f, playerTiles.getRange(1, 3));
	}
	
}
