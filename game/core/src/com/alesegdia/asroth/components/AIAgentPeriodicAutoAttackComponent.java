package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class AIAgentPeriodicAutoAttackComponent extends Component {

	public float attackCooldown;	// interval between will of attack. Obviously if
									// AttackComponent.attackCooldown > PeriodicAutoAttack.attackCooldown,
									// this one won't do nothing. It's useful to do periodic attacks until
									// some condition, so that the entity can attack just at the moment

	public float nextAttack; 		// time for next will of attack 
	
}
