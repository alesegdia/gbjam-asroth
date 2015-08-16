package com.alesegdia.asroth.game;

import com.alesegdia.platgen.region.IRegionTreeVisitor;
import com.alesegdia.platgen.region.RegionTree;
import com.alesegdia.platgen.sector.Sector;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SectorMinimapRendererVisitor implements IRegionTreeVisitor {

	private ShapeRenderer renderer;
	private float dy;
	private float dx;

	public SectorMinimapRendererVisitor ( ShapeRenderer renderer, float dx, float dy ) {
		this.renderer = renderer;
		this.dx = dx; this.dy = dy;
	}
	
	@Override
	public void process(RegionTree rt) {
		for( Sector s : rt.sectors ) {
			if( !s.isGap ) {
				float w = s.size.x;
				float x = s.position.x;
				float h = s.size.y;
				float y = s.height;

				//renderer.rect(s.position.x + 100f, s.position.y + s.height, s.size.x, s.height);
				renderer.rect(dx + x, dy + y, w, h);
			}
		}
	}

}
