package net.doodcraft.oshcon.bukkit.enderpads.listeners;

import net.doodcraft.oshcon.bukkit.enderpads.EnderPadsPlugin;
import net.doodcraft.oshcon.bukkit.enderpads.api.EnderPad;
import net.doodcraft.oshcon.bukkit.enderpads.api.EnderPadAPI;
import net.doodcraft.oshcon.bukkit.enderpads.api.EnderPadDestroyEvent;
import net.doodcraft.oshcon.bukkit.enderpads.api.EnderPadUseEvent;
import net.doodcraft.oshcon.bukkit.enderpads.config.Settings;
import net.doodcraft.oshcon.bukkit.enderpads.util.StaticMethods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerListener implements org.bukkit.event.Listener {
    public PlayerListener() {
    }

    @EventHandler(ignoreCancelled = true)
    public void onUse(EnderPadUseEvent event) {
        final Player player = (Player) event.getEntity();
        EnderPad origin = event.getOriginEnderPad();
        EnderPad dest = event.getDestinationEnderPad();
        Location from = origin.getLocation().add(0.0D, 1.0D, 0.0D);
        final Location to = dest.getLocation().add(0.0D, 1.0D, 0.0D);

        if (!dest.isValid()) {
            dest.delete(null);
            EnderPadAPI.teleportPlayer(origin, player);
            return;
        }

        EnderPadsPlugin.playerCooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));

        if ((Settings.lightningUse.booleanValue()) &&
                (!StaticMethods.isVanished(player))) {
            to.getWorld().strikeLightningEffect(to);
        }


        if ((Settings.logUse.booleanValue()) || (Settings.debug.booleanValue())) {
            StaticMethods.log("&b" + player.getName() + " 使用了 EnderPad: &d" + EnderPadAPI.getLocString(from) + " &b-> &d" + EnderPadAPI.getLocString(to));
        }

        to.setYaw(player.getLocation().getYaw());
        to.setPitch(player.getLocation().getPitch());

        Bukkit.getScheduler().runTaskLater(EnderPadsPlugin.plugin, new Runnable() {

            public void run() {
                player.teleport(to, org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        }, 1L);
    }


    public boolean isOffHandClick(PlayerInteractEvent event) {
        if (net.doodcraft.oshcon.bukkit.enderpads.util.Compatibility.isSupported(EnderPadsPlugin.version, "1.9", "2.0")) {
            return event.getHand().equals(EquipmentSlot.valueOf("OFF_HAND"));
        }
        try {
            return event.getHand().equals(EquipmentSlot.valueOf("OFF_HAND"));
        } catch (NoSuchMethodError ex) {
        }
        return false;
    }


    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (isOffHandClick(event)) {
                return;
            }
            Block clicked = event.getClickedBlock();
            org.bukkit.Material type = clicked.getType();
            if (EnderPadAPI.isValidPlate(type).booleanValue()) {
                EnderPad enderPad = new EnderPad(clicked.getRelative(BlockFace.DOWN).getLocation());
                if (enderPad.isValid()) {
                    if (!StaticMethods.hasPermission(player, "enderpads.seeinfo", Boolean.valueOf(false)).booleanValue()) {
                        return;
                    }
                    if ((!player.getUniqueId().equals(enderPad.getOwnerUUID())) &&
                            (!StaticMethods.hasPermission(player, "enderpads.seeinfo.others", Boolean.valueOf(false)).booleanValue())) {
                        return;
                    }

                    if (player.isSneaking()) {
                        event.setCancelled(true);
                    }
                    String linkId = enderPad.getLinkId();
                    net.doodcraft.oshcon.bukkit.enderpads.config.Configuration linkedPads = new net.doodcraft.oshcon.bukkit.enderpads.config.Configuration(EnderPadsPlugin.plugin.getDataFolder() + java.io.File.separator + "data" + java.io.File.separator + "linked.yml");
                    java.util.List<String> linked = linkedPads.getStringList(linkId);
                    player.sendMessage(StaticMethods.addColor("&8:: &3传送板: &7[" + enderPad.getPadId() + "]"));
                    if (linked.size() == 1) {
                        player.sendMessage(StaticMethods.addColor("&8- &3连接&8: &e无"));
                    } else {
                        player.sendMessage(StaticMethods.addColor("&8- &3连接&8: &e" + net.doodcraft.oshcon.bukkit.enderpads.util.NumberConverter.convert(linked.size() - 1).toUpperCase()));
                    }
                    if (StaticMethods.hasPermission(player, "enderpads.seeinfo.linkid", Boolean.valueOf(false)).booleanValue()) {
                        player.sendMessage(StaticMethods.addColor("&8- &3连接Id&8: &e" + enderPad.getLinkId()));
                    }
                    if (StaticMethods.hasPermission(player, "enderpads.seeinfo.owner", Boolean.valueOf(false)).booleanValue()) {
                        String ownerString = "&8- &3主人&8: &e" + enderPad.getOwnerName();
                        if ((Bukkit.getPlayer(enderPad.getOwnerUUID()) != null) &&
                                (Bukkit.getPlayer(enderPad.getOwnerUUID()).isOnline())) {
                            ownerString = ownerString + " &a(在线)";
                        }

                        player.sendMessage(StaticMethods.addColor(ownerString));
                    }
                }
            }
        }
        if ((event.getAction().equals(Action.PHYSICAL)) &&
                (EnderPadAPI.isValidPlate(event.getClickedBlock().getType()).booleanValue())) {
            Block centerBlock = event.getClickedBlock().getRelative(BlockFace.DOWN);
            EnderPad enderPad = new EnderPad(centerBlock.getLocation());
            if (EnderPadsPlugin.playerCooldowns.containsKey(player.getName())) {
                if (System.currentTimeMillis() - ((Long) EnderPadsPlugin.playerCooldowns.get(player.getName())).longValue() > Settings.playerCooldown * 1000) {
                    if (enderPad.isValid()) {
                        if (StaticMethods.hasPermission(player, "enderpads.use", Boolean.valueOf(true)).booleanValue()) {
                            EnderPadAPI.teleportPlayer(enderPad, player);
                        }
                    } else {
                        EnderPadDestroyEvent destroyEvent = new EnderPadDestroyEvent(enderPad, null);
                        Bukkit.getServer().getPluginManager().callEvent(destroyEvent);
                    }
                } else {
                    long remaining = Settings.playerCooldown * 1000 - (System.currentTimeMillis() - ((Long) EnderPadsPlugin.playerCooldowns.get(player.getName())).longValue());
                    long portion = Settings.playerCooldown * 1000 / 4;
                    if (remaining < Settings.playerCooldown * 1000 - portion) {
                        if ((enderPad.isValid()) && (enderPad.isSaved())) {
                            if ((StaticMethods.hasPermission(player, "enderpads.use", Boolean.valueOf(true)).booleanValue()) &&
                                    (!Settings.cooldownMessage.equals(" ")) &&
                                    (!StaticMethods.isVanished(player))) {
                                net.doodcraft.oshcon.bukkit.enderpads.util.ReflectionUtil.sendActionBar(player, StaticMethods.addColor(Settings.cooldownMessage.replaceAll("<remaining>", String.valueOf(remaining))));
                            }
                        } else {
                            EnderPadDestroyEvent destroyEvent = new EnderPadDestroyEvent(enderPad, null);
                            Bukkit.getServer().getPluginManager().callEvent(destroyEvent);
                        }
                    }
                }
            } else if (enderPad.isValid()) {
                EnderPadAPI.teleportPlayer(enderPad, player);
            } else {
                EnderPadDestroyEvent destroyEvent = new EnderPadDestroyEvent(enderPad, null);
                Bukkit.getServer().getPluginManager().callEvent(destroyEvent);
            }
        }
    }


    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        Block block = event.getBlock();
        org.bukkit.Material material = block.getType();
        if (net.doodcraft.oshcon.bukkit.enderpads.util.BlockHelper.isPhysicsBlock(material)) {
            return;
        }
        String valid = Settings.centerMaterial.toUpperCase();
        String checkBelow;
        if (EnderPadAPI.isValidPlate(material).booleanValue()) {
            Block below = block.getRelative(BlockFace.DOWN);
            checkBelow = EnderPadAPI.getBlockString(below);
            if (checkBelow.equals(valid)) {
                EnderPadAPI.runTelepadCheck(below, player, Boolean.valueOf(true));
            }
        }
        String check = EnderPadAPI.getBlockString(block);
        if (check.equals(valid)) {
            EnderPadAPI.runTelepadCheck(block, player, Boolean.valueOf(true));
        }
        for (BlockFace face : EnderPadsPlugin.faces) {
            Block b = block.getRelative(face);
            String checkB = EnderPadAPI.getBlockString(b);
            if (checkB.equals(valid)) {
                EnderPadAPI.runTelepadCheck(b, player, Boolean.valueOf(true));
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player != null) {
            EnderPadAPI.destroyCheck(event.getBlock(), player);
        } else {
            EnderPadAPI.destroyCheck(event.getBlock(), null);
        }
    }
}
