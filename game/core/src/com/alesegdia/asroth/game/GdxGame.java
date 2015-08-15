package com.alesegdia.asroth.game;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.components.HealthComponent;
import com.alesegdia.asroth.components.PhysicsComponent;
import com.alesegdia.asroth.ecs.Entity;
import com.alesegdia.asroth.map.MapPhysicsBuilderVisitor;
import com.alesegdia.asroth.map.TiledTileMapConverter;
import com.alesegdia.asroth.physics.Physics;
import com.alesegdia.platgen.config.Config;
import com.alesegdia.platgen.config.ERDFSType;
import com.alesegdia.platgen.config.ERegionGenerator;
import com.alesegdia.platgen.generator.GeneratorPipeline;
import com.alesegdia.platgen.map.PlatformGenerator;
import com.alesegdia.platgen.map.TileMap;
import com.alesegdia.platgen.map.TileType;
import com.alesegdia.platgen.map.PlatformGenerator.Level;
import com.alesegdia.platgen.util.RNG;
import com.alesegdia.platgen.util.Vec2;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private SpriteBatch batch;
	private SpriteBatch sprBatch;
	
	Physics physics;
	GameWorld gameWorld;
	
	RNG rng;

	@Override
	public void create () {
		
		Gfx.Initialize();
		
		rng = new RNG();
		RNG.rng = rng;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameConfig.VIEWPORT_WIDTH, GameConfig.VIEWPORT_HEIGHT);
		camera.update();
		camera.zoom = 1f;

		OrthographicCamera ocam = camera;
		
		font = new BitmapFont();
		batch = new SpriteBatch();

		Config cfg = new Config();
		//cfg.mapSize = new Vec2(400,200);
		cfg.mapSize = new Vec2(400,400);
		
		cfg.regionGeneratorType = ERegionGenerator.BALANCED;
		
		cfg.minK = 0.25f;
		cfg.maxK = 0.75f;
		//cfg.numRegions = 7;
		cfg.numRegions = 20;
		
		cfg.rdfsType = ERDFSType.COMBINED;
		
		cfg.rasterRegionLimits = false;

		
		GeneratorPipeline gp = new GeneratorPipeline();
		TileMap tm = gp.generate(cfg);
		
		PlatformGenerator pg = new PlatformGenerator();
		pg.addLevel(new Level(10, 20, 10, 10));
		pg.addLevel(new Level(10, 20, 15, 15));
		pg.addLevel(new Level(10, 20, 25, 25));
		tm = pg.generate(tm);

		TiledTileMapConverter ttmc = new TiledTileMapConverter(tm);
		map = ttmc.process();
		renderer = new OrthogonalTiledMapRenderer(map, GameConfig.PIXELS_TO_METERS);
		
		physics = new Physics();
		
		MapPhysicsBuilderVisitor mpbv = new MapPhysicsBuilderVisitor(physics);
		gp.getLogicMap().regionTree.visit(mpbv);

		sprBatch = new SpriteBatch();
		gameWorld = new GameWorld(physics, sprBatch, camera, tm);
		GameWorld.instance = gameWorld;
		
		
	}
	
	@Override
	public void render () {
		float dt = Gdx.graphics.getRawDeltaTime();
		
		gameWorld.step();
		physics.step(dt);

		Gdx.gl.glClearColor(155f / 255f, 188f / 255f, 15f / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameWorld.setCam();
		camera.update();
		renderer.setView(camera);
		renderer.render();

		sprBatch.setProjectionMatrix(camera.combined);
		sprBatch.begin();
		gameWorld.render();
		sprBatch.end();
		
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		font.draw(batch, "entities: " + gameWorld.getNumEntities(), 80, 20);
		TextureRegion t = Gfx.hpTexture;

		batch.draw(Gfx.hpHud, 0, 0, 0, 0,
				Gfx.hpHud.getRegionWidth(), Gfx.hpHud.getRegionHeight(),
				3,3, 0);
		batch.draw(Gfx.wingsHud, 118*3, 0, 0, 0,
				Gfx.wingsHud.getRegionWidth(), Gfx.wingsHud.getRegionHeight(),
				3,3, 0);
		float trw = t.getRegionWidth();
		float trh = t.getRegionHeight();
		float s = 3;
		Entity pl = gameWorld.getPlayer();
		HealthComponent hc = (HealthComponent) pl.getComponent(HealthComponent.class);
		int numBars = (int) Math.floor(hc.currentHP * 8 / hc.maxHP);
		for( int i = 0; i < numBars; i++ ) {
			batch.draw(Gfx.hpTexture, 14*3 + i * trw * s, 2*3, trw*s, trh*s);	
		}
		
		for( int i = 0; i < 6; i++ ) {
			batch.draw(Gfx.hpTexture, 146*3 - i * trw * s, 2*3, -trw*s, trh*s);	
		}
		batch.end();

		physics.render(camera);
	}
}
