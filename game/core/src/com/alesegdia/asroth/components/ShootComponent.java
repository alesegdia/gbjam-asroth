package com.alesegdia.asroth.components;

import java.util.List;

import com.alesegdia.asroth.ecs.Component;
import com.badlogic.gdx.math.Vector2;

public class ShootComponent extends Component {

	public static final int BULLET1 = 1;
	
	public int bulletType;
	
	public List<Vector2> bulletOrigins;
	
}
