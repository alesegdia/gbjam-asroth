package com.alesegdia.asroth.physics;

import com.alesegdia.asroth.game.GameConfig;
import com.alesegdia.asroth.systems.PhysicsSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Physics {

	World world;
	Box2DDebugRenderer debugRenderer;
	private float accumulator = 0;
	private static final float TIME_STEP = 1/60.f;
	private static final int VELOCITY_ITERATIONS = 6000;
	private static final int POSITION_ITERATIONS = 2000;
	
	public PhysicsSystem physicsSystem;
	
	public Physics() {
		world = new World(new Vector2(0, -10f), false);
		physicsSystem = new PhysicsSystem();
		world.setContactListener(this.physicsSystem);
		debugRenderer = new Box2DDebugRenderer();
	}
	
	public void step(float deltaTime) {

	    accumulator += deltaTime;
	    while (accumulator >= TIME_STEP) {
	        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
	        accumulator -= TIME_STEP;
	    }
	    //world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
	}
	
	public void render(Camera cam) {
		debugRenderer.render(world, cam.combined);
	}
	
	Body createBody(float x, float y, boolean dynamic) {
		BodyDef bodyDef = new BodyDef();
		if( dynamic ) {
			bodyDef.type = BodyType.DynamicBody;
		} else {
			bodyDef.type = BodyType.StaticBody;
		}
		bodyDef.position.set(x, y);
		Body b = world.createBody(bodyDef);
		return b;
	}
	
	public Fixture createFixture(Body body, Shape shape, short catbits, short maskbits, short group, float density, float friction, float restitution ) {
		return createFixture(body, shape, catbits, maskbits, group, density, friction, restitution, false);
	}
	
	public Fixture createFixture(Body body, Shape shape, short catbits, short maskbits, short group, float density, float friction,
			float restitution, boolean isSensor ) {
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;
		fd.filter.categoryBits = catbits;
		fd.filter.maskBits = maskbits;
		fd.filter.groupIndex = group;
		fd.isSensor = isSensor;
		return body.createFixture(fd);
	}
	
	public Body createCircleBody(float x, float y, float radius, short catbits, short maskbits, short group, boolean dynamic) {
		Body b = createBody(x * GameConfig.PIXELS_TO_METERS, y* GameConfig.PIXELS_TO_METERS,dynamic);
		CircleShape cs = new CircleShape();
		cs.setRadius(radius * GameConfig.PIXELS_TO_METERS);
		System.out.println("PLAYERBODY");
		createFixture(b, cs, catbits, maskbits, group, 1f, 0f, 0f);
		cs.dispose();
		return b;
	}
	
	public Body createRectBody(float x, float y, float w, float h, short catbits, short maskbits, short group, boolean dynamic) {
		Body b = createBody(x,y,dynamic);
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(w, h);
		createFixture(b, ps, catbits, maskbits, group, 0, 0, 0);
		ps.dispose();
		return b;
	}
	
	public Body createEnemyBody(float x, float y) {
		return createCircleBody(x, y, 10, CollisionLayers.CATEGORY_ENEMYPHYSIC, CollisionLayers.MASK_ENEMYPHYSIC, CollisionLayers.GROUP_ENEMYPHYSIC, true);
	}

	public Body createPlayerBody(float x, float y) {
		return createCircleBody(x, y, 7.41f, CollisionLayers.CATEGORY_PLAYERPHYSIC, CollisionLayers.MASK_PLAYERPHYSIC, CollisionLayers.GROUP_PLAYERPHYSIC, true);
	}
	
	public Body createPlayerBulletBody( float x, float y ) {
		Body b = createBody(x, y, true);
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(5 * GameConfig.PIXELS_TO_METERS, 5 * GameConfig.PIXELS_TO_METERS);
		createFixture(b, ps, CollisionLayers.CATEGORY_PLBULLETS, CollisionLayers.MASK_PLBULLETS, CollisionLayers.GROUP_PLBULLETS, 0, 0, 0, true);
		b.setGravityScale(0);
		ps.dispose();
		return b;
	}

	public Body createGroundExplosionBody( float x, float y ) {
		Body b = createBody(x, y, true);
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(5 * GameConfig.PIXELS_TO_METERS, 5 * GameConfig.PIXELS_TO_METERS);
		createFixture(b, ps, CollisionLayers.CATEGORY_PLBULLETS, CollisionLayers.MASK_PLBULLETS, CollisionLayers.MASK_PLBULLETS, 0, 0, 0, true);
		b.setGravityScale(0);
		ps.dispose();
		return b;
	}
	
	public Body createEnemyBody2( float x, float y, float w, float h ) {
		Body b = createBody(x, y, true);
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(w * GameConfig.PIXELS_TO_METERS, h * GameConfig.PIXELS_TO_METERS);
		createFixture(b, ps, CollisionLayers.CATEGORY_ENEMYPHYSIC, CollisionLayers.MASK_ENEMYPHYSIC, CollisionLayers.GROUP_ENEMYPHYSIC, 1f, 0, 0, false);
		createFixture(b, ps, CollisionLayers.CATEGORY_ENEMYLOGIC, CollisionLayers.MASK_ENEMYLOGIC, CollisionLayers.GROUP_ENEMYPHYSIC, 0.01f, 0, 0, true);
		ps.dispose();
		return b;
	}

}
