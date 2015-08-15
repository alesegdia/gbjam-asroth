package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.HideComponent;

public class HideSystem extends EntitySystem {

	public HideSystem() {
		super(HideComponent.class, GraphicsComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		HideComponent hc = (HideComponent) e.getComponent(HideComponent.class);
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		
		if( hc.isHidden ) {
			gc.alpha = 0.1f;
		} else {
			gc.alpha = 1f;
		}
	}

}
