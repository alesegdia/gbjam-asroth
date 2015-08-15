package com.alesegdia.asroth.components;

import java.util.List;

import com.alesegdia.asroth.ecs.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ShootComponent extends Component {

	public static class BulletModel {
		public TextureRegion tr;
		public int w, h;
		public float destructionTime = 0.5f;
		public float speed = 10f;
		public boolean isPlayer = false;
	}
	
	public BulletModel bulletModel;
	
	public boolean horizontal = true;
	
	public List<Vector2> bulletOrigins;
	
}
