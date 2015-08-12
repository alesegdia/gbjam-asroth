package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;

public class FlipSystem extends EntitySystem {

	public FlipSystem() {
		super(GraphicsComponent.class, PhysicsComponent.class);
	}
	
	@Override
	public void process(Entity e) {
		PhysicsComponent phc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		
		if( phc.body.getLinearVelocity().x > 0.01 ) {
			gc.flipX = false;
		} else if( phc.body.getLinearVelocity().x < -0.01 ) {
			gc.flipX = true;
		}
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
}
