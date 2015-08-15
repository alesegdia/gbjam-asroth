package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.components.ShootComponent;
import com.alesegdia.asroth.components.ActiveComponent;
import com.alesegdia.asroth.components.ShootComponent.BulletModel;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameWorld;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ShootingSystem extends EntitySystem {

	public ShootingSystem() {
		super(ShootComponent.class, TransformComponent.class, GraphicsComponent.class,
				AttackComponent.class, ActiveComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		
	}

	@Override
	public void process(Entity e) {
		ShootComponent sc = (ShootComponent) e.getComponent(ShootComponent.class);
		TransformComponent pc = (TransformComponent) e.getComponent(TransformComponent.class);
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		ActiveComponent actc = (ActiveComponent) e.getComponent(ActiveComponent.class);
		
		if( ac.doAttack && actc.isActive ) {
			ac.attackedLastFrame = true;
			for( Vector2 origin : sc.bulletOrigins ) {
				// 	( float x, float y, float w, float h, float speed, boolean player, TextureRegion tr, boolean flipX ) {
				BulletModel bm = sc.bulletModel;
				if( sc.horizontal ) {
					GameWorld.instance.addToEngine(GameWorld.instance.makeHorizontalBullet(
								pc.position.x + origin.x * (gc.flipX?1:-1),
								pc.position.y + origin.y,
								bm.w, bm.h, 10, false, bm.tr, !gc.flipX, bm.destructionTime));
				} else {
					TransformComponent plpc = GameWorld.playerPositionComponent;
					System.out.println(gc.flipX);
					System.out.println(gc.isFlipped);
					Vector2 dir = new Vector2(plpc.position).sub(pc.position).nor().scl(bm.speed);					
					GameWorld.instance.addToEngine(GameWorld.instance.makeBullet(
							pc.position.x + origin.x * (gc.flipX?1:-1),
							pc.position.y + origin.y,
							bm.w, bm.h, dir, false, bm.tr, bm.destructionTime));
				}
			}
		}
	}

}
