package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class WalkingComponent extends Component {

	public boolean isWalking = false;
	public float walkSpeed;
	public boolean walkingLeft = true; // change at contact listener
	
	public float timeToRest;
	public float restingTimer;
	
	public float timeToWalk;
	public float walkingTimer;
	
	
}
