package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class WingsComponent extends Component {

	public int maxCapacity = 16;
	public int currentBoost = 16;
	public boolean isRecovering;
	public float nextRecovery;
	public float recoveryRate = 1f;
	
}
