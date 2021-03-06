package com.alesegdia.asroth.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.alesegdia.asroth.ecs.Component;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.platgen.map.MobZoneExtractor.MobZone;

public class SummonZoneComponent extends Component {

	public static enum SummonedCreature {
		ZOMBIE, RUNNER, THREEHEADED, JUMPER
	}
	
	public float summonProb[] = new float[4];
	public List<Entity> summonedCreatures = new LinkedList<Entity>();	
	public MobZone actingZone;
	public int maxCreatures = 4;

}
