package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class VanishingComponent extends Component {
	
	public boolean isVanishing = false;
	public float timeToVanish;
	public float vanishTimer;
	public boolean willTurn;
	
}
