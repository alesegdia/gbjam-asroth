package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.components.DebugComponent;

public class DebugSystem extends EntitySystem {

	public DebugSystem() {
		super(DebugComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		System.out.println(e.name);
	}

}
