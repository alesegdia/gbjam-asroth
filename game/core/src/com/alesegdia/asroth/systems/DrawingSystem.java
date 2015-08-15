package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameConfig;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawingSystem extends EntitySystem {

	private SpriteBatch spriteBatch;

	public DrawingSystem( SpriteBatch spriteBatch ) {
		super(GraphicsComponent.class, TransformComponent.class);
		this.spriteBatch = spriteBatch;
	}
	
	@Override
	public void process(Entity e) {
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		TransformComponent pc = (TransformComponent) e.getComponent(TransformComponent.class);


		if( gc.allowFlip ) {
			if( gc.isFlipped && gc.flipX ) {
				gc.sprite.flip(true, false);
				gc.isFlipped = true;
			}
			
			if( !gc.isFlipped && !gc.flipX ) {
				gc.sprite.flip(true, false);
				gc.isFlipped = false;
			}
		}
		
		gc.sprite.setScale(GameConfig.PIXELS_TO_METERS);
		gc.sprite.setRotation(pc.angle);
		gc.sprite.setOriginCenter();
		gc.sprite.setPosition(pc.position.x - gc.sprite.getOriginX(), pc.position.y - gc.sprite.getOriginY());
		gc.sprite.draw(spriteBatch);
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
}
