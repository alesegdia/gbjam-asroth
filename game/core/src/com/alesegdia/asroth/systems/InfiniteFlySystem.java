package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.InfiniteFlyComponent;
import com.alesegdia.asroth.components.WingsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;

public class InfiniteFlySystem extends EntitySystem {

	public InfiniteFlySystem() {
		super(InfiniteFlyComponent.class, WingsComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		InfiniteFlyComponent ifc = (InfiniteFlyComponent) e.getComponent(InfiniteFlyComponent.class);
		WingsComponent wc = (WingsComponent) e.getComponent(WingsComponent.class);
		if( ifc.timer > 0 ) {
			ifc.timer -= Gdx.graphics.getDeltaTime();
			wc.currentBoost = wc.maxCapacity;
		}
	}

}
