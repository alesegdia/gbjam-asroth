package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.PositionComponent;
import com.alesegdia.asroth.components.ShootComponent;
import com.alesegdia.asroth.components.ShootComponent.BulletModel;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameWorld;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class HorizontalShootingSystem extends EntitySystem {

	public HorizontalShootingSystem() {
		super(ShootComponent.class, PositionComponent.class, GraphicsComponent.class,
				AttackComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		ShootComponent sc = (ShootComponent) e.getComponent(ShootComponent.class);
		PositionComponent pc = (PositionComponent) e.getComponent(PositionComponent.class);
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		
		if( ac.doAttack ) {
			ac.attackedLastFrame = true;
			for( Vector2 origin : sc.bulletOrigins ) {
				// 	( float x, float y, float w, float h, float speed, boolean player, TextureRegion tr, boolean flipX ) {
				System.out.println("CHUT!");
				BulletModel bm = sc.bulletModel;
				GameWorld.instance.addToEngine(GameWorld.instance.makeHorizontalBullet(
							pc.position.x + origin.x * (gc.flipX?-1:1),
							pc.position.y + origin.y,
							bm.w, bm.h, 10, false, bm.tr, gc.flipX, bm.destructionTime));
			}
		}
	}

}
