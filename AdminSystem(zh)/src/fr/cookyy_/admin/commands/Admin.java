package fr.cookyy_.admin.commands;

import fr.cookyy_.admin.AdminSystemMain;
import fr.cookyy_.admin.listeners.OtherGUI;
import fr.cookyy_.admin.listeners.PlayerGUI;
import fr.cookyy_.admin.listeners.ServerGUI;
import fr.cookyy_.admin.listeners.WorldGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Admin implements Listener, CommandExecutor {

    public static void openAdminGUI(Player player) {
        Inventory admin = Bukkit.createInventory(null, 27, AdminSystemMain.prefix);

        ItemStack server = new ItemStack(Material.REDSTONE);
        ItemMeta serverm = server.getItemMeta();
        serverm.setDisplayName("§c服务器");
        server.setItemMeta(serverm);

        ItemStack world = new ItemStack(Material.WATCH);
        ItemMeta worldm = world.getItemMeta();
        worldm.setDisplayName("§a世界");
        world.setItemMeta(worldm);

        ItemStack playerp = new ItemStack(Material.ARMOR_STAND);
        ItemMeta playerpm = playerp.getItemMeta();
        playerpm.setDisplayName("§6玩家");
        playerp.setItemMeta(playerpm);

        ItemStack others = new ItemStack(Material.STICK);
        ItemMeta othersm = others.getItemMeta();
        othersm.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
        othersm.setDisplayName("§2其他");
        others.setItemMeta(othersm);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitmeta = exit.getItemMeta();
        exitmeta.setDisplayName("§c§l❌ 关闭 ❌");
        exit.setItemMeta(exitmeta);

        ItemStack nop = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
        ItemMeta nopmeta = nop.getItemMeta();
        nopmeta.setDisplayName("§c---");
        nop.setItemMeta(nopmeta);

        admin.setItem(10, server);
        admin.setItem(12, world);
        admin.setItem(14, playerp);
        admin.setItem(16, others);
        admin.setItem(22, exit);

        for (int i = 0; i < admin.getContents().length; i++) {
            ItemStack is = admin.getItem(i);
            if ((is == null) || (is.getType() == Material.AIR)) {
                admin.setItem(i, nop);
            }
        }
        player.openInventory(admin);
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (!event.getInventory().getName().equalsIgnoreCase(AdminSystemMain.prefix)) {
            return;
        }
        Player p = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if ((event.getCurrentItem() == null) || (event.getCurrentItem().getType() == Material.AIR) || (!event.getCurrentItem().hasItemMeta())) {
            p.closeInventory();
            return;
        }
        switch (event.getCurrentItem().getType()) {
            case REDSTONE:
                ServerGUI.openServerGUI(p);
                break;
            case WATCH:
                WorldGUI.openWorldGUI(p);
                break;
            case ARMOR_STAND:
                PlayerGUI.openPlayerGUI(p);
                break;
            case STICK:
                OtherGUI.openOtherGUI(p);
                break;
            case BARRIER:
                p.closeInventory();
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg3) {
        if (cmd.getName().equalsIgnoreCase("admin")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "这是玩家执行的命令");
                return false;
            }
            if (!sender.hasPermission("adminfr.admin")) {
                sender.sendMessage(ChatColor.RED + "你没有权限");
                return false;
            }
            if (sender.isOp()) {
                openAdminGUI((Player) sender);
            }
        }
        return false;
    }
}
