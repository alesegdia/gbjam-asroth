package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameConfig;
import com.badlogic.gdx.Gdx;

public class AnimationSystem extends EntitySystem {

	public AnimationSystem () {
		super(AnimationComponent.class, GraphicsComponent.class);
	}
	
	@Override
	public void process(Entity e) {
		AnimationComponent ac = (AnimationComponent) e.getComponent(AnimationComponent.class);
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		ac.currentTime += Gdx.graphics.getDeltaTime();
		gc.drawElement = ac.currentAnim.getKeyFrame(ac.currentTime, true);
		gc.sprite.setRegion(gc.drawElement);
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
}
