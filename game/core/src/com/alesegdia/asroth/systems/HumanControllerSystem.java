package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.LinearVelocityComponent;
import com.alesegdia.asroth.components.MashComponent;
import com.alesegdia.asroth.components.PlayerComponent;
import com.alesegdia.asroth.components.ShootComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.components.WeaponComponent;
import com.alesegdia.asroth.components.WingsComponent;
import com.alesegdia.asroth.components.GraphicsComponent;
import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.asset.Sfx;
import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.BuyerComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.BulletConfigs;
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
			Sfx.Play(3);
			TransformComponent posc = (TransformComponent) e.getComponent(TransformComponent.class);
			float x, y;
			x = posc.position.x;
			y = posc.position.y;
			float yOffset = 2.6f * GameConfig.PIXELS_TO_METERS;
			MashComponent mc = (MashComponent) e.getComponent(MashComponent.class);
			for( int i = 0; i < mc.number; i++ ) {
				GameWorld.instance.makeGroundExplosion(x-0.6f - i * 0.5f, y-yOffset, mc.power);
			}
			for( int i = 0; i < mc.number; i++ ) {
				GameWorld.instance.makeGroundExplosion(x+0.6f + i * 0.5f, y-yOffset, mc.power);
			}
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
		
		if( plc.mashing && !phc.grounded ) {
			ac.currentAnim = Gfx.playerMashDown;
		}
		
		if( plc.dashingWall ) {
			plc.mashing = false;
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

			if( plc.overOneWayPlat || (!plc.overOneWayPlat && !phc.grounded) ) {
				plc.mashing = true;
				plc.flying = false;
				plc.jumping = false;
			}
		} else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			dy = 1;
		}
		
		float x, y;
		TransformComponent posc = (TransformComponent) e.getComponent(TransformComponent.class);
		x = posc.position.x;
		y = posc.position.y;
		
		AttackComponent atc = (AttackComponent) e.getComponent(AttackComponent.class);
		atc.wantToAttack = Gdx.input.isKeyJustPressed(Input.Keys.C);
		atc.forceFace = (plc.dashingWall? (gc.flipX ? 1 : -1) : 0);
		//GameWorld.instance.makePlayerBullet(x, y, (plc.dashingWall? gc.flipX : !gc.flipX));
		
		WingsComponent wc = (WingsComponent) e.getComponent(WingsComponent.class);
		wc.isRecovering = phc.grounded;
		
		/*
		if( Gdx.input.isKeyJustPressed(Input.Keys.Q) ) { GameWorld.instance.makeThreeHeaded(x, y); }
		if( Gdx.input.isKeyJustPressed(Input.Keys.W) ) { GameWorld.instance.makeRunner(x, y); }
		if( Gdx.input.isKeyJustPressed(Input.Keys.E) ) { GameWorld.instance.makeJumper(x, y); }
		if( Gdx.input.isKeyJustPressed(Input.Keys.R) ) { GameWorld.instance.makeZombie(x, y); }
		//if( Gdx.input.isKeyJustPressed(Input.Keys.A) ) { GameWorld.instance.makeSummoner(x, y); }
		if( Gdx.input.isKeyJustPressed(Input.Keys.S) ) { GameWorld.instance.makeDemon(x, y); }
		if( Gdx.input.isKeyJustPressed(Input.Keys.D) ) { GameWorld.instance.makeEvilCherub(x, y); }
		if( Gdx.input.isKeyJustPressed(Input.Keys.F) ) { GameWorld.instance.makeCryingMask(x, y); }

		WeaponComponent wep = (WeaponComponent) e.getComponent(WeaponComponent.class);
		if( Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) ) {
			wep.weaponModel = BulletConfigs.defaultGun;
		} else if( Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) ) {
			wep.weaponModel = BulletConfigs.fireball;
		} else if( Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) ) {
			wep.weaponModel = BulletConfigs.sacredPunch;
		} else if( Gdx.input.isKeyJustPressed(Input.Keys.NUM_4) ) {
			wep.weaponModel = BulletConfigs.triplasma;
		} else if( Gdx.input.isKeyJustPressed(Input.Keys.NUM_5) ) {
			wep.weaponModel = BulletConfigs.sineGun;
		}
		*/

		
		if( Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if( phc.grounded ) {
				Sfx.Play(0);

				plc.jumping = true;
				plc.flying = false;
				plc.mashing = false;

				lvc.doCap[1] = false;
				prevYlinear = 6;

			} else if( wc.currentBoost > 0 ) {
				Sfx.Play(0);
				wc.currentBoost--;
				plc.flying = true;
				plc.jumping = false;
				plc.mashing = false;
				lvc.doCap[1] = true;
				prevYlinear = 6;
			}
			if( plc.dashingWall ) {
				Sfx.Play(0);
				plc.dashingWall = false;
				plc.releaseWallVelocity = -10 * (gc.flipX ? 1 : -1);
				prevYlinear = 6;
			}
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
		
		if( Gdx.input.isKeyJustPressed(Input.Keys.B) ) {
			BuyerComponent bc = (BuyerComponent) e.getComponent(BuyerComponent.class);
			bc.triedBuyLastFrame = true;
		}
		
		plc.isPressingDown = Gdx.input.isKeyPressed(Input.Keys.DOWN);
		
		if( plc.dashingWall ) {
			ac.currentAnim = Gfx.playerWall;
		}
		
		if( Gdx.input.isKeyJustPressed(Input.Keys.M) ) {
			plc.minimapEnabled = !plc.minimapEnabled;
		}
	}

	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
}
