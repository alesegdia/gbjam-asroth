package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.AIAgentAnimatorComponent;
import com.alesegdia.asroth.components.AIAgentComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;

public class AIAgentAnimationSystem extends EntitySystem {
	
	public AIAgentAnimationSystem() {
		super(AIAgentComponent.class, AIAgentAnimatorComponent.class, AnimationComponent.class,
				PhysicsComponent.class, AttackComponent.class);
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		AIAgentComponent ec = (AIAgentComponent) e.getComponent(AIAgentComponent.class);
		AIAgentAnimatorComponent eac = (AIAgentAnimatorComponent) e.getComponent(AIAgentAnimatorComponent.class);
		AnimationComponent ac = (AnimationComponent) e.getComponent(AnimationComponent.class);
		PhysicsComponent phc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		AttackComponent atc = (AttackComponent) e.getComponent(AttackComponent.class);
		
		if( ec.isPreparingAttack || atc.isAttacking ) {
			ac.currentAnim = eac.prepareAnim;
		} else if( ec.isAttacking ) {
			ac.currentAnim = eac.attackAnim;
		} else if( Math.abs(phc.body.getLinearVelocity().x) > 0.01 ) {
			ac.currentAnim = eac.walkAnim;
		} else {
			ac.currentAnim = eac.standAnim;
		}
	}

}
