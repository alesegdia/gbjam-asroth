package com.alesegdia.asroth.physics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameContactListener implements ContactListener {

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
	
	public GameContactListener() {
		callbacks.add(new ICollisionCallback(){
			{ setCategories( CollisionLayers.CATEGORY_PLAYER, CollisionLayers.CATEGORY_MAP ); }
			
			@Override
			public void startCollision(Body player, Body map, Vector2 normal) {
				System.out.println("START PLAYER MAP! normal: " + normal);
			}

			@Override
			public void endCollision(Body player, Body map) {
				
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
				System.out.println("IT!");
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
		} else if( CheckCollision(cb2, cb2, icb.B1_CATEGORY, icb.B2_CATEGORY) ) {
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

}
