package com.alesegdia.asroth.components;

import com.alesegdia.asroth.ecs.Component;

public class PlayerComponent extends Component{

	public boolean jumping;
	public boolean flying;
	public boolean mashing;
	public boolean justLandedMashing;
	public boolean dashingWall;
	public boolean releasingWall;
	public float releaseWallVelocity;
	public boolean canDash;
	public boolean isPressingDown;
	public boolean minimapEnabled = false;
	public boolean nearPortal = false;
	public boolean overOneWayPlat;
	

}
