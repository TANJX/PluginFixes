//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Test;

import Test.LoreAttributes;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LoreManager {
    private LoreAttributes plugin;
    private Pattern healthRegex;
    private Pattern negHealthRegex;
    private Pattern regenRegex;
    private Pattern attackSpeedRegex;
    private Pattern damageValueRegex;
    private Pattern negitiveDamageValueRegex;
    private Pattern damageRangeRegex;
    private Pattern dodgeRegex;
    private Pattern critChanceRegex;
    private Pattern critDamageRegex;
    private Pattern lifestealRegex;
    private Pattern armorRegex;
    private Pattern restrictionRegex;
    private Pattern levelRegex;
    private HashMap<String, Timestamp> attackLog;
    private boolean attackSpeedEnabled;
    private Random generator;

    public LoreManager(LoreAttributes plugin) {
        this.plugin = plugin;
        this.generator = new Random();
        this.attackSpeedEnabled = false;
        if(LoreAttributes.config.getBoolean("lore.attack-speed.enabled")) {
            this.attackSpeedEnabled = true;
            this.attackLog = new HashMap();
        }

        this.healthRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.health.keyword").toLowerCase() + ")");
        this.negHealthRegex = Pattern.compile("[-](\\d+)[ ](" + LoreAttributes.config.getString("lore.health.keyword").toLowerCase() + ")");
        this.regenRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.regen.keyword").toLowerCase() + ")");
        this.attackSpeedRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.attack-speed.keyword").toLowerCase() + ")");
        this.damageValueRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.damage.keyword").toLowerCase() + ")");
        this.negitiveDamageValueRegex = Pattern.compile("[-](\\d+)[ ](" + LoreAttributes.config.getString("lore.damage.keyword").toLowerCase() + ")");
        this.damageRangeRegex = Pattern.compile("(\\d+)(-)(\\d+)[ ](" + LoreAttributes.config.getString("lore.damage.keyword").toLowerCase() + ")");
        this.dodgeRegex = Pattern.compile("[+](\\d+)[%][ ](" + LoreAttributes.config.getString("lore.dodge.keyword").toLowerCase() + ")");
        this.critChanceRegex = Pattern.compile("[+](\\d+)[%][ ](" + LoreAttributes.config.getString("lore.critical-chance.keyword").toLowerCase() + ")");
        this.critDamageRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.critical-damage.keyword").toLowerCase() + ")");
        this.lifestealRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.life-steal.keyword").toLowerCase() + ")");
        this.armorRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.armor.keyword").toLowerCase() + ")");
        this.restrictionRegex = Pattern.compile("(" + LoreAttributes.config.getString("lore.restriction.keyword").toLowerCase() + ": )(\\w*)");
        this.levelRegex = Pattern.compile("level (\\d+)()");
    }

    public void disable() {
        this.attackSpeedEnabled = false;
        if(this.attackLog != null) {
            this.attackLog.clear();
        }

    }

    public void handleArmorRestriction(Player player) {
        if(!this.canUse(player, player.getInventory().getBoots())) {
            if(player.getInventory().firstEmpty() >= 0) {
                player.getInventory().addItem(new ItemStack[]{player.getInventory().getBoots()});
            } else {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getBoots());
            }

            player.getInventory().setBoots(new ItemStack(0));
        }

        if(!this.canUse(player, player.getInventory().getChestplate())) {
            if(player.getInventory().firstEmpty() >= 0) {
                player.getInventory().addItem(new ItemStack[]{player.getInventory().getChestplate()});
            } else {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getChestplate());
            }

            player.getInventory().setChestplate(new ItemStack(0));
        }

        if(!this.canUse(player, player.getInventory().getHelmet())) {
            if(player.getInventory().firstEmpty() >= 0) {
                player.getInventory().addItem(new ItemStack[]{player.getInventory().getHelmet()});
            } else {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getHelmet());
            }

            player.getInventory().setHelmet(new ItemStack(0));
        }

        if(!this.canUse(player, player.getInventory().getLeggings())) {
            if(player.getInventory().firstEmpty() >= 0) {
                player.getInventory().addItem(new ItemStack[]{player.getInventory().getLeggings()});
            } else {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getLeggings());
            }

            player.getInventory().setLeggings(new ItemStack(0));
        }

        this.applyHpBonus(player);
    }

    public boolean canUse(Player player, ItemStack item) {
        if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List lore = item.getItemMeta().getLore();
            String allLore = lore.toString().toLowerCase();
            Matcher valueMatcher = this.levelRegex.matcher(allLore);
            if(valueMatcher.find() && player.getLevel() < Integer.valueOf(valueMatcher.group(1)).intValue()) {
                player.sendMessage("Item was not able to be equipped.");
                return false;
            }

            valueMatcher = this.restrictionRegex.matcher(allLore);
            if(valueMatcher.find()) {
                if(player.hasPermission("loreattributes." + valueMatcher.group(2))) {
                    return true;
                }

                if(LoreAttributes.config.getBoolean("lore.restriction.display-message")) {
                    player.sendMessage(LoreAttributes.config.getString("lore.restriction.message").replace("%itemname%", item.getType().toString()));
                }

                return false;
            }
        }

        return true;
    }

    public int getDodgeBonus(LivingEntity entity) {
        Integer dodgeBonus = Integer.valueOf(0);
        ItemStack[] valueMatcher;
        int allLore = (valueMatcher = entity.getEquipment().getArmorContents()).length;

        ItemStack item;
        for(int lore = 0; lore < allLore; ++lore) {
            item = valueMatcher[lore];
            if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List lore1 = item.getItemMeta().getLore();
                String allLore1 = lore1.toString().toLowerCase();
                Matcher valueMatcher1 = this.dodgeRegex.matcher(allLore1);
                if(valueMatcher1.find()) {
                    dodgeBonus = Integer.valueOf(dodgeBonus.intValue() + Integer.valueOf(valueMatcher1.group(1)).intValue());
                }
            }
        }

        item = entity.getEquipment().getItemInHand();
        if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List var10 = item.getItemMeta().getLore();
            String var11 = var10.toString().toLowerCase();
            Matcher var12 = this.dodgeRegex.matcher(var11);
            if(var12.find()) {
                dodgeBonus = Integer.valueOf(dodgeBonus.intValue() + Integer.valueOf(var12.group(1)).intValue());
            }
        }

        return dodgeBonus.intValue();
    }

    public boolean dodgedAttack(LivingEntity entity) {
        if(!entity.isValid()) {
            return false;
        } else {
            Integer chance = Integer.valueOf(this.getDodgeBonus(entity));
            Integer roll = Integer.valueOf(this.generator.nextInt(100) + 1);
            return chance.intValue() >= roll.intValue();
        }
    }

    private int getCritChance(LivingEntity entity) {
        Integer chance = Integer.valueOf(0);
        ItemStack[] valueMatcher;
        int allLore = (valueMatcher = entity.getEquipment().getArmorContents()).length;

        ItemStack item;
        for(int lore = 0; lore < allLore; ++lore) {
            item = valueMatcher[lore];
            if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List lore1 = item.getItemMeta().getLore();
                String allLore1 = lore1.toString().toLowerCase();
                Matcher valueMatcher1 = this.critChanceRegex.matcher(allLore1);
                if(valueMatcher1.find()) {
                    chance = Integer.valueOf(chance.intValue() + Integer.valueOf(valueMatcher1.group(1)).intValue());
                }
            }
        }

        item = entity.getEquipment().getItemInHand();
        if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List var10 = item.getItemMeta().getLore();
            String var11 = var10.toString().toLowerCase();
            Matcher var12 = this.critChanceRegex.matcher(var11);
            if(var12.find()) {
                chance = Integer.valueOf(chance.intValue() + Integer.valueOf(var12.group(1)).intValue());
            }
        }

        return chance.intValue();
    }

    private boolean critAttack(LivingEntity entity) {
        if(!entity.isValid()) {
            return false;
        } else {
            Integer chance = Integer.valueOf(this.getCritChance(entity));
            Integer roll = Integer.valueOf(this.generator.nextInt(100) + 1);
            return chance.intValue() >= roll.intValue();
        }
    }

    public int getArmorBonus(LivingEntity entity) {
        Integer armor = Integer.valueOf(0);
        ItemStack[] valueMatcher;
        int allLore = (valueMatcher = entity.getEquipment().getArmorContents()).length;

        ItemStack item;
        for(int lore = 0; lore < allLore; ++lore) {
            item = valueMatcher[lore];
            if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List lore1 = item.getItemMeta().getLore();
                String allLore1 = lore1.toString().toLowerCase();
                Matcher valueMatcher1 = this.armorRegex.matcher(allLore1);
                if(valueMatcher1.find()) {
                    armor = Integer.valueOf(armor.intValue() + Integer.valueOf(valueMatcher1.group(1)).intValue());
                }
            }
        }

        item = entity.getEquipment().getItemInHand();
        if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List var10 = item.getItemMeta().getLore();
            String var11 = var10.toString().toLowerCase();
            Matcher var12 = this.armorRegex.matcher(var11);
            if(var12.find()) {
                armor = Integer.valueOf(armor.intValue() + Integer.valueOf(var12.group(1)).intValue());
            }
        }

        return armor.intValue();
    }

    public int getLifeSteal(LivingEntity entity) {
        Integer steal = Integer.valueOf(0);
        ItemStack[] valueMatcher;
        int allLore = (valueMatcher = entity.getEquipment().getArmorContents()).length;

        ItemStack item;
        for(int lore = 0; lore < allLore; ++lore) {
            item = valueMatcher[lore];
            if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List lore1 = item.getItemMeta().getLore();
                String allLore1 = lore1.toString().toLowerCase();
                Matcher valueMatcher1 = this.lifestealRegex.matcher(allLore1);
                if(valueMatcher1.find()) {
                    steal = Integer.valueOf(steal.intValue() + Integer.valueOf(valueMatcher1.group(1)).intValue());
                }
            }
        }

        item = entity.getEquipment().getItemInHand();
        if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List var10 = item.getItemMeta().getLore();
            String var11 = var10.toString().toLowerCase();
            Matcher var12 = this.lifestealRegex.matcher(var11);
            if(var12.find()) {
                steal = Integer.valueOf(steal.intValue() + Integer.valueOf(var12.group(1)).intValue());
            }
        }

        return steal.intValue();
    }

    public int getCritDamage(LivingEntity entity) {
        if(!this.critAttack(entity)) {
            return 0;
        } else {
            Integer damage = Integer.valueOf(0);
            ItemStack[] valueMatcher;
            int allLore = (valueMatcher = entity.getEquipment().getArmorContents()).length;

            ItemStack item;
            for(int lore = 0; lore < allLore; ++lore) {
                item = valueMatcher[lore];
                if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                    List lore1 = item.getItemMeta().getLore();
                    String allLore1 = lore1.toString().toLowerCase();
                    Matcher valueMatcher1 = this.critDamageRegex.matcher(allLore1);
                    if(valueMatcher1.find()) {
                        damage = Integer.valueOf(damage.intValue() + Integer.valueOf(valueMatcher1.group(1)).intValue());
                    }
                }
            }

            item = entity.getEquipment().getItemInHand();
            if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List var10 = item.getItemMeta().getLore();
                String var11 = var10.toString().toLowerCase();
                Matcher var12 = this.critDamageRegex.matcher(var11);
                if(var12.find()) {
                    damage = Integer.valueOf(damage.intValue() + Integer.valueOf(var12.group(1)).intValue());
                }
            }

            return damage.intValue();
        }
    }

    private double getAttackCooldown(Player player) {
        return !this.attackSpeedEnabled?0.0D:LoreAttributes.config.getDouble("lore.attack-speed.base-delay") * 0.1D - this.getAttackSpeed(player) * 0.1D;
    }

    public void addAttackCooldown(String playerName) {
        if(this.attackSpeedEnabled) {
            Timestamp able = new Timestamp((long)((double)(new Date()).getTime() + this.getAttackCooldown(Bukkit.getPlayerExact(playerName)) * 1000.0D));
            this.attackLog.put(playerName, able);
        }
    }

    public boolean canAttack(String playerName) {
        if(!this.attackSpeedEnabled) {
            return true;
        } else if(!this.attackLog.containsKey(playerName)) {
            return true;
        } else {
            Date now = new Date();
            return now.after((Date)this.attackLog.get(playerName));
        }
    }

    private double getAttackSpeed(Player player) {
        if(player == null) {
            return 1.0D;
        } else {
            double speed = 1.0D;
            ItemStack[] valueMatcher;
            int allLore = (valueMatcher = player.getEquipment().getArmorContents()).length;

            ItemStack item;
            for(int lore = 0; lore < allLore; ++lore) {
                item = valueMatcher[lore];
                if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                    List lore1 = item.getItemMeta().getLore();
                    String allLore1 = lore1.toString().toLowerCase();
                    Matcher valueMatcher1 = this.attackSpeedRegex.matcher(allLore1);
                    if(valueMatcher1.find()) {
                        speed += (double)Integer.valueOf(valueMatcher1.group(1)).intValue();
                    }
                }
            }

            item = player.getEquipment().getItemInHand();
            if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List var11 = item.getItemMeta().getLore();
                String var12 = var11.toString().toLowerCase();
                Matcher var13 = this.attackSpeedRegex.matcher(var12);
                if(var13.find()) {
                    speed += (double)Integer.valueOf(var13.group(1)).intValue();
                }
            }

            return speed;
        }
    }

    public void applyHpBonus(LivingEntity entity) {
        if(entity.isValid()) {
            Integer hpToAdd = Integer.valueOf(this.getHpBonus(entity));
            if(entity instanceof Player) {
                if(entity.getHealth() > (double)(this.getBaseHealth((Player)entity) + hpToAdd.intValue())) {
                    entity.setHealth((double)(this.getBaseHealth((Player)entity) + hpToAdd.intValue()));
                }

                entity.setMaxHealth((double)(this.getBaseHealth((Player)entity) + hpToAdd.intValue()));
            }

        }
    }

    public int getHpBonus(LivingEntity entity) {
        Integer hpToAdd = Integer.valueOf(0);
        ItemStack[] var6;
        int var5 = (var6 = entity.getEquipment().getArmorContents()).length;

        for(int var4 = 0; var4 < var5; ++var4) {
            ItemStack item = var6[var4];
            if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List lore = item.getItemMeta().getLore();
                String allLore = lore.toString().toLowerCase();
                Matcher negmatcher = this.negHealthRegex.matcher(allLore);
                Matcher matcher = this.healthRegex.matcher(allLore);
                if(matcher.find()) {
                    hpToAdd = Integer.valueOf(hpToAdd.intValue() + Integer.valueOf(matcher.group(1)).intValue());
                }

                if(negmatcher.find()) {
                    hpToAdd = Integer.valueOf(hpToAdd.intValue() - Integer.valueOf(negmatcher.group(1)).intValue());
                }

                if(hpToAdd.intValue() < 0) {
                    hpToAdd = Integer.valueOf(0);
                }
            }
        }

        return hpToAdd.intValue();
    }

    public int getBaseHealth(Player player) {
        int hp = LoreAttributes.config.getInt("lore.health.base-health");
        return hp;
    }

    public int getRegenBonus(LivingEntity entity) {
        if(!entity.isValid()) {
            return 0;
        } else {
            Integer regenBonus = Integer.valueOf(0);
            ItemStack[] var6;
            int var5 = (var6 = entity.getEquipment().getArmorContents()).length;

            for(int var4 = 0; var4 < var5; ++var4) {
                ItemStack item = var6[var4];
                if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                    List lore = item.getItemMeta().getLore();
                    String allLore = lore.toString().toLowerCase();
                    Matcher matcher = this.regenRegex.matcher(allLore);
                    if(matcher.find()) {
                        regenBonus = Integer.valueOf(regenBonus.intValue() + Integer.valueOf(matcher.group(1)).intValue());
                    }
                }
            }

            return regenBonus.intValue();
        }
    }

    public int getDamageBonus(LivingEntity entity) {
        if(!entity.isValid()) {
            return 0;
        } else {
            Integer damageMin = Integer.valueOf(0);
            Integer damageMax = Integer.valueOf(0);
            Integer damageBonus = Integer.valueOf(0);
            ItemStack[] negValueMatcher;
            int allLore = (negValueMatcher = entity.getEquipment().getArmorContents()).length;

            ItemStack item;
            for(int lore = 0; lore < allLore; ++lore) {
                item = negValueMatcher[lore];
                if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                    List rangeMatcher = item.getItemMeta().getLore();
                    String valueMatcher = rangeMatcher.toString().toLowerCase();
                    Matcher rangeMatcher1 = this.damageRangeRegex.matcher(valueMatcher);
                    Matcher valueMatcher1 = this.damageValueRegex.matcher(valueMatcher);
                    if(rangeMatcher1.find()) {
                        damageMin = Integer.valueOf(damageMin.intValue() + Integer.valueOf(rangeMatcher1.group(1)).intValue());
                        damageMax = Integer.valueOf(damageMax.intValue() + Integer.valueOf(rangeMatcher1.group(3)).intValue());
                    }

                    if(valueMatcher1.find()) {
                        damageBonus = Integer.valueOf(damageBonus.intValue() + Integer.valueOf(valueMatcher1.group(1)).intValue());
                    }
                }
            }

            item = entity.getEquipment().getItemInHand();
            if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List var13 = item.getItemMeta().getLore();
                String var14 = var13.toString().toLowerCase();
                Matcher var15 = this.negitiveDamageValueRegex.matcher(var14);
                Matcher var16 = this.damageRangeRegex.matcher(var14);
                Matcher var17 = this.damageValueRegex.matcher(var14);
                if(var16.find()) {
                    damageMin = Integer.valueOf(damageMin.intValue() + Integer.valueOf(var16.group(1)).intValue());
                    damageMax = Integer.valueOf(damageMax.intValue() + Integer.valueOf(var16.group(3)).intValue());
                }

                if(var17.find()) {
                    damageBonus = Integer.valueOf(damageBonus.intValue() + Integer.valueOf(var17.group(1)).intValue());
                    damageBonus = Integer.valueOf(damageBonus.intValue() - Integer.valueOf(var15.group(1)).intValue());
                }
            }

            if(damageMax < 0) {
                damageMax = 0;
            }

            if(damageMin < 0) {
                damageMin = 0;
            }

            return (int)Math.round(Math.random() * (double)(damageMax.intValue() - damageMin.intValue()) + (double)damageMin.intValue() + (double)damageBonus.intValue() + (double)this.getCritDamage(entity));
        }
    }

    public boolean useRangeOfDamage(LivingEntity entity) {
        if(!entity.isValid()) {
            return false;
        } else {
            ItemStack[] rangeMatcher;
            int allLore = (rangeMatcher = entity.getEquipment().getArmorContents()).length;

            ItemStack item;
            for(int lore = 0; lore < allLore; ++lore) {
                item = rangeMatcher[lore];
                if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                    List lore1 = item.getItemMeta().getLore();
                    String allLore1 = lore1.toString().toLowerCase();
                    Matcher rangeMatcher1 = this.damageRangeRegex.matcher(allLore1);
                    if(rangeMatcher1.find()) {
                        return true;
                    }
                }
            }

            item = entity.getEquipment().getItemInHand();
            if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List var9 = item.getItemMeta().getLore();
                String var10 = var9.toString().toLowerCase();
                Matcher var11 = this.damageRangeRegex.matcher(var10);
                if(var11.find()) {
                    return true;
                }
            }

            return false;
        }
    }

    private int getPermissionsHealth(Player player) {
        int hp = LoreAttributes.config.getInt("lore.health.base-health");

        try {
            hp = LoreAttributes.config.getInt("lore.health.base-health");
            return hp;
        } catch (Exception var4) {
            return hp;
        }
    }

    public void displayLoreStats(Player sender) {
        HashSet message = new HashSet();
        if(this.getHpBonus(sender) != 0) {
            message.add(ChatColor.GRAY + LoreAttributes.config.getString("lore.health.keyword") + ": " + ChatColor.WHITE + this.getHpBonus(sender));
        }

        if(this.getRegenBonus(sender) != 0) {
            message.add(ChatColor.GRAY + LoreAttributes.config.getString("lore.regen.keyword") + ": " + ChatColor.WHITE + this.getRegenBonus(sender));
        }

        if(LoreAttributes.config.getBoolean("lore.attack-speed.enabled")) {
            message.add(ChatColor.GRAY + LoreAttributes.config.getString("lore.attack-speed.keyword") + ": " + ChatColor.WHITE + this.getAttackSpeed(sender));
        }

        if(this.getDamageBonus(sender) != 0) {
            message.add(ChatColor.GRAY + LoreAttributes.config.getString("lore.damage.keyword") + ": " + ChatColor.WHITE + this.getDamageBonus(sender));
        }

        if(this.getDodgeBonus(sender) != 0) {
            message.add(ChatColor.GRAY + LoreAttributes.config.getString("lore.dodge.keyword") + ": " + ChatColor.WHITE + this.getDodgeBonus(sender) + "%");
        }

        if(this.getCritChance(sender) != 0) {
            message.add(ChatColor.GRAY + LoreAttributes.config.getString("lore.critical-chance.keyword") + ": " + ChatColor.WHITE + this.getCritChance(sender) + "%");
        }

        if(this.getCritDamage(sender) != 0) {
            message.add(ChatColor.GRAY + LoreAttributes.config.getString("lore.critical-damage.keyword") + ": " + ChatColor.WHITE + this.getCritDamage(sender));
        }

        if(this.getLifeSteal(sender) != 0) {
            message.add(ChatColor.GRAY + LoreAttributes.config.getString("lore.life-steal.keyword") + ": " + ChatColor.WHITE + this.getLifeSteal(sender));
        }

        if(this.getArmorBonus(sender) != 0) {
            message.add(ChatColor.GRAY + LoreAttributes.config.getString("lore.armor.keyword") + ": " + ChatColor.WHITE + this.getArmorBonus(sender));
        }

        String newMessage = "";
        Iterator var5 = message.iterator();

        while(var5.hasNext()) {
            String toSend = (String)var5.next();
            newMessage = newMessage + "     " + toSend;
            if(newMessage.length() > 40) {
                sender.sendMessage(newMessage);
                newMessage = "";
            }
        }

        if(newMessage.length() > 0) {
            sender.sendMessage(newMessage);
        }

        message.clear();
    }
}
