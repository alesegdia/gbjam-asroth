package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;

public class AttackTriggeringSystem extends EntitySystem {
	
	public AttackTriggeringSystem() {
		super(AttackComponent.class);
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);
		ac.nextAttackAvailable -= Gdx.graphics.getDeltaTime();

		if( ac.attackedLastFrame ) {
			// reset attack
			ac.nextAttackAvailable = ac.attackCooldown;
			ac.attackedLastFrame = false;
		}
		
		ac.canAttack = ac.nextAttackAvailable <= 0;
		
		
		ac.doAttack = ac.canAttack && ac.wantToAttack;
		//ac.wantToAttack = false;

	}

}
