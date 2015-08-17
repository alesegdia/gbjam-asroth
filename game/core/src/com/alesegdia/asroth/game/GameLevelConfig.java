package com.alesegdia.asroth.game;

import com.alesegdia.platgen.config.Config;
import com.alesegdia.platgen.config.ERDFSType;
import com.alesegdia.platgen.config.ERegionGenerator;
import com.alesegdia.platgen.util.Vec2;

public class GameLevelConfig {
	
	public static Config configs[] = new Config[5];
	
	public static void Initialize() {
		/* LEVEL 1 **********************************************/
		configs[0] = new Config();
		configs[0].mapSize = new Vec2(100,100);
		configs[0].regionGeneratorType = ERegionGenerator.BALANCED;
		configs[0].minK = 0.25f;
		configs[0].maxK = 0.75f;
		configs[0].numRegions = 2;
		configs[0].rdfsType = ERDFSType.COMBINED;
		configs[0].rasterRegionLimits = false;
		/*********************************************************/


		/* LEVEL 2 **********************************************/
		configs[1] = new Config();
		configs[1].mapSize = new Vec2(150,150);
		configs[1].regionGeneratorType = ERegionGenerator.BALANCED;
		configs[1].minK = 0.25f;
		configs[1].maxK = 0.75f;
		configs[1].numRegions = 5;
		configs[1].rdfsType = ERDFSType.COMBINED;
		configs[1].rasterRegionLimits = false;
		/*********************************************************/

		
		/* LEVEL 3 **********************************************/
		configs[2] = new Config();
		configs[2].mapSize = new Vec2(200,200);
		configs[2].regionGeneratorType = ERegionGenerator.BALANCED;
		configs[2].minK = 0.25f;
		configs[2].maxK = 0.75f;
		configs[2].numRegions = 10;
		configs[2].rdfsType = ERDFSType.COMBINED;
		configs[2].rasterRegionLimits = false;
		/*********************************************************/

		/* LEVEL 4 **********************************************/
		configs[3] = new Config();
		configs[3].mapSize = new Vec2(300,300);
		configs[3].regionGeneratorType = ERegionGenerator.BALANCED;
		configs[3].minK = 0.25f;
		configs[3].maxK = 0.75f;
		configs[3].numRegions = 15;
		configs[3].rdfsType = ERDFSType.COMBINED;
		configs[3].rasterRegionLimits = false;
		/*********************************************************/

		/* LEVEL 5 **********************************************/
		configs[4] = new Config();
		configs[4].mapSize = new Vec2(400,400);
		configs[4].regionGeneratorType = ERegionGenerator.BALANCED;
		configs[4].minK = 0.25f;
		configs[4].maxK = 0.75f;
		configs[4].numRegions = 30;
		configs[4].rdfsType = ERDFSType.COMBINED;
		configs[4].rasterRegionLimits = false;
		/*********************************************************/
		
	}

}
