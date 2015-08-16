package com.alesegdia.asroth.components;

import java.util.List;

import com.alesegdia.asroth.components.ShootComponent.BulletEntry;
import com.alesegdia.asroth.components.ShootComponent.BulletModel;
import com.alesegdia.asroth.ecs.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WeaponComponent extends Component {

	public static enum WeaponType {
		DEFAULT, FIREBALL, SINEGUN, TRIPLASMA, SACREDGUN, TROQUEGUN
	}
	
	public static class WeaponModel {
		public float rate;
		public List<BulletEntry> bulletEntries;
	}
	
	public WeaponModel weaponModel;
	
}
