package com.alesegdia.asroth.game;

import com.alesegdia.asroth.components.ShopComponent.ShopProduct;
import com.alesegdia.platgen.util.RNG;

public class ShopConfig {
	
	static final float shopProbs[] = {
	// 	FIREBALL, 	SINEGUN,	DEFAULTGUN,	TRIPLASMA,	SACREDPUNCH, 	WINGSINV,	HEALTHINV, 	MASHNUM, 	MASHPOW
		0.15f, 		0.3f, 		0.45f, 		0.60f, 		0.75f, 			0.80f, 		0.85f, 		0.95f, 		1f
	};
		
	static final int shopPrices[] = {
		5,			5,			1, 			7,			10, 			12,			15, 		9, 			9
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
		else if( i == 5 ) return ShopProduct.HEALTHCAPACITY;
		else if( i == 5 ) return ShopProduct.MASH_NUMBER;
		else return ShopProduct.MASH_POWER;		
	}

	public static String getNameFor(ShopProduct sp) {
		if( sp == ShopProduct.FIREBALL ) return "Fireball";
		else if( sp == ShopProduct.SINEGUN ) return "Sinegun";
		else if( sp == ShopProduct.DEFAULTGUN ) return "Defogun";
		else if( sp == ShopProduct.TRIPLASMA ) return "Triplasma";
		else if( sp == ShopProduct.SACREDPUNCH ) return "Sacredpunch";
		else if( sp == ShopProduct.WINGSCAPACITY ) return "InfiniteFly10";
		else if( sp == ShopProduct.HEALTHCAPACITY ) return "Awesome10";
		else if( sp == ShopProduct.MASH_NUMBER ) return "MashN";
		else if( sp == ShopProduct.MASH_POWER ) return "MashP";
		else return "";
	}
	
}
