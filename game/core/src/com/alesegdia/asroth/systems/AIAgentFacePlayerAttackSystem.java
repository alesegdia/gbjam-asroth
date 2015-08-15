package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AIAgentComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameWorld;

public class AIAgentFacePlayerAttackSystem extends EntitySystem {

	public AIAgentFacePlayerAttackSystem() {
		super(GraphicsComponent.class, AIAgentComponent.class, TransformComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		AIAgentComponent aiac = (AIAgentComponent) e.getComponent(AIAgentComponent.class);
		TransformComponent epc = (TransformComponent) e.getComponent(TransformComponent.class);
		TransformComponent plpc = GameWorld.instance.playerPositionComponent;

		if( aiac.isAttacking || aiac.isPreparingAttack ) {
			float dx = epc.position.x - plpc.position.x;
			gc.flipX = dx < 0;
		}
		
	}

}
