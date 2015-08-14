package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AIAgentComponent;
import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;

public class AIAgentSystem extends EntitySystem {

	public AIAgentSystem() {
		super(AIAgentComponent.class, AttackComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		AIAgentComponent aiac = (AIAgentComponent) e.getComponent(AIAgentComponent.class);
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);
		
		aiac.isAttacking = ac.isAttacking;
	}

}
