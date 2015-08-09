package com.alesegdia.asroth.game;

import com.alesegdia.asroth.asset.Gfx;
import com.alesegdia.asroth.map.MapPhysicsBuilderVisitor;
import com.alesegdia.asroth.map.TiledTileMapConverter;
import com.alesegdia.platgen.generator.ERegionGenerator;
import com.alesegdia.platgen.generator.GeneratorPipeline;
import com.alesegdia.platgen.generator.IRegionGenerator;
import com.alesegdia.platgen.generator.LogicMap;
import com.alesegdia.platgen.generator.MapRasterizer;
import com.alesegdia.platgen.generator.RegionGeneratorBalanced;
import com.alesegdia.platgen.generator.SectorCreatorVisitor;
import com.alesegdia.platgen.generator.SectorGenerator;
import com.alesegdia.platgen.tilemap.TileMap;
import com.alesegdia.platgen.util.Vec2;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GdxGame extends ApplicationAdapter {

	Texture img;
	
	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private BitmapFont font;
	private SpriteBatch batch;
	
	Physics physics;
	Body player;
	
	@Override
	public void create () {
		
		Gfx.Initialize();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 320, 320);
		camera.update();
		//camera.zoom = 0.50f;
		camera.zoom = 4f;

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
		//player = physics.createCircleBody(0, 0, 10, true);
		player = physics.createRectBody(0, 0, 10, 10, true);
		
		MapPhysicsBuilderVisitor mpbv = new MapPhysicsBuilderVisitor(physics);
		gp.getLogicMap().regionTree.visit(mpbv);
	}

	@Override
	public void render () {
		
		float dt = Gdx.graphics.getRawDeltaTime();
		physics.step(dt);
		
		Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		batch.end();
		int dx = 0; int dy = 0;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			dx = -1;
		} else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			dx = 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			dy = -1;
		} else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			dy = 1;
		}
		
		camera.translate(new Vector2(dx*5*camera.zoom,dy*5*camera.zoom));
		physics.render(camera);
	}
}
