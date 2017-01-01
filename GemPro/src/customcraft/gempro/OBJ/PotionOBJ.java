//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.OBJ;

public class PotionOBJ {
	public final String potionEffectName;
	public final String target;
	public final Integer level;
	public final Integer chance;
	public final Integer duration;

	public PotionOBJ(String potionEffectName, String target, Integer level, Integer duration, Integer chance) {
		this.potionEffectName = potionEffectName;
		this.level = level;
		this.target = target;
		this.chance = chance;
		this.duration = duration;
	}
}
