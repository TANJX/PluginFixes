package net.doodcraft.oshcon.bukkit.enderpads.listeners;

import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityListener implements org.bukkit.event.Listener {
    public EntityListener() {
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onChange(EntityChangeBlockEvent event) {
        net.doodcraft.oshcon.bukkit.enderpads.api.EnderPadAPI.runTelepadCheck(event.getBlock(), Boolean.valueOf(false));
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onChange(EntityBlockFormEvent event) {
        net.doodcraft.oshcon.bukkit.enderpads.api.EnderPadAPI.runTelepadCheck(event.getBlock(), Boolean.valueOf(false));
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onExplosion(EntityExplodeEvent event) {
        for (org.bukkit.block.Block block : event.blockList()) {
            net.doodcraft.oshcon.bukkit.enderpads.api.EnderPadAPI.destroyCheck(block, null);
        }
    }
}
