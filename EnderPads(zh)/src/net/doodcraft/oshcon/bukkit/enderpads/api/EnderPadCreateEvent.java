package net.doodcraft.oshcon.bukkit.enderpads.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EnderPadCreateEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private EnderPad enderPad;
    private boolean cancelled;

    public EnderPadCreateEvent(EnderPad enderPad, Player player) {
        this.player = player;
        this.enderPad = enderPad;
    }

    public Player getPlayer() {
        return player;
    }

    public EnderPad getEnderPad() {
        return enderPad;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean bln) {
        cancelled = bln;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
