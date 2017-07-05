package fr.cookyy_.admin.listeners;

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

import java.util.ArrayList;


public class OtherGUI
        implements Listener {

    public static void openOtherGUI(Player player) {
        Inventory others = Bukkit.createInventory(null, 9, "§2其他");

        ItemStack ban = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        ItemMeta banm = ban.getItemMeta();
        banm.setDisplayName("§4被封禁的玩家");
        ban.setItemMeta(banm);

        ItemStack cleari = new ItemStack(Material.PAPER);
        ItemMeta clearim = cleari.getItemMeta();
        ArrayList<String> lore = new ArrayList();
        lore.add("§c清空玩家背包:");
        lore.add("§a/adminclear <玩家名>");
        clearim.setLore(lore);
        clearim.setDisplayName("§7清空背包");
        cleari.setItemMeta(clearim);


        ItemStack clearc = new ItemStack(Material.ANVIL);
        ItemMeta clearcm = clearc.getItemMeta();
        clearcm.setDisplayName("§c清空聊天记录");
        clearc.setItemMeta(clearcm);

        ItemStack nop = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta nopmeta = nop.getItemMeta();
        nopmeta.setDisplayName("§c---");
        nop.setItemMeta(nopmeta);

        others.setItem(1, ban);
        others.setItem(4, cleari);
        others.setItem(7, clearc);
        for (int i = 0; i < others.getContents().length; i++) {
            ItemStack is = others.getItem(i);
            if ((is == null) || (is.getType() == Material.AIR)) {
                others.setItem(i, nop);
            }
        }
        player.openInventory(others);
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (!event.getInventory().getName().equalsIgnoreCase("§2其他")) {
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
                BanGUI.openBanGUI(p);
                break;
            case ANVIL:
                for (int i = 0; i < 100; i++) {
                    Bukkit.broadcastMessage("");
                }
                Bukkit.broadcastMessage("§e聊天记录被 §c" + p.getName()+" §e清除");
                break;
            case PAPER:
                p.performCommand("adminclear");
                break;
            case FROSTED_ICE:
                p.closeInventory();
        }
    }
}
