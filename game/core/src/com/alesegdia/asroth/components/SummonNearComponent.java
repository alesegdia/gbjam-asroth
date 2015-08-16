package com.alesegdia.asroth.components;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.asroth.ecs.Component;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.platgen.map.MobZoneExtractor.MobZone;

public class SummonNearComponent extends Component {

	public static enum SummonedCreature {
		CHERUB, MASK
	}
	
	public float summonProb[] = new float[2];
	public List<Entity> summonedCreatures = new LinkedList<Entity>();	
	public int maxCreatures = 4;

	
}
