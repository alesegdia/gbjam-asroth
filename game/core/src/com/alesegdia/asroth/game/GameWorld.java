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
import com.alesegdia.asroth.systems.AnimationSystem;
import com.alesegdia.asroth.systems.DrawingSystem;
import com.alesegdia.asroth.systems.HumanControllerSystem;
import com.alesegdia.asroth.systems.MovementSystem;
import com.alesegdia.asroth.systems.UpdatePhysicsSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameWorld {

	Engine engine;
	private Physics physics;
	private Camera cam;
	
	public GameWorld( Physics physics, SpriteBatch batch, Camera cam ) {
		this.physics = physics;
		this.cam = cam;
		engine = new Engine();
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new UpdatePhysicsSystem());
		engine.addSystem(new HumanControllerSystem());
		engine.addSystem(new MovementSystem());
		engine.addSystem(new DrawingSystem(batch), true);
		makePlayer();
	}
	
	PositionComponent playerPositionComponent;
	
	public void makePlayer() {
		Entity player = new Entity();
		PhysicsComponent pc = (PhysicsComponent) player.addComponent(new PhysicsComponent());
		pc.body = physics.createCircleBody(50, 1000, 10, true);
		GraphicsComponent gc = (GraphicsComponent) player.addComponent(new GraphicsComponent());
		gc.drawElement = Gfx.playerTiles.get(0);
		playerPositionComponent = (PositionComponent) player.addComponent(new PositionComponent());
		playerPositionComponent.position = pc.body.getPosition();
		AnimationComponent ac = (AnimationComponent) player.addComponent(new AnimationComponent());
		ac.currentAnim = Gfx.playerWalk;
		player.addComponent(new PlayerComponent());
		LinearVelocityComponent lvc = (LinearVelocityComponent) player.addComponent(new LinearVelocityComponent());		
		lvc.speed.set(20,20);
		engine.addEntity(player);
	}
	
	public void step() {
		cam.position.x = playerPositionComponent.position.x;
		cam.position.y = playerPositionComponent.position.y;
		engine.step();
	}
	
	public void render() {
		engine.render();
	}
	
}
