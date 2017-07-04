package fr.cookyy_.admin.listeners;

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

public class PlayerGUI implements Listener
{
  public PlayerGUI() {}
  
  public static void openPlayerGUI(Player player)
  {
    Inventory adminp = Bukkit.createInventory(null, 27, "§6Player");
    
    ItemStack healfeed = new ItemStack(Material.BREAD);
    ItemMeta healfeedmeta = healfeed.getItemMeta();
    healfeedmeta.setDisplayName("§aHeal §f/ §cFeed");
    healfeed.setItemMeta(healfeedmeta);
    
    ItemStack gm = new ItemStack(Material.GRASS);
    ItemMeta gmm = gm.getItemMeta();
    gmm.setDisplayName("§aGameMode");
    gm.setItemMeta(gmm);
    
    ItemStack main = new ItemStack(Material.NETHER_STAR);
    ItemMeta mainmeta = main.getItemMeta();
    mainmeta.setDisplayName("§cReturn");
    main.setItemMeta(mainmeta);
    
    ItemStack nop = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
    ItemMeta nopmeta = nop.getItemMeta();
    nopmeta.setDisplayName("§cThis is useless");
    nop.setItemMeta(nopmeta);
    
    adminp.setItem(10, healfeed);
    adminp.setItem(16, gm);
    adminp.setItem(22, main);
    for (int i = 0; i < adminp.getContents().length; i++)
    {
      ItemStack is = adminp.getItem(i);
      if ((is == null) || (is.getType() == Material.AIR)) {
        adminp.setItem(i, nop);
      }
    }
    player.openInventory(adminp);
  }
  
  @EventHandler
  public void click(InventoryClickEvent event)
  {
    if (!event.getInventory().getName().equalsIgnoreCase("§6Player")) {
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
    case OBSIDIAN: 
      HealFeedGUI.openHeelFeedGUI(p);
      break;
    case ACACIA_FENCE: 
      GameModeGUI.openGameModeGUI(p);
      break;
    case SPRUCE_FENCE: 
      Admin.openAdminGUI(p);
      break;
    case FROSTED_ICE: 
      p.closeInventory();
    }
  }
}
