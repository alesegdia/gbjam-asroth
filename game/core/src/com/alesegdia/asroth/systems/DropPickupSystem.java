package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.platgen.util.RNG;
import com.alesegdia.asroth.components.DropPickupComponent;
import com.alesegdia.asroth.components.TransformComponent;

public class DropPickupSystem extends EntitySystem {

	public DropPickupSystem() {
		super(DropPickupComponent.class, TransformComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		DropPickupComponent dpc = (DropPickupComponent) e.getComponent(DropPickupComponent.class);
		if( e.isDead ) {
			if( RNG.rng.nextFloat() > dpc.probDrop ) {
				int i = 0;
				float dice = RNG.rng.nextFloat();
				while( i < 3 ) {
					if( dice < dpc.probs[i] ) {
						break;
					}
				}
				if( i == 0 ) {
					// CREATE HEALTH PICKUP ENTITY
				} else if( i == 1 ) {
					// CREATE WINGS PICKUP ENTITY
				} else if( i == 2 ) {
					// CREATE MONEY PICKUP ENTITY
				}
			}
		}
	}

}
