package com.alesegdia.asroth.game;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.CountdownDestructionComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.PlayerComponent;
import com.alesegdia.asroth.components.PositionComponent;
import com.alesegdia.asroth.ecs.Engine;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.physics.Physics;
import com.alesegdia.asroth.systems.AnimationSystem;
import com.alesegdia.asroth.systems.CountdownDestructionSystem;
import com.alesegdia.asroth.systems.DrawingSystem;
import com.alesegdia.asroth.systems.FlipSystem;
import com.alesegdia.asroth.systems.HumanControllerSystem;
import com.alesegdia.asroth.systems.MovementSystem;
import com.alesegdia.asroth.systems.UpdatePhysicsSystem;
import com.alesegdia.platgen.map.TileMap;
import com.alesegdia.platgen.map.TileType;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameWorld {
	
	public static GameWorld instance;

	Engine engine;
	private Physics physics;
	private Camera cam;
	
	public GameWorld( Physics physics, SpriteBatch batch, Camera cam, TileMap tm ) {
		this.physics = physics;
		this.cam = cam;
		engine = new Engine();
		engine.addSystem(new HumanControllerSystem());
		engine.addSystem(new AnimationSystem());
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
	}
	
	PositionComponent playerPositionComponent;
	
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
