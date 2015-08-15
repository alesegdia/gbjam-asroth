package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.components.AIAgentPeriodicAutoAttackComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameWorld;
import com.badlogic.gdx.Gdx;

public class AIAgentPeriodicAttackSystem extends EntitySystem {
	
	public AIAgentPeriodicAttackSystem() {
		super(AIAgentPeriodicAutoAttackComponent.class, AttackComponent.class, TransformComponent.class);
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);
		AIAgentPeriodicAutoAttackComponent pac = (AIAgentPeriodicAutoAttackComponent) e.getComponent(AIAgentPeriodicAutoAttackComponent.class);
		
		TransformComponent epc = (TransformComponent) e.getComponent(TransformComponent.class);
		TransformComponent plpc = GameWorld.instance.playerPositionComponent;
		
		if( Math.abs(epc.position.x - plpc.position.x) < 10 &&
			Math.abs(epc.position.y - plpc.position.y) < 10 ) {		
			pac.nextAttack -= Gdx.graphics.getDeltaTime();
			if( pac.nextAttack <= 0 ) {
				ac.wantToAttack = true;
				if( ac.attackedLastFrame ) {
					pac.nextAttack = pac.attackCooldown;
				}
			} else {
				ac.wantToAttack = false;
			}
		}
		//ac.wantToAttack = pac.nextAttack <= 0;
	}

}
