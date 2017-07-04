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

public class GameModeGUI implements Listener
{
  public GameModeGUI() {}
  
  public static void openGameModeGUI(Player player)
  {
    Inventory fly = Bukkit.createInventory(null, 9, "§aGameMode");
    
    ItemStack crea = new ItemStack(Material.DIAMOND);
    ItemMeta creameta = crea.getItemMeta();
    creameta.setDisplayName("§bCreatif");
    crea.setItemMeta(creameta);
    
    ItemStack adventure = new ItemStack(Material.GRASS);
    ItemMeta adventuremeta = adventure.getItemMeta();
    adventuremeta.setDisplayName("§bAdventure");
    adventure.setItemMeta(adventuremeta);
    
    ItemStack survi = new ItemStack(Material.WOOD);
    ItemMeta survimeta = survi.getItemMeta();
    survimeta.setDisplayName("§bSurvival");
    survi.setItemMeta(survimeta);
    
    ItemStack nop = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)3);
    ItemMeta nopmeta = nop.getItemMeta();
    nopmeta.setDisplayName("§cThis is useless");
    nop.setItemMeta(nopmeta);
    
    fly.setItem(1, crea);
    fly.setItem(4, adventure);
    fly.setItem(7, survi);
    for (int i = 0; i < fly.getContents().length; i++)
    {
      ItemStack is = fly.getItem(i);
      if ((is == null) || (is.getType() == Material.AIR)) {
        fly.setItem(i, nop);
      }
    }
    player.openInventory(fly);
  }
  
  @EventHandler
  public void click(InventoryClickEvent event)
  {
    if (!event.getInventory().getName().equalsIgnoreCase("§aGameMode")) {
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
    case LINGERING_POTION: 
      p.setGameMode(GameMode.CREATIVE);
      p.sendMessage(AdminSystemMain.prefix + "§fGamemode: §aCreatif");
      p.closeInventory();
      openGameModeGUI(p);
      break;
    case ACACIA_FENCE: 
      p.setGameMode(GameMode.ADVENTURE);
      p.sendMessage(AdminSystemMain.prefix + "§fGamemode: §aAdventure");
      p.closeInventory();
      openGameModeGUI(p);
      break;
    case ACTIVATOR_RAIL: 
      p.setGameMode(GameMode.SURVIVAL);
      p.sendMessage(AdminSystemMain.prefix + "§fGamemode: §aSurvival");
      p.closeInventory();
      openGameModeGUI(p);
      break;
    case FROSTED_ICE: 
      p.closeInventory();
    }
  }
}
