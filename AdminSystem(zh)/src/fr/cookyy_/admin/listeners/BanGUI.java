package fr.cookyy_.admin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BanGUI implements org.bukkit.event.Listener {

    public static void openBanGUI(Player player) {
        Inventory ban = Bukkit.createInventory(null, 54, "§4被封禁的玩家");
        int slot = 0;
        for (OfflinePlayer t : Bukkit.getBannedPlayers()) {
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            ItemMeta meta = skull.getItemMeta();
            meta.setDisplayName(t.getName());
            skull.setItemMeta(meta);

            ban.setItem(slot, skull);
            slot++;
            if (slot >= 54) break;
        }
        player.openInventory(ban);
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (!event.getInventory().getName().equalsIgnoreCase("§4被封禁的玩家")) {
            return;
        }
        Player p = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if ((event.getCurrentItem() == null) || (event.getCurrentItem().getType() == Material.AIR) || (!event.getCurrentItem().hasItemMeta())) {
            p.closeInventory();
        }
    }
}
