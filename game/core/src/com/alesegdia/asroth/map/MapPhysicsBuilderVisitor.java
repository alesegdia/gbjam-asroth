package com.alesegdia.asroth.map;

import com.alesegdia.asroth.game.GameConfig;
import com.alesegdia.asroth.physics.CollisionLayers;
import com.alesegdia.asroth.physics.Physics;
import com.alesegdia.platgen.region.IRegionTreeVisitor;
import com.alesegdia.platgen.region.RegionTree;
import com.alesegdia.platgen.sector.Sector;

public class MapPhysicsBuilderVisitor implements IRegionTreeVisitor {

	private Physics physics;

	public MapPhysicsBuilderVisitor( Physics physics ) {
		this.physics = physics;
	}
	
	@Override
	public void process(RegionTree rt) {
		for( Sector s : rt.sectors ) {
			if( !s.isGap ) {
				float w = s.size.x * 10 / 2;
				float x = s.position.x * 10 + w;
				float h = s.size.y * 5;
				float y = (s.position.y + s.height - s.region.position.y) * 10 + h;
				physics.createRectBody(
						x * GameConfig.PIXELS_TO_METERS,
						y * GameConfig.PIXELS_TO_METERS,
						w * GameConfig.PIXELS_TO_METERS,
						h * GameConfig.PIXELS_TO_METERS,
						CollisionLayers.CATEGORY_MAP, CollisionLayers.MASK_MAP, false);
			}
		}
	}

}
