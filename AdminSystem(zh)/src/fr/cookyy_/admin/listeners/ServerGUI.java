package fr.cookyy_.admin.listeners;

import fr.cookyy_.admin.AdminSystemMain;
import fr.cookyy_.admin.commands.Admin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ServerGUI implements Listener {

    public static void openServerGUI(Player player) {
        Inventory server = Bukkit.createInventory(null, 27, "§c服务器");

        ItemStack stop = new ItemStack(Material.PAPER);
        ItemMeta stopmeta = stop.getItemMeta();
        stopmeta.setDisplayName("§4§l关闭服务器");
        stop.setItemMeta(stopmeta);

        ItemStack reload = new ItemStack(Material.ARROW);
        ItemMeta reloadmeta = reload.getItemMeta();
        reloadmeta.setDisplayName("§3重载");
        reload.setItemMeta(reloadmeta);

        ItemStack whitelist = new ItemStack(Material.WATER_BUCKET);
        ItemMeta whitelistmeta = whitelist.getItemMeta();
        whitelistmeta.setDisplayName("§f§l白名单");
        whitelist.setItemMeta(whitelistmeta);

        ItemStack difficulty = new ItemStack(Material.EYE_OF_ENDER);
        ItemMeta difficultymeta = difficulty.getItemMeta();
        difficultymeta.setDisplayName("§a难度");
        difficulty.setItemMeta(difficultymeta);

        ItemStack main = new ItemStack(Material.NETHER_STAR);
        ItemMeta mainmeta = main.getItemMeta();
        mainmeta.setDisplayName("§c返回");
        main.setItemMeta(mainmeta);

        ItemStack nop = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta nopmeta = nop.getItemMeta();
        nopmeta.setDisplayName("§c---");
        nop.setItemMeta(nopmeta);

        server.setItem(10, stop);
        server.setItem(12, reload);
        server.setItem(14, whitelist);
        server.setItem(16, difficulty);
        server.setItem(22, main);
        for (int i = 0; i < server.getContents().length; i++) {
            ItemStack is = server.getItem(i);
            if ((is == null) || (is.getType() == Material.AIR)) {
                server.setItem(i, nop);
            }
        }
        player.openInventory(server);
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (!event.getInventory().getName().equalsIgnoreCase("§c服务器")) {
            return;
        }
        Player p = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if ((event.getCurrentItem() == null) || (event.getCurrentItem().getType() == Material.AIR) || (!event.getCurrentItem().hasItemMeta())) {
            p.closeInventory();
            return;
        }
        switch (event.getCurrentItem().getType()) {
            case PAPER:
                Bukkit.getServer().shutdown();
                break;
            case ARROW:
                p.closeInventory();
                p.sendMessage(AdminSystemMain.prefix + "§c重载");
                Bukkit.getServer().reload();
                p.sendMessage(AdminSystemMain.prefix + "§a重载成功");
                openServerGUI(p);
                break;
            case WATER_BUCKET:
                WhiteListGUI.openWhitelistGUI(p);
                break;
            case EYE_OF_ENDER:
                DifficultyGUI.openDifficultyGUI(p);
                break;
            case NETHER_STAR:
                Admin.openAdminGUI(p);
        }
    }
}
