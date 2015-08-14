package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AIAttackPreparationComponent;
import com.alesegdia.asroth.components.AIEnemyAnimatorComponent;
import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.EnemyComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;

public class AIPrepareAttackSystem extends EntitySystem {

	public AIPrepareAttackSystem () {
		super(AttackComponent.class, AIAttackPreparationComponent.class,
				EnemyComponent.class, AIEnemyAnimatorComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {

	}

	@Override
	public void process(Entity e) {
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);
		AIAttackPreparationComponent pac = (AIAttackPreparationComponent) e.getComponent(AIAttackPreparationComponent.class);
		EnemyComponent ec = (EnemyComponent) e.getComponent(EnemyComponent.class);
		AIEnemyAnimatorComponent eac = (AIEnemyAnimatorComponent) e.getComponent(AIEnemyAnimatorComponent.class);
		
		if( !ec.isPreparingAttack ) {
			pac.preparingTimer = pac.timeToPrepare;
		}
		pac.preparingTimer -= Gdx.graphics.getDeltaTime();

			if( ac.wantToAttack ) {
				// are we prepared yet?
				if( pac.preparingTimer < 0 ) {
					System.out.println("<" + Gdx.graphics.getFrameId() + " - " + pac.preparingTimer + " - PREPARED>");
					ac.wantToAttack = true;
				} else {
					System.out.println("<" + Gdx.graphics.getFrameId() + " - " + pac.preparingTimer + " - preparing...>");
					// wait for it until I'm prepared
					ac.wantToAttack = false;
					ec.isPreparingAttack = true;
				}
			} else {
				ec.isPreparingAttack = false;
				pac.preparingTimer = pac.timeToPrepare;
				System.out.println("<" + Gdx.graphics.getFrameId() + " - " + pac.preparingTimer + " - DONT WANT>");
			}
	}

}
