package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.asset.Sfx;
import com.alesegdia.asroth.components.DamageComponent;
import com.alesegdia.asroth.components.HealthComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;

public class PainSystem extends EntitySystem {

	public PainSystem() {
		super(HealthComponent.class, DamageComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		HealthComponent hc = (HealthComponent) e.getComponent(HealthComponent.class);
		DamageComponent dc = (DamageComponent) e.getComponent(DamageComponent.class);

		if( dc.painTimer <= 0 ) {
			if( dc.damageDealtLastFrame > 0 ) {
				Sfx.Play(dc.soundIndex);
				hc.currentHP -= dc.damageDealtLastFrame;
				dc.painTimer = dc.painCooldown;
			}
		} else {
			dc.painTimer -= Gdx.graphics.getDeltaTime();
		}
		
		if( hc.currentHP <= 0 ) {
			e.isDead = true;
		}
		
		dc.damageDealtLastFrame = 0;
	}

}
