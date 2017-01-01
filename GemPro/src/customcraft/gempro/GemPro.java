//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customcraft.gempro;

import com.me.tft_02.soulbound.api.ItemAPI;
import customcraft.gempro.EnumPack.EnchantmentEnum;
import customcraft.gempro.EnumPack.Msg;
import customcraft.gempro.EnumPack.PotionEffectEnum;
import customcraft.gempro.Handler.FileLoader;
import customcraft.gempro.Handler.Util;
import customcraft.gempro.Listen.Cmd;
import customcraft.gempro.Listen.DamageCheck;
import customcraft.gempro.Listen.MobDrop;
import customcraft.gempro.Listen.furnaceListen;
import customcraft.gempro.OBJ.EnchantmentEntry;
import customcraft.gempro.OBJ.GemOBJ;
import customcraft.gempro.OBJ.PotionOBJ;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class GemPro extends JavaPlugin {
    public Economy economy;
    public static GemPro instance;
    private FileLoader conf;
    private FileLoader drops;
    public FileLoader generalGems;
    public FileLoader enchantGems;
    public List<ItemStack> gemTexture;
    public HashMap<String, HashMap<GemOBJ, Double>> dropList;
    public HashMap<String, GemOBJ> gemList;
    private boolean soulboundEnabled = false;
    public List<Set<Integer>> canFurnList;
    public Double processedNeedMoney;
    public ItemStack processedNeedItem;
    public Integer processedNeedItemAmount;
    public Integer processedChance;
    public boolean processedFaildDisappear;
    public List<String> punchItemList;
    public Double punchNeedMoney;
    public ItemStack punchNeedItem;
    public Integer punchNeedItemAmount;
    public Integer punchChance;
    public boolean punchFaildDisappear;
    public Integer punchMaxAmount;
    public Integer socketChance;
    public boolean socketFalseDisapper;

    public GemPro() {
    }

    public void onEnable() {
        instance = this;
        new Util();
        if(this.getServer().getPluginManager().getPlugin("Vault") != null && this.setupEconomy()) {
            Util.instance.info("启用经济功能");
        } else {
            Util.instance.info("未找到Vault, 经济功能禁用");
        }

        if(this.getServer().getPluginManager().getPlugin("Soulbound") != null && this.getServer().getPluginManager().isPluginEnabled("Soulbound")) {
            this.soulboundEnabled = true;
            Util.instance.info("启用Soulbound支持");
        }

        this.loadFiles();
        this.getServer().getPluginManager().registerEvents(new MobDrop(), this);
        this.getServer().getPluginManager().registerEvents(new DamageCheck(), this);
        this.getServer().getPluginManager().registerEvents(new furnaceListen(), this);
        this.getCommand("GemPro").setExecutor(new Cmd());
        this.initFurnList();
        Util.instance.info(Msg.loadSuccess.getText() + ", 插件启用");
    }

    public void loadFiles() {
        this.conf = new FileLoader(new File(this.getDataFolder(), "conf.yml"));
        this.conf.setTemplateName("/conf.yml");
        this.conf.load();
        this.drops = new FileLoader(new File(this.getDataFolder(), "drops.yml"));
        this.drops.setTemplateName("/drops.yml");
        this.drops.load();
        this.generalGems = new FileLoader(new File(this.getDataFolder(), "generalGems.yml"));
        this.generalGems.setTemplateName("/generalGems.yml");
        this.generalGems.load();
        this.enchantGems = new FileLoader(new File(this.getDataFolder(), "enchantGems.yml"));
        this.enchantGems.setTemplateName("/enchantGems.yml");
        this.enchantGems.load();
        this.dropList = new HashMap();
        this.gemTexture = new ArrayList();
        this.gemList = new HashMap();
        this.punchItemList = new ArrayList();
        this.loadConf();
        this.loadGems();
        this.loadMobDrops();
    }

    private void loadConf() {
        ArrayList tempList = new ArrayList(this.conf.getStringList("宝石材质列表"));
        Iterator string = tempList.iterator();

        String id;
        Integer data;
        Short itemstack;
        ItemStack itemstack1;
        while(string.hasNext()) {
            id = (String)string.next();
            itemstack = 0;
            if(id.contains(":")) {
                data = Util.instance.getNumber(id.split(":")[0]);

                try {
                    itemstack = Short.valueOf(id.split(":")[1]);
                } catch (NumberFormatException var10) {
                    itemstack = 0;
                }
            } else {
                data = Util.instance.getNumber(id);
            }

            if(data != null && data != 0) {
                itemstack1 = Util.instance.validItem(data, itemstack);
                if(itemstack1 != null) {
                    this.gemTexture.add(itemstack1);
                }
            }
        }

        tempList = new ArrayList(this.conf.getStringList("可加工物品列表"));
        string = tempList.iterator();

        while(string.hasNext()) {
            id = (String)string.next();
            itemstack = 0;
            if(id.contains(":")) {
                data = Util.instance.getNumber(id.split(":")[0]);

                try {
                    itemstack = Short.valueOf(id.split(":")[1]);
                } catch (NumberFormatException var9) {
                    itemstack = 0;
                }
            } else {
                data = Util.instance.getNumber(id);
            }

            if(data != null) {
                itemstack1 = Util.instance.validItem(data, itemstack);
                if(itemstack1 != null) {
                    this.punchItemList.add(id);
                }
            }
        }

        this.processedNeedMoney = this.conf.getDouble("每次加工消耗金钱");
        if(this.processedNeedMoney < 0.0D) {
            this.processedNeedMoney = 0.0D;
        }

        String string1 = this.conf.getString("每次加工消耗物品", "0");
        Short data1 = 0;
        Integer id1;
        if(string1.contains(":")) {
            id1 = Util.instance.getNumber(string1.split(":")[0]);

            try {
                data1 = Short.valueOf(string1.split(":")[1]);
            } catch (NumberFormatException var8) {
                data1 = 0;
            }
        } else {
            id1 = Util.instance.getNumber(string1);
        }

        ItemStack itemstack2;
        if(id1 != null) {
            if(id1 > 0) {
                itemstack2 = Util.instance.validItem(id1, data1);
                if(itemstack2 != null) {
                    this.processedNeedItem = itemstack2;
                }
            } else {
                this.processedNeedItem = null;
            }
        } else {
            this.processedNeedItem = null;
        }

        this.processedNeedItemAmount = this.conf.getInt("每次加工消耗物品数量");
        if(this.processedNeedItemAmount <= 0) {
            this.processedNeedItemAmount = 0;
            this.processedNeedItem = null;
        }

        this.processedChance = this.conf.getInt("加工成功率", 20);
        if(this.processedChance < 1) {
            this.processedChance = 20;
        }

        this.processedFaildDisappear = this.conf.getBoolean("加工失败是否消失");
        this.punchNeedMoney = this.conf.getDouble("每次打孔消耗金钱");
        if(this.punchNeedMoney < 0.0D) {
            this.punchNeedMoney = 0.0D;
        }

        string1 = this.conf.getString("每次打孔消耗物品", "0");
        data1 = 0;
        if(string1.contains(":")) {
            id1 = Util.instance.getNumber(string1.split(":")[0]);

            try {
                data1 = Short.valueOf(string1.split(":")[1]);
            } catch (NumberFormatException var7) {
                data1 = 0;
            }
        } else {
            id1 = Util.instance.getNumber(string1);
        }

        if(id1 != null) {
            if(id1 > 0) {
                itemstack2 = Util.instance.validItem(id1, data1);
                if(itemstack2 != null) {
                    this.punchNeedItem = itemstack2;
                }
            } else {
                this.punchNeedItem = null;
            }
        } else {
            this.punchNeedItem = null;
        }

        this.punchNeedItemAmount = this.conf.getInt("每次打孔消耗物品数量");
        if(this.punchNeedItemAmount <= 0) {
            this.punchNeedItemAmount = 0;
            this.punchNeedItem = null;
        }

        this.punchChance = this.conf.getInt("打孔成功率", 20);
        if(this.punchChance < 1) {
            this.punchChance = 20;
        }

        this.punchFaildDisappear = this.conf.getBoolean("打孔失败是否消失");
        this.punchMaxAmount = this.conf.getInt("最大孔数", 2);
        if(this.punchMaxAmount < 1) {
            this.punchMaxAmount = 2;
        }

        this.socketChance = this.conf.getInt("镶嵌成功率", 20);
        if(this.socketChance < 1) {
            this.socketChance = 20;
        }

        this.socketFalseDisapper = this.conf.getBoolean("镶嵌失败是否消失");
    }

    private void loadMobDrops() {
        Set dropKeys = this.drops.getKeys(false);
        Iterator i$ = dropKeys.iterator();

        while(true) {
            while(i$.hasNext()) {
                String key = (String)i$.next();
                if(this.gemList.containsKey(key)) {
                    ArrayList tempList = new ArrayList(this.drops.getStringList(key));

                    for (Object aTempList : tempList) {
                        String value = (String) aTempList;
                        if (value.contains(":")) {
                            String[] sp = value.split(":");
                            String mobName = sp[0];
                            Double chance = Util.instance.getDoublePlusNumber(sp[1]);
                            if (chance > 0.0D) {
                                HashMap tempMap;
                                tempMap = this.dropList.containsKey(mobName) ? this.dropList.get(mobName) : new HashMap();

                                tempMap.put(this.gemList.get(key), chance);
                                this.dropList.put(mobName, tempMap);
                            }
                        }
                    }
                } else {
                    Util.instance.info("配置文件(drops.yml)中使用了不存在的宝石: " + key + ", 请检查");
                }
            }

            return;
        }
    }

    private void loadGems() {
        this.loadEnchantGems();
        this.loadGeneralGems();
    }

    private void loadEnchantGems() {
        Set gemKeys = this.enchantGems.getKeys(false);

        for (Object gemKey : gemKeys) {
            String key = (String) gemKey;
            HashMap enchantmentEffect = new HashMap();
            Integer type = this.enchantGems.getInt(key + ".类型");
            ArrayList tempList = new ArrayList(this.enchantGems.getStringList(key + ".附魔"));

            for (Object aTempList : tempList) {
                String row = (String) aTempList;
                EnchantmentEntry ench = this.getEnchantmentFromStandardString(row);
                if (ench != null) {
                    enchantmentEffect.put(ench.enchantment, ench.level);
                }
            }

            this.gemList.put(key, new GemOBJ(key, 1, null, null, null, null, null, null, null, enchantmentEffect, type));
        }

    }

    private void loadGeneralGems() {
        Set gemKeys = this.generalGems.getKeys(false);

        for (Object gemKey : gemKeys) {
            String key = (String) gemKey;
            ArrayList potionEffect = new ArrayList();
            String extraDamage = this.generalGems.getString(key + ".附加伤害");
            String realDamage = this.generalGems.getString(key + ".直接伤害");
            String critMultiple = this.generalGems.getString(key + ".暴击");
            String ignoreAttack = this.generalGems.getString(key + ".闪避");
            String extraDefense = this.generalGems.getString(key + ".附加防御");
            String suckBlood = this.generalGems.getString(key + ".吸血");
            if (!this.validRange(extraDamage)) {
                extraDamage = null;
            }

            if (!this.validRange(realDamage)) {
                realDamage = null;
            }

            if (!this.validRange(critMultiple)) {
                critMultiple = null;
            }

            Integer value = Util.instance.getNumber(ignoreAttack);
            if (value == null || value <= 0) {
                ignoreAttack = null;
            }

            if (!this.validRange(extraDefense)) {
                extraDefense = null;
            }

            if (!this.validRange(suckBlood)) {
                suckBlood = null;
            }

            Integer type = this.generalGems.getInt(key + ".类型");
            ArrayList tempList = new ArrayList(this.generalGems.getStringList(key + ".药水效果"));

            for (Object aTempList : tempList) {
                String row = (String) aTempList;
                PotionOBJ pot = this.getPotionOBJFromStandardString(row);
                if (pot != null) {
                    potionEffect.add(pot);
                }
            }

            this.gemList.put(key, new GemOBJ(key, 0, extraDamage, realDamage, critMultiple, ignoreAttack, extraDefense, suckBlood, potionEffect, null, type));
        }

    }

    private boolean validRange(String range) {
        if(range != null && range.contains("~")) {
            String[] str = range.split("~");
            if(Util.instance.getNumber(str[0]) != null && str[1].contains("-")) {
                String[] value = str[1].split("-");
                if(Util.instance.getNumber(value[0]) != null && Util.instance.getNumber(value[1]) != null) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean validDoubleRange(String range) {
        if(range != null && range.contains("~")) {
            String[] str = range.split("~");
            if(Util.instance.getDoubleNumber(str[0]) != null && str[1].contains("-")) {
                String[] value = str[1].split("-");
                if(Util.instance.getDoubleNumber(value[0]) != null && Util.instance.getDoubleNumber(value[1]) != null) {
                    return true;
                }
            }
        }

        return false;
    }

    @SuppressWarnings("deprecation")
    private void initFurnList() {
        HashSet weaponList = new HashSet();
        HashSet armorList = new HashSet();
        weaponList.add(256);
        weaponList.add(257);
        weaponList.add(258);
        weaponList.add(261);
        weaponList.addAll(this.getMiddleList(267, 279));
        weaponList.addAll(this.getMiddleList(283, 286));
        weaponList.addAll(this.getMiddleList(290, 294));
        armorList.addAll(this.getMiddleList(298, 317));
        this.canFurnList = Arrays.asList(new Set[]{weaponList, armorList});

        for(int kind = 0; kind < 2; ++kind) {

            for (ItemStack mat : this.gemTexture) {
                for (Object o : ((Set) this.canFurnList.get(kind))) {
                    Integer id = (Integer) o;
                    ItemStack item = new ItemStack(id, 1);
                    FurnaceRecipe recipe = new FurnaceRecipe(item, mat.getType());
                    recipe.setInput(item.getType());
                    this.getServer().addRecipe(recipe);
                }
            }
        }

    }

    private List<Integer> getMiddleList(int min, int max) {
        ArrayList list = new ArrayList();

        for(int x = min; x <= max; ++x) {
            list.add(x);
        }

        return list;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if(economyProvider != null) {
            this.economy = (Economy)economyProvider.getProvider();
        }

        return this.economy != null;
    }

    public void soulboundItem(Player player, ItemStack item) {
        if(this.soulboundEnabled && !ItemAPI.isSoulbound(item)) {
            ItemAPI.soulbindItem(player, item);
        }

    }

    public EnchantmentEntry getEnchantmentFromStandardString(String row) {
        if(row.contains(":")) {
            String[] sp = row.split(":");

            Enchantment ench;
            try {
                ench = EnchantmentEnum.valueOf(sp[0]).getText();
            } catch (IllegalArgumentException var5) {
                ench = null;
            }

            if(ench != null) {
                Integer level = Util.instance.getPlusNumber(sp[1]);
                if(level != null && level > 0) {
                    return new EnchantmentEntry(ench, level);
                }
            }
        }

        return null;
    }

    public PotionOBJ getPotionOBJFromStandardString(String row) {
        if(row.contains(":")) {
            String[] sp = row.split(":");
            String target = "";
            if(sp[0].startsWith("-")) {
                target = "-";
                sp[0] = sp[0].replaceFirst(target, "");
            }

            PotionEffectType ptef;
            try {
                ptef = PotionEffectEnum.valueOf(sp[0]).getText();
            } catch (IllegalArgumentException var8) {
                ptef = null;
            }

            if(ptef != null) {
                Integer level = Util.instance.getPlusNumber(sp[1]);
                if(level != null) {
                    if(level < 1) {
                        level = 1;
                    }

                    level = level - 1;
                    Integer duration = 0;
                    Integer chance;
                    if(sp.length > 3) {
                        duration = Util.instance.getPlusNumber(sp[2]);
                        if(duration == null) {
                            duration = 0;
                        }

                        chance = Util.instance.getPlusNumber(sp[3]);
                    } else {
                        chance = Util.instance.getPlusNumber(sp[2]);
                    }

                    if(chance != null && chance >= 0) {
                        return new PotionOBJ(sp[0], target, level, duration, chance);
                    }
                }
            }
        }

        return null;
    }
}
