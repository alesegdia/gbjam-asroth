package com.alesegdia.asroth.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sfx {

	public static Sound jump;
	public static Sound shoot;
	public static Sound hurt;
	public static Sound explo;
	public static Sound pwup1;
	public static Sound pwup2;
	
	public static Music music;
	
	public static void Initialize() {
		
		music = Gdx.audio.newMusic(Gdx.files.internal("asroth_theme.mp3"));
		
		jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
		ids[0] = jump.play(0);

		shoot = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.wav"));
		ids[1] = shoot.play(0);

		hurt = Gdx.audio.newSound(Gdx.files.internal("sounds/hurt.wav"));
		ids[2] = hurt.play(0);
		
		explo = Gdx.audio.newSound(Gdx.files.internal("sounds/explo.wav"));
		ids[3] = explo.play(0);
		
		pwup1 = Gdx.audio.newSound(Gdx.files.internal("sounds/pwup.wav"));
		ids[4] = pwup1.play(0);
		
		pwup2 = Gdx.audio.newSound(Gdx.files.internal("sounds/pwup2.wav"));
		ids[5] = pwup2.play(0);
		
		
	}
	
	public static int JUMP = 0;
	public static int SHOOT = 1;
	public static int HURT = 1;
	
	public static long ids[] = new long[10];
	
	public static void Play(int sfx) {
		if( sfx == 0 ) {
			jump.stop(ids[sfx]);
			ids[sfx] = jump.play(1f);
		} else if( sfx == 1 ) {
			shoot.stop(ids[sfx]);
			ids[sfx] = shoot.play(1f);
		} else if( sfx == 2 ) {
			hurt.stop(ids[sfx]);
			ids[sfx] = hurt.play(1f);
		} else if( sfx == 3 ) {
			explo.stop(ids[sfx]);
			ids[sfx] = explo.play(1f);
		} else if( sfx == 4 ) {
			pwup1.stop(ids[sfx]);
			ids[sfx] = pwup1.play(1f);
		} else if( sfx == 5 ) {
			pwup2.stop(ids[sfx]);
			ids[sfx] = pwup2.play(1f);
		}
	}
	
}
