package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class StrikeAttackComponent extends Component {

	/* STRIKES ********************************/
	public boolean enableStrike; 			// is the attack a strike?
	
	public int strikeNum; 					// num of attacks per strike
	public int currentStrike = 0; 			// current strike point

	public int timeTweenStrike; 			// time between strike attacks
	public int strikeTimer; 				// timer for the next strike attack
	
}
