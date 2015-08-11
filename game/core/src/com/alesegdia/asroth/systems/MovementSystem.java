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

		float prevLinearY = lvc.linearVelocity.y;
		
		// top
		if( prevLinearY > 0 && lvc.doCap[0] ) {
			lvc.linearVelocity.y = Math.min( lvc.cap.y, prevLinearY );
		}

		// bot
		if( prevLinearY < 0 && lvc.doCap[1] ) {
			lvc.linearVelocity.y = Math.max( -lvc.cap.y, prevLinearY );
		}

		// left
		if( lvc.doCap[2] ) {
			
		}

		// right
		if( lvc.doCap[3] ) {
			
		}

		pc.body.setLinearVelocity(lvc.linearVelocity.x, lvc.linearVelocity.y);
	}
	
}
