package fr.cookyy_.admin;

import fr.cookyy_.admin.commands.Admin;
import fr.cookyy_.admin.commands.AdminClearInventory;
import fr.cookyy_.admin.commands.AdminHelp;
import fr.cookyy_.admin.events.AdminPlayerJoin;
import fr.cookyy_.admin.listeners.BanGUI;
import fr.cookyy_.admin.listeners.DifficultyGUI;
import fr.cookyy_.admin.listeners.GameModeGUI;
import fr.cookyy_.admin.listeners.HealFeedGUI;
import fr.cookyy_.admin.listeners.OtherGUI;
import fr.cookyy_.admin.listeners.PlayerGUI;
import fr.cookyy_.admin.listeners.PlayerWhitelist;
import fr.cookyy_.admin.listeners.ServerGUI;
import fr.cookyy_.admin.listeners.WhiteListGUI;
import fr.cookyy_.admin.listeners.WorldGUI;
import java.util.logging.Logger;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class AdminSystemMain
  extends JavaPlugin
{
  public static String prefix = ChatColor.translateAlternateColorCodes('&', "&b&lAdminFR › ");
  private static AdminSystemMain plugin;
  
  public AdminSystemMain() {}
  
  public void onEnable() { getLogger().info(ChatColor.translateAlternateColorCodes('&', prefix + "&aactiver !"));
    plugin = this;
    registerCommands();
    registerEvents();
  }
  
  public static AdminSystemMain getInstance()
  {
    return plugin;
  }
  
  public void registerCommands()
  {
    getCommand("admin").setExecutor(new Admin());
    getCommand("adminhelp").setExecutor(new AdminHelp());
    getCommand("adminclear").setExecutor(new AdminClearInventory());
  }
  
  public void registerEvents()
  {
    PluginManager pm = getServer().getPluginManager();
    
    pm.registerEvents(new Admin(), this);
    pm.registerEvents(new AdminHelp(), this);
    pm.registerEvents(new AdminPlayerJoin(), this);
    pm.registerEvents(new BanGUI(), this);
    pm.registerEvents(new DifficultyGUI(), this);
    pm.registerEvents(new GameModeGUI(), this);
    pm.registerEvents(new HealFeedGUI(), this);
    pm.registerEvents(new OtherGUI(), this);
    pm.registerEvents(new PlayerGUI(), this);
    pm.registerEvents(new PlayerWhitelist(), this);
    pm.registerEvents(new ServerGUI(), this);
    pm.registerEvents(new WhiteListGUI(), this);
    pm.registerEvents(new WorldGUI(), this);
  }
  
  public void onDisable()
  {
    getLogger().info(ChatColor.translateAlternateColorCodes('&', prefix + "&cdesactiver !"));
  }
}
