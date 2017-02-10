//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.github.SHENDong;

import io.github.sjj118.credit.CreditAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreditShop extends JavaPlugin implements Listener {
    private final CreditAPI creditAPI = new CreditAPI();
    private final List<String> additionalLore = new ArrayList<>();

    public void onEnable() {
        this.getLogger().info("CreditShop_v1.0�Ѽ���");
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }
        PluginFile loreFile = new PluginFile(this, "lore.yml", "lore.yml");
        loreFile.setEditable(false);
        System.out.print(loreFile.getKeys(true));
        for (String lore : loreFile.getStringList("lore")) {
            additionalLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("Cshop")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("��9CreditShop ��f- ��5����̨�޷�ִ�и�ָ��");
                return true;
            }

            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage("��9CreditShop ��2-----��6������2-----");
                p.sendMessage("/Cshop open ��9���̵����");
                p.sendMessage("/Cshop add [x] [y] [�۸�] [����]��9����������Ʒ��ĳĳ�۸��ϼܵ���x��y��");
                p.sendMessage("/Cshop remove [x] [y] ��9ɾ����x�е�y����Ʒ");
                return true;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("open")) {
                if (!p.hasPermission("CreditShop.open") && !p.hasPermission("CreditShop.admin")) {
                    p.sendMessage("��9CreditShop ��f- ��9��û��Ȩ��������");
                    return true;
                }

                this.ShopOpen(p);
                return true;
            }

            if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
                if (!p.hasPermission("CreditShop.remove") && !p.hasPermission("CreditShop.admin")) {
                    p.sendMessage("��9CreditShop ��f- ��9��û��Ȩ��������");
                    return true;
                }

                if (args[1].matches("[0-9]") && args[2].matches("[0-9]")) {
                    this.removeItem(p, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    return true;
                }

                p.sendMessage("��9CreditShop ��f- ��4����ֻ��Ϊ����");
                return true;
            }

            if (args.length == 5 && (args[0].equalsIgnoreCase("add") || p.hasPermission("CreditShop.admin")) && p.hasPermission("CreditShop.add")) {
                if (args[1].matches("[0-9]") && args[2].matches("[0-9]")) {
                    int x = Integer.parseInt(args[1]);
                    int y = Integer.parseInt(args[2]);
                    if (x <= 0 && y > 0 && x < 55 && y < 55) {
                        p.sendMessage("��9Credit ��f- ��e����������Χ");
                        return true;
                    }

                    ItemStack i = p.getInventory().getItemInMainHand();
                    this.addItem(p, i, Integer.parseInt(args[3]), args[4], x, y);
                    return true;
                }

                p.sendMessage("��9CreditShop ��f- ��4���������ֻ��Ϊ����");
                return true;
            }
        }

        return true;
    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onPlayerClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("��2��ȯ�̵�")) {
            Player p = (Player) event.getWhoClicked();
            File file = new File(this.getDataFolder(), "Shop.yml");
            if (file.exists()) {
                FileConfiguration config = this.load(file);
                int slot = event.getRawSlot() + 1;

                for (int x = 1; x <= config.getInt("Num"); ++x) {
                    int Slot = config.getInt(x + ".Slot");
                    if (slot == Slot) {
                        int price = config.getInt(x + ".Price");
                        if (this.creditAPI.getCredit(p.getName()) < price) {
                            p.closeInventory();
                            p.sendMessage("��9CreditShop ��f- ��9��û���㹻�ĵ�ȯ���������Ʒ");
                            return;
                        }

                        this.creditAPI.takeCredit(p.getName(), price);
                        p.closeInventory();
                        ItemStack i = config.getItemStack(x + ".ItemStack");
                        p.getInventory().addItem(i);
                        p.sendMessage("��9CreditShop ��f- ��9�ɹ����򣡡�b����" + price + "��ȯ");
                        this.getLogger().info(p.getName() + "�ڵ�ȯ�̵깺����" + i.getType());
                        return;
                    }
                }

            }
        }
    }

    private FileConfiguration load(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    private void ShopOpen(Player p) {
        File file = new File(this.getDataFolder(), "Shop.yml");
        if (!file.exists()) {
            p.sendMessage("��9CreditShop ��f- ��9Ŀǰ�̵������޻�Ʒ");
        } else {
            FileConfiguration config = this.load(file);
            int size = this.getSize();
            if (size == 0) {
                p.sendMessage("��9CreditShop ��f- ��9Ŀǰ�̵������޻�Ʒ");
            } else {
                size = this.getContainer(size);
                Inventory inv = Bukkit.createInventory(null, size, "��2��ȯ�̵�");
                int num = config.getInt("Num");

                for (int x = 1; x <= num; ++x) {
                    ItemStack i = new ItemStack(config.getItemStack(x + ".ItemStack"));
                    ItemMeta meta = i.getItemMeta();
                    int price = config.getInt(x + ".Price");
                    ArrayList<String> lore = new ArrayList<>();
                    String Lore = config.getString(x + ".Lore");
                    lore.add("��2����" + price + "��ȯ");
                    lore.add(Lore);
                    lore.addAll(additionalLore);
                    meta.setLore(lore);
                    i.setItemMeta(meta);
                    inv.setItem(config.getInt(x + ".Slot") - 1, i);
                }

                p.openInventory(inv);
            }
        }
    }

    private int getSize() {
        File file = new File(this.getDataFolder(), "Shop.yml");
        if (!file.exists()) {
            return 0;
        } else {
            FileConfiguration config = this.load(file);
            int size = 0;

            for (int x = 1; x <= config.getInt("Num"); ++x) {
                int figure = config.getInt(x + ".Slot");
                if (size < figure) {
                    size = figure;
                }
            }

            return size;
        }
    }

    private int getContainer(int x) {
        return x > 0 && x < 10 ? 9 : (x > 9 && x < 19 ? 18 : (x > 18 && x < 28 ? 27 : (x > 27 && x < 37 ? 36 : (x > 36 && x < 46 ? 45 : 54))));
    }

    private void addItem(Player p, ItemStack i, int Price, String args, int x, int y) {
        if (i.getType() == Material.AIR) {
            p.sendMessage("��9Credit ��f- ��9�޷����ۿ���Ʒ");
        } else {
            File file = new File(this.getDataFolder(), "Shop.yml");
            FileConfiguration config;
            if (!file.exists()) {
                config = this.load(file);
                config.set("Num", 1);
                config.set("1.ItemStack", i);
                config.set("1.Price", Price);
                config.set("1.Lore", args);
                config.set("1.Slot", (x - 1) * 9 + y);
                p.sendMessage("��9CreditShop ��f- ��9������Ʒ�ɹ�");

                try {
                    config.save(file);
                } catch (IOException var12) {
                    var12.printStackTrace();
                }

            } else {
                config = this.load(file);
                int slot = (x - 1) * 9 + y;
                if (this.isCover(config, slot)) {
                    p.sendMessage("��9Credit ��f- ��9�ø�λ��������Ʒ");
                } else {
                    int num = config.getInt("Num");
                    if (num == 54) {
                        p.sendMessage("��9CreditShop ��f- ��e�̵�����,�޷������");
                    } else {
                        config.set("Num", num + 1);
                        config.set(num + 1 + ".ItemStack", i);
                        config.set(num + 1 + ".Price", Price);
                        config.set(num + 1 + ".Lore", args);
                        config.set(num + 1 + ".Slot", slot);

                        try {
                            config.save(file);
                        } catch (IOException var13) {
                            var13.printStackTrace();
                        }

                        p.sendMessage("��9CreditShop ��f- ��9������Ʒ�ɹ�");
                    }
                }
            }
        }
    }

    private void removeItem(Player p, int x, int y) {
        File file = new File(this.getDataFolder(), "Shop.yml");
        if (file.exists()) {
            FileConfiguration config = this.load(file);
            int slot = (x - 1) * 9 + y;

            for (int i = 1; i <= config.getInt("Num"); ++i) {
                if (slot == config.getInt(i + ".Slot")) {
                    int num;
                    if (i == config.getInt("Num")) {
                        config.set(Integer.toString(i), null);
                        num = config.getInt("Num");
                        config.set("Num", num - 1);

                        try {
                            config.save(file);
                        } catch (IOException var13) {
                            var13.printStackTrace();
                        }

                        p.sendMessage("��9CreditShop ��f- ��2ɾ����Ʒ�ɹ�");
                        return;
                    }

                    num = config.getInt("Num");
                    config.set("Num", num - 1);
                    ItemStack item = config.getItemStack(num + ".ItemStack");
                    int price = config.getInt(num + ".Price");
                    String lore = config.getString(num + ".Lore");
                    config.set(i + ".ItemStack", item);
                    config.set(i + ".Price", price);
                    config.set(i + ".Lore", lore);

                    try {
                        config.save(file);
                    } catch (IOException var14) {
                        var14.printStackTrace();
                    }

                    p.sendMessage("��9Credit ��f- ��2ɾ����Ʒ�ɹ�");
                    return;
                }
            }

            p.sendMessage("��9Credit ��f- ��eҪɾ������Ʒ������");
        }
    }

    private boolean isCover(FileConfiguration config, int slot) {
        int num = config.getInt("Num");

        for (int abc = 1; abc <= num; ++abc) {
            int theSlot = config.getInt(abc + ".Slot");
            if (slot == theSlot) {
                return true;
            }
        }

        return false;
    }
}
