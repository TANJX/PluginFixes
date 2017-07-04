package info.TrenTech.EasyKits.Kit;

import info.TrenTech.EasyKits.Events.KitPlayerCooldownEvent;
import info.TrenTech.EasyKits.Events.KitPlayerEquipEvent;
import info.TrenTech.EasyKits.Events.KitPlayerLimitEvent;
import info.TrenTech.EasyKits.Events.WithdrawMoneyEvent;
import info.TrenTech.EasyKits.SQL.SQLPlayers;
import info.TrenTech.EasyKits.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class KitUser {

    private Player user;
    private Kit kit;
    private String userUUID;
    private int limit;
    private String dateObtained;

    public KitUser(Player user, Kit kit) {
        this.user = user;
        this.kit = kit;
        userUUID = user.getUniqueId().toString();
        if (!SQLPlayers.kitExist(userUUID, kit.getName())) {
            SQLPlayers.addKit(userUUID, kit.getName(), "2000-1-1 12:00:00", kit.getLimit(), "FALSE");
        }
        limit = SQLPlayers.getLimit(userUUID, kit.getName());
        if (limit == -1) {
            limit = kit.getLimit();
        }
        dateObtained = SQLPlayers.getDateObtained(userUUID, kit.getName());
    }

    public int getCurrentLimit() {
        return limit;
    }

    public String getCooldownTimeRemaining() {
        if (!hasObtainedBefore()) {
            return null;
        }
        if (kit.getCooldown().equalsIgnoreCase("0")) {
            return null;
        }

        Date date = new Date();
        Date dateObtained = null;
        if (getDateObtained().equalsIgnoreCase("0")) {
            return null;
        }

        try {
            dateObtained = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getDateObtained());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeSince = TimeUnit.MILLISECONDS.toSeconds(date.getTime() - dateObtained.getTime());
        int waitTime = Utils.getTimeInSeconds(kit.getCooldown());
        if (waitTime - timeSince <= 0) {
            return null;
        }

        return Utils.getReadableTime((int) (waitTime - timeSince));
    }

    public String getDateObtained() {
        return dateObtained;
    }

    public boolean setDateObtained(String dateObtained) {
        KitPlayerCooldownEvent event = new KitPlayerCooldownEvent(user, kit);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            SQLPlayers.setDateObtained(userUUID, kit.getName(), dateObtained);
            this.dateObtained = dateObtained;
            return true;
        }
        return false;
    }

    public boolean setCurrentLimit(int limit) {
        KitPlayerLimitEvent event = new KitPlayerLimitEvent(user, kit);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            if (kit.getLimit() > 0) {
                SQLPlayers.setLimit(userUUID, kit.getName(), limit);
                this.limit = limit;
            }
            return true;
        }
        return false;
    }

    public boolean chargeUser() {
        WithdrawMoneyEvent event = new WithdrawMoneyEvent(user, kit, this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            if (kit.getPrice() != 0) {
                if (Utils.getPluginContainer().economy.getBalance(user) < kit.getPrice()) {
                    return false;
                }
                Utils.getPluginContainer().economy.withdrawPlayer(user, kit.getPrice());
                return true;
            }
        }
        return false;
    }

    public boolean hasObtainedBefore() {
        if (SQLPlayers.getObtained(userUUID, kit.getName()).equalsIgnoreCase("TRUE")) {
            return true;
        }
        return false;
    }

    public boolean canAfford() {
        double price = kit.getPrice();
        double balance = Utils.getPluginContainer().economy.getBalance(user);
        return !(balance < price);
    }

    public boolean applyKit() throws Exception {
        KitPlayerEquipEvent event = new KitPlayerEquipEvent(user, kit, this);

        ItemStack[] inv = kit.getInventory();
        ItemStack[] arm = kit.getArmor();
        List<ItemStack> items = new ArrayList<>();
        for (ItemStack item : arm) {
            if (item != null) {
                items.add(item);
            }
        }
        for (ItemStack item : inv) {
            if (item != null) {
                items.add(item);
            }
        }
        ItemStack[] itemList = new ItemStack[items.size()];
        items.toArray(itemList);
        int empty = 36;
        for (ItemStack itemStack : user.getInventory().getContents()) {
            if (itemStack != null) {
                empty--;
            }
        }
        if (itemList.length > empty) {
            user.sendMessage(ChatColor.RED + "由于你的背包不够大领取不了礼包");
            return false;
        }


        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            user.getInventory().addItem(itemList);
            SQLPlayers.setObtained(userUUID, kit.getName(), "TRUE");
            return true;
        }

        return false;
    }
}
