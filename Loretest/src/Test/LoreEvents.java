//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Test;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class LoreEvents implements Listener {
    public LoreEvents() {
    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void applyOnInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            LoreAttributes.loreManager.handleArmorRestriction((Player) event.getPlayer());
        }

    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void applyOnPlayerLogin(PlayerJoinEvent event) {
        LoreAttributes.loreManager.applyHpBonus(event.getPlayer());
    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void applyOnPlayerRespawn(PlayerRespawnEvent event) {
        LoreAttributes.loreManager.applyHpBonus(event.getPlayer());
    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void applyOnEntityTarget(EntityTargetEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity e = (LivingEntity) event.getEntity();
            LoreAttributes.loreManager.applyHpBonus(e);
        }

    }

    @EventHandler(
            priority = EventPriority.HIGH
    )
    public void modifyEntityDamage(EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getEntity() instanceof LivingEntity) {
            if (LoreAttributes.loreManager.dodgedAttack((LivingEntity) event.getEntity())) {
                event.setDamage(0.0D);
                event.setCancelled(true);
            } else {
                if (event.getDamager() instanceof LivingEntity) {
                    LivingEntity damager = (LivingEntity) event.getDamager();
                    if (damager instanceof Player) {
                        if (!LoreAttributes.loreManager.canAttack(((Player) damager).getName())) {
                            if (!LoreAttributes.config.getBoolean("lore.attack-speed.display-message")) {
                                event.setCancelled(true);
                                return;
                            }

                            ((Player) damager).sendMessage(LoreAttributes.config.getString("lore.attack-speed.message"));
                            event.setCancelled(true);
                            return;
                        }

                        LoreAttributes.loreManager.addAttackCooldown(((Player) damager).getName());
                    }

                    if (LoreAttributes.loreManager.useRangeOfDamage(damager)) {
//                        System.out.println(1 + damager.getType().toString() + LoreAttributes.loreManager.getDamageBonus(damager) + " " + LoreAttributes.loreManager.getArmorBonus((LivingEntity) event.getEntity()));
                        event.setDamage(
                                (double) Math.max(0,
                                        LoreAttributes.loreManager.getDamageBonus(damager) -
                                                LoreAttributes.loreManager.getArmorBonus((LivingEntity) event.getEntity())));
                    } else {
//                        System.out.println(2 + damager.getType().toString() + LoreAttributes.loreManager.getDamageBonus(damager) + " " + LoreAttributes.loreManager.getArmorBonus((LivingEntity) event.getEntity()));

                        event.setDamage(Math.max(0.0D, event.getDamage() + (double) LoreAttributes.loreManager.getDamageBonus(damager) - (double) LoreAttributes.loreManager.getArmorBonus((LivingEntity) event.getEntity())));
                    }

                    damager.setHealth(Math.min(damager.getMaxHealth(), damager.getHealth() + Math.min((double) LoreAttributes.loreManager.getLifeSteal(damager), event.getDamage())));
                } else if (event.getDamager() instanceof Arrow) {
                    Arrow arrow1 = (Arrow) event.getDamager();
                    if (arrow1.getShooter() != null && arrow1.getShooter() instanceof LivingEntity) {
                        LivingEntity damager = (LivingEntity) arrow1.getShooter();
                        if (damager instanceof Player) {
                            if (!LoreAttributes.loreManager.canAttack(((Player) damager).getName())) {
                                if (!LoreAttributes.config.getBoolean("lore.attack-speed.display-message")) {
                                    event.setCancelled(true);
                                    return;
                                }

                                ((Player) damager).sendMessage(LoreAttributes.config.getString("lore.attack-speed.message"));
                                event.setCancelled(true);
                                return;
                            }

                            LoreAttributes.loreManager.addAttackCooldown(((Player) damager).getName());
                        }

                        if (LoreAttributes.loreManager.useRangeOfDamage(damager)) {
                            event.setDamage((double) Math.max(0, LoreAttributes.loreManager.getDamageBonus(damager) - LoreAttributes.loreManager.getArmorBonus((LivingEntity) event.getEntity())));
                        } else {
                            event.setDamage(Math.max(0.0D, event.getDamage() + (double) LoreAttributes.loreManager.getDamageBonus(damager)) - (double) LoreAttributes.loreManager.getArmorBonus((LivingEntity) event.getEntity()));
                        }
                        double newHealth = Math.min(damager.getMaxHealth(), damager.getHealth() + Math.min((double) LoreAttributes.loreManager.getLifeSteal(damager), event.getDamage()));
                        if (newHealth > damager.getMaxHealth()) {
                            newHealth = damager.getMaxHealth();
                        }
                        damager.setHealth(newHealth);
                    }
                }

            }
        }
    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void damage(EntityDamageEvent event) {
    }

    @EventHandler(
            priority = EventPriority.NORMAL
    )
    public void applyHealthRegen(EntityRegainHealthEvent event) {
        if (!event.isCancelled()) {
            if (event.getEntity() instanceof Player && event.getRegainReason() == RegainReason.SATIATED) {
                event.setAmount(event.getAmount() + (double) LoreAttributes.loreManager.getRegenBonus((LivingEntity) event.getEntity()));
                if (event.getAmount() <= 0.0D) {
                    event.setCancelled(true);
                }
            }

        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void checkBowRestriction(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            if (!LoreAttributes.loreManager.canUse((Player) event.getEntity(), event.getBow())) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void checkCraftRestriction(CraftItemEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            ItemStack[] var5;
            int var4 = (var5 = event.getInventory().getContents()).length;

            for (int var3 = 0; var3 < var4; ++var3) {
                ItemStack item = var5[var3];
                if (!LoreAttributes.loreManager.canUse((Player) event.getWhoClicked(), item)) {
                    event.setCancelled(true);
                    return;
                }
            }

        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void checkWeaponRestriction(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (!LoreAttributes.loreManager.canUse((Player) event.getDamager(), ((Player) event.getDamager()).getItemInHand())) {
                event.setCancelled(true);
            }
        }
    }
}
