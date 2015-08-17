package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.InfiniteFlyComponent;
import com.alesegdia.asroth.components.InvincibilityComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;

public class EnhancedSystem extends EntitySystem {

	public EnhancedSystem() {
		super(InfiniteFlyComponent.class, InvincibilityComponent.class, GraphicsComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		InfiniteFlyComponent ifc = (InfiniteFlyComponent) e.getComponent(InfiniteFlyComponent.class);
		InvincibilityComponent ic = (InvincibilityComponent) e.getComponent(InvincibilityComponent.class);
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		if( ifc.timer > 0 || ic.timer > 0 ) {
			float biggest = ifc.timer > ic.timer ? ifc.timer : ic.timer;
			float s = (float) Math.sin(biggest*100);
			if( s > 0 ) {
				gc.alpha = 1f;
			} else {
				gc.alpha = 0f;
			}
		}
	}

}
