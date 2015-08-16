package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class PickupItemComponent extends Component {

	public static enum PickupType {
		HEALTH, WINGS, MONEY
	}
	
	public PickupType pickupType;
	
}
