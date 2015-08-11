package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysicsComponent extends Component {
	
	public Body body;
	public boolean grounded;

}
