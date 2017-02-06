package blainicus.MonsterApocalypse;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpawnPoints implements Runnable, CommandExecutor {
    private final ArrayList<spawnpoint> pointlist;
    private List<String> conflist;
    private final MonsterApocalypse plugin;
    private String name;
    private String propertyname;
    private EntityType type;
    private int period;
    private int count;
    private int min;
    private int max;
    private double chance;
    private spawnpoint selectedpoint;

    public SpawnPoints(MonsterApocalypse instance) {
        // etc.
        plugin = instance;
        getspawnlist();
        pointlist = new ArrayList<>();
        fillpointlist();
        setconfig();
        save();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!plugin.togglespawnpoints) {
            sender.sendMessage("Spawn points are not enabled in the config, please enable them to use commands.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("Please use a full Monster Apocalypse command.");
            return true;
        }
        Player player = null;

        String returnstring;
        if (args[0].equalsIgnoreCase("addspawnpoint")) {
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                returnstring = addspawnpoint(args);
                if (returnstring != null) {
                    sender.sendMessage(returnstring);
                }
            } else {
                returnstring = addspawnpoint(args, player.getLocation());
                if (returnstring != null) {
                    sender.sendMessage(returnstring);
                }
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("removespawnpoint")) {
            returnstring = removespawnpoint(args);
            if (returnstring != null) {
                sender.sendMessage(returnstring);
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("properties")) {
            returnstring = properties(args);
            if (returnstring != null) {
                sender.sendMessage(returnstring);
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("listspawnpoints")) {
            returnstring = getlist();
            if (returnstring != null) {
                sender.sendMessage(returnstring);
            }
            return true;
        }
        if (args[0].startsWith("get")) {
            returnstring = getproperty(args);
            if (returnstring != null) {
                sender.sendMessage(returnstring);
            }
            return true;
        }
        if (args[0].startsWith("set")) {
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                returnstring = setproperty(args);
                if (returnstring != null) {
                    sender.sendMessage(returnstring);
                }
            } else {
                returnstring = setproperty(args, player.getLocation());
                if (returnstring != null) {
                    sender.sendMessage(returnstring);
                }
            }
            return true;
        }
        return false;
    }

    private String setproperty(String[] args, Location loc) {
        try {
            propertyname = args[0].substring(3);
            name = args[1];
            selectedpoint = null;
            for (spawnpoint aPointlist : pointlist) {
                if (aPointlist.getName().equalsIgnoreCase(name)) {
                    selectedpoint = aPointlist;
                }
            }
            if (selectedpoint == null) {
                return "Invalid spawn point name.";
            }

            if (propertyname.equalsIgnoreCase("type")) {
                selectedpoint.setType(EntityType.fromName(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("period")) {
                selectedpoint.setPeriod(Integer.parseInt(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("pos") || propertyname.equalsIgnoreCase("position")) {
                if (selectedpoint.setPos(loc.add(0.0, 0.01, 0.0))) {
                    return propertyname + " set for " + name + ".";
                } else {
                    return "The world set for this spawn location is not enabled for Monster Apocalypse. Location will not change.";
                }
            } else if (propertyname.equalsIgnoreCase("count")) {
                selectedpoint.setCount(Integer.parseInt(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("lightmin")) {
                selectedpoint.setMinLight(Integer.parseInt(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("lightmax")) {
                selectedpoint.setMaxLight(Integer.parseInt(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("chance")) {
                selectedpoint.setChance(Double.parseDouble(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("x")) {
                selectedpoint.setX(Double.parseDouble(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("y")) {
                selectedpoint.setY(Double.parseDouble(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("z")) {
                selectedpoint.setZ(Double.parseDouble(args[2]));
                return propertyname + " set for " + name + ".";
            }
            return propertyname + " flag not found. Valid flags: chance, count, lightmax, lightmin, period, pos, type, x, y, z.";
        } catch (Exception e) {
            return "Invalid property input.";
        }
    }

    private String setproperty(String[] args) {
        try {
            propertyname = args[0].substring(3);
            name = args[1];
            selectedpoint = null;
            for (spawnpoint aPointlist : pointlist) {
                if (aPointlist.getName().equalsIgnoreCase(name)) {
                    selectedpoint = aPointlist;
                }
            }
            if (selectedpoint == null) {
                return "Invalid spawn point name.";
            }

            if (propertyname.equalsIgnoreCase("type")) {
                selectedpoint.setType(EntityType.fromName(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("period")) {
                selectedpoint.setPeriod(Integer.parseInt(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("pos") || propertyname.equalsIgnoreCase("position")) {
                return "this flag cannot be run from the console";
            } else if (propertyname.equalsIgnoreCase("count")) {
                selectedpoint.setCount(Integer.parseInt(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("lightmin")) {
                selectedpoint.setMinLight(Integer.parseInt(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("lightmax")) {
                selectedpoint.setMaxLight(Integer.parseInt(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("chance")) {
                selectedpoint.setChance(Double.parseDouble(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("x")) {
                selectedpoint.setX(Double.parseDouble(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("y")) {
                selectedpoint.setY(Double.parseDouble(args[2]));
                return propertyname + " set for " + name + ".";
            } else if (propertyname.equalsIgnoreCase("z")) {
                selectedpoint.setZ(Double.parseDouble(args[2]));
                return propertyname + " set for " + name + ".";
            }
            return propertyname + " flag not found. Valid flags: chance, count, lightmax, lightmin, period, pos, type, x, y, z.";
        } catch (Exception e) {
            return "Invalid property input.";
        }
    }

    private String getproperty(String[] args) {
        try {
            propertyname = args[0].substring(3);
            name = args[1];
            selectedpoint = null;
            for (spawnpoint aPointlist : pointlist) {
                if (aPointlist.getName().equalsIgnoreCase(name)) {
                    selectedpoint = aPointlist;
                }
            }
            if (selectedpoint == null) {
                return "Invalid spawn point name.";
            }

            if (propertyname.equalsIgnoreCase("type")) {
                return selectedpoint.getType().getClass().getSimpleName();
            } else if (propertyname.equalsIgnoreCase("period")) {
                return propertyname + ": " + selectedpoint.getPeriod() + "";
            } else if (propertyname.equalsIgnoreCase("pos") || propertyname.equalsIgnoreCase("position")) {
                return propertyname + ": " + selectedpoint.getPos().getWorld().getName() + ", " + selectedpoint.getPos().getX() + ", " + selectedpoint.getPos().getY() + ", " + selectedpoint.getPos().getZ() + ".";
            } else if (propertyname.equalsIgnoreCase("count")) {
                return propertyname + ": " + selectedpoint.getCount() + "";
            } else if (propertyname.equalsIgnoreCase("lightmin")) {
                return propertyname + ": " + selectedpoint.getMinLight() + "";
            } else if (propertyname.equalsIgnoreCase("lightmax")) {
                return propertyname + ": " + selectedpoint.getMaxLight() + "";
            } else if (propertyname.equalsIgnoreCase("chance")) {
                return propertyname + ": " + selectedpoint.getChance() + "";
            }
            return propertyname + " flag not found. Valid flags: chance, count, lightmax, lightmin, period, pos, type.";
        } catch (Exception e) {
            return "Invalid property input.";
        }
    }

    private String properties(String[] args) {
        try {
            name = args[1];
            selectedpoint = null;
            for (spawnpoint aPointlist : pointlist) {
                if (aPointlist.getName().equalsIgnoreCase(name)) {
                    selectedpoint = aPointlist;
                }
            }
            if (selectedpoint == null) {
                return "Invalid spawn point name.";
            }
        } catch (Exception e) {
            return "Invalid property input or error.";
        }
        return "Spawn point " + selectedpoint.getName() + ": "
                + "Position X: " + selectedpoint.getPos().getX() + ", "
                + "Position Y: " + selectedpoint.getPos().getY() + ", "
                + "Position Z: " + selectedpoint.getPos().getZ() + ", "
                + "Creature: " + selectedpoint.getType().getClass().getSimpleName() + ", "
                + "Period: " + selectedpoint.getPeriod() + ", "
                + "Count: " + selectedpoint.getCount() + ", "
                + "Min Light: " + selectedpoint.getMinLight() + ", "
                + "Max Light: " + selectedpoint.getMaxLight() + ", "
                + "Chance: " + selectedpoint.getChance() + ".";
    }

    private String getlist() {
        String spawnnames = "Spawn Points: ";
        for (spawnpoint aPointlist : pointlist) {
            spawnnames = spawnnames.concat(aPointlist.getName() + ", ");
        }
        return spawnnames;
    }

    private String addspawnpoint(String[] args, Location loc) {
        /*"name:type"
		"name:type:period"
		"name:type:period:count"
		"name:type:period:count:min-max"
		"name:type:period:count:min-max:chance"*/
        for (int i = 0; i < plugin.worldnames.size(); i++) {
            String worldname = plugin.worldnames.get(i);
            if (loc.getWorld().getName().equals(worldname)) break;
            if (i == plugin.worldnames.size() - 1)
                return "Invalid worldname or world not enabled for Monster Apocalypse.";
        }
        try {
            name = args[1];
            for (spawnpoint aPointlist : pointlist) {
                if (aPointlist.getName().equalsIgnoreCase(name)) {
                    return "Spawnpoint name already in use.";
                }
            }
            try {
//				type=EntityType.fromName(args[2]);
                type = EntityType.fromName(args[2]);
            } catch (NullPointerException e) {
                try {
//				type=EntityType.fromName(args[2]);
                    type = EntityType.fromName(args[2]);
                } catch (NullPointerException e2) {
                    return "Invalid monster name.";
                }
            }
            if (type == null) {
                type = CTName(args[2]);
            }
            if (type == null) return "Invalid monster name " + args[2] + ".";
            period = Integer.parseInt(args[3]);
            count = Integer.parseInt(args[4]);
            min = Integer.parseInt(args[5]);
            max = Integer.parseInt(args[6]);
            chance = Double.parseDouble(args[7]);
            pointlist.add(new spawnpoint(name, loc.add(0.0, 0.01, 0.0), type, period, count, min, max, chance));
            //fully typed
            setconfig();
            save();
            return "Spawn point added.";
        } catch (Exception e) {
            return "Invalid input, use /ma addspawnpoint [name] [mobtype] [period (X seconds)] [mobcount] [minlight(0-15)] [maxlight (0-15)] [chance (1-100)]";
        }
    }

    private EntityType CTName(String name) {
        if (name.equalsIgnoreCase("PigZombie")) {
            return EntityType.PIG_ZOMBIE;
        }
        if (name.equalsIgnoreCase("Zombie")) {
            return EntityType.ZOMBIE;
        }
        if (name.equalsIgnoreCase("Bat")) {
            return EntityType.BAT;
        }
        if (name.equalsIgnoreCase("Wither")) {
            return EntityType.WITHER;
        }
        if (name.equalsIgnoreCase("Creeper")) {
            return EntityType.CREEPER;
        }
        if (name.equalsIgnoreCase("Enderman")) {
            return EntityType.ENDERMAN;
        }
        if (name.equalsIgnoreCase("Skeleton")) {
            return EntityType.SKELETON;
        }
        if (name.equalsIgnoreCase("WitherSkeleton")) {
            return EntityType.WITHER_SKULL;
        }
        if (name.equalsIgnoreCase("Blaze")) {
            return EntityType.BLAZE;
        }
        if (name.equalsIgnoreCase("CaveSpider")) {
            return EntityType.CAVE_SPIDER;
        }
        if (name.equalsIgnoreCase("Spider")) {
            return EntityType.SPIDER;
        }
        if (name.equalsIgnoreCase("Chicken")) {
            return EntityType.CHICKEN;
        }
        if (name.equalsIgnoreCase("Cow")) {
            return EntityType.COW;
        }
        if (name.equalsIgnoreCase("Ghast")) {
            return EntityType.GHAST;
        }
        if (name.equalsIgnoreCase("Horse")) {
            return EntityType.HORSE;
        }
        if (name.equalsIgnoreCase("MushroomCow")) {
            return EntityType.MUSHROOM_COW;
        }
        if (name.equalsIgnoreCase("Pig")) {
            return EntityType.PIG;
        }
        if (name.equalsIgnoreCase("Sheep")) {
            return EntityType.SHEEP;
        }
        if (name.equalsIgnoreCase("Silverfish")) {
            return EntityType.SILVERFISH;
        }
        if (name.equalsIgnoreCase("MagmaCube")) {
            return EntityType.MAGMA_CUBE;
        }
        if (name.equalsIgnoreCase("Slime")) {
            return EntityType.SLIME;
        }
        if (name.equalsIgnoreCase("Squid")) {
            return EntityType.SQUID;
        }
        if (name.equalsIgnoreCase("Villager")) {
            return EntityType.VILLAGER;
        }
        if (name.equalsIgnoreCase("Wolf")) {
            return EntityType.WOLF;
        }
        if (name.equalsIgnoreCase("Giant")) {
            return EntityType.GIANT;
        }
        if (name.equalsIgnoreCase("Ocelot")) {
            return EntityType.OCELOT;
        }
        if (name.equalsIgnoreCase("IronGolem")) {
            return EntityType.IRON_GOLEM;
        }
        if (name.equalsIgnoreCase("EnderDragon")) {
            return EntityType.ENDER_DRAGON;
        }

        return null;
    }

    private String addspawnpoint(String[] args) {
		/*"name:type"
		"name:type:period"
		"name:type:period:count"
		"name:type:period:count:min-max"
		"name:type:period:count:min-max:chance"*/
        String worldnamez = args[2];
        double x = Double.parseDouble(args[3]);
        double y = Double.parseDouble(args[4]);
        double z = Double.parseDouble(args[5]);
        for (int i = 0; i < plugin.worldnames.size(); i++) {
            String worldname = plugin.worldnames.get(i);
            if (worldnamez.equals(worldname)) break;
            if (i == plugin.worldnames.size() - 1)
                return "Invalid worldname or world not enabled for Monster Apocalypse.";
        }
        try {
            name = args[5];
            for (spawnpoint aPointlist : pointlist) {
                if (aPointlist.getName().equalsIgnoreCase(name)) {
                    return "Spawnpoint name already in use.";
                }
            }
            try {
//				type=EntityType.fromName(args[2]);
                EntityType.fromName(args[6]);
            } catch (NullPointerException e) {
                try {
//				type=EntityType.fromName(args[2]);
                    EntityType.fromName(args[6]);
                } catch (NullPointerException e2) {
                    return "Invalid monster name.";
                }
            }
            if (type == null) {
                type = CTName(args[2]);
            }
            if (type == null) return "Invalid monster name " + args[2] + ".";
            period = Integer.parseInt(args[7]);
            count = Integer.parseInt(args[8]);
            min = Integer.parseInt(args[9]);
            max = Integer.parseInt(args[10]);
            chance = Double.parseDouble(args[11]);
            pointlist.add(new spawnpoint(name, worldnamez, x, y, z, type, period, count, min, max, chance));
            //fully typed
            setconfig();
            save();
            return "Spawn point added.";
        } catch (Exception e) {
            return "Invalid input for console, use /ma addspawnpoint (worldname) (x) (y) (z) [name] [mobtype] [period (X seconds)] [mobcount] [minlight(0-15)] [maxlight (0-15)] [chance (1-100)]";
        }
    }

    private String removespawnpoint(String[] args) {
        try {
            name = args[1];
            for (int i = 0; i < pointlist.size(); i++) {

                if (pointlist.get(i).getName().equalsIgnoreCase(name)) {
                    pointlist.remove(i);
                    --i;
                    setconfig();
                    save();
                    return "Spawn point removed.";
                }
            }


            return "Spawn point does not exist.";
        } catch (Exception e) {
            return "Invalid input, use /ma removespawnpoint [name]";
        }
    }

    public void save() {
        try {
            plugin.conf.save(plugin.confFile);
        } catch (IOException e) {
            System.out.println("Monster Apocalypse: Error saving spawnpoints.");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Monster Apocalypse: Error saving spawnpoints.");
            e.printStackTrace();
        }
    }

    private void setconfig() {
        String pointname;
        conflist.clear();
        for (int n = 0; n < pointlist.size(); n++) {
            spawnpoint point = pointlist.get(n);
            pointname = point.getName();
            plugin.conf.set(pointname + ".name", pointname);
            plugin.conf.set(pointname + ".world", point.getPos().getWorld().getName());
            plugin.conf.set(pointname + ".x", point.getPos().getX());
            plugin.conf.set(pointname + ".y", point.getPos().getY());
            plugin.conf.set(pointname + ".z", point.getPos().getZ());
            try {
                plugin.conf.set(pointname + ".type", point.getType().getName());
            } catch (NullPointerException e) {
                System.out.println("Monster Apocalypse: MagmaCube bug detected, ignoring spawnpoint, please use CB-1.0.1-R1");
                conflist.remove(n);
                pointlist.remove(n);
                --n;
                continue;
            }
            plugin.conf.set(pointname + ".period", point.getPeriod());
            plugin.conf.set(pointname + ".count", point.getCount());
            plugin.conf.set(pointname + ".lightmin", point.getMinLight());
            plugin.conf.set(pointname + ".lightmax", point.getMaxLight());
            plugin.conf.set(pointname + ".spawn chance", point.getChance());
            plugin.conf.set(pointname + ".current time", point.getTime());
            for (String aConflist : conflist) {
                if (aConflist.equalsIgnoreCase(pointname)) {
                    continue;
                }
            }
            conflist.add(pointname);
        }
        plugin.conf.set("Spawn Point Names (Do not edit)", conflist);
    }

    private void getspawnlist() {
        try {
            conflist = plugin.conf.getStringList("Spawn Point Names (Do not edit)");
        } catch (NullPointerException e) {
            conflist = new ArrayList<>();
            conflist.add("startlist");
        }
        if (conflist == null || conflist.isEmpty()) {
            conflist = new ArrayList<>();
            conflist.add("startlist");
        }
        plugin.conf.set("Spawn Point Names (Do not edit)", conflist);
    }

    private void fillpointlist() {
        String pointname;
        for (String aConflist : conflist) {
            pointname = aConflist;
            if (pointname.equals("startlist")) continue;
            for (spawnpoint aPointlist : pointlist) {
                if (aPointlist.getName().equalsIgnoreCase(pointname)) {
                    continue;
                }
            }
            try {
                EntityType.fromName(plugin.conf.getString(pointname + ".type"));
            } catch (NullPointerException e) {
                System.out.println("Monster Apocalypse: Monster type " + plugin.conf.getString(pointname + ".type") + " not valid or CB Version too high, please use CB-1.0.1-R1.");
                continue;
            }
            try {
                pointlist.add(new spawnpoint(
                        plugin.conf.getString(pointname + ".name"),
                        plugin.conf.getString(pointname + ".world"),
                        plugin.conf.getDouble(pointname + ".x"),
                        plugin.conf.getDouble(pointname + ".y"),
                        plugin.conf.getDouble(pointname + ".z"),
                        EntityType.fromName(plugin.conf.getString(pointname + ".type")),
                        plugin.conf.getInt(pointname + ".period"),
                        plugin.conf.getInt(pointname + ".count"),
                        plugin.conf.getInt(pointname + ".lightmin"),
                        plugin.conf.getInt(pointname + ".lightmax"),
                        plugin.conf.getDouble(pointname + ".spawn chance"),
                        plugin.conf.getInt(pointname + ".current time")
                ));
            } catch (NullPointerException e) {
                System.out.println("Monster Apocalypse: World " + plugin.conf.getString(pointname + ".world") + " not found for spawnpoint " + plugin.conf.getString(pointname + ".name") + ", spawnpoint skipped.");
            }
        }
    }


    public void run() {
        for (spawnpoint aPointlist1 : pointlist) {
            aPointlist1.incrTime();
        }
        for (spawnpoint aPointlist : pointlist) {
            aPointlist.tryspawn();
        }
    }

    @SuppressWarnings("unused")
    private class spawnpoint {
        String name;
        Location pos;
        EntityType type;
        int period, count, minlight, maxlight;
        double chance;
        int currenttime;

        public spawnpoint(String nam, String world, double x, double y, double z, EntityType t, int per, int cou, int min, int max, double ch, int ct) {
            name = nam;
            pos = new Location(plugin.getServer().getWorld(world), x, y, z);
            type = t;
            period = per;
            count = cou;
            minlight = min;
            maxlight = max;
            chance = ch;
            currenttime = ct;
        }

        public spawnpoint(String nam, String world, double x, double y, double z, EntityType t, int per, int cou, int min, int max, double ch) {
            name = nam;
            pos = new Location(plugin.getServer().getWorld(world), x, y, z);
            type = t;
            period = per;
            count = cou;
            minlight = min;
            maxlight = max;
            chance = ch;
            currenttime = 0;
        }

        public spawnpoint(String nam, Location loc, EntityType t, int per, int cou, int min, int max, double ch, int ct) {
            name = nam;
            pos = loc;
            type = t;
            period = per;
            count = cou;
            minlight = min;
            maxlight = max;
            chance = ch;
            currenttime = ct;
        }

        public spawnpoint(String nam, Location loc, EntityType t, int per, int cou, int min, int max, double ch) {
            name = nam;
            pos = loc;
            type = t;
            period = per;
            count = cou;
            minlight = min;
            maxlight = max;
            chance = ch;
            currenttime = 0;
        }

        public spawnpoint(String nam, Location loc, EntityType t, int per, int cou, int min, int max) {
            name = nam;
            pos = loc;
            type = t;
            period = per;
            count = cou;
            minlight = min;
            maxlight = max;
            chance = 100.0d;
            currenttime = 0;
        }

        public spawnpoint(String nam, Location loc, EntityType t, int per, int cou) {
            name = nam;
            pos = loc;
            type = t;
            period = per;
            count = cou;
            minlight = 0;
            maxlight = 15;
            chance = 100.0d;
            currenttime = 0;
        }

        public spawnpoint(String nam, Location loc, EntityType t, int per) {
            name = nam;
            pos = loc;
            type = t;
            period = per;
            count = 1;
            minlight = 0;
            maxlight = 15;
            chance = 100.0d;
            currenttime = 0;
        }

        public spawnpoint(String nam, Location loc, EntityType t) {
            name = nam;
            pos = loc;
            type = t;
            period = 30;
            count = 1;
            minlight = 0;
            maxlight = 15;
            chance = 100.0d;
            currenttime = 0;
        }

        private void spawnmob() {
            for (int n = 0; n < count; n++) {
                if (plugin.spawnrand.nextDouble() * 100d <= chance) {
                    if (pos.getBlock().getLightLevel() >= minlight && pos.getBlock().getLightLevel() <= maxlight) {
//						pos.getWorld().spawnEntity(pos, type);
                        if (type == EntityType.WITHER_SKULL) {
                            Skeleton sk = ((Skeleton) pos.getWorld().spawnEntity(pos, EntityType.SKELETON));
                            sk.setSkeletonType(SkeletonType.WITHER);
                            sk.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD));
                            //pos.getWorld().getEntities().add(sk);
                        } else if (type == EntityType.SKELETON) {
                            Skeleton sk = ((Skeleton) pos.getWorld().spawnEntity(pos, EntityType.SKELETON));
                            sk.getEquipment().setItemInHand(new ItemStack(Material.BOW));
                            //pos.getWorld().getEntities().add(sk);
                        } else {
                            //pos.getWorld().getEntities().add(
                            pos.getWorld().spawnEntity(pos, type);
                            //);
                        }
                    }
                }
            }
        }

        public void tryspawn() {
            for (int i = 0; i < plugin.worldnames.size(); i++) {
                String worldname = plugin.worldnames.get(i);
                if (pos.getWorld().getName().equals(worldname)) break;
                if (i == plugin.worldnames.size() - 1) return;
            }
            if (currenttime >= period) {
                spawnmob();
                resetTime();
            }
        }

        public String getName() {
            return name;
        }

        public Location getPos() {
            return pos;
        }

        public EntityType getType() {
            return type;
        }

        public int getPeriod() {
            return period;
        }

        public int getCount() {
            return count;
        }

        public int getMinLight() {
            return minlight;
        }

        public int getMaxLight() {
            return maxlight;
        }

        public double getChance() {
            return chance;
        }

        public int getTime() {
            return currenttime;
        }

        public void incrTime() {
            currenttime++;
        }

        public void resetTime() {
            currenttime = 0;
        }

        public boolean setPos(Location l) {
            for (int i = 0; i < plugin.worldnames.size(); i++) {
                String worldname = plugin.worldnames.get(i);
                if (pos.getWorld().getName().equals(worldname)) break;
                if (i == plugin.worldnames.size() - 1) return false;
            }
            pos = l;
            return true;
        }

        public void setType(EntityType c) {
            type = c;
        }

        public void setPeriod(int p) {
            period = p;
        }

        public void setCount(int c) {
            count = c;
        }

        public void setMinLight(int m) {
            minlight = m;
        }

        public void setMaxLight(int m) {
            maxlight = m;
        }

        public void setChance(double c) {
            chance = c;
        }

        public void setX(double x) {
            pos.setX(x);
        }

        public void setY(double x) {
            pos.setY(x);
        }

        public void setZ(double x) {
            pos.setZ(x);
        }
    }
}
