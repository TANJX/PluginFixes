package net.doodcraft.oshcon.bukkit.enderpads.listeners;

import net.doodcraft.oshcon.bukkit.enderpads.api.EnderPadAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class BlockListener implements org.bukkit.event.Listener {
    public BlockListener() {
    }

    @EventHandler(ignoreCancelled = true)
    public void onBurn(org.bukkit.event.block.BlockBurnEvent event) {
        EnderPadAPI.destroyCheck(event.getBlock(), null);
    }

    @EventHandler(ignoreCancelled = true)
    public void onGrow(org.bukkit.event.block.BlockGrowEvent event) {
        EnderPadAPI.destroyCheck(event.getBlock(), null);
    }

    @EventHandler(ignoreCancelled = true)
    public void onSpread(org.bukkit.event.block.BlockSpreadEvent event) {
        EnderPadAPI.destroyCheck(event.getBlock(), null);
    }

    @EventHandler(ignoreCancelled = true)
    public void onFade(org.bukkit.event.block.BlockFadeEvent event) {
        EnderPadAPI.destroyCheck(event.getBlock(), null);
    }

    @EventHandler(ignoreCancelled = true)
    public void onExtend(BlockPistonExtendEvent event) {
        for (org.bukkit.block.Block block : event.getBlocks()) {
            EnderPadAPI.destroyCheck(block, null);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onRetract(BlockPistonRetractEvent event) {
        if (net.doodcraft.oshcon.bukkit.enderpads.util.Compatibility.isSupported(net.doodcraft.oshcon.bukkit.enderpads.EnderPadsPlugin.version, "1.8", "2.0")) {
            for (org.bukkit.block.Block block : event.getBlocks()) {
                EnderPadAPI.destroyCheck(block, null);
            }
        } else {
            EnderPadAPI.destroyCheck(event.getBlock(), null);
            EnderPadAPI.destroyCheck(event.getRetractLocation().getBlock(), null);
        }
    }
}
