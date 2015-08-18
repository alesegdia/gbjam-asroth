package com.alesegdia.asroth.game;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.asset.Sfx;
import com.alesegdia.asroth.components.BuyerComponent;
import com.alesegdia.asroth.components.HealthComponent;
import com.alesegdia.asroth.components.InfiniteFlyComponent;
import com.alesegdia.asroth.components.InvincibilityComponent;
import com.alesegdia.asroth.components.MashComponent;
import com.alesegdia.asroth.components.MoneyComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.components.PlayerComponent;
import com.alesegdia.asroth.components.ShopComponent;
import com.alesegdia.asroth.components.VanishingComponent;
import com.alesegdia.asroth.components.WeaponComponent;
import com.alesegdia.asroth.components.WingsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.map.MapPhysicsBuilderVisitor;
import com.alesegdia.asroth.map.TiledTileMapConverter;
import com.alesegdia.asroth.physics.Physics;
import com.alesegdia.asroth.systems.CrossComponent;
import com.alesegdia.platgen.config.Config;
import com.alesegdia.platgen.config.ERDFSType;
import com.alesegdia.platgen.config.ERegionGenerator;
import com.alesegdia.platgen.generator.GeneratorPipeline;
import com.alesegdia.platgen.map.LogicMap;
import com.alesegdia.platgen.map.PlatformGenerator;
import com.alesegdia.platgen.map.TileMap;
import com.alesegdia.platgen.map.TileType;
import com.alesegdia.platgen.map.PlatformGenerator.Level;
import com.alesegdia.platgen.util.RNG;
import com.alesegdia.platgen.util.Vec2;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class GdxGame extends ApplicationAdapter {

	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private BitmapFont font;
	private BitmapFont warningFont;
	private SpriteBatch batch;
	private SpriteBatch sprBatch;
	BitmapFont customFont;
	SectorMinimapRendererVisitor minimapRenderer;

	Physics physics;
	GameWorld gameWorld;
	
	ShapeRenderer srenderer;
	
	RNG rng;
	
	LogicMap lm;
	TileMap tm;
	
	int currentLevel = 0;
	
	@Override
	public void create () {
		
		Gfx.Initialize();
		Sfx.Initialize();
		GameLevelConfig.Initialize();
		
		rng = new RNG();
		RNG.rng = rng;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameConfig.VIEWPORT_WIDTH, GameConfig.VIEWPORT_HEIGHT);
		camera.update();
		camera.zoom = 1f;

		customFont = new BitmapFont(Gdx.files.internal("visitor.fnt"));
		font = new BitmapFont(Gdx.files.internal("visitor.fnt"));
		font.getData().setScale(0.5f);
		
		warningFont = new BitmapFont(Gdx.files.internal("visitor.fnt"));
		warningFont.getData().setScale(2f);
		
		customFont.setColor(139f/255f, 172f/255f, 15f/255f, 1f);
		font.setColor(139f/255f, 172f/255f, 15f/255f, 1f);
		warningFont.setColor(15f/255f, 56f/255f,  15f/255f, 1f);
		
		batch = new SpriteBatch();

		srenderer = new ShapeRenderer();
		srenderer.setAutoShapeType(true);

		physics = new Physics();
		sprBatch = new SpriteBatch();

		
		gameWorld = new GameWorld(physics, sprBatch, camera);		
		
	}
	
	void ClearWorld() {
	}
	
	void GenLevel( ) {

		gameWorld.engine.Clear();
		physics.Dispose();

		Config cfg = GameLevelConfig.configs[currentLevel];

		GeneratorPipeline gp = new GeneratorPipeline();
		tm = gp.generate(cfg);
		
		lm = gp.getLogicMap();
		
		PlatformGenerator pg = new PlatformGenerator();
		pg.addLevel(new Level(10, 20, 10, 10));
		pg.addLevel(new Level(10, 20, 15, 15));
		pg.addLevel(new Level(10, 20, 25, 25));
		tm = pg.generate(tm);

		TiledTileMapConverter ttmc = new TiledTileMapConverter(tm);
		map = ttmc.process();
		renderer = new OrthogonalTiledMapRenderer(map, GameConfig.PIXELS_TO_METERS);
		
		
		MapPhysicsBuilderVisitor mpbv = new MapPhysicsBuilderVisitor(physics);
		gp.getLogicMap().regionTree.visit(mpbv);

		gameWorld.InitWorld(tm);
		GameWorld.instance = gameWorld;
		
		minimapRenderer = new SectorMinimapRendererVisitor(srenderer,
				GameConfig.WINDOW_WIDTH/2 - tm.rows/4,
				GameConfig.WINDOW_HEIGHT/2 - tm.cols/4);
		
	}
	
	public void gameStep() {
		
		float dt = Gdx.graphics.getRawDeltaTime();

		// step world
		gameWorld.step();
		physics.step(dt);

		// prepare render
		Gdx.gl.glClearColor(155f / 255f, 188f / 255f, 15f / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameWorld.setCam();
		camera.update();
		
		// render map
		renderer.setView(camera);
		renderer.render();

		// render world
		sprBatch.setProjectionMatrix(camera.combined);
		sprBatch.begin();
		gameWorld.render();
		sprBatch.end();
		
		batch.begin();
		/*
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		font.draw(batch, "entities: " + gameWorld.getNumEntities(), 80, 20);
		*/
		TextureRegion t = Gfx.hpTexture;

		batch.draw(Gfx.upHudTexture, 0, 390, 0, 0,
				Gfx.upHudTexture.getRegionWidth(), Gfx.upHudTexture.getRegionHeight(),
				3,3, 0);
		float trw = t.getRegionWidth();
		float trh = t.getRegionHeight();
		float s = 3;
		Entity pl = gameWorld.getPlayer();
		
		/* HEALTH BAR ********************************************************/
		HealthComponent hc = (HealthComponent) pl.getComponent(HealthComponent.class);
		int numBars = (int) Math.floor(hc.currentHP * 8 / hc.maxHP);
		
		batch.draw(Gfx.hpHud.getTexture(),    0,    0, 14*3, 12*3, 0, 0, 14, 12, false, false);
		int capacity = hc.maxHP/5;
		numBars = hc.currentHP/5;
		for( int i = 0; i < capacity; i++ ) {
			float x = 14*3 + i * 3*3;
			float y = 0;
			float w = 3*3;
			float h = 12 * 3;
			batch.draw(Gfx.hpHud.getTexture(), x, y, w, h, 14, 0, 3, 12, false, false);			
		}
		batch.draw(Gfx.hpHud.getTexture(),    14*3 + (capacity) * 3 * 3,    0, 3*3, 12*3, 38, 0, 3, 12, false, false);
		for( int i = 0; i < numBars; i++ ) {
			batch.draw(Gfx.hpTexture, 14*3 + i * trw * s, 2*3, trw*s, trh*s);	
		}

		/* WINGS BAR *********************************************************/
		WingsComponent wc = (WingsComponent) pl.getComponent(WingsComponent.class);
		capacity = wc.maxCapacity / GameConstants.FLY_TANK_SIZE;
		numBars = wc.currentBoost / GameConstants.FLY_TANK_SIZE;
		batch.draw(Gfx.wingsHud.getTexture(), GameConfig.WINDOW_WIDTH - 14*3, 0, 14*3, 12*3, 28, 0, 14, 12, false, false);
		for( int i = 0; i < capacity; i++ ) {
			float x = GameConfig.WINDOW_WIDTH - 14*3 - (i+1) * 3 * 3;
			float y = 0;
			float w = 3*3;
			float h = 12 * 3;
			batch.draw(Gfx.wingsHud.getTexture(), x, y, w, h, 4, 0, 3, 12, false, false);
		}
		batch.draw(Gfx.wingsHud.getTexture(), GameConfig.WINDOW_WIDTH - 14*3 - (capacity+1) * 3 * 3 - 3,    0, 4*3, 12*3, 0, 0, 4, 12, false, false);

		for( int i = 0; i < numBars; i++ ) {
			batch.draw(Gfx.hpTexture, 146*3 - i * trw * s, 2*3, -trw*s, trh*s);
		}
		/**********************************************************************/

		renderWeaponSlot(batch, 70, 6);
		
		//batch.setColor(48f/255f, 98f/255f, 48f/255f, 1f);
		batch.setColor(1,0,0,1);
		MoneyComponent mc = (MoneyComponent) pl.getComponent(MoneyComponent.class);
		customFont.draw(batch, "X " + mc.currency, 70, 435);
		batch.setColor(1f, 1f, 1f, 1f);
		
		BuyerComponent bc = (BuyerComponent) pl.getComponent(BuyerComponent.class);
		if( bc.standingShop != null ) {
			String text = "";
			ShopComponent sc = (ShopComponent) bc.standingShop.getComponent(ShopComponent.class);
			int price = ShopConfig.getPriceFor(sc.vendingProduct);
			VanishingComponent vc = (VanishingComponent) bc.standingShop.getComponent(VanishingComponent.class);
			if( vc.isVanishing ) {
				if( vc.willTurn ) {
					text = "Hehehe... lets see what you can do!";
				} else {
					text = "Keep an eye on my brothers, pal.";
				}
			} else if( sc.refillingTimer <= 0 ) {
				if( mc.currency - price >= 0 ) {
					text = "Press B to buy [" + ShopConfig.getNameFor(sc.vendingProduct) + "] for " + price + "¢";
				} else {
					text = "To buy [" + ShopConfig.getNameFor(sc.vendingProduct) + "], you need " + price + "¢";
				}
			} else {
				text = "Replenishing stock... wait " + ((int)sc.refillingTimer) + " seconds.";
			}
			font.draw(batch, text, 10, 470);
		} else {
			String text = "";
			InvincibilityComponent ic = (InvincibilityComponent) pl.getComponent(InvincibilityComponent.class);
			InfiniteFlyComponent ifc = (InfiniteFlyComponent) pl.getComponent(InfiniteFlyComponent.class);
			PlayerComponent plc = (PlayerComponent) pl.getComponent(PlayerComponent.class);
			if( ic.timer > 0 ) {
				text = "You are AWESOME! " + ((int)ic.timer) + " seconds left.";
			} else if( ifc.timer > 0 ) {
				text = "Fly... fly... FLY! " + ((int)ifc.timer) + " seconds left.";
			} else if( plc.nearPortal ){
				CrossComponent cc = (CrossComponent) pl.getComponent(CrossComponent.class);
				int crossesLeft = cc.neededCrosses - cc.currentCrosses;
				crossesLeft = Math.max(crossesLeft, 0);
				if( crossesLeft != 0 ) {
					text = crossesLeft + " [CrossK] to travel next dimension.";
				} else {
					text = "Press T to travel next dimension!";
				}
			} else {
				text = "";
			}
			font.draw(batch, text, 10, 470);
		}
		batch.end();

		//physics.render(camera);

		PlayerComponent plc = (PlayerComponent) pl.getComponent(PlayerComponent.class);
		if( plc.minimapEnabled ) {
			srenderer.setColor(48f/255f, 98f/255f, 48f/255f, 1f);
			srenderer.begin();
			srenderer.set(ShapeType.Filled);
			lm.regionTree.visit(minimapRenderer);
	
			srenderer.setColor(1f,0f,0f, 1f);
			srenderer.rect(
					camera.position.x + GameConfig.WINDOW_WIDTH/2 - tm.rows/4,
					camera.position.y + GameConfig.WINDOW_HEIGHT/2 - tm.cols/4,
					2,2);
		}

		srenderer.end();
		
		warningTimer += Gdx.graphics.getDeltaTime();
		
		if( camera.position.y < 0 ) {
			float sin = (float) Math.sin(warningTimer*20);
			if( sin > 0 ) {
				batch.begin();
				warningFont.draw(batch, "WARNING!", 50, 300);
				batch.end();
			}
		}
		
	}
	
	float warningTimer = 0;
	
	enum GameState {
		MAIN, GAME, MIDLEVEL, WIN, LOSE
	}
	
	GameState currentState = GameState.MAIN;

	String midLevelText = "";
	
	void mainScreenStep() {
		if( Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			currentState = GameState.GAME;
			GenLevel();
		}
		mainTimer += Gdx.graphics.getDeltaTime();
		batch.begin();
		batch.draw(Gfx.mainAnim.getKeyFrame(mainTimer, true), 0, 0);
		batch.end();
	}
	
	@Override
	public void render () {
		
		Sfx.PlayMusic();

		if(currentState == GameState.GAME) {
			gameStep();
			
			// check if level is finished
			CrossComponent cc = (CrossComponent) gameWorld.getPlayer().getComponent(CrossComponent.class);
			PlayerComponent plc = (PlayerComponent) gameWorld.getPlayer().getComponent(PlayerComponent.class);
			if( cc.currentCrosses >= cc.neededCrosses && Gdx.input.isKeyJustPressed(Input.Keys.T) && plc.nearPortal ) {
				// ok this level
				if( currentLevel < 4 ) {
					midLevelText = "You are goin to dimension " + (currentLevel + 2);
					currentState = GameState.MIDLEVEL;
				} else {
					currentState = GameState.WIN;
				}
			}
			
			// check if player has died
			if( gameWorld.isPlayerDead() ) {
				currentState = GameState.LOSE;
			}
		} else if( currentState == GameState.MAIN ) {
			currentLevel = 0;
			mainScreenStep();
		} else if( currentState == GameState.MIDLEVEL ) {
			if( Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ) {
				currentLevel++;
				MashComponent mc1 = (MashComponent) gameWorld.getPlayer().getComponent(MashComponent.class);
				WeaponComponent wc1 = (WeaponComponent) gameWorld.getPlayer().getComponent(WeaponComponent.class);
				MoneyComponent moc1 = (MoneyComponent) gameWorld.getPlayer().getComponent(MoneyComponent.class);
				GenLevel();
				MashComponent mc2 = (MashComponent) gameWorld.getPlayer().getComponent(MashComponent.class);
				WeaponComponent wc2 = (WeaponComponent) gameWorld.getPlayer().getComponent(WeaponComponent.class);
				MoneyComponent moc2 = (MoneyComponent) gameWorld.getPlayer().getComponent(MoneyComponent.class);
				mc2.number = mc1.number;
				mc2.power = mc2.power;
				wc2.weaponModel = wc1.weaponModel;
				moc2.currency = moc1.currency;
				CrossComponent cc = (CrossComponent) gameWorld.getPlayer().getComponent(CrossComponent.class);
				cc.neededCrosses = this.currentLevel+1;
				currentState = GameState.GAME;
			}
			Gdx.gl.glClearColor(15f / 255f, 56f / 255f, 15f / 255f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, midLevelText, 10, 470);
			font.draw(batch, KillStatsComponent.instance.toString(), 10, 400);
			batch.end();
		} else if( currentState == GameState.WIN ) {
			if( Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ) {
				currentLevel = 0;
				currentState = GameState.MAIN;
				KillStatsComponent.instance.clear();
			}
			Gdx.gl.glClearColor(15f / 255f, 56f / 255f, 15f / 255f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, "Asroth managed to escape!", 10, 470);
			font.draw(batch, KillStatsComponent.instance.toString(), 10, 400);
			batch.end();
		} else if( currentState == GameState.LOSE ) {
			if( Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ) {
				currentLevel = 0;
				currentState = GameState.MAIN;
				KillStatsComponent.instance.clear();
			}
			Gdx.gl.glClearColor(15f / 255f, 56f / 255f, 15f / 255f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, "Asroth has died!", 10, 470);
			font.draw(batch, KillStatsComponent.instance.toString(), 10, 400);
			batch.end();
		}
	}
	
	private float mainTimer = 0;

	private void renderWeaponSlot(SpriteBatch batch2, int i, int j) {
		Entity pl = gameWorld.getPlayer();

		TextureRegion t;
		float sw, sh;
		t = Gfx.wepslot;
		sw = t.getRegionWidth() * 3;
		sh = t.getRegionHeight() * 3;
		batch.draw(t, i*3,j*3, sw, sh);
		
		WeaponComponent wep = (WeaponComponent) pl.getComponent(WeaponComponent.class);
		float trw, trh, tx, ty;
		t = wep.weaponModel.tr;
		trw = t.getRegionWidth() * 3;
		trh = t.getRegionHeight() * 3;
		tx = i * 3 + sw / 2 - trw / 2;
		ty = j * 3 + sh / 2 - trh / 2;
		
		batch.draw(t, tx,ty, trw, trh);		
		
	}
}
