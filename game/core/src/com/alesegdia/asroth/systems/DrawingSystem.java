package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.PositionComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameConfig;
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

		//gc.sprite.setPosition(pc.position.x - 11, pc.position.y - 11 + GameConfig.PIXELS_TO_METERS);
		/*
		gc.sprite.setScale(GameConfig.PIXELS_TO_METERS);

		gc.sprite.setOrigin(0, 0);
		gc.sprite.flip(gc.flipX, false);

		gc.sprite.setOrigin(-gc.sprite.getWidth()/2 * GameConfig.PIXELS_TO_METERS,
				-gc.sprite.getHeight()/2 * GameConfig.PIXELS_TO_METERS);
		gc.sprite.setPosition(pc.position.x, pc.position.y);

		gc.sprite.draw(spriteBatch);
		*/

		float sX, cX;
		if( gc.flipX && gc.allowFlip ) {
			sX = -GameConfig.PIXELS_TO_METERS;
			cX = gc.drawElement.getRegionWidth()/2f * GameConfig.PIXELS_TO_METERS;
		} else {
			sX = GameConfig.PIXELS_TO_METERS;
			cX = -gc.drawElement.getRegionWidth()/2f * GameConfig.PIXELS_TO_METERS;
		}
		
		spriteBatch.setColor(1,1,1,gc.alpha);
		spriteBatch.draw(gc.drawElement, pc.position.x, pc.position.y,
				cX,
				-gc.drawElement.getRegionHeight()/2f * GameConfig.PIXELS_TO_METERS,
				gc.drawElement.getRegionWidth(), gc.drawElement.getRegionHeight(),
				sX, GameConfig.PIXELS_TO_METERS, 0);
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
}
