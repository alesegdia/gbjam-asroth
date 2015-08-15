package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AIAgentComponent;
import com.alesegdia.asroth.components.AIAgentInhibitWalkComponent;
import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.WalkingComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;

public class AIAgentIhibitWalkWhenAttackingSystem extends EntitySystem {

	public AIAgentIhibitWalkWhenAttackingSystem() {
		super(WalkingComponent.class, AIAgentInhibitWalkComponent.class, AttackComponent.class, AIAgentComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		AIAgentInhibitWalkComponent inhibit = (AIAgentInhibitWalkComponent) e.getComponent(AIAgentInhibitWalkComponent.class);
		AIAgentComponent ec = (AIAgentComponent) e.getComponent(AIAgentComponent.class);
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);

		if( ec.isPreparingAttack || ac.isAttacking ) {
			inhibit.canWalk = false;
		} else if( ec.isAttacking || ac.wantToAttack ) {
			inhibit.canWalk = false;
		} else {
			inhibit.canWalk = true;
		}

	}

	
}
