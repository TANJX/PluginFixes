//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.EnumPack;

import org.bukkit.enchantments.Enchantment;

public enum EnchantmentEnum {
	力量(Enchantment.ARROW_DAMAGE),
	火矢(Enchantment.ARROW_FIRE),
	无限(Enchantment.ARROW_INFINITE),
	冲击(Enchantment.ARROW_KNOCKBACK),
	锋利(Enchantment.DAMAGE_ALL),
	节肢杀手(Enchantment.DAMAGE_ARTHROPODS),
	亡灵杀手(Enchantment.DAMAGE_UNDEAD),
	效率(Enchantment.DIG_SPEED),
	耐久(Enchantment.DURABILITY),
	火焰附加(Enchantment.FIRE_ASPECT),
	击退(Enchantment.KNOCKBACK),
	时运(Enchantment.LOOT_BONUS_BLOCKS),
	抢夺(Enchantment.LOOT_BONUS_MOBS),
	水下呼吸(Enchantment.OXYGEN),
	保护(Enchantment.PROTECTION_ENVIRONMENTAL),
	爆炸保护(Enchantment.PROTECTION_EXPLOSIONS),
	摔落保护(Enchantment.PROTECTION_FALL),
	火焰保护(Enchantment.PROTECTION_FIRE),
	弹射物保护(Enchantment.PROTECTION_PROJECTILE),
	精准采集(Enchantment.SILK_TOUCH),
	荆棘(Enchantment.THORNS),
	水下工作(Enchantment.WATER_WORKER);

	private final Enchantment ench;

	EnchantmentEnum(Enchantment ench) {
		this.ench = ench;
	}

	public Enchantment getText() {
		return this.ench;
	}
}
