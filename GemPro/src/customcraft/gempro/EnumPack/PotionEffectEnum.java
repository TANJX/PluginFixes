//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.EnumPack;

import org.bukkit.potion.PotionEffectType;

public enum PotionEffectEnum {
	伤害吸收(PotionEffectType.ABSORPTION),
	失明(PotionEffectType.BLINDNESS),
	混乱(PotionEffectType.CONFUSION),
	抗性提升(PotionEffectType.DAMAGE_RESISTANCE),
	急迫(PotionEffectType.FAST_DIGGING),
	防火(PotionEffectType.FIRE_RESISTANCE),
	瞬间伤害(PotionEffectType.HARM),
	瞬间治疗(PotionEffectType.HEAL),
	生命提升(PotionEffectType.HEALTH_BOOST),
	饥饿(PotionEffectType.HUNGER),
	力量(PotionEffectType.INCREASE_DAMAGE),
	隐身(PotionEffectType.INVISIBILITY),
	跳跃提升(PotionEffectType.JUMP),
	夜视(PotionEffectType.NIGHT_VISION),
	中毒(PotionEffectType.POISON),
	生命恢复(PotionEffectType.REGENERATION),
	饱食(PotionEffectType.SATURATION),
	缓慢(PotionEffectType.SLOW),
	挖掘疲劳(PotionEffectType.SLOW_DIGGING),
	速度(PotionEffectType.SPEED),
	水下呼吸(PotionEffectType.WATER_BREATHING),
	虚弱(PotionEffectType.WEAKNESS),
	凋零(PotionEffectType.WITHER);

	private final PotionEffectType potionEF;

	PotionEffectEnum(PotionEffectType potionEF) {
		this.potionEF = potionEF;
	}

	public PotionEffectType getText() {
		return this.potionEF;
	}
}
