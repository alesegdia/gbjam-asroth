package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.components.PickupEffectComponent;
import com.alesegdia.asroth.components.PickupItemComponent.PickupType;
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
		for( PickupType pt : pec.pickupsCollectedLastFrame ) {
			switch( pt ) {
			case HEALTH:
				HealthComponent hc = (HealthComponent) e.getComponent(HealthComponent.class);
				hc.currentHP++;
				break;
			case WINGS:
				WingsComponent wc = (WingsComponent) e.getComponent(WingsComponent.class);
				wc.currentBoost++;
				break;
			case MONEY:
				MoneyComponent mc = (MoneyComponent) e.getComponent(MoneyComponent.class);
				mc.currency++;
				break;
			}
		}
	}

}
