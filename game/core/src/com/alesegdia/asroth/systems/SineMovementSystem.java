package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;

public class SineMovementSystem extends EntitySystem {

	public SineMovementSystem() {
		super(SineMovementComponent.class, PhysicsComponent.class, TransformComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		SineMovementComponent smc = (SineMovementComponent) e.getComponent(SineMovementComponent.class);
		PhysicsComponent phc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		TransformComponent tc = (TransformComponent) e.getComponent(TransformComponent.class);
		System.out.println("IMSINE");
		smc.timer += Gdx.graphics.getDeltaTime();
		float sine = (float) (2f * Math.sin(smc.timer*15));
		float finalY = smc.baseY + sine;
		phc.body.setTransform(tc.position.x, finalY, 0);
	}

	
	
}
