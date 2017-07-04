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

public class Admin implements Listener, CommandExecutor
{
  public Admin() {}
  
  public static void openAdminGUI(Player player)
  {
    Inventory admin = Bukkit.createInventory(null, 27, AdminSystemMain.prefix);
    
    ItemStack server = new ItemStack(Material.REDSTONE);
    ItemMeta serverm = server.getItemMeta();
    serverm.setDisplayName("§cServer");
    server.setItemMeta(serverm);
    
    ItemStack world = new ItemStack(Material.WATCH);
    ItemMeta worldm = world.getItemMeta();
    worldm.setDisplayName("§aWorld");
    world.setItemMeta(worldm);
    
    ItemStack playerp = new ItemStack(Material.ARMOR_STAND);
    ItemMeta playerpm = playerp.getItemMeta();
    playerpm.setDisplayName("§6Player");
    playerp.setItemMeta(playerpm);
    
    ItemStack others = new ItemStack(Material.STICK);
    ItemMeta othersm = others.getItemMeta();
    othersm.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
    othersm.setDisplayName("§2Others");
    others.setItemMeta(othersm);
    
    ItemStack exit = new ItemStack(Material.BARRIER);
    ItemMeta exitmeta = exit.getItemMeta();
    exitmeta.setDisplayName("§c§l❌ Close ❌");
    exit.setItemMeta(exitmeta);
    
    ItemStack nop = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0);
    ItemMeta nopmeta = nop.getItemMeta();
    nopmeta.setDisplayName("§cThis is useless");
    nop.setItemMeta(nopmeta);
    
    admin.setItem(10, server);
    admin.setItem(12, world);
    admin.setItem(14, playerp);
    admin.setItem(16, others);
    admin.setItem(22, exit);
    
    for (int i = 0; i < admin.getContents().length; i++)
    {
      ItemStack is = admin.getItem(i);
      if ((is == null) || (is.getType() == Material.AIR)) {
        admin.setItem(i, nop);
      }
    }
    player.openInventory(admin);
  }
  
  @EventHandler
  public void click(InventoryClickEvent event)
  {
    if (!event.getInventory().getName().equalsIgnoreCase(AdminSystemMain.prefix)) {
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
    case QUARTZ_STAIRS: 
      ServerGUI.openServerGUI(p);
      break;
    case RECORD_7: 
      WorldGUI.openWorldGUI(p);
      break;
    case STONE_SLAB2: 
      PlayerGUI.openPlayerGUI(p);
      break;
    case MONSTER_EGGS: 
      OtherGUI.openOtherGUI(p);
      break;
    case COOKED_MUTTON: 
      p.closeInventory();
    }
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg3)
  {
    if (cmd.getName().equalsIgnoreCase("admin"))
    {
      if (!(sender instanceof Player))
      {
        sender.sendMessage(ChatColor.RED + "You must be a player to perform this command");
        return false;
      }
      if (!sender.hasPermission("adminfr.admin"))
      {
        sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command");
        return false;
      }
      if (sender.isOp()) {
        openAdminGUI((Player)sender);
      }
    }
    return false;
  }
}
