package blainicus.MonsterApocalypse;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

public class DamageListener implements Listener {
    private final MonsterApocalypse plugin;

    public DamageListener(MonsterApocalypse instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageEvent event) {
        for (int i = 0; i < plugin.worldnames.size(); i++) {
            String worldname = plugin.worldnames.get(i);
            if (event.getEntity().getWorld().getName().equals(worldname)) break;
            if (i == plugin.worldnames.size() - 1) return;
        }
        if (event.isCancelled()) return;
        if (plugin.checkblockspawn) {
            if (event.getCause() == DamageCause.SUFFOCATION && event.getEntity().getTicksLived() < 10) {
                event.setCancelled(true);
                event.getEntity().remove();
                return;
            }
        }
        if (plugin.changeadvanced) {
            if (plugin.getMobImmunity(event.getEntity(), event.getCause())) {
                event.setCancelled(true);
                return;
            }
        /*if(plugin.changecombatdmg){
		if(event.getCause()==DamageCause.ENTITY_ATTACK){
			Entity potarrow=((EntityDamageByEntityEvent) event).getDamager();
			if(potarrow instanceof Arrow){
				if(((Arrow)potarrow).getShooter() instanceof Skeleton){
					event.setDamage(plugin.getMobDamage((Skeleton)((Arrow)potarrow).getShooter()));
				}
			}
		}}*/
//		if(event.getCause()==DamageCause.ENTITY_ATTACK){
//			try{
//				@SuppressWarnings("unused")
//				LivingEntity ent=(LivingEntity) event.getEntity();}
//				catch(ClassCastException e){return;}
//				LivingEntity entz=(LivingEntity) event.getEntity();
//			Entity attackerz=((EntityDamageByEntityEvent) event).getDamager();
//			if (!(entz instanceof Player)&&(attackerz instanceof Skeleton)){
//				if(plugin.preventhurt&&(entz instanceof Skeleton)){
//					event.setCancelled(true);
////					((EntityDamageByProjectileEvent) event).getProjectile().remove();
//					return;
//				}
//			}
//		}
        }
        if (event.getEntity() instanceof org.bukkit.craftbukkit.v1_9_R2.entity.CraftArrow || event.getEntity() instanceof org.bukkit.craftbukkit.v1_9_R2.entity.CraftItem || event.getEntity() instanceof Wolf || event.getEntity() instanceof Slime || event.getEntity() instanceof MagmaCube)
            return;
        if (event.getEntity().isDead()) {
            event.setCancelled(true);
            return;
        }
        if (!(plugin.changecombathp)) {
            try {
                @SuppressWarnings("unused")
                LivingEntity ent = (LivingEntity) event.getEntity();
            } catch (ClassCastException e) {
                return;
            }
            LivingEntity ent = (LivingEntity) event.getEntity();
            if ((!(ent instanceof Player))
                    ||
                    (!plugin.checkaggro || (plugin.checkaggro && !ent.hasPermission("Monster Apocalypse.deaggro")))) {
                if (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.PROJECTILE) {

                    Entity attacker = ((EntityDamageByEntityEvent) event).getDamager();
                    if (attacker instanceof Arrow) {
                        Entity newattacker = (Entity) ((Arrow) attacker).getShooter();
                        if (newattacker instanceof Skeleton) {
                            attacker = newattacker;
                        }
                    }
                    if (plugin.potionhits) {
                        plugin.applyPotionEffects(ent, attacker);
                    }
                }
                if (plugin.changecombatdmg) {
                    if (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.PROJECTILE) {

                        Entity attacker = ((EntityDamageByEntityEvent) event).getDamager();
                        if (attacker instanceof Arrow) {
                            Entity newattacker = (Entity) ((Arrow) attacker).getShooter();
                            if (newattacker instanceof Skeleton) {
                                attacker = newattacker;
                            }
                        }
                        if ((ent instanceof Player) && !(attacker instanceof Player)) {

                            double priorhp = ent.getHealth();
                            double newhp = priorhp - plugin.getMobTrueDamage(attacker);
                            boolean deathflag = false;
                            if (newhp <= 0) {
                                newhp = 1;
                                deathflag = true;
                            }
                            ent.setHealth(newhp);
                            if (!(plugin.getMobName(attacker).equalsIgnoreCase("")) && !(attacker instanceof Wolf)
                                    && !(plugin.getMobName(attacker).equalsIgnoreCase("Slime")) && !(plugin.getMobName(attacker).equalsIgnoreCase("LavaSlime"))
                                    && !(plugin.getMobName(attacker).equalsIgnoreCase("MagmaCube"))) {
                                event.setDamage(plugin.getMobDamage(attacker));
                            } else {
                                if ((attacker instanceof Wolf)) {
                                    if (!((Wolf) attacker).isTamed()) {
                                        event.setDamage(plugin.getMobDamage(attacker));
                                    }
                                }
                            }
                            if (deathflag) {
                                event.setDamage(60);
                            }
                            //}else{event.setCancelled(true);}
                            if (plugin.wgattack && !plugin.getWGMobAttack(ent)) {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            } else {
                event.setCancelled(true);
            }
            return;
        }


        try {
            @SuppressWarnings("unused")
            LivingEntity ent = (LivingEntity) event.getEntity();
        } catch (ClassCastException e) {
            return;
        }
        LivingEntity ent = (LivingEntity) event.getEntity();


        if (!(ent instanceof Player)) {
            if (plugin.getMobName(ent) == "") {
                return;
            }
        }
        double remaininghealth = 100000;
        if (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.PROJECTILE) {
            if (!(ent instanceof Player)) {
                try {

                    if (plugin.healths.getLastDamageTime(ent) > System.currentTimeMillis() - Math.min(500L, plugin.damageperiod)) {
                        event.setCancelled(true);
                        return;
                    } else {
                        if (plugin.damageperiod < 500) {
                            Entity attacker, attacked;
                            float knockback;
                            attacked = ent;
                            attacker = ((EntityDamageByEntityEvent) event).getDamager();
                            knockback = 0.55f * plugin.damageperiod / 500;
                            ((LivingEntity) attacked).setNoDamageTicks(0);
                            attacked.setVelocity(attacked.getVelocity().add(attacked.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize().multiply(knockback)));
                            attacked.playEffect(EntityEffect.HURT);
                            playAppropriateSound(attacked);
                        }
                    }
                } catch (Exception e) {
                }
            }
        } else {
            if (!(ent instanceof Player)) {
                if (plugin.healths.getLastDamageTime(ent) > System.currentTimeMillis() - 500L) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
        if (plugin.hphandledexternally) {
            return;
        }
        if (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.PROJECTILE) {
            Entity attacker = ((EntityDamageByEntityEvent) event).getDamager();
            if (attacker instanceof Arrow) {
                Entity newattacker = (Entity) ((Arrow) attacker).getShooter();
                if (newattacker instanceof Skeleton) {
                    attacker = newattacker;
                }
            }
            if ((ent instanceof Player) && !(attacker instanceof Player)) {
                if (!plugin.checkaggro || (plugin.checkaggro && !ent.hasPermission("Monster Apocalypse.deaggro"))) {
                    if (plugin.potionhits) {
                        plugin.applyPotionEffects(ent, attacker);
                    }
                    if (plugin.changecombatdmg) {

                        double priorhp = ent.getHealth();
                        double newhp = priorhp - plugin.getMobTrueDamage(attacker);
                        boolean deathflag = false;
                        if (newhp <= 0) {
                            newhp = 1;
                            deathflag = true;
                        }
                        ent.setHealth(newhp);
                        if (!(plugin.getMobName(attacker).equalsIgnoreCase("")) && !(attacker instanceof Wolf)
                                && !(plugin.getMobName(attacker).equalsIgnoreCase("Slime")) && !(plugin.getMobName(attacker).equalsIgnoreCase("LavaSlime"))
                                && !(plugin.getMobName(attacker).equalsIgnoreCase("MagmaCube"))) {
                            event.setDamage(plugin.getMobDamage(attacker));
                        } else {
                            if ((attacker instanceof Wolf)) {
                                if (!((Wolf) attacker).isTamed()) {
                                    event.setDamage(plugin.getMobDamage(attacker));


                                }
                            }
                        }
                        if (deathflag) {
                            event.setDamage(60);
                        }

                    }
                } else {
                    event.setCancelled(true);
                }
                if (plugin.wgattack && !plugin.getWGMobAttack(ent)) {
                    event.setCancelled(true);
                }

            } else if (!((ent instanceof Player) && (attacker instanceof Player))) {
                if (plugin.getMobName(attacker) != "") {
                    if (plugin.changecombatdmg) {
                        remaininghealth = plugin.healths.damagemob(ent, plugin.getMobDamage(attacker));
                    } else {
                        remaininghealth = plugin.healths.damagemob(ent, event.getDamage());
                    }

                } else {
                    remaininghealth = plugin.healths.damagemob(ent, event.getDamage());
                }
                event.setDamage(1);
                if (remaininghealth <= ent.getMaxHealth()) {
                    if (remaininghealth >= 1) {
                        ent.setHealth(remaininghealth);
                    } else {
                        ent.setHealth(0.01);
                    }
                } else {
                    ent.setHealth(ent.getMaxHealth());
                }
                return;
            }
        }

        if (!(ent instanceof Player)) {
            if (plugin.healths.getmobhp(ent) > 0) {
                remaininghealth = plugin.healths.damagemob(ent, event.getDamage());
                event.setDamage(1);
                if (remaininghealth <= ent.getMaxHealth()) {
                    if (remaininghealth >= 1) {
                        ent.setHealth(remaininghealth);
                    } else {
                        ent.setHealth(0.01);
                    }
                } else {
                    ent.setHealth(ent.getMaxHealth());
                }
            }
        }
//		if(plugin.healths.getmobhp(ent)-event.getDamage()<=0){ent.remove();}


    }

    private void playAppropriateSound(Entity attacked) {
        if (attacked instanceof PigZombie) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_ZOMBIE_PIG_HURT, 1, 1);
        } else if (attacked instanceof Zombie) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_ZOMBIE_HURT, 1, 1);
        } else if (attacked instanceof Skeleton) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_SKELETON_HURT, 1, 1);
        } else if (attacked instanceof Creeper) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_CREEPER_HURT, 1, 1);
        } else if (attacked instanceof Slime) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_SLIME_HURT, 1, 1);
        } else if (attacked instanceof Bat) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_BAT_HURT, 1, 1);
        } else if (attacked instanceof Blaze) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_BLAZE_HURT, 1, 1);
        } else if (attacked instanceof Ocelot) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_CAT_HURT, 1, 1);
        } else if (attacked instanceof EnderDragon) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 1, 1);
        } else if (attacked instanceof Enderman) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_ENDERMEN_HURT, 1, 1);
        } else if (attacked instanceof Ghast) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
        } else if (attacked instanceof IronGolem) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_IRONGOLEM_HURT, 1, 1);
        } else if (attacked instanceof Silverfish) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_SILVERFISH_HURT, 1, 1);
        } else if (attacked instanceof Wither) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_WITHER_HURT, 1, 1);
        } else if (attacked instanceof Horse) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_HORSE_HURT, 1, 1);
        } else if (attacked instanceof Player) {
            attacked.getLocation().getWorld().playSound(attacked.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 1);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityCombust(EntityCombustEvent event) {
        if (plugin.changeadvanced) {
            for (int i = 0; i < plugin.worldnames.size(); i++) {
                String worldname = plugin.worldnames.get(i);
                if (event.getEntity().getWorld().getName().equals(worldname)) break;
                if (i == plugin.worldnames.size() - 1) return;
            }
            Entity combuster = event.getEntity();
            if (!plugin.getMobSunlightCombustion(combuster)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDeath(EntityDeathEvent event) {
        if (!plugin.changeadvanced) {
            return;
        }
        for (int i = 0; i < plugin.worldnames.size(); i++) {
            String worldname = plugin.worldnames.get(i);
            if (event.getEntity().getWorld().getName().equals(worldname)) break;
            if (i == plugin.worldnames.size() - 1) return;
        }
        Entity ent = event.getEntity();
        Location loc = new Location(ent.getWorld(), ent.getLocation().getX(), ent.getLocation().getY(), ent.getLocation().getZ());
        if (plugin.changecombathp) {
            plugin.healths.removemob(ent);
        }
        if (plugin.getMobDeathExplode(ent)) {
            plugin.WGexplode(loc, (float) plugin.getMobDeathRadius(ent), plugin.getMobDeathFire(ent));
        }
        if (plugin.wgspawn && !plugin.getWGSpawnable(ent)) {
        } else if (plugin.getMobDeathSpawn(ent)) {
//			try{


            for (int n = 0; n < plugin.getMobDeathCount(ent); n++) {
                if (plugin.spawnrand.nextDouble() * 100d <= plugin.getMobDeathChance(ent)) {
                    LivingEntity newmob = (LivingEntity) ent.getWorld().spawnEntity(loc, plugin.getMobDeathSpawnType(ent));
                    if (plugin.changecombathp) {
                        plugin.healths.addmob(newmob);
                    }
                }
            }

            //			}


            //	}catch(NullPointerException e){
            //		System.out.println("Monster Apocalypse: Invalid death creature spawn settings for "+ent.getClass().getSimpleName()+".");

        }
    }
	/*@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityShootBow(EntityShootBowEvent event){
		for(int i=0; i<plugin.worldnames.size(); i++){
			String worldname=plugin.worldnames.get(i);
			if(event.getEntity().getWorld().getName().equals(worldname))break;
			if(i==plugin.worldnames.size()-1)return;
		}
		if(event.isCancelled()) return;
		if(plugin.changecombatdmg){
			if(event.getEntity() instanceof Skeleton){
				event.getProjectile().getUniqueId();MAP
			}
		}
		
	}*/
    //@EventHandler(priority = EventPriority.HIGHEST)
    //public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
    //
    //}
}
