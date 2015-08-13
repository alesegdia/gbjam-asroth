package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.AIEnemyAnimatorComponent;
import com.alesegdia.asroth.components.EnemyComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;

public class AIEnemyAnimationSystem extends EntitySystem {
	
	public AIEnemyAnimationSystem() {
		super(EnemyComponent.class, AIEnemyAnimatorComponent.class, AnimationComponent.class,
				LinearVelocityComponent.class);
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		EnemyComponent ec = (EnemyComponent) e.getComponent(EnemyComponent.class);
		AIEnemyAnimatorComponent eac = (AIEnemyAnimatorComponent) e.getComponent(AIEnemyAnimatorComponent.class);
		AnimationComponent ac = (AnimationComponent) e.getComponent(AnimationComponent.class);
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.getComponent(LinearVelocityComponent.class);
		
		if( ec.isAttacking ) {
			ac.currentAnim = eac.attackAnim;
		} else if( Math.abs(lvc.linearVelocity.x) > 0.01 ) {
			ac.currentAnim = eac.walkAnim;
		} else {
			ac.currentAnim = eac.standAnim;
		}
	}

}
