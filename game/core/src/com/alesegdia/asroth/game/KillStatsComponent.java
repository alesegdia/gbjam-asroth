package com.alesegdia.asroth.game;

public class KillStatsComponent {

	public void sum( KillStatsComponent other ) {
		for( int i = 0; i < EnemyType.MAX_ENEMIES; i++ ) {
			kills[i] += other.kills[i];
		}
	}
	
	public int kills[] = new int[EnemyType.MAX_ENEMIES];
	
	public KillStatsComponent () {
		clear();
	}

	public void clear() {
		for( int i = 0; i < EnemyType.MAX_ENEMIES; i++ ) {
			kills[i] = 0;
		}
	}
	
	public static KillStatsComponent instance = new KillStatsComponent();
	
	public String toString() {
		return
				kills[EnemyType.ZOMBIE] 		+ " zombies butchered!\n" +
				kills[EnemyType.RUNNER] 		+ " runners crushed!\n" +
				kills[EnemyType.JUMPER] 		+ " jumpers annihilated!\n" +
				kills[EnemyType.THREEHEADED]	+ " three-headeds slaughtered!\n" +
				kills[EnemyType.SUMMONER] 		+ " summoners murdered!\n" +
				kills[EnemyType.CHERUB] 		+ " evil cherubs impaled\n" +
				kills[EnemyType.MASK] 			+ " crying masks assassinated!\n" +
				kills[EnemyType.DEMON] 			+ " demons slained!\n";
	}
	
}
