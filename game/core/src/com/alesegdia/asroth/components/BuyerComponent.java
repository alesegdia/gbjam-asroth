package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;
import com.alesegdia.asroth.ecs.Entity;

public class BuyerComponent extends Component {

	public Entity standingShop;
	public boolean triedBuyLastFrame = false;
	
}
