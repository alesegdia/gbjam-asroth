package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.AIAgentComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.KillStatsComponent;

public class AccountStatsSystem extends EntitySystem {

	public AccountStatsSystem() {
		super(AIAgentComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		if( e.isDead ) {
			AIAgentComponent aiac = (AIAgentComponent) e.getComponent(AIAgentComponent.class);
			KillStatsComponent.instance.kills[aiac.agentType]++;
		}
	}

}
