package com.alesegdia.asroth.ecs;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.GraphicsComponent;

public class Entity {

	public boolean isDead = false;
	private BitSet bits = new BitSet(32);
	public String name;
	Component[] components;
	
	public Entity() {
		components = new Component[64];
	}
	
	public Component addComponent(Component c1) {
		ComponentType ct = ComponentType.IsComponentRegistered(c1);
		if( ct == null ) {
			ct = ComponentType.RegisterComponentType(c1.getClass());
		}
		this.components[ComponentType.getIndexFor(c1.getClass())] = c1;
		this.bits.set(ct.getIndex());
		return c1;
	}

	public BitSet getBits() {
		return bits;
	}
	
	public String toString() {
		return name;
	}

	public Component getComponent(Class<? extends Component> class1) {
		return this.components[ComponentType.getIndexFor(class1)];
	}


}
