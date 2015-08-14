package com.alesegdia.asroth.game;

import java.util.ArrayList;
import java.util.List;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.ActiveComponent;
import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.CountdownDestructionComponent;
import com.alesegdia.asroth.components.AIAgentAttackPreparationComponent;
import com.alesegdia.asroth.components.AIAgentAnimatorComponent;
import com.alesegdia.asroth.components.AIAgentComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.AIAgentPeriodicAutoAttackComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.PlayerComponent;
import com.alesegdia.asroth.components.PositionComponent;
import com.alesegdia.asroth.components.ShootComponent;
import com.alesegdia.asroth.components.SummonComponent;
import com.alesegdia.asroth.components.WalkingComponent;
import com.alesegdia.asroth.ecs.Engine;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.physics.CollisionLayers;
import com.alesegdia.asroth.physics.Physics;
import com.alesegdia.asroth.systems.AnimationSystem;
import com.alesegdia.asroth.systems.AttackTriggeringSystem;
import com.alesegdia.asroth.systems.CountdownDestructionSystem;
import com.alesegdia.asroth.systems.DrawingSystem;
import com.alesegdia.asroth.systems.AIAgentAnimationSystem;
import com.alesegdia.asroth.systems.FarDeactivationSystem;
import com.alesegdia.asroth.systems.FlipSystem;
import com.alesegdia.asroth.systems.HumanControllerSystem;
import com.alesegdia.asroth.systems.MovementSystem;
import com.alesegdia.asroth.systems.HorizontalShootingSystem;
import com.alesegdia.asroth.systems.AIAgentPeriodicAttackSystem;
import com.alesegdia.asroth.systems.AIAgentPrepareAttackSystem;
import com.alesegdia.asroth.systems.SummoningSystem;
import com.alesegdia.asroth.systems.UpdatePhysicsSystem;
import com.alesegdia.asroth.systems.AIAgentWalkingSystem;
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
	
	private List<Vector2> threeHeadedOrigins = new ArrayList<Vector2>();
	
	public int getNumEntities() {
		return engine.getNumEntities();
	}
	
	public GameWorld( Physics physics, SpriteBatch batch, Camera cam, TileMap tm ) {
		this.physics = physics;
		this.cam = cam;
		engine = new Engine();
		engine.addSystem(new HumanControllerSystem());
		engine.addSystem(new AnimationSystem());
		//engine.addSystem(new FarDeactivationSystem());
		
		engine.addSystem(new AIAgentWalkingSystem());

		engine.addSystem(new AIAgentAnimationSystem());

		engine.addSystem(new AIAgentPeriodicAttackSystem());
		engine.addSystem(new AIAgentPrepareAttackSystem());
		engine.addSystem(new AttackTriggeringSystem());

		engine.addSystem(new SummoningSystem());
		engine.addSystem(new HorizontalShootingSystem());

		engine.addSystem(new UpdatePhysicsSystem());
		engine.addSystem(new CountdownDestructionSystem());
		engine.addSystem(new MovementSystem());
		engine.addSystem(new FlipSystem());
		engine.addSystem(new DrawingSystem(batch), true);
		engine.addSystem(physics.physicsSystem);
		
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
		this.threeHeadedOrigins.add(new Vector2(0.5f,-0.35f));
		this.threeHeadedOrigins.add(new Vector2(0.5f,0.3f));
		this.threeHeadedOrigins.add(new Vector2(0.5f,0.75f));
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
	
	public static PositionComponent playerPositionComponent;
	
	public void makePlayer(int x, int y) {
		Entity player = new Entity();
		
		PhysicsComponent pc = (PhysicsComponent) player.addComponent(new PhysicsComponent());
		pc.body = physics.createPlayerBody(x, y);
		pc.body.setUserData(player);
		
		GraphicsComponent gc = (GraphicsComponent) player.addComponent(new GraphicsComponent());
		gc.drawElement = Gfx.playerSheet.get(0);
		gc.sprite = new Sprite(gc.drawElement);
		
		playerPositionComponent = (PositionComponent) player.addComponent(new PositionComponent());
		playerPositionComponent.position = pc.body.getPosition();
		
		AnimationComponent ac = (AnimationComponent) player.addComponent(new AnimationComponent());
		ac.currentAnim = Gfx.playerWalk;

		PlayerComponent plc = (PlayerComponent) player.addComponent(new PlayerComponent());

		LinearVelocityComponent lvc = (LinearVelocityComponent) player.addComponent(new LinearVelocityComponent());		
		lvc.speed.set(0.5f,0.25f);
		lvc.cap.y = 2;
		lvc.doCap[1] = true;
		
		engine.addEntity(player);
	}
	
	public Entity makePlayerBullet( float x, float y, boolean faceLeft ) {
		Entity e = new Entity();
		
		GraphicsComponent gc = (GraphicsComponent) e.addComponent(new GraphicsComponent());
		gc.drawElement = Gfx.playerBulletTexture;
		gc.sprite = new Sprite(gc.drawElement);
		
		PositionComponent posc = (PositionComponent) e.addComponent(new PositionComponent());
		posc.position = new Vector2(x,y);
		posc.offset.x = 0;
		posc.offset.y = 0;

		PhysicsComponent phc = (PhysicsComponent) e.addComponent(new PhysicsComponent());
		phc.body = physics.createPlayerBulletBody(x, y);
		phc.body.setUserData(e);
		
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.addComponent(new LinearVelocityComponent());		
		lvc.speed.set(0.5f,0);
		lvc.linearVelocity.x = 10 * ((faceLeft) ? -1 : 1);
		gc.flipX = faceLeft;

		engine.addEntity(e);
		return e;
	}
	
	public Entity makeGroundExplosion(float x, float y) {
		Entity e = new Entity();

		GraphicsComponent gc = (GraphicsComponent) e.addComponent(new GraphicsComponent());
		gc.drawElement = Gfx.groundExplosionSpritesheet.get(0);
		gc.sprite = new Sprite(gc.drawElement);
		
		PositionComponent posc = (PositionComponent) e.addComponent(new PositionComponent());
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
		
		PositionComponent posc = (PositionComponent) e.addComponent(new PositionComponent());
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
		
		return e;
	}
	
	public Entity letEnemyWalk( Entity e, float ttw, float ttr, float speed ) {
		WalkingComponent wc = (WalkingComponent) e.addComponent(new WalkingComponent());
		wc.isWalking = false;
		wc.timeToWalk = ttw;
		wc.timeToRest = ttr;
		wc.restingTimer = ttr;
		wc.walkingLeft = true;
		
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.addComponent(new LinearVelocityComponent());
		lvc.speed.x = speed;
		
		return e;
	}
	
	public Entity makeEnemy( float x, float y, TextureRegion tr, float offw, float offh) {
		Entity e = makeEnemy(x, y, tr, offw, offh, false);
		return e;
	}
	
	public Entity addEnemyAnimator( Entity e, Animation walkAnim, Animation standAnim, Animation attackAnim ) {
		AIAgentAnimatorComponent eac = (AIAgentAnimatorComponent) e.addComponent(new AIAgentAnimatorComponent());
		eac.standAnim = standAnim;
		eac.walkAnim = walkAnim;
		eac.attackAnim = attackAnim;
		eac.prepareAnim = Gfx.summonerPrepare;
		AnimationComponent ac = (AnimationComponent) e.addComponent(new AnimationComponent());
		ac.currentAnim = standAnim;
		
		return e;
	}
	
	public Entity makeThreeHeaded( float x, float y ) {
		Entity e = makeEnemy(x,y,Gfx.threeHeadSheet.get(0), 0, -2);
		
		letEnemyWalk( e, 6, 3, 4 );
		addEnemyAnimator(e, Gfx.threeHeadWalk, Gfx.threeHeadStand, Gfx.threeHeadAttack);
		
		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.attackCooldown = 1f;

		ShootComponent sc = (ShootComponent) e.addComponent(new ShootComponent());
		sc.bulletOrigins = this.threeHeadedOrigins;

		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 3f;
		
		return engine.addEntity(e);
	}
	
	public Entity makeRunner(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.runnerSheet.get(0), 0, 0);
		letEnemyWalk( e, 10, 0, 6 + RNG.rng.nextFloat()*2 );
		addEnemyAnimator(e, Gfx.runnerWalk, Gfx.runnerStand, Gfx.runnerAttack);
		return engine.addEntity(e);
	}
	
	public Entity makeJumper(float x, float y) {
		return engine.addEntity(makeEnemy(x,y,Gfx.jumperSheet.get(2), 0, -14));
	}
	
	public void makeDemon(float x, float y) {
		engine.addEntity(makeEnemy(x,y,Gfx.demonSheet.get(0), 0, 0, true));
	}
	
	public void makeEvilCherub(float x, float y) {
		engine.addEntity(makeEnemy(x,y,Gfx.evilCherubSheet.get(0), -10, 0, true));
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
		sc.summonProb[2] = 1.f;
		sc.actingZone = mz;

		AttackComponent ac = (AttackComponent) e.addComponent(new AttackComponent());
		ac.nextAttackAvailable = 3f;
		ac.canAttack = false;
		ac.attackCooldown = 1f;
		
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.addComponent(new AIAgentPeriodicAutoAttackComponent());
		pac.attackCooldown = 5f;
		pac.nextAttack = 1f;
		
		letEnemyWalk( e, 6, 0, 1 );
		addEnemyAnimator(e, Gfx.summonerWalk, Gfx.summonerStand, Gfx.summonerAttack);
		
		AIAgentAttackPreparationComponent aipac = (AIAgentAttackPreparationComponent) e.addComponent(new AIAgentAttackPreparationComponent());
		aipac.timeToPrepare = 3;
		aipac.preparingTimer = 3;
		return engine.addEntity(e);
	}
	
	public void makeCryingMask(float x, float y) {
		engine.addEntity(makeEnemy(x,y,Gfx.cryingMaskSheet.get(0), 0, 0, true));
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
	
}
