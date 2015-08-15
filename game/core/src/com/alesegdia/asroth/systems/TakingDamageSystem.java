package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.DamageComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;

public class TakingDamageSystem extends EntitySystem {

	public TakingDamageSystem() {
		super(DamageComponent.class, GraphicsComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		
	}

	@Override
	public void process(Entity e) {
		DamageComponent dc = (DamageComponent) e.getComponent(DamageComponent.class);
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		
		if( dc.painTimer > 0 ) {
			float s = (float) Math.sin(dc.painTimer*50);
			if( s > 0 ) {
				gc.alpha = 1f;
			} else {
				gc.alpha = 0f;
			}
		} else {
			gc.alpha = 1f;
		}
	}
}
