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

	public static Animation jumperWalk;
	public static Animation jumperAttack;
	public static Animation jumperStand;
	public static Animation jumperPrepare;
	public static TextureRegion playerFireballTexture;
	public static TextureRegion playerSacredpunchTexture;
	public static TextureRegion playerTriplasmaTexture;
	public static TextureRegion playerSinegunTexture;
	public static TextureRegion upHudTexture;
	
	public static Spritesheet healthPickupSheet;
	public static Spritesheet wingsPickupSheet;
	public static Spritesheet moneyPickupSheet;
	public static Animation healthPickupAnim;
	public static Animation wingsPickupAnim;
	public static Animation moneyPickupAnim;
	public static Spritesheet shopSheet;
	public static Animation shopAnim;
	public static TextureRegion sineSymbol;
	public static TextureRegion triplasmaSymbol;
	public static TextureRegion sacredSymbol;
	public static TextureRegion fireballSymbol;
	public static TextureRegion defaultSymbol;
	public static TextureRegion wepslot;
	public static Animation cryingMaskAnim;
	public static Animation cherubAnim;
	
	public static Spritesheet mashShopItems;

	public static Spritesheet crossSheet;
	public static Animation crossAnim;
	
	public static TextureRegion portalTexture;
	
	
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
		
		shopSheet = new Spritesheet("GBshoopkeeper.png", 1, 4);
		shopAnim = new Animation(0.2f, shopSheet.getRange(0, 3));
		shopAnim.setPlayMode(PlayMode.LOOP);
		
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
		
		upHudTexture = new TextureRegion();
		upHudTexture.setRegion(new Texture(Gdx.files.internal("uphud.png")));
		
		trget = new TextureRegion();
		trget.setRegion(new Texture(Gdx.files.internal("GBhealthPickup.png")));
		healthPickupSheet = new Spritesheet(trget, 4, 1);
		healthPickupAnim = new Animation(0.1f, healthPickupSheet.getRange(0,3));

		trget = new TextureRegion();
		trget.setRegion(new Texture(Gdx.files.internal("GBwingsPickup.png")));
		wingsPickupSheet = new Spritesheet(trget, 4, 1);
		wingsPickupAnim = new Animation(0.1f, wingsPickupSheet.getRange(0,3));
		
		trget = new TextureRegion();
		trget.setRegion(new Texture(Gdx.files.internal("GBmoneyPickup.png")));
		moneyPickupSheet = new Spritesheet(trget, 4, 1);
		moneyPickupAnim = new Animation(0.1f, moneyPickupSheet.getRange(0,3));
		
		groundExplosion = new Animation(0.1f, groundExplosionSpritesheet.getRange(0,7));
		groundExplosion.setPlayMode(PlayMode.LOOP);
		
		playerBulletTexture = new TextureRegion();
		playerBulletTexture.setRegion(new Texture(Gdx.files.internal("flyinGB_playerBullet.png")));
		
		playerFireballTexture = new TextureRegion();
		playerFireballTexture.setRegion(new Texture(Gdx.files.internal("GBfireball.png")));

		playerSacredpunchTexture = new TextureRegion();
		playerSacredpunchTexture.setRegion(new Texture(Gdx.files.internal("GBsacred.png")));
		
		playerTriplasmaTexture = new TextureRegion();
		playerTriplasmaTexture.setRegion(new Texture(Gdx.files.internal("GBtriplasma.png")));
		
		playerSinegunTexture = new TextureRegion();
		playerSinegunTexture.setRegion(new Texture(Gdx.files.internal("sinegun.png")));
		
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
		runnerPrepare = new Animation(0.1f, runnerSheet.getRange(8,9));
		
		summonerStand = new Animation(0.1f, summonerSheet.get(3));
		summonerWalk = new Animation(0.4f, summonerSheet.getRange(2, 3));
		summonerAttack = new Animation(0.1f, summonerSheet.get(1));
		summonerPrepare = new Animation(0.1f, summonerSheet.getRange(0,1));

		cryingMaskAnim = new Animation(0.1f, cryingMaskSheet.getRange(0,5));
		cherubAnim  = new Animation(0.1f, evilCherubSheet.getRange(0,2));
		
		demonWalk = new Animation(0.2f, demonSheet.getRange(0,2));
		demonWalk.setPlayMode(PlayMode.LOOP_PINGPONG);

		demonStand = new Animation(0.2f, demonSheet.getRange(0,2));
		demonStand.setPlayMode(PlayMode.LOOP_PINGPONG);

		demonAttack = new Animation(0.2f, demonSheet.getRange(0,2));
		demonAttack.setPlayMode(PlayMode.LOOP_PINGPONG);

		jumperWalk = new Animation(0.2f, jumperSheet.get(0));
		jumperStand = new Animation(0.2f, jumperSheet.get(2));
		jumperAttack = new Animation(0.2f, jumperSheet.get(0));
		jumperPrepare = new Animation(0.2f, jumperSheet.get(0));

		defaultSymbol = new TextureRegion();
		defaultSymbol.setRegion(new Texture(Gdx.files.internal("defaultSymbol.png")));

		fireballSymbol = new TextureRegion();
		fireballSymbol.setRegion(new Texture(Gdx.files.internal("fireballSymbol.png")));

		sineSymbol = new TextureRegion();
		sineSymbol.setRegion(new Texture(Gdx.files.internal("sinegunSymbol.png")));

		triplasmaSymbol = new TextureRegion();
		triplasmaSymbol.setRegion(new Texture(Gdx.files.internal("triplasmaSymbol.png")));
		
		sacredSymbol = new TextureRegion();
		sacredSymbol.setRegion(new Texture(Gdx.files.internal("sacredPunchSymbol.png")));
		
		wepslot = new TextureRegion();
		wepslot.setRegion(new Texture(Gdx.files.internal("wepslot.png")));
		
		mashShopItems = new Spritesheet("smashPowerups.png", 1, 2);
		
		
		
		crossSheet = new Spritesheet("cross.png", 1, 4);
		crossAnim = new Animation(0.2f, crossSheet.getRange(0, 3));
		
		portalTexture = new TextureRegion();
		portalTexture.setRegion(new Texture(Gdx.files.internal("portal.png")));

		
		
		
	}
	
}
