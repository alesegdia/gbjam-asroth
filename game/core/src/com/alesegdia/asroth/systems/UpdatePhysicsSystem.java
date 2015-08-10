package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.PositionComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;

public class UpdatePhysicsSystem extends EntitySystem {

	public UpdatePhysicsSystem() {
		super(PositionComponent.class, PhysicsComponent.class);
	}
	
	@Override
	public void process(Entity e) {
		PhysicsComponent pc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		PositionComponent posc = (PositionComponent) e.getComponent(PositionComponent.class);
		posc.position.set(pc.body.getPosition());
		posc.position.x = Math.round(posc.position.x);
		posc.position.y = Math.round(posc.position.y);
		
	}
	
}
