package com.alesegdia.asroth.game;

import java.util.ArrayList;
import java.util.List;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.ShootComponent.BulletEntry;
import com.alesegdia.asroth.components.ShootComponent.BulletModel;
import com.badlogic.gdx.math.Vector2;

public class BulletConfigs {

	public List<BulletEntry> threeHeadedBulletConfigs = new ArrayList<BulletEntry>();
	public List<BulletEntry> runnerBulletConfigs = new ArrayList<BulletEntry>();
	public List<BulletEntry> jumperBulletConfigs = new ArrayList<BulletEntry>();
	public List<BulletEntry> demonBulletConfigs = new ArrayList<BulletEntry>();
	public List<BulletEntry> playerBulletConfigs = new ArrayList<BulletEntry>();
	
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
		this.threeHeadedBulletConfigs.add(be);
		be = new BulletEntry();
		be.origin = new Vector2(-0.5f,0.3f);
		be.bm = threeHeadedBulletModel;
		this.threeHeadedBulletConfigs.add(be);
		be = new BulletEntry();
		be.origin = new Vector2(-0.5f,0.75f);
		be.bm = threeHeadedBulletModel;
		this.threeHeadedBulletConfigs.add(be);
		
		/* RUNNER SHOOT CONFIG */
		be = new BulletEntry();
		be.origin = new Vector2(0,0.3f);
		be.bm = threeHeadedBulletModel;
		this.runnerBulletConfigs.add(be);
		
		/* JUMPER SHOOT CONFIG */
		be = new BulletEntry();
		be.origin = new Vector2(0, 0.5f);
		be.bm = threeHeadedBulletModel;
		this.jumperBulletConfigs.add(be);
		
		/* DEMON SHOOT CONFIG */
		be = new BulletEntry();
		be.origin = new Vector2(-0.3f, 0);
		be.bm = threeHeadedBulletModel;
		this.demonBulletConfigs.add(be);
		be = new BulletEntry();
		be.bm = threeHeadedBulletModel;
		be.origin = new Vector2(0.3f, 0);
		this.demonBulletConfigs.add(be);
		
		/* PLAYER SHOOT CONFIG */
		be = new BulletEntry();
		be.origin = new Vector2(0,0);
		be.bm = threeHeadedBulletModel;
		this.playerBulletConfigs.add(be);
	}
	
}
