//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.doodcraft.oshcon.bukkit.enderpads.api;

import net.doodcraft.oshcon.bukkit.enderpads.EnderPadsPlugin;
import net.doodcraft.oshcon.bukkit.enderpads.config.Configuration;
import net.doodcraft.oshcon.bukkit.enderpads.config.Settings;
import net.doodcraft.oshcon.bukkit.enderpads.util.BlockHelper;
import net.doodcraft.oshcon.bukkit.enderpads.util.StaticMethods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.io.File;
import java.util.*;

public class EnderPadAPI {
    public EnderPadAPI() {
    }

    public static boolean isValidCenter(String string) {
        return string.equals(Settings.centerMaterial.toUpperCase());
    }

    public static Boolean isValidPlate(Material material) {
        return Boolean.valueOf(material.equals(Material.STONE_PLATE) || material.equals(Material.WOOD_PLATE) || material.equals(Material.GOLD_PLATE) || material.equals(Material.IRON_PLATE));
    }

    public static void teleportPlayer(EnderPad enderPad, Player player) {
        if (StaticMethods.hasPermission(player, "enderpads.use", Boolean.valueOf(true)).booleanValue()) {
            Configuration pads = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + "pads.yml");
            if (pads.contains(enderPad.getPadId())) {
                String linkedId = pads.getString(enderPad.getPadId() + ".LinkId");
                Configuration linkedPads = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + "linked.yml");
                List<String> list = linkedPads.getStringList(linkedId);
                list.remove(enderPad.getPadId());
                if (list.size() >= 1) {
                    ArrayList<Location> locations = new ArrayList();
                    Iterator var7 = list.iterator();

                    while (var7.hasNext()) {
                        String loc = (String) var7.next();
                        String[] coords = loc.split(" ");
                        String world = String.valueOf(coords[0]);
                        double x = Double.valueOf(coords[1]).doubleValue();
                        double y = Double.valueOf(coords[2]).doubleValue();
                        double z = Double.valueOf(coords[3]).doubleValue();
                        Location location = new Location(Bukkit.getWorld(world), x, y, z);
                        if (Settings.safeTeleport.booleanValue()) {
                            Block block = location.getWorld().getBlockAt((int) location.getX(), (int) location.getY() + 2, (int) location.getZ());
                            if (!block.isEmpty()) {
                                if (!block.getType().isSolid()) {
                                    locations.add(location);
                                }
                            } else {
                                locations.add(location);
                            }
                        } else {
                            locations.add(location);
                        }
                    }

                    if (locations.size() > 0) {
                        Location random = (Location) locations.get(EnderPadsPlugin.random.nextInt(locations.size()));
                        EnderPad dest = new EnderPad(random);
                        EnderPadUseEvent useEvent = new EnderPadUseEvent(enderPad, dest, player);
                        Bukkit.getPluginManager().callEvent(useEvent);
                    }
                }
            }

        }
    }

    public static String getLocString(Location loc) {
        return loc.getWorld().getName() + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ();
    }

    public static void runTelepadCheck(Block block, Player player, Boolean create) {
        if (player != null) {
            EnderPad enderPad = new EnderPad(block.getLocation(), player);
            if (create.booleanValue()) {
                if (enderPad.isValid()) {
                    enderPad.save();
                }
            } else if (enderPad.isValid()) {
                EnderPad t = getPadFromLocation(enderPad.getLocation());
                if (t != null && t.isValid()) {
                    enderPad.delete(player);
                }
            }
        } else {
            runTelepadCheck(block, create);
        }

    }

    public static void runTelepadCheck(Block block, Boolean create) {
        EnderPad enderPad = new EnderPad(block.getLocation());
        if (!create.booleanValue()) {
            if (enderPad.isValid()) {
                enderPad.delete((Player) null);
            }
        } else if (enderPad.isValid()) {
            enderPad.save();
        }

    }

    public static void destroyCheck(Block block, Player player) {
        Material material = block.getType();
        runTelepadCheck(block, player, Boolean.valueOf(false));
        if (isValidPlate(material).booleanValue()) {
            Block below = block.getRelative(BlockFace.DOWN);
            String check = getBlockString(below);
            String valid = Settings.centerMaterial.toUpperCase();
            if (check.equals(valid)) {
                runTelepadCheck(below, player, Boolean.valueOf(false));
                return;
            }
        }

        BlockFace[] var10 = EnderPadsPlugin.faces;
        int var11 = var10.length;

        for (int var12 = 0; var12 < var11; ++var12) {
            BlockFace face = var10[var12];
            Block b = block.getRelative(face);
            String check = getBlockString(b);
            String valid = Settings.centerMaterial.toUpperCase();
            if (check.equals(valid)) {
                runTelepadCheck(b, player, Boolean.valueOf(false));
            }
        }

    }

    public static EnderPad getPadFromLocation(Location location) {
        Block block = location.getBlock();
        if (!block.isEmpty()) {
            Material type = block.getType();
            if (isValidPlate(type).booleanValue()) {
                Block center = block.getRelative(BlockFace.DOWN);
                return getPadFromBlock(center);
            }

            EnderPad pad = getPadFromBlock(block);
            if (pad != null) {
                return pad;
            }

            BlockFace[] var4 = EnderPadsPlugin.faces;
            int var5 = var4.length;
            byte var6 = 0;
            if (var6 < var5) {
                BlockFace face = var4[var6];
                Block b = block.getRelative(face);
                return getPadFromBlock(b);
            }
        }

        return null;
    }

    public static EnderPad getPadFromID(String id) {
        String[] coords = id.split(" ");
        String world = String.valueOf(coords[0]);
        double x = Double.valueOf(coords[1]).doubleValue();
        double y = Double.valueOf(coords[2]).doubleValue();
        double z = Double.valueOf(coords[3]).doubleValue();
        Location location = new Location(Bukkit.getWorld(world), x, y, z);
        return getPadFromLocation(location);
    }

    public static EnderPad getPadFromBlock(Block block) {
        String check = getBlockString(block);
        String valid = Settings.centerMaterial.toUpperCase();
        if (check.equals(valid)) {
            EnderPad enderPad = new EnderPad(block.getLocation());
            return enderPad.isValid() && enderPad.isSaved() ? enderPad : null;
        } else {
            return null;
        }
    }

    public static String getBlockString(Block block) {
        return BlockHelper.fixDual(block) + "~" + BlockHelper.fixVariant(block);
    }

    public static int getMaxPads(Player player) {
        if (StaticMethods.hasPermission(player, "enderpads.use", Boolean.valueOf(false)).booleanValue()) {
            if (player.isOp()) {
                return 2147483647;
            } else if (player.hasPermission("enderpads.*")) {
                return 2147483647;
            } else {
                boolean hasNode = false;
                Set<PermissionAttachmentInfo> perms = player.getEffectivePermissions();
                ArrayList<Integer> possibleValues = new ArrayList();
                possibleValues.add(Integer.valueOf(Settings.defaultMax));
                Iterator var4 = perms.iterator();

                while (var4.hasNext()) {
                    PermissionAttachmentInfo perm = (PermissionAttachmentInfo) var4.next();
                    String permission = perm.getPermission();
                    if (permission.toLowerCase().startsWith("enderpads.max.")) {
                        hasNode = true;
                        String[] args = permission.split("\\.");
                        if (permission.toLowerCase().equals("enderpads.max.*")) {
                            return 2147483647;
                        }

                        try {
                            possibleValues.add(Integer.valueOf(args[2]));
                        } catch (Exception var9) {
                            StaticMethods.debug("&eDiscovered an invalid permission node for &b" + player.getName() + "&e: &c" + permission);
                            possibleValues.add(Integer.valueOf(Settings.defaultMax));
                        }
                    }
                }

                if (hasNode) {
                    return ((Integer) Collections.max(possibleValues)).intValue();
                } else if (Settings.defaultMax == 0) {
                    return 0;
                } else if (Settings.defaultMax < 0) {
                    return 2147483647;
                } else {
                    return Settings.defaultMax;
                }
            }
        } else {
            return 0;
        }
    }

    public static void addTelepadToMemory(EnderPad enderPad) {
        if (!EnderPadsPlugin.enderPads.containsKey(enderPad.getPadId())) {
            EnderPadsPlugin.enderPads.put(enderPad.getPadId(), enderPad);
            AddToMemoryEvent event = new AddToMemoryEvent(enderPad);
            Bukkit.getPluginManager().callEvent(event);
        }

    }

    public static void removeTelepadFromMemory(EnderPad enderPad) {
        EnderPad actualPad = new EnderPad(enderPad.getLocation());
        if (EnderPadsPlugin.enderPads.containsKey(actualPad.getPadId())) {
            EnderPadsPlugin.enderPads.remove(actualPad.getPadId());
            RemoveFromMemoryEvent event = new RemoveFromMemoryEvent(actualPad);
            Bukkit.getPluginManager().callEvent(event);
        }

    }

    public static boolean verify(Location location, EnderPad enderPad, String linkId, Configuration pads) {
        Block block = location.getBlock();
        enderPad.setLinkId(linkId);
        if (!enderPad.isValid()) {
            return false;
        } else {
            String bCenter = getBlockString(block);
            String bNorth = getBlockString(block.getRelative(BlockFace.NORTH));
            String bEast = getBlockString(block.getRelative(BlockFace.EAST));
            String bSouth = getBlockString(block.getRelative(BlockFace.SOUTH));
            String bWest = getBlockString(block.getRelative(BlockFace.WEST));
            return !bCenter.equals(pads.getString(enderPad.getPadId() + ".Center.Block")) ? false : (!bNorth.equals(pads.getString(enderPad.getPadId() + ".Faces.North")) ? false : (!bEast.equals(pads.getString(enderPad.getPadId() + ".Faces.East")) ? false : (!bSouth.equals(pads.getString(enderPad.getPadId() + ".Faces.South")) ? false : bWest.equals(pads.getString(enderPad.getPadId() + ".Faces.West")))));
        }
    }

    public static void verifyAllTelepads() {
        long start = System.currentTimeMillis();
        StaticMethods.log("&bVerifying and caching all EnderPads..");
        final Configuration pads = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + "pads.yml");
        Bukkit.getServer().getScheduler().runTask(EnderPadsPlugin.plugin, new Runnable() {
            public void run() {
                Iterator var1 = pads.getKeys(false).iterator();

                while (var1.hasNext()) {
                    String padId = (String) var1.next();
                    String[] args = padId.split(" ");
                    World world = Bukkit.getWorld(args[0]);
                    double x = (double) Integer.valueOf(args[1]).intValue();
                    double y = (double) Integer.valueOf(args[2]).intValue();
                    double z = (double) Integer.valueOf(args[3]).intValue();
                    Location padLocation = new Location(world, x, y, z);
                    EnderPad enderPad = new EnderPad(padLocation);
                    if (EnderPadAPI.verify(padLocation, enderPad, pads.getString(padId + ".LinkId"), pads)) {
                        EnderPadAPI.addTelepadToMemory(enderPad);
                    } else {
                        StaticMethods.log("&cDiscovered and removed an invalid EnderPad: &e" + enderPad.getPadId());
                        enderPad.delete((Player) null);
                        EnderPadAPI.removeTelepadFromMemory(enderPad);
                    }
                }

            }
        });
        long finish = System.currentTimeMillis();
        StaticMethods.log("&bVerified and cached " + pads.getKeys(false).size() + " EnderPads! &e(" + (finish - start) + "ms)");
    }
}
