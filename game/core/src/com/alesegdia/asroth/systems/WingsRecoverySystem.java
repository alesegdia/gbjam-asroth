package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.WingsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;

public class WingsRecoverySystem extends EntitySystem {

	public WingsRecoverySystem() {
		super(WingsComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		WingsComponent wc = (WingsComponent) e.getComponent(WingsComponent.class);
		if( wc.isRecovering && wc.currentBoost < wc.maxCapacity ) {
			wc.nextRecovery -= Gdx.graphics.getDeltaTime();
			if( wc.nextRecovery <= 0 ) {
				wc.currentBoost++;
				wc.nextRecovery = wc.recoveryRate;
			}
		}
	}

}
