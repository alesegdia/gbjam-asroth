package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AIAgentComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.components.ActiveComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameWorld;

public class FarDeactivationSystem extends EntitySystem {

	public FarDeactivationSystem () {
		super(AIAgentComponent.class, PhysicsComponent.class, TransformComponent.class,
				ActiveComponent.class, LinearVelocityComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		PhysicsComponent phc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		TransformComponent posc = (TransformComponent) e.getComponent(TransformComponent.class);
		ActiveComponent actc = (ActiveComponent) e.getComponent(ActiveComponent.class);
		TransformComponent plc = GameWorld.playerPositionComponent;
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.getComponent(LinearVelocityComponent.class);
		if( Math.abs(plc.position.x - posc.position.x) + Math.abs(plc.position.y - posc.position.y) > 40f ) {
			actc.isActive = false;
			phc.body.setLinearVelocity(0,0);
			phc.body.setGravityScale(0);
			phc.body.setSleepingAllowed(true);
			lvc.linearVelocity.x = 0;
			lvc.linearVelocity.y = 0;
		} else {
			actc.isActive = true;
			phc.body.setSleepingAllowed(false);
			phc.body.setGravityScale(1);
		}
	}

}
