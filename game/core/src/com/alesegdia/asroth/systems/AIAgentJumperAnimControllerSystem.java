package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.JumperAttackComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;

public class AIAgentJumperAnimControllerSystem extends EntitySystem {

	public AIAgentJumperAnimControllerSystem() {
		super(JumperAttackComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		AnimationComponent ac = (AnimationComponent) e.getComponent(AnimationComponent.class);
		PhysicsComponent pc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
	
		if( !pc.grounded ) {
			ac.currentAnim = Gfx.jumperAttack;
		} else {
			ac.currentAnim = Gfx.jumperStand; 
		}
	}

}
