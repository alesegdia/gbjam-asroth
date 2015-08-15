package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.StrikeAttackComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;

public class AIAgentStrikeAttackSystem extends EntitySystem {

	public AIAgentStrikeAttackSystem() {
		super(StrikeAttackComponent.class, AttackComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);
		StrikeAttackComponent sac = (StrikeAttackComponent) e.getComponent(StrikeAttackComponent.class);

		if( sac.strikeRunning ) {
			ac.wantToAttack = false;
			// did we finish the whole strike attack?
			if( sac.currentStrike >= sac.strikeNum ) {
				ac.isAttacking = true;
				ac.attackTimer = ac.attackDuration;
				sac.currentStrike = 0;
				sac.strikeRunning = false;
			} else {
				// still attacks to deploy!
				if( ac.attackTimer <= 0 ) {
					// ATTACK!
					sac.currentStrike++;
					ac.wantToAttack = true;
					ac.cooldownOverriden = true;
					ac.attackTimer = sac.strikeCooldown;
				}
			}
		} else if( ac.wantToAttack ) {
			// we can try to start a strike
			sac.strikeRunning = true;
			ac.wantToAttack = false;
		}
	}

}
