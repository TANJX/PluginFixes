package fr.cookyy_.admin.listeners;

import fr.cookyy_.admin.AdminSystemMain;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameModeGUI implements Listener {
    public GameModeGUI() {
    }

    public static void openGameModeGUI(Player player) {
        Inventory fly = Bukkit.createInventory(null, 9, "§a游戏模式");

        ItemStack crea = new ItemStack(Material.DIAMOND);
        ItemMeta creameta = crea.getItemMeta();
        creameta.setDisplayName("§b创造");
        crea.setItemMeta(creameta);

        ItemStack adventure = new ItemStack(Material.GRASS);
        ItemMeta adventuremeta = adventure.getItemMeta();
        adventuremeta.setDisplayName("§b冒险");
        adventure.setItemMeta(adventuremeta);

        ItemStack survi = new ItemStack(Material.WOOD);
        ItemMeta survimeta = survi.getItemMeta();
        survimeta.setDisplayName("§b生存");
        survi.setItemMeta(survimeta);

        ItemStack nop = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
        ItemMeta nopmeta = nop.getItemMeta();
        nopmeta.setDisplayName("§c---");
        nop.setItemMeta(nopmeta);

        fly.setItem(1, crea);
        fly.setItem(4, adventure);
        fly.setItem(7, survi);
        for (int i = 0; i < fly.getContents().length; i++) {
            ItemStack is = fly.getItem(i);
            if ((is == null) || (is.getType() == Material.AIR)) {
                fly.setItem(i, nop);
            }
        }
        player.openInventory(fly);
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (!event.getInventory().getName().equalsIgnoreCase("§a游戏模式")) {
            return;
        }
        Player p = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if ((event.getCurrentItem() == null) || (event.getCurrentItem().getType() == Material.AIR) || (!event.getCurrentItem().hasItemMeta())) {
            p.closeInventory();
            return;
        }
        switch (event.getCurrentItem().getType()) {
            case DIAMOND:
                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(AdminSystemMain.prefix + "§f游戏模式: §a创造");
                p.closeInventory();
                openGameModeGUI(p);
                break;
            case GRASS:
                p.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(AdminSystemMain.prefix + "§f游戏模式: §a冒险");
                p.closeInventory();
                openGameModeGUI(p);
                break;
            case WOOD:
                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(AdminSystemMain.prefix + "§f游戏模式: §a生存");
                p.closeInventory();
                openGameModeGUI(p);
                break;
        }
    }
}
