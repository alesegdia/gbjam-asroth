package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;
import com.badlogic.gdx.math.Vector2;

public class AIAgentFlyingComponent extends Component {
	
	public Vector2 force = new Vector2(0,0);
	public float nearForceCap = 30;
	public float farForceCap = 15;
	public float repulsionFactor = 0.9f;

}
