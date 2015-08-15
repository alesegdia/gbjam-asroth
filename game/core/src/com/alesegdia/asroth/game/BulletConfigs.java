package com.alesegdia.asroth.game;

import java.util.ArrayList;
import java.util.List;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.ShootComponent.BulletEntry;
import com.alesegdia.asroth.components.ShootComponent.BulletModel;
import com.badlogic.gdx.math.Vector2;

public class BulletConfigs {

	public List<BulletEntry> threeHeadedOrigins = new ArrayList<BulletEntry>();
	public List<BulletEntry> runnerOrigins = new ArrayList<BulletEntry>();
	public List<BulletEntry> jumperOrigins = new ArrayList<BulletEntry>();
	public List<BulletEntry> demonOrigins = new ArrayList<BulletEntry>();
	public List<BulletEntry> playerOrigins = new ArrayList<BulletEntry>();
	
	public BulletConfigs() {

		BulletModel threeHeadedBulletModel;
		threeHeadedBulletModel = new BulletModel();
		threeHeadedBulletModel.h = 5;
		threeHeadedBulletModel.w = 5;
		threeHeadedBulletModel.tr = Gfx.playerBulletTexture;
		threeHeadedBulletModel.speed = 10;
		
		BulletEntry be;
		be = new BulletEntry();
		be.origin = new Vector2(-0.5f,-0.35f);
		be.bm = threeHeadedBulletModel;
		this.threeHeadedOrigins.add(be);
		be = new BulletEntry();
		be.origin = new Vector2(-0.5f,0.3f);
		be.bm = threeHeadedBulletModel;
		this.threeHeadedOrigins.add(be);
		be = new BulletEntry();
		be.origin = new Vector2(-0.5f,0.75f);
		be.bm = threeHeadedBulletModel;
		this.threeHeadedOrigins.add(be);
		
		/* RUNNER SHOOT CONFIG */
		be = new BulletEntry();
		be.origin = new Vector2(0,0.3f);
		be.bm = threeHeadedBulletModel;
		this.runnerOrigins.add(be);
		
		/* JUMPER SHOOT CONFIG */
		be = new BulletEntry();
		be.origin = new Vector2(0, 0.5f);
		be.bm = threeHeadedBulletModel;
		this.jumperOrigins.add(be);
		
		/* DEMON SHOOT CONFIG */
		be = new BulletEntry();
		be.origin = new Vector2(-0.3f, 0);
		be.bm = threeHeadedBulletModel;
		this.demonOrigins.add(be);
		be = new BulletEntry();
		be.bm = threeHeadedBulletModel;
		be.origin = new Vector2(0.3f, 0);
		this.demonOrigins.add(be);
		
		/* PLAYER SHOOT CONFIG */
		be = new BulletEntry();
		be.origin = new Vector2(0,0);
		be.bm = threeHeadedBulletModel;
		this.playerOrigins.add(be);
	}
	
}
