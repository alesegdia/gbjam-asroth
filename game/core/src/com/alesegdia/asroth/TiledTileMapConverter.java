package com.alesegdia.asroth;

import com.alesegdia.platgen.tilemap.TileMap;
import com.alesegdia.platgen.tilemap.TileType;
import com.alesegdia.platgen.util.RNG;
import com.alesegdia.platgen.util.Vec2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class TiledTileMapConverter {
	
	private AssetManager assetManager;
	private Texture tiles;
	private Texture texture;
	private TiledMap map;
	private TileMap tm;
	
	private Vec2 currentTile = new Vec2(0,0);
	
	public TiledTileMapConverter(TileMap tm) {
		this.tm = tm;
	}

	boolean CheckTileSurroundings( int horL, int horR, int verT, int verB ) {
		return 	tm.Get(currentTile.y, currentTile.x-1) == horL &&
				tm.Get(currentTile.y, currentTile.x+1) == horR &&
				tm.Get(currentTile.y-1, currentTile.x) == verT &&
				tm.Get(currentTile.y+1, currentTile.x) == verB;
	}

	TiledMap process() {
		tiles = new Texture(Gdx.files.internal("flyinGB_tiles.png"));
		TextureRegion[][] splitTiles = TextureRegion.split(tiles, 10, 10);
		map = new TiledMap();
		MapLayers layers = map.getLayers();
		RNG rng = new RNG();

		for (int l = 0; l < 1; l++) {
			TiledMapTileLayer layer = new TiledMapTileLayer(tm.cols, tm.rows, 10, 10);
			for (int x = 0; x < tm.cols; x++) {
				for (int y = 0; y < tm.rows; y++) {
					currentTile.Set(x,y);
					int tt = tm.Get(y, x);
					int ty = 0;
					int tx = 0;
					Cell cell = new Cell();
					if( tt == TileType.WALL ) {
						// 		( X - 1 )  	   ( X + 1 )      ( Y - 1 )      ( Y + 1 )
						if( CheckTileSurroundings( 	 		// BOTTOM
								TileType.WALL, TileType.WALL, TileType.FREE, TileType.WALL) ) { tx = 3; ty = 3;
						} else if( CheckTileSurroundings( 	// TOP
								TileType.WALL, TileType.WALL, TileType.WALL, TileType.FREE) ) { tx = 2; ty = 1;
						} else if( CheckTileSurroundings( 	// RIGHT
								TileType.WALL, TileType.FREE, TileType.WALL, TileType.WALL) ) { tx = 5; ty = 2;
						} else if( CheckTileSurroundings( 	// LEFT
								TileType.FREE, TileType.WALL, TileType.WALL, TileType.WALL) ) { tx = 1; ty = 2;
						} else if( CheckTileSurroundings( 	// TOP-LEFT
								TileType.FREE, TileType.WALL, TileType.WALL, TileType.FREE) ) { tx = 1; ty = 1;
						} else if( CheckTileSurroundings( 	// TOP-RIGHT
								TileType.WALL, TileType.FREE, TileType.WALL, TileType.FREE) ) { tx = 5; ty = 1;
						} else if( CheckTileSurroundings( 	// BOT-LEFT
								TileType.FREE, TileType.WALL, TileType.FREE, TileType.WALL) ) { tx = 1; ty = 3;
						} else if( CheckTileSurroundings( 	// BOT-RIGHT
								TileType.WALL, TileType.FREE, TileType.FREE, TileType.WALL) ) { tx = 5; ty = 3;
						} else if( CheckTileSurroundings(  	// ALL SOLID
								TileType.WALL, TileType.WALL, TileType.WALL, TileType.WALL) ) {
							if( rng.nextFloat() > 0.99 ) {
								tx = 2 + rng.nextInt(3); ty = 2;
							} else {
								tx = 0; ty = 0;
							}
						}
					} else {
						if( tm.Get(y-1, x) == TileType.WALL ) {
							tx = 2; ty = 0;
						} else {
							tx = 0; ty = 1;
						}
					}
					cell.setTile(new StaticTiledMapTile(splitTiles[ty][tx]));
					layer.setCell(x, y, cell);
				}
			}
			layers.add(layer);
		}
		return map;
	}

}
