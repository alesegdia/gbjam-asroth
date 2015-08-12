package com.alesegdia.asroth.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.alesegdia.asroth.asset.Spritesheet;

public class Gfx {

	public static Texture mapTilesTexture;
	public static Spritesheet playerSheet;
	
	public static Animation playerStand;
	public static Animation playerWalk;
	public static Animation playerFly;
	
	public static Animation playerJumpUp;
	public static Animation playerJumpDown;
	
	public static Animation playerMashDown;
	
	public static Spritesheet groundExplosionSpritesheet;
	public static Animation groundExplosion;
	
	public static TextureRegion playerBulletTexture;
	public static Animation playerWall;
	
	public static void Initialize() {
		mapTilesTexture = new Texture(Gdx.files.internal("flyinGB_tiles.png"));
		Texture pt = new Texture(Gdx.files.internal("flyinGB.png"));
		TextureRegion tr = new TextureRegion();
		tr.setRegion(pt);
		playerSheet = new Spritesheet(tr, 3, 5);
		
		playerStand = new Animation(0.2f, playerSheet.get(0));
		playerWall = new Animation(0.2f, playerSheet.get(14));
		playerWalk = new Animation(0.2f, playerSheet.getRange(1, 3));
		playerWalk.setPlayMode(PlayMode.LOOP);
		playerFly = new Animation(0.15f, playerSheet.getRange(6, 8));
		playerJumpUp = new Animation(0.2f, playerSheet.get(7));
		playerJumpDown = new Animation(0.2f, playerSheet.get(8));
		playerMashDown = new Animation(0.05f, playerSheet.getRange(10,13));
		
		Texture get = new Texture(Gdx.files.internal("flyinGB_groundExplosion.png"));
		TextureRegion trget = new TextureRegion();
		trget.setRegion(get);
		groundExplosionSpritesheet = new Spritesheet(trget, 2,4);
		
		groundExplosion = new Animation(0.1f, groundExplosionSpritesheet.getRange(0,7));
		groundExplosion.setPlayMode(PlayMode.LOOP);
		
		playerBulletTexture = new TextureRegion();
		playerBulletTexture.setRegion(new Texture(Gdx.files.internal("flyinGB_playerBullet.png")));
	}
	
}
