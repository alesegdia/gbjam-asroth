package com.alesegdia.asroth.game;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.PlayerComponent;
import com.alesegdia.asroth.components.PositionComponent;
import com.alesegdia.asroth.ecs.Engine;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.physics.Physics;
import com.alesegdia.asroth.systems.AnimationSystem;
import com.alesegdia.asroth.systems.DrawingSystem;
import com.alesegdia.asroth.systems.HumanControllerSystem;
import com.alesegdia.asroth.systems.MovementSystem;
import com.alesegdia.asroth.systems.UpdatePhysicsSystem;
import com.alesegdia.platgen.tilemap.TileMap;
import com.alesegdia.platgen.tilemap.TileType;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameWorld {

	Engine engine;
	private Physics physics;
	private Camera cam;
	
	public GameWorld( Physics physics, SpriteBatch batch, Camera cam, TileMap tm ) {
		this.physics = physics;
		this.cam = cam;
		engine = new Engine();
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new UpdatePhysicsSystem());
		engine.addSystem(new HumanControllerSystem());
		engine.addSystem(new MovementSystem());
		engine.addSystem(new DrawingSystem(batch), true);
		
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
		GraphicsComponent gc = (GraphicsComponent) player.addComponent(new GraphicsComponent());
		gc.drawElement = Gfx.playerTiles.get(0);
		gc.sprite = new Sprite(gc.drawElement);
		playerPositionComponent = (PositionComponent) player.addComponent(new PositionComponent());
		playerPositionComponent.position = pc.body.getPosition();
		playerPositionComponent.offset.x = -11;
		playerPositionComponent.offset.y = -11 + GameConfig.PIXELS_TO_METERS;
		
		AnimationComponent ac = (AnimationComponent) player.addComponent(new AnimationComponent());
		ac.currentAnim = Gfx.playerWalk;
		player.addComponent(new PlayerComponent());
		LinearVelocityComponent lvc = (LinearVelocityComponent) player.addComponent(new LinearVelocityComponent());		
		lvc.speed.set(0.25f,0.25f);
		engine.addEntity(player);
	}
	
	public void setCam() {
		cam.position.x = playerPositionComponent.position.x - playerPositionComponent.offset.x;
		cam.position.y = playerPositionComponent.position.y - playerPositionComponent.offset.y;
	}
	
	public void step() {
		engine.step();
	}
	
	public void render() {
		engine.render();
	}
	
}
