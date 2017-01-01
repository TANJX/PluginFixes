//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.OBJ;

import customcraft.gempro.OBJ.PotionOBJ;
import java.util.List;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;

public class GemOBJ {
	public final String gemName;
	public final String extraDamage;
	public final String realDamage;
	public final String critMultiple;
	public final String ignoreAttack;
	public final String extraDefense;
	public final String suckBlood;
	public final List<PotionOBJ> potionEffect;
	public final Map<Enchantment, Integer> enchantmentEffect;
	public final Integer kind;
	public final Integer type;

	public GemOBJ(String gemName, Integer kind, String extraDamage, String realDamage, String critMultiple, String ignoreAttack, String extraDefense, String suckBlood, List<PotionOBJ> potionEffect, Map<Enchantment, Integer> enchantmentEffect, Integer type) {
		this.gemName = gemName;
		this.kind = kind;
		this.extraDamage = extraDamage;
		this.realDamage = realDamage;
		this.critMultiple = critMultiple;
		this.ignoreAttack = ignoreAttack;
		this.extraDefense = extraDefense;
		this.suckBlood = suckBlood;
		this.potionEffect = potionEffect;
		this.enchantmentEffect = enchantmentEffect;
		this.type = type;
	}
}
