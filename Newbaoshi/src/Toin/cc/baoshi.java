//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Toin.cc;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class baoshi extends JavaPlugin implements Listener {
    private static final HashMap<String, Integer> useing = new HashMap<>();
    private static final String[] qinglinshi = new String[]{"§3§2§1§b§l一级青鳞石", "§3§2§1§b§l二级青鳞石", "§3§2§1§b§l三级青鳞石", "§3§2§1§b§l四级青鳞石", "§3§2§1§b§l五级青鳞石"};
    private static final Integer[] qinglinshishuju = new Integer[]{Integer.valueOf(5), Integer.valueOf(10), Integer.valueOf(20), Integer.valueOf(40), Integer.valueOf(100)};
    private static final String[] yanlinyu = new String[]{"§3§2§1§a§l一级烟灵玉", "§3§2§1§a§l二级烟灵玉", "§3§2§1§a§l三级烟灵玉", "§3§2§1§a§l四级烟灵玉", "§3§2§1§a§l五级烟灵玉"};
    private static final Integer[] yanlinyushuju = new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(10)};
    private static final String[] luofengshi = new String[]{"§3§2§1§e§l一级落凤石", "§3§2§1§e§l二级落凤石", "§3§2§1§e§l三级落凤石", "§3§2§1§e§l四级落凤石", "§3§2§1§e§l五级落凤石"};
    private static final Integer[] luofengshishuju = new Integer[]{Integer.valueOf(2), Integer.valueOf(5), Integer.valueOf(15), Integer.valueOf(30), Integer.valueOf(180)};
    private static final String[] shuixishi = new String[]{"§3§2§1§3§l一级水夕石", "§3§2§1§3§l二级水夕石", "§3§2§1§3§l三级水夕石", "§3§2§1§3§l四级水夕石", "§3§2§1§3§l五级水夕石"};
    private static final Integer[] shuixishishuju = new Integer[]{Integer.valueOf(2), Integer.valueOf(4), Integer.valueOf(8), Integer.valueOf(12), Integer.valueOf(25)};
    private static final String[] yanruyu = new String[]{"§3§2§1§c§l一级炎如玉", "§3§2§1§c§l二级炎如玉", "§3§2§1§c§l三级炎如玉", "§3§2§1§c§l四级炎如玉", "§3§2§1§c§l五级炎如玉"};
    private static final Integer[] sjyanruyu = new Integer[]{Integer.valueOf(6), Integer.valueOf(10), Integer.valueOf(15), Integer.valueOf(20), Integer.valueOf(35)};
    private static final String[] youmingshi = new String[]{"§3§2§1§8§l一级幽冥石", "§3§2§1§8§l二级幽冥石", "§3§2§1§8§l三级幽冥石", "§3§2§1§8§l四级幽冥石", "§3§2§1§8§l五级幽冥石"};
    private static final Integer[] sjyoumingshi = new Integer[]{Integer.valueOf(6), Integer.valueOf(10), Integer.valueOf(15), Integer.valueOf(20), Integer.valueOf(35)};
    private static final String[] zuoyishi = new String[]{"§3§2§1§9§l一级濯燿石", "§3§2§1§9§l二级濯燿石", "§3§2§1§9§l三级濯燿石", "§3§2§1§9§l四级濯燿石", "§3§2§1§9§l五级濯燿石"};
    private static final Integer[] sjzuoyishi = new Integer[]{Integer.valueOf(6), Integer.valueOf(10), Integer.valueOf(15), Integer.valueOf(20), Integer.valueOf(35)};

    public baoshi() {
    }

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("baoshi") && args.length < 3) {
            MainUtil.info(sender, "&2/baoshi 1-7(宝石类型) 1-5(宝石等级) <玩家ID>   &6给玩家指定类型的宝石");
            MainUtil.info(sender, "&2/baoshi 9 1 <玩家ID>   &6给指定玩家宝石黏合剂");
            MainUtil.info(sender, "&2宝石类型: &a1.§3§2§1§b§l青鳞石 , &a2.§3§2§1§b§l烟灵玉 , &a3.§3§2§1§e§l落凤石 , &a4.§3§2§1§3§l水夕石");
            MainUtil.info(sender, "&a5.§3§2§1§c§l炎如玉 , &a6.§3§2§1§8§l幽冥石 , &a7.§3§2§1§9§l濯熠石");
        }

        if (label.equalsIgnoreCase("baoshi") && args.length == 3) {
            if (!sender.isOp()) {
                return true;
            }

            Player gp = Bukkit.getPlayer(args[2]);
            if (gp == null) {
                sender.sendMessage("§c玩家不在线");
                return true;
            }

            switch (args[0]) {
                case "1":
                    switch (args[1]) {
                        case "1":
                            gp.getInventory().addItem(item_baoshi_qinglinshi(1));
                            break;
                        case "2":
                            gp.getInventory().addItem(item_baoshi_qinglinshi(2));
                            break;
                        case "3":
                            gp.getInventory().addItem(item_baoshi_qinglinshi(3));
                            break;
                        case "4":
                            gp.getInventory().addItem(item_baoshi_qinglinshi(4));
                            break;
                        case "5":
                            gp.getInventory().addItem(item_baoshi_qinglinshi(5));
                            break;
                    }
                    break;
                case "2":
                    switch (args[1]) {
                        case "1":
                            gp.getInventory().addItem(item_baoshi_yanlinyu(1));
                            break;
                        case "2":
                            gp.getInventory().addItem(item_baoshi_yanlinyu(2));
                            break;
                        case "3":
                            gp.getInventory().addItem(item_baoshi_yanlinyu(3));
                            break;
                        case "4":
                            gp.getInventory().addItem(item_baoshi_yanlinyu(4));
                            break;
                        case "5":
                            gp.getInventory().addItem(item_baoshi_yanlinyu(5));
                            break;
                    }
                    break;
                case "3":
                    switch (args[1]) {
                        case "1":
                            gp.getInventory().addItem(item_baoshi_luofengshi(1));
                            break;
                        case "2":
                            gp.getInventory().addItem(item_baoshi_luofengshi(2));
                            break;
                        case "3":
                            gp.getInventory().addItem(item_baoshi_luofengshi(3));
                            break;
                        case "4":
                            gp.getInventory().addItem(item_baoshi_luofengshi(4));
                            break;
                        case "5":
                            gp.getInventory().addItem(item_baoshi_luofengshi(5));
                            break;
                    }
                    break;
                case "4":
                    switch (args[1]) {
                        case "1":
                            gp.getInventory().addItem(item_baoshi_shuixishi(1));
                            break;
                        case "2":
                            gp.getInventory().addItem(item_baoshi_shuixishi(2));
                            break;
                        case "3":
                            gp.getInventory().addItem(item_baoshi_shuixishi(3));
                            break;
                        case "4":
                            gp.getInventory().addItem(item_baoshi_shuixishi(4));
                            break;
                        case "5":
                            gp.getInventory().addItem(item_baoshi_shuixishi(5));
                            break;
                    }
                    break;
                case "5":
                    switch (args[1]) {
                        case "1":
                            gp.getInventory().addItem(item_baoshi_yanruyu(1));
                            break;
                        case "2":
                            gp.getInventory().addItem(item_baoshi_yanruyu(2));
                            break;
                        case "3":
                            gp.getInventory().addItem(item_baoshi_yanruyu(3));
                            break;
                        case "4":
                            gp.getInventory().addItem(item_baoshi_yanruyu(4));
                            break;
                        case "5":
                            gp.getInventory().addItem(item_baoshi_yanruyu(5));
                            break;
                    }
                    break;
                case "6":
                    switch (args[1]) {
                        case "1":
                            gp.getInventory().addItem(item_baoshi_youmingshi(1));
                            break;
                        case "2":
                            gp.getInventory().addItem(item_baoshi_youmingshi(2));
                            break;
                        case "3":
                            gp.getInventory().addItem(item_baoshi_youmingshi(3));
                            break;
                        case "4":
                            gp.getInventory().addItem(item_baoshi_youmingshi(4));
                            break;
                        case "5":
                            gp.getInventory().addItem(item_baoshi_youmingshi(5));
                            break;
                    }
                    break;
                case "7":
                    switch (args[1]) {
                        case "1":
                            gp.getInventory().addItem(item_baoshi_zuoyishi(1));
                            break;
                        case "2":
                            gp.getInventory().addItem(item_baoshi_zuoyishi(2));
                            break;
                        case "3":
                            gp.getInventory().addItem(item_baoshi_zuoyishi(3));
                            break;
                        case "4":
                            gp.getInventory().addItem(item_baoshi_zuoyishi(4));
                            break;
                        case "5":
                            gp.getInventory().addItem(item_baoshi_zuoyishi(5));
                            break;
                    }
                    break;
                case "9":
                    gp.getInventory().addItem(item_baoshi_nianheji());
                    break;
            }
        }

        return true;
    }

    private static String getItemName(ItemStack item) {
        Material meta = item.getType();
        return meta.equals(Material.BOW) ? "弓" : (meta.equals(Material.IRON_SWORD) ? "铁剑" : (meta.equals(Material.WOOD_SWORD) ? "木剑" : (meta.equals(Material.STONE_SWORD) ? "石剑" : (meta.equals(Material.DIAMOND_SWORD) ? "钻石剑" : (meta.equals(Material.GOLD_SWORD) ? "金剑" : (meta.equals(Material.LEATHER_HELMET) ? "皮帽" : (meta.equals(Material.LEATHER_CHESTPLATE) ? "皮衣" : (meta.equals(Material.LEATHER_LEGGINGS) ? "皮裤" : (meta.equals(Material.LEATHER_BOOTS) ? "皮鞋" : (meta.equals(Material.CHAINMAIL_HELMET) ? "锁链帽" : (meta.equals(Material.CHAINMAIL_CHESTPLATE) ? "锁链衣" : (meta.equals(Material.CHAINMAIL_LEGGINGS) ? "锁链裤" : (meta.equals(Material.CHAINMAIL_BOOTS) ? "锁链鞋" : (meta.equals(Material.IRON_HELMET) ? "铁帽" : (meta.equals(Material.IRON_CHESTPLATE) ? "铁衣" : (meta.equals(Material.IRON_LEGGINGS) ? "铁裤" : (meta.equals(Material.IRON_BOOTS) ? "铁鞋" : (meta.equals(Material.DIAMOND_HELMET) ? "钻石帽" : (meta.equals(Material.DIAMOND_CHESTPLATE) ? "钻石衣" : (meta.equals(Material.DIAMOND_LEGGINGS) ? "钻石裤" : (meta.equals(Material.DIAMOND_BOOTS) ? "钻石鞋" : (meta.equals(Material.GOLD_HELMET) ? "金帽" : (meta.equals(Material.GOLD_CHESTPLATE) ? "金衣" : (meta.equals(Material.GOLD_LEGGINGS) ? "金裤" : (meta.equals(Material.GOLD_BOOTS) ? "金鞋" : "未知")))))))))))))))))))))))));
    }

    private static ItemStack xiangqian(ItemStack xqitem, String quchu, String baoshi) {
        ItemMeta id = xqitem.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        String fujia = "";
        if (baoshi.contains("青鳞石")) {
            fujia = "§7  -  §a附加伤害+§6" + qinglinshishuju[getqinglinshiLevel(baoshi) - 1];
        } else if (baoshi.contains("落凤石")) {
            fujia = "§7  -  §a火焰附加+§6" + luofengshishuju[getluofengshiLevel(baoshi) - 1] + "§a秒";
        } else if (baoshi.contains("烟灵玉")) {
            fujia = "§7  -  §a闪避+§6" + yanlinyushuju[getyanlinyuLevel(baoshi) - 1] + "§a%";
        } else if (baoshi.contains("水夕石")) {
            fujia = "§7  -  §a附加防御+§6" + shuixishishuju[getshuixishiLevel(baoshi) - 1];
        } else if (baoshi.contains("炎如玉")) {
            fujia = "§7  -  §a暴击几率+§6" + sjyanruyu[getyanruyuLevel(baoshi) - 1] + "§a%";
        } else if (baoshi.contains("幽冥石")) {
            fujia = "§7  -  §a附加凋零几率+§6" + sjyoumingshi[getyoumingshiLevel(baoshi) - 1] + "§a%";
        } else if (baoshi.contains("濯燿石")) {
            fujia = "§7  -  §a生命恢复几率+§6" + sjzuoyishi[getzuoyishiLevel(baoshi) - 1] + "§a%";
        }

        if (id.hasLore()) {
            lore.addAll(id.getLore());
        }

        boolean yijingxiangqian = false;

        for (Object aLore1 : lore) {
            String var10 = (String) aLore1;
            if (var10.contains("§e§l宝石镶嵌")) {
                yijingxiangqian = true;
                break;
            }
        }

        int var111;
        if (!yijingxiangqian) {
            var111 = 0;

            for (Object aLore : lore) {
                String var11 = (String) aLore;
                if (var11.contains("已绑定")) {
                    ++var111;
                } else if (var11.contains("淬炼保护")) {
                    ++var111;
                } else if (var11.contains("装备淬炼")) {
                    var111 += 2;
                }
            }

            lore.add(var111, "§c§m§l  §6§m§l  §e§m§l  §a§m§l  §b§m§l  §e§l宝石镶嵌§b§m§l  §a§m§l  §e§m§l  §6§m§l  §c§m§l  ");
        }

        for (var111 = 0; var111 < lore.size(); ++var111) {
            if (((String) lore.get(var111)).contains(quchu)) {
                lore.remove(var111);
                break;
            }
        }

        for (var111 = 0; var111 < lore.size(); ++var111) {
            if (((String) lore.get(var111)).contains("§e§l宝石镶嵌")) {
                lore.add(var111 + 1, baoshi + fujia);
                break;
            }
        }

        id.setLore(lore);
        if (!id.hasDisplayName()) {
            id.setDisplayName(getItemName(xqitem));
        }

        xqitem.setItemMeta(id);
        return xqitem;
    }

    private static int getshuixishiLevel(String itemname) {
        int dj = 0;
        String[] var5 = shuixishi;
        int var4 = shuixishi.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            String line = var5[var3];
            ++dj;
            if (line.equals(itemname)) {
                return dj;
            }
        }

        return 0;
    }

    private static int getyanruyuLevel(String itemname) {
        int dj = 0;
        String[] var5 = yanruyu;
        int var4 = yanruyu.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            String line = var5[var3];
            ++dj;
            if (line.equals(itemname)) {
                return dj;
            }
        }

        return 0;
    }

    private static int getyoumingshiLevel(String itemname) {
        int dj = 0;
        String[] var5 = youmingshi;
        int var4 = youmingshi.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            String line = var5[var3];
            ++dj;
            if (line.equals(itemname)) {
                return dj;
            }
        }

        return 0;
    }

    private static int getzuoyishiLevel(String itemname) {
        int dj = 0;
        String[] var5 = zuoyishi;
        int var4 = zuoyishi.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            String line = var5[var3];
            ++dj;
            if (line.equals(itemname)) {
                return dj;
            }
        }

        return 0;
    }

    private static int getqinglinshiLevel(String itemname) {
        int dj = 0;
        String[] var5 = qinglinshi;
        int var4 = qinglinshi.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            String line = var5[var3];
            ++dj;
            if (line.equals(itemname)) {
                return dj;
            }
        }

        return 0;
    }

    private static int getyanlinyuLevel(String itemname) {
        int dj = 0;
        String[] var5 = yanlinyu;
        int var4 = yanlinyu.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            String line = var5[var3];
            ++dj;
            if (line.equals(itemname)) {
                return dj;
            }
        }

        return 0;
    }

    private static int getluofengshiLevel(String itemname) {
        int dj = 0;
        String[] var5 = luofengshi;
        int var4 = luofengshi.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            String line = var5[var3];
            ++dj;
            if (line.equals(itemname)) {
                return dj;
            }
        }

        return 0;
    }

    private static ItemStack item_baoshi_qinglinshi(Integer dj) {
        ItemStack i = new ItemStack(Material.getMaterial(351), 1, (short) 4);
        ItemMeta id = i.getItemMeta();
        id.setDisplayName(qinglinshi[dj - 1]);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§e用法：§a打开背包，鼠标右键物品");
        lore.add("§e作用：§a将该宝石镶嵌至 §c武器");
        lore.add("§e属性：§a附加伤害+§6" + qinglinshishuju[dj - 1]);
        lore.add("§e说明：");
        lore.add("§7  -§c该宝石只能对武器使用");
        lore.add("§7  -§a武器附加伤害增加");
        lore.add("§7  -§a同类型宝石只能镶嵌一颗");
        if (dj < 5) {
            lore.add("§7  -§a5颗同等级宝石拿在手上右键可合成");
            if (dj == 4) {
                lore.add("§7  -§c合成需要宝石粘合剂");
            }
        }

        id.setLore(lore);
        id.addEnchant(Enchantment.ARROW_DAMAGE, dj, true);
        i.setItemMeta(id);
        return i;
    }

    private static ItemStack item_baoshi_zuoyishi(Integer dj) {
        ItemStack i = new ItemStack(Material.getMaterial(351), 1, (short) 12);
        ItemMeta id = i.getItemMeta();
        id.setDisplayName(zuoyishi[dj - 1]);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§e用法：§a打开背包，鼠标右键物品");
        lore.add("§e作用：§a将该宝石镶嵌至 §c武器");
        lore.add("§e属性：§a生命恢复几率+§6" + sjzuoyishi[dj - 1] + "%");
        lore.add("§e说明：");
        lore.add("§7  -§c该宝石只能对武器使用");
        lore.add("§7  -§a武器附带生命恢复效果");
        lore.add("§7  -§a同类型宝石只能镶嵌一颗");
        if (dj < 5) {
            lore.add("§7  -§a5颗同等级宝石拿在手上右键可合成");
            if (dj == 4) {
                lore.add("§7  -§c合成需要宝石粘合剂");
            }
        }

        id.setLore(lore);
        id.addEnchant(Enchantment.WATER_WORKER, dj, true);
        i.setItemMeta(id);
        return i;
    }

    private static ItemStack item_baoshi_youmingshi(Integer dj) {
        ItemStack i = new ItemStack(Material.getMaterial(351), 1, (short) 8);
        ItemMeta id = i.getItemMeta();
        id.setDisplayName(youmingshi[dj - 1]);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§e用法：§a打开背包，鼠标右键物品");
        lore.add("§e作用：§a将该宝石镶嵌至 §c武器");
        lore.add("§e属性：§a凋零伤害几率+§6" + sjyoumingshi[dj - 1] + "%");
        lore.add("§e说明：");
        lore.add("§7  -§c该宝石只能对武器使用");
        lore.add("§7  -§a武器附加凋零攻击效果");
        lore.add("§7  -§a同类型宝石只能镶嵌一颗");
        if (dj < 5) {
            lore.add("§7  -§a5颗同等级宝石拿在手上右键可合成");
            if (dj == 4) {
                lore.add("§7  -§c合成需要宝石粘合剂");
            }
        }

        id.setLore(lore);
        id.addEnchant(Enchantment.WATER_WORKER, dj, true);
        i.setItemMeta(id);
        return i;
    }

    private static ItemStack item_baoshi_yanruyu(Integer dj) {
        ItemStack i = new ItemStack(Material.getMaterial(351), 1, (short) 14);
        ItemMeta id = i.getItemMeta();
        id.setDisplayName(yanruyu[dj - 1]);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§e用法：§a打开背包，鼠标右键物品");
        lore.add("§e作用：§a将该宝石镶嵌至 §c武器");
        lore.add("§e属性：§a暴击+§6" + sjyanruyu[dj - 1] + "%");
        lore.add("§e说明：");
        lore.add("§7  -§c该宝石只能对武器使用");
        lore.add("§7  -§a武器暴击增加");
        lore.add("§7  -§a同类型宝石只能镶嵌一颗");
        if (dj < 5) {
            lore.add("§7  -§a5颗同等级宝石拿在手上右键可合成");
            if (dj == 4) {
                lore.add("§7  -§c合成需要宝石粘合剂");
            }
        }

        id.setLore(lore);
        id.addEnchant(Enchantment.WATER_WORKER, dj, true);
        i.setItemMeta(id);
        return i;
    }

    private static ItemStack item_baoshi_yanlinyu(Integer dj) {
        ItemStack i = new ItemStack(Material.getMaterial(351), 1, (short) 15);
        ItemMeta id = i.getItemMeta();
        id.setDisplayName(yanlinyu[dj - 1]);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§e用法：§a打开背包，鼠标右键物品");
        lore.add("§e作用：§a将该宝石镶嵌至 §c护甲");
        lore.add("§e属性：§a闪避+§6" + yanlinyushuju[dj - 1] + "%");
        lore.add("§e说明：");
        lore.add("§7  -§c该宝石只能对护甲使用");
        lore.add("§7  -§a护甲闪避增加");
        lore.add("§7  -§a同类型宝石只能镶嵌一颗");
        if (dj < 5) {
            lore.add("§7  -§a5颗同等级宝石拿在手上右键可合成");
            if (dj == 4) {
                lore.add("§7  -§c合成需要宝石粘合剂");
            }
        }

        id.setLore(lore);
        id.addEnchant(Enchantment.WATER_WORKER, dj, true);
        i.setItemMeta(id);
        return i;
    }

    private static ItemStack item_baoshi_luofengshi(Integer dj) {
        ItemStack i = new ItemStack(Material.getMaterial(351), 1, (short) 11);
        ItemMeta id = i.getItemMeta();
        id.setDisplayName(luofengshi[dj - 1]);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§e用法：§a打开背包，鼠标右键物品");
        lore.add("§e作用：§a将该宝石镶嵌至 §c武器");
        lore.add("§e属性：§a火焰附加+§6" + luofengshishuju[dj - 1] + "秒");
        lore.add("§e说明：");
        lore.add("§7  -§c该宝石只能对武器使用");
        lore.add("§7  -§a武器火焰附加");
        lore.add("§7  -§a同类型宝石只能镶嵌一颗");
        if (dj < 5) {
            lore.add("§7  -§a5颗同等级宝石拿在手上右键可合成");
            if (dj == 4) {
                lore.add("§7  -§c合成需要宝石粘合剂");
            }
        }

        id.setLore(lore);
        id.addEnchant(Enchantment.FIRE_ASPECT, dj, true);
        i.setItemMeta(id);
        return i;
    }

    private static ItemStack item_baoshi_shuixishi(Integer dj) {
        ItemStack i = new ItemStack(Material.GHAST_TEAR);
        ItemMeta id = i.getItemMeta();
        id.setDisplayName(shuixishi[dj - 1]);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§e用法：§a打开背包，鼠标右键物品");
        lore.add("§e作用：§a将该宝石镶嵌至 §c护甲");
        lore.add("§e属性：§a附加防御+§6" + shuixishishuju[dj - 1]);
        lore.add("§e说明：");
        lore.add("§7  -§c该宝石只能对护甲使用");
        lore.add("§7  -§a护甲附加防御增加");
        lore.add("§7  -§a同类型宝石只能镶嵌一颗");
        if (dj < 5) {
            lore.add("§7  -§a5颗同等级宝石拿在手上右键可合成");
            if (dj == 4) {
                lore.add("§7  -§c合成需要宝石粘合剂");
            }
        }

        id.setLore(lore);
        id.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, dj, true);
        i.setItemMeta(id);
        return i;
    }

    private static ItemStack item_baoshi_nianheji() {
        ItemStack i = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta id = i.getItemMeta();
        id.setDisplayName("§3§2§1§d宝石粘合剂");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§e用法：§a无");
        lore.add("§e作用：§a合成5级宝石时需要");
        lore.add("§e说明：");
        lore.add("§7  -§a合成5级宝石时该物品在背包内即可");
        id.setLore(lore);
        id.addEnchant(Enchantment.DURABILITY, 1, true);
        i.setItemMeta(id);
        return i;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK && !e.isCancelled()) {
            Player p = e.getPlayer();
            ItemStack item = p.getItemInHand();
            if (item != null && item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String itemname = item.getItemMeta().getDisplayName();
                int i;
                String[] var9;
                int var8;
                int var7;
                String name;
                if (itemname.contains("青鳞石")) {
                    i = 0;
                    var9 = qinglinshi;
                    var8 = qinglinshi.length;

                    for (var7 = 0; var7 < var8; ++var7) {
                        name = var9[var7];
                        ++i;
                        if (itemname.equals(name)) {
                            if (i == 5) {
                                p.sendMessage("§c5级宝石暂时不可继续合成，敬请期待。");
                                return;
                            }

                            if (i == 4) {
                                if (playerHasItem(p, item_baoshi_nianheji().getItemMeta().getDisplayName()) > 0) {
                                    if (TakePlayerHandItem(p, 5)) {
                                        TakePlayerItem(p, item_baoshi_nianheji());
                                        p.getInventory().addItem(item_baoshi_qinglinshi(i + 1));
                                        p.sendMessage("§a成功合成" + qinglinshi[i]);
                                    } else {
                                        p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                                    }
                                } else {
                                    p.sendMessage("§c合成5级宝石需要" + item_baoshi_nianheji().getItemMeta().getDisplayName());
                                }
                            } else if (TakePlayerHandItem(p, 5)) {
                                p.getInventory().addItem(item_baoshi_qinglinshi(i + 1));
                                p.sendMessage("§a成功合成" + qinglinshi[i]);
                            } else {
                                p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                            }
                            break;
                        }
                    }
                }

                if (itemname.contains("烟灵玉")) {
                    i = 0;
                    var9 = yanlinyu;
                    var8 = yanlinyu.length;

                    for (var7 = 0; var7 < var8; ++var7) {
                        name = var9[var7];
                        ++i;
                        if (itemname.equals(name)) {
                            if (i == 5) {
                                p.sendMessage("§c5级宝石暂时不可继续合成，敬请期待。");
                                return;
                            }

                            if (i == 4) {
                                if (playerHasItem(p, item_baoshi_nianheji().getItemMeta().getDisplayName()) > 0) {
                                    if (TakePlayerHandItem(p, 5)) {
                                        TakePlayerItem(p, item_baoshi_nianheji());
                                        p.getInventory().addItem(item_baoshi_yanlinyu(i + 1));
                                        p.sendMessage("§a成功合成" + yanlinyu[i]);
                                    } else {
                                        p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                                    }
                                } else {
                                    p.sendMessage("§c合成5级宝石需要" + item_baoshi_nianheji().getItemMeta().getDisplayName());
                                }
                            } else if (TakePlayerHandItem(p, 5)) {
                                p.getInventory().addItem(item_baoshi_yanlinyu(i + 1));
                                p.sendMessage("§a成功合成" + yanlinyu[i]);
                            } else {
                                p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                            }
                            break;
                        }
                    }
                }

                if (itemname.contains("落凤石")) {
                    i = 0;
                    var9 = luofengshi;
                    var8 = luofengshi.length;

                    for (var7 = 0; var7 < var8; ++var7) {
                        name = var9[var7];
                        ++i;
                        if (itemname.equals(name)) {
                            if (i == 5) {
                                p.sendMessage("§c5级宝石暂时不可继续合成，敬请期待。");
                                return;
                            }

                            if (i == 4) {
                                if (playerHasItem(p, item_baoshi_nianheji().getItemMeta().getDisplayName()) > 0) {
                                    if (TakePlayerHandItem(p, 5)) {
                                        TakePlayerItem(p, item_baoshi_nianheji());
                                        p.getInventory().addItem(item_baoshi_luofengshi(i + 1));
                                        p.sendMessage("§a成功合成" + luofengshi[i]);
                                    } else {
                                        p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                                    }
                                } else {
                                    p.sendMessage("§c合成5级宝石需要" + item_baoshi_nianheji().getItemMeta().getDisplayName());
                                }
                            } else if (TakePlayerHandItem(p, 5)) {
                                p.getInventory().addItem(item_baoshi_luofengshi(i + 1));
                                p.sendMessage("§a成功合成" + luofengshi[i]);
                            } else {
                                p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                            }
                            break;
                        }
                    }
                }

                if (itemname.contains("水夕石")) {
                    i = 0;
                    var9 = shuixishi;
                    var8 = shuixishi.length;

                    for (var7 = 0; var7 < var8; ++var7) {
                        name = var9[var7];
                        ++i;
                        if (itemname.equals(name)) {
                            if (i == 5) {
                                p.sendMessage("§c5级宝石暂时不可继续合成，敬请期待。");
                                return;
                            }

                            if (i == 4) {
                                if (playerHasItem(p, item_baoshi_nianheji().getItemMeta().getDisplayName()) > 0) {
                                    if (TakePlayerHandItem(p, 5)) {
                                        TakePlayerItem(p, item_baoshi_nianheji());
                                        p.getInventory().addItem(item_baoshi_shuixishi(i + 1));
                                        p.sendMessage("§a成功合成" + shuixishi[i]);
                                    } else {
                                        p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                                    }
                                } else {
                                    p.sendMessage("§c合成5级宝石需要" + item_baoshi_nianheji().getItemMeta().getDisplayName());
                                }
                            } else if (TakePlayerHandItem(p, 5)) {
                                p.getInventory().addItem(item_baoshi_shuixishi(i + 1));
                                p.sendMessage("§a成功合成" + shuixishi[i]);
                            } else {
                                p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                            }
                            break;
                        }
                    }
                }

                if (itemname.contains("炎如玉")) {
                    i = 0;
                    var9 = yanruyu;
                    var8 = yanruyu.length;

                    for (var7 = 0; var7 < var8; ++var7) {
                        name = var9[var7];
                        ++i;
                        if (itemname.equals(name)) {
                            if (i == 5) {
                                p.sendMessage("§c5级宝石暂时不可继续合成，敬请期待。");
                                return;
                            }

                            if (i == 4) {
                                if (playerHasItem(p, item_baoshi_nianheji().getItemMeta().getDisplayName()) > 0) {
                                    if (TakePlayerHandItem(p, 5)) {
                                        TakePlayerItem(p, item_baoshi_nianheji());
                                        p.getInventory().addItem(item_baoshi_yanruyu(i + 1));
                                        p.sendMessage("§a成功合成" + yanruyu[i]);
                                    } else {
                                        p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                                    }
                                } else {
                                    p.sendMessage("§c合成5级宝石需要" + item_baoshi_nianheji().getItemMeta().getDisplayName());
                                }
                            } else if (TakePlayerHandItem(p, 5)) {
                                p.getInventory().addItem(item_baoshi_yanruyu(i + 1));
                                p.sendMessage("§a成功合成" + yanruyu[i]);
                            } else {
                                p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                            }
                            break;
                        }
                    }
                }

                if (itemname.contains("幽冥石")) {
                    i = 0;
                    var9 = youmingshi;
                    var8 = youmingshi.length;

                    for (var7 = 0; var7 < var8; ++var7) {
                        name = var9[var7];
                        ++i;
                        if (itemname.equals(name)) {
                            if (i == 5) {
                                p.sendMessage("§c5级宝石暂时不可继续合成，敬请期待。");
                                return;
                            }

                            if (i == 4) {
                                if (playerHasItem(p, item_baoshi_nianheji().getItemMeta().getDisplayName()) > 0) {
                                    if (TakePlayerHandItem(p, 5)) {
                                        TakePlayerItem(p, item_baoshi_nianheji());
                                        p.getInventory().addItem(item_baoshi_youmingshi(i + 1));
                                        p.sendMessage("§a成功合成" + youmingshi[i]);
                                    } else {
                                        p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                                    }
                                } else {
                                    p.sendMessage("§c合成5级宝石需要" + item_baoshi_nianheji().getItemMeta().getDisplayName());
                                }
                            } else if (TakePlayerHandItem(p, 5)) {
                                p.getInventory().addItem(item_baoshi_youmingshi(i + 1));
                                p.sendMessage("§a成功合成" + youmingshi[i]);
                            } else {
                                p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                            }
                            break;
                        }
                    }
                }

                if (itemname.contains("濯燿石")) {
                    i = 0;
                    var9 = zuoyishi;
                    var8 = zuoyishi.length;

                    for (var7 = 0; var7 < var8; ++var7) {
                        name = var9[var7];
                        ++i;
                        if (itemname.equals(name)) {
                            if (i == 5) {
                                p.sendMessage("§c5级宝石暂时不可继续合成，敬请期待。");
                                return;
                            }

                            if (i == 4) {
                                if (playerHasItem(p, item_baoshi_nianheji().getItemMeta().getDisplayName()) > 0) {
                                    if (TakePlayerHandItem(p, 5)) {
                                        TakePlayerItem(p, item_baoshi_nianheji());
                                        p.getInventory().addItem(item_baoshi_zuoyishi(i + 1));
                                        p.sendMessage("§a成功合成" + zuoyishi[i]);
                                    } else {
                                        p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                                    }
                                } else {
                                    p.sendMessage("§c合成5级宝石需要" + item_baoshi_nianheji().getItemMeta().getDisplayName());
                                }
                            } else if (TakePlayerHandItem(p, 5)) {
                                p.getInventory().addItem(item_baoshi_zuoyishi(i + 1));
                                p.sendMessage("§a成功合成" + zuoyishi[i]);
                            } else {
                                p.sendMessage("§c宝石合成需要5个同等级同类型的宝石。");
                            }
                            break;
                        }
                    }
                }
            }
        }

    }

    private static int playerHasItem(Player p, String itemname) {
        int sl = 0;
        ItemStack[] var6;
        int var5 = (var6 = p.getInventory().getContents()).length;

        for (int var4 = 0; var4 < var5; ++var4) {
            ItemStack s = var6[var4];
            if (s != null && s.getItemMeta().hasDisplayName() && s.getItemMeta().getDisplayName().equals(itemname)) {
                sl += s.getAmount();
            }
        }

        return sl;
    }

    private static boolean TakePlayerItem(Player p, ItemStack item) {
        boolean cg = false;
        boolean jg = false;
        int a = 0;
        ItemStack[] var9;
        int var8 = (var9 = p.getInventory().getContents()).length;

        int var10;
        for (var10 = 0; var10 < var8; ++var10) {
            ItemStack b = var9[var10];
            if (b != null && b.getItemMeta().hasDisplayName() && b.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                jg = true;
                a += b.getAmount();
                p.getInventory().removeItem(b);
            }
        }

        if (!jg) {
            return false;
        } else {
            var10 = a;
            a -= item.getAmount();
            if (a > 0) {
                cg = true;
                item.setAmount(a);
                p.getInventory().addItem(item);
            } else if (a < 0) {
                cg = false;
                item.setAmount(var10);
                p.getInventory().addItem(item);
            } else {
                cg = true;
            }

            return cg;
        }
    }

    private static boolean TakePlayerHandItem(Player p, Integer sl) {
        ItemStack item = p.getInventory().getItemInHand();
        int count = item.getAmount() - sl;
        if (count == 0) {
            item.setAmount(0);
            p.setItemInHand(null);
            return true;
        } else if (count > 0) {
            item.setAmount(count);
            return true;
        } else {
            return false;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getRawSlot() >= 0) {
            ItemStack item = e.getCurrentItem();
            Player p = (Player) e.getWhoClicked();
            if (e.isRightClick()) {
                if (useing.containsKey(p.getName())) {
                    if (e.getInventory().getType() != InventoryType.CRAFTING) {
                        p.sendMessage("§c请在背包内右键");
                    } else if (item != null && !item.getType().equals(Material.AIR)) {
                        int dj;
                        int itemname1;
                        if (useing.get(p.getName()) > 160) {
                            itemname1 = item.getTypeId();
                            if (itemname1 != 261 && itemname1 != 267 && itemname1 != 268 && itemname1 != 272 && itemname1 != 276 && itemname1 != 283) {
                                p.sendMessage("§c该宝石只支持镶嵌在武器。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            dj = useing.get(p.getName()) - 160;
                            if (!TakePlayerItem(p, item_baoshi_zuoyishi(dj))) {
                                p.sendMessage("§c所需物品\"" + zuoyishi[dj - 1] + "§c\"不足。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            item = xiangqian(item, "濯燿石", zuoyishi[dj - 1]);
                            p.sendMessage("§a装备成功镶嵌" + zuoyishi[dj - 1]);
                        } else if (useing.get(p.getName()) >= 150) {
                            itemname1 = item.getTypeId();
                            if (itemname1 != 261 && itemname1 != 267 && itemname1 != 268 && itemname1 != 272 && itemname1 != 276 && itemname1 != 283) {
                                p.sendMessage("§c该宝石只支持镶嵌在武器。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            dj = useing.get(p.getName()) - 150;
                            if (!TakePlayerItem(p, item_baoshi_youmingshi(dj))) {
                                p.sendMessage("§c所需物品\"" + youmingshi[dj - 1] + "§c\"不足。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            item = xiangqian(item, "幽冥石", youmingshi[dj - 1]);
                            p.sendMessage("§a装备成功镶嵌" + youmingshi[dj - 1]);
                        } else if (useing.get(p.getName()) >= 140) {
                            itemname1 = item.getTypeId();
                            if (itemname1 != 261 && itemname1 != 267 && itemname1 != 268 && itemname1 != 272 && itemname1 != 276 && itemname1 != 283) {
                                p.sendMessage("§c该宝石只支持镶嵌在武器。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            dj = useing.get(p.getName()) - 140;
                            if (!TakePlayerItem(p, item_baoshi_yanruyu(dj))) {
                                p.sendMessage("§c所需物品\"" + yanruyu[dj - 1] + "§c\"不足。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            item = xiangqian(item, "炎如玉", yanruyu[dj - 1]);
                            p.sendMessage("§a装备成功镶嵌" + yanruyu[dj - 1]);
                        } else if (useing.get(p.getName()) > 130) {
                            itemname1 = item.getTypeId();
                            if (itemname1 < 298 || itemname1 > 317) {
                                p.sendMessage("§c该宝石只支持镶嵌在护甲。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            dj = useing.get(p.getName()) - 130;
                            if (!TakePlayerItem(p, item_baoshi_yanlinyu(dj))) {
                                p.sendMessage("§c所需物品\"" + yanlinyu[dj - 1] + "§c\"不足。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            item = xiangqian(item, "烟灵玉", yanlinyu[dj - 1]);
                            p.sendMessage("§a装备成功镶嵌" + yanlinyu[dj - 1]);
                        } else if (useing.get(p.getName()) > 120) {
                            itemname1 = item.getTypeId();
                            if (itemname1 < 298 || itemname1 > 317) {
                                p.sendMessage("§c该宝石只支持镶嵌在护甲。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            dj = useing.get(p.getName()) - 120;
                            if (!TakePlayerItem(p, item_baoshi_shuixishi(dj))) {
                                p.sendMessage("§c所需物品\"" + shuixishi[dj - 1] + "§c\"不足。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            item = xiangqian(item, "水夕石", shuixishi[dj - 1]);
                            p.sendMessage("§a装备成功镶嵌" + shuixishi[dj - 1]);
                        } else if (useing.get(p.getName()) > 110) {
                            itemname1 = item.getTypeId();
                            if (itemname1 != 261 && itemname1 != 267 && itemname1 != 268 && itemname1 != 272 && itemname1 != 276 && itemname1 != 283) {
                                p.sendMessage("§c该宝石只支持镶嵌在武器。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            dj = useing.get(p.getName()) - 110;
                            if (!TakePlayerItem(p, item_baoshi_qinglinshi(dj))) {
                                p.sendMessage("§c所需物品\"" + qinglinshi[dj - 1] + "§c\"不足。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            item = xiangqian(item, "青鳞石", qinglinshi[dj - 1]);
                            p.sendMessage("§a装备成功镶嵌" + qinglinshi[dj - 1]);
                        } else if (useing.get(p.getName()) <= 100) {
                            if (useing.get(p.getName()) <= 100) {
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            itemname1 = item.getTypeId();
                            if (itemname1 != 261 && itemname1 != 267 && itemname1 != 268 && itemname1 != 272 && itemname1 != 276 && itemname1 != 283) {
                                p.sendMessage("§c该宝石只支持镶嵌在武器。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            dj = useing.get(p.getName()) - 100;
                            if (!TakePlayerItem(p, item_baoshi_luofengshi(dj))) {
                                p.sendMessage("§c所需物品\"" + luofengshi[dj - 1] + "§c\"不足。");
                                e.setCancelled(true);
                                p.closeInventory();
                                p.sendMessage("§c你取消了本次操作!");
                                useing.remove(p.getName());
                                return;
                            }

                            item = xiangqian(item, "落凤石", luofengshi[dj - 1]);
                            p.sendMessage("§a装备成功镶嵌" + luofengshi[dj - 1]);
                        }

                        e.setCancelled(true);
                        e.setCurrentItem(item);
                        useing.remove(p.getName());
                        p.closeInventory();
                    } else {
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage("§c你取消了本次操作!");
                        useing.remove(p.getName());
                    }
                } else if (item != null && !item.getType().equals(Material.AIR) && item.getItemMeta().hasDisplayName()) {
                    String itemname = item.getItemMeta().getDisplayName();
                    if (getluofengshiLevel(itemname) > 0 || getqinglinshiLevel(itemname) > 0 || getshuixishiLevel(itemname) > 0 || getyanlinyuLevel(itemname) > 0 || getyanruyuLevel(itemname) > 0 || getyoumingshiLevel(itemname) > 0 || getzuoyishiLevel(itemname) > 0) {
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage("§a你正在使用" + itemname);
                        p.sendMessage("§a按E打开背包，鼠标右键点击一件装备来进行宝石镶嵌");
                        p.sendMessage("§a或者右键点击背包空位来取消本次操作");
                        if (itemname.contains("落凤石")) {
                            useing.put(p.getName(), 100 + getluofengshiLevel(itemname));
                        } else if (itemname.contains("青鳞石")) {
                            useing.put(p.getName(), 110 + getqinglinshiLevel(itemname));
                        } else if (itemname.contains("水夕石")) {
                            useing.put(p.getName(), 120 + getshuixishiLevel(itemname));
                        } else if (itemname.contains("烟灵玉")) {
                            useing.put(p.getName(), 130 + getyanlinyuLevel(itemname));
                        } else if (itemname.contains("炎如玉")) {
                            useing.put(p.getName(), 140 + getyanruyuLevel(itemname));
                        } else if (itemname.contains("幽冥石")) {
                            useing.put(p.getName(), 150 + getyoumingshiLevel(itemname));
                        } else if (itemname.contains("濯燿石")) {
                            useing.put(p.getName(), 160 + getzuoyishiLevel(itemname));
                        }
                    }
                }
            }
        }

    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        double damage = e.getDamage();
        Player d;
        ItemStack itemtou;
        String itemtui1;
        int itemxie3;
        int itemname1;
        int ylydj;
        int sxsdj;
        int yrydj;
        ItemStack itemtui11;
        String itemname11;
        if (e.getDamager() instanceof Player) {
            d = (Player) e.getDamager();
            itemtou = d.getItemInHand();
            if (itemtou.getTypeId() == 261) {
                return;
            }

            if (itemtou.hasItemMeta()) {
                ItemMeta itemxiong3 = itemtou.getItemMeta();
                if (itemxiong3.hasDisplayName()) {
                    itemtui1 = itemxiong3.getDisplayName();
                    itemxie3 = getzhuangbeibaoshilevel(itemtou, "青鳞石");
                    itemname1 = getzhuangbeibaoshilevel(itemtou, "落凤石");
                    ylydj = getzhuangbeibaoshilevel(itemtou, "炎如玉");
                    sxsdj = getzhuangbeibaoshilevel(itemtou, "幽冥石");
                    yrydj = getzhuangbeibaoshilevel(itemtou, "濯燿石");
                    if (itemxie3 > 0) {
                        damage += (double) qinglinshishuju[itemxie3 - 1];
                    }

                    if (itemname1 > 0) {
                        e.getEntity().setFireTicks(luofengshishuju[itemname1 - 1] * 20);
                    }

                    if (ylydj > 0 && (int) (Math.random() * 100.0D) + 1 <= sjyanruyu[ylydj - 1]) {
                        damage *= 2.0D;
                    }

                    if (sxsdj > 0 && (int) (Math.random() * 100.0D) + 1 <= sjyoumingshi[sxsdj - 1]) {
                        e.getEntity().getWorld().playEffect(e.getEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 5);
                        Player ymsdj = (Player) e.getEntity();
                        ymsdj.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
                    }

                    if (yrydj > 0 && (int) (Math.random() * 100.0D) + 1 <= sjzuoyishi[yrydj - 1]) {
                        if (d.getMaxHealth() * 0.03D + d.getHealth() >= d.getMaxHealth()) {
                            d.setHealth(d.getMaxHealth());
                        } else {
                            d.setHealth(d.getHealth() + d.getMaxHealth() * 0.03D);
                        }
                    }
                }
            }
        } else if (e.getDamager() instanceof Arrow) {
            Arrow d1 = (Arrow) e.getDamager();
            LivingEntity itemtou1 = (LivingEntity) d1.getShooter();
            if (itemtou1 instanceof Player) {
                Player itemxiong31 = (Player) itemtou1;
                itemtui11 = itemxiong31.getItemInHand();
                if (itemtui11.hasItemMeta()) {
                    ItemMeta itemxie31 = itemtui11.getItemMeta();
                    if (itemxie31.hasDisplayName()) {
                        itemname11 = itemxie31.getDisplayName();
                        ylydj = getzhuangbeibaoshilevel(itemtui11, "青鳞石");
                        sxsdj = getzhuangbeibaoshilevel(itemtui11, "落凤石");
                        yrydj = getzhuangbeibaoshilevel(itemtui11, "炎如玉");
                        int ymsdj1 = getzhuangbeibaoshilevel(itemtui11, "幽冥石");
                        int zysdj = getzhuangbeibaoshilevel(itemtui11, "濯燿石");
                        if (ylydj > 0) {
                            damage += (double) qinglinshishuju[ylydj - 1];
                        }

                        if (sxsdj > 0) {
                            if (e.isCancelled()) {
                                return;
                            }

                            e.getEntity().setFireTicks(luofengshishuju[sxsdj - 1] * 20);
                        }

                        if (yrydj > 0 && (int) (Math.random() * 100.0D) + 1 <= sjyanruyu[ylydj - 1]) {
                            damage *= 2.0D;
                        }

                        if (ymsdj1 > 0 && (int) (Math.random() * 100.0D) + 1 <= sjyoumingshi[ymsdj1 - 1]) {
                            e.getEntity().getWorld().playEffect(e.getEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 5);
                            Player player = (Player) e.getEntity();
                            player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
                        }

                        if (zysdj > 0 && (int) (Math.random() * 100.0D) + 1 <= sjzuoyishi[zysdj - 1]) {
                            itemtou1.sendMessage(String.valueOf(itemtou1.getMaxHealth() * 0.03D));
                            if (itemtou1.getMaxHealth() * 0.03D + itemtou1.getHealth() >= itemtou1.getMaxHealth()) {
                                itemtou1.setHealth(itemtou1.getMaxHealth());
                            } else {
                                itemtou1.setHealth(itemtou1.getHealth() + itemtou1.getMaxHealth() * 0.03D);
                            }
                        }
                    }
                }
            }
        }

        if (e.getEntity() instanceof Player) {
            d = (Player) e.getEntity();
            itemtou = d.getInventory().getHelmet();
            if (itemtou != null && itemtou.hasItemMeta() && itemtou.getItemMeta().hasLore()) {
                String itemxiong32 = itemtou.getItemMeta().getDisplayName();
                int itemtui12 = getzhuangbeibaoshilevel(itemtou, "烟灵玉");
                itemxie3 = getzhuangbeibaoshilevel(itemtou, "水夕石");
                if (itemtui12 > 0 && (int) (Math.random() * 100.0D) + 1 <= yanlinyushuju[itemtui12 - 1]) {
                    e.setCancelled(true);
                    if (e.getDamager() instanceof Player) {
                        e.getDamager().sendMessage("§c对方闪避了你的攻击。");
                    }

                    d.sendMessage("§a你闪避了对方的攻击.");
                    return;
                }

                if (itemxie3 > 0) {
                    damage -= (double) shuixishishuju[itemxie3 - 1];
                }
            }

            ItemStack itemxiong33 = d.getInventory().getChestplate();
            if (itemxiong33 != null && itemxiong33.hasItemMeta() && itemxiong33.getItemMeta().hasLore()) {
                itemtui1 = itemxiong33.getItemMeta().getDisplayName();
                itemxie3 = getzhuangbeibaoshilevel(itemxiong33, "烟灵玉");
                itemname1 = getzhuangbeibaoshilevel(itemxiong33, "水夕石");
                if (itemxie3 > 0 && (int) (Math.random() * 100.0D) + 1 <= yanlinyushuju[itemxie3 - 1]) {
                    e.setCancelled(true);
                    if (e.getDamager() instanceof Player) {
                        e.getDamager().sendMessage("§c对方闪避了你的攻击。");
                    }

                    d.sendMessage("§a你闪避了对方的攻击.");
                    return;
                }

                if (itemname1 > 0) {
                    damage -= (double) shuixishishuju[itemname1 - 1];
                }
            }

            itemtui11 = d.getInventory().getLeggings();
            if (itemtui11 != null && itemtui11.hasItemMeta() && itemtui11.getItemMeta().hasLore()) {
                String itemxie32 = itemtui11.getItemMeta().getDisplayName();
                itemname1 = getzhuangbeibaoshilevel(itemtui11, "烟灵玉");
                ylydj = getzhuangbeibaoshilevel(itemtui11, "水夕石");
                if (itemname1 > 0 && (int) (Math.random() * 100.0D) + 1 <= yanlinyushuju[itemname1 - 1]) {
                    e.setCancelled(true);
                    if (e.getDamager() instanceof Player) {
                        e.getDamager().sendMessage("§c对方闪避了你的攻击。");
                    }

                    d.sendMessage("§a你闪避了对方的攻击.");
                    return;
                }

                if (ylydj > 0) {
                    damage -= (double) shuixishishuju[ylydj - 1];
                }
            }

            ItemStack itemxie33 = d.getInventory().getBoots();
            if (itemxie33 != null && itemxie33.hasItemMeta() && itemxie33.getItemMeta().hasLore()) {
                itemname11 = itemxie33.getItemMeta().getDisplayName();
                ylydj = getzhuangbeibaoshilevel(itemxie33, "烟灵玉");
                sxsdj = getzhuangbeibaoshilevel(itemxie33, "水夕石");
                if (ylydj > 0 && (int) (Math.random() * 100.0D) + 1 <= yanlinyushuju[ylydj - 1]) {
                    e.setCancelled(true);
                    if (e.getDamager() instanceof Player) {
                        e.getDamager().sendMessage("§c对方闪避了你的攻击。");
                    }

                    d.sendMessage("§a你闪避了对方的攻击.");
                    return;
                }

                if (sxsdj > 0) {
                    damage -= (double) shuixishishuju[sxsdj - 1];
                }
            }
        }

        if (damage < 1.0D) {
            damage = 1.0D;
        }

        e.setDamage(damage);
    }

    private static int getzhuangbeibaoshilevel(ItemStack xqitem, String baoshi) {
        ItemMeta meta = xqitem.getItemMeta();
        if (!meta.hasLore()) {
            return 0;
        } else {
            Iterator var4;
            String line;
            String baoshiname;
            switch (baoshi) {
                case "青鳞石":
                    var4 = meta.getLore().iterator();
                    line = null;

                    while (var4.hasNext()) {
                        line = (String) var4.next();
                        if (line.contains("青鳞石")) {
                            break;
                        }
                    }

                    baoshiname = line.split("§7  -  ")[0];
                    return getqinglinshiLevel(baoshiname);
                case "落凤石":
                    var4 = meta.getLore().iterator();
                    line = null;

                    while (var4.hasNext()) {
                        line = (String) var4.next();
                        if (line.contains("落凤石")) {
                            break;
                        }
                    }

                    baoshiname = line.split("§7  -  ")[0];
                    return getluofengshiLevel(baoshiname);
                case "烟灵玉":
                    var4 = meta.getLore().iterator();
                    line = null;

                    while (var4.hasNext()) {
                        line = (String) var4.next();
                        if (line.contains("烟灵玉")) {
                            break;
                        }
                    }

                    baoshiname = line.split("§7  -  ")[0];
                    return getyanlinyuLevel(baoshiname);
                case "水夕石":
                    var4 = meta.getLore().iterator();
                    line = null;

                    while (var4.hasNext()) {
                        line = (String) var4.next();
                        if (line.contains("水夕石")) {
                            break;
                        }
                    }

                    baoshiname = line.split("§7  -  ")[0];
                    return getshuixishiLevel(baoshiname);
                case "炎如玉":
                    var4 = meta.getLore().iterator();
                    line = null;

                    while (var4.hasNext()) {
                        line = (String) var4.next();
                        if (line.contains("炎如玉")) {
                            break;
                        }
                    }

                    baoshiname = line.split("§7  -  ")[0];
                    return getyanruyuLevel(baoshiname);
                case "幽冥石":
                    var4 = meta.getLore().iterator();
                    line = null;

                    while (var4.hasNext()) {
                        line = (String) var4.next();
                        if (line.contains("幽冥石")) {
                            break;
                        }
                    }

                    baoshiname = line.split("§7  -  ")[0];
                    return getyoumingshiLevel(baoshiname);
                default:
                    if (baoshi.equals("濯燿石")) {
                        var4 = meta.getLore().iterator();

                        while (var4.hasNext()) {
                            line = (String) var4.next();
                            if (line.contains("濯燿石")) {
                                baoshiname = line.split("§7  -  ")[0];
                                return getzuoyishiLevel(baoshiname);
                            }
                        }
                    }

                    return 0;
            }
        }
    }
}
