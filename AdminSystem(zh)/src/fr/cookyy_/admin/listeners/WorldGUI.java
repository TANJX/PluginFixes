package fr.cookyy_.admin.listeners;

import fr.cookyy_.admin.AdminSystemMain;
import fr.cookyy_.admin.commands.Admin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WorldGUI implements Listener
{
  public WorldGUI() {}
  
  public static void openWorldGUI(Player player)
  {
    Inventory world = Bukkit.createInventory(null, 27, "§aWorld");
    
    ItemStack kick = new ItemStack(Material.BLAZE_ROD);
    ItemMeta kickmeta = kick.getItemMeta();
    kickmeta.setDisplayName("§cKick all connected");
    kick.setItemMeta(kickmeta);
    
    ItemStack kill = new ItemStack(Material.BONE);
    ItemMeta killmeta = kill.getItemMeta();
    killmeta.setDisplayName("§7Kill everyone");
    kill.setItemMeta(killmeta);
    
    ItemStack day = new ItemStack(Material.BUCKET);
    ItemMeta daymeta = day.getItemMeta();
    daymeta.setDisplayName("§aDay");
    day.setItemMeta(daymeta);
    
    ItemStack night = new ItemStack(Material.LAVA_BUCKET);
    ItemMeta nightmeta = night.getItemMeta();
    nightmeta.setDisplayName("§7Night");
    night.setItemMeta(nightmeta);
    
    ItemStack nop = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
    ItemMeta nopmeta = nop.getItemMeta();
    nopmeta.setDisplayName("§cThis is useless");
    nop.setItemMeta(nopmeta);
    
    ItemStack main = new ItemStack(Material.NETHER_STAR);
    ItemMeta mainmeta = main.getItemMeta();
    mainmeta.setDisplayName("§cReturn");
    main.setItemMeta(mainmeta);
    
    world.setItem(10, kick);
    world.setItem(14, kill);
    world.setItem(12, day);
    world.setItem(16, night);
    world.setItem(22, main);
    for (int i = 0; i < world.getContents().length; i++)
    {
      ItemStack is = world.getItem(i);
      if ((is == null) || (is.getType() == Material.AIR)) {
        world.setItem(i, nop);
      }
    }
    player.openInventory(world);
  }
  
  @EventHandler
  public void click(InventoryClickEvent event)
  {
    if (!event.getInventory().getName().equalsIgnoreCase("§aWorld")) {
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
    case SAND: 
      for (Player playerp : Bukkit.getServer().getOnlinePlayers()) {
        playerp.kickPlayer("§cYou were kicked by admin");
      }
      break;
    case REDSTONE_COMPARATOR: 
      for (Player health : Bukkit.getServer().getOnlinePlayers()) {
        health.setHealth(0.0D);
      }
      break;
    case PURPUR_PILLAR: 
      p.getWorld().setTime(1000L);
      p.sendMessage(AdminSystemMain.prefix + "§eIt's the day");
      openWorldGUI(p);
      break;
    case PURPUR_STAIRS: 
      p.getWorld().setTime(14000L);
      p.sendMessage(AdminSystemMain.prefix + "§eIt's night");
      openWorldGUI(p);
      break;
    case SPRUCE_FENCE: 
      Admin.openAdminGUI(p);
      break;
    
    case FROSTED_ICE: 
      p.closeInventory();
    }
  }
}
