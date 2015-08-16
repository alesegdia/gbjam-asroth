package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.WeaponComponent;
import com.alesegdia.asroth.components.ShootComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;

public class WeaponChangeSystem extends EntitySystem {

	public WeaponChangeSystem() {
		super(WeaponComponent.class, AttackComponent.class, ShootComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		WeaponComponent wep = (WeaponComponent) e.getComponent(WeaponComponent.class);
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);
		ShootComponent sc = (ShootComponent) e.getComponent(ShootComponent.class);
		ac.attackCooldown = wep.weaponModel.rate;
		sc.bulletConfigs = wep.weaponModel.bulletEntries;
	}

}
