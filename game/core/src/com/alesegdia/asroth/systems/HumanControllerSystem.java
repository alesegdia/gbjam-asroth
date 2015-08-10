package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.PlayerComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class HumanControllerSystem extends EntitySystem {

	public HumanControllerSystem() {
		super(PlayerComponent.class, PhysicsComponent.class, LinearVelocityComponent.class);
	}
	
	@Override
	public void process(Entity e) {
		PhysicsComponent phc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.getComponent(LinearVelocityComponent.class);
		
		int dx = 0; int dy = 0;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			dx = -1;
		} else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			dx = 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			dy = -1;
		} else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			dy = 1;
		}

		lvc.linearVelocity.x = dx * 5;
		lvc.linearVelocity.y = dy * 5;
		
	}
	
}
