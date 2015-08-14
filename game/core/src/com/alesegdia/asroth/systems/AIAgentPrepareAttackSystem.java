package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AIAgentAttackPreparationComponent;
import com.alesegdia.asroth.components.AIAgentAnimatorComponent;
import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.AIAgentComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;

public class AIAgentPrepareAttackSystem extends EntitySystem {

	public AIAgentPrepareAttackSystem () {
		super(AttackComponent.class, AIAgentAttackPreparationComponent.class,
				AIAgentComponent.class, AIAgentAnimatorComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {

	}

	@Override
	public void process(Entity e) {
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);
		AIAgentAttackPreparationComponent pac = (AIAgentAttackPreparationComponent) e.getComponent(AIAgentAttackPreparationComponent.class);
		AIAgentComponent ec = (AIAgentComponent) e.getComponent(AIAgentComponent.class);
		AIAgentAnimatorComponent eac = (AIAgentAnimatorComponent) e.getComponent(AIAgentAnimatorComponent.class);
		
		if( !ec.isPreparingAttack ) {
			pac.preparingTimer = pac.timeToPrepare;
		}
		pac.preparingTimer -= Gdx.graphics.getDeltaTime();

			if( ac.wantToAttack ) {
				// are we prepared yet?
				if( pac.preparingTimer < 0 ) {
					ac.wantToAttack = true;
				} else {
					// wait for it until I'm prepared
					ac.wantToAttack = false;
					ec.isPreparingAttack = true;
				}
			} else {
				ec.isPreparingAttack = false;
				pac.preparingTimer = pac.timeToPrepare;
			}
	}

}
