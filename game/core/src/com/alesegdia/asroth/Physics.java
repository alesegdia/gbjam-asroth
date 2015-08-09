package com.alesegdia.asroth;

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
	private static final int VELOCITY_ITERATIONS = 6;
	private static final int POSITION_ITERATIONS = 2;
	
	public Physics() {
		world = new World(new Vector2(0, -10), false);
		debugRenderer = new Box2DDebugRenderer();
	}
	
	public void step(float deltaTime) {
		/*
	    accumulator += deltaTime;
	    while (accumulator >= TIME_STEP) {
	        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
	        accumulator -= TIME_STEP;
	    }
	    */
		world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
	}
	
	public void render(Camera cam) {
		debugRenderer.render(world, cam.combined);
	}
	
	Body createBody(int x, int y, boolean dynamic) {
		BodyDef bodyDef = new BodyDef();
		if( dynamic ) {
			bodyDef.type = BodyType.DynamicBody;
		} else {
			bodyDef.type = BodyType.StaticBody;
		}
		bodyDef.position.set(x, y);
		return world.createBody(bodyDef);
	}
	
	public Fixture createFixture(Body body, Shape shape, float density, float friction, float restitution) {
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;
		return body.createFixture(fd);
	}
	
	public Body createCircleBody(int x, int y, float radius, boolean dynamic) {
		Body b = createBody(x,y,dynamic);
		CircleShape cs = new CircleShape();
		cs.setRadius(radius);
		createFixture(b, cs, 0.5f, 0.4f, 0.0f);
		cs.dispose();
		return b;
	}
	
	public Body createRectBody(int x, int y, int w, int h, boolean dynamic) {
		Body b = createBody(x,y,dynamic);
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(w, h);
		createFixture(b, ps, 0, 0, 0);
		ps.dispose();
		return b;
	}

}
