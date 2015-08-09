package com.alesegdia.asroth.ecs;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public abstract class EntitySystem implements IEntityListener {

	private BitSet familyBits;
	private List<Entity> entities = new LinkedList<Entity>();
	public String name;
	
	public EntitySystem(Class<? extends Component>... componentClasses) {
		setComponentsFamily(componentClasses);
	}
	
	public void setComponentsFamily(Class<? extends Component>... componentClasses) {
		familyBits = new BitSet(64);
		for( Class<? extends Component> c : componentClasses ) {
			familyBits.set(ComponentType.getIndexFor(c));
		}
	}

	boolean checkEntityBits( Entity e ) {
		BitSet result = (BitSet) familyBits.clone();
		result.and(e.getBits());
		return result.equals(familyBits);
	}
	
	@Override
	public void entityAdded(Entity e) {
		if( checkEntityBits(e) ) {
			System.out.println("adding entity " + e + " from " + this);
			entities.add(e);
		}
	}

	@Override
	public void entityRemoved(Entity e) {
		if( checkEntityBits(e) ) {
			System.out.println("removing entity " + e + " from " + this);
			entities.remove(e);
		}
	}
	
	public void process() {
		for( Entity e : entities ) {
			process(e);
		}
	}

	public void process(Entity e) {
		// TODO Auto-generated method stub	
	}
	
}
