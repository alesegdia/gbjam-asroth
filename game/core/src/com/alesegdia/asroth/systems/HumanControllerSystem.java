package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.PlayerComponent;
import com.alesegdia.asroth.components.PositionComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameConfig;
import com.alesegdia.asroth.game.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class HumanControllerSystem extends EntitySystem {

	public HumanControllerSystem() {
		super(PlayerComponent.class, PhysicsComponent.class,
			  LinearVelocityComponent.class, AnimationComponent.class,
			  GraphicsComponent.class);
	}
	
	@Override
	public void process(Entity e) {
		PhysicsComponent phc = (PhysicsComponent) e.getComponent(PhysicsComponent.class);
		LinearVelocityComponent lvc = (LinearVelocityComponent) e.getComponent(LinearVelocityComponent.class);
		AnimationComponent ac = (AnimationComponent) e.getComponent(AnimationComponent.class);
		PlayerComponent plc = (PlayerComponent) e.getComponent(PlayerComponent.class);
		GraphicsComponent gc = (GraphicsComponent) e.getComponent(GraphicsComponent.class);
		
		if( plc.justLandedMashing ) {
			PositionComponent posc = (PositionComponent) e.getComponent(PositionComponent.class);
			float x, y;
			x = posc.position.x;
			y = posc.position.y;
			float yOffset = 2.6f * GameConfig.PIXELS_TO_METERS;
			GameWorld.instance.makeGroundExplosion(x-0.6f, y-yOffset);
			GameWorld.instance.makeGroundExplosion(x-1.1f, y-yOffset);
			GameWorld.instance.makeGroundExplosion(x-1.6f, y-yOffset);
			GameWorld.instance.makeGroundExplosion(x+0.6f, y-yOffset);
			GameWorld.instance.makeGroundExplosion(x+1.1f, y-yOffset);
			GameWorld.instance.makeGroundExplosion(x+1.6f, y-yOffset);
			plc.justLandedMashing = false;
		}
		
		if( plc.dashingWall ) {
			ac.currentAnim = Gfx.playerWall;
			lvc.doCap[1] = true;
			lvc.cap.y = 0;
		} else if( phc.grounded ){
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
			} else if( plc.flying ) {
				ac.currentAnim = Gfx.playerFly;
			} else if( plc.mashing ) {
				ac.currentAnim = Gfx.playerMashDown;
			}
		}

		int dx = 0; int dy = 0;
		float prevYlinear = phc.body.getLinearVelocity().y;
		float prevXlinear = phc.body.getLinearVelocity().x;
		if( !plc.dashingWall ) {
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				dx = -1;
				plc.releasingWall = false;
			} else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				dx = 1;
				plc.releasingWall = false;
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {

			plc.mashing = true;
			plc.flying = false;
			plc.jumping = false;
		} else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			dy = 1;
		}

		if( Gdx.input.isKeyJustPressed(Input.Keys.C) ) {
			float x, y;
			PositionComponent posc = (PositionComponent) e.getComponent(PositionComponent.class);
			x = posc.position.x;
			y = posc.position.y;
			GameWorld.instance.makePlayerBullet(x, y, (plc.dashingWall? !gc.flipX : gc.flipX));
		}
		
		if( Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if( phc.grounded ) {
				plc.jumping = true;
				plc.flying = false;
				plc.mashing = false;

				lvc.doCap[1] = false;
			} else {
				plc.flying = true;
				plc.jumping = false;
				plc.mashing = false;
				lvc.doCap[1] = true;
			}
			if( plc.dashingWall ) {
				plc.dashingWall = false;
				plc.releaseWallVelocity = -10 * (gc.flipX ? -1 : 1);
			}
			prevYlinear = 6;
			//phc.body.applyForce(new Vector2(0,50), new Vector2(0,0), true);
		}
		
		if( plc.mashing ) {
			prevYlinear = -15;
			lvc.doCap[1] = true;
			lvc.cap.y = 15;
		} else if( plc.flying ) {
			lvc.doCap[1] = true;
			lvc.cap.y = 2;
		}
		if( plc.jumping ) {
			lvc.doCap[1] = false;
		}

		plc.releaseWallVelocity *= 0.9f;

		if( !plc.dashingWall ) {
			lvc.linearVelocity.x = dx * 5 * lvc.speed.x + plc.releaseWallVelocity;
			lvc.linearVelocity.y = prevYlinear;
			phc.body.setGravityScale(1);
		} else {
			lvc.linearVelocity.set(0,0);
			phc.body.setGravityScale(0);
		}
		
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
}
