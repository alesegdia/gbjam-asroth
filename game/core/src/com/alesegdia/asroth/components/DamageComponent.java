package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class DamageComponent extends Component {

	public int damageDealtLastFrame; 	// damage dealt in last frame
	public boolean painLastFrame;		// did get hit last frame?
	
	public float painCooldown; 		// time to be able to be damaged again
	public float painTimer;
	public int soundIndex = 2;
	
	
}
