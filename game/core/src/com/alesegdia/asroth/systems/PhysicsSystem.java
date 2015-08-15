package com.alesegdia.asroth.systems;

import java.util.ArrayList;
import java.util.List;

import com.alesegdia.asroth.components.DamageComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.PlayerComponent;
import com.alesegdia.asroth.components.WalkingComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameWorld;
import com.alesegdia.asroth.physics.CollisionLayers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class PhysicsSystem extends EntitySystem implements ContactListener {

	abstract class ICollisionCallback {
		public abstract void startCollision(Body b1, Body b2, Vector2 normal);
		public abstract void endCollision(Body b1, Body b2);
		public short B1_CATEGORY;
		public short B2_CATEGORY;
		public void setCategories( short c1, short c2 ) {
			this.B1_CATEGORY = c1; this.B2_CATEGORY = c2;
		}
	}

	List<ICollisionCallback> callbacks = new ArrayList<ICollisionCallback>();
	
	public PhysicsSystem() {
		super(PhysicsComponent.class);
		
		callbacks.add(new ICollisionCallback() {
			{ setCategories( CollisionLayers.CATEGORY_ENEMYLOGIC, CollisionLayers.CATEGORY_ENEMYLIMIT ); }
			
			@Override
			public void startCollision(Body b1, Body b2, Vector2 normal) {
				Entity e = (Entity) b1.getUserData();
				WalkingComponent wc = (WalkingComponent) e.getComponent(WalkingComponent.class);
				if( wc != null ) {
					wc.walkingLeft = !wc.walkingLeft;
				}
			}

			@Override
			public void endCollision(Body b1, Body b2) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		callbacks.add(new ICollisionCallback() {
			{ setCategories( CollisionLayers.CATEGORY_ENEMYPHYSIC, CollisionLayers.CATEGORY_MAP ); }
			
			@Override
			public void startCollision(Body b1, Body b2, Vector2 normal) {
				Entity e = (Entity) b1.getUserData();
				PhysicsComponent pc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
				pc.grounded = true;
			}

			@Override
			public void endCollision(Body b1, Body b2) {
				Entity e = (Entity) b1.getUserData();
				PhysicsComponent pc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
				pc.grounded = false;
			}
			
		});
		
		callbacks.add(new ICollisionCallback() {
			{ setCategories( CollisionLayers.CATEGORY_PLBULLETS, CollisionLayers.CATEGORY_ENEMYLOGIC ); }

			@Override
			public void startCollision(Body plbullet, Body enemy, Vector2 normal) {
				Entity b = (Entity) plbullet.getUserData();
				Entity e = (Entity) enemy.getUserData();
				DamageComponent dc = (DamageComponent) e.getComponent(DamageComponent.class);
				dc.damageDealtLastFrame = 1;
				b.isDead = dc.painTimer <= 0;
			}

			@Override
			public void endCollision(Body b1, Body b2) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		callbacks.add(new ICollisionCallback() {
			{ setCategories( CollisionLayers.CATEGORY_ENBULLETS, CollisionLayers.CATEGORY_PLAYERLOGIC ); }

			@Override
			public void startCollision(Body plbullet, Body enemy, Vector2 normal) {
				Entity b = (Entity) plbullet.getUserData();
				Entity p = (Entity) enemy.getUserData();
				DamageComponent dc = (DamageComponent) p.getComponent(DamageComponent.class);
				dc.damageDealtLastFrame = 1;
				b.isDead = dc.painTimer <= 0;
			}

			@Override
			public void endCollision(Body b1, Body b2) {
				// TODO Auto-generated method stub
				
			}
			
		});

		
		callbacks.add(new ICollisionCallback(){
			{ setCategories( CollisionLayers.CATEGORY_PLAYERPHYSIC, CollisionLayers.CATEGORY_MAP ); }
			
			@Override
			public void startCollision(Body player, Body map, Vector2 normal) {
				Entity e = (Entity) player.getUserData();
				PlayerComponent plc = (PlayerComponent) e.getComponent(PlayerComponent.class);
				plc.releaseWallVelocity = 0f;
				if( normal.y == 1 ) {
					PhysicsComponent pc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
					
					if( plc.mashing ) {
						plc.justLandedMashing = true;
					}
					
					plc.flying = false;
					plc.jumping = false;
					pc.grounded = true;
					plc.mashing = false;
				} else if( Math.abs(normal.x) == 1 ) {
					plc.dashingWall = true;
					plc.releasingWall = true;
				}
			}

			@Override
			public void endCollision(Body player, Body map) {
				Entity e = (Entity) player.getUserData();
				PhysicsComponent pc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
				pc.grounded = false;
				PlayerComponent plc = (PlayerComponent) e.getComponent(PlayerComponent.class);
				plc.jumping = true;
				if( plc.dashingWall ) {
					plc.flying = false;
				}
				plc.dashingWall = false;
			}
		});
	}
	
	
	@Override
	public void beginContact(Contact contact) {
		short cb1 = contact.getFixtureA().getFilterData().categoryBits;
		short cb2 = contact.getFixtureB().getFilterData().categoryBits;
		Body b1 = contact.getFixtureA().getBody();
		Body b2 = contact.getFixtureB().getBody();
		Vector2 normal = contact.getWorldManifold().getNormal();

		for( ICollisionCallback icb : callbacks ) {
			
			if( HandleStartCollision(cb1, cb2, b1, b2, icb, normal) ) {
				break;
			}
		}
	}

	private boolean HandleStartCollision(short cb1, short cb2, Body b1, Body b2, ICollisionCallback icb, Vector2 normal) {
		if( CheckCollision(cb1, cb2, icb.B1_CATEGORY, icb.B2_CATEGORY) ) {
			icb.startCollision(b1, b2, normal);
			return true;
		} else if( CheckCollision(cb2, cb1, icb.B1_CATEGORY, icb.B2_CATEGORY) ) {
			icb.startCollision(b2, b1, normal);
			return true;
		}
		return false;
	}

	private boolean CheckCollision(short cb1, short cb2, short c1, short c2) {
		return cb1 == c1 && cb2 == c2;
	}

	@Override
	public void endContact(Contact contact) {
		short cb1 = contact.getFixtureA().getFilterData().categoryBits;
		short cb2 = contact.getFixtureB().getFilterData().categoryBits;
		Body b1 = contact.getFixtureA().getBody();
		Body b2 = contact.getFixtureB().getBody();

		for( ICollisionCallback icb : callbacks ) {
			if( HandleEndCollision(cb1, cb2, b1, b2, icb) ) {
				break;
			}
		}		
	}

	private boolean HandleEndCollision(short cb1, short cb2, Body b1, Body b2, ICollisionCallback icb) {
		if( CheckCollision(cb1, cb2, icb.B1_CATEGORY, icb.B2_CATEGORY) ) {
			icb.endCollision(b1, b2);
			return true;
		} else if( CheckCollision(cb2, cb1, icb.B1_CATEGORY, icb.B2_CATEGORY) ) {
			icb.endCollision(b2, b1);
			return true;
		}
		return false;
	}


	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		PhysicsComponent phc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		phc.body.getWorld().destroyBody(phc.body);
	}


	@Override
	public void process(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
