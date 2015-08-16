package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class DropPickupComponent extends Component {

	public boolean hasDropped = false;
	public float probDrop;
	public float probs[] = new float[3]; // HEALTH, WINGS, MONEY
	
}
