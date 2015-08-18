package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.HealthComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;

public class FallingSystem extends EntitySystem {

	public FallingSystem() {
		super(TransformComponent.class, HealthComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		TransformComponent tc = (TransformComponent) e.getComponent(TransformComponent.class);
		if( tc.position.y < -30f ) {
			e.isDead = true;
		}
	}

}
