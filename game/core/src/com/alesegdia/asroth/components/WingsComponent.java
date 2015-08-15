package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class WingsComponent extends Component {

	public int maxCapacity = 32;
	public int currentBoost = 32;
	public boolean isRecovering;
	public float nextRecovery;
	public float recoveryRate = 0.5f;
	
}
