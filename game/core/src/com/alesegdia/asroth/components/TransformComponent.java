package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent extends Component {

	public Vector2 position = new Vector2(0,0);
	public Vector2 offset = new Vector2(0,0);
	public float angle;
	
}
