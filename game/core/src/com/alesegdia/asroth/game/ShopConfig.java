package com.alesegdia.asroth.game;

import com.alesegdia.asroth.components.ShopComponent.ShopProduct;
import com.alesegdia.platgen.util.RNG;

public class ShopConfig {
	
	static final float shopProbs[] = {
	// 	FIREBALL, 	SINEGUN,	DEFAULTGUN,	TRIPLASMA,	SACREDPUNCH, 	WINGSINV,	HEALTHINV, 	MASHNUM, 	MASHPOW
		0.15f, 		0.3f, 		0f, 		0.45f, 		0.60f, 			0.7f, 		0.8f, 		0.9f, 		1f
	};

	static final int shopPrices[] = {
		6,			3,			1, 			9,			10, 			8,			9, 			6, 			6
	};
	
	public static int getPriceFor(ShopProduct sp) {
		if( sp == ShopProduct.FIREBALL ) return shopPrices[0];
		else if( sp == ShopProduct.SINEGUN ) return shopPrices[1];
		else if( sp == ShopProduct.DEFAULTGUN ) return shopPrices[2];
		else if( sp == ShopProduct.TRIPLASMA ) return shopPrices[3];
		else if( sp == ShopProduct.SACREDPUNCH ) return shopPrices[4];
		else if( sp == ShopProduct.WINGSCAPACITY ) return shopPrices[5];
		else if( sp == ShopProduct.HEALTHCAPACITY ) return shopPrices[6];
		else if( sp == ShopProduct.MASH_NUMBER ) return shopPrices[7];
		else if( sp == ShopProduct.MASH_POWER ) return shopPrices[8];
		else return 0;
	}
		
	public static ShopProduct chooseRandomProduct() {
		float r = RNG.rng.nextFloat();
		int i = 0;
		while(i < 9) {
			if( r < shopProbs[i] ) {
				break;
			}
			i++;
		}
		if( i == 0 ) return ShopProduct.FIREBALL;
		else if( i == 1 ) return ShopProduct.SINEGUN;
		else if( i == 2 ) return ShopProduct.DEFAULTGUN;
		else if( i == 3 ) return ShopProduct.TRIPLASMA;
		else if( i == 4 ) return ShopProduct.SACREDPUNCH;
		else if( i == 5 ) return ShopProduct.WINGSCAPACITY;
		else if( i == 6 ) return ShopProduct.HEALTHCAPACITY;
		else if( i == 7 ) return ShopProduct.MASH_NUMBER;
		else return ShopProduct.MASH_POWER;		
	}

	public static String getNameFor(ShopProduct sp) {
		if( sp == ShopProduct.FIREBALL ) return "FireB";
		else if( sp == ShopProduct.SINEGUN ) return "SineG";
		else if( sp == ShopProduct.DEFAULTGUN ) return "Defogun";
		else if( sp == ShopProduct.TRIPLASMA ) return "TriP";
		else if( sp == ShopProduct.SACREDPUNCH ) return "SacredP";
		else if( sp == ShopProduct.WINGSCAPACITY ) return "InfiFLY";
		else if( sp == ShopProduct.HEALTHCAPACITY ) return "InfiHP";
		else if( sp == ShopProduct.MASH_NUMBER ) return "MashN";
		else if( sp == ShopProduct.MASH_POWER ) return "MashP";
		else return "";
	}
	
}
