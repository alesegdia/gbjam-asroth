package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.BulletConfigs;
import com.alesegdia.asroth.game.GameConstants;
import com.alesegdia.asroth.game.GameWorld;
import com.alesegdia.asroth.game.ShopConfig;
import com.alesegdia.platgen.util.RNG;

import com.alesegdia.asroth.asset.Sfx;
import com.alesegdia.asroth.components.BuyerComponent;
import com.alesegdia.asroth.components.HealthComponent;
import com.alesegdia.asroth.components.InfiniteFlyComponent;
import com.alesegdia.asroth.components.InvincibilityComponent;
import com.alesegdia.asroth.components.MashComponent;
import com.alesegdia.asroth.components.MoneyComponent;
import com.alesegdia.asroth.components.ShopComponent;
import com.alesegdia.asroth.components.VanishingComponent;
import com.alesegdia.asroth.components.WeaponComponent;
import com.alesegdia.asroth.components.WingsComponent;

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
					Sfx.Play(5);
					switch( sc.vendingProduct ) {
					case DEFAULTGUN:
						wc.weaponModel = BulletConfigs.defaultGun;
						bc.lastWeaponBought = sc.vendingProduct;
						break;
					case SINEGUN:
						wc.weaponModel = BulletConfigs.sineGun;
						bc.lastWeaponBought = sc.vendingProduct;
						break;
					case FIREBALL:
						wc.weaponModel = BulletConfigs.fireball;
						bc.lastWeaponBought = sc.vendingProduct;
						break;
					case TRIPLASMA:
						wc.weaponModel = BulletConfigs.triplasma;
						bc.lastWeaponBought = sc.vendingProduct;
						break;
					case SACREDPUNCH:
						wc.weaponModel = BulletConfigs.sacredPunch;
						bc.lastWeaponBought = sc.vendingProduct;
						break;
					case WINGSCAPACITY:
						InfiniteFlyComponent ifc = (InfiniteFlyComponent) e.getComponent(InfiniteFlyComponent.class);
						ifc.timer = ifc.infiniteFlyTime;
						WingsComponent wic = (WingsComponent) e.getComponent(WingsComponent.class);
						wic.maxCapacity += GameConstants.FLY_TANK_SIZE;
						if( wic.maxCapacity > GameConstants.PLAYER_MAX_UPGRADE_FLY ) {
							wic.maxCapacity = (int) GameConstants.PLAYER_MAX_UPGRADE_FLY;
						}
						break;
					case HEALTHCAPACITY:
						InvincibilityComponent ic = (InvincibilityComponent) e.getComponent(InvincibilityComponent.class);
						ic.timer = ic.invincibilityTime;
						HealthComponent hc = (HealthComponent) e.getComponent(HealthComponent.class);
						hc.maxHP += GameConstants.HP_TANK_SIZE;
						if( hc.maxHP > GameConstants.PLAYER_MAX_UPGRADE_HEALTH ) {
							hc.maxHP = (int) GameConstants.PLAYER_MAX_UPGRADE_HEALTH;
						}
						break;
					case MASH_POWER:
						MashComponent mac = (MashComponent) e.getComponent(MashComponent.class);
						mac.power++;
						bc.mashPowers++;
						break;
					case MASH_NUMBER:
						MashComponent mac2 = (MashComponent) e.getComponent(MashComponent.class);
						mac2.number++;
						bc.mashNumbers++;
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
