package com.alesegdia.asroth.systems;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.asroth.components.AttackComponent;
import com.alesegdia.asroth.components.SummonNearComponent;
import com.alesegdia.asroth.components.SummonZoneComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameConfig;
import com.alesegdia.asroth.game.GameWorld;
import com.alesegdia.platgen.map.MobZoneExtractor.MobZone;
import com.alesegdia.platgen.util.RNG;

public class SummoningNearSystem extends EntitySystem {

	public SummoningNearSystem() {
		super(SummonNearComponent.class, TransformComponent.class, AttackComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		SummonNearComponent sc = (SummonNearComponent) e.getComponent(SummonNearComponent.class);
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
		
		if( ac.doAttack && sc.summonedCreatures.size() < sc.maxCreatures ) {
			float prob = RNG.rng.nextFloat();
			int i = 0;
			while( i < 2 ) {
				if( prob < sc.summonProb[i] ) {
					break;
				}
				i++;
			}
			float sx, sy;
			Entity c;
			sx = pc.position.x + (RNG.rng.nextFloat()-0.5f)*4;
			sy = pc.position.y + (RNG.rng.nextFloat()-0.5f)*4;
			if( i == 0 ) {
				c = GameWorld.instance.makeCryingMask(sx, sy);
			} else  {
				c = GameWorld.instance.makeEvilCherub(sx, sy);
			}
			sc.summonedCreatures.add(c);
		}
		
	}

}
