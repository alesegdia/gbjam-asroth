package com.alesegdia.asroth.test;

import com.alesegdia.asroth.ecs.Component;
import com.alesegdia.asroth.ecs.Engine;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;

public class Test_ECS {

	public static class C1 extends Component { }
	public static class C2 extends Component { }
	public static class C3 extends Component { }
	public static class C4 extends Component { }
	
	public static class ES1 extends EntitySystem {
		public ES1() { super(C1.class, C2.class); }

		@Override
		public void notifyEntityRemoved(Entity e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void process(Entity e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static class ES2 extends EntitySystem {
		public ES2() { super(C2.class, C4.class); }

		@Override
		public void notifyEntityRemoved(Entity e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void process(Entity e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static class ES3 extends EntitySystem {
		public ES3() { super(C3.class, C1.class); }

		@Override
		public void notifyEntityRemoved(Entity e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void process(Entity e) {
			// TODO Auto-generated method stub
			
		}
	}	
	
	public static void main(String[] args) {
		ES1 es1 = new ES1();
		ES2 es2 = new ES2();
		ES3 es3 = new ES3();
		Engine eng = new Engine();

		eng.addSystem(es1);
		eng.addSystem(es2);
		eng.addSystem(es3);
		
		Entity e1 = new Entity();
		e1.name = "E1";
		e1.addComponent(new C1());
		e1.addComponent(new C2());
		e1.addComponent(new C3());
		
		Entity e2 = new Entity();
		e2.name = "E2";
		e2.addComponent(new C1());
		e2.addComponent(new C2());
		
		Entity e3 = new Entity();
		e3.name = "E3";
		e3.addComponent(new C1());
		e3.addComponent(new C2());
		
		Entity e4 = new Entity();
		e4.name = "E4";
		e4.addComponent(new C4());
		
		eng.addEntity(e1);
		eng.addEntity(e2);
		eng.addEntity(e3);
		
		eng.step();
		e1.isDead = true;
		eng.step();
		e4.isDead = true;
		eng.step();
		e2.isDead = true;
		eng.step();
		
	}

}
