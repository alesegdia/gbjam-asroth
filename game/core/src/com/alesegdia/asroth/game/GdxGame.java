package com.alesegdia.asroth.game;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.map.MapPhysicsBuilderVisitor;
import com.alesegdia.asroth.map.TiledTileMapConverter;
import com.alesegdia.platgen.generator.ERegionGenerator;
import com.alesegdia.platgen.generator.GeneratorPipeline;
import com.alesegdia.platgen.tilemap.TileMap;
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

	Texture img;
	
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
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 320, 320);
		camera.update();
		camera.zoom = 1.f;
		//camera.zoom = 4f;

		font = new BitmapFont();
		batch = new SpriteBatch();

		GeneratorPipeline.Config cfg = new GeneratorPipeline.Config();
		cfg.mapSize = new Vec2(400,200);
		cfg.regionGeneratorType = ERegionGenerator.BALANCED;
		GeneratorPipeline gp = new GeneratorPipeline();
		TileMap tm = gp.generate(cfg);

		TiledTileMapConverter ttmc = new TiledTileMapConverter(tm);
		map = ttmc.process();
		renderer = new OrthogonalTiledMapRenderer(map);
		
		physics = new Physics();
		
		MapPhysicsBuilderVisitor mpbv = new MapPhysicsBuilderVisitor(physics);
		gp.getLogicMap().regionTree.visit(mpbv);

		sprBatch = new SpriteBatch();
		gameWorld = new GameWorld(physics, sprBatch, camera);
	}
	
	@Override
	public void render () {
		
		float dt = Gdx.graphics.getRawDeltaTime();
		physics.step(dt);

		gameWorld.step();
		
		Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
