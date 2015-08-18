package com.alesegdia.asroth.game;

import java.util.ArrayList;
import java.util.List;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.ActiveComponent;
import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.BulletComponent;
import com.alesegdia.asroth.components.BuyerComponent;
import com.alesegdia.asroth.components.CountdownDestructionComponent;
import com.alesegdia.asroth.components.DamageComponent;
import com.alesegdia.asroth.components.DebugComponent;
import com.alesegdia.asroth.components.DropPickupComponent;
import com.alesegdia.asroth.components.AIAgentAttackPreparationComponent;
import com.alesegdia.asroth.components.AIAgentAnimatorComponent;
import com.alesegdia.asroth.components.AIAgentComponent;
import com.alesegdia.asroth.components.AIAgentFlyingComponent;
import com.alesegdia.asroth.components.AIAgentInhibitWalkComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.HealthComponent;
import com.alesegdia.asroth.components.HideComponent;
import com.alesegdia.asroth.components.InfiniteFlyComponent;
import com.alesegdia.asroth.components.InvincibilityComponent;
import com.alesegdia.asroth.components.JumperAttackComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.MashComponent;
import com.alesegdia.asroth.components.MoneyComponent;
import com.alesegdia.asroth.components.AIAgentPeriodicAutoAttackComponent;
import com.alesegdia.asroth.components.AIWarpComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.PickupEffectComponent;
import com.alesegdia.asroth.components.PickupItemComponent;
import com.alesegdia.asroth.components.PickupItemComponent.PickupType;
import com.alesegdia.asroth.components.PlayerComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.components.VanishingComponent;
import com.alesegdia.asroth.components.ShootComponent;
import com.alesegdia.asroth.components.ShootComponent.BulletEntry;
import com.alesegdia.asroth.components.ShootComponent.BulletModel;
import com.alesegdia.asroth.components.ShopComponent.ShopProduct;
import com.alesegdia.asroth.components.ShopComponent;
import com.alesegdia.asroth.components.StrikeAttackComponent;
import com.alesegdia.asroth.components.SummonNearComponent;
import com.alesegdia.asroth.components.SummonZoneComponent;
import com.alesegdia.asroth.components.WalkingComponent;
import com.alesegdia.asroth.components.WeaponComponent;
import com.alesegdia.asroth.components.WingsComponent;
import com.alesegdia.asroth.ecs.Engine;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.physics.CollisionLayers;
import com.alesegdia.asroth.physics.Physics;
import com.alesegdia.asroth.systems.AnimationSystem;
import com.alesegdia.asroth.systems.AttackTriggeringSystem;
import com.alesegdia.asroth.systems.CountdownDestructionSystem;
import com.alesegdia.asroth.systems.CrossComponent;
import com.alesegdia.asroth.systems.DebugSystem;
import com.alesegdia.asroth.systems.DrawingSystem;
import com.alesegdia.asroth.systems.DropPickupSystem;
import com.alesegdia.asroth.systems.EnhancedSystem;
import com.alesegdia.asroth.systems.FallingSystem;
import com.alesegdia.asroth.systems.AIAgentAnimationSystem;
import com.alesegdia.asroth.systems.AIAgentFacePlayerAttackSystem;
import com.alesegdia.asroth.systems.AIAgentFlyingSystem;
import com.alesegdia.asroth.systems.AIAgentIhibitWalkWhenAttackingSystem;
import com.alesegdia.asroth.systems.AIAgentJumperAnimControllerSystem;
import com.alesegdia.asroth.systems.FarDeactivationSystem;
import com.alesegdia.asroth.systems.FlipSystem;
import com.alesegdia.asroth.systems.HideSystem;
import com.alesegdia.asroth.systems.HumanControllerSystem;
import com.alesegdia.asroth.systems.InfiniteFlySystem;
import com.alesegdia.asroth.systems.InvincibilitySystem;
import com.alesegdia.asroth.systems.JumperAttackSystem;
import com.alesegdia.asroth.systems.MovementSystem;
import com.alesegdia.asroth.systems.PainSystem;
import com.alesegdia.asroth.systems.PickupSystem;
import com.alesegdia.asroth.systems.AIAgentStrikeAttackSystem;
import com.alesegdia.asroth.systems.ShootingSystem;
import com.alesegdia.asroth.systems.ShopItemDrawingSystem;
import com.alesegdia.asroth.systems.ShopRefillingSystem;
import com.alesegdia.asroth.systems.ShoppingSystem;
import com.alesegdia.asroth.systems.SineMovementSystem;
import com.alesegdia.asroth.systems.SummoningNearSystem;
import com.alesegdia.asroth.systems.AIAgentPeriodicAttackSystem;
import com.alesegdia.asroth.systems.AIAgentPrepareAttackSystem;
import com.alesegdia.asroth.systems.AIAgentSystem;
import com.alesegdia.asroth.systems.SummoningZoneSystem;
import com.alesegdia.asroth.systems.TakingDamageSystem;
import com.alesegdia.asroth.systems.UpdatePhysicsSystem;
import com.alesegdia.asroth.systems.VanishingSystem;
import com.alesegdia.asroth.systems.WeaponChangeSystem;
import com.alesegdia.asroth.systems.WingsRecoverySystem;
import com.alesegdia.asroth.systems.AIAgentWalkingSystem;
import com.alesegdia.asroth.systems.AIAgentWarpingSystem;
import com.alesegdia.asroth.systems.AccountStatsSystem;
import com.alesegdia.platgen.map.AirPlatformExtractor;
import com.alesegdia.platgen.map.MobZoneExtractor;
import com.alesegdia.platgen.map.MobZoneExtractor.MobZone;
import com.alesegdia.platgen.map.TileMap;
import com.alesegdia.platgen.map.TileType;
import com.alesegdia.platgen.map.AirPlatformExtractor.AirPlatform;
import com.alesegdia.platgen.util.RNG;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class GameWorld {
	
	public static GameWorld instance;

	Engine engine;
	private Physics physics;
	private Camera cam;
	BulletConfigs bulletCfgs;
	
	public int getNumEntities() {
		return engine.getNumEntities();
	}
	
	
	Entity player;

	private TileMap tm;

	public GameWorld( Physics physics, SpriteBatch batch, Camera cam ) {
		this.physics = physics;
		this.cam = cam;
		engine = new Engine();
		engine.addSystem(new HumanControllerSystem());
		engine.addSystem(new AnimationSystem());
		//engine.addSystem(new FarDeactivationSystem());
		
		engine.addSystem(new AIAgentSystem());
		engine.addSystem(new AIAgentWalkingSystem());
		engine.addSystem(new AIAgentFlyingSystem());

		engine.addSystem(new AIAgentPeriodicAttackSystem());
		engine.addSystem(new AIAgentPrepareAttackSystem());
		engine.addSystem(new AIAgentStrikeAttackSystem());
		engine.addSystem(new AttackTriggeringSystem());
		engine.addSystem(new AIAgentAnimationSystem());
		engine.addSystem(new AIAgentIhibitWalkWhenAttackingSystem());
		engine.addSystem(new WeaponChangeSystem());

		engine.addSystem(new WingsRecoverySystem());
		engine.addSystem(new PainSystem());
		engine.addSystem(new AIAgentWarpingSystem());
		engine.addSystem(new SummoningZoneSystem());
		engine.addSystem(new SummoningNearSystem());
		engine.addSystem(new JumperAttackSystem());
		engine.addSystem(new ShootingSystem());

		engine.addSystem(new UpdatePhysicsSystem());
		engine.addSystem(new CountdownDestructionSystem());
		engine.addSystem(new MovementSystem());
		engine.addSystem(new FlipSystem());
		engine.addSystem(new AIAgentFacePlayerAttackSystem());
		engine.addSystem(new TakingDamageSystem());
		engine.addSystem(new AIAgentJumperAnimControllerSystem());
		engine.addSystem(new HideSystem());
		engine.addSystem(new SineMovementSystem());
		engine.addSystem(new DropPickupSystem());
		engine.addSystem(new PickupSystem());
		engine.addSystem(new ShoppingSystem());
		engine.addSystem(new ShopRefillingSystem());
		engine.addSystem(new VanishingSystem());
		engine.addSystem(new AccountStatsSystem());
		

		engine.addSystem(new InvincibilitySystem());
		engine.addSystem(new InfiniteFlySystem());
		engine.addSystem(new EnhancedSystem());

		engine.addSystem(new FallingSystem());
		
		engine.addSystem(new DrawingSystem(batch), true);
		engine.addSystem(new ShopItemDrawingSystem(batch), true);
		
		//textRenderingSystem = new TextRenderingSystem(batch, cam);
		//engine.addSystem(textRenderingSystem);
		engine.addSystem(physics.physicsSystem);
		
		bulletCfgs = new BulletConfigs();
		
	}
	
	void InitWorld(TileMap tm) {
		engine.Clear();
		this.tm = tm;
		int x = -1;
		int y = -1;
		for( int i = 0; i < tm.cols; i++ ) {
			for( int j = tm.rows-1; j >= 0; j-- ) {
				int tt = tm.Get(j, i);
				if( tt == TileType.WALL ) {
					x = i+3;
					y = j+1;
					break;
				}
			}
			if( x != -1 ) break;
		}
		makePortal(x*10, (y+1+0.5f)*10);
		makePlayer((x+1)*10, (y+2)*10);
		adjustToTile(this.player, x+2, y+1);
		

		AirPlatformExtractor ape = new AirPlatformExtractor();
		List<AirPlatform> platforms = ape.computeMobZones(tm);
		
		for( AirPlatform ap : platforms ) {
			float ww = Math.abs(ap.xRange.x - ap.xRange.y) * 10f / 2f + 5;
			float xx = ap.xRange.x * 10 + ww;
			float yy = ap.height * 10 + 9f;
			float hh = 1f;
			physics.createRectBody(
					xx * GameConfig.PIXELS_TO_METERS,
					yy * GameConfig.PIXELS_TO_METERS,
					ww * GameConfig.PIXELS_TO_METERS,
					hh * GameConfig.PIXELS_TO_METERS,
					CollisionLayers.CATEGORY_1WAYPLATS, CollisionLayers.MASK_1WAYPLATS, CollisionLayers.GROUP_1WAYPLATS, false);

		}
		
		MobZoneExtractor mze = new MobZoneExtractor();
		List<MobZone> mobZones = mze.computeMobZones(tm);
		int i = 0;
		for( MobZone mz : mobZones ) {
			float w, h;
			w = 10; h = 10;
			//x = s.position.x * 10 + w;
			x = mz.xRange.x * 10;
			//y = (s.position.y + s.height - s.region.position.y) * 10 + h;
			y = (mz.height+1) * 10;
			physics.createRectBody(
					x * GameConfig.PIXELS_TO_METERS,
					y * GameConfig.PIXELS_TO_METERS,
					w * GameConfig.PIXELS_TO_METERS,
					h * GameConfig.PIXELS_TO_METERS,
					CollisionLayers.CATEGORY_ENEMYLIMIT,
					CollisionLayers.MASK_ENEMYLIMIT,
					CollisionLayers.GROUP_ENEMYLIMIT,
					false);
			x = mz.xRange.y * 10;
			physics.createRectBody(
					x * GameConfig.PIXELS_TO_METERS,
					y * GameConfig.PIXELS_TO_METERS,
					w * GameConfig.PIXELS_TO_METERS,
					h * GameConfig.PIXELS_TO_METERS,
					CollisionLayers.CATEGORY_ENEMYLIMIT,
					CollisionLayers.MASK_ENEMYLIMIT,
					CollisionLayers.GROUP_ENEMYLIMIT,
					false);
			
			//Entity e = makeSummoner(0,0,mz);
			//adjustToTile(e, mz.xRange.x + 3, mz.height + 1);

			int xx = mz.xRange.x;
			int size = Math.abs(mz.xRange.y - mz.xRange.x);
			xx = mz.xRange.x + size/2;

			Entity s;
			if(  size < GameConstants.MIN_MOBZONE_SIZE_SPAWN_SHOP ) {
				s = makeShopKeeper(0,0);
			} else {
				float r = RNG.rng.nextFloat();
				if( r < GameConstants.SPAWN_SUMMONER_PROB ) {
					s = makeSummoner(0,0,mz);
				} else {
					s = makeShopKeeper(0,0);
				}
			}
			adjustToTile(s, xx, mz.height+1);
			
			i++;
		}
		
	}
	
	public Entity makePickup( float x, float y, PickupType pt ) {
		Entity e = new Entity();
		TransformComponent tc = (TransformComponent) e.addComponent(new TransformComponent());
		PhysicsComponent phc = (PhysicsComponent) e.addComponent(new PhysicsComponent());
		GraphicsComponent gc = (GraphicsComponent) e.addComponent(new GraphicsComponent());
		AnimationComponent ac = (AnimationComponent) e.addComponent(new AnimationComponent());
		phc.body = physics.createPickupBody(x, y, 5);
		PickupItemComponent pic = (PickupItemComponent) e.addComponent(new PickupItemComponent());
		pic.pickupType = pt;
		phc.body.setUserData(e);
		switch( pt ) {
		case HEALTH:
			gc.drawElement = Gfx.healthPickupSheet.get(0);
			gc.sprite = new Sprite(gc.drawElement);
			ac.currentAnim = Gfx.healthPickupAnim;
			break;
		case WINGS:
			gc.drawElement = Gfx.wingsPickupSheet.get(0);
			gc.sprite = new Sprite(gc.drawElement);
			ac.currentAnim = Gfx.wingsPickupAnim;
			break;
		case MONEY:
			gc.drawElement = Gfx.moneyPickupSheet.get(0);
			gc.sprite = new Sprite(gc.drawElement);
			ac.currentAnim = Gfx.moneyPickupAnim;
			break;
		case CROSS:
			gc.drawElement = Gfx.crossSheet.get(0);
			gc.sprite = new Sprite(gc.drawElement);
			ac.currentAnim = Gfx.crossAnim;
			break;
		}
		return engine.addEntity(e);
	}
	
	public void adjustToTile( Entity e, int tx, int ty ) {
		PhysicsComponent pc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		float dc = pc.boxOffset.y * -1f;
		TextureRegion tr = gc.drawElement;
		float dy = tr.getRegionHeight()/2 - dc + 1f;
		float fx = (tx+0.5f) * 10f * GameConfig.PIXELS_TO_METERS;
		float fy = (ty) * 10f * GameConfig.PIXELS_TO_METERS + dy * GameConfig.PIXELS_TO_METERS;
		pc.body.setTransform(fx, fy, 0);
	}
	
	public static TransformComponent playerPositionComponent;
	
	public void makePlayer(int x, int y) {
		player = new Entity();
		
		PhysicsComponent pc = (PhysicsComponent) player.addComponent(new PhysicsComponent());
		pc.body = physics.createPlayerBody(x, y);
		pc.body.setUserData(player);
		
		MashComponent mac = (MashComponent) player.addComponent(new MashComponent());
		
		InvincibilityComponent ic = (InvincibilityComponent) player.addComponent(new InvincibilityComponent());
		ic.invincibilityTime = GameConstants.INFINITE_HP_TIME;
		
		InfiniteFlyComponent ifc = (InfiniteFlyComponent) player.addComponent(new InfiniteFlyComponent());
		ifc.infiniteFlyTime = GameConstants.INFINITE_WINGS_TIME;
		
		GraphicsComponent gc = (GraphicsComponent) player.addComponent(new GraphicsComponent());
		gc.drawElement = Gfx.playerSheet.get(0);
		gc.sprite = new Sprite(gc.drawElement);
		
		playerPositionComponent = (TransformComponent) player.addComponent(new TransformComponent());
		playerPositionComponent.position = pc.body.getPosition();
		
		AnimationComponent ac = (AnimationComponent) player.addComponent(new AnimationComponent());
		ac.currentAnim = Gfx.playerWalk;

		PlayerComponent plc = (PlayerComponent) player.addComponent(new PlayerComponent());

		LinearVelocityComponent lvc = (LinearVelocityComponent) player.addComponent(new LinearVelocityComponent());		
		lvc.speed.set(0.5f,0.25f);
		lvc.cap.y = 2;
		lvc.doCap[1] = true;
		
		AttackComponent atc = (AttackComponent) player.addComponent(new AttackComponent());
		atc.attackCooldown = 2f;
		
		ShootComponent sc = (ShootComponent) player.addComponent(new ShootComponent());
		sc.bulletConfigs = bulletCfgs.playerDefaultBEList;
		
		addHealthDamage(player, GameConstants.PLAYER_HEALTH, GameConstants.PLAYER_PAIN_COOLDOWN);
		
		WingsComponent wc = (WingsComponent) player.addComponent(new WingsComponent());
		wc.maxCapacity = (int) GameConstants.PLAYER_FLY;
		wc.currentBoost = wc.maxCapacity;
		
		ActiveComponent actc = (ActiveComponent) player.addComponent(new ActiveComponent());
		actc.isActive = true;
		
		WeaponComponent wep = (WeaponComponent) player.addComponent(new WeaponComponent());
		wep.weaponModel = BulletConfigs.defaultGun;
		
		PickupEffectComponent pec = (PickupEffectComponent) player.addComponent(new PickupEffectComponent());
		MoneyComponent mc = (MoneyComponent) player.addComponent(new MoneyComponent());
		mc.currency = GameConstants.STARTING_MONEY;
		
		BuyerComponent bc = (BuyerComponent) player.addComponent(new BuyerComponent());

		CrossComponent cc = (CrossComponent) player.addComponent(new CrossComponent());
		cc.neededCrosses = GameConstants.NEEDED_CROSSES;
		
		engine.addEntity(player);
	}
	
	private void addHealthDamage(Entity e, float maxHP, float cooldownPain) {
		HealthComponent hc = (HealthComponent) e.addComponent(new HealthComponent());
		DamageComponent dc = (DamageComponent) e.addComponent(new DamageComponent());
		hc.currentHP = (int) maxHP;
		hc.maxHP = (int) maxHP;
		dc.painCooldown = cooldownPain;
	}
	
	public Entity makeShopKeeper( float x, float y ) {
		Entity e = new Entity();
		TransformComponent tc = (TransformComponent) e.addComponent(new TransformComponent());
		PhysicsComponent phc = (PhysicsComponent) e.addComponent(new PhysicsComponent());
		GraphicsComponent gc = (GraphicsComponent) e.addComponent(new GraphicsComponent());
		AnimationComponent ac = (AnimationComponent) e.addComponent(new AnimationComponent());
		phc.body = physics.createShopBody(x, y, 8, 15);
		phc.body.setUserData(e);
		phc.boxOffset.y = -1f;
		
		gc.drawElement = Gfx.shopSheet.get(0);
		gc.sprite = new Sprite(gc.drawElement);
		ac.currentAnim = Gfx.shopAnim;

		ShopComponent sc = (ShopComponent) e.addComponent(new ShopComponent());
		sc.vendingProduct = ShopConfig.chooseRandomProduct();
		sc.refillingCooldown = GameConstants.SHOP_REFILLING_TIME;

		VanishingComponent vc = (VanishingComponent) e.addComponent(new VanishingComponent());
		vc.timeToVanish = GameConstants.SHOP_VANISHING_TIME;
		vc.willTurn = RNG.rng.nextFloat() < GameConstants.SHOP_TURN_PROB;
		vc.isVanishing = false;
		
		return engine.addEntity(e);
	}
	
	public Entity makePortal( float x, float y ) {
		Entity e = new Entity();
		TransformComponent tc = (TransformComponent) e.addComponent(new TransformComponent());
		PhysicsComponent phc = (PhysicsComponent) e.addComponent(new PhysicsComponent());
		GraphicsComponent gc = (GraphicsComponent) e.addComponent(new GraphicsComponent());

		phc.body = physics.createPortalBody(x * GameConfig.PIXELS_TO_METERS, y * GameConfig.PIXELS_TO_METERS, 10, 10);
		phc.body.setUserData(e);
		//phc.boxOffset.y = -1f;
		
		gc.drawElement = Gfx.portalTexture;
		gc.sprite = new Sprite(gc.drawElement);
		gc.allowFlip = false;
		
		e.addComponent(new DebugComponent());
		return engine.addEntity(e);
	}


	public Entity makeBullet( float x, float y, float w, float h, Vector2 dir, boolean player, TextureRegion tr,
			float destructionTime, int power, boolean trespassingEnabled ) {
		Entity e = new Entity();

		BulletComponent bc = (BulletComponent) e.addComponent(new BulletComponent());
		bc.power = power;
		bc.trespassingEnabled = trespassingEnabled;
		
		GraphicsComponent gc = (GraphicsComponent) e.addComponent(new GraphicsComponent());
		gc.drawElement = tr;
		gc.sprite = new Sprite(gc.drawElement);
		gc.allowFlip = false;
		
		TransformComponent posc = (TransformComponent) e.addComponent(new TransformComponent());
		posc.position = new Vector2(x,y);
		posc.offset.x = 0;
		posc.offset.y = 0;
		PhysicsComponent phc = (PhysicsComponent) e.addComponent(new PhysicsComponent());
		short cat, mask, group;
		if( player ) {
			cat = CollisionLayers.CATEGORY_PLBULLETS;
			mask = CollisionLayers.MASK_PLBULLETS;
			group = CollisionLayers.GROUP_PLBULLETS;
		} else {
			cat = CollisionLayers.CATEGORY_ENBULLETS;
			mask = CollisionLayers.MASK_ENBULLETS;
			group = CollisionLayers.GROUP_ENBULLETS;
		}
		phc.body = physics.createBulletBody(x, y, w, h, cat, mask, group);
		phc.body.setTransform(x, y, (float) Math.toRadians(dir.angle()));
		phc.body.setUserData(e);
		
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.addComponent(new LinearVelocityComponent());		
		lvc.speed.set(0.5f,0);
		lvc.linearVelocity.set(dir);

		CountdownDestructionComponent cdc = (CountdownDestructionComponent) e.addComponent(new CountdownDestructionComponent());
		cdc.timeToLive = destructionTime;
		
		return e;
	}
	
	public Entity makeHorizontalBullet( float x, float y, float w, float h, float speed, boolean player, TextureRegion tr, boolean flipX, float dt, int power, boolean trespassingEnabled ) {
		Entity e = makeBullet(x, y, w, h, new Vector2(speed * (flipX ? -1 : 1), 0), player, tr, dt, power, trespassingEnabled);
		return e;
	}
	
	public Entity makeGroundExplosion(float x, float y, float power) {
		Entity e = new Entity();

		GraphicsComponent gc = (GraphicsComponent) e.addComponent(new GraphicsComponent());
		gc.drawElement = Gfx.groundExplosionSpritesheet.get(0);
		gc.sprite = new Sprite(gc.drawElement);
		
		TransformComponent posc = (TransformComponent) e.addComponent(new TransformComponent());
		posc.position = new Vector2(x,y);
		posc.offset.x = 0;
		posc.offset.y = 0;
		
		PhysicsComponent phc = (PhysicsComponent) e.addComponent(new PhysicsComponent());
		phc.body = physics.createGroundExplosionBody(x, y);
		phc.body.setUserData(e);
		
		AnimationComponent ac = (AnimationComponent) e.addComponent(new AnimationComponent());
		ac.currentAnim = Gfx.groundExplosion;
		
		CountdownDestructionComponent cdc = (CountdownDestructionComponent) e.addComponent(new CountdownDestructionComponent());
		cdc.timeToLive = 0.7f;
		
		BulletComponent bc = (BulletComponent) e.addComponent(new BulletComponent());
		bc.power = (int) power;
		bc.trespassingEnabled = true;
		
		engine.addEntity(e);
		return e;
	}
	
	public Entity makeEnemy( float x, float y, TextureRegion tr, float offw, float offh, boolean flying, int agentType ) {
		
		Entity e = new Entity();
		GraphicsComponent gc = (GraphicsComponent) e.addComponent(new GraphicsComponent());
		gc.drawElement = tr;
		gc.sprite = new Sprite(gc.drawElement);
		
		TransformComponent posc = (TransformComponent) e.addComponent(new TransformComponent());
		posc.position = new Vector2(x,y);
		posc.offset.x = 0;
		posc.offset.y = 0;

		PhysicsComponent phc = (PhysicsComponent) e.addComponent(new PhysicsComponent());
		phc.body = physics.createEnemyBody2(x, y, gc.sprite.getWidth()/2 + offw, gc.sprite.getHeight()/2 + offh );
		phc.body.setUserData(e);
		phc.body.setGravityScale(flying ? 0 : 1);
		phc.boxOffset.set(offw, offh);
		
		ActiveComponent actc = (ActiveComponent) e.addComponent(new ActiveComponent());
		AIAgentComponent enc = (AIAgentComponent) e.addComponent(new AIAgentComponent());
		enc.agentType = agentType;
		
		DropPickupComponent dpc = (DropPickupComponent) e.addComponent(new DropPickupComponent());
		dpc.probDrop = 1;
		dpc.probs[0] = 0.30f;
		dpc.probs[1] = 0.50f;
		dpc.probs[2] = 1f;

		return e;
	}
	
	public Entity letEnemyWalk( Entity e, float ttw, float ttr, float speed ) {
		WalkingComponent wc = (WalkingComponent) e.addComponent(new WalkingComponent());
		wc.isWalking = false;
		wc.timeToWalk = ttw;
		wc.timeToRest = ttr;
		wc.restingTimer = ttr;
		wc.walkingLeft = true;
		
		AIAgentInhibitWalkComponent inhibit = (AIAgentInhibitWalkComponent) e.addComponent(new AIAgentInhibitWalkComponent());
		inhibit.canWalk = true;
		
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.addComponent(new LinearVelocityComponent());
		lvc.speed.x = speed;
		
		return e;
	}
	
	public Entity makeEnemy( float x, float y, TextureRegion tr, float offw, float offh, int agentType) {
		Entity e = makeEnemy(x, y, tr, offw, offh, false, agentType);
		return e;
	}
	
	private Entity addEnemyAnimator(Entity e, Animation w, Animation s, Animation a) {
		return addEnemyAnimator(e, w, s, a, null);
	}
	
	public Entity addEnemyAnimator( Entity e, Animation walkAnim, Animation standAnim, Animation attackAnim, Animation prepareAnim ) {
		AIAgentAnimatorComponent eac = (AIAgentAnimatorComponent) e.addComponent(new AIAgentAnimatorComponent());
		eac.standAnim = standAnim;
		eac.walkAnim = walkAnim;
		eac.attackAnim = attackAnim;
		eac.prepareAnim = prepareAnim;
		AnimationComponent ac = (AnimationComponent) e.addComponent(new AnimationComponent());
		ac.currentAnim = standAnim;
		
		return e;
	}
	
	public Entity makeThreeHeaded( float x, float y ) {
		Entity e = makeEnemy(x,y,Gfx.threeHeadSheet.get(0), 0, -2, EnemyType.THREEHEADED);
		
		letEnemyWalk( e, 6, 0, 4 );
		addEnemyAnimator(e, Gfx.threeHeadWalk, Gfx.threeHeadStand, Gfx.threeHeadAttack, Gfx.threeHeadPrepare);
		
		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.attackCooldown = 0f;
		ac.attackTimer = 0.2f;
		ac.attackDuration = 0.3f;

		ShootComponent sc = (ShootComponent) e.addComponent(new ShootComponent());
		sc.bulletConfigs = bulletCfgs.threeHeadedBEList;
		sc.horizontal = true;
		
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 6f;

		AIAgentAttackPreparationComponent aipac = (AIAgentAttackPreparationComponent) e.addComponent(new AIAgentAttackPreparationComponent());
		aipac.timeToPrepare = 0.5f;
		aipac.preparingTimer = 0.5f;

		StrikeAttackComponent sac = (StrikeAttackComponent) e.addComponent(new StrikeAttackComponent());
		sac.strikeNum = 3;
		sac.strikeCooldown = 0.8f;
		
		addHealthDamage(e, GameConstants.THREEHEADED_HP, GameConstants.ENEMY_PAIN_COOLDOWN);
		
		return engine.addEntity(e);
	}
	
	public Entity makeRunner(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.runnerSheet.get(0), 0, 0, EnemyType.RUNNER);
		letEnemyWalk( e, 10, 0, 6 + RNG.rng.nextFloat()*2 );
		addEnemyAnimator(e, Gfx.runnerWalk, Gfx.runnerStand, Gfx.runnerAttack, Gfx.runnerPrepare);
		
		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.attackCooldown = 0f;
		ac.attackTimer = 0.2f;
		ac.attackDuration = 0.3f;

		ShootComponent sc = (ShootComponent) e.addComponent(new ShootComponent());
		sc.bulletConfigs = bulletCfgs.runnerBEList;
		
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 5f;

		AIAgentAttackPreparationComponent aipac = (AIAgentAttackPreparationComponent) e.addComponent(new AIAgentAttackPreparationComponent());
		aipac.timeToPrepare = 0.5f;
		aipac.preparingTimer = 0.5f;

		StrikeAttackComponent sac = (StrikeAttackComponent) e.addComponent(new StrikeAttackComponent());
		sac.strikeNum = 5;
		sac.strikeCooldown = 0.1f;
		
		addHealthDamage(e, GameConstants.RUNNER_HP, GameConstants.ENEMY_PAIN_COOLDOWN);
		
		return engine.addEntity(e);
	}
	
	public Entity makeJumper(float x, float y) {
		
		Entity e = makeEnemy(x,y,Gfx.jumperSheet.get(2), 0, -14, EnemyType.JUMPER);
		this.addEnemyAnimator(e, Gfx.jumperWalk, Gfx.jumperStand, Gfx.jumperAttack, Gfx.jumperPrepare);
		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.attackCooldown = 0.01f;
		ac.nextAttackAvailable = 1f;
		ac.isAttacking = false;
		
		JumperAttackComponent jac = (JumperAttackComponent) e.addComponent(new JumperAttackComponent());
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 5.2f;
		
		ShootComponent sc = (ShootComponent) e.addComponent(new ShootComponent());
		sc.bulletConfigs = bulletCfgs.jumperBEList;
		
		StrikeAttackComponent sac = (StrikeAttackComponent) e.addComponent(new StrikeAttackComponent());
		sac.strikeNum = 5;
		sac.strikeCooldown = 0.14f;
		
		addHealthDamage(e, GameConstants.JUMPER_HP, GameConstants.ENEMY_PAIN_COOLDOWN);
		
		return engine.addEntity(e);
	}
	
	public void makeDemon(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.demonSheet.get(0), 0, 0, true, EnemyType.DEMON);
		AIWarpComponent aiwc = (AIWarpComponent) e.addComponent(new AIWarpComponent());
		HideComponent hc = (HideComponent) e.addComponent(new HideComponent());
		aiwc.hiddenTime = 1f;
		aiwc.unhiddenTime = 4f;
		aiwc.maxWarpDistance = 2f;
		aiwc.minKeepDistance = 3f;
		aiwc.hiddenTimer = 0f;
		aiwc.unhiddenTimer = 4f;
		addEnemyAnimator(e, Gfx.demonWalk, Gfx.demonStand, Gfx.demonAttack, Gfx.demonAttack);

		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.attackCooldown = 0f;
		ac.attackTimer = 0.2f;
		ac.attackDuration = 0.3f;

		ShootComponent sc = (ShootComponent) e.addComponent(new ShootComponent());
		sc.bulletConfigs = bulletCfgs.demonBEList;
		sc.horizontal = false;
		
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 3f;

		AIAgentAttackPreparationComponent aipac = (AIAgentAttackPreparationComponent) e.addComponent(new AIAgentAttackPreparationComponent());
		aipac.timeToPrepare = 0.5f;
		aipac.preparingTimer = 0.5f;

		StrikeAttackComponent sac = (StrikeAttackComponent) e.addComponent(new StrikeAttackComponent());
		sac.strikeNum = 6;
		sac.strikeCooldown = 0.1f;
		
		addHealthDamage(e, GameConstants.DEMON_HP, GameConstants.ENEMY_PAIN_COOLDOWN);
		
		SummonNearComponent snc = (SummonNearComponent) e.addComponent(new SummonNearComponent());
		snc.maxCreatures = 3;
		snc.summonProb[0] = 0.5f;
		snc.summonProb[1] = 1f;
		
		DropPickupComponent dpc = (DropPickupComponent) e.getComponent(DropPickupComponent.class);
		dpc.probs[0] = 0f;
		dpc.probs[1] = 0f;
		dpc.probs[2] = 0f;
		dpc.probs[3] = 1f;
		
		engine.addEntity(e);
	}
	
	public Entity makeEvilCherub(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.evilCherubSheet.get(0), -10, 0, true, EnemyType.CHERUB);
		AIAgentFlyingComponent aifc= (AIAgentFlyingComponent) e.addComponent(new AIAgentFlyingComponent());
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.addComponent(new LinearVelocityComponent());
		
		aifc.farForceCap += RNG.rng.nextInt(10);
		aifc.nearForceCap += RNG.rng.nextInt(10);
		aifc.repulsionFactor = 0.9f - RNG.rng.nextFloat()/5f;
		
		this.addEnemyAnimator(e, Gfx.cherubAnim, Gfx.cherubAnim, Gfx.cherubAnim, Gfx.cherubAnim);
		addHealthDamage(e, GameConstants.CHERUB_HP, GameConstants.ENEMY_PAIN_COOLDOWN);
		
		return engine.addEntity(e);
	}
	
	public Entity makeZombie(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.zombieSheet.get(0), 0, 0, EnemyType.ZOMBIE);
		letEnemyWalk( e, 6, 0, 1 );
		addEnemyAnimator(e, Gfx.zombieWalk, Gfx.zombieStand, Gfx.zombieAttack);
		addHealthDamage(e, GameConstants.ZOMBIE_HP, GameConstants.ENEMY_PAIN_COOLDOWN);
		return engine.addEntity(e);
	}
	
	public Entity makeSummoner(float x, float y, MobZone mz) {
		Entity e = makeEnemy(x,y,Gfx.summonerSheet.get(0), -5, -1, EnemyType.SUMMONER);

		SummonZoneComponent sc = (SummonZoneComponent) e.addComponent(new SummonZoneComponent());
		sc.summonProb[0] = 0.25f;
		sc.summonProb[1] = 0.5f;
		sc.summonProb[2] = 0.75f;
		sc.summonProb[3] = 1.f;
		sc.actingZone = mz;
		sc.maxCreatures = 8;

		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.nextAttackAvailable = 3f;
		ac.canAttack = false;
		ac.attackCooldown = 1f;
		
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 5f;
		pac.nextAttack = 1f;
		
		letEnemyWalk( e, 6, 0, 1 );
		addEnemyAnimator(e, Gfx.summonerWalk, Gfx.summonerStand, Gfx.summonerAttack, Gfx.summonerPrepare);
		
		AIAgentAttackPreparationComponent aipac = (AIAgentAttackPreparationComponent) e.addComponent(new AIAgentAttackPreparationComponent());
		aipac.timeToPrepare = 3;
		aipac.preparingTimer = 3;
		
		addHealthDamage(e, GameConstants.SUMMONER_HP, GameConstants.ENEMY_PAIN_COOLDOWN);
		
		DropPickupComponent dpc = (DropPickupComponent) e.getComponent(DropPickupComponent.class);
		dpc.probs[0] = 0f;
		dpc.probs[1] = 0f;
		dpc.probs[2] = 0f;
		dpc.probs[3] = 1f;
		
		
		return engine.addEntity(e);
	}
	
	public Entity makeCryingMask(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.cryingMaskSheet.get(0), 0, 0, true, EnemyType.MASK);
		AIAgentFlyingComponent aifc= (AIAgentFlyingComponent) e.addComponent(new AIAgentFlyingComponent());
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.addComponent(new LinearVelocityComponent());
		
		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.attackCooldown = 0f;
		ac.attackTimer = 0.2f;
		ac.attackDuration = 0.3f;
		
		addEnemyAnimator(e, Gfx.cryingMaskAnim, Gfx.cryingMaskAnim, Gfx.cryingMaskAnim, Gfx.cryingMaskAnim);

		ShootComponent sc = (ShootComponent) e.addComponent(new ShootComponent());
		sc.bulletConfigs = bulletCfgs.maskBEList;
		
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 8f;

		AIAgentAttackPreparationComponent aipac = (AIAgentAttackPreparationComponent) e.addComponent(new AIAgentAttackPreparationComponent());
		aipac.timeToPrepare = 0.5f;
		aipac.preparingTimer = 0.5f;

		StrikeAttackComponent sac = (StrikeAttackComponent) e.addComponent(new StrikeAttackComponent());
		sac.strikeNum = 3;
		sac.strikeCooldown = 0.5f;

		addHealthDamage(e, GameConstants.MASK_HP, GameConstants.ENEMY_PAIN_COOLDOWN);
		
		return engine.addEntity(e);
	}
	
	public void setCam() {
		cam.position.x = playerPositionComponent.position.x;
		cam.position.y = playerPositionComponent.position.y;
	}
	
	public void step() {
		engine.step();
	}
	
	public void render() {
		engine.render();
	}

	public Entity addToEngine(Entity e) {
		return engine.addEntity(e);
	}

	public Entity getPlayer() {
		return player;
	}

	public boolean isPlayerDead() {
		return player.isDead;
	}
	
}
