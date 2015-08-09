package com.alesegdia.asroth.game;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.PositionComponent;
import com.alesegdia.asroth.ecs.Engine;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.systems.AnimationSystem;
import com.alesegdia.asroth.systems.DrawingSystem;
import com.alesegdia.asroth.systems.UpdatePhysicsSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameWorld {

	Engine engine;
	private Physics physics;
	
	public GameWorld( Physics physics, SpriteBatch batch ) {
		this.physics = physics;
		engine = new Engine();
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new UpdatePhysicsSystem());
		engine.addSystem(new DrawingSystem(batch), true);
		makePlayer();
	}
	
	public void makePlayer() {
		Entity player = new Entity();
		PhysicsComponent pc = (PhysicsComponent) player.addComponent(new PhysicsComponent());
		pc.body = physics.createCircleBody(0, 0, 10, true);
		GraphicsComponent gc = (GraphicsComponent) player.addComponent(new GraphicsComponent());
		gc.drawElement = Gfx.playerTiles.get(0);
		PositionComponent posc = (PositionComponent) player.addComponent(new PositionComponent());
		posc.position = pc.body.getPosition();
		AnimationComponent ac = (AnimationComponent) player.addComponent(new AnimationComponent());
		ac.currentAnim = Gfx.playerWalk;
		
		engine.addEntity(player);
	}
	
	public void step() {
		engine.step();
	}
	
	public void render() {
		engine.render();
	}
	
}
