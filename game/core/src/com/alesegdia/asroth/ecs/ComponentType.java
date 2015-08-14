package com.alesegdia.asroth.ecs;

import java.util.Hashtable;

public class ComponentType {

	public static int lastIndex = 0;
	public static Hashtable<Class<? extends Component>, ComponentType> registeredComponentTypes =
			new Hashtable<Class<? extends Component>, ComponentType>();
	
	public static ComponentType IsComponentRegistered( Component c ) {
		return registeredComponentTypes.get(c.getClass());
	}

	private int index;
	
	public ComponentType () {
		this.index = lastIndex;
		lastIndex++;
	}

	public static ComponentType RegisterComponentType(Class<? extends Component> c1) {
		ComponentType ct = registeredComponentTypes.get(c1);
		if( ct == null ) {
			ct = new ComponentType();
			registeredComponentTypes.put(c1, ct);
		}
		return ct;
	}

	public int getIndex() {
		return index;
	}

	public static int getIndexFor(Class<? extends Component> c) {
		return RegisterComponentType(c).getIndex();
	}
	
}
