package fr.cookyy_.admin.listeners;

import fr.cookyy_.admin.AdminSystemMain;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DifficultyGUI implements Listener {
    public DifficultyGUI() {
    }

    public static void openDifficultyGUI(Player player) {
        Inventory difficulty = Bukkit.createInventory(null, 27, "§a难度");

        ItemStack dirt = new ItemStack(Material.DIRT);
        ItemMeta dirtmeta = dirt.getItemMeta();
        dirtmeta.setDisplayName("§f和平");
        dirt.setItemMeta(dirtmeta);

        ItemStack grass = new ItemStack(Material.GRASS);
        ItemMeta grassmeta = grass.getItemMeta();
        grassmeta.setDisplayName("§7简单");
        grass.setItemMeta(grassmeta);

        ItemStack gl = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta glmeta = gl.getItemMeta();
        glmeta.setDisplayName("§c---");
        gl.setItemMeta(glmeta);

        ItemStack wood = new ItemStack(Material.WOOD);
        ItemMeta woodmeta = wood.getItemMeta();
        woodmeta.setDisplayName("§a普通");
        wood.setItemMeta(woodmeta);

        ItemStack soul = new ItemStack(Material.SOUL_SAND);
        ItemMeta soulmeta = soul.getItemMeta();
        soulmeta.setDisplayName("§4困难");
        soul.setItemMeta(soulmeta);

        ItemStack apple = new ItemStack(Material.NETHER_STAR);
        ItemMeta applemeta = apple.getItemMeta();
        applemeta.setDisplayName("§c返回");
        apple.setItemMeta(applemeta);

        difficulty.setItem(10, dirt);
        difficulty.setItem(12, grass);
        difficulty.setItem(14, wood);
        difficulty.setItem(16, soul);
        difficulty.setItem(22, apple);
        for (int i = 0; i < difficulty.getContents().length; i++) {
            ItemStack is = difficulty.getItem(i);
            if ((is == null) || (is.getType() == Material.AIR)) {
                difficulty.setItem(i, gl);
            }
        }
        player.openInventory(difficulty);
    }

    @EventHandler
    public void clickDiff(InventoryClickEvent event) {
        if (!event.getInventory().getName().equalsIgnoreCase("§a难度")) {
            return;
        }
        Player p = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if ((event.getCurrentItem() == null) || (event.getCurrentItem().getType() == Material.AIR) || (!event.getCurrentItem().hasItemMeta())) {
            p.closeInventory();
            return;
        }
        switch (event.getCurrentItem().getType()) {
            case DIRT:
                Bukkit.getServer().getWorld(p.getWorld().getName()).setDifficulty(Difficulty.PEACEFUL);
                p.sendMessage(AdminSystemMain.prefix + "§a难度: §b和平");
                p.closeInventory();
                openDifficultyGUI(p);
                break;
            case GRASS:
                Bukkit.getServer().getWorld(p.getWorld().getName()).setDifficulty(Difficulty.EASY);
                p.sendMessage(AdminSystemMain.prefix + "§a难度: §b简单");
                p.closeInventory();
                openDifficultyGUI(p);
                break;
            case WOOD:
                Bukkit.getServer().getWorld(p.getWorld().getName()).setDifficulty(Difficulty.NORMAL);
                p.sendMessage(AdminSystemMain.prefix + "§a难度: §b普通");
                p.closeInventory();
                openDifficultyGUI(p);
                break;
            case SOUL_SAND:
                Bukkit.getServer().getWorld(p.getWorld().getName()).setDifficulty(Difficulty.HARD);
                p.sendMessage(AdminSystemMain.prefix + "§a难度: §b困难");
                p.closeInventory();
                openDifficultyGUI(p);
                break;
            case NETHER_STAR:
                ServerGUI.openServerGUI(p);
                break;
        }
    }
}
