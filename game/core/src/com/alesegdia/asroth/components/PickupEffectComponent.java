package com.alesegdia.asroth.components;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.asroth.components.PickupItemComponent.PickupType;
import com.alesegdia.asroth.ecs.Component;
import com.alesegdia.asroth.ecs.Entity;

public class PickupEffectComponent extends Component {

	public List<Entity> pickupsCollectedLastFrame = new LinkedList<Entity>();
	
}
