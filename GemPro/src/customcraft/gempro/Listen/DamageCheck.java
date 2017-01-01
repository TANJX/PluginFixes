//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro.Listen;

import customcraft.gempro.EnumPack.Msg;
import customcraft.gempro.EnumPack.PotionEffectEnum;
import customcraft.gempro.Handler.Util;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.List;

public class DamageCheck implements Listener {
    private final HashMap<Player, Double> extraDefenseMap = new HashMap<>();

    @EventHandler(
            priority = EventPriority.LOW,
            ignoreCancelled = true
    )
    public void onDefense(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && (e.getDamager() instanceof Damageable || e.getDamager() instanceof Projectile)) {
            Player defense = (Player) e.getEntity();
            if (!this.canUse(defense)) {
                return;
            }

            ItemStack[] item = defense.getInventory().getArmorContents();
            if (item != null) {
                Object damager;
                if (e.getDamager() instanceof Projectile) {
                    Projectile extraDefense = (Projectile) e.getDamager();
                    if (extraDefense.getShooter() == null || !(extraDefense.getShooter() instanceof Damageable)) {
                        return;
                    }

                    damager = extraDefense.getShooter();
                } else {
                    damager = e.getDamager();
                }

                double var13 = 0.0D;

                for (ItemStack item1 : item) {
                    if (Util.instance.isRPGItem(item1)) {
                        List lores = item1.getItemMeta().getLore();
                        Integer[] value = Util.instance.getLoreValue((String) lores.get(4));
                        if (!e.isCancelled() && value != null && value[0] > Util.instance.rand(100)) {
                            e.setCancelled(true);
                        }

                        value = Util.instance.getLoreValue((String) lores.get(5));
                        if (value != null && value[0] > Util.instance.rand(100)) {
                            var13 += (double) (Util.instance.rand(value[2] - value[1] + 1) + value[1]);
                        }

                        this.extraDefenseMap.put(defense, var13);
                        this.potionEffectCheck(lores, (LivingEntity) damager, defense);
                    }
                }
            }
        }

    }

    @SuppressWarnings("deprecation")
    @EventHandler(
            priority = EventPriority.NORMAL,
            ignoreCancelled = true
    )
    public void onAttack(EntityDamageByEntityEvent e) {
        if ((e.getDamager() instanceof Player || e.getDamager() instanceof Projectile) && e.getEntity() instanceof Damageable) {
            Player damager;
            if (e.getDamager() instanceof Player) {
                damager = (Player) e.getDamager();
            } else {
                Projectile defense = (Projectile) e.getDamager();
                if (defense.getShooter() == null || !(defense.getShooter() instanceof Player)) {
                    return;
                }

                damager = (Player) defense.getShooter();
            }

            if (!this.canUse(damager)) {
                return;
            }

            if (damager.getItemInHand() != null && damager.getItemInHand().getType() != Material.AIR) {
                Damageable defense1 = (Damageable) e.getEntity();
                ItemStack item = damager.getItemInHand();
                if (Util.instance.isRPGItem(item)) {
                    List lores = item.getItemMeta().getLore();
                    Integer[] value = Util.instance.getLoreValue((String) lores.get(3));
                    double extraDefense;
                    if (value != null && value[0] > Util.instance.rand(100)) {
                        extraDefense = e.getDamage() * (double) (Util.instance.rand(value[2] - value[1] + 1) + value[1]);
                        if (extraDefense < 0.0D) {
                            extraDefense = 0.0D;
                        }

                        e.setDamage(extraDefense);
                    }

                    value = Util.instance.getLoreValue((String) lores.get(1));
                    if (value != null && value[0] > Util.instance.rand(100)) {
                        extraDefense = e.getDamage() + (double) Util.instance.rand(value[2] - value[1] + 1) + (double) value[1];
                        if (extraDefense < 0.0D) {
                            extraDefense = 0.0D;
                        }

                        e.setDamage(extraDefense);
                    }

                    Double extraDefense1 = 0.0D;
                    if (defense1 instanceof Player) {
                        extraDefense1 = this.extraDefenseMap.remove(defense1);
                        if (extraDefense1 == null) {
                            extraDefense1 = 0.0D;
                        }
                    }

                    value = Util.instance.getLoreValue((String) lores.get(2));
                    double addhealth;
                    if (value != null && value[0] > Util.instance.rand(100)) {
                        addhealth = (double) (Util.instance.rand(value[2] - value[1] + 1) + value[1]) - extraDefense1;
                        if (addhealth > 0.0D) {
                            double newHealth = defense1.getHealth() - addhealth;
                            if (newHealth < 0) newHealth = 0;
                            defense1.setHealth(newHealth);
//                            defense1.damage(addhealth, damager);
                        }
                    }

                    if (e.getDamage() > 0.0D) {
                        e.setDamage(e.getDamage() - extraDefense1);
                    }

                    if (e.getDamage() < 0.0D) {
                        e.setDamage(0.0D);
                    }

                    value = Util.instance.getLoreValue((String) lores.get(6));
                    if (value != null && value[0] > Util.instance.rand(100)) {
                        addhealth = (double) (Util.instance.rand(value[2] - value[1] + 1) + value[1]);
                        if (addhealth > e.getDamage()) {
                            addhealth = e.getDamage();
                        }

                        if (addhealth > defense1.getHealth()) {
                            addhealth = defense1.getHealth();
                        }

                        if (addhealth + damager.getHealth() > damager.getMaxHealth()) {
                            addhealth = damager.getMaxHealth() - damager.getHealth();
                        }

                        damager.setHealth(addhealth + damager.getHealth());
                    }

                    if (defense1.isDead()) {
                        return;
                    }

                    this.potionEffectCheck(lores, (LivingEntity) defense1, damager);
                }
            }
        }

    }

    private void potionEffectCheck(List<String> lores, LivingEntity damager, LivingEntity defense) {
        Integer[] value = Util.instance.getLoreValue(lores.get(7));
        if (value != null) {
            for (int x = 0; x < value[0]; ++x) {
                String potionLore = lores.get(x + 8);
                if (potionLore.startsWith(Msg.potionPrefix.getText())) {
                    potionLore = potionLore.replace(Msg.potionPrefix.getText(), "").replace(Msg.potionLevel.getText(), "").replace(Msg.potionDuration.getText(), "").replace(Msg.chance.getText(), "");
                    String[] potionEffect = potionLore.split(":");
                    if (Util.instance.getNumber(potionEffect[3]) > Util.instance.rand(100)) {
                        LivingEntity target;
                        if (potionEffect[0].startsWith("-")) {
                            potionEffect[0] = potionEffect[0].replaceFirst("-", "");
                            target = damager;
                        } else {
                            target = defense;
                        }

                        target.addPotionEffect(new PotionEffect(PotionEffectEnum.valueOf(potionEffect[0]).getText(), Util.instance.getNumber(potionEffect[2]) * 20, Util.instance.getNumber(potionEffect[1]) - 1));
                    }
                }
            }
        }

    }

    private boolean canUse(Player player) {
        return player.hasPermission("gempro.use");
    }
}
