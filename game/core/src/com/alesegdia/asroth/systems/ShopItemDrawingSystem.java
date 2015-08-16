package com.alesegdia.asroth.systems;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.AnimationComponent;
import com.alesegdia.asroth.components.ShopComponent;
import com.alesegdia.asroth.components.TransformComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.ecs.EntitySystem;
import com.alesegdia.asroth.game.GameConfig;
import com.alesegdia.asroth.game.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

public class ShopItemDrawingSystem extends EntitySystem {

	private Batch batch;
	private BitmapFont customFont;

	public ShopItemDrawingSystem (Batch batch) {
		super(ShopComponent.class);
		this.batch = batch;
		customFont = new BitmapFont(Gdx.files.internal("visitor2.fnt"));
		customFont.getData().setScale(0.06f);
	}
	
	@Override
	public void notifyEntityRemoved(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(Entity e) {
		ShopComponent sc = (ShopComponent) e.getComponent(ShopComponent.class);
		TextureRegion tr = null;
		TransformComponent tc = (TransformComponent) e.getComponent(TransformComponent.class);
		
		if( !sc.isSold ) {
			switch(sc.vendingProduct) {
			case DEFAULTGUN: tr = Gfx.defaultSymbol; break;
			case SINEGUN: tr = Gfx.sineSymbol; break;
			case FIREBALL: tr = Gfx.fireballSymbol; break;
			case TRIPLASMA: tr = Gfx.triplasmaSymbol; break;
			case SACREDPUNCH: tr = Gfx.sacredSymbol; break;
			case WINGSCAPACITY: tr = Gfx.wingsPickupSheet.get(0); break;
			case HEALTHCAPACITY:tr = Gfx.healthPickupSheet.get(0); break;
			}
			AnimationComponent ac = (AnimationComponent) e.getComponent(AnimationComponent.class);
			int i = ac.currentAnim.getKeyFrameIndex(ac.currentTime);
			if( i == 2 ) {
				i = 0;
			} else if( i == 3) {
				i = -1;
			}
			batch.draw(tr,
					tc.position.x - tr.getRegionWidth()/2 * GameConfig.PIXELS_TO_METERS,
					tc.position.y + 5 * GameConfig.PIXELS_TO_METERS + i * GameConfig.PIXELS_TO_METERS,
					tr.getRegionWidth() * GameConfig.PIXELS_TO_METERS,
					tr.getRegionHeight() * GameConfig.PIXELS_TO_METERS);
		} else {
			/*
			Matrix4 old = new Matrix4(batch.getTransformMatrix());
			Matrix4 m = new Matrix4(batch.getTransformMatrix());
			m.setToTranslationAndScaling(tc.position.x * 2, tc.position.y * 2, 1f, 0.5f, 0.5f, 1);
			batch.setTransformMatrix(m);
			*/
			//customFont.draw(batch, "" + sc.refillingTimer, tc.position.x, tc.position.y);
			//batch.setTransformMatrix(old);
		}
	}

}
