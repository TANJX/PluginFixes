package info.TrenTech.EasyKits.Events;

import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.Kit.KitUser;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KitPlayerEquipEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private Player player;
    private Kit kit;
    private KitUser kitUser;

    public KitPlayerEquipEvent(Player player, Kit kit, KitUser kitUser) {
        this.player = player;
        this.kit = kit;
        this.kitUser = kitUser;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public Kit getKit() {
        return kit;
    }

    public Player getPlayer() {
        return player;
    }

    public KitUser getKitUser() {
        return kitUser;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
