package info.TrenTech.EasyKits;

import info.TrenTech.EasyKits.Commands.CommandHandler;
import info.TrenTech.EasyKits.Events.MainListener;
import info.TrenTech.EasyKits.Events.SignListener;
import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.SQL.PluginFile;
import info.TrenTech.EasyKits.SQL.SQLKits;
import info.TrenTech.EasyKits.SQL.SQLPlayers;
import info.TrenTech.EasyKits.SQL.SQLUtils;
import info.TrenTech.EasyKits.Utils.Notifications;
import info.TrenTech.EasyKits.Utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class EasyKits extends JavaPlugin {

    public final Logger logger = Logger.getLogger("Minecraft");
    public Economy economy;
    public static HashMap<UUID, String> players = new HashMap<UUID, String>();
    public boolean econSupport = true;
    private CommandHandler cmdExecutor;
    public static HashMap<String, String> messages = new HashMap<String, String>();

    public static PluginFile kitFile;

    @Override
    public void onEnable() {
        setPlugin();
        registerEvents(this, new MainListener(), new SignListener());

        getConfig().options().copyDefaults(true);
        saveConfig();

        new Notifications().getMessages();

        this.cmdExecutor = new CommandHandler();
        getCommand("kit").setExecutor(cmdExecutor);

        if (!setupEconomy()) {
            logger.warning(String.format("[%s] Vault not found! Economy support disabled!", new Object[]{getDescription().getName()}));
            econSupport = false;
        } else {
            logger.info(String.format("[%s] Vault found! Economy support enabled!", new Object[]{getDescription().getName()}));
        }

        try {
            SQLUtils.connect();
        } catch (Exception e) {
            logger.severe(String.format("[%s] Disabled! Unable to connect to database!", new Object[]{getDescription().getName()}));
            return;
        }

        kitFile = new PluginFile(this, "kits.yml");

//        if (!SQLKits.tableExist()) {
//            SQLKits.createTable();
//            logger.info("Creating Database tables!");
//        }

        Collection<? extends Player> onlinePlayers = getServer().getOnlinePlayers();
        for (Player player : onlinePlayers) {
            players.put(player.getUniqueId(), player.getName());
        }
        fixKitValues();
        fixPlayerValues();
        fixConfigValues();
        getLogger().info("由 ISOTOPE Studio 修改");
    }

    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }

    public void setPlugin() {
        Utils.setPlugin(new EasyKitsMod(this, this.logger, getConfig()));
    }

    // TEMP METHODS TO FIX DB INFO

    public void fixConfigValues() {
        String cooldown = getConfig().getString("Config.Default-Cooldown");
        if (cooldown.equalsIgnoreCase("-1")) {
            logger.warning(String.format("[%s] Patching Config Value", new Object[]{getDescription().getName()}));
            getConfig().set("Config.Default-Cooldown", "0");
        }
        double price = getConfig().getDouble("Config.Default-Price");
        if (price == -1) {
            logger.warning(String.format("[%s] Patching Config Value", new Object[]{getDescription().getName()}));
            getConfig().set("Config.Default-Price", 0);
        }
        int limit = getConfig().getInt("Config.Default-Kit-Limit");
        if (limit == -1) {
            logger.warning(String.format("[%s] Patching Config Value", new Object[]{getDescription().getName()}));
            getConfig().set("Config.Default-Kit-Limit", 0);
        }
    }

    public void fixKitValues() {
        List<Kit> list = SQLKits.getKitList();
        for (Kit kit : list) {
            if (SQLKits.getKitLimit(kit.getName()) == -1) {
                logger.warning(String.format("[%s] Patching Kit Table", new Object[]{getDescription().getName()}));
                SQLKits.setKitLimit(kit.getName(), 0);
            }
            if (SQLKits.getKitPrice(kit.getName()) == -1) {
                logger.warning(String.format("[%s] Patching Kit Table", new Object[]{getDescription().getName()}));
                SQLKits.setKitPrice(kit.getName(), 0);
            }
            if (SQLKits.getKitCooldown(kit.getName()).equalsIgnoreCase("-1")) {
                logger.warning(String.format("[%s] Patching Kit Table", new Object[]{getDescription().getName()}));
                SQLKits.setKitCooldown(kit.getName(), "0");
            }
        }
    }

    public void fixPlayerValues() {
        List<String> playerList = SQLPlayers.getPlayerList();
        for (String playerUUID : playerList) {
            List<Kit> kitList = SQLKits.getKitList();
            for (Kit kit : kitList) {
                if (SQLPlayers.getLimit(playerUUID, kit.getName()) == -1) {
                    logger.warning(String.format("[%s] Patching Player Table", new Object[]{getDescription().getName()}));
                    SQLPlayers.setLimit(playerUUID, kit.getName(), 0);
                }
            }
        }
    }

}
