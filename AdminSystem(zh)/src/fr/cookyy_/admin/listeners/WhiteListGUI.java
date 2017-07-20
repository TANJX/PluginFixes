package fr.cookyy_.admin.listeners;

import fr.cookyy_.admin.AdminSystemMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WhiteListGUI implements Listener {
    public WhiteListGUI() {
    }

    public static void openWhitelistGUI(Player player) {
        Inventory inv3 = Bukkit.createInventory(null, 27, "§f§l白名单");

        ItemStack off = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta offmeta = off.getItemMeta();
        offmeta.setDisplayName("§c关闭");
        off.setItemMeta(offmeta);

        ItemStack on = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta onmeta = on.getItemMeta();
        onmeta.setDisplayName("§a启动");
        on.setItemMeta(onmeta);

        ItemStack gl = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta glmeta = gl.getItemMeta();
        glmeta.setDisplayName("§c---");
        gl.setItemMeta(glmeta);

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName("§f§l白名单玩家");
        skull.setItemMeta(meta);

        ItemStack bac = new ItemStack(Material.PAPER);
        ItemMeta bacmeta = bac.getItemMeta();
        bacmeta.setDisplayName("§c返回");
        bac.setItemMeta(bacmeta);

        inv3.setItem(22, bac);
        inv3.setItem(12, bac);
        inv3.setItem(14, bac);
        inv3.setItem(4, bac);
        inv3.setItem(15, off);
        inv3.setItem(11, on);
        inv3.setItem(13, skull);
        for (int i = 0; i < inv3.getContents().length; i++) {
            ItemStack is = inv3.getItem(i);
            if ((is == null) || (is.getType() == Material.AIR)) {
                inv3.setItem(i, gl);
            }
        }
        player.openInventory(inv3);
    }

    @EventHandler
    public void clickWhitelist(InventoryClickEvent event) {
        if (!event.getInventory().getName().equalsIgnoreCase("§f§l白名单")) {
            return;
        }
        Player p = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if ((event.getCurrentItem() == null) || (event.getCurrentItem().getType() == Material.AIR) || (!event.getCurrentItem().hasItemMeta())) {
            p.closeInventory();
            return;
        }
        switch (event.getCurrentItem().getType()) {
            case SKULL_ITEM:
                PlayerWhitelist.openWhitePlayersGUI(p);
                break;
            case REDSTONE_BLOCK:
                Bukkit.getServer().setWhitelist(false);
                p.sendMessage(AdminSystemMain.prefix + "§f白名单: §c启用");
                p.closeInventory();
                openWhitelistGUI(p);
                break;
            case EMERALD_BLOCK:
                Bukkit.getServer().setWhitelist(true);
                p.sendMessage(AdminSystemMain.prefix + "§f白名单: §a关闭");
                p.closeInventory();
                openWhitelistGUI(p);
                break;
            case PAPER:
                ServerGUI.openServerGUI(p);
        }
    }
}
