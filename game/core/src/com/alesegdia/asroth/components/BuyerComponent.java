package com.alesegdia.asroth.components;

import com.alesegdia.asroth.components.ShopComponent.ShopProduct;
import com.alesegdia.asroth.ecs.Component;
import com.alesegdia.asroth.ecs.Entity;

public class BuyerComponent extends Component {

	public Entity standingShop;
	public boolean triedBuyLastFrame = false;
	public ShopProduct lastWeaponBought = ShopProduct.DEFAULTGUN;
	public int mashPowers = 0;
	public int mashNumbers = 0;
	
}
