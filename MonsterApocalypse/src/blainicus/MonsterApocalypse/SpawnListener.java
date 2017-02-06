package blainicus.MonsterApocalypse;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//import org.bukkit.craftbukkit.v1_4_R1.entity.CraftSkeleton;

public class SpawnListener implements Listener {
    private static MonsterApocalypse plugin;
    public int spawncountblaze;
    public int spawncounthorse;
    public int spawncountbat;
    public int spawncountwither;
    public int spawncountwitherskeleton;
    private int spawncountdragon;
    private int spawncountgiant;
    public int spawncountocelot;
    public int spawncountirongolem;
    public int spawncountmagmacube;
    public int spawncountcavespider;
    public int spawncountchicken;
    public int spawncountcow;
    public int spawncountcreeper;
    public int spawncountenderman;
    public int spawncountghast;
    public int spawncountpig;
    public int spawncountwolf;
    public int spawncountmushroomcow;
    public int spawncountpigzombie;
    public int spawncountsheep;
    public int spawncountsilverfish;
    public int spawncountskeleton;
    public int spawncountslime;
    public int spawncountspider;
    public int spawncountsquid;
    public int spawncountvillager;
    public int spawncountzombie;
    public double spawnchanceblaze;
    public double spawnchancebat;
    public double spawnchancewither;
    public double spawnchancewitherskeleton;
    private double spawnchancedragon;
    private double spawnchancegiant;
    public double spawnchanceirongolem;
    public double spawnchanceocelot;
    public double spawnchancemagmacube;
    public double spawnchancecavespider;
    public double spawnchancewolf;
    public double spawnchancemushroomcow;
    public double spawnchancechicken;
    public double spawnchancecow;
    public double spawnchancecreeper;
    public double spawnchanceenderman;
    public double spawnchanceghast;
    public double spawnchancepig;
    public double spawnchancepigzombie;
    public double spawnchancehorse;
    public double spawnchancesheep;
    public double spawnchancesilverfish;
    public double spawnchanceskeleton;
    public double spawnchanceslime;
    public double spawnchancespider;
    public double spawnchancesquid;
    public double spawnchancevillager;
    public double spawnchancezombie;
    public boolean spawnblaze;
    public boolean spawnbat;
    public boolean spawnhorse;
    public boolean spawnwither;
    public boolean spawnwitherskeleton;
    private boolean spawndragon;
    private boolean spawngiant;
    public boolean spawnirongolem;
    public boolean spawnocelot;
    public boolean spawncavespider;
    public boolean spawnmagmacube;
    public boolean spawnchicken;
    public boolean spawnmushroomcow;
    public boolean spawnwolf;
    public boolean spawncow;
    public boolean spawncreeper;
    public boolean spawnenderman;
    public boolean spawnghast;
    public boolean spawnpig;
    public boolean spawnpigzombie;
    public boolean spawnsheep;
    public boolean spawnsilverfish;
    public boolean spawnskeleton;
    public boolean spawnslime;
    public boolean spawnspider;
    public boolean spawnsquid;
    public boolean spawnvillager;
    public boolean spawnzombie;
    public int spawnyminblaze;
    public int spawnyminbat;
    public int spawnyminhorse;
    public int spawnyminwither;
    public int spawnyminwitherskeleton;
    private int spawnymindragon;
    public int spawnyminirongolem;
    private int spawnymingiant;
    public int spawnyminocelot;
    public int spawnymincavespider;
    public int spawnyminmagmacube;
    public int spawnyminchicken;
    public int spawnymincow;
    public int spawnyminmushroomcow;
    public int spawnyminwolf;
    public int spawnymincreeper;
    public int spawnyminenderman;
    public int spawnyminghast;
    public int spawnyminpig;
    public int spawnyminpigzombie;
    public int spawnyminsheep;
    public int spawnyminsilverfish;
    public int spawnyminskeleton;
    public int spawnyminslime;
    public int spawnyminspider;
    public int spawnyminsquid;
    public int spawnyminvillager;
    public int spawnyminzombie;
    public int spawnymaxblaze;
    public int spawnymaxbat;
    public int spawnymaxhorse;
    public int spawnymaxwither;
    public int spawnymaxwitherskeleton;
    public int spawnymaxcavespider;
    private int spawnymaxdragon;
    private int spawnymaxgiant;
    public int spawnymaxocelot;
    public int spawnymaxirongolem;
    public int spawnymaxmagmacube;
    public int spawnymaxchicken;
    public int spawnymaxcow;
    public int spawnymaxmushroomcow;
    public int spawnymaxwolf;
    public int spawnymaxcreeper;
    public int spawnymaxenderman;
    public int spawnymaxghast;
    public int spawnymaxpig;
    public int spawnymaxpigzombie;
    public int spawnymaxsheep;
    public int spawnymaxsilverfish;
    public int spawnymaxskeleton;
    public int spawnymaxslime;
    public int spawnymaxspider;
    public int spawnymaxsquid;
    public int spawnymaxvillager;
    public int spawnymaxzombie;
    public EntityType replacementblaze;
    public EntityType replacementhorse;
    public EntityType replacementbat;
    public EntityType replacementwither;
    public EntityType replacementwitherskeleton;
    private EntityType replacementdragon;
    public EntityType replacementirongolem;
    private EntityType replacementgiant;
    public EntityType replacementocelot;
    public EntityType replacementcavespider;
    public EntityType replacementmagmacube;
    public EntityType replacementchicken;
    public EntityType replacementmushroomcow;
    public EntityType replacementwolf;
    public EntityType replacementcow;
    public EntityType replacementcreeper;
    public EntityType replacementenderman;
    public EntityType replacementghast;
    public EntityType replacementpig;
    public EntityType replacementpigzombie;
    public EntityType replacementsheep;
    public EntityType replacementsilverfish;
    public EntityType replacementskeleton;
    public EntityType replacementslime;
    public EntityType replacementspider;
    public EntityType replacementsquid;
    public EntityType replacementvillager;
    public EntityType replacementzombie;
    private final List<LivingEntity> spawnedmobs;

    public SpawnListener(MonsterApocalypse instance) {
        plugin = instance;
        spawnedmobs = new LinkedList<>();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        for (int i = 0; i < plugin.worldnames.size(); i++) {
            String worldname = plugin.worldnames.get(i);
            if (event.getEntity().getWorld().getName().equals(worldname)) break;
            if (i == plugin.worldnames.size() - 1) return;
        }
        if (adjustedspawn(event) != 0) {
            setmobproperties(spawnedmobs);
        }
    }

    @SuppressWarnings("deprecation")
    private int adjustedspawn(CreatureSpawnEvent event) {
        spawnedmobs.clear();
        try {
            LivingEntity ent = event.getEntity();
            //if(ent.getWorld().getEntities().size()>=plugin.nhardcap){
            //	event.setCancelled(true); return 0;
            //}
            SpawnReason sreason = event.getSpawnReason();
            EntityType originaltype = event.getEntityType();
            Location originalloc = event.getLocation();

            if (!plugin.spawnfooter.getAllowSpawn(originalloc.getBlock().getRelative(BlockFace.DOWN).getType())) {
                event.setCancelled(true);
                return 0;
            }
            int originaly = originalloc.getBlockY();
            if (!plugin.togglehostiles && sreason == SpawnReason.NATURAL
                    &&
                    (originaltype == EntityType.BLAZE
                            ||
                            originaltype == EntityType.CAVE_SPIDER
                            ||
                            originaltype == EntityType.CREEPER
                            ||
                            originaltype == EntityType.ENDERMAN
                            ||
                            originaltype == EntityType.GHAST
                            ||
                            originaltype == EntityType.SILVERFISH
                            ||
                            originaltype == EntityType.SKELETON
                            ||
                            originaltype == EntityType.SLIME
                            ||
                            originaltype == EntityType.SPIDER
                            ||
                            originaltype == EntityType.ZOMBIE
                            ||
                            originaltype == EntityType.MAGMA_CUBE
                            ||
                            originaltype == EntityType.WITCH

                    )
                    ) {
                event.setCancelled(true);
                return 0;
            }
            if (!plugin.togglepassives && sreason == SpawnReason.NATURAL
                    &&
                    (originaltype == EntityType.CHICKEN
                            ||
                            originaltype == EntityType.COW
                            ||
                            originaltype == EntityType.MUSHROOM_COW
                            ||
                            originaltype == EntityType.BAT
                            ||
                            originaltype == EntityType.PIG
                            ||
                            originaltype == EntityType.PIG_ZOMBIE
                            ||
                            originaltype == EntityType.SHEEP
                            ||
                            originaltype == EntityType.SQUID
                            ||
                            originaltype == EntityType.VILLAGER
                            ||
                            originaltype == EntityType.WOLF
                            ||
                            originaltype == EntityType.OCELOT
                            ||
                            originaltype == EntityType.IRON_GOLEM
                            ||
                            originaltype == EntityType.HORSE
                    )
                    ) {
                event.setCancelled(true);
                return 0;
            }
            if (sreason == SpawnReason.CUSTOM) {
                if (plugin.wgspawn && !plugin.getWGSpawnable(ent)) {
                    event.setCancelled(true);
                    return 0;
                } else {
                    spawnedmobs.add(ent);
                    return 2;
                }
            }
            if (sreason == SpawnReason.SPAWNER || sreason == SpawnReason.BED || sreason == SpawnReason.EGG || sreason == SpawnReason.BREEDING || sreason == SpawnReason.LIGHTNING) {
                spawnedmobs.add(ent);
                return 1;
            }
            if (plugin.changespawning) {
                if (originaltype == EntityType.BAT) {
                    event.setCancelled(true);
                    if (spawnbat && originaly >= spawnyminbat && originaly <= spawnymaxbat) {
                        for (int n = 0; n < spawncountblaze; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancebat)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementbat);
                        }
                    }
                } else if (originaltype == EntityType.BLAZE) {
                    event.setCancelled(true);
                    if (spawnblaze && originaly >= spawnyminblaze && originaly <= spawnymaxblaze) {
                        for (int n = 0; n < spawncountblaze; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchanceblaze)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementblaze);
                        }
                    }
                } else if (originaltype == EntityType.CAVE_SPIDER) {
                    event.setCancelled(true);
                    if (spawncavespider && originaly >= spawnymincavespider && originaly <= spawnymaxcavespider) {
                        for (int n = 0; n < spawncountcavespider; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancecavespider)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementcavespider);
                        }
                    }
                } else if (originaltype == EntityType.CHICKEN) {
                    event.setCancelled(true);
                    if (spawnchicken && originaly >= spawnyminchicken && originaly <= spawnymaxchicken) {
                        for (int n = 0; n < spawncountchicken; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancechicken)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementchicken);
                        }
                    }
                } else if (originaltype == EntityType.GIANT) {
                    event.setCancelled(true);
                    if (spawngiant && originaly >= spawnymingiant && originaly <= spawnymaxgiant) {
                        for (int n = 0; n < spawncountgiant; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancegiant)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementgiant);
                        }
                    }
                } else if (originaltype == EntityType.ENDER_DRAGON) {
                    event.setCancelled(true);
                    if (spawndragon && originaly >= spawnymindragon && originaly <= spawnymaxdragon) {
                        for (int n = 0; n < spawncountdragon; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancedragon)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementdragon);
                        }
                    }
                } else if (originaltype == EntityType.HORSE) {
                    event.setCancelled(true);
                    if (spawnhorse && originaly >= spawnyminhorse && originaly <= spawnymaxhorse) {
                        for (int n = 0; n < spawncounthorse; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancehorse)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementhorse);
                        }
                    }
                } else if (originaltype == EntityType.IRON_GOLEM) {

                } else if (originaltype == EntityType.OCELOT) {
                    event.setCancelled(true);
                    if (spawnocelot && originaly >= spawnyminocelot && originaly <= spawnymaxocelot) {
                        for (int n = 0; n < spawncountocelot; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchanceocelot)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementocelot);
                        }
                    }
                } else if (originaltype == EntityType.MUSHROOM_COW) {
                    event.setCancelled(true);
                    if (spawnmushroomcow && originaly >= spawnyminmushroomcow && originaly <= spawnymaxmushroomcow) {
                        for (int n = 0; n < spawncountmushroomcow; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancemushroomcow)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementmushroomcow);
                        }
                    }
                } else if (originaltype == EntityType.COW) {
                    event.setCancelled(true);
                    if (spawncow && originaly >= spawnymincow && originaly <= spawnymaxcow) {
                        for (int n = 0; n < spawncountcow; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancecow)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementcow);
                        }
                    }
                } else if (originaltype == EntityType.CREEPER) {
                    event.setCancelled(true);
                    if (spawncreeper && originaly >= spawnymincreeper && originaly <= spawnymaxcreeper) {
                        for (int n = 0; n < spawncountcreeper; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancecreeper)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementcreeper);
                        }
                    }
                } else if (originaltype == EntityType.ENDERMAN) {
                    event.setCancelled(true);
                    if (spawnenderman && originaly >= spawnyminenderman && originaly <= spawnymaxenderman) {
                        for (int n = 0; n < spawncountenderman; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchanceenderman)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementenderman);
                        }
                    }
                } else if (originaltype == EntityType.GHAST) {
                    event.setCancelled(true);
                    if (spawnghast && originaly >= spawnyminghast && originaly <= spawnymaxghast) {
                        for (int n = 0; n < spawncountghast; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchanceghast)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementghast);
                        }
                    }
                } else if (originaltype == EntityType.MAGMA_CUBE) {
                    if (replacementmagmacube != EntityType.MAGMA_CUBE && replacementmagmacube != EntityType.SLIME) {
                        event.setCancelled(true);
                        if (spawnmagmacube && originaly >= spawnyminmagmacube && originaly <= spawnymaxmagmacube) {
                            for (int n = 0; n < spawncountmagmacube; n++) {
                                if (plugin.spawnrand.nextDouble() * 100d <= spawnchancemagmacube) {
                                    ent.getWorld().spawnEntity(ent.getLocation(), replacementmagmacube);
                                }
                            }
                        }
                    }
                } else if (originaltype == EntityType.PIG) {
                    event.setCancelled(true);
                    if (spawnpig && originaly >= spawnyminpig && originaly <= spawnymaxpig) {
                        for (int n = 0; n < spawncountpig; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancepig)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementpig);
                        }
                    }
                } else if (originaltype == EntityType.PIG_ZOMBIE) {
                    event.setCancelled(true);
                    if (spawnpigzombie && originaly >= spawnyminpigzombie && originaly <= spawnymaxpigzombie) {
                        for (int n = 0; n < spawncountpigzombie; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancepigzombie) {

                                if (replacementpigzombie == EntityType.PIG_ZOMBIE) {
                                    PigZombie pz = ((PigZombie) ent.getWorld().spawnEntity(ent.getLocation(), replacementpigzombie));
                                    if (plugin.getMobStandardItems(pz)) {
                                        pz.getEquipment().setItemInHand(new ItemStack(Material.GOLD_SWORD));
                                    }
                                } else {
                                    ent.getWorld().spawnEntity(ent.getLocation(), replacementpigzombie);
                                }
                            }

                        }
                    }
                } else if (originaltype == EntityType.SHEEP) {
                    event.setCancelled(true);
                    if (spawnsheep && originaly >= spawnyminsheep && originaly <= spawnymaxsheep) {
                        for (int n = 0; n < spawncountsheep; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancesheep)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementsheep);
                        }
                    }
                } else if (originaltype == EntityType.SILVERFISH) {
                    event.setCancelled(true);
                    if (spawnsilverfish && originaly >= spawnyminsilverfish && originaly <= spawnymaxsilverfish) {
                        for (int n = 0; n < spawncountsilverfish; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancesilverfish)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementsilverfish);
                        }
                    }
                } else if (originaltype == EntityType.SKELETON) {
                    event.setCancelled(true);
                    if (((Skeleton) ent).getSkeletonType() == SkeletonType.WITHER) {
                        if (spawnwitherskeleton && originaly >= spawnyminwitherskeleton && originaly <= spawnymaxwitherskeleton) {
                            for (int n = 0; n < spawncountwitherskeleton; n++) {
                                if (plugin.spawnrand.nextDouble() * 100d <= spawnchancewitherskeleton)


                                    //spawn a wither skeleton
                                    if (replacementskeleton == EntityType.WITHER_SKULL) {
                                        LivingEntity sent = (LivingEntity) ent.getWorld().spawnEntity(ent.getLocation(), EntityType.SKELETON);
                                        ((Skeleton) sent).setSkeletonType(SkeletonType.WITHER);
                                        if (plugin.getMobStandardItems(sent)) {
                                            sent.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
                                        }
                                    } else {
                                        ent.getWorld().spawnEntity(ent.getLocation(), replacementwitherskeleton);
                                    }

                            }
                        }

                    } else {
                        if (spawnskeleton && originaly >= spawnyminskeleton && originaly <= spawnymaxskeleton) {
                            for (int n = 0; n < spawncountskeleton; n++) {
                                if (plugin.spawnrand.nextDouble() * 100d <= spawnchanceskeleton)


                                    //spawn a skeleton
                                    if (replacementskeleton == EntityType.SKELETON) {
                                        LivingEntity sk = ((LivingEntity) ent.getWorld().spawnEntity(ent.getLocation(), replacementskeleton));
                                        if (plugin.getMobStandardItems(sk)) {
                                            sk.getEquipment().setItemInHand(new ItemStack(Material.BOW));
                                        }
                                    } else {
                                        ent.getWorld().spawnEntity(ent.getLocation(), replacementskeleton);
                                    }

                            }
                        }
                    }
                } else if (originaltype == EntityType.WITHER) {
                    event.setCancelled(true);
                    if (spawnwither && originaly >= spawnyminwither && originaly <= spawnymaxwither) {
                        for (int n = 0; n < spawncountwither; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancewither)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementwither);
                        }
                    }
                } else if (originaltype == EntityType.SLIME) {
                    if (replacementslime != EntityType.MAGMA_CUBE && replacementslime != EntityType.SLIME) {
                        event.setCancelled(true);
                        if (spawnslime && originaly >= spawnyminslime && originaly <= spawnymaxslime) {
                            for (int n = 0; n < spawncountslime; n++) {
                                if (plugin.spawnrand.nextDouble() * 100d <= spawnchanceslime)
                                    ent.getWorld().spawnEntity(ent.getLocation(), replacementslime);
                            }
                        }
                    }
                } else if (originaltype == EntityType.SPIDER) {
                    event.setCancelled(true);
                    if (spawnspider && originaly >= spawnyminspider && originaly <= spawnymaxspider) {
                        for (int n = 0; n < spawncountspider; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancespider)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementspider);
                        }
                    }
                } else if (originaltype == EntityType.SQUID) {
                    event.setCancelled(true);
                    if (spawnsquid && originaly >= spawnyminsquid && originaly <= spawnymaxsquid) {
                        for (int n = 0; n < spawncountsquid; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancesquid)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementsquid);
                        }
                    }
                } else if (originaltype == EntityType.VILLAGER) {
                    event.setCancelled(true);
                    if (spawnvillager && originaly >= spawnyminvillager && originaly <= spawnymaxvillager) {
                        for (int n = 0; n < spawncountvillager; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancevillager)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementvillager);
                        }
                    }
                } else if (originaltype == EntityType.WOLF) {
                    event.setCancelled(true);
                    if (spawnwolf && originaly >= spawnyminwolf && originaly <= spawnymaxwolf) {
                        for (int n = 0; n < spawncountwolf; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancewolf)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementwolf);
                        }
                    }
                } else if (originaltype == EntityType.ZOMBIE) {
                    event.setCancelled(true);
                    if (spawnzombie && originaly >= spawnyminzombie && originaly <= spawnymaxzombie) {
                        for (int n = 0; n < spawncountzombie; n++) {
                            if (plugin.spawnrand.nextDouble() * 100d <= spawnchancezombie)
                                ent.getWorld().spawnEntity(ent.getLocation(), replacementzombie);
                        }
                    }
                }
            } else {
                spawnedmobs.add(ent);
                return 1;
            }
        } catch (NullPointerException e) {
            return 0;
        }
        return 1;
    }

    private void setmobproperties(List<LivingEntity> mobset) {
        Iterator<LivingEntity> mobiter = mobset.iterator();
        while (mobiter.hasNext()) {
            LivingEntity mob = mobiter.next();
            if (!(mob instanceof Wolf || mob instanceof Slime || mob instanceof MagmaCube)) {

                //mob.getWorld().getEntities().add(mob);
                if (plugin.changecombathp) {
                    plugin.healths.addmob(mob);
                }
            }
        }
    }
} 

