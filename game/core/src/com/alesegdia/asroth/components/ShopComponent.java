package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class ShopComponent extends Component {

	public static enum ShopProduct {
		FIREBALL, SINEGUN, DEFAULTGUN, TRIPLASMA, SACREDPUNCH,
		WINGSCAPACITY, HEALTHCAPACITY,
		MASH_POWER, MASH_NUMBER
	}
	
	public float refillingCooldown = 10f;
	public float refillingTimer = 0f;
	
	public ShopProduct vendingProduct;
	public boolean isSold = false;
	public float refillingProb = 0.5f;
	
}
