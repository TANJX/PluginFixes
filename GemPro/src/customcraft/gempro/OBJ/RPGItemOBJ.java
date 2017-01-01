//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.OBJ;

import org.bukkit.enchantments.Enchantment;

import java.util.List;
import java.util.Map;

public class RPGItemOBJ {
    public String extraDamage;
    public String realDamage;
    public String critMultiple;
    public String extraDefense;
    public String suckBlood;
    public String ignoreAttack;
    public List<PotionOBJ> potionEffect;
    private final Map<Enchantment, Integer> enchantmentEffect;
    public final List<String> desc;
    public final Integer punch;
    public Integer socket;

    public RPGItemOBJ(String extraDamage, String realDamage, String critMultiple, String ignoreAttack, String extraDefense, String suckBlood, List<PotionOBJ> potionEffect, Integer punch, Integer socket, List<String> desc, Map<Enchantment, Integer> enchantmentEffect) {
        this.extraDamage = extraDamage;
        this.realDamage = realDamage;
        this.critMultiple = critMultiple;
        this.ignoreAttack = ignoreAttack;
        this.extraDefense = extraDefense;
        this.suckBlood = suckBlood;
        this.potionEffect = potionEffect;
        this.punch = punch;
        this.socket = socket;
        this.desc = desc;
        this.enchantmentEffect = enchantmentEffect;
    }
}
