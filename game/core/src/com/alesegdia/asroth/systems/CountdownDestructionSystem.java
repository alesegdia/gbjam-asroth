package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.CountdownDestructionComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;

public class CountdownDestructionSystem extends EntitySystem {

	public CountdownDestructionSystem() {
		super(CountdownDestructionComponent.class);
	}
	
	@Override
	public void process(Entity e) {
		CountdownDestructionComponent cdc = (CountdownDestructionComponent) e.getComponent(CountdownDestructionComponent.class);
		if( cdc.timeToLive <= 0.f ) {
			e.isDead = true;
		} else {
			cdc.timeToLive -= Gdx.graphics.getDeltaTime();
		}
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
}
