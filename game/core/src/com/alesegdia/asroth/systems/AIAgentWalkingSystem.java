package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AIAgentComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.WalkingComponent;
import com.alesegdia.asroth.components.AIAgentInhibitWalkComponent;
import com.alesegdia.asroth.components.ActiveComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class AIAgentWalkingSystem extends EntitySystem {

	public AIAgentWalkingSystem() {
		super(AIAgentComponent.class, WalkingComponent.class,
				LinearVelocityComponent.class, ActiveComponent.class,
				AIAgentInhibitWalkComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		ActiveComponent actc = (ActiveComponent) e.getComponent(ActiveComponent.class);
		AIAgentInhibitWalkComponent inhibit = (AIAgentInhibitWalkComponent) e.getComponent(AIAgentInhibitWalkComponent.class);
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.getComponent(LinearVelocityComponent.class);
		if( inhibit.canWalk && actc.isActive ) {
			WalkingComponent wc = (WalkingComponent) e.getComponent(WalkingComponent.class);
			if( wc.isWalking ) {
				// update walking timer
				wc.walkingTimer -= Gdx.graphics.getDeltaTime();
				if( wc.walkingTimer < 0 ) {
					// go rest
					wc.isWalking = false;
					wc.restingTimer = wc.timeToRest;
				} else {
					// still waking
					lvc.linearVelocity.x = lvc.speed.x * (wc.walkingLeft ? -1 : 1);
				}
			} else {
				// update resting timer
				wc.restingTimer -= Gdx.graphics.getDeltaTime();
				if( wc.restingTimer < 0 ) {
					// enough resting!
					wc.isWalking = true;
					wc.walkingTimer = wc.timeToWalk;
				} else {
					// still resting
					lvc.linearVelocity.x = 0;
				}
			}
		} else {
			lvc.linearVelocity.x = 0;
		}
	}

}
