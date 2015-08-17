package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameWorld;
import com.alesegdia.platgen.util.RNG;
import com.alesegdia.asroth.components.DropPickupComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.components.PickupItemComponent.PickupType;

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
		if( e.isDead && !dpc.hasDropped ) {
			dpc.hasDropped = true;

			TransformComponent tc = (TransformComponent) e.getComponent(TransformComponent.class);
			if( RNG.rng.nextFloat() < dpc.probDrop ) {
				int i = 0;
				float dice = RNG.rng.nextFloat();
				while( i < 4 ) {
					if( dice < dpc.probs[i] ) {
						break;
					}
					i++;
				}
				if( i == 0 ) {
					GameWorld.instance.makePickup(tc.position.x, tc.position.y, PickupType.HEALTH);
				} else if( i == 1 ) {
					GameWorld.instance.makePickup(tc.position.x, tc.position.y, PickupType.WINGS);
				} else if( i == 2 ) {
					GameWorld.instance.makePickup(tc.position.x, tc.position.y, PickupType.MONEY);
				} else if( i == 3 ) {
					GameWorld.instance.makePickup(tc.position.x, tc.position.y, PickupType.CROSS);
				}
			}
		}
	}

}
