package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.PlayerComponent;
import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class HumanControllerSystem extends EntitySystem {

	public HumanControllerSystem() {
		super(PlayerComponent.class, PhysicsComponent.class,
			  LinearVelocityComponent.class, AnimationComponent.class);
	}
	
	@Override
	public void process(Entity e) {
		PhysicsComponent phc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.getComponent(LinearVelocityComponent.class);
		AnimationComponent ac = (AnimationComponent) e.getComponent(AnimationComponent.class);
		PlayerComponent plc = (PlayerComponent) e.getComponent(PlayerComponent.class);
		
		if( phc.grounded ){
			if( Math.abs(phc.body.getLinearVelocity().x) > 0 ) {
				ac.currentAnim = Gfx.playerWalk;
			} else {
				ac.currentAnim = Gfx.playerStand;
			}
		} else {
			if( plc.jumping ) {
				if( phc.body.getLinearVelocity().y > 0.01 ) {
					ac.currentAnim = Gfx.playerJumpUp;
				} else {
					ac.currentAnim = Gfx.playerJumpDown;
				}
			}
			
			if( plc.flying ) {
				ac.currentAnim = Gfx.playerFly;
			}
		}

		int dx = 0; int dy = 0;
		float prevYlinear = phc.body.getLinearVelocity().y;
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

		if( Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if( phc.grounded ) {
				plc.jumping = true;
				lvc.doCap[1] = false;
			} else {
				plc.flying = true;
				lvc.doCap[1] = true;
			}
			prevYlinear = 5;
			//phc.body.applyForce(new Vector2(0,50), new Vector2(0,0), true);
		}
		lvc.linearVelocity.x = dx * 5 * lvc.speed.x;
		lvc.linearVelocity.y = prevYlinear;
		
	}
	
}
