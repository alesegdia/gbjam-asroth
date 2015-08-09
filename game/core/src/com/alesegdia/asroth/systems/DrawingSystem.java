package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.PositionComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawingSystem extends EntitySystem {

	private SpriteBatch spriteBatch;

	public DrawingSystem( SpriteBatch spriteBatch ) {
		super(GraphicsComponent.class, PositionComponent.class);
		this.spriteBatch = spriteBatch;
	}
	
	@Override
	public void process(Entity e) {
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		PositionComponent pc = (PositionComponent) e.getComponent(PositionComponent.class);
		spriteBatch.draw(gc.drawElement, pc.position.x, pc.position.y);
	}
	
}
