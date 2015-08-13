package com.alesegdia.asroth.game;

import java.util.List;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.ActiveComponent;
import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.CountdownDestructionComponent;
import com.alesegdia.asroth.components.EnemyAnimatorComponent;
import com.alesegdia.asroth.components.EnemyComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.PlayerComponent;
import com.alesegdia.asroth.components.PositionComponent;
import com.alesegdia.asroth.components.WalkingComponent;
import com.alesegdia.asroth.ecs.Engine;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.physics.CollisionLayers;
import com.alesegdia.asroth.physics.Physics;
import com.alesegdia.asroth.systems.AnimationSystem;
import com.alesegdia.asroth.systems.CountdownDestructionSystem;
import com.alesegdia.asroth.systems.DrawingSystem;
import com.alesegdia.asroth.systems.EnemyAnimationSystem;
import com.alesegdia.asroth.systems.FarDeactivationSystem;
import com.alesegdia.asroth.systems.FlipSystem;
import com.alesegdia.asroth.systems.HumanControllerSystem;
import com.alesegdia.asroth.systems.MovementSystem;
import com.alesegdia.asroth.systems.UpdatePhysicsSystem;
import com.alesegdia.asroth.systems.WalkingSystem;
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
		engine.addSystem(new EnemyAnimationSystem());
		engine.addSystem(new WalkingSystem());
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
			
			makeThreeHeaded(
					(mz.xRange.x + 3) * 10f * GameConfig.PIXELS_TO_METERS,
					(mz.height + 3) * 10f * GameConfig.PIXELS_TO_METERS
				);


/*			physics.createRectBody(mz.xRange.x, mz.height,//tm.cols - (mz.xRange.x-1),y-1, // (mz.height-1),
					10, 10,
					//10 * GameConfig.PIXELS_TO_METERS, 10 * GameConfig.PIXELS_TO_METERS, 
					CollisionLayers.CATEGORY_ENEMYLIMIT,
					CollisionLayers.MASK_ENEMYLIMIT,
					CollisionLayers.GROUP_ENEMYLIMIT,
					false);
*/
		}
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
		playerPositionComponent.offset.x = 0;
		playerPositionComponent.offset.y = 0;
		//playerPositionComponent.offset.x = -11;
		//playerPositionComponent.offset.y = -11 + GameConfig.PIXELS_TO_METERS;
		
		AnimationComponent ac = (AnimationComponent) player.addComponent(new AnimationComponent());
		ac.currentAnim = Gfx.playerWalk;
		player.addComponent(new PlayerComponent());
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
		
		ActiveComponent actc = (ActiveComponent) e.addComponent(new ActiveComponent());
		
		EnemyComponent enc = (EnemyComponent) e.addComponent(new EnemyComponent());
		
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
		EnemyAnimatorComponent eac = (EnemyAnimatorComponent) e.addComponent(new EnemyAnimatorComponent());
		eac.standAnim = standAnim;
		eac.walkAnim = walkAnim;
		eac.attackAnim = attackAnim;
		AnimationComponent ac = (AnimationComponent) e.addComponent(new AnimationComponent());
		ac.currentAnim = standAnim;
		return e;
	}
	
	public void makeThreeHeaded( float x, float y ) {
		Entity e = makeEnemy(x,y,Gfx.threeHeadSheet.get(0), 0, -2);
		letEnemyWalk( e, 6, 3, 4 );
		addEnemyAnimator(e, Gfx.threeHeadWalk, Gfx.threeHeadStand, Gfx.threeHeadAttack);
		engine.addEntity(e);
	}
	
	public void makeRunner(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.runnerSheet.get(0), 0, 0);
		letEnemyWalk( e, 10, 0, 6 + RNG.rng.nextFloat()*2 );
		addEnemyAnimator(e, Gfx.runnerWalk, Gfx.runnerStand, Gfx.runnerAttack);
		engine.addEntity(e);
	}
	
	public void makeJumper(float x, float y) {
		engine.addEntity(makeEnemy(x,y,Gfx.jumperSheet.get(0), 0, -14));
	}
	
	public void makeDemon(float x, float y) {
		engine.addEntity(makeEnemy(x,y,Gfx.demonSheet.get(0), 0, 0, true));
	}
	
	public void makeEvilCherub(float x, float y) {
		engine.addEntity(makeEnemy(x,y,Gfx.evilCherubSheet.get(0), -10, 0, true));
	}
	
	public void makeZombie(float x, float y) {
		Entity e = makeEnemy(x,y,Gfx.zombieSheet.get(0), 0, 0);
		letEnemyWalk( e, 6, 0, 1 );
		addEnemyAnimator(e, Gfx.zombieWalk, Gfx.zombieStand, Gfx.zombieAttack);
		engine.addEntity(e);
	}
	
	public void makeSummoner(float x, float y) {
		engine.addEntity(makeEnemy(x,y,Gfx.summonerSheet.get(0), -5, -1));
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
