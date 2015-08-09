package com.alesegdia.asroth;

import com.alesegdia.platgen.generator.IRegionGenerator;
import com.alesegdia.platgen.generator.LogicMap;
import com.alesegdia.platgen.generator.MapRasterizer;
import com.alesegdia.platgen.generator.RegionGeneratorBalanced;
import com.alesegdia.platgen.generator.SectorCreatorVisitor;
import com.alesegdia.platgen.generator.SectorGenerator;
import com.alesegdia.platgen.tilemap.TileMap;
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
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GdxGame extends ApplicationAdapter {

	Texture img;
	
	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private BitmapFont font;
	private SpriteBatch batch;
	
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 320, 320);
		camera.update();
		camera.zoom = 0.50f;

		font = new BitmapFont();
		batch = new SpriteBatch();
		
		IRegionGenerator g = new RegionGeneratorBalanced();
		//RegionGenerator g = new RegionGenerator();
		LogicMap lm = g.Generate(200, 100);
		SectorGenerator sg = new SectorGenerator();
		SectorCreatorVisitor scv = new SectorCreatorVisitor(sg);
		lm.regionTree.visit(scv);
		MapRasterizer mr = new MapRasterizer(lm);
		TileMap tm = mr.raster();

		TiledTileMapConverter ttmc = new TiledTileMapConverter(tm);
		map = ttmc.process();
		renderer = new OrthogonalTiledMapRenderer(map);		
	}

	@Override
	public void render () {
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
		camera.translate(new Vector2(dx*4,dy*4));
	}
}
