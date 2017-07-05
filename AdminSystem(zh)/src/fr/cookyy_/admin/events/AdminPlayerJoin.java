package fr.cookyy_.admin.events;

import fr.cookyy_.admin.AdminSystemMain;
import fr.cookyy_.admin.commands.Admin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class AdminPlayerJoin implements Listener {
    public AdminPlayerJoin() {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("adminfr.admin")) {
            Player p = event.getPlayer();
//            p.sendMessage(AdminSystemMain.prefix + "§aLet's GO ?");
            event.getPlayer().getInventory();
            ItemStack join = new ItemStack(Material.SUGAR, 1);
            ItemMeta joinm = join.getItemMeta();
            joinm.setDisplayName("§b管理员菜单");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7右键打开");
            joinm.setLore(lore);
            join.setItemMeta(joinm);
            event.getPlayer().getInventory().setItem(4, new ItemStack(join));
        }
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (!event.getPlayer().hasPermission("adminfr.admin")) {
            return;
        }
        if (!event.getPlayer().isOp()) {
            return;
        }
        Player player = event.getPlayer();
        if ((player.getInventory().getItemInHand().getType() == Material.SUGAR) && (
                (event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_AIR))) {
            Admin.openAdminGUI(player);
        }
    }
}
