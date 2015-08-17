package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.components.PickupEffectComponent;
import com.alesegdia.asroth.components.PickupItemComponent;
import com.alesegdia.asroth.components.PickupItemComponent.PickupType;
import com.alesegdia.asroth.asset.Sfx;
import com.alesegdia.asroth.components.HealthComponent;
import com.alesegdia.asroth.components.WingsComponent;
import com.alesegdia.asroth.components.MoneyComponent;

public class PickupSystem extends EntitySystem {

	public PickupSystem () {
		super(PickupEffectComponent.class, WingsComponent.class, HealthComponent.class, MoneyComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		PickupEffectComponent pec = (PickupEffectComponent) e.getComponent(PickupEffectComponent.class);
		if( pec.pickupsCollectedLastFrame.size() > 0 ) {
			Sfx.Play(4);
		}
		for( Entity pt : pec.pickupsCollectedLastFrame ) {
			PickupItemComponent pic = (PickupItemComponent) pt.getComponent(PickupItemComponent.class);
			boolean wasPicked = false;
			
			switch( pic.pickupType ) {
			case HEALTH:
				HealthComponent hc = (HealthComponent) e.getComponent(HealthComponent.class);
				if( hc.currentHP < hc.maxHP ) {
					hc.currentHP++;
					wasPicked = true;
				}
				break;
			case WINGS:
				WingsComponent wc = (WingsComponent) e.getComponent(WingsComponent.class);
				if( wc.currentBoost < wc.maxCapacity ) {
					wc.currentBoost++;
					wasPicked = true;
				}
				break;
			case MONEY:
				MoneyComponent mc = (MoneyComponent) e.getComponent(MoneyComponent.class);
				wasPicked = true;
				mc.currency++;
				break;
			case CROSS:
				CrossComponent cc = (CrossComponent) e.getComponent(CrossComponent.class);
				cc.currentCrosses++;
				wasPicked = true;
				break;
			}
			pt.isDead = wasPicked;
		}
		pec.pickupsCollectedLastFrame.clear();
	}

}
