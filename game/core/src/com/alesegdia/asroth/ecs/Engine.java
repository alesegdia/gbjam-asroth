package com.alesegdia.asroth.ecs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Engine {

	List<EntitySystem> systems = new ArrayList<EntitySystem>();
	List<Entity> entities = new LinkedList<Entity>();
	
	public void addEntity(Entity e) {
		entities.add(e);
		for( EntitySystem es : systems ) {
			es.entityAdded(e);
		}
	}
	
	public void step() throws InterruptedException {
		System.out.println("engine step!");
		for( EntitySystem es : systems ) {
			es.process();
		}
		cleanupEntities();
	}
	
	void cleanupEntities() {
		List<Entity> toDel = new ArrayList<Entity>();
		for( Entity e : entities ) {
			if( e.isDead ) {
				toDel.add(e);
			}
		}
		
		for( Entity e : toDel ) {
			entities.remove(e);
			for( EntitySystem es : systems ) {
				es.entityRemoved(e);
			}
		}
	}
	
	public void addSystem(EntitySystem es) {
		for( Entity e : entities ) {
			es.entityAdded(e);
		}
		systems.add(es);
	}
	
}
