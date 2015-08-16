package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AIAgentComponent;
import com.alesegdia.asroth.components.AIAgentFlyingComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameWorld;
import com.badlogic.gdx.math.Vector2;

public class AIAgentFlyingSystem extends EntitySystem {

	public AIAgentFlyingSystem() {
		super(AIAgentFlyingComponent.class, LinearVelocityComponent.class, TransformComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
	float cap(float x, float max) {
		int s = x >= 0 ? 1 : -1;
		if( Math.abs(x) > max ) return max * s;
		else return x;
	}

	@Override
	public void process(Entity e) {
		TransformComponent plpc = GameWorld.playerPositionComponent;
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.getComponent(LinearVelocityComponent.class);
		AIAgentFlyingComponent aifc = (AIAgentFlyingComponent) e.getComponent(AIAgentFlyingComponent.class);
		TransformComponent pc = (TransformComponent) e.getComponent(TransformComponent.class);
		AIAgentComponent aiac = (AIAgentComponent) e.getComponent(AIAgentComponent.class);
		
		if( !aiac.isAttacking ) {
			Vector2 plpos = new Vector2(plpc.position);
			plpos.y += 2;
			plpos.sub(pc.position);
			float mod = plpos.len();
			
			float forceCap = 0;
			if( mod > 5 ) {
				forceCap = aifc.farForceCap;
			} else {
				forceCap = aifc.nearForceCap;
			}
			plpos.nor();
			aifc.force = aifc.force.add(plpos);
			aifc.force.x = cap(aifc.force.x, forceCap);
			aifc.force.y = cap(aifc.force.y, forceCap);
			
			Vector2 attraction = new Vector2(aifc.force);
			Vector2 repulsion = new Vector2(aifc.force);
			repulsion.scl(-aifc.repulsionFactor);
			attraction.add(repulsion);
			
			lvc.linearVelocity.set(attraction);
			//lvc.linearVelocity.x = cap(lvc.linearVelocity.x, 2);
			//lvc.linearVelocity.y = cap(lvc.linearVelocity.y, 2);
		} else {
			lvc.linearVelocity.x = 0;
			lvc.linearVelocity.y = 0;
		}
	}

	
}
