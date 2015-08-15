package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.HideComponent;

public class HideSystem extends EntitySystem {

	public HideSystem() {
		super(HideComponent.class, GraphicsComponent.class, PhysicsComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		HideComponent hc = (HideComponent) e.getComponent(HideComponent.class);
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		PhysicsComponent pc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		
		if( hc.isHidden ) {
			System.out.println("ishid " + hc.isHidden);
			gc.alpha = 0f;
			pc.body.setActive(false);
		} else {
			pc.body.setActive(true);
		}
	}

}
