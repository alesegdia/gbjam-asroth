package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.PeriodicAutoAttackComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;

public class PeriodicAttackSystem extends EntitySystem {
	
	public PeriodicAttackSystem() {
		super(PeriodicAutoAttackComponent.class, AttackComponent.class);
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);
		PeriodicAutoAttackComponent pac = (PeriodicAutoAttackComponent) e.getComponent(PeriodicAutoAttackComponent.class);
		
		pac.nextAttack -= Gdx.graphics.getDeltaTime();
		if( pac.nextAttack <= 0 ) {
			ac.wantToAttack = true;
			if( ac.attackedLastFrame ) {
				pac.nextAttack = pac.attackCooldown;
			}
		} else {
			ac.wantToAttack = false;
		}
		//ac.wantToAttack = pac.nextAttack <= 0;
	}

}
