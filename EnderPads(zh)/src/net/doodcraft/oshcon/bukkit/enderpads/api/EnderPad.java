package net.doodcraft.oshcon.bukkit.enderpads.api;

import net.doodcraft.oshcon.bukkit.enderpads.EnderPadsPlugin;
import net.doodcraft.oshcon.bukkit.enderpads.config.Configuration;
import net.doodcraft.oshcon.bukkit.enderpads.config.Settings;
import net.doodcraft.oshcon.bukkit.enderpads.util.StaticMethods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class EnderPad {
    private String padId;
    private String linkId;
    private UUID ownerUUID;
    private Location padLocation;
    private Block centerBlock;
    private Block northBlock;
    private Block eastBlock;
    private Block southBlock;
    private Block westBlock;

    public EnderPad(Location location) {
        setLocation(location);
        setPadId();
        if (isSaved()) {
            Configuration pads = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + "pads.yml");
            ownerUUID = UUID.fromString(pads.getString(getPadId() + ".Owner.UUID"));
        }
        setAllSides(location);
    }

    public EnderPad(Location location, Player player) {
        setLocation(location);
        setPadId();
        setOwnerUUID(player);
        setAllSides(location);
    }

    private void setPadId() {
        padId = String.valueOf(padLocation.getWorld().getName() + " " + (int) padLocation.getX() + " " + (int) padLocation.getY() + " " + (int) padLocation.getZ());
    }

    private void setLinkId(Block[] blocks) {
        List<String> blockNames = new java.util.ArrayList();
        for (Block block : blocks) {
            blockNames.add(EnderPadAPI.getBlockString(block));
        }
        java.util.Collections.sort(blockNames, java.text.Collator.getInstance());
        linkId = String.join("-", blockNames);
    }

    public void setAllSides(Location location) {
        Block block = location.getBlock();
        setCenterBlock(block);
        for (BlockFace face : EnderPadsPlugin.faces) {
            Block sideBlock = getCenterBlock().getRelative(face);
            if ((sideBlock.isEmpty()) || (sideBlock.isLiquid())) break;
            Material material = sideBlock.getType();
            if (Settings.blackListedBlocks.contains(material.toString().toUpperCase())) {
                break;
            }
            if ((!material.isBlock()) || (!material.isSolid()) || (!material.isOccluding()) || (material.isTransparent())) {
                break;
            }
            if (face.equals(BlockFace.NORTH)) {
                setNorthBlock(sideBlock);
            }
            if (face.equals(BlockFace.EAST)) {
                setEastBlock(sideBlock);
            }
            if (face.equals(BlockFace.SOUTH)) {
                setSouthBlock(sideBlock);
            }
            if (face.equals(BlockFace.WEST)) {
                setWestBlock(sideBlock);
            }
        }
    }


    public String getPadId() {
        return padId;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String id) {
        linkId = id;
    }

    public List<EnderPad> getLinkedPads() {
        Configuration linkedPads = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + "linked.yml");
        List<String> list = linkedPads.getStringList(padId);
        List<EnderPad> links = new java.util.ArrayList();
        for (String s : list) {
            links.add(EnderPadAPI.getPadFromID(s));
        }
        return links;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(Player player) {
        ownerUUID = player.getUniqueId();
    }

    public String getOwnerName() {
        return Bukkit.getOfflinePlayer(ownerUUID).getName();
    }

    public Location getLocation() {
        return padLocation;
    }

    public void setLocation(Location location) {
        padLocation = location;
    }

    public Block getCenterBlock() {
        return centerBlock;
    }

    public void setCenterBlock(Block block) {
        centerBlock = block;
    }

    public Block getNorthBlock() {
        return northBlock;
    }

    public void setNorthBlock(Block block) {
        northBlock = block;
    }

    public Block getEastBlock() {
        return eastBlock;
    }

    public void setEastBlock(Block block) {
        eastBlock = block;
    }

    public Block getSouthBlock() {
        return southBlock;
    }

    public void setSouthBlock(Block block) {
        southBlock = block;
    }

    public Block getWestBlock() {
        return westBlock;
    }

    public void setWestBlock(Block block) {
        westBlock = block;
    }

    public Block[] getSideBlocks() {
        return new Block[]{northBlock, eastBlock, southBlock, westBlock};
    }

    public void save() {
        if (ownerUUID != null) {
            Configuration pads = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + "pads.yml");
            Configuration linkedPads = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + "linked.yml");
            Configuration players = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + "players.yml");
            List<String> owned = players.getStringList(getOwnerUUID().toString());
            if (Bukkit.getPlayer(ownerUUID) != null) {
                EnderPadCreateEvent createEvent = new EnderPadCreateEvent(this, Bukkit.getPlayer(ownerUUID));
                Bukkit.getServer().getPluginManager().callEvent(createEvent);

                if (!createEvent.isCancelled()) {
                    if (owned.size() + 1 > EnderPadAPI.getMaxPads(Bukkit.getPlayer(ownerUUID))) {
                        Bukkit.getPlayer(ownerUUID).sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &r" + Settings.atMaximum));
                        return;
                    }
                    if (owned.isEmpty()) {
                        owned.add(getPadId());
                        players.add(getOwnerUUID().toString(), owned);
                        players.save();
                    } else {
                        owned.add(getPadId());
                        players.remove(getOwnerUUID().toString());
                        players.add(getOwnerUUID().toString(), owned);
                        players.save();
                    }
                    pads.add(getPadId() + ".LinkId", getLinkId());
                    pads.add(getPadId() + ".Center.Location", getLocation().getWorld().getName() + ", " + (getLocation().getX() + 1.0D) + ", " + getLocation().getY() + ", " + getLocation().getZ());
                    pads.add(getPadId() + ".Center.Block", EnderPadAPI.getBlockString(getCenterBlock()));
                    pads.add(getPadId() + ".Owner.UUID", String.valueOf(getOwnerUUID()));
                    pads.add(getPadId() + ".Faces.North", EnderPadAPI.getBlockString(getNorthBlock()));
                    pads.add(getPadId() + ".Faces.East", EnderPadAPI.getBlockString(getEastBlock()));
                    pads.add(getPadId() + ".Faces.South", EnderPadAPI.getBlockString(getSouthBlock()));
                    pads.add(getPadId() + ".Faces.West", EnderPadAPI.getBlockString(getWestBlock()));
                    pads.save();
                    List<String> links = linkedPads.getStringList(linkId);
                    if (links.isEmpty()) {
                        links.add(getPadId());
                        linkedPads.add(getLinkId(), links);
                        linkedPads.save();
                    } else {
                        links.add(getPadId());
                        linkedPads.remove(getLinkId());
                        linkedPads.add(getLinkId(), links);
                        linkedPads.save();
                    }
                    OfflinePlayer owner = Bukkit.getOfflinePlayer(ownerUUID);
                    if (StaticMethods.hasPermission((Player) owner, "enderpads.alerts", Boolean.valueOf(false)).booleanValue()) {
                        ((Player) owner).sendMessage(StaticMethods.addColor("&8:: &a已创建 &7[" + getPadId() + "]"));
                    }
                    if (StaticMethods.hasPermission((Player) owner, "enderpads.seeinfo", Boolean.valueOf(false)).booleanValue()) {
                        if (!StaticMethods.hasPermission((Player) owner, "enderpads.alerts", Boolean.valueOf(false)).booleanValue()) {
                            ((Player) owner).sendMessage(StaticMethods.addColor("&8:: &3传送板: &7[" + getPadId() + "] &a(new)"));
                        }
                        String max = String.valueOf(EnderPadAPI.getMaxPads((Player) owner));
                        if (max.equals("2147483647")) {
                            max = "&d∞";
                        }
                        List<String> linked = linkedPads.getStringList(linkId);
                        int size = linked.size();
                        if (size <= 0) {
                            size = 1;
                        }
                        if (size == 1) {
                            ((Player) owner).sendMessage(StaticMethods.addColor("&8- &3连接&8: &e无"));
                        } else {
                            ((Player) owner).sendMessage(StaticMethods.addColor("&8- &3连接&8: &e" + net.doodcraft.oshcon.bukkit.enderpads.util.NumberConverter.convert(size - 1).toUpperCase()));
                        }
                        if (StaticMethods.hasPermission((Player) owner, "enderpads.seeinfo.linkid", Boolean.valueOf(false)).booleanValue()) {
                            ((Player) owner).sendMessage(StaticMethods.addColor("&8- &3连接ID&8: &e" + getLinkId()));
                        }
                        ((Player) owner).sendMessage(StaticMethods.addColor("&8- &3限制&8: &e" + players.getStringList(ownerUUID.toString()).size() + "&7/&6" + max));
                    }
                    if ((Settings.logUse.booleanValue()) || (Settings.debug.booleanValue())) {
                        StaticMethods.log(owner.getName() + " created an EnderPad: " + getPadId());
                    }
                    EnderPadAPI.addTelepadToMemory(this);
                    if ((Settings.lightningCreate.booleanValue()) &&
                            (!StaticMethods.isVanished((Player) owner))) {
                        Location loc = getLocation();
                        loc.getWorld().strikeLightningEffect(loc);
                    }
                } else {
                    StaticMethods.debug("EnderPadCreateEvent was cancelled.");
                }
            } else {
                StaticMethods.debug("Player was null. Cannot create an EnderPad without a player.");
            }
        }
    }

    public void delete(Player player) {
        EnderPadDestroyEvent destroyEvent = new EnderPadDestroyEvent(this, player);
        Bukkit.getServer().getPluginManager().callEvent(destroyEvent);

        if (!destroyEvent.isCancelled()) {
            if (isSaved()) {
                Configuration pads = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + "pads.yml");
                Configuration linkedPads = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + "linked.yml");
                Configuration players = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + "players.yml");
                if (linkId == null) {
                    for (String key : linkedPads.getKeys(false)) {
                        List<String> linked = linkedPads.getStringList(key);
                        StaticMethods.log("&cThere was an error getting the linkId for " + getPadId());
                        StaticMethods.log("&cThis could be caused by 3rd party modification, like WorldEdit.");
                        StaticMethods.log("&eYou can usually ignore this warning. EnderPads will self-repair now.");
                        if (linked.size() <= 1) {
                            linkedPads.remove(key);
                            linkedPads.save();
                        } else {
                            linkedPads.remove(key);
                            linked.remove(getPadId());
                            linkedPads.add(key, linked);
                            linkedPads.save();
                        }
                    }
                } else {
                    try {
                        Object linked = linkedPads.getStringList(linkId);
                        if (((List) linked).size() <= 1) {
                            linkedPads.remove(getLinkId());
                            linkedPads.save();
                        } else {
                            linkedPads.remove(getLinkId());
                            ((List) linked).remove(getPadId());
                            linkedPads.add(getLinkId(), linked);
                            linkedPads.save();
                        }
                    } catch (Exception ex) {
                        if (Settings.debug.booleanValue()) {
                            ex.printStackTrace();
                        }
                    }
                }
                Object owned = players.getStringList(getOwnerUUID().toString());
                if (((List) owned).size() <= 1) {
                    players.remove(getOwnerUUID().toString());
                    players.save();
                } else {
                    ((List) owned).remove(getPadId());
                    players.remove(getOwnerUUID().toString());
                    players.add(getOwnerUUID().toString(), owned);
                    players.save();
                }
                if (pads.contains(getPadId())) {
                    pads.remove(getPadId());
                    pads.save();
                }

                OfflinePlayer owner = Bukkit.getOfflinePlayer(getOwnerUUID());
                if ((owner != null) &&
                        (owner.isOnline())) {
                    Player onlineOwner = (Player) owner;
                    if (player != null) {
                        if (player.equals(onlineOwner)) {
                            if (StaticMethods.hasPermission(player, "enderpads.alerts", Boolean.valueOf(false)).booleanValue()) {
                                player.sendMessage(StaticMethods.addColor("&8:: &c已破坏 &7[" + getPadId() + "]"));
                            }
                        } else if (StaticMethods.hasPermission(onlineOwner, "enderpads.alerts", Boolean.valueOf(false)).booleanValue()) {
                            onlineOwner.sendMessage(StaticMethods.addColor("&8:: &c有个人破坏了 &7[" + getPadId() + "]"));
                        }

                    } else if (StaticMethods.hasPermission(onlineOwner, "enderpads.alerts", Boolean.valueOf(false)).booleanValue()) {
                        onlineOwner.sendMessage(StaticMethods.addColor("&8:: &c有个人破坏了 &7[" + getPadId() + "]"));
                    }

                    if (StaticMethods.hasPermission((Player) owner, "enderpads.seeinfo", Boolean.valueOf(false)).booleanValue()) {
                        String max = String.valueOf(EnderPadAPI.getMaxPads((Player) owner));
                        if (max.equals("2147483647")) {
                            max = "&d∞";
                        }
                        List<String> linked = linkedPads.getStringList(linkId);
                        int size = linked.size();
                        if (size <= 0) {
                            size = 1;
                        }
                        if (!StaticMethods.hasPermission(onlineOwner, "enderpads.alerts", Boolean.valueOf(false)).booleanValue()) {
                            onlineOwner.sendMessage(StaticMethods.addColor("&8:: &3传送板: &7[" + getPadId() + "] &c(removed)"));
                        }
                        if (size == 1) {
                            onlineOwner.sendMessage(StaticMethods.addColor("&8- &3连接&8: &e无"));
                        } else {
                            onlineOwner.sendMessage(StaticMethods.addColor("&8- &3连接&8: &e" + net.doodcraft.oshcon.bukkit.enderpads.util.NumberConverter.convert(size - 1).toUpperCase()));
                        }
                        if (StaticMethods.hasPermission(onlineOwner, "enderpads.seeinfo.linkid", Boolean.valueOf(false)).booleanValue()) {
                            onlineOwner.sendMessage(StaticMethods.addColor("&8- &3连接ID&8: &e" + getLinkId()));
                        }
                        onlineOwner.sendMessage(StaticMethods.addColor("&8- &3限制: &e" + players.getStringList(ownerUUID.toString()).size() + "&7/&6" + max));
                    }
                }


                if ((Settings.logUse.booleanValue()) || (Settings.debug.booleanValue())) {
                    if (player != null) {
                        StaticMethods.log(player.getName() + " destroyed an EnderPad: " + getPadId());
                    } else {
                        StaticMethods.log("An EnderPad was destroyed: " + getPadId());
                    }
                }
                EnderPadAPI.removeTelepadFromMemory(this);
                if ((Settings.lightningDestroy.booleanValue()) &&
                        (!StaticMethods.isVanished(player))) {
                    Location loc = getLocation();
                    loc.getWorld().strikeLightningEffect(loc);
                }
            }
        } else {
            StaticMethods.debug("EnderPadDestroyEvent was cancelled.");
        }
    }

    public boolean isValid() {
        Block[] blocks = getSideBlocks();
        for (Block block : blocks) {
            if (block == null) {
                return false;
            }
        }
        setLinkId(blocks);
        if (padId == null) {
            return false;
        }
        if (ownerUUID == null) {
            return false;
        }
        if (padLocation == null) {
            return false;
        }
        if (centerBlock == null) {
            return false;
        }
        if (!EnderPadAPI.isValidPlate(centerBlock.getRelative(BlockFace.UP).getType()).booleanValue()) {
            return false;
        }
        String check = EnderPadAPI.getBlockString(getCenterBlock());
        String valid = Settings.centerMaterial.toUpperCase();
        if (!check.equals(valid)) {
            return false;
        }
        return linkId != null;
    }

    public boolean isSaved() {
        Configuration pads = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + "pads.yml");
        return pads.contains(padId);
    }
}
