package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.JumperAttackComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameWorld;

public class JumperAttackSystem extends EntitySystem {

	public JumperAttackSystem() {
		super(AttackComponent.class, JumperAttackComponent.class, PhysicsComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);
		JumperAttackComponent jac = (JumperAttackComponent) e.getComponent(JumperAttackComponent.class);
		PhysicsComponent pc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		TransformComponent posc = (TransformComponent) e.getComponent(TransformComponent.class);
		TransformComponent pposc = GameWorld.instance.playerPositionComponent;
		
		
		//ac.isAttacking = Math.abs(pc.body.getLinearVelocity().len()) > 0.1f;

		float xspeed = (posc.position.x-pposc.position.x > 0 ? -1 : 1) * 2;

		System.out.println(Math.abs(pc.body.getLinearVelocity().y));
		if( Math.abs(pc.body.getLinearVelocity().y) > 0.3f ) {
			float dx = Math.abs(posc.position.x - pposc.position.x);
			if( dx > 1 ) {
				pc.body.setLinearVelocity(xspeed, pc.body.getLinearVelocity().y);
			}
		} else {
			pc.body.setLinearVelocity(0, pc.body.getLinearVelocity().y);			
		}
		
		if( ac.doAttack && Math.abs(pc.body.getLinearVelocity().len()) < 0.001 ) {
			ac.attackedLastFrame = true;
			pc.body.setLinearVelocity(xspeed,10);
		}
	}

}
