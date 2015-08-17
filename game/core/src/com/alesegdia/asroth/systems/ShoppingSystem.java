package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.BulletConfigs;
import com.alesegdia.asroth.game.GameWorld;
import com.alesegdia.asroth.game.ShopConfig;
import com.alesegdia.platgen.util.RNG;

import sun.io.ByteToCharDBCS_ASCII;

import com.alesegdia.asroth.components.BuyerComponent;
import com.alesegdia.asroth.components.MashComponent;
import com.alesegdia.asroth.components.MoneyComponent;
import com.alesegdia.asroth.components.ShopComponent;
import com.alesegdia.asroth.components.VanishingComponent;
import com.alesegdia.asroth.components.WeaponComponent;

public class ShoppingSystem extends EntitySystem {

	public ShoppingSystem() {
		super(BuyerComponent.class);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		BuyerComponent bc = (BuyerComponent) e.getComponent(BuyerComponent.class);

		if( bc.triedBuyLastFrame ) {
			if( bc.standingShop != null ) {
				MoneyComponent mc = (MoneyComponent) e.getComponent(MoneyComponent.class);
				ShopComponent sc = (ShopComponent) bc.standingShop.getComponent(ShopComponent.class);
				if( !sc.isSold && mc.currency - ShopConfig.getPriceFor(sc.vendingProduct) >= 0 ) {
					WeaponComponent wc = (WeaponComponent) e.getComponent(WeaponComponent.class);
					switch( sc.vendingProduct ) {
					case DEFAULTGUN:
						wc.weaponModel = BulletConfigs.defaultGun;
						break;
					case SINEGUN:
						wc.weaponModel = BulletConfigs.sineGun;
						break;
					case FIREBALL:
						wc.weaponModel = BulletConfigs.fireball;
						break;
					case TRIPLASMA:
						wc.weaponModel = BulletConfigs.triplasma;
						break;
					case SACREDPUNCH:
						wc.weaponModel = BulletConfigs.sacredPunch;
						break;
					case WINGSCAPACITY:
						break;
					case HEALTHCAPACITY:
						break;
					case MASH_POWER:
						MashComponent mac = (MashComponent) e.getComponent(MashComponent.class);
						mac.power++;
						break;
					case MASH_NUMBER:
						MashComponent mac2 = (MashComponent) e.getComponent(MashComponent.class);
						mac2.number++;
						break;
					}

					sc.isSold = true;
					mc.currency -= ShopConfig.getPriceFor(sc.vendingProduct);
					sc.refillingTimer = sc.refillingCooldown;
					sc.vendingProduct = ShopConfig.chooseRandomProduct();

					if( RNG.rng.nextFloat() > sc.refillingProb ) {
						VanishingComponent vc = (VanishingComponent) bc.standingShop.getComponent(VanishingComponent.class);
						vc.isVanishing = true;
						vc.vanishTimer = vc.timeToVanish;
					}
				}
			}
		}
		
		bc.triedBuyLastFrame = false;
	}

}
