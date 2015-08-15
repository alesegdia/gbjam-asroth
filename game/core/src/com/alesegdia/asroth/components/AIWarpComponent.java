package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class AIWarpComponent extends Component {

	public float maxWarpDistance; 	// max distance to travel
	public float minKeepDistance; 	// min distance to keep away from player
	
	public float hiddenTime;		// time to be hidden
	public float hiddenTimer;
	
	public float unhiddenTime; 		// time to be unhidden
	public float unhiddenTimer;
	
}
