package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.math.Vector2;

public class MovementSystem extends EntitySystem {

	public MovementSystem() {
		super(LinearVelocityComponent.class, PhysicsComponent.class);
	}
	
	@Override
	public void process(Entity e) {
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.getComponent(LinearVelocityComponent.class);
		PhysicsComponent pc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		pc.body.setLinearVelocity(lvc.linearVelocity.x * lvc.speed.x, pc.body.getLinearVelocity().y);
	}
	
}
