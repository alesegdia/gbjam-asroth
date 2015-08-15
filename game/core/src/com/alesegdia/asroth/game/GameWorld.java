package com.alesegdia.asroth.game;

import java.util.ArrayList;
import java.util.List;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.ActiveComponent;
import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.CountdownDestructionComponent;
import com.alesegdia.asroth.components.DamageComponent;
import com.alesegdia.asroth.components.AIAgentAttackPreparationComponent;
import com.alesegdia.asroth.components.AIAgentAnimatorComponent;
import com.alesegdia.asroth.components.AIAgentComponent;
import com.alesegdia.asroth.components.AIAgentFlyingComponent;
import com.alesegdia.asroth.components.AIAgentInhibitWalkComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.HealthComponent;
import com.alesegdia.asroth.components.HideComponent;
import com.alesegdia.asroth.components.JumperAttackComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.AIAgentPeriodicAutoAttackComponent;
import com.alesegdia.asroth.components.AIWarpComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.PlayerComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.components.ShootComponent;
import com.alesegdia.asroth.components.ShootComponent.BulletEntry;
import com.alesegdia.asroth.components.ShootComponent.BulletModel;
import com.alesegdia.asroth.components.StrikeAttackComponent;
import com.alesegdia.asroth.components.SummonComponent;
import com.alesegdia.asroth.components.WalkingComponent;
import com.alesegdia.asroth.components.WingsComponent;
import com.alesegdia.asroth.ecs.Engine;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.physics.CollisionLayers;
import com.alesegdia.asroth.physics.Physics;
import com.alesegdia.asroth.systems.AnimationSystem;
import com.alesegdia.asroth.systems.AttackTriggeringSystem;
import com.alesegdia.asroth.systems.CountdownDestructionSystem;
import com.alesegdia.asroth.systems.DrawingSystem;
import com.alesegdia.asroth.systems.AIAgentAnimationSystem;
import com.alesegdia.asroth.systems.AIAgentFacePlayerAttackSystem;
import com.alesegdia.asroth.systems.AIAgentFlyingSystem;
import com.alesegdia.asroth.systems.AIAgentIhibitWalkWhenAttackingSystem;
import com.alesegdia.asroth.systems.AIAgentJumperAnimControllerSystem;
import com.alesegdia.asroth.systems.FarDeactivationSystem;
import com.alesegdia.asroth.systems.FlipSystem;
import com.alesegdia.asroth.systems.HideSystem;
import com.alesegdia.asroth.systems.HumanControllerSystem;
import com.alesegdia.asroth.systems.JumperAttackSystem;
import com.alesegdia.asroth.systems.MovementSystem;
import com.alesegdia.asroth.systems.PainSystem;
import com.alesegdia.asroth.systems.AIAgentStrikeAttackSystem;
import com.alesegdia.asroth.systems.ShootingSystem;
import com.alesegdia.asroth.systems.AIAgentPeriodicAttackSystem;
import com.alesegdia.asroth.systems.AIAgentPrepareAttackSystem;
import com.alesegdia.asroth.systems.AIAgentSystem;
import com.alesegdia.asroth.systems.SummoningSystem;
import com.alesegdia.asroth.systems.TakingDamageSystem;
import com.alesegdia.asroth.systems.UpdatePhysicsSystem;
import com.alesegdia.asroth.systems.WingsRecoverySystem;
import com.alesegdia.asroth.systems.AIAgentWalkingSystem;
import com.alesegdia.asroth.systems.AIAgentWarpingSystem;
import com.alesegdia.platgen.map.MobZoneExtractor;
import com.alesegdia.platgen.map.MobZoneExtractor.MobZone;
import com.alesegdia.platgen.map.TileMap;
import com.alesegdia.platgen.map.TileType;
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

	public GameWorld( Physics physics, SpriteBatch batch, Camera cam, TileMap tm ) {
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

		engine.addSystem(new WingsRecoverySystem());
		engine.addSystem(new PainSystem());
		engine.addSystem(new AIAgentWarpingSystem());
		engine.addSystem(new SummoningSystem());
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

		engine.addSystem(new DrawingSystem(batch), true);
		engine.addSystem(physics.physicsSystem);
		
		bulletCfgs = new BulletConfigs();
		
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
		System.out.println(x);
		System.out.println(y);
		makePlayer(x*10, y*10);

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

			i++;
		}
		
	}
	
	public void adjustToTile( Entity e, int tx, int ty ) {
		PhysicsComponent pc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		float dc = pc.boxOffset.y * -1f;
		TextureRegion tr = gc.drawElement;
		float dy = tr.getRegionHeight()/2 - dc + 1f;
		System.out.println(dc);
		float fx = (tx) * 10f * GameConfig.PIXELS_TO_METERS;
		float fy = (ty) * 10f * GameConfig.PIXELS_TO_METERS + dy * GameConfig.PIXELS_TO_METERS;
		pc.body.setTransform(fx, fy, 0);
	}
	
	public static TransformComponent playerPositionComponent;
	
	public void makePlayer(int x, int y) {
		player = new Entity();
		
		PhysicsComponent pc = (PhysicsComponent) player.addComponent(new PhysicsComponent());
		pc.body = physics.createPlayerBody(x, y);
		pc.body.setUserData(player);
		
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
		sc.bulletOrigins = bulletCfgs.playerOrigins;
		
		addHealthDamage(player, 10f, 1f);
		
		WingsComponent wc = (WingsComponent) player.addComponent(new WingsComponent());
		
		ActiveComponent actc = (ActiveComponent) player.addComponent(new ActiveComponent());
		actc.isActive = true;
		
		engine.addEntity(player);
	}
	
	private void addHealthDamage(Entity e, float maxHP, float cooldownPain) {
		HealthComponent hc = (HealthComponent) e.addComponent(new HealthComponent());
		DamageComponent dc = (DamageComponent) e.addComponent(new DamageComponent());
		hc.currentHP = (int) maxHP;
		hc.maxHP = (int) maxHP;
		dc.painCooldown = cooldownPain;
	}

	public Entity makeBullet( float x, float y, float w, float h, Vector2 dir, boolean player, TextureRegion tr, float destructionTime ) {
		Entity e = new Entity();

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
	
	public Entity makeHorizontalBullet( float x, float y, float w, float h, float speed, boolean player, TextureRegion tr, boolean flipX, float dt ) {
		Entity e = makeBullet(x, y, w, h, new Vector2(speed * (flipX ? -1 : 1), 0), player, tr, dt);
		return e;
	}
	
	public Entity makePlayerBullet( float x, float y, boolean faceLeft ) {
		return engine.addEntity(makeHorizontalBullet( x, y, 5, 5, 10, true, Gfx.playerBulletTexture, faceLeft, 1f));
	}
	
	public Entity makeGroundExplosion(float x, float y) {
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
		
		engine.addEntity(e);
		return e;
	}
	
	public Entity makeEnemy( float x, float y, TextureRegion tr, float offw, float offh, boolean flying ) {
		
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
		addHealthDamage(e, 10, 1);

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
	
	public Entity makeEnemy( float x, float y, TextureRegion tr, float offw, float offh) {
		Entity e = makeEnemy(x, y, tr, offw, offh, false);
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
		Entity e = makeEnemy(x,y,Gfx.threeHeadSheet.get(0), 0, -2);
		
		letEnemyWalk( e, 6, 0, 4 );
		addEnemyAnimator(e, Gfx.threeHeadWalk, Gfx.threeHeadStand, Gfx.threeHeadAttack, Gfx.threeHeadPrepare);
		
		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.attackCooldown = 0f;
		ac.attackTimer = 0.2f;
		ac.attackDuration = 0.3f;

		ShootComponent sc = (ShootComponent) e.addComponent(new ShootComponent());
		sc.bulletOrigins = bulletCfgs.threeHeadedOrigins;
		sc.horizontal = true;
		
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 3f;

		AIAgentAttackPreparationComponent aipac = (AIAgentAttackPreparationComponent) e.addComponent(new AIAgentAttackPreparationComponent());
		aipac.timeToPrepare = 0.5f;
		aipac.preparingTimer = 0.5f;

		StrikeAttackComponent sac = (StrikeAttackComponent) e.addComponent(new StrikeAttackComponent());
		sac.strikeNum = 3;
		sac.strikeCooldown = 0.8f;
		
		return engine.addEntity(e);
	}
	
	public Entity makeRunner(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.runnerSheet.get(0), 0, 0);
		letEnemyWalk( e, 10, 0, 6 + RNG.rng.nextFloat()*2 );
		addEnemyAnimator(e, Gfx.runnerWalk, Gfx.runnerStand, Gfx.runnerAttack, Gfx.runnerPrepare);
		
		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.attackCooldown = 0f;
		ac.attackTimer = 0.2f;
		ac.attackDuration = 0.3f;

		ShootComponent sc = (ShootComponent) e.addComponent(new ShootComponent());
		sc.bulletOrigins = bulletCfgs.runnerOrigins;
		
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 3f;

		AIAgentAttackPreparationComponent aipac = (AIAgentAttackPreparationComponent) e.addComponent(new AIAgentAttackPreparationComponent());
		aipac.timeToPrepare = 0.5f;
		aipac.preparingTimer = 0.5f;

		/*
		StrikeAttackComponent sac = (StrikeAttackComponent) e.addComponent(new StrikeAttackComponent());
		sac.strikeNum = 5;
		sac.strikeCooldown = 0.1f;
*/
		
		return engine.addEntity(e);
	}
	
	public Entity makeJumper(float x, float y) {
		
		Entity e = makeEnemy(x,y,Gfx.jumperSheet.get(2), 0, -14);
		this.addEnemyAnimator(e, Gfx.jumperWalk, Gfx.jumperStand, Gfx.jumperAttack, Gfx.jumperPrepare);
		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.attackCooldown = 0.01f;
		ac.nextAttackAvailable = 1f;
		ac.isAttacking = false;
		
		JumperAttackComponent jac = (JumperAttackComponent) e.addComponent(new JumperAttackComponent());
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 5.2f;
		
		ShootComponent sc = (ShootComponent) e.addComponent(new ShootComponent());
		sc.bulletOrigins = bulletCfgs.jumperOrigins;
		
		StrikeAttackComponent sac = (StrikeAttackComponent) e.addComponent(new StrikeAttackComponent());
		sac.strikeNum = 10;
		sac.strikeCooldown = 0.07f;
		
		return engine.addEntity(e);
	}
	
	public void makeDemon(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.demonSheet.get(0), 0, 0, true);
		AIWarpComponent aiwc = (AIWarpComponent) e.addComponent(new AIWarpComponent());
		HideComponent hc = (HideComponent) e.addComponent(new HideComponent());
		aiwc.hiddenTime = 1f;
		aiwc.unhiddenTime = 4f;
		aiwc.maxWarpDistance = 2f;
		aiwc.minKeepDistance = 3f;
		addEnemyAnimator(e, Gfx.demonWalk, Gfx.demonStand, Gfx.demonAttack, Gfx.demonAttack);

		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.attackCooldown = 0f;
		ac.attackTimer = 0.2f;
		ac.attackDuration = 0.3f;

		ShootComponent sc = (ShootComponent) e.addComponent(new ShootComponent());
		sc.bulletOrigins = bulletCfgs.demonOrigins;
		sc.horizontal = false;
		
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 3f;

		AIAgentAttackPreparationComponent aipac = (AIAgentAttackPreparationComponent) e.addComponent(new AIAgentAttackPreparationComponent());
		aipac.timeToPrepare = 0.5f;
		aipac.preparingTimer = 0.5f;

		StrikeAttackComponent sac = (StrikeAttackComponent) e.addComponent(new StrikeAttackComponent());
		sac.strikeNum = 6;
		sac.strikeCooldown = 0.1f;
		
		engine.addEntity(e);
	}
	
	public void makeEvilCherub(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.evilCherubSheet.get(0), -10, 0, true);
		AIAgentFlyingComponent aifc= (AIAgentFlyingComponent) e.addComponent(new AIAgentFlyingComponent());
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.addComponent(new LinearVelocityComponent());
		engine.addEntity(e);
	}
	
	public Entity makeZombie(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.zombieSheet.get(0), 0, 0);
		letEnemyWalk( e, 6, 0, 1 );
		addEnemyAnimator(e, Gfx.zombieWalk, Gfx.zombieStand, Gfx.zombieAttack);
		return engine.addEntity(e);
	}
	
	public Entity makeSummoner(float x, float y, MobZone mz) {
		Entity e = makeEnemy(x,y,Gfx.summonerSheet.get(0), -5, -1);

		SummonComponent sc = (SummonComponent) e.addComponent(new SummonComponent());
		sc.summonProb[0] = 0.1f;
		sc.summonProb[1] = 0.2f;
		sc.summonProb[2] = 0.3f;
		sc.summonProb[3] = 1.f;
		sc.actingZone = mz;

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
		return engine.addEntity(e);
	}
	
	public void makeCryingMask(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.cryingMaskSheet.get(0), 0, 0, true);
		AIAgentFlyingComponent aifc= (AIAgentFlyingComponent) e.addComponent(new AIAgentFlyingComponent());
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.addComponent(new LinearVelocityComponent());
		
		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.attackCooldown = 0f;
		ac.attackTimer = 0.2f;
		ac.attackDuration = 0.3f;

		ShootComponent sc = (ShootComponent) e.addComponent(new ShootComponent());
		sc.bulletOrigins = bulletCfgs.threeHeadedOrigins;
		
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 3f;

		AIAgentAttackPreparationComponent aipac = (AIAgentAttackPreparationComponent) e.addComponent(new AIAgentAttackPreparationComponent());
		aipac.timeToPrepare = 0.5f;
		aipac.preparingTimer = 0.5f;

		StrikeAttackComponent sac = (StrikeAttackComponent) e.addComponent(new StrikeAttackComponent());
		sac.strikeNum = 3;
		sac.strikeCooldown = 0.8f;

		
		engine.addEntity(e);
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

	public void addToEngine(Entity e) {
		engine.addEntity(e);
	}

	public Entity getPlayer() {
		return player;
	}
	
}
