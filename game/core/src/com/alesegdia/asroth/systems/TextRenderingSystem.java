package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TextRenderingSystem extends EntitySystem {

	private Batch batch;
	private BitmapFont font;
	private Camera cam;

	public TextRenderingSystem(Batch batch, Camera cam) {
		super(TextRenderingComponent.class, TransformComponent.class);
		this.batch = batch;
		this.font = new BitmapFont(Gdx.files.internal("visitor2.fnt"));
		this.cam = cam;
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		TextRenderingComponent trc = (TextRenderingComponent) e.getComponent(TextRenderingComponent.class);
		TransformComponent tc = (TransformComponent) e.getComponent(TransformComponent.class);
		font.draw(batch, trc.textToRender, tc.position.x - cam.position.x, tc.position.y - cam.position.y);
	}

}
