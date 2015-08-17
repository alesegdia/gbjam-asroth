package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class AIAgentComponent extends Component {

	public boolean isAttacking;
	public boolean isPreparingAttack;
	
	public static final int DEMON = 0;
	public static final int THREEHEAD = 1;
	
	public int agentType;
	
}
