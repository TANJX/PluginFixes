package fr.cookyy_.admin.listeners;

import java.util.ArrayList;
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


public class OtherGUI
  implements Listener
{
  public OtherGUI() {}
  
  public static void openOtherGUI(Player player)
  {
    Inventory others = Bukkit.createInventory(null, 9, "§2Others");
    
    ItemStack ban = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
    ItemMeta banm = ban.getItemMeta();
    banm.setDisplayName("§4Players Banned");
    ban.setItemMeta(banm);
    
    ItemStack cleari = new ItemStack(Material.PAPER);
    ItemMeta clearim = cleari.getItemMeta();
    ArrayList<String> lore = new ArrayList();
    lore.add("§6To clear a player's inventory");
    lore.add("§cYou can use:");
    lore.add("§a/adminclear <player>");
    clearim.setLore(lore);
    clearim.setDisplayName("§7Clear Inventory");
    cleari.setItemMeta(clearim);
    

    ItemStack clearc = new ItemStack(Material.ANVIL);
    ItemMeta clearcm = clearc.getItemMeta();
    clearcm.setDisplayName("§cClear chat");
    clearc.setItemMeta(clearcm);
    
    ItemStack nop = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
    ItemMeta nopmeta = nop.getItemMeta();
    nopmeta.setDisplayName("§cThis is useless");
    nop.setItemMeta(nopmeta);
    
    others.setItem(1, ban);
    others.setItem(4, cleari);
    others.setItem(7, clearc);
    for (int i = 0; i < others.getContents().length; i++)
    {
      ItemStack is = others.getItem(i);
      if ((is == null) || (is.getType() == Material.AIR)) {
        others.setItem(i, nop);
      }
    }
    player.openInventory(others);
  }
  
  @EventHandler
  public void click(InventoryClickEvent event)
  {
    if (!event.getInventory().getName().equalsIgnoreCase("§2Others")) {
      return;
    }
    Player p = (Player)event.getWhoClicked();
    event.setCancelled(true);
    if ((event.getCurrentItem() == null) || (event.getCurrentItem().getType() == Material.AIR) || (!event.getCurrentItem().hasItemMeta()))
    {
      p.closeInventory();
      return;
    }
    switch (event.getCurrentItem().getType())
    {
    case SPRUCE_DOOR: 
      BanGUI.openBanGUI(p);
      break;
    case EXP_BOTTLE: 
      for (int i = 0; i < 100; i++) {
        Bukkit.broadcastMessage("");
      }
      Bukkit.broadcastMessage("§eChat was deleted by §c" + p.getName());
      break;
    case RAW_FISH: 
      p.performCommand("adminclear");
      break;
    case FROSTED_ICE: 
      p.closeInventory();
    }
  }
}
