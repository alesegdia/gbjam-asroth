package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameConfig;

public class UpdatePhysicsSystem extends EntitySystem {

	public UpdatePhysicsSystem() {
		super(TransformComponent.class, PhysicsComponent.class);
	}
	
	@Override
	public void process(Entity e) {
		PhysicsComponent pc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		TransformComponent posc = (TransformComponent) e.getComponent(TransformComponent.class);
		posc.position.set(pc.body.getPosition());
		posc.position.x = (Math.round(posc.position.x*GameConfig.METERS_TO_PIXELS)/GameConfig.METERS_TO_PIXELS + posc.offset.x);
		posc.position.y = (Math.round(posc.position.y*GameConfig.METERS_TO_PIXELS)/GameConfig.METERS_TO_PIXELS + posc.offset.y);
		posc.angle = (float) Math.toDegrees(pc.body.getAngle());
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
}
