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
	public static TextureRegion hpTexture;
	public static TextureRegion hpHud;
	public static TextureRegion wingsHud;
	
	public static Animation playerWall;
	
	public static Spritesheet cryingMaskSheet;
	public static Spritesheet jumperSheet;
	public static Spritesheet runnerSheet;
	public static Spritesheet evilCherubSheet;
	public static Spritesheet summonerSheet;
	public static Spritesheet threeHeadSheet;
	public static Spritesheet zombieSheet;
	public static Spritesheet demonSheet;
	
	public static Animation threeHeadStand;
	public static Animation threeHeadWalk;
	public static Animation threeHeadAttack;
	public static Animation threeHeadPrepare;

	public static Animation zombieWalk;
	public static Animation zombieStand;
	public static Animation zombieAttack;
	
	public static Animation runnerWalk;
	public static Animation runnerStand;
	public static Animation runnerAttack;
	
	public static Animation summonerWalk;
	public static Animation summonerStand;
	public static Animation summonerAttack;
	public static Animation summonerPrepare;
	
	public static Animation demonWalk;
	public static Animation demonStand;
	public static Animation demonAttack;
	public static Animation runnerPrepare;
	
	public static void Initialize() {
		mapTilesTexture = new Texture(Gdx.files.internal("flyinGB_tiles.png"));
		Texture pt = new Texture(Gdx.files.internal("flyinGB.png"));
		TextureRegion tr = new TextureRegion();
		tr.setRegion(pt);
		
		hpTexture = new TextureRegion();
		hpTexture.setRegion(new Texture(Gdx.files.internal("hpSlot.png")));
		hpHud = new TextureRegion();
		hpHud.setRegion(new Texture(Gdx.files.internal("hpHud.png")));
		wingsHud = new TextureRegion();
		wingsHud.setRegion(new Texture(Gdx.files.internal("wingsBar.png")));
		
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

		cryingMaskSheet = new Spritesheet("cryingmask.png", 3, 2);
		jumperSheet = new Spritesheet("jumpers.png", 1, 3);
		threeHeadSheet = new Spritesheet("threeheaded.png", 1, 2);
		runnerSheet = new Spritesheet("runner.png", 4, 3);
		summonerSheet = new Spritesheet("summoner.png", 1, 4);
		evilCherubSheet = new Spritesheet("evilcherubs.png", 1, 3);
		zombieSheet = new Spritesheet("zombie-sheet.png", 1, 2);
		demonSheet = new Spritesheet("demon.png", 1, 3);

		threeHeadStand = new Animation(0.1f, threeHeadSheet.get(1));
		threeHeadWalk = new Animation(0.1f, threeHeadSheet.getRange(0, 1));
		threeHeadAttack = new Animation(0.1f, threeHeadSheet.get(0));
		threeHeadPrepare = new Animation(0.1f, threeHeadSheet.get(0));
		
		zombieStand = new Animation(0.1f, zombieSheet.get(1));
		zombieWalk = new Animation(0.5f, zombieSheet.getRange(0, 1));
		zombieAttack = new Animation(0.1f, zombieSheet.get(0));

		runnerStand = new Animation(0.1f, runnerSheet.get(0));
		runnerWalk = new Animation(0.1f, runnerSheet.getRange(0, 3));
		runnerAttack = new Animation(0.1f, runnerSheet.get(10));
		runnerPrepare = new Animation(0.1f, runnerSheet.getRange(4,9));
		
		summonerStand = new Animation(0.1f, summonerSheet.get(3));
		summonerWalk = new Animation(0.4f, summonerSheet.getRange(2, 3));
		summonerAttack = new Animation(0.1f, summonerSheet.get(1));
		summonerPrepare = new Animation(0.1f, summonerSheet.getRange(0,1));
		
		demonWalk = new Animation(0.2f, demonSheet.getRange(0,2));
		demonWalk.setPlayMode(PlayMode.LOOP_PINGPONG);

		demonStand = new Animation(0.2f, demonSheet.getRange(0,2));
		demonStand.setPlayMode(PlayMode.LOOP_PINGPONG);

		demonAttack = new Animation(0.2f, demonSheet.getRange(0,2));
		demonAttack.setPlayMode(PlayMode.LOOP_PINGPONG);

		
	}
	
}
