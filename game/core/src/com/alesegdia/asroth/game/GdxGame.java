package com.alesegdia.asroth.game;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.map.MapPhysicsBuilderVisitor;
import com.alesegdia.asroth.map.TiledTileMapConverter;
import com.alesegdia.asroth.physics.Physics;
import com.alesegdia.platgen.generator.ERegionGenerator;
import com.alesegdia.platgen.generator.GeneratorPipeline;
import com.alesegdia.platgen.tilemap.TileMap;
import com.alesegdia.platgen.tilemap.TileType;
import com.alesegdia.platgen.util.Vec2;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GdxGame extends ApplicationAdapter {

	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private BitmapFont font;
	private SpriteBatch batch;
	private SpriteBatch sprBatch;
	
	Physics physics;
	GameWorld gameWorld;

	@Override
	public void create () {
		
		Gfx.Initialize();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameConfig.VIEWPORT_WIDTH, GameConfig.VIEWPORT_HEIGHT);
		camera.update();
		camera.zoom = 1f;

		font = new BitmapFont();
		batch = new SpriteBatch();

		GeneratorPipeline.Config cfg = new GeneratorPipeline.Config();
		cfg.mapSize = new Vec2(400,200);
		cfg.regionGeneratorType = ERegionGenerator.BALANCED;
		GeneratorPipeline gp = new GeneratorPipeline();
		TileMap tm = gp.generate(cfg);

		TiledTileMapConverter ttmc = new TiledTileMapConverter(tm);
		map = ttmc.process();
		renderer = new OrthogonalTiledMapRenderer(map, GameConfig.PIXELS_TO_METERS);
		
		physics = new Physics();
		
		MapPhysicsBuilderVisitor mpbv = new MapPhysicsBuilderVisitor(physics);
		gp.getLogicMap().regionTree.visit(mpbv);

		sprBatch = new SpriteBatch();
		gameWorld = new GameWorld(physics, sprBatch, camera, tm);
		
	}
	
	@Override
	public void render () {
		
		float dt = Gdx.graphics.getRawDeltaTime();
		gameWorld.step();
		physics.step(dt);
		
		Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
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
		batch.end();
		physics.render(camera);
	}
}
