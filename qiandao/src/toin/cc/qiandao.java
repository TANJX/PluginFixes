package toin.cc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class qiandao extends JavaPlugin implements Listener {
    private final HashMap<String, Integer> playerMenu = new HashMap<>();
    private PluginFile config;

    @Override
    public void onEnable() {
        config = new PluginFile(this, "config.yml", "config.yml");
        config.setEditable(false);
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + config.getString("mysql.ip") + "/" + config.getString("mysql.dbname"), config.getString("mysql.dbuser"), config.getString("mysql.dbpass"));
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `qiandao` (`player` varchar(64) NOT NULL,`qd1` varchar(2) DEFAULT NULL,`qd2` varchar(2) DEFAULT NULL,`qd3` varchar(2) DEFAULT NULL,`qd4` varchar(2) DEFAULT NULL,`qd5` varchar(2) DEFAULT NULL,`qd6` varchar(2) DEFAULT NULL,`qd7` varchar(2) DEFAULT NULL,`qd8` varchar(2) DEFAULT NULL,`qd9` varchar(2) DEFAULT NULL,`qd10` varchar(2) DEFAULT NULL,`qd11` varchar(2) DEFAULT NULL,`qd12` varchar(2) DEFAULT NULL,`qd13` varchar(2) DEFAULT NULL,`qd14` varchar(2) DEFAULT NULL,`qd15` varchar(2) DEFAULT NULL,`qd16` varchar(2) DEFAULT NULL,`qd17` varchar(2) DEFAULT NULL,`qd18` varchar(2) DEFAULT NULL,`qd19` varchar(2) DEFAULT NULL,`qd20` varchar(2) DEFAULT NULL,`qd21` varchar(2) DEFAULT NULL,`qd22` varchar(2) DEFAULT NULL,`qd23` varchar(2) DEFAULT NULL,`qd24` varchar(2) DEFAULT NULL,`qd25` varchar(2) DEFAULT NULL,`qd26` varchar(2) DEFAULT NULL,`qd27` varchar(2) DEFAULT NULL,`qd28` varchar(2) DEFAULT NULL,`qd29` varchar(2) DEFAULT NULL,`qd30` varchar(2) DEFAULT NULL,`qd31` varchar(2) DEFAULT NULL,PRIMARY KEY (`player`)) ENGINE=MyISAM DEFAULT CHARSET=gbk;");
        } catch (SQLException e1) {
            Bukkit.getConsoleSender().sendMessage("��c�������ݿ�ʧ�ܣ�");
        }
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("qiandao")) {
            if (sender.isOp() && args.length > 0
                    && args[0].equalsIgnoreCase("reload")) {
                config.reload();
                sender.sendMessage("��f[��dÿ��ǩ����f]��a���سɹ�.");
            } else
                openQd((Player) sender);
        }
        return true;
    }

    //ǩ��ӴӴӴӴӴ
    private void openQd(Player p) {
        Connection conn;
        SimpleDateFormat MM = new SimpleDateFormat("MM");
        String timeM = "" + Integer.parseInt(MM.format(new Date()));
        SimpleDateFormat dd = new SimpleDateFormat("dd");
        String timed = "" + Integer.parseInt(dd.format(new Date()));
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + config.getString("mysql.ip") + "/" + config.getString("mysql.dbname"), config.getString("mysql.dbuser"), config.getString("mysql.dbpass"));
            Statement statement = conn.createStatement();
            String sql = (new StringBuilder("select * from qiandao where player='")).append(p.getName()).append("' ").toString();
            ResultSet rs = statement.executeQuery(sql);
            //�������Ҵ�δǩ��,�ʹ���һ������������
            if (!rs.next()) {//�����ھʹ���
                sql = "INSERT INTO qiandao VALUES ('" + p.getName() + "', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' )";
                statement.executeUpdate(sql);
                sql = "select * from qiandao where player='" + p.getName() + "'";
                rs = statement.executeQuery(sql);
                rs.next();
                //conn.close();
            }
            String qdtime = rs.getString("qd" + timed);
            //��������Ѿ�ǩ��
            if (timeM == qdtime) {
                conn.close();
                return;
            }
            Inventory inv = getServer().createInventory(null, 54, "��f[��6��lÿ��ǩ����f]");
            playerMenu.put(p.getName(), 1);
            int ljqd = 0;//�ۼ�ǩ������
            for (int i = 0; i < getCurrentMonthLastDay(); i++) {
                ItemStack item;
                if (i + 1 < Integer.parseInt(timed)) {//��ǰ������ǰ
                    if (timeM.equals(rs.getString("qd" + (i + 1)))) {//��ǰǩ������
                        item = new ItemStack(Material.WOOL, i + 1, (short) 5);
                        ItemMeta id = item.getItemMeta();
                        id.setDisplayName("��a��l��ǩ��");
                        List<String> lore = new ArrayList<>();
                        lore.add("��7����" + (i + 1) + "��");
                        id.setLore(lore);
                        item.setItemMeta(id);
                        ljqd++;
                    } else {//��ǰûǩ��
                        item = new ItemStack(Material.WOOL, i + 1, (short) 14);
                        ItemMeta id = item.getItemMeta();
                        id.setDisplayName("��4��lδǩ��");
                        List<String> lore = new ArrayList<>();
                        lore.add("��7����" + (i + 1) + "��");
                        id.setLore(lore);
                        item.setItemMeta(id);
                    }
                } else if (i + 1 == Integer.parseInt(timed)) {//����
                    if (rs.getString("qd" + timed).equals(timeM)) {
                        item = new ItemStack(Material.WOOL, i + 1, (short) 5);
                        ItemMeta id = item.getItemMeta();
                        id.setDisplayName("��a��l��ǩ����b(����)");
                        List<String> lore = new ArrayList<>();
                        lore.add("��7����" + (i + 1) + "��");
                        id.setLore(lore);
                        item.setItemMeta(id);
                        ljqd++;
                    } else {
                        item = new ItemStack(Material.WOOL, i + 1, (short) 1);
                        ItemMeta id = item.getItemMeta();
                        id.setDisplayName("��4��lδǩ����b(����)");
                        List<String> lore = new ArrayList<>();
                        lore.add("��7����" + (i + 1) + "��");
                        lore.add("��c��l���ǩ��");
                        id.setLore(lore);
                        item.setItemMeta(id);
                    }
                } else {//֮��
                    item = new ItemStack(Material.WOOL, i + 1, (short) 7);
                    ItemMeta id = item.getItemMeta();
                    id.setDisplayName("��4��l����δ��");
                    List<String> lore = new ArrayList<>();
                    lore.add("��7����" + (i + 1) + "��");
                    id.setLore(lore);
                    item.setItemMeta(id);
                }
                inv.addItem(item);
            }
            ItemStack item = new ItemStack(Material.THIN_GLASS, 1, (short) 0);
            ItemMeta id = item.getItemMeta();
            id.setDisplayName("��7---�ָ�---");
            //id.addEnchant(Enchantment.getById(0), 1, true);
            item.setItemMeta(id);
            inv.setItem(36, item);
            inv.setItem(37, item);
            inv.setItem(38, item);
            inv.setItem(39, item);
            inv.setItem(40, item);
            inv.setItem(41, item);
            inv.setItem(42, item);
            inv.setItem(43, item);
            inv.setItem(44, item);
            //�����ۼ�ǩ��
            item = new ItemStack(Material.WATCH, ljqd, (short) 7);
            id = item.getItemMeta();
            id.setDisplayName("��d��l�����ۼ�ǩ��");
            List<String> lore = new ArrayList<>();
            lore.add("��7�ۼ�ǩ����������a" + ljqd);
            id.setLore(lore);
            item.setItemMeta(id);
            inv.setItem(45, item);
            //����˵��
            item = new ItemStack(Material.APPLE, 1, (short) 0);
            id = item.getItemMeta();
            id.setDisplayName("��c����˵��");
            lore = new ArrayList<>();
            lore.addAll(config.getStringList("config.jieshao"));
            id.setLore(lore);
            item.setItemMeta(id);
            inv.setItem(46, item);

            p.openInventory(inv);
            conn.close();
        } catch (SQLException ignored) {
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        (new BukkitRunnable() {
            public void run() {
                openQd(p);
            }
        }).runTaskLater(this, 10L);
    }

    @EventHandler
    public void onPlayerInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            if (playerMenu.containsKey(event.getPlayer().getName())) {
                playerMenu.remove(event.getPlayer().getName());
            }
        }
    }

    @EventHandler
    public final void onInventoryClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            if (playerMenu.containsKey(e.getWhoClicked().getName()))
                e.setCancelled(true);
            if (e.getInventory() == null) return;
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == null || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName())
                return;
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("��4��lδǩ��")) {
                p.sendMessage("��f[��dÿ��ǩ����f]��c��ȥ�����ڲ�����ǩ��.");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("��a��l��ǩ����b(����)")) {
                p.sendMessage("��f[��dÿ��ǩ����f]��c������Ѿ�ǩ������.");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("��4��l����δ��")) {
                p.sendMessage("��f[��dÿ��ǩ����f]��c������ǰǩ��.");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("��4��lδǩ����b(����)")) {
                //���½���δǩ���ķ���
                int i = e.getCurrentItem().getAmount();
                ItemStack item = new ItemStack(Material.WOOL, i, (short) 5);
                ItemMeta id = item.getItemMeta();
                id.setDisplayName("��a��l��ǩ����b(����)");
                List<String> lore = new ArrayList<>();
                lore.add("��7����" + i + "��");
                id.setLore(lore);
                item.setItemMeta(id);
                e.getInventory().setItem(i - 1, item);
                //�����ۼ�ǩ������
                i = e.getInventory().getItem(45).getAmount() + 1;
                item = new ItemStack(Material.WATCH, i, (short) 7);
                id = item.getItemMeta();
                id.setDisplayName("��d��l�����ۼ�ǩ��");
                lore = new ArrayList<>();
                lore.add("��7�ۼ�ǩ����������a" + i);
                id.setLore(lore);
                item.setItemMeta(id);
                e.getInventory().setItem(45, item);
                //��¼����ǩ��
                try {
                    SimpleDateFormat MM = new SimpleDateFormat("MM");
                    String timeM = "" + Integer.parseInt(MM.format(new Date()));
                    SimpleDateFormat dd = new SimpleDateFormat("dd");
                    String timed = "" + Integer.parseInt(dd.format(new Date()));
                    Connection conn = DriverManager.getConnection("jdbc:mysql://" + config.getString("mysql.ip") + "/" + config.getString("mysql.dbname"), config.getString("mysql.dbuser"), config.getString("mysql.dbpass"));
                    Statement statement = conn.createStatement();
                    String sql = "UPDATE qiandao SET qd" + timed + " = '" + timeM + "' WHERE player = '" + p.getName() + "'";
                    statement.executeUpdate(sql);
                    conn.close();
                } catch (SQLException ignored) {

                }
                //���轱��
                //
                List<String> jiangliList = config.getStringList("config.jiangli.vip0");
                if (p.hasPermission("qiandao.jiangli.vip1")) {
                    jiangliList = config.getStringList("config.jiangli.vip1");
                } else if (p.hasPermission("qiandao.jiangli.vip2")) {
                    jiangliList = config.getStringList("config.jiangli.vip2");
                } else if (p.hasPermission("qiandao.jiangli.vip3")) {
                    jiangliList = config.getStringList("config.jiangli.vip3");
                } else if (p.hasPermission("qiandao.jiangli.vip4")) {
                    jiangliList = config.getStringList("config.jiangli.vip4");
                }
                jiangliList.forEach(s ->
                        getServer().dispatchCommand(getServer().getConsoleSender(),
                                s.replace("%player%", p.getName())));
                p.sendMessage("��f[��dÿ��ǩ����f]��a���ճɹ�ǩ��,��ý�����");
                if (i == 7) {//
                    p.sendMessage("��f[��dÿ��ǩ����f]��a�ۼ�ǩ���ﵽ��b7��a��,��ý�����");
                    List<String> leijiList = config.getStringList("config.leiji.7");
                    for (String leijicmd : leijiList) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), leijicmd.replace("%player%", p.getName()));
                    }
                } else if (i == 15) {//
                    p.sendMessage("��f[��dÿ��ǩ����f]��a�ۼ�ǩ���ﵽ��b15��a��,��ý�����");
                    List<String> leijiList = config.getStringList("config.leiji.15");
                    for (String leijicmd : leijiList) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), leijicmd.replace("%player%", p.getName()));
                    }
                } else if (i == 28) {//
                    p.sendMessage("��f[��dÿ��ǩ����f]��a�ۼ�ǩ���ﵽ��b28��a��,��ý�����");
                    List<String> leijiList = config.getStringList("config.leiji.28");
                    for (String leijicmd : leijiList) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), leijicmd.replace("%player%", p.getName()));
                    }
                }
            }
        }
    }

    private static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//����������Ϊ���µ�һ��
        a.roll(Calendar.DATE, -1);//���ڻع�һ�죬Ҳ�������һ��
        return a.get(Calendar.DATE);
    }

}