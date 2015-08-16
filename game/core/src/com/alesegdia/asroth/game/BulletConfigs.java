package com.alesegdia.asroth.game;

import java.util.ArrayList;
import java.util.List;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.ShootComponent.BulletEntry;
import com.alesegdia.asroth.components.ShootComponent.BulletModel;
import com.alesegdia.asroth.components.WeaponComponent.WeaponModel;
import com.badlogic.gdx.math.Vector2;

public class BulletConfigs {

	public List<BulletEntry> threeHeadedBEList = new ArrayList<BulletEntry>();
	public List<BulletEntry> runnerBEList = new ArrayList<BulletEntry>();
	public List<BulletEntry> jumperBEList = new ArrayList<BulletEntry>();
	public List<BulletEntry> demonBEList = new ArrayList<BulletEntry>();
	
	
	BulletModel standardEnemyBM = new BulletModel();
	
	public static List<BulletEntry> playerDefaultBEList = new ArrayList<BulletEntry>();
	BulletModel playerDefaultBM = new BulletModel();
	
	public static List<BulletEntry> playerTriplasmaBEList = new ArrayList<BulletEntry>();
	BulletModel playerTriplasmaBM_T = new BulletModel();
	BulletModel playerTriplasmaBM_B = new BulletModel();
	BulletModel playerTriplasmaBM_M = new BulletModel();

	public static List<BulletEntry> playerFireballBEList = new ArrayList<BulletEntry>();
	BulletModel playerFireballBM = new BulletModel();
	
	public static List<BulletEntry> playerSinegunBEList = new ArrayList<BulletEntry>();
	BulletModel playerSinegunBM = new BulletModel();
	
	public static List<BulletEntry> playerSacredpunchBEList = new ArrayList<BulletEntry>();
	BulletModel playerSacredpunchBM = new BulletModel();
	
	public static List<BulletEntry> playerTroquegunBEList = new ArrayList<BulletEntry>();
	BulletModel playerTroquegunBM = new BulletModel();
	
	public static WeaponModel defaultGun = new WeaponModel();
	public static WeaponModel fireball = new WeaponModel();
	public static WeaponModel triplasma = new WeaponModel();
	public static WeaponModel sineGun = new WeaponModel();
	public static WeaponModel sacredPunch = new WeaponModel();
	public static WeaponModel troqueGun = new WeaponModel();
	
	public BulletConfigs() {

		standardEnemyBM.h = 5;
		standardEnemyBM.w = 5;
		standardEnemyBM.tr = Gfx.playerBulletTexture;
		standardEnemyBM.speed = 10;
		standardEnemyBM.destructionTime = 1f;
		standardEnemyBM.isPlayer = false;
		standardEnemyBM.power = 1;
		
		createEntryInList( -0.5f, -0.35f, standardEnemyBM, threeHeadedBEList );
		createEntryInList( -0.5f, 0.3f, standardEnemyBM, threeHeadedBEList );
		createEntryInList( -0.5f, 0.75f, standardEnemyBM, threeHeadedBEList );

		/* RUNNER SHOOT CONFIG */
		createEntryInList( 0, 0.3f, standardEnemyBM, runnerBEList );
		
		/* JUMPER SHOOT CONFIG */
		createEntryInList( 0, 0.5f, standardEnemyBM, jumperBEList );
		
		/* DEMON SHOOT CONFIG */
		createEntryInList( -0.3f, 0f, standardEnemyBM, demonBEList );
		createEntryInList( 0.3f, 0.3f, standardEnemyBM, demonBEList );
		
		/* PLAYER DEFAULT SHOOT CONFIG */
		playerDefaultBM.h = 5;
		playerDefaultBM.w = 5;
		playerDefaultBM.isPlayer = true;
		playerDefaultBM.speed = 10;
		playerDefaultBM.tr = Gfx.playerBulletTexture;
		playerDefaultBM.power = 2;
		playerDefaultBM.destructionTime = 1f;
		createEntryInList( 0, 0, playerDefaultBM, playerDefaultBEList );
		defaultGun.rate = 0.2f;
		defaultGun.bulletEntries = playerDefaultBEList;
		
		playerFireballBM.h = 5;
		playerFireballBM.w = 5;
		playerFireballBM.isPlayer = true;
		playerFireballBM.speed = 20;
		playerFireballBM.tr = Gfx.playerFireballTexture;
		playerFireballBM.destructionTime = 1f;
		createEntryInList( 0, 0, playerFireballBM, playerFireballBEList );
		fireball.rate = 0.1f;
		fireball.bulletEntries = playerFireballBEList;
		
		playerSacredpunchBM.h = 5;
		playerSacredpunchBM.w = 5;
		playerSacredpunchBM.isPlayer = true;
		playerSacredpunchBM.power = 5;
		playerSacredpunchBM.speed = 15;
		playerSacredpunchBM.destructionTime = 0.2f;
		playerSacredpunchBM.tr = Gfx.playerSacredpunchTexture;
		createEntryInList( 0, -0.2f, playerSacredpunchBM, playerSacredpunchBEList);
		createEntryInList( 0, 0.2f, playerSacredpunchBM, playerSacredpunchBEList);
		sacredPunch.rate = 0.05f;
		sacredPunch.bulletEntries = playerSacredpunchBEList;
		
		playerTriplasmaBM_T.h = 5;
		playerTriplasmaBM_T.w = 5;
		playerTriplasmaBM_T.isPlayer = true;
		playerTriplasmaBM_T.power = 2;
		playerTriplasmaBM_T.speed = 10;
		playerTriplasmaBM_T.destructionTime = 0.5f;
		playerTriplasmaBM_T.tr = Gfx.playerTriplasmaTexture;
		playerTriplasmaBM_T.dir = new Vector2(1, 0.3f);
		playerTriplasmaBM_T.horizontal = false;
		createEntryInList( 0, 0f, playerTriplasmaBM_T, playerTriplasmaBEList);
		playerTriplasmaBM_M.h = 5;
		playerTriplasmaBM_M.w = 5;
		playerTriplasmaBM_M.isPlayer = true;
		playerTriplasmaBM_M.power = 2;
		playerTriplasmaBM_M.speed = 10;
		playerTriplasmaBM_M.destructionTime = 0.5f;
		playerTriplasmaBM_M.tr = Gfx.playerTriplasmaTexture;
		playerTriplasmaBM_M.horizontal = true;
		createEntryInList( 0, 0f, playerTriplasmaBM_M, playerTriplasmaBEList);
		playerTriplasmaBM_B.h = 5;
		playerTriplasmaBM_B.w = 5;
		playerTriplasmaBM_B.isPlayer = true;
		playerTriplasmaBM_B.power = 2;
		playerTriplasmaBM_B.speed = 10;
		playerTriplasmaBM_B.destructionTime = 0.5f;
		playerTriplasmaBM_B.tr = Gfx.playerTriplasmaTexture;
		playerTriplasmaBM_B.dir = new Vector2(1, -0.3f);
		playerTriplasmaBM_B.horizontal = false;
		createEntryInList( 0, 0f, playerTriplasmaBM_B, playerTriplasmaBEList);
		triplasma.rate = 0.05f;
		triplasma.bulletEntries = playerTriplasmaBEList;

		playerSinegunBM.h = 5;
		playerSinegunBM.w = 5;
		playerSinegunBM.isPlayer = true;
		playerSinegunBM.power = 1;
		playerSinegunBM.speed = 10;
		playerSinegunBM.destructionTime = 1f;
		playerSinegunBM.tr = Gfx.playerSinegunTexture;
		playerSinegunBM.horizontal = true;
		playerSinegunBM.sinegun = true;
		createEntryInList( 0, 0, playerSinegunBM, playerSinegunBEList);
		sineGun.rate = 0.05f;
		sineGun.bulletEntries = playerSinegunBEList;
		
	}
	
	void createEntryInList( float oX, float oY, BulletModel bm, List<BulletEntry> belist ) {
		BulletEntry be = new BulletEntry();
		be.origin = new Vector2(oX, oY);
		be.bm = bm;
		belist.add(be);
	}
	
}
