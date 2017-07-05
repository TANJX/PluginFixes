package net.doodcraft.oshcon.bukkit.enderpads.listeners;

import org.bukkit.event.block.BlockExplodeEvent;

public class BlockExplodeListener implements org.bukkit.event.Listener {
    public BlockExplodeListener() {
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onExplode(BlockExplodeEvent event) {
        for (org.bukkit.block.Block block : event.blockList()) {
            if (block.isEmpty()) {
                net.doodcraft.oshcon.bukkit.enderpads.api.EnderPadAPI.destroyCheck(block, null);
            }
        }
    }
}
