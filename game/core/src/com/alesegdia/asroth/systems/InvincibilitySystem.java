package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.HealthComponent;
import com.alesegdia.asroth.components.InvincibilityComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;

public class InvincibilitySystem extends EntitySystem {

	public InvincibilitySystem() {
		super(InvincibilityComponent.class, HealthComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		InvincibilityComponent ic = (InvincibilityComponent) e.getComponent(InvincibilityComponent.class);
		HealthComponent hc = (HealthComponent) e.getComponent(HealthComponent.class);
		if( ic.timer > 0 ) {
			ic.timer -= Gdx.graphics.getDeltaTime();
			hc.currentHP = hc.maxHP;
		}
	}

}
