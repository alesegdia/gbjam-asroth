package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.EnemyComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.WalkingComponent;
import com.alesegdia.asroth.components.ActiveComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class WalkingSystem extends EntitySystem {

	public WalkingSystem() {
		super(EnemyComponent.class, WalkingComponent.class,
				LinearVelocityComponent.class, ActiveComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		ActiveComponent actc = (ActiveComponent) e.getComponent(ActiveComponent.class);
		if( actc.isActive ) {
			WalkingComponent wc = (WalkingComponent) e.getComponent(WalkingComponent.class);
			LinearVelocityComponent lvc = (LinearVelocityComponent) e.getComponent(LinearVelocityComponent.class);
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
		}
	}

}
