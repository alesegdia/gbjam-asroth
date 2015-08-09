package com.alesegdia.asroth;

import com.alesegdia.platgen.generator.IRegionTreeVisitor;
import com.alesegdia.platgen.generator.RegionTree;
import com.alesegdia.platgen.generator.Sector;

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
				physics.createRectBody(x, y, w, h, false);
			}
		}
	}

}
