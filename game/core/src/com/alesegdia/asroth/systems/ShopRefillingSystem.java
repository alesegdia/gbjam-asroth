package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.ShopComponent;
import com.alesegdia.asroth.components.VanishingComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;

public class ShopRefillingSystem extends EntitySystem {

	public ShopRefillingSystem () {
		super(ShopComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		ShopComponent sc = (ShopComponent) e.getComponent(ShopComponent.class);
		VanishingComponent vc = (VanishingComponent) e.getComponent(VanishingComponent.class);
		if( sc.isSold && !vc.isVanishing ) {
			sc.refillingTimer -= Gdx.graphics.getDeltaTime();
		}
		if( sc.refillingTimer <= 0 ) {
			sc.isSold = false;
		}
	}

}
