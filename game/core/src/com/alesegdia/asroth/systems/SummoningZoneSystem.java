package com.alesegdia.asroth.systems;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.SummonZoneComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameConfig;
import com.alesegdia.asroth.game.GameWorld;
import com.alesegdia.platgen.map.MobZoneExtractor.MobZone;
import com.alesegdia.platgen.util.RNG;
import com.badlogic.gdx.Gdx;

public class SummoningZoneSystem extends EntitySystem {

	public SummoningZoneSystem() {
		super(SummonZoneComponent.class, AttackComponent.class, TransformComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		
	}

	@Override
	public void process(Entity e) {
		SummonZoneComponent sc = (SummonZoneComponent) e.getComponent(SummonZoneComponent.class);
		TransformComponent pc = (TransformComponent) e.getComponent(TransformComponent.class);
		
		AttackComponent ac = (AttackComponent) e.getComponent(AttackComponent.class);

		List<Entity> deadEntities = new LinkedList<Entity>();
		for( Entity s : sc.summonedCreatures ) {
			if( s.isDead ) {
				deadEntities.add(s);
			}
		}
		
		for( Entity s : deadEntities ) {
			sc.summonedCreatures.remove(s);
		}
		
		if( ac.doAttack ) {
			ac.attackedLastFrame = true;
		}
		
		if( ac.doAttack && sc.summonedCreatures.size() < sc.maxCreatures ) {
			float prob = RNG.rng.nextFloat();
			int i = 0;
			while( i < 4 ) {
				if( prob < sc.summonProb[i] ) {
					break;
				}
				i++;
			}
			MobZone mz = sc.actingZone;
			float sx, sy;
			sy = mz.height+1;
			int size = mz.xRange.x - mz.xRange.y + 1;
			if( size < 4 ) {
				sx = pc.position.x;
			} else {
				int r = RNG.rng.nextInt(0,size);
				sx = mz.xRange.x + r;
			}
			//sx *= 10 * GameConfig.PIXELS_TO_METERS;
			//sy *= 10 * GameConfig.PIXELS_TO_METERS;
			Entity c;
			sx = mz.xRange.x + 2;
			if( i == 0 ) {
				c = GameWorld.instance.makeZombie(sx, sy);
				GameWorld.instance.adjustToTile(c, ((int)sx), ((int)sy));
			} else if( i == 1 ) {
				c = GameWorld.instance.makeRunner(sx, sy + 25 * GameConfig.PIXELS_TO_METERS);
				GameWorld.instance.adjustToTile(c, ((int)sx), ((int)sy));
			} else if( i == 2 ) {
				c = GameWorld.instance.makeThreeHeaded(sx, sy + 25 * GameConfig.PIXELS_TO_METERS);
				GameWorld.instance.adjustToTile(c, ((int)sx), ((int)sy));
			} else  {
				c = GameWorld.instance.makeJumper(0, 0);
				GameWorld.instance.adjustToTile(c, ((int)sx), ((int)sy));
			}
			sc.summonedCreatures.add(c);
		}
	}

}
