package info.TrenTech.EasyKits.Kit;

import info.TrenTech.EasyKits.Events.KitCreateEvent;
import info.TrenTech.EasyKits.SQL.SQLKits;
import info.TrenTech.EasyKits.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class Kit {

    private String kitName;
    private int limit;
    private String cooldown;
    private double price;
    private ItemStack[] inventory;
    private ItemStack[] armor;

    public Kit(String kitName) {

        this.kitName = kitName;
        if (!exists()) {
            FileConfiguration config = Utils.getConfig();
            cooldown = config.getString("Config.Default-Cooldown");
            price = config.getDouble("Config.Default-Price");
            limit = config.getInt("Config.Default-Kit-Limit");
        } else {
            cooldown = SQLKits.getKitCooldown(kitName);
            price = SQLKits.getKitPrice(kitName);
            limit = SQLKits.getKitLimit(kitName);
        }
    }

    public String getName() {
        return kitName;
    }

    public ItemStack[] getInventory() {
        byte[] byteInv = SQLKits.getKitInventory(kitName.toLowerCase());

        ByteArrayInputStream ByteArIS = new ByteArrayInputStream(byteInv);
        Object inv = null;
        try {
            BukkitObjectInputStream invObjIS = new BukkitObjectInputStream(ByteArIS);
            inv = invObjIS.readObject();
            invObjIS.close();
        } catch (IOException | ClassNotFoundException ioexception) {
            ioexception.printStackTrace();
        }
        this.inventory = (ItemStack[]) inv;
        return inventory;
    }

    public ItemStack[] getArmor() {
        byte[] byteArm = SQLKits.getKitArmor(kitName.toLowerCase());

        ByteArrayInputStream ByteArIS = new ByteArrayInputStream(byteArm);
        Object arm = null;
        try {
            BukkitObjectInputStream armObjIS = new BukkitObjectInputStream(ByteArIS);
            arm = armObjIS.readObject();
            armObjIS.close();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        this.armor = (ItemStack[]) arm;
        return armor;
    }

    public int getLimit() {
        return limit;
    }

    public double getPrice() {
        return price;
    }

    public String getCooldown() {
        return cooldown;
    }

    public void create(Player creator, ItemStack[] inventory, ItemStack[] armor) throws SQLException {
        KitCreateEvent event = new KitCreateEvent(creator, kitName);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            ByteArrayOutputStream invByteArray = new ByteArrayOutputStream();
            try {
                BukkitObjectOutputStream invObjOS = new BukkitObjectOutputStream(invByteArray);
                invObjOS.writeObject(inventory);
                invObjOS.close();
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }

            ByteArrayOutputStream armByteArray = new ByteArrayOutputStream();
            try {
                BukkitObjectOutputStream armObjOS = new BukkitObjectOutputStream(armByteArray);
                armObjOS.writeObject(armor);
                armObjOS.close();
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }

            SQLKits.createKit(kitName.toLowerCase(), invByteArray.toByteArray(), armByteArray.toByteArray(), price, cooldown, limit, creator.getUniqueId().toString());
        }
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;

        ByteArrayOutputStream invByteArray = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream invObjOS = new BukkitObjectOutputStream(invByteArray);
            invObjOS.writeObject(inventory);
            invObjOS.close();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

        SQLKits.setInventory(kitName, invByteArray.toByteArray());
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;

        ByteArrayOutputStream invByteArray = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream invObjOS = new BukkitObjectOutputStream(invByteArray);
            invObjOS.writeObject(armor);
            invObjOS.close();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

        SQLKits.setArmor(kitName, invByteArray.toByteArray());
    }

    public void setLimit(int limit) {
        SQLKits.setKitLimit(kitName, limit);
        this.limit = limit;
    }

    public void setPrice(double price) {
        SQLKits.setKitPrice(kitName, price);
        this.price = price;
    }

    public void setCooldown(String cooldown) {
        SQLKits.setKitCooldown(kitName, cooldown);
        this.cooldown = cooldown;
    }

    public void remove() throws SQLException {
        SQLKits.deleteKit(kitName);
        try {
            this.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public boolean exists() {
        return SQLKits.kitExist(kitName);
    }

}
