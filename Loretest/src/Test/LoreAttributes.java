//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Test;

import Test.LoreEvents;
import Test.LoreManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class LoreAttributes extends JavaPlugin {
    public static LoreManager loreManager;
    public static FileConfiguration config = null;

    public LoreAttributes() {
    }

    public void onEnable() {
        config = this.getConfig();
        config.options().copyDefaults(true);
        this.saveConfig();
        if(loreManager == null) {
            loreManager = new LoreManager(this);
        }

        Bukkit.getServer().getPluginManager().registerEvents(new LoreEvents(), this);
    }

    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getLabel().equalsIgnoreCase("hp")) {
            if(!(sender instanceof Player)) {
                return false;
            } else {
                Player p = (Player)sender;
                p.sendMessage("Health: " + p.getHealth() + "/" + p.getMaxHealth());
                return true;
            }
        } else if(cmd.getLabel().equalsIgnoreCase("lorestats")) {
            if(!(sender instanceof Player)) {
                return false;
            } else {
                loreManager.displayLoreStats((Player)sender);
                return true;
            }
        } else {
            return false;
        }
    }
}
