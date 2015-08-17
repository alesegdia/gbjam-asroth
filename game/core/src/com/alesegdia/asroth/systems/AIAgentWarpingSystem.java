package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AIWarpComponent;
import com.alesegdia.asroth.components.HideComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameWorld;
import com.alesegdia.platgen.util.RNG;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class AIAgentWarpingSystem extends EntitySystem {

	public AIAgentWarpingSystem() {
		super(AIWarpComponent.class, HideComponent.class, PhysicsComponent.class, TransformComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		AIWarpComponent wc = (AIWarpComponent) e.getComponent(AIWarpComponent.class);
		HideComponent hc = (HideComponent) e.getComponent(HideComponent.class);
		PhysicsComponent phc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		TransformComponent pposc = GameWorld.instance.playerPositionComponent;
		TransformComponent posc = (TransformComponent) e.getComponent(TransformComponent.class);

		Vector2 ppos = new Vector2(pposc.position);
		
		if( hc.isHidden ) {
			wc.hiddenTimer -= Gdx.graphics.getDeltaTime();
			if( wc.hiddenTimer <= 0 ) {
				// go unhid
				hc.isHidden = false;
				wc.unhiddenTimer = wc.unhiddenTime;

				ppos.sub(posc.position);
				float distToPlayer = ppos.len();
				
				if( distToPlayer < 20 ) {
					float warpingDistance = distToPlayer - wc.minKeepDistance;
					float angle = RNG.rng.nextInt(0,360);
					float portion = warpingDistance / distToPlayer;
					Vector2 displacement = new Vector2(pposc.position);
					displacement.sub(posc.position);
					displacement.scl(-(1f - portion));
					displacement.rotate(angle);
					displacement.add(pposc.position);
					phc.body.setTransform(displacement, 0);
				}
			}
		} else {
			wc.unhiddenTimer -= Gdx.graphics.getDeltaTime();
			if( wc.unhiddenTimer <= 0 ) {
				// go hid
				hc.isHidden = true;
				wc.hiddenTimer = wc.hiddenTime;
			}
		}
		
	}

	
	
}
