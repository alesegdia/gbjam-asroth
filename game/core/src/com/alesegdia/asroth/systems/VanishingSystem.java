package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameWorld;
import com.badlogic.gdx.Gdx;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.components.VanishingComponent;
import com.alesegdia.asroth.components.TransformComponent;;

public class VanishingSystem extends EntitySystem {

	public VanishingSystem() {
		super(VanishingComponent.class, TransformComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		VanishingComponent vc = (VanishingComponent) e.getComponent(VanishingComponent.class);
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);

		if( vc.isVanishing ) {
			vc.vanishTimer -= Gdx.graphics.getDeltaTime();
			if( vc.vanishTimer > 0 ) {
				float s = (float) Math.sin(vc.vanishTimer*25);
				if( s > 0 ) {
					gc.alpha = 1f;
				} else {
					gc.alpha = 0f;
				}
			} else {
				e.isDead = true;
				if( vc.willTurn ) {
					TransformComponent tc = (TransformComponent) e.getComponent(TransformComponent.class);
					GameWorld.instance.makeDemon(tc.position.x, tc.position.y+1f);
				}
			}

		}
	}

}
