package com.alesegdia.asroth.physics;

import com.alesegdia.asroth.game.GameConfig;
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
	
	public Physics() {
		world = new World(new Vector2(0, -10f), false);
		world.setContactListener(new GameContactListener());
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
		return world.createBody(bodyDef);
	}
	
	public Fixture createFixture(Body body, Shape shape, short catbits, short maskbits, float density, float friction, float restitution) {
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;
		fd.filter.categoryBits = catbits;
		fd.filter.maskBits = maskbits;
		return body.createFixture(fd);
	}
	
	public Body createCircleBody(float x, float y, float radius, short catbits, short maskbits, boolean dynamic) {
		Body b = createBody(x * GameConfig.PIXELS_TO_METERS, y* GameConfig.PIXELS_TO_METERS,dynamic);
		CircleShape cs = new CircleShape();
		cs.setRadius(radius * GameConfig.PIXELS_TO_METERS);
		System.out.println("PLAYERBODY");
		createFixture(b, cs, catbits, maskbits, 1f, 0f, 0f);
		cs.dispose();
		return b;
	}
	
	public Body createRectBody(float x, float y, float w, float h, short catbits, short maskbits, boolean dynamic) {
		Body b = createBody(x,y,dynamic);
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(w, h);
		createFixture(b, ps, catbits, maskbits, 0, 0, 0);
		ps.dispose();
		return b;
	}

	public Body createEnemyBody(float x, float y) {
		return createCircleBody(x, y, 10, CollisionLayers.CATEGORY_ENEMY, CollisionLayers.MASK_ENEMY, true);
	}

	public Body createPlayerBody(float x, float y) {
		return createCircleBody(x, y, 7, CollisionLayers.CATEGORY_PLAYER, CollisionLayers.MASK_PLAYER, true);
	}

}
