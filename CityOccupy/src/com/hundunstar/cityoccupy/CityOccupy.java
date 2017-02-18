package com.hundunstar.cityoccupy;

import com.hundunstar.cityoccupy.city.COCityManager;
import com.hundunstar.cityoccupy.listener.COCityEventListener;
import com.hundunstar.cityoccupy.listener.COResetSelectionEventListener;
import com.hundunstar.cityoccupy.listener.COSelectEventListener;
import com.hundunstar.cityoccupy.selection.COSelectionManager;
import com.hundunstar.cityoccupy.timer.OccupiedSalary;
import com.hundunstar.cityoccupy.timer.Occupying;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

//import java.util.HashSet;
//import java.util.Set;


public class CityOccupy extends JavaPlugin {
    public static Logger logger;
    public static Server server;

    private static Listener cityeventlistener;
    private static Listener resetselectioneventlistener;
    private static Listener selecteventlistener;

    public static COCityManager cmanager;
    public static COSelectionManager smanager;

    public static Economy economy;
    public static Permission permission;
    private static boolean isEco = false;
    private static boolean isPer = false;

    public static FileConfiguration config;
    public static FileConfiguration citys;

    private boolean setEconomy()//����Economy�����ã�
    {
        RegisteredServiceProvider<Economy> eco =
                getServer().getServicesManager().getRegistration(Economy.class);
        if (eco != null) {
            economy = eco.getProvider();
        }
        return (economy != null);
    }

    private boolean setPermission()//����Permission��vault�����permission����Ȩ�����йص��Ǹ���
    {
        RegisteredServiceProvider<Permission> per =
                getServer().getServicesManager().getRegistration(Permission.class);
        if (per != null) {
            permission = per.getProvider();
        }
        return (permission != null);
    }

    @Override
    public void onEnable() {
        this.getLogger().info("Plugin Enabling...");


        this.getLogger().info("Checking PrePlugins...");

        //���ǰ��Vault���
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            getLogger().info("Find Vault Failed!");
            return;
        }

        getLogger().info("Find Vault!");


        this.getLogger().info("Reading Configuation...");


        CityOccupy.smanager = new COSelectionManager();//ʵ����ѡ�������
        CityOccupy.cmanager = new COCityManager();//ʵ�������й�����
        CityOccupy.logger = this.getLogger();
        CityOccupy.server = this.getServer();

        CityOccupy.cityeventlistener = new COCityEventListener();
        CityOccupy.resetselectioneventlistener = new COResetSelectionEventListener();
        CityOccupy.selecteventlistener = new COSelectEventListener();

        getServer().getPluginManager().registerEvents(cityeventlistener, this);//ע���¼�������
        getServer().getPluginManager().registerEvents(resetselectioneventlistener, this);
        getServer().getPluginManager().registerEvents(selecteventlistener, this);
        isEco = this.setEconomy();//ʵ����Economy
        isPer = this.setPermission();//ʵ����Permission


        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        //��ȡconfig.yml
        File file = new File(getDataFolder(), "config.yml");
        if (!(file.exists())) {
            this.saveDefaultConfig();
        }
        this.reloadConfig();


        CityOccupy.config = this.getConfig();
        CityOccupy.citys = this.LoadConfiguration(new File(this.getDataFolder(), "citys.yml"), "citys.yml");


        this.getLogger().info("Configuation Read Successfully!");

        new Occupying().runTaskTimer(this, 0, 20);
        new OccupiedSalary().runTaskTimer(this, 0, 20 * 60 * 5);

        this.getLogger().info("Plugin Enabled Successfully!");
    }


    private YamlConfiguration LoadConfiguration(File file, String name)//���ض�������
    {
        if (!(file.exists())) {
            try {
                this.saveResource(name, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugin Disabling...");


        this.getLogger().info("Save Config Files...");

        try {
            this.getConfig().save(new File(getDataFolder(), "config.yml"));//��������
            citys.save(new File(getDataFolder(), "citys.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.getLogger().info("Plugin Disabled Successfully!");


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return COCommandExecuter.execute(sender, command, label, args);
    }
}
