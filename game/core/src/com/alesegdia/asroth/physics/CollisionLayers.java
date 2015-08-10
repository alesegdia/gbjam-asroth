package com.alesegdia.asroth.physics;

public class CollisionLayers {

	public static final short CATEGORY_MAP 			= 0x0001;
	public static final short CATEGORY_PLAYER 		= 0x0002;
	public static final short CATEGORY_PLBULLETS 	= 0x0004;
	public static final short CATEGORY_ENEMY 		= 0x0008;
	public static final short CATEGORY_ENBULLETS 	= 0x0010;
	public static final short CATEGORY_1WAYPLATS 	= 0x0020;
	
	public static final short MASK_MAP =
			CATEGORY_PLAYER 	| CATEGORY_PLBULLETS 	|
			CATEGORY_ENBULLETS	| CATEGORY_ENBULLETS 	|
			CATEGORY_1WAYPLATS;
	
	public static final short MASK_PLAYER =
			CATEGORY_ENEMY		| CATEGORY_ENBULLETS 	|
			CATEGORY_1WAYPLATS	| CATEGORY_MAP;
	
	public static final short MASK_PLBULLETS =
			CATEGORY_ENEMY | CATEGORY_1WAYPLATS | CATEGORY_MAP;
	
	public static final short MASK_ENEMY =
			CATEGORY_PLAYER		| CATEGORY_PLBULLETS 	|
			CATEGORY_1WAYPLATS	| CATEGORY_MAP;
	
	public static final short MASK_ENBULLETS =
			CATEGORY_PLAYER | CATEGORY_1WAYPLATS | CATEGORY_MAP;
	
	public static final short MASK_1WAYPLATS =
			CATEGORY_PLAYER | CATEGORY_ENEMY | CATEGORY_PLBULLETS | CATEGORY_ENBULLETS;
	
	

	
	
	
}
